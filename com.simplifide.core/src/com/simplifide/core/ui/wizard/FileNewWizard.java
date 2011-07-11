/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.core.ConfigurationDirectoryLoader2;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.navigator.element.BasicProjectElement;
import com.simplifide.core.ui.wizard.other.NewFilePage;
import com.simplifide.core.util.FileUtility;



public abstract class FileNewWizard extends BaseNewWizard {

	private NewFilePage newPage;
	private String pagename;

	public FileNewWizard(String pagename) {
		this.setPagename(pagename);
	}

	protected NewFilePage createNewFilePage(String name, IStructuredSelection sel) {
		return new NewFilePage(this,name,sel);
	}
	protected void createMainPage() {
		IStructuredSelection sel = this.getSelection();
		if (sel != null && sel.getFirstElement() instanceof BasicProjectElement) {
			final BasicProjectElement projectElement = (BasicProjectElement) sel.getFirstElement();
			IFolder ufolder = projectElement.getDesignFolder();
			sel = new StructuredSelection(ufolder);
		}
		newPage = this.createNewFilePage(getPagename(),sel);
		this.addPage(newPage);
		
	}


	/** Returns the location of the directory where the template resides */
	public abstract String getTemplateDirectory();
	/** Returns the  name of the template */
	public abstract String getTemplateName();
	/** Return the default extension of the file */
	public abstract String getDefaultExtension();
	public abstract String getHeaderTemplate();
	public abstract String getFileTemplate();
	public abstract String getDefaultHeaderFile();
	

	private String[] getExtension(String fname) {
		String[] cont = fname.split("\\.");
		if (cont.length == 2) return cont;
		else return new String[] {fname,null};
	}

	public String getTemplatePath() {
		String loc = "templates/" +  this.getTemplateDirectory() + this.getTemplateName();
		return loc;
	}
	
	public void editTemplate() {
		String loc = this.getTemplatePath();
		File open = ConfigurationDirectoryLoader2.getOrCreateFromPath(loc + ".ftl");
		try {
			
			IFileStore store = EFS.getStore(open.toURI());
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(store.getName());
			String id = null;
			if (desc != null) id = desc.getId();
			else id = org.eclipse.ui.editors.text.EditorsUI.DEFAULT_TEXT_EDITOR_ID;
			
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileStoreEditorInput(store), id);
			this.getShell().setVisible(false);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		} 
	}
	
	private void openFileInEditor(IFile file) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorDescriptor desc = PlatformUI.getWorkbench().
		getEditorRegistry().getDefaultEditor(file.getName());
		try {
			page.openEditor(
					new FileEditorInput(file),
					desc.getId());
		} catch (PartInitException e) {
			BaseLog.logError(e);
		}
	}

	protected HashMap<String,Object> createMap(String fn, String fe, IFile ifile) {
		java.io.File file = FileUtility.convertIFile2File(ifile);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("FILE_NAME", fn);
		map.put("FILE_EXTENSION", fe);
		map.put("FILE_PATH", file.getParentFile().getAbsolutePath());
		map.put("FULL_FILE_NAME", file.getAbsolutePath());
		map.put("DATE", new Date());
		map.put("AUTHOR", System.getProperty("user.name"));
		map.put("object", new ModuleObject(fn));

		return map;
	}
	
	
	private String createFileContents(HashMap<String,Object> map) {
		
		File afile = ConfigurationDirectoryLoader2.getFileFromPath(getTemplatePath());
		String header = this.getHeaderTemplate();
		String fcontent = this.getFileTemplate();
		
		// First Check to See if the Contents
		if (fcontent != null && !fcontent.equalsIgnoreCase("")) { 
			return TemplateGenerator.generateTemplateFromString(fcontent, map);
		}
		else if (header != null && !fcontent.equalsIgnoreCase("")) { 
			String head = TemplateGenerator.generateTemplateFromString(header, map);
			map.put("HEADER", head);
			return TemplateGenerator.generateTemplatewithHash(this.getTemplateDirectory() + this.getTemplateName(), map);
		}
		else {
			if (this.getDefaultHeaderFile() != null) {
				String head = TemplateGenerator.generateTemplatewithHash(this.getDefaultHeaderFile(), map);
				map.put("HEADER", head);
			}
		}
		if (!map.containsKey("signals")) map.put("signals", "");
		if (!map.containsKey("instance")) map.put("instance", "");
		return TemplateGenerator.generateTemplatewithHash(this.getTemplateDirectory() + this.getTemplateName(), map);
	}
		
	
	
	private void writeFile(IFile file, String ustr) {
		try {
			ByteArrayInputStream str = new ByteArrayInputStream(ustr.getBytes("UTF-8"));
			file.setContents(str, IFile.FORCE, null);
			
		} catch (UnsupportedEncodingException e) {
			HardwareLog.logError(e);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	protected void addToMap(HashMap<String,Object> map){}
	
	@Override
	public boolean performFinish() {

		String fn1 = newPage.getFileName();
		String[] n = this.getExtension(fn1);
		String fileName = n[0];
		String fileExtension = n[1];

		if (fileExtension == null) {
			newPage.setErrorMessage("File Extension Required");
			return false;
		}
		IFile file = newPage.createNewFile();
		HashMap<String,Object> map = this.createMap(fileName, fileExtension, file);
		this.addToMap(map);
		String contents = this.createFileContents(map);
		this.writeFile(file, contents);
		
		this.openFileInEditor(file);
		
		// TODO Auto-generated method stub
		return true;
	}

	public void setNewPage(NewFilePage newPage) {
		this.newPage = newPage;
	}

	public NewFilePage getNewPage() {
		return newPage;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getPagename() {
		return pagename;
	}

	public static class FileTemp extends ModuleObject{
		private String date;
		private String user;
		private String[] comment;

		public FileTemp(String name, String date, String user, String[] comment) {
			super(name);
			this.date = date;
			this.user = user;
			this.setComment(comment);
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getUser() {
			return user;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getDate() {
			return date;
		}

		public void setComment(String[] comment) {
			this.comment = comment;
		}

		public String[] getComment() {
			return comment;
		}

	}


}
