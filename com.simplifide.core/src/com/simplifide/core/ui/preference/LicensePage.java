/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.CoreActivator;

public class LicensePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private Label typeLabel;
	private Label expLabel;
	private Text typeText;
	private Text expText;
	private Text statusText;
	
	public LicensePage() {
		super("SimplifIDE", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		LicenseFieldEditor stfield = new LicenseFieldEditor(PreferenceConstants.LICENSE_STRING,
				"License",
				80,
				this.getFieldEditorParent());
		this.addField(stfield);

      
        this.typeLabel = new Label(this.getFieldEditorParent(),SWT.NONE);
        this.typeLabel.setText("License Type");
        
        this.typeText = new Text(this.getFieldEditorParent(),SWT.NONE);
        this.typeText.setTextLimit(64);
        
        Label statusLabel = new Label(this.getFieldEditorParent(),SWT.None);
        statusLabel.setText("License Status");

        this.statusText = new Text(this.getFieldEditorParent(),SWT.None);
        this.statusText.setTextLimit(64);
        this.statusText.setText("                                              ");
        
        this.expLabel = new Label(this.getFieldEditorParent(),SWT.NONE);
        this.expLabel.setText("License Expiration");
        
        
        this.expText = new Text(this.getFieldEditorParent(),SWT.NONE);
        this.expText.setTextLimit(64);
        this.expText.setText("                                              ");
        this.updateFields();
	}
	
	private void updateFields() {
		String license = ConfigurationDirectoryLoader.getLicenseString();
		LicenseCheck.Info info = LicenseCheck.checkLicense(license);
		if (info == null) return;
		
		if (info.issueType == LicenseCheck.LICENSE_FREE) {
			this.typeText.setText("Free");
		}
		else if (info.issueType == LicenseCheck.LICENSE_TRIAL ||
				 info.issueType == LicenseCheck.LICENSE_TRIAL_EXPIRED) {
			this.typeText.setText("Trial");
		}
		else if (info.issueType == LicenseCheck.LICENSE_VALID ||
				 info.issueType == LicenseCheck.LICENSE_VALID_EXPIRED) {
			this.typeText.setText("Professional");
		}
		else if (info.issueType == LicenseCheck.LICENSE_ACADEMIC_VALID ||
				 info.issueType == LicenseCheck.LICENSE_ACADEMIC_VALID_EXPIRED) {
			this.typeText.setText("Academic");
		}
		
		if (info.issueType == LicenseCheck.LICENSE_INVALID) {
			this.statusText.setText("Invalid License");
		}
		else if (info.issueType == LicenseCheck.LICENSE_TRIAL_EXPIRED ||
				 info.issueType == LicenseCheck.LICENSE_ACADEMIC_VALID_EXPIRED || 
				 info.issueType == LicenseCheck.LICENSE_VALID_EXPIRED) {
			this.statusText.setText("Expired License");
		}
		else if (info.issueType == LicenseCheck.LICENSE_TRIAL ||
				 info.issueType == LicenseCheck.LICENSE_ACADEMIC_VALID ||
				 info.issueType == LicenseCheck.LICENSE_VALID ||
				 info.issueType == LicenseCheck.LICENSE_FREE) {
			this.statusText.setText("Valid License");
		}
		if (info.expiration != null) this.expText.setText(info.expiration.toString());
		
	}
	
	public boolean performOk() {
		boolean status = super.performOk();
		this.updateFields();
		/*String license = ConfigurationDirectoryLoader.getLicenseString();
		LicenseCheck.Info info = LicenseCheck.checkLicense(license); */
		return status;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
