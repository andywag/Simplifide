/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.other;


import org.eclipse.core.resources.IFolder;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.ui.wizard.BaseNewWizard;




public abstract class ConfigurationDirectoryWizard extends BaseNewWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.other.ConfigurationDirectoryWizard";
	
	private ConfigurationTopPage configPage;
	private IFolder location;
	
	public ConfigurationDirectoryWizard() {super();
		init();
	}
	
	
	public abstract String getProjectName();
	public abstract IFolder getBaseProjectLocation(EclipseSuite suite);
	
	private void init() {
	   
		
	   
                       
	}
	
	protected void createMainPage() {
		this.configPage = new ConfigurationTopPage(this.getProjectName());
		this.addPage(this.configPage);
	}
	
	
	public boolean needsProgressMonitor() {
        return true;
    }
	 
	
	
	public boolean performFinish() {
		return true;
	}


	protected void setLocation(IFolder location) {
		this.location = location;
	}


	protected IFolder getLocation() {
		return location;
	}
	
	
	
	
    
}
