package com.simplifide.core.make;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.ConfigurationDirectoryLoader2;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.util.FileUtility;

public class ProjectMakeGenerator {

	private static HashMap<String,Object> getBasicMap(java.io.File file) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("FILE_NAME", "Makefile");
		map.put("FILE_EXTENSION", "");
		map.put("FILE_PATH", file.getParentFile().getAbsolutePath());
		map.put("FULL_FILE_NAME", file.getAbsolutePath());
		map.put("DATE", new Date());
		map.put("AUTHOR", System.getProperty("user.name"));
		
		return map;
	}
	
	public static void createSuiteMakeFile(EclipseSuite suite) {

		IFolder folder = (IFolder) suite.getBuildFolder().getParent();
		java.io.File file =FileUtility.convertIFolder2File(suite.getBuildFolder().getParent());
		HashMap<String,Object> map = getBasicMap(file);
		map.put("suite", new SuiteWrapper(suite));
		
		String ustr = TemplateGenerator.generateTemplatewithHash("makefile/project/Makefile", map);
		
		IFile make = folder.getFile("Makefile");
		
		ByteArrayInputStream in = new ByteArrayInputStream(ustr.getBytes());
		try {
			if (make.exists()) {
				make.setContents(in, IResource.DEPTH_ONE, null);
			}
			else {
				make.create(in, true, null);
			}
			
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	
	public static void createMakefile(EclipseBaseProject project) {
		
		// Create the Dependent Makefiles
		ArrayList projs = project.getRequiredProjects();
		ArrayList<EclipseBaseProject> projects = (ArrayList<EclipseBaseProject>) projs;
		for (EclipseBaseProject project1 : projects) {
			createMakefile(project1);
		}
		// Create the HashMap for the temlpates
		java.io.File file = FileUtility.convertIFolder2File(project.getBaseFolder());
		HashMap<String,Object> map = getBasicMap(file);
		map.put("project", new ProjectWrapper(project));


		String ustr = TemplateGenerator.generateTemplatewithHash(ConfigurationDirectoryLoader2.TEMPLATE_MAKEFILE, map);
		
		IFolder folder = project.getBaseFolder();
		IFile make = folder.getFile("Makefile");
		
		ByteArrayInputStream in = new ByteArrayInputStream(ustr.getBytes());
		try {
			if (make.exists()) {
				make.setContents(in, IResource.DEPTH_ONE, null);
			}
			else {
				make.create(in, true, null);
			}
			
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
}
	

