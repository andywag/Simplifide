/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors;

import java.net.URI;
import java.util.HashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareBundle;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.outline.GeneralContentPane;
import com.simplifide.core.baseeditor.source.GeneralFile;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.editors.actions.ExpandTemplateAction;
import com.simplifide.core.editors.actions.FormatAction;
import com.simplifide.core.editors.actions.search.GoToSubMenu;
import com.simplifide.core.editors.actions.search.ReferenceMenu;
import com.simplifide.core.editors.folding.SourceFoldingHover;
import com.simplifide.core.editors.folding.SourceFoldingSupport;
import com.simplifide.core.editors.outline.SourceContentPane;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.vhdl.describer.VhdlFile;

public class SourceEditor extends GeneralEditor implements ChangeListener {

	private HashSet<EditorActionDelegate> actionSet = new HashSet<EditorActionDelegate>();
	
	private ProjectionSupport projectionSupport;
	private SourceFoldingSupport foldingSupport;
	//private OccurenceListener occListener;

	// One of two access mechanisms for the editor
	//private IFile file; 
	//private IFileStore fileStore;
	
	private URI fileLocation;
	// Used only for the fileStore although I am not sure why it isn't used for the file
	private DesignFile designStore;

	private Action completionAction;
	private SourceEditorMouseListener findItemHolder;
	
	public static String GROUP_SIMPLIFIDE = CoreActivator.PLUGIN_ID + ".editor.simplifide.group";
	public static String HDLCONTEXT = "#HdlEditorContext";
	
	// private DesignFile designFile;

	public SourceEditor() {
		super();
	}

	public void addListener(EditorActionDelegate del) {
		this.actionSet.add(del);
	}
	
	
	
	public void dispose() {
		//getColorManager().dispose();
		DesignFile dfile = this.getDesignFile();
		if (dfile != null)
			dfile.closeFileFromEditor();

		this.projectionSupport = null;
		this.foldingSupport = null;
	
		//if (this.occListener != null) this.occListener.dispose();
		this.findItemHolder.clearOldResult();
		if (this.findItemHolder != null) this.findItemHolder.dispose();
		this.findItemHolder = null;
		this.actionSet = null;
		this.designStore = null;
		super.dispose();

	}
	
	public void resetFindItemHolder() {
		this.findItemHolder.clearOldResult();
		if (this.findItemHolder != null) this.findItemHolder.dispose();
		this.findItemHolder = new SourceEditorMouseListener(this,(ProjectionViewer)this.getSourceViewer());

	}
	

	


	public void syncSave() {
		this.getEditorSite().getShell().getDisplay().syncExec(
				new Runnable() {
					public void run() {
						doSave(null);
					}
				});
	}

	public void goToPosition(int pos) {
		
		int newpos = pos;
		if (this.foldingSupport != null) {
			newpos = this.foldingSupport.getRealPosition(pos, this.getSourceViewer().getDocument());
		}
		this.getSourceViewer().getTextWidget().setCaretOffset(newpos);
		this.getSourceViewer().getTextWidget().setSelection(newpos);
	}

    public void goToPosition(int pos, int length) {
    	int newpos = pos;
		if (this.foldingSupport != null) {
			newpos = this.foldingSupport.getRealPosition(pos, this.getSourceViewer().getDocument());
		}
		this.getSourceViewer().getTextWidget().setCaretOffset(newpos);
		this.getSourceViewer().getTextWidget().setSelection(newpos,newpos + length);
    }
	
	
	
	public void createPartControl(Composite parent) {
		
		
		IPreferenceStore store = this.getPreferenceStore();
		IPreferenceStore current = CoreActivator.getDefault().getPreferenceStore();
		ChainedPreferenceStore chain = new ChainedPreferenceStore(new IPreferenceStore[] {current,store});
		this.setPreferenceStore(chain);
		
		super.createPartControl(parent);
		
		ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
		projectionSupport = new ProjectionSupport(viewer,getAnnotationAccess(), getSharedColors());
		projectionSupport.setHoverControlCreator(new SourceFoldingHover.Factory());
		projectionSupport.install();
		// turn projection mode on
		viewer.doOperation(ProjectionViewer.TOGGLE);

		//this.setDocumentProvider(new SourceDocumentProvider());
		// Loads the file in different ways dependent on modes
		IEditorInput einput = this.getEditorInput();
		if (this.getEditorInput() instanceof IFileEditorInput) {
			IFile file =  ((IFileEditorInput) this.getEditorInput()).getFile();
			this.fileLocation = file.getLocationURI();
		}
		else if (this.getEditorInput() instanceof FileStoreEditorInput) {
			FileStoreEditorInput uin = (FileStoreEditorInput)einput;
			URI uri = uin.getURI();
			this.fileLocation = uri;
			
		}

		
		this.initializeCompletionAction();
		this.setFoldingSupport(new SourceFoldingSupport(this,viewer.getProjectionAnnotationModel()));
		this.findItemHolder = new SourceEditorMouseListener(this,viewer);
		//this.parseFinishedAsync(viewer);
		this.startAsyncParse();
		IContextService contextService = (IContextService) getSite().getService(IContextService.class);
		contextService.activateContext(HDLCONTEXT);
		
	}

	public void startAsyncParse() {
		this.getEditorSite().getShell().getDisplay().asyncExec(
				new Runnable() {
					public void run() {
						DesignFile dfile = getDesignFile();
						if (dfile != null && dfile.getBuilder() != null) {
							dfile.getBuilder().build(BuildInterface.BUILD_FILE_OPEN);
							getFoldingSupport().updateFolds(getDesignFile().getParseDescriptor());
							updateContentPane();

						}
						
					}
				});
	}
	
	
	
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		DefaultCharacterPairMatcher mz = new DefaultCharacterPairMatcher( new char[] {'(', ')', '{', '}', '[', ']'});
		support.setCharacterPairMatcher(mz);
		support.setMatchingCharacterPainterPreferenceKeys(PreferenceConstants.EDITOR_PAREN_MATCH,
				PreferenceConstants.EDITOR_PAREN_COLOR);
		super.configureSourceViewerDecorationSupport(support);

	}
	
	
	

	protected DesignFile getOutOfWorkspaceDesignFile() {
		return new VhdlFile(null);
	}

	private void initializeCompletionAction() {
		Action action = new ContentAssistAction(
				HardwareBundle.getDefault(), "ContentAssistProposal.", this);
		String id = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
		action.setActionDefinitionId(id);
		setAction("ContentAssistProposal", action);
		markAsStateDependentAction("ContentAssistProposal", true);
		this.completionAction = action;
	}

	

	
	
	
	public void parseFinished() {
		if (this.getDesignFile() != null) {
			if (this.getFoldingSupport() != null) {
				this.getFoldingSupport().updateFolds(this.getDesignFile().getParseDescriptor());
			}
		}
	}

	@Override
	protected GeneralContentPane createContentPane() {
		return new SourceContentPane(this);
	}
	
	

	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {

		ISourceViewer viewer = new ProjectionViewer(parent, ruler,
				getOverviewRuler(), isOverviewRulerVisible(), styles);

		getSourceViewerDecorationSupport(viewer);
		return viewer;
	}

	public int getCaretPosition() {
		int caretOffset = this.getSourceViewer().getTextWidget().getCaretOffset();
		int foldCaret = this.foldingSupport.getOffsetPosition(caretOffset, this.getSourceViewer().getDocument());
		return foldCaret;

	}


	private void notifyMenuActions() {
		for (EditorActionDelegate del : this.actionSet) {
			del.enableUpdate();
		}
	}
	
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		this.notifyMenuActions();
		menu.addMenuListener(new MenuRemover()); // Really Kludgey way of removing unwanted menu items
		super.editorContextMenuAboutToShow(menu);
		
		this.addGroup(menu, ITextEditorActionConstants.GROUP_EDIT,SourceEditor.GROUP_SIMPLIFIDE);


        //EditorFindItem findItem = EditorUtilities.getEditorFindItem(this.getDesignFile().getParseDescriptor(),
        //		this.getCaretPosition());
        
		EditorFindItem findItem = this.findItemHolder.getFindItem();
		
		
		menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new FormatAction((SourceViewer)this.getSourceViewer()));
		menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new ExpandTemplateAction(this.getDesignFile()));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new ExpandEmacsAutoAction(this.getDesignFile()));

		menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new GoToSubMenu(menu, this, findItem));
		menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new ReferenceMenu(menu, this, findItem));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new RefactorMenu(menu, this,findItem));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,new NewMenu(menu, this));
		
		this.addAction(menu, ITextEditorActionConstants.GROUP_EDIT,ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		this.addAction(menu,ITextEditorActionConstants.GROUP_EDIT,ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);

		
	}

	public IAction getAction(String actionID) {
		if (actionID.equals(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS)) {
			return this.completionAction;
		}
		return super.getAction(actionID);
	}

	public void stateChanged(ChangeEvent arg0) {
		this.parseFinished();

	}

	


	protected DesignFile getDesignFileAlone(IFileStore fileStore) {
		return null;
	}

	public GeneralFile getGeneralFile() { 
		return this.getDesignFile();
	}
	
	public DesignFile getDesignFile() {
		if (this.designStore == null) {
			SourceObject source = SourceObject.resolveObject(this.fileLocation);
			if (source instanceof DesignFile) {
				this.designStore = (DesignFile) source;
				if (this.designStore != null) {
					this.designStore.setOpened(true);
					this.designStore.setEditor(this);
				}
			}
			
			
		}
		return this.designStore;
	}
	
	// Kludge class to remove excess menu items
	public static class MenuRemover implements IMenuListener {

		public void menuAboutToShow(IMenuManager manager) {
			manager.remove(ITextEditorActionConstants.SHIFT_RIGHT);
			manager.remove(ITextEditorActionConstants.SHIFT_LEFT);
			manager.remove("org.eclipse.debug.ui.contextualLaunch.run.submenu");
			manager.remove("org.eclipse.debug.ui.contextualLaunch.debug.submenu");
			manager.remove("org.eclipse.debug.ui.contextualLaunch.profile.submenu");
			manager.remove("ValidationAction");
			manager.removeMenuListener(this);
		}
		
	}
	
	
	
	
	

	
	public void setFoldingSupport(SourceFoldingSupport foldingSupport) {
		this.foldingSupport = foldingSupport;
	}

	public SourceFoldingSupport getFoldingSupport() {
		return foldingSupport;
	}

	

	public void setFindItemHolder(SourceEditorMouseListener findItemHolder) {
		this.findItemHolder = findItemHolder;
	}

	public SourceEditorMouseListener getFindItemHolder() {
		return findItemHolder;
	}
}


