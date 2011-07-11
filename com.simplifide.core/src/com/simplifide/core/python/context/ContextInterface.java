package com.simplifide.core.python.context;

import java.util.List;

public interface ContextInterface {
  
	public String getname();
	
	public interface Suite extends ContextInterface {
		public List getAllProjects();
		public String getBaseDirectory();
		public String getBuildDirectory();
		public String getMainModule();
	}
	   
	public interface Project extends ContextInterface {
		public String getBaseDirectory();
		public String getBuildDirectory();
		public List<Source> getVhdlList();
		public List<Source> getVerilogList();
		public List<Project> getDependentProjects();
		public String getMainModule();
	}
	
	public interface Source extends ContextInterface {
		public String getPath();
		public boolean isVHDLFile();
	}
	 
	public interface Editor extends ContextInterface {
		public Source getSource();
		
	}
	
}
