/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.pythonext.basic;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.verilog.editor.describer.VerilogFile;
import com.simplifide.core.vhdl.describer.VhdlFile;

public class TopCompile {
	
	private String name;
	
	
	public TopCompile(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	

	public static class Suite extends TopCompile{
		
		private ArrayList<Project> projectList = new ArrayList();
		private ArrayList<Project> libraryList = new ArrayList();
		private ArrayList<Project> testList    = new ArrayList();
		
		public Suite() {
			super("Suite");
		}

		public void clearLists() {
			this.projectList.clear();
			this.libraryList.clear();
			this.testList.clear();
		}
		
		public void addFile(DesignFile dfile) {
			ReferenceItem ref = dfile.getProjectRef();
			if (ref.getname().equalsIgnoreCase("ieee")) return;
			if (ref.getname().equalsIgnoreCase("std")) return;
			
			if (ref.getname().equalsIgnoreCase("test")) {
				if (testList.size() > 0) {
					Project testproj = testList.get(0);
					testproj.addFile(dfile);
					return;
				}
				else {
					Project newProj = new Project(ref.getname(),(EclipseBaseProject)ref.getObject());
					newProj.addFile(dfile);
					this.testList.add(newProj);
				}
				
			}
			else if (ref.getType() == ReferenceUtilities.REF_PROJECT_BASIC) {
				for (Project uproj : projectList) {
					if (uproj.getName().equals(ref.getname())) {
						uproj.addFile(dfile);
						return;
					}
				}
				
				Project newProj = new Project(ref.getname(),(EclipseBaseProject)ref.getObject());
				newProj.addFile(dfile);
				this.projectList.add(newProj);
			}
			else if (ref.getType() == ReferenceUtilities.REF_PROJECT_LIBRARY) {
				for (Project uproj : projectList) {
					if (uproj.getName().equals(ref.getname())) {
						uproj.addFile(dfile);
						return;
					}
				}
				Project newProj = new Project(ref.getname(),(EclipseBaseProject)ref.getObject());
				newProj.addFile(dfile);
				this.libraryList.add(newProj);
			}
			
		}
		
		public void setProjectList(ArrayList<Project> projectList) {
			this.projectList = projectList;
		}

		public ArrayList<Project> getProjectList() {
			return projectList;
		}

		public void setLibraryList(ArrayList<Project> libraryList) {
			this.libraryList = libraryList;
		}

		public ArrayList<Project> getLibraryList() {
			return libraryList;
		}

		public void setTestList(ArrayList<Project> testList) {
			this.testList = testList;
		}

		public ArrayList<Project> getTestList() {
			return testList;
		}
	}
	
	
	public static class Project extends TopCompile {
		
		private ArrayList<DesignFile> verilogList = new ArrayList();
		private ArrayList<DesignFile> vhdlList = new ArrayList();
		
		int first = 0;
		
		private EclipseBaseProject project;
		
		public Project(String name, EclipseBaseProject project) {
			super(name);
			this.project = project;
		}

		public IFolder getBuildLocation() {
			return project.getBuildFolder();
		}
		
		public boolean vhdlFirst() {
			if (first == 1) return true;
			return false;
		}
		
		public void addFile(DesignFile dfile) {
			if (dfile instanceof VhdlFile) {
				vhdlList.add(dfile);
				if (first == 0) first = 1;
			}
			else if (dfile instanceof VerilogFile) {
				verilogList.add(dfile);
				if (first == 0) first = 2;
			}
				
		}
		
		public void setVerilogList(ArrayList<DesignFile> verilogList) {
			this.verilogList = verilogList;
		}

		public ArrayList<DesignFile> getVerilogList() {
			return verilogList;
		}

		public void setVhdlList(ArrayList<DesignFile> vhdlList) {
			this.vhdlList = vhdlList;
		}

		public ArrayList<DesignFile> getVhdlList() {
			return vhdlList;
		}

		
	}
	
	
	
	
}
