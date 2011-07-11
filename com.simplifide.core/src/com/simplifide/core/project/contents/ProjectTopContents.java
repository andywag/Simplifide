package com.simplifide.core.project.contents;

import java.util.ArrayList;

import com.simplifide.core.project.generator.ProjectTopGenerator;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;

public abstract class ProjectTopContents extends TopContents{

	private boolean fileLink = false;
	private StructureFile allFiles;
	
	private ArrayList<StructureDirectory> sources = new ArrayList<StructureDirectory>();
	
	public ProjectTopContents(String name) {super(name);}

	
	protected abstract StructureDirectory createDefaultStructureDirectory(WorkspaceDirectoryStructure wstr,
			String name);
	protected abstract StructureDirectory getMainFolder(WorkspaceDirectoryStructure wstr);
	
	public void createStructureDirectory(WorkspaceDirectoryStructure wstr) {
		StructureDirectory dir = this.getMainFolder(wstr);
		if (dir == null) { return;
			
		}
		if (this.isFileLink()) {
			StructureFile file = new StructureFile(this.getName());
			file.setLinkType(StructureFile.LINK_COPY_DISTRIBUTION);
			file.setLocation(this.getLinkLocation());
			dir.addChild(file);
		}
		else if (this.getLinkType() == StructureDirectory.LINK_DISTRIBUTION) {
			StructureDirectory ndir = new StructureDirectory(this.getName());
			ndir.setLinkType(StructureDirectory.LINK_DISTRIBUTION);
			ndir.setLocation(this.getLinkLocation());
			dir.addChild(ndir);
		}
		else if (this.getLinkType() == StructureDirectory.LINK_ECLIPSE) {
			StructureDirectory ndir = new StructureDirectory(this.getName());
			ndir.setLinkType(StructureDirectory.LINK_ECLIPSE);
			ndir.setLocation(this.getLinkLocation());
			dir.addChild(ndir);
		}
		else if (this.getLinkType() == StructureDirectory.LINK_COPY_DISTRIBUTION) {
			StructureDirectory ndir = new StructureDirectory(this.getName());
			ndir.setLinkType(StructureDirectory.LINK_COPY_DISTRIBUTION);
			ndir.setLocation(this.getLinkLocation());
			dir.addChild(ndir);
		}
		else if (this.getLinkType() == StructureDirectory.LINK_COPY) {
			StructureDirectory ndir = new StructureDirectory(this.getName());
			ndir.setLinkType(StructureDirectory.LINK_COPY);
			ndir.setLocation(this.getLinkLocation());
			dir.addChild(ndir);
		}
		else {
			StructureDirectory ndir = this.createDefaultStructureDirectory(wstr,this.getName());
			dir.addChild(ndir);
			if (allFiles != null) {
				ProjectTopGenerator.handleAllFiles(allFiles, ndir);
			}
		}
	}

	public void setFileLink(boolean fileLink) {
		this.fileLink = fileLink;
	}

	public boolean isFileLink() {
		return fileLink;
	}


	public void setAllFiles(StructureFile allFiles) {
		this.allFiles = allFiles;
	}


	public StructureFile getAllFiles() {
		return allFiles;
	}

	
	
	
	
	
}
