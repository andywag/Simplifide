package com.simplifide.core.project.prj;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.simplifide.core.HardwareLog;

public class ProjectFileLoader {

	
	private List<Project> projects = new ArrayList<Project>();
	
	
	
	
	private void addFile(String library, String file, java.io.File file1) {
		Project uproj = null;
		for (Project project : getProjects()) {
			if (project.name.equalsIgnoreCase(library)) {
				uproj = project;
			}
		}
		if (uproj == null) {
			uproj = new Project(library);
			getProjects().add(uproj);
		}
		file = file.replace("\"", "");
		uproj.files.add(new File(file));
	}
	
	private void parseLine(String line, java.io.File file) {
		String[] cont = line.split(" ");
		if (cont.length < 3) return;
		
		if (cont[0].toLowerCase().equalsIgnoreCase("vhdl") ||
			cont[0].toLowerCase().equalsIgnoreCase("verilog")) {
			this.addFile(cont[1], cont[2],file);
		}
		
		
	}
	
	public void loadProjectFile(java.io.File file) {
		try {
			loadProjectFileInternal(file);
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		}
	}
	
	public void loadProjectFileInternal(java.io.File file) throws FileNotFoundException  {
			Scanner scanner = new Scanner(file);
		    try {	
		      while (scanner.hasNextLine()){
		    	  String line = scanner.nextLine();
		    	  this.parseLine(line,file);
		      }
		    }
		    
		    finally{
		      scanner.close();
		    }
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public static class Project {
		public String name;
		public List<File> files = new ArrayList<File>();
		
		public Project(String name) {
			this.name = name;
		}
		
	}
	
	public static class File {
		public String name;
		
		public File(String name) {
			this.name = name;
		}
		
	}
	
}
