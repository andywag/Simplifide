package com.simplifide.core.license.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.simplifide.base.license.info.UserInformation;

public class LicenseTypeComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	
	private Button freeButton;
	private Button trialButton;
	private Button proButton;
	private Button acaButton;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LicenseTypeComposite(NoLicenseComposite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		NoLicenseComposite.TypeButtonListener listener = parent.createListener();
		freeButton = toolkit.createButton(this, "Free Version", SWT.RADIO);
		freeButton.setBounds(10, 10, 120, 16);
		toolkit.adapt(freeButton, true, true);
		freeButton.addSelectionListener(listener);
		
		trialButton = toolkit.createButton(this, "Trial Version", SWT.RADIO);
		trialButton.setSelection(true);
		trialButton.setBounds(10, 32, 134, 16);
		toolkit.adapt(trialButton, true, true);
		trialButton.addSelectionListener(listener);

		
		acaButton = toolkit.createButton(this, "Academic Version", SWT.RADIO);
		acaButton.setBounds(10, 54, 175, 16);
		toolkit.adapt(acaButton, true, true);
		acaButton.addSelectionListener(listener);

		
		proButton = toolkit.createButton(this, "Professional Version", SWT.RADIO);
		proButton.setBounds(10, 76, 164, 16);
		toolkit.adapt(proButton, true, true);
		proButton.addSelectionListener(listener);
		

		

	}

	public String getLicenseType() {
		if (this.proButton.getSelection()) return UserInformation.PRO;
		else if (this.acaButton.getSelection()) return UserInformation.ACADEMIC;
		else if (this.trialButton.getSelection()) return UserInformation.TRIAL;
		else return UserInformation.FREE;
	}
	
}
