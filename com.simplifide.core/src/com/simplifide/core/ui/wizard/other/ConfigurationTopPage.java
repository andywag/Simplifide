/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.other;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;



public class ConfigurationTopPage extends WizardPage{

	
	 public ConfigurationTopPage(String type) {
	        super("Configuration Directory ");
	        
	        this.setTitle("Configuration Directory");
	        
	        setPageComplete(true);
	    }

	
	public void createControl(Composite parent) {
		
	}
	
	
	
	 
}
