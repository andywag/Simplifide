/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.generator.VerilogExampleGenerator;
import com.simplifide.core.ui.wizard.example.ExampleTopWizard;

public class VerilogExampleWizard extends ExampleTopWizard{

	
	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.suite.VerilogExampleWizard";
	
	
	public VerilogExampleWizard() {super();
		init();
	}
	
	private void init() {
	  
		this.getMainPage().setTitle("New Example Hardware Project");
		this.getMainPage().setDescription("New Example Hardware Project");
		
		
	}

	@Override
	protected void generateProject(IProject handle) {
		VerilogExampleGenerator.getDefault().createProject(handle, true);
	
	        
	}
	
	
}
