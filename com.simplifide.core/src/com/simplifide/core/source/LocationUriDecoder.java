package com.simplifide.core.source;

import java.net.URI;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

/** Class which has functions for decoding the URIs into projects 
 *  and source files
 * @author andy
 *
 */
public class LocationUriDecoder {
	
	private static boolean checkProjectURI(EclipseBaseProject project,URI location) {
		if (project == null || project.getBaseFolder() == null) return false;
		URI uri = project.getBaseFolder().getLocationURI();
		URI rel = uri.relativize(location);
		if (!rel.equals(location)) return true;
		return false;
		

	}
	
	public static EclipseBaseProject findProjectfromURI(URI location) {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		for (EclipseBaseProject project : suite.getProjectList()) {
			if (checkProjectURI(project, location)) return project;
		}
		for (CoreProjectBasic project1 : suite.getLibraryList()) {
			EclipseBaseProject project = (EclipseBaseProject) project1;
			if (checkProjectURI(project, location)) return project;
		}
		if (suite.getProjectList().size() > 0) return suite.getProjectList().get(0);
		return null;
	}
	
}
