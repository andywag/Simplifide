package com.simplifide.core.project.structure;

import com.simplifide.core.ui.preference.PreferenceConstants;

public class LibraryStructureHolder extends ProjectStructureHolderTop {

	public LibraryStructureHolder(StructureDirectory directory) {
		super(directory);
	}

	
	public static RootStructureDirectory createDefaultSuiteStructure() {
		RootStructureDirectory root = new RootStructureDirectory();
		// Required Directories
		root.addDefaultChild(ProjectStructureHolderTop.FOLDER_DESIGN,RootStructureDirectory.LINK_DESIGN);
		// Optional Directories
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_BUILD)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_BUILD,RootStructureDirectory.LINK_DESIGN);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_DOC)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_DOC,RootStructureDirectory.LINK_DOCS);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_ROUTE)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_ROUTE,RootStructureDirectory.LINK_ROUTE);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_SCRIPT)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SCRIPT,RootStructureDirectory.LINK_SCRIPT);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_SYNTHESIS)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SYN,RootStructureDirectory.LINK_SYNTHESIS);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_TEST)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_TEST,RootStructureDirectory.LINK_TEST);
		if (root.getUseDir(PreferenceConstants.LIBRARY_DIR_SUB)) root.addDefaultChild(ProjectStructureHolderTop.FOLDER_SUBPROJECTS,RootStructureDirectory.LINK_SUBPROJECT);

		return root;
	}

	/*
	@Override
	protected String getXmlStoreDir() {
		String suiteDir = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LIBRARY_DIR_CONFIG);
		return suiteDir;
	}
	@Override
	protected String getDirectoryPreference() {
		return PreferenceConstants.LIBRARY_DIR_CONFIG;
	}
	
	public ProjectStructureHolderTop getDefaultProjectStructure() {
		return LibraryStructureHolder.Default.getDefault();
	}

	public ProjectStructureHolderTop getNewProjectStructure() {
		return new LibraryStructureHolder();
	}
	
	public static class Default extends LibraryStructureHolder {
		private static Default instance;
		
		private Default() {
			this.createDefaultSuiteStructure();
		}
		
		public static Default getDefault() {
			if (instance == null) instance = new Default();
			return instance;
		}
	}
	*/
}
