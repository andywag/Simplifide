/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SuiteDirectoryConfigurationPage extends WizardPage{

    private FileHolder suiteConfigHolder;
    private FileHolder projectConfigHolder;
    private FileHolder libraryConfigHolder;
    private FileHolder subProjectConfigHolder;

    
    protected SuiteDirectoryConfigurationPage() {
        super("Suite Directory Configuration");
        this.setTitle("Suite Directory Configuration");
        this.setDescription("Suite Directory Configuration");
        setPageComplete(true);
    }

    
    
    
   
    
   
    
    public void createControl(Composite parent) {
    	this.suiteConfigHolder = new FileHolder(parent,"Suite Configuration");
    	this.projectConfigHolder = new FileHolder(parent,"Project Configuration");
    	this.libraryConfigHolder = new FileHolder(parent,"Library Configuration");
    	this.subProjectConfigHolder = new FileHolder(parent,"SubProject Configuration");

    	
    }

   
    
    
    public static class FileHolder extends Composite{

    	private String labelText;
    	private Text text;
    	private Button button;
    	
		public FileHolder(Composite parent,String labelText) {
			super(parent, SWT.None);
			this.labelText = labelText;
		}
    	
		public void createControl(Composite parent) {
			Label lab = new Label(parent,SWT.None);
			lab.setText(this.labelText);
			this.text = new Text(parent,SWT.None);
			
			this.button = new Button(parent,SWT.None);
			
		}
    	
    	
    }
    
    
    
}
