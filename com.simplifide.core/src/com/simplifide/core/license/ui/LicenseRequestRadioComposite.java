package com.simplifide.core.license.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class LicenseRequestRadioComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	
	public static final int FORM    = 0;
	public static final int EMAIL   = 2;
	public static final int NONE    = 3;
	
	Button userForm;
	Button emailForm;
	Button continueForm;
	
	private LicenseTopComposite topComposite;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LicenseRequestRadioComposite(Composite parent, int style, LicenseTopComposite top) {
		super(parent, style);
		this.topComposite = top;
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		userForm = toolkit.createButton(this, "Web Request", SWT.RADIO);
		userForm.setBounds(10, 10, 168, 16);
		toolkit.adapt(userForm, true, true);
        Listener listener = new Listener() {
            public void handleEvent (Event e) {
            	topComposite.toggleForm(userForm.getSelection() || emailForm.getSelection());
            }
        };
        userForm.addListener(SWT.Selection, listener);

		
		//webForm = toolkit.createButton(this, "Fill Out Web Form", SWT.RADIO);
		//webForm.setEnabled(false);
		//webForm.setBounds(10, 32, 182, 16);
		//toolkit.adapt(webForm, true, true);
		
		emailForm = toolkit.createButton(this, "Email Request", SWT.RADIO);
		emailForm.setBounds(10, 30, 290, 16);
		toolkit.adapt(emailForm, true, true);
		emailForm.addListener(SWT.Selection, listener);
		
		continueForm = toolkit.createButton(this, "Continue", SWT.RADIO);
		continueForm.setBounds(10, 50, 197, 16);
		toolkit.adapt(continueForm, true, true);
		continueForm.addListener(SWT.Selection, listener);
	}
	
	public void setContinueSelected() {
		this.continueForm.setSelection(true);
	}
	
	public int getRequestType() {
		if (userForm != null && userForm.getSelection()) return FORM;
		else if (emailForm.getSelection()) return EMAIL;
		else return NONE;
	}

	public void setEmailButton() {
		this.emailForm.setSelection(true);
		this.continueForm.setSelection(false);
		this.userForm.setSelection(false);
		
	}
	
	
	

}
