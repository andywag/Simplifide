/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.search;

import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObject;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.LocationUriDecoder;

public abstract class RefactorTreeHolder extends ModuleObject{

	public RefactorTreeHolder() {}
	public RefactorTreeHolder(String name) {super(name);}

	public abstract void addElement(SourceMatch usage);  

	public  void sortElements() {

		for (int i=0;i<this.getnumber();i++) {
			RefactorTreeHolder holder = (RefactorTreeHolder) this.getObject(i);
			holder.sortElements();
		}
	}

	public static class Root extends RefactorTreeHolder {
		public Root() {}

		public void addElements(List<SourceMatch> matches) {
			for (SourceMatch match : matches) {
				this.addElement(match);
			}
		}

		public void addElement(SourceMatch usage) {
			ReferenceLocation loc = usage.getUsage().getLocation();
			EclipseBaseProject newProject = LocationUriDecoder.findProjectfromURI(loc.getUri());

			for (int i=0;i<this.getnumber();i++) {
				Project uproj = (Project) this.getObject(i);
				if (newProject == uproj.project) {
					uproj.addElement(usage);
					return;
				}
			}
			Project uproj = new Project(newProject);
			uproj.addElement(usage);
			this.addObject(uproj);
			/*for (int i=0;i<this.getnumber();i++) {
				Project uproj = (Project) this.getObject(i);
				if (uproj.index == loc.getProjectIndex()) {
					uproj.addElement(usage);
					return;
				}
			}
			EclipseBaseProject proj = ProjectLocationHandler.getInstance().getLocation(loc.getProjectIndex());
			Project uproj = new Project(proj,loc.getProjectIndex());
			uproj.addElement(usage);
			this.addObject(uproj);*/
		}
	}

	public static class Project extends RefactorTreeHolder {
		public EclipseBaseProject project;

		public Project(EclipseBaseProject project) {
			super(project.getname());
			this.project = project;
		}
		public void addElement(SourceMatch usage) {
			ReferenceLocation loc = usage.getUsage().getLocation();
			for (int i=0;i<this.getnumber();i++) {
				File uproj = (File) this.getObject(i);
				if (uproj.source.getUri().equals(loc.getUri())) {
					uproj.addElement(usage);
					return;
				}
			}
			SourceFile sfile = LocationOperations.getSourceFile(loc);
			File ufile = new File(sfile);
			ufile.addElement(usage);
			this.addObject(ufile);
			/*for (int i=0;i<this.getnumber();i++) {
				File uproj = (File) this.getObject(i);
				if (uproj.index == loc.getModIndex()) {
					uproj.addElement(usage);
					return;
				}
			}
			SourceFile sfile = LocationOperations.getSourceFile(loc);
			File ufile = new File(sfile,loc.getModIndex());
			ufile.addElement(usage);
			this.addObject(ufile);*/

		}

	}

	public static class File extends RefactorTreeHolder {
		private SourceFile source;

		public File(SourceFile source) {
			super(source.getname());
			this.setSource(source);
		}



		public void sortElements() {


		}

		private boolean checkExists(SourceMatch match) {
			for (int i=0;i<this.getnumber();i++) {
				Element childMatch = (Element) this.getObject(i);
				if (childMatch.getUsage().comparePosition(match)) return true;
			}
			return false;
		}

		public void addElement(SourceMatch usage) {
			if (!checkExists(usage)) {
				this.addObject(new Element(usage));
			}
		}

		public void setSource(SourceFile source) {
			this.source = source;
		}

		public SourceFile getSource() {
			return source;
		}
	}

	public static class Element extends RefactorTreeHolder {
		private SourceMatch usage;
		public Element(SourceMatch usage) {
			this.setUsage(usage);
			this.setname(usage.getUsage().getText());
		}

		public int compareTo(Object o) {
		   if (o instanceof Element) {
			   Element e = (Element) o;
			   int tline = this.getUsage().getUsage().getLocation().getLine();
			   int eline = e.getUsage().getUsage().getLocation().getLine();
			   return Integer.valueOf(tline).compareTo(Integer.valueOf(eline));
			  
		   }
		   else if (o instanceof TopObject) {
				TopObject op = (TopObject) o;
				return this.getDisplayName().compareTo(op.getDisplayName());
			}
			
			return 0; 
		}
		public String toString() {return this.getDisplayName();}

		public String getDisplayName() {
			
			int line = this.getUsage().getUsage().getLocation().getLine();
			return "Line " + line + " : " + super.getDisplayName();
		}

		public void addElement(SourceMatch usage) {

		}



		public void setUsage(SourceMatch usage) {
			this.usage = usage;
		}

		public SourceMatch getUsage() {
			return usage;
		}
	}




}
