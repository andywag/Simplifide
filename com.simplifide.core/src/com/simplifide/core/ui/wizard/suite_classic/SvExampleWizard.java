/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;


import org.eclipse.core.resources.IProject;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.generator.SvProjectGenerator;
import com.simplifide.core.ui.wizard.example.ExampleTopWizard;




public abstract class SvExampleWizard extends ExampleTopWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.suite.OvmExampleWizard";
	
	
	
	public SvExampleWizard() {
		super();
		init();
	}
	
	protected WizardNewProjectCreationPage createMainPage2() {
		return new SvExampleMainPage.Ovm("Ovm Example Hardware Project"); 
	}
	
	private void init() {
	   
		//this.getMainPage().setTitle("New Ovm Example Project");
		//this.getMainPage().setDescription("New Ovm Example Project");
		
	}
	
	protected abstract String getLocation();
	
	protected void generateProject(IProject handle) {
		SvExampleMainPage main = (SvExampleMainPage) this.getMainPage();
		String exName = main.getSelectedProject();
		String loc = this.getLocation();
		SvProjectGenerator.getDefault().createProject(handle, loc, exName);
	}
	
	public static class Ovm extends SvExampleWizard {
		public Ovm() {
			this.getMainPage().setTitle("New Ovm Example Project");
			this.getMainPage().setDescription("New Ovm Example Project");
		}
		protected String getLocation() {return "ovm";}
		protected WizardNewProjectCreationPage createMainPage2() {
			return new SvExampleMainPage.Ovm("Ovm Example Hardware Project"); 
		}
	}
	
	public static class Uvm extends SvExampleWizard {
		public Uvm() {
			this.getMainPage().setTitle("New Uvm Example Project");
			this.getMainPage().setDescription("New Uvm Example Project");
		}
		protected String getLocation() {return "uvm";}

		protected WizardNewProjectCreationPage createMainPage2() {
			return new SvExampleMainPage.Uvm("Uvm Example Hardware Project"); 
		}
	}
	
	public static class Vmm extends SvExampleWizard {
		public Vmm() {
			this.getMainPage().setTitle("New Vmm Example Project");
			this.getMainPage().setDescription("New Vmm Example Project");
		}
		protected String getLocation() {return "vmm";}

		protected WizardNewProjectCreationPage createMainPage2() {
			return new SvExampleMainPage.Vmm("Vvm Example Hardware Project"); 
		}
	}
	
	

}
