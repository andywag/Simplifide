package com.simplifide.core.ui.filebrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;
import com.simplifide.core.ui.wizard.project2.ProjectPage2;
import com.simplifide.core.ui.wizard.suite_import.SuiteStructureBottomComposite;


public class StructureFileTreeFullComposite extends Composite{
	private StructureFileTreeComposite structureFileTreeComposite1;
	private SuiteStructureBottomComposite suiteStructureBottomComposite1;
	private int projectType;
	
	public StructureFileTreeFullComposite(Composite parent, int style, int projectType) {
		super(parent, style);
		this.setSize(600, 360);
		this.projectType = projectType;
		{
			structureFileTreeComposite1 = new StructureFileTreeComposite(this, SWT.NONE);
			structureFileTreeComposite1.setBounds(-5, 0, 605, 246);
		}
		{
			suiteStructureBottomComposite1 = new SuiteStructureBottomComposite(this, SWT.NONE);
			suiteStructureBottomComposite1.setBounds(0, 252, 594, 80);
		}
	}
	
	public void setDefaultStructure(StructureDirectory struct) {
		this.structureFileTreeComposite1.setInputStructure(struct);
	}
	
	public StructureFile getStructureFile() {
		StructureFile ufile = suiteStructureBottomComposite1.getStructureFile();
		if (ufile == null) {
			StructureDirectory dir = structureFileTreeComposite1.getStructureBase();
			String out = "";
			switch(projectType) {
			case(ProjectPage2.TYPE_PROJECT) : 
				out = WorkspaceDirectoryStructure.getFileContents(null, dir, null, null); break;
			case(ProjectPage2.TYPE_LIBRARY) : 
				out = WorkspaceDirectoryStructure.getFileContents(null, null, dir, null); break;
			case(ProjectPage2.TYPE_SUBPROJECT) : 
				out = WorkspaceDirectoryStructure.getFileContents(null, null, null, dir); break;

			}
			StructureFile struct = new StructureFile("Structure.xml");
			struct.setLocation("Structure.xml");
			struct.setContents(out);
			struct.setLinkType(StructureFile.LINK_NEW);
			return struct;
		}
		//if (ufile == null) ufile = structureFileTreeComposite1.getStructureBase();
		return ufile;
	}

}
