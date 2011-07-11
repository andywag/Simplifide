package com.simplifide.core.baseeditor.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.util.FileUtility;
import com.simplifide.core.verilog.editor.describer.VerilogFile;

public class ExpandMacroActionDelegate extends GeneralEditorActionDelegate{

	public ExpandMacroActionDelegate() {}
	public ExpandMacroActionDelegate(GeneralEditor editor) {
		super(editor);
		this.setEnabled(true);
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		if (action != null) action.setEnabled(true);
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}
	@Override
	public void run(GeneralEditor editor) {
		SourceEditor edit = (SourceEditor) editor;
		VerilogFile dfile = (VerilogFile) edit.getDesignFile();
		
		try {
			String file = dfile.getUri().toURL().getFile();
			File ufile = new File(file);
			String[] n = ufile.getName().split("\\.");
			final File pfile = ufile.getParentFile();
			final File nfile = new File(pfile,n[0] + "_expanded." + n[1]);
			try {
				nfile.createNewFile();
				Writer output = new BufferedWriter(new FileWriter(nfile));
				String content = dfile.expandMacros();
			    output.write(content);
			    output.close();
			   
			    
			    
			    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
					public void run() {
						try {
							 IFile pifile = FileUtility.getResourceforPath(pfile.getPath());
							    pifile.refreshLocal(IResource.DEPTH_INFINITE, null);
							    final IFile curfile = FileUtility.getResourceforPath(nfile.getPath());
							 IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							 IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(curfile.getName());
							 page.openEditor(new FileEditorInput(curfile), desc.getId());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});

			   
			    
			} catch (IOException e) {
				HardwareLog.logError(e);
			} 
			
			
		} catch (MalformedURLException e) {
			HardwareLog.logError(e);
		}
		//HardwareLog.logInfo("Here");
		
	}
	
	public static class Handler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new ExpandMacroActionDelegate(editor);
		}
		
	}
	
}
