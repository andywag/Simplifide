package com.simplifide.core.source;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.library.zip.ZipFileSystem;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.util.FileUtility;

public class LocationOperations {

	 public static DesignFile getDesignFile(ReferenceLocation location) {
		 
		 SourceFile sfile = (SourceFile) getSourceFile(location);
		 if (sfile instanceof DesignFile) {
			 return (DesignFile) sfile;
		 }
		 return null;
	 }
	
	 public static SourceFile getSourceFile(ReferenceLocation location) {
		 if (location == null || location.getUri() == null) return null;
		 URI uri = location.getUri();
		 return getSourceFile(uri);
		
	 }
	 
	 public static SourceFile getSourceFile(URI uri) {
		 if (uri.getScheme().equalsIgnoreCase(ZipFileSystem.SCHEME_ZIP)) {
			 return LocationMap.getInstance().getDesignFile(uri);
		 }
		 SourceObject obj = SourceObject.resolveObject(uri);
		 if (obj instanceof SourceFile) return (SourceFile) obj;
		 
		 return null;
	 }
	 
	 public static DesignFile getDesignFile(URI uri) {
		 IFile ifile = FileUtility.convertURItoFile(uri); 
		 if (ifile == null) {
			 SourceFile source = getSourceFile(uri);
			 if (source != null && source instanceof DesignFile) return (DesignFile) source;
		 }
		 else {
			 SourceObject obj = SourceObject.retrieveSourceObject(ifile);
			 if (obj == null) obj = SourceObject.resolveObject(uri);
			 if (obj instanceof DesignFile) return (DesignFile) obj;
		 }
		 
		 // If object doesn't exist here, search the suite 
		 // (Thisoccurs in a delete operation where the resource no longer exists)
		 return getDeletedDesignFile(uri);
	 }
	
	 public static DesignFile getDeletedDesignFile(URI uri) {
		 try {
			 EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
			 if (suite == null || suite.getCompileList() == null) return null;
			 for (ReferenceItem<? extends DesignFile> dfileRef : suite.getCompileList().getGenericSelfList()) {
				 if (dfileRef.getObject() != null) {
					 URI uri2 = dfileRef.getObject().getUri();
					 if (uri2.equals(uri)) return dfileRef.getObject();
				 }
			 }
		 }
		 catch (Exception e) {
			 HardwareLog.logError(e);
		 }
		
		 return null;
	 }
	 
	protected static void goToFileStorePosition(ReferenceLocation location) {
		try {
			IFileStore ifile = EFS.getStore(location.getUri());
    		IWorkbench wb = PlatformUI.getWorkbench();
        	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
        	IWorkbenchPage page = win.getActivePage();
        	
        	IEditorPart edit1 = page.getActiveEditor();
        	if (edit1 instanceof SourceEditor) {
        		((SourceEditor) edit1).resetFindItemHolder();
        	}
        	
        	String fname = ifile.getName();
        	IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fname);
        	
        	IEditorPart part = page.openEditor(new FileStoreEditorInput(ifile), desc.getId());
    		
    		if (part instanceof SourceEditor) {
    			SourceEditor edit = (SourceEditor) part;
    			edit.goToPosition(location.getDocPosition(),location.getLength());
    			edit.resetFindItemHolder();
    			
    		}

		} catch (CoreException e) {
			HardwareLog.logError(e);
		}

	}
	
	public static void goToPosition(final ReferenceLocation location) {
		final IFile ifile = FileUtility.convertURItoFile(location.getUri());
    	if (ifile == null) {
    		goToFileStorePosition(location);
    		return;
    	}
    	
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(ifile.getName());
    	
    		final IWorkbench wb = PlatformUI.getWorkbench();
        	final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
        	final IWorkbenchPage page = win.getActivePage();
        	
        	
        	IEditorPart edit1 = page.getActiveEditor();
        	if (edit1 instanceof SourceEditor) {
        		((SourceEditor) edit1).resetFindItemHolder();
        	}
        	
        	//Job job = new Job("Moving to Location") {
			//     protected IStatus run(IProgressMonitor monitor) {
			 IEditorPart part;
					try {
						
						part = page.openEditor(new FileEditorInput(ifile), desc.getId());
						if (part instanceof GeneralEditor) {
							GeneralEditor edit = (GeneralEditor) part;
			    			int pos = location.getDocPosition();
			    			if (pos > 1) edit.goToPosition(location.getDocPosition(),location.getLength());
			    		}
					} catch (PartInitException e) {
						HardwareLog.logError(e);
					}
			    		
			    		
			         
			        
			     
			  //job.setPriority(Job.SHORT);
			  //job.schedule();
    		
    		
    	
	}
	
}
