
package com.simplifide.core.source.design;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.GeneralFile;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.hyperlink.SourceHyperlink;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.source.EclipseSourceFolder;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.SourceLocationMap;

public abstract class DesignFile extends GeneralFile {


	private ReferenceItem<CoreProjectBasic> projectRef;
	
	/** Definitely a Kludge as this should be defined in other ways */
	private boolean opened;
	


	/** Project Dependency Information Support for the design file */
	private DesignFileCompileInfo compileInfo;

	public DesignFile(URI uri) {
		super(uri);
		this.baseInit();
		SourceLocationMap.getInstance().addDesignFile(uri, this);
		
	}

	protected void baseInit() {
		super.baseInit();
		this.setCompileInfo(this.createCompileInfo());
		//this.builder = this.createBuilder();
	}

	//protected abstract DesignFileBuilder createBuilder();
	protected DesignFileCompileInfo createCompileInfo() {
		return new DesignFileCompileInfo(this);
	}

	
	
	
	public InputStream getInputStream() {
		try {
			return this.getIFile().getContents();
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}

	/** Returns the text at this line number. Used for search */
	public String getTextAtLine(int line) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getIFile().getContents()));
			String str  = "";
			for (int i=0;i<line;i++) {
				str = reader.readLine();
			}
			return str;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
	/** Returns the nth error */
	public TopError getError(int index) {
		return this.getDesignFileBuilder().getParseDescriptor().getErrorList().get(index);
	}

	/** Deletes the object --- this is also called when the project is deleted 
	 * 	from the file system
	 */
	public void deleteObject() {
		if (this.getBuilder() != null) this.getDesignFileBuilder().deleteObject();
		/*if (this.getProjectRef() != null) {
			EclipseBaseProject proj = (EclipseBaseProject) this.getProjectRef().getObject();
			proj.removeDesignFile(this);
		}*/
			
		this.setBuilder(null);
		this.setCompileInfo(null);
		super.deleteObject();
	}

	
	/* Go to the hyperlink */
	public void goToHyperlink(SourceHyperlink link) {
		ReferenceItem item = link.getItem();
		if (item != null) {
			if (item.getLocation() != null) {
				LocationOperations.goToPosition(item.getLocation());
			}
		}
	}
	
	public void handleSave() {
		if (this.getEditor() != null) {
			this.getEditor().updateContentPane();
		}
	}

	public void handleParentDeleted(List<DesignFile> dfile) {
		if (this.getCompileInfo() != null) { // Null when object already deleted
			this.getCompileInfo().handleParentDeleted(dfile);
			this.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
		}
	}


	
	
	/** Method used when this file is opened from the project */
	public void resolveDesignItems(EclipseBaseProject basic, EclipseSourceFolder folder) {
		if (this.tooLarge()) { // Check if the file is too large
			this.handleTooLarge(); // Create Dialog to change this
			if (this.tooLarge()) return; // Check again if it is too large
		}
		
		super.resolveDesignItems(basic, folder);
		
		
		if (this.getBuilder() == null) {
			this.setBuilder(this.createBuilder());
			this.setCompileInfo(new DesignFileCompileInfo(this));
		}
		
		if (basic != null) {
			this.setProjectRef(basic.createReferenceItem());
			this.getDesignFileBuilder().attachProject(basic);
		}
		
		
		

	}

	/** Method used when the file is opened up from the editor */
	/*public void openFileFromEditor(SourceEditor editor) {
		
		this.setOpened(true);
		this.getBuilder().build(BuildInterface.BUILD_FILE_OPEN);
	}*/

	/** Method used when the source file is closed in the editor */
	public void closeFileFromEditor() {
		this.setEditor(null);
		this.setOpened(false);
		if (this.getBuilder() != null)
			this.getDesignFileBuilder().closeFile();
	}


		
	public void replaceText(ReferenceLocation loc, String newText) {
		IFile file = this.getIFile();

		
		
		final TextFileChange change = new TextFileChange(file.getName(),file);
		change.setSaveMode(TextFileChange.FORCE_SAVE);
		MultiTextEdit multi = new MultiTextEdit();
		change.setEdit(multi);
		ReplaceEdit rep = new ReplaceEdit(loc.getDocPosition(),loc.getLength(),newText);
		multi.addChild(rep);
		try {

			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException{
					if (getGeneralEditor() != null) getGeneralEditor().doSave(monitor);
					change.perform(monitor);
				};
			}, null);

		} catch (CoreException e) {
			HardwareLog.logError(e);
		}

	}


	

	public UniqueList getDependentList() {
		return this.getCompileInfo().getDependentList();
	}


	
	
	public InputStream getFileContents() {
		try {
			return this.getIFile().getContents();
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	
	/** Method which returns the contents of this file. Only valid when file is open
	 * in editor
	 * @return
	 */
	public ReferenceItem<SuperModule> getModuleRef() {
		return this.getDesignFileBuilder().getSuperModule();
	}

	/** File which returns the parse descriptor for this file. Most methods are only 
	 * valid when the file is open in the editor 
	 * @return
	 */
	public ParseDescriptor getParseDescriptor() {
		return this.getDesignFileBuilder().getParseDescriptor();
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setProjectRef(ReferenceItem<CoreProjectBasic> projectRef) {
		this.projectRef = projectRef;
	}

	public ReferenceItem<CoreProjectBasic> getProjectRef() {
		return projectRef;
	}

	public void setEditor(SourceEditor editor) {
		this.setGeneralEditor(editor);
	}

	public SourceEditor getEditor() {
		return (SourceEditor) this.getGeneralEditor();
	}

	/*
	public void setBuilder(DesignFileBuilder builder) {
		this.builder = builder;
	}

	public GeneralFileBuilder getBuilder() {
		return builder;
	}*/
	
	public DesignFileBuilder getDesignFileBuilder() {
		return (DesignFileBuilder) this.getBuilder();
	}

	public void setCompileInfo(DesignFileCompileInfo compileInfo) {
		this.compileInfo = compileInfo;
	}

	public DesignFileCompileInfo getCompileInfo() {
		return compileInfo;
	}



}
