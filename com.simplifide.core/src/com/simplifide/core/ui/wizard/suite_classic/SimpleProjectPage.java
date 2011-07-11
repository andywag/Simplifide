/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;



public class SimpleProjectPage extends WizardPage{

	private static int MAX_FOLDER = 7;
	
	private Text projectNameField;
	private Text[] projectLocationText = new Text[MAX_FOLDER];
	
	 public SimpleProjectPage(String type) {
	        super("New " + type);
	        
	        this.setTitle("New " + type);
	        this.setDescription("Select a name for the project, and then add existing design directories to " +
	        		"be linked into the project");
	        setPageComplete(true);
	        // TODO Auto-generated constructor stub
	    }

	private void createProjectNamePanel(Composite parent) {
		Composite composite = new Composite(parent,SWT.NULL);
	    composite.setFont(parent.getFont());
	    GridLayout layout = new GridLayout();
	    layout.numColumns = 2;
	    composite.setLayout(layout);
	    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	
	    Label lab = new Label(composite,SWT.NULL);
	    lab.setText("Project Name");
	    projectNameField  = new Text(composite,SWT.BORDER);
	    projectNameField.setText("work");
	    projectNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	
	}
	
	private void createProjectLocationPanel(Composite parent) {
		Composite composite = new Composite(parent,SWT.NULL);
	    composite.setFont(parent.getFont());
	    GridLayout layout = new GridLayout();
	    layout.numColumns = 3;
	    composite.setLayout(layout);
	    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    
	    for (int i=0;i<MAX_FOLDER;i++) {
	    	Label projectLabel1 = new Label(composite, SWT.NONE);
	        projectLabel1.setText("Design Location");
	        projectLabel1.setFont(parent.getFont());
	        
	        //	      new project name entry field
	        projectLocationText[i] = new Text(composite, SWT.BORDER);
	        GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
	        projectLocationText[i].setLayoutData(data1);
	        projectLocationText[i].setFont(parent.getFont());
	        projectLocationText[i].setText("");
	        
	        Button  projectLocation = new Button(composite, SWT.PUSH | SWT.CENTER);
	        projectLocation.setText("Browse");
	        projectLocation.addListener(SWT.Selection, new ButtonListener(i,composite));
	    }	
	}
	 
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent,SWT.NULL);
	    composite.setFont(parent.getFont());
	    composite.setLayout(new GridLayout());
	    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    this.createProjectNamePanel(composite);
	    this.createProjectLocationPanel(composite);
	    this.setControl(composite);
		
	}
	
	 private class ButtonListener implements Listener{
	        
	        private int index;
	        private Composite composite;   
	        
	        public ButtonListener(int index,Composite composite) {
	            this.index = index;
	            this.composite = composite;
	        }
	        
	        public void handleEvent(Event event) {
	            
	            DirectoryDialog dirdialog = new DirectoryDialog(composite.getShell());
	            
	            String ustr = dirdialog.open();
	            projectLocationText[index].setText(ustr);
	           
	            
	        }
	        
	    }
}
