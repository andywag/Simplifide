package com.simplifide.core.project.structure;

import org.eclipse.core.runtime.QualifiedName;

import com.simplifide.core.CoreActivator;

public class DefaultSuiteRootStructureDirectory extends SuiteStructureHolder{
	
	public static QualifiedName SUITE_DIR_STRUCTURE_FILE = new QualifiedName(CoreActivator.PLUGIN_ID , "suite_dir_structure");
	private static DefaultSuiteRootStructureDirectory instance;

	private DefaultSuiteRootStructureDirectory() {
		//this.createDefaultSuiteStructure();
	}
	
	public static DefaultSuiteRootStructureDirectory getDefault() {
		if (instance == null) instance = new DefaultSuiteRootStructureDirectory();
		return instance;
	}
	
	
	/*
	public void createDefaultSuiteStructure() {
		// Required Directories
		this.getLibraries().add(this.addDefaultChild(SuiteStructureHolder.FOLDER_LIB));
		this.getProjects().add(this.addDefaultChild(SuiteStructureHolder.FOLDER_PROJECTS));
		// Optional Directories
		if (getUseDir(PreferenceConstants.SUITE_DIR_BUILD)) this.addDefaultChild(SuiteStructureHolder.FOLDER_BUILD);
		if (getUseDir(PreferenceConstants.SUITE_DIR_DOC)) this.addDefaultChild(SuiteStructureHolder.FOLDER_DOC);
		if (getUseDir(PreferenceConstants.SUITE_DIR_ROUTE)) this.addDefaultChild(SuiteStructureHolder.FOLDER_ROUTE);
		if (getUseDir(PreferenceConstants.SUITE_DIR_SCRIPT)) this.addDefaultChild(SuiteStructureHolder.FOLDER_SCRIPT);
		if (getUseDir(PreferenceConstants.SUITE_DIR_SYNTHESIS)) this.addDefaultChild(SuiteStructureHolder.FOLDER_SYNTHESIS);
		if (getUseDir(PreferenceConstants.SUITE_DIR_TEST)) {
			StructureDirectory dir = this.addDefaultChild(SuiteStructureHolder.FOLDER_TEST);
			dir.getChildren().add(new StructureDirectory(EclipseTestProject.DESIGN));
			dir.getChildren().add(new StructureDirectory(EclipseTest.DATA_FOLDER));
		}
	}
	*/
}
