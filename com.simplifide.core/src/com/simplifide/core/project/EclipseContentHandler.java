package com.simplifide.core.project;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.suitecontents.SuiteContentLoader;
import com.simplifide.core.project.suitecontents.SuiteContentObject;
import com.simplifide.core.util.FileUtility;

public class EclipseContentHandler {

    private static String contentFileName = "Content.xml";

	
   
    
    /** Handle's Automatically creating a link to the projects folder */
    private static IFolder handleLink(List<IFolder> folders, SuiteContentObject project) {
		if (folders.size() > 0) {
			IPath path = new Path(project.getLocation());
			IFolder res = folders.get(0).getFolder(project.getName());
			if (res != null && res.exists()) return res; // Return existing link
			FileUtility.createLink(path,res);
			IFolder folder = folders.get(0).getFolder(path); 
			return folder;
		}
		return null;
    }
    
    /** This is an amazingly screwed up routine. This should load up each project/library. 
     *  If the project is defined it will load it normally. Otherwise it will create it if
     *  it doesn't exist.  */
    /*private static void handleProjectContents(EclipseSuite suite, SuiteContentObject project) {
    	
    	IFolder folder = null;
    	if (project.getLocation() != null && (!(project.getLocation().equalsIgnoreCase("")))) { // If the project location exists
    		File ufile = new File(project.getLocation());
    		if (ufile.isAbsolute()) {
    			
    			IFileStore store = EFS.getLocalFileSystem().getStore(new Path(project.getLocation()));
    			URI uri = store.toURI();
    			folder = (IFolder) FileUtility.convertURItoResourceFolder(uri);    			
    		}
    	else {
        		folder = suite.getProject().getFolder(new Path(project.getLocation()));
    		}
    		
    	}
    	
    	if (folder == null || !folder.exists()) { // Not Linked Creation Case
    		if (project.getType() == SuiteContentObject.PROJECT) {
    			if (!project.isLink()) {
    				IFolder nfolder = suite.getMainProjectFolder();
    				ProjectGeneratorOptions opt = new ProjectGeneratorOptions(project.getName());
    				opt.setNewproject(true);
    				ProjectGenerator.getDefault().createProjectfromWizard(nfolder, opt);
    			}
    		}
    		else {
    			if (!project.isLink()) {
    				IFolder nfolder = suite.getMainLibraryFolder();
    				ProjectGeneratorOptions opt = new ProjectGeneratorOptions(project.getName());
    				opt.setNewproject(true);
    				LibraryGenerator.getDefault().createProjectfromWizard(nfolder, opt);
    			}
    		}
   
    	}
    	if (folder == null || !folder.exists()) { // Detect Failure
    		HardwareLog.logInfo("Project at Location " + project.getLocation() + " Doesn't Exist");
    		return;
    	}
    	
    	if (project.getType() == SuiteContentObject.PROJECT) {
			if (project.isLink()) {
				IFolder nfolder = handleLink(suite.getProjectFolders(), project);
				if (nfolder != null) folder = nfolder;
			}
			else if (project.getLocation() == null || project.getLocation().equalsIgnoreCase("")) {
				folder = suite.getMainProjectFolder().getFolder(project.getName());
			}
			if (!folder.exists()) {
				HardwareLog.logInfo("Project at Location " + folder.getLocation() + " Does not exist");
				return;
			}
			EclipseProject proj = new EclipseProject(project.getName(),folder,suite.createReferenceItem());
			proj.loadFiles();
			proj.setSourceOnly(project.isSourceOnly());
			suite.createReferenceItem().addReferenceItem(proj.createReferenceItem());

		}
		else if (project.getType() == SuiteContentObject.LIBRARY) {
			if (project.isLink()) {
				IFolder nfolder = handleLink(suite.getLibraryFolders(), project);
				if (nfolder != null) folder = nfolder;
			}
			else if (project.getLocation() == null || project.getLocation().equalsIgnoreCase("")) {
				folder = suite.getMainProjectFolder().getFolder(project.getLocation());
			}
			if (!folder.exists()) {
				HardwareLog.logInfo("Project at Location " + folder.getLocation() + " Does not exist");
				return;
			}
			EclipseLibraryProject library = new EclipseLibraryProject(project.getName(),folder,suite.createReferenceItem());
			library.loadFiles();
			library.setSourceOnly(project.isSourceOnly());
			suite.getLibraryReference().addReferenceItem(library.createReferenceItem());

		}
    }*/
    
    public static void serializeSuite(EclipseSuite suite) {
    	ArrayList<SuiteContentObject> objects = new ArrayList<SuiteContentObject>();
    	for (CoreProjectBasic project : suite.getAllRealProject()) {
    		EclipseBaseProject proj = (EclipseBaseProject) project;
    		SuiteContentObject obj = new SuiteContentObject(proj);
    		objects.add(obj);
    	}
    	String contents = SuiteContentLoader.createContents(objects);
    	ByteArrayInputStream in = new ByteArrayInputStream(contents.getBytes());
    	try {
    		IFile cont = suite.getContentXmlFile();
    		if (cont.exists()) {
    			cont.delete(true, null);
    		}
			suite.getContentXmlFile().create(in,true,null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
    }
    
    
    /** Load up projects from the Content.xml File at the Suite's Root Directory */
    public static void handleEclipseContents(EclipseSuite suite ) {
    	IFile uf = suite.getProject().getFile(contentFileName);
    	ArrayList<SuiteContentObject> projects = SuiteContentLoader.loadContents(uf);
    	
    	for (SuiteContentObject project : projects) {
    		project.loadProject(suite);
    		//handleProjectContents(suite, project);
    	}
    }

	
}
