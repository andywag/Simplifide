package com.simplifide.core.license.ui;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.base.license.info.UserInformation;

public class NoLicenseComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private static String LABEL_NO_LICENSE = "No license was found for Simplifide. Simplifide has a free version and a professional version which both\n" +
	"require a valid license for the majority of the features. Please use the form below to request a license or\n" +
	"continue using with limited features.";
	
	private static String LABEL_INVALID_LICENSE = "The license file format or contents are invalid. You can request a new license below or send an email\n" + 
	" to support@simplifide.com if you feel there is an issue. A new license is required when upgrading\nto version 1.2.0.";
	
	private static String LABEL_FREE_LICENSE = "You are currently using a free license which has a limited set of features. You can request a trial license\n" +
	"or a professional license below.";
	
	private static String LABEL_LICENSE_NO_MATCH = "The license file you are using is not valid for this computer or user. \n" + 
	"If you feel there is an error please contact support@simplifide.com or request another license.";
	
	
	LicenseRequestRadioComposite requestComposite;
	LicenseTypeComposite typeComposite;
	LicenseCheck.Info info;
	
	
	public NoLicenseComposite(Composite parent, int style, LicenseCheck.Info info, LicenseTopComposite comp) {
		super(parent, style);
		this.info = info;
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(10, 10, 560, 56);
		toolkit.adapt(label, true, true);
		String lab = LABEL_NO_LICENSE; //NO_LICENSE;
		label.setText(lab);
		
		
		//Composite composite = new Composite(this, SWT.NONE);
		requestComposite = new LicenseRequestRadioComposite(this, SWT.NONE, comp);
		requestComposite.setBounds(20, 62, 320, 128);
		toolkit.adapt(requestComposite);
		toolkit.paintBordersFor(requestComposite);
		
		//Composite composite_1 = new Composite(this, SWT.NONE);
		typeComposite = new LicenseTypeComposite(this, SWT.NONE);
		typeComposite.setBounds(340, 62, 200, 91);
		toolkit.adapt(typeComposite);
		toolkit.paintBordersFor(typeComposite);
		
		this.contextSetup(label, info);
	}
	
	private String getTrialLicenseText(LicenseCheck.Info info) {
		SimpleDateFormat format = new SimpleDateFormat("MMMMMMMMMMMM dd, yyyy");
	
		String lab = "You are currently using a trial license which will expire on " + format.format(info.expiration) + ".\n";
		return lab;
	}
	
	private String getExpiredTrialLicenseText(LicenseCheck.Info info) {
		SimpleDateFormat format = new SimpleDateFormat("MMMMMMMMMMMM dd, yyyy");
		String lab = "You are currently using a trial license which expired on " + format.format(info.expiration) + ".\n";
		lab += "You can either purchase the professional version or continue using the free version with limited features.";
		return lab;
	}
	
	
	private void contextSetup(Label lab, LicenseCheck.Info info) {
		if (info == null) return;
		String labelString = "";
		switch(info.issueType) {
		    case LicenseCheck.LICENSE_INVALID       : labelString = LABEL_INVALID_LICENSE; break;
			case LicenseCheck.LICENSE_NONE          : labelString = LABEL_NO_LICENSE; break;
			case LicenseCheck.LICENSE_FREE          : 
				labelString = LABEL_FREE_LICENSE; 
				requestComposite.setContinueSelected();						
				break;
			case LicenseCheck.LICENSE_TRIAL         : 
				labelString = this.getTrialLicenseText(info); 
				requestComposite.setContinueSelected();	
				break;
			case LicenseCheck.LICENSE_TRIAL_EXPIRED : labelString = this.getExpiredTrialLicenseText(info); break;	
			case LicenseCheck.LICENSE_NOMATCH : labelString = LABEL_LICENSE_NO_MATCH; break;	
		}
		lab.setText(labelString);
	}
	
	public int getRequestType() {
		return requestComposite.getRequestType();
	}
	
	public String getLicenseType() {
		return typeComposite.getLicenseType();
	}

	public void setEmailButton() {
		this.requestComposite.setEmailButton();
		
	}
	
	public TypeButtonListener createListener() {
		return new TypeButtonListener();
	}
	
	public  class TypeButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			if (typeComposite.getLicenseType().equalsIgnoreCase(UserInformation.PRO) ||
				typeComposite.getLicenseType().equalsIgnoreCase(UserInformation.ACADEMIC)) {
				requestComposite.userForm.setEnabled(false);
				requestComposite.emailForm.setSelection(true);
			}
			else {
				requestComposite.userForm.setEnabled(true);
			}
		}
	}
	
	
}
