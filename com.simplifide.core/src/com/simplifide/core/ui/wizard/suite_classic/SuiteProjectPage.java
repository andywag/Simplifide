/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SuiteProjectPage extends WizardPage{

    private static int MAX_PROJECT = 7;
    private Text[] projectNameField    = new Text[MAX_PROJECT];
    private Text[] projectLocationText = new Text[MAX_PROJECT];
    private Composite[] projectComp = new Composite[MAX_PROJECT];
    
    private String type;
    private int num_projects = 0;
    
    protected SuiteProjectPage(String type) {
        super("Existing " + type);
        this.type = type;
        this.setTitle(type);
        this.setDescription("New Projects to add to the suite. Use the location entry to link to existing design folders");
        setPageComplete(true);
        // TODO Auto-generated constructor stub
    }

    
    
    private Composite createProjectName(Composite parent, int index) {
        Composite projectGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 5;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        
        Label projectLabel = new Label(projectGroup, SWT.NONE);
        projectLabel.setText(type + " Name");
        projectLabel.setFont(parent.getFont());

        // new project name entry field
        projectNameField[index] = new Text(projectGroup, SWT.BORDER);
        //GridData data = new GridData(GridData.FILL_HORIZONTAL);
        //projectNameField[index].setLayoutData(data);
        projectNameField[index].setFont(parent.getFont());
        projectNameField[index].setTextLimit(32);
        GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
        projectNameField[index].setLayoutData(nameData);
        projectNameField[index].addFocusListener(new TextListener(index, parent));
        
        Label projectLabel1 = new Label(projectGroup, SWT.NONE);
        projectLabel1.setText("Design Location");
        projectLabel1.setFont(parent.getFont());
        
//      new project name entry field
        projectLocationText[index] = new Text(projectGroup, SWT.BORDER);
        GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
        projectLocationText[index].setLayoutData(data1);
        projectLocationText[index].setFont(parent.getFont());
        projectLocationText[index].setText("");
        
        Button  projectLocation = new Button(projectGroup, SWT.PUSH | SWT.CENTER);
        projectLocation.setText("Browse");
        projectLocation.addListener(SWT.Selection, new ButtonListener(index,projectGroup));
        
        return projectGroup;
    }
   
    
   
    
    public void createControl(Composite parent) {
       Composite composite = new Composite(parent,SWT.NULL);
       composite.setFont(parent.getFont());
       composite.setLayout(new GridLayout());
       composite.setLayoutData(new GridData(GridData.FILL_BOTH));
       projectComp[0]  = this.createProjectName(composite, 0);
       for (int i=1;i<MAX_PROJECT;i++) {
           projectComp[i] = this.createProjectName(composite, i);
           projectComp[i].setVisible(false);
       }
       this.setControl(composite);
      
    }

   
    
    
    private class TextListener implements FocusListener {

        private int index;
        private Composite parent;
        public TextListener(int index, Composite parent) {
            this.index = index;
            this.parent = parent;
        }
        
        public void focusGained(FocusEvent e) {
            // TODO Auto-generated method stub
            
        }

        public void focusLost(FocusEvent e) {
            if (!projectNameField[index].getText().equalsIgnoreCase("") && index == num_projects && index < MAX_PROJECT-1) {
                num_projects++;
                
                projectComp[num_projects].setVisible(true);
                
            }
            
            
        }
        
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
