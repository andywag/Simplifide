package com.simplifide.core.project.structure;

import com.simplifide.core.ui.preference.PreferenceConstants;

public class ProjectStructureHolder extends ProjectStructureHolderTop {
	
	public ProjectStructureHolder(StructureDirectory directory) {
		super(directory);
	}
	
	
	protected static RootStructureDirectory createDefaultSuiteStructure() {
		// Required Directories
		RootStructureDirectory root = new RootStructureDirectory();
		root.addDefaultChild(ProjectStructureHolderTop.FOLDER_DESIGN,RootStructureDirectory.LINK_DESIGN);
		//root.getDesignList().add(new StructureDirectory(ProjectStructureHolderTop.FOLDER_DESIGN));
		// Optional Directories
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_BUILD)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_BUILD,RootStructureDirectory.LINK_BUILD);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_DOC)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_DOC,RootStructureDirectory.LINK_DOCS);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_ROUTE)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_ROUTE,RootStructureDirectory.LINK_ROUTE);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_SCRIPT)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SCRIPT,RootStructureDirectory.LINK_SCRIPT);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_SYNTHESIS)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SYN,RootStructureDirectory.LINK_SYNTHESIS);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_TEST)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_TEST,RootStructureDirectory.LINK_TEST);
		if (root.getUseDir(PreferenceConstants.PROJECT_DIR_SUB)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SUBPROJECTS,RootStructureDirectory.LINK_SUBPROJECT);

		return root;
	}
	
}
