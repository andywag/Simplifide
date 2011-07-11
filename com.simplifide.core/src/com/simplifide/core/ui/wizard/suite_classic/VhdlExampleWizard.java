/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;


import org.eclipse.core.resources.IProject;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.generator.VhdlExampleGenerator;
import com.simplifide.core.ui.wizard.example.ExampleTopWizard;




public class VhdlExampleWizard extends ExampleTopWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.suite.VhdlExampleWizard";
	
	
	
	public VhdlExampleWizard() {super();
		init();
	}
	
	private void init() {
	   
		this.getMainPage().setTitle("New Vhdl Example Project");
		this.getMainPage().setDescription("New Vhdl Example Project");
		
	}
	
	@Override
	protected void generateProject(IProject handle) {
		VhdlExampleGenerator.getDefault().createProject(handle,true);  
		
	}
	
	
	

}
