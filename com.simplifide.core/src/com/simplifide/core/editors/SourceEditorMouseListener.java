package com.simplifide.core.editors;


import org.eclipse.jface.text.TextViewer;
import org.eclipse.search2.internal.ui.text.AnnotationManagers;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.search.occurence.OccurenceSearchQuery;
import com.simplifide.core.search.TopSearchQuery;
import com.simplifide.core.search.TopSearchResult;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class SourceEditorMouseListener implements MouseListener,IPartListener {

	private EditorFindItem findItem;
	private SourceEditor editor;
	private TextViewer viewer;
	
	private TopSearchResult oldResult;
	private boolean disposed;
	
	
	public SourceEditorMouseListener(SourceEditor editor,TextViewer viewer) {
		this.editor = editor;
		this.viewer = viewer;
		if (viewer != null && this.viewer.getTextWidget() != null)
			this.viewer.getTextWidget().addMouseListener(this);
		editor.getSite().getWorkbenchWindow().getPartService().addPartListener(this);
	}

	public void clearOldResult() {
		if (oldResult != null) 
			AnnotationManagers.removeSearchResult(editor.getSite().getWorkbenchWindow(), oldResult);
	}
	
	public void dispose() {
		this.disposed = true;
		this.clearOldResult();
		if (viewer != null && this.viewer.getTextWidget() != null)
			this.viewer.getTextWidget().removeMouseListener(this);
		if (this.editor != null) editor.getSite().getWorkbenchWindow().getPartService().removePartListener(this);
	}
	
	public void setFindItem(EditorFindItem findItem) {
		this.findItem = findItem;
	}

	public EditorFindItem getFindItem() {
		return findItem;
	}

	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/** TODO : Check differences between finditem calculations */
	public void mouseDown(MouseEvent e) {
		//if (e.button != 1) return;
		if (disposed) return;
		int pos = viewer.getTextWidget().getCaretOffset();
		//this.findItem = EditorUtilities.getEditorFindItem(editor.getDesignFile().getParseDescriptor(),
        //		pos);
		this.findItem = EditorUtilities.getHoverItem(editor.getDesignFile().getParseDescriptor(),
        		pos);
		
	}

	
	public void runSearch(final TopSearchQuery query) {
		
		if (!HardwareChecker.isHighlightEnabled()) return;
		
		TopSearchResult result = query.getResult();
		this.clearOldResult();
		AnnotationManagers.addSearchResult(editor.getSite().getWorkbenchWindow(), result);
		
		oldResult = result;
		Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				query.run(null);
				
			}
		});
	}
	
	public void mouseUp(MouseEvent e) {
		//if (e.button != 1) return;
		
		if (disposed) return;
		DesignFile dfile = this.editor.getDesignFile();
		//if (dfile instanceof DesignFileAlone) return;
		boolean bool = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_MARK_OCCURENCE); 
		if (!bool) return;
		//if (true) return;
		try {
	        if (findItem != null) {
	        	TopSearchQuery query = new OccurenceSearchQuery(findItem,dfile);
	        	runSearch(query);
	        }
	        else {
	    		int pos = viewer.getTextWidget().getCaretOffset();
	        	ReferenceUsage[] usage = EditorUtilities.getBeginEndItem(editor.getDesignFile().getParseDescriptor(), pos);
	        	if (usage != null) {
		        	TopSearchQuery query = new OccurenceSearchQuery(usage);
		        	runSearch(query);
	        	}
	        }
	        
		}
		catch (Exception e1) {
			HardwareLog.logError(e1);
		}
		
	}

	

	public void partActivated(IWorkbenchPart part) {
	}

	
	public void partBroughtToTop(IWorkbenchPart part) {
		
	}

	
	public void partClosed(IWorkbenchPart part) {
		
	}

	
	public void partDeactivated(IWorkbenchPart part) {
		this.clearOldResult();
		
	}

	
	public void partOpened(IWorkbenchPart part) {		
	}




	

} 
