package com.simplifide.core.project.structure;

import java.util.ArrayList;

import org.eclipse.core.runtime.QualifiedName;

import com.simplifide.core.CoreActivator;

public abstract class ProjectStructureHolderTop extends BaseStructureHolder{

	private ArrayList<StructureDirectory> designList = new ArrayList<StructureDirectory>();


	
	protected static String FOLDER_DESIGN = "design";
	protected static String FOLDER_BUILD = "build";
	protected static String FOLDER_SYN = "synthesis";
	protected static String FOLDER_ROUTE = "route";
	public static String FILE_CONFIG = "project.store";
	protected static String FOLDER_DOC = "doc";
	protected static String FOLDER_SCRIPT = "script";
	protected static String FOLDER_TEST   = "test";
	protected static String FOLDER_SUBPROJECTS = "subprojects";
	
	public static QualifiedName PROJECT_DIR_STRUCTURE_FILE = new QualifiedName(CoreActivator.PLUGIN_ID , "suite_dir_structure");
	
	
	public ProjectStructureHolderTop(StructureDirectory directory) {
		super(directory);
		this.parseStructureDirectory(directory);

	}
	
	
	protected void parseSingleStructureBase(StructureBase dir) {
		String linkName = dir.getLinkName();
		if (linkName == null) return;

		if (linkName.equalsIgnoreCase(FOLDER_DESIGN)) {
			if (this.getLinkMap().get(FOLDER_DESIGN) == null) {
				super.parseSingleStructureBase(dir);
			}
			this.getDesignList().add((StructureDirectory)dir);
		}
		else {
			super.parseSingleStructureBase(dir);
		}
	}
		
	public StructureDirectory getBaseDesignStructure() {return (StructureDirectory) this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_DESIGN);}
	public String getDesignsStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_DESIGN);}

	public StructureDirectory getBuildStructure() { return (StructureDirectory) this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_BUILD);}
	public StructureDirectory getDocStructure() {return (StructureDirectory)this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_DOC);}
	public StructureDirectory getRouteStructure() {return (StructureDirectory)this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_ROUTE);}
	public StructureDirectory getScriptStructure() {return(StructureDirectory) this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_SCRIPT);}
	public StructureDirectory getSynthesisStructure() {return (StructureDirectory)this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_SYN);}
	public StructureDirectory getTestStructure() {return (StructureDirectory)this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_TEST);}
	public StructureDirectory getSubProjectStructure() {return (StructureDirectory)this.getLinkMap().get(ProjectStructureHolderTop.FOLDER_SUBPROJECTS);}

	public String getBuildStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_BUILD);}
	public String getDocStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_DOC);}
	public String getRouteStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_ROUTE);}
	public String getScriptStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_SCRIPT);}
	public String getSynthesisStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_SYN);}
	public String getTestStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_TEST);}
	public String getSubProjectStructureName() {return this.getStructureName(ProjectStructureHolderTop.FOLDER_SUBPROJECTS);}

	public void setDesignList(ArrayList<StructureDirectory> designList) {
		this.designList = designList;
	}

	public ArrayList<StructureDirectory> getDesignList() {
		return designList;
	}

	
	

	
}
