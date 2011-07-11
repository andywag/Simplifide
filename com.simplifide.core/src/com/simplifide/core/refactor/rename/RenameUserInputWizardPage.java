/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.rename;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RenameUserInputWizardPage extends UserInputWizardPage{

    private RenameRefactoring refactoring;
    private Text textField;
    private Button globalButton;
    
    public RenameUserInputWizardPage(String name, RenameRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    public void createControl(Composite parent) {
        Composite result = new Composite(parent,SWT.None);
        

        this.setControl(result);
        
        
        /*
        Label label = new Label(result,SWT.LEFT);
        String labelText  = "This feature has been extensively tested, but it is a somewhat complicated operation which can't be undone. Please take appropriate caution for large changes.\n";
        
        label.setText(labelText);
        
        
        String labelText  = "This feature has been extensively tested, but is a somewhat complicated operation which can't be undone.\n";
        labelText += "Please take appropriate caution for large changes.\n\n";
        labelText += "Functions are not Supported\n\n";
        */
        this.setTitle("Rename " + refactoring.getProcessor().getInitialName());
        
        String labelText = "New Name";
        Label nameLabel = new Label(result,SWT.LEFT);
        nameLabel.setBounds(10, 10, 70, 20);
        
        nameLabel.setText(labelText);
        
        
        textField = new Text(result,SWT.SINGLE | SWT.BORDER | SWT.LEFT);
        this.setControl(result);
        textField.setBounds(80, 10, 300, 20);
        textField.setText(this.refactoring.getProcessor().getNewName());
        
        this.globalButton = new Button(result,SWT.CHECK);
        this.globalButton.setBounds(390,10, 100, 20);
        this.globalButton.setText("Global");
        this.globalButton.setEnabled(true);
        this.globalButton.setSelection(true);
        
        
    }
    
    
    public IWizardPage getNextPage() {
        refactoring.getProcessor().setNewName(textField.getText());
        return super.getNextPage();
    }

    protected boolean performFinish() {
        refactoring.getProcessor().setNewName(textField.getText());
        return super.performFinish();
    }
    
}
