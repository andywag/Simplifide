package com.simplifide.core.license.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.simplifide.base.license.info.CustomerInfo;
import com.simplifide.base.license.info.LicenseCheck;

public class LicenseTopComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private NoLicenseComposite lisenceComposite;
	private UserEntryComposite userComposite;
	private LicenseCheck.Info  licenseInfo;
	private Form form;
	
	public LicenseTopComposite(Composite parent, int style, LicenseCheck.Info info) {
		super(parent, style);
		this.licenseInfo = info;
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		this.setForm(toolkit.createForm(this));
		getForm().setBounds(10, 10, 700, 540);
		toolkit.paintBordersFor(getForm());
		getForm().setText("License Request Form");
		getForm().getBody().setLayout(new GridLayout(1, false));
		/*Form form = toolkit.createForm(this);
		form.setBounds(this.getBounds());
		*/
		
		//Composite composite = new Composite(this, SWT.NONE);
		
		lisenceComposite = new NoLicenseComposite(getForm().getBody(), SWT.NONE, info, this);
		lisenceComposite.setBounds(10, 10, 700, 197);
		toolkit.adapt(lisenceComposite);
		toolkit.paintBordersFor(lisenceComposite); 
		
		
		
		
		userComposite = new UserEntryComposite(getForm().getBody(), SWT.NONE);
		userComposite.setBounds(10, 224, 700, 240);
		toolkit.adapt(userComposite);
		toolkit.paintBordersFor(userComposite);
		
		if (lisenceComposite.getRequestType() == LicenseRequestRadioComposite.FORM) {
			userComposite.toggleEnable(true);
		}
		else {
			userComposite.toggleEnable(false);
		}
	
	}
	
	public void setEmail() {
		this.lisenceComposite.setEmailButton();
	}
	
	public void toggleForm(boolean enable) {
		this.userComposite.toggleEnable(enable);
	}

	public int getRequestType() {
		return lisenceComposite.getRequestType();
	}
	
	public String getLicenseType() {
		return lisenceComposite.getLicenseType();
	}
	
	public CustomerInfo getCustomerInfo() {
		return userComposite.getCustomerInformation();
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Form getForm() {
		return form;
	}
}
