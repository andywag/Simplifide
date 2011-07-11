package com.simplifide.core.project.generator;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSubProject;
import com.simplifide.core.project.library.EclipseLibraryProject;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.ui.wizard.project2.ProjectPage2;

public class ProjectGeneratorOptions {
	
	private String name;
	
	private StructureDirectory structureLocation;
	private StructureFile structureXmlFile;
	private StructureFile allFiles;
	
	private boolean newproject;
	private boolean link;
	private boolean copy;
	
	private boolean sourceOnly;
	
	public ProjectGeneratorOptions(String name) {
		this.setName(name);	
	}
	
	
	public void setStructureXmlFile(StructureFile structureXmlFile) {
		this.structureXmlFile = structureXmlFile;
	}
	public StructureFile getStructureXmlFile() {
		return structureXmlFile;
	}
	
	public boolean isLibrary() {return false;}
	
	public static class Existing extends ProjectGeneratorOptions {

		private EclipseBaseProject project;
		public Existing(EclipseBaseProject project) {
			super(project.getname());
			this.setProject(project);
			
		}
		
		public boolean isLibrary() {
			if (project instanceof EclipseLibraryProject) return true;
			return false;
		}
		
		public String getName() {
			return getProject().getname();
		}
		
		public String getLocation() {
			return getProject().getBaseFolder().toString();
		}
		
		public boolean srcOnly() {
			return getProject().isSourceOnly();
		}
		
		public boolean fileList() {
			return getProject().isFileList();
		}
		public void createSourceXml(StructureFile struct) {
			String con = ((StructureFile)struct).getContents();
			ByteArrayInputStream bs = new ByteArrayInputStream(con.getBytes());
			 try {
				project.getSourceXmlFile().delete(true, null);
				project.getSourceXmlFile().create(bs, true, null);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
		}
		
		public List<java.io.File> initialFiles() {
			return getProject().initialFiles();
		}
		
		public StructureDirectory getDefaultStructure() {
			return getProject().getProjectStructure().getStructureDirectory();
		}
		
		public String getType() {
			if (getProject() instanceof EclipseLibraryProject) return "Library";
			else return "Project";
		}
		
		public int getProjectType() {
			if (getProject() instanceof EclipseLibraryProject) return ProjectPage2.TYPE_LIBRARY;
			else if (getProject() instanceof EclipseSubProject) return ProjectPage2.TYPE_SUBPROJECT;
			return ProjectPage2.TYPE_PROJECT;
		}
		
		public boolean isLink() {
			if (getProject() == null || getProject().getBaseFolder() == null) return false;
			if (getProject().getBaseFolder().isLinked()) return true;
			return false;
		}

		public void setProject(EclipseBaseProject project) {
			this.project = project;
		}

		public EclipseBaseProject getProject() {
			return project;
		}
		
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAllFiles(StructureFile allFiles) {
		this.allFiles = allFiles;
	}

	public StructureFile getAllFiles() {
		return allFiles;
	}

	public void setStructureLocation(StructureDirectory structureLocation) {
		this.structureLocation = structureLocation;
	}

	public StructureDirectory getStructureLocation() {
		return structureLocation;
	}

	public void setSourceOnly(boolean sourceOnly) {
		this.sourceOnly = sourceOnly;
	}

	public boolean isSourceOnly() {
		return sourceOnly;
	}

	public void setNewproject(boolean newproject) {
		this.newproject = newproject;
	}

	public boolean isNewproject() {
		return newproject;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public boolean isLink() {
		return link;
	}

	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	public boolean isCopy() {
		return copy;
	}


}
