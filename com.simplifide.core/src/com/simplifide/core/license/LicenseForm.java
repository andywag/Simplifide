package com.simplifide.core.license;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LicenseForm extends Composite {
	private Text nameText;
	private Text companyText;
	private Text addressText;
	private Text cityText;
	private Text stateText;
	private Text zipText;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LicenseForm(Composite parent, int style) {
		super(parent, style);
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setBounds(10, 10, 55, 15);
		lblName.setText("Name");
		
		nameText = new Text(this, SWT.BORDER);
		nameText.setBounds(89, 7, 150, 21);
		
		Label lblCompany = new Label(this, SWT.NONE);
		lblCompany.setBounds(10, 34, 55, 15);
		lblCompany.setText("Company");
		
		companyText = new Text(this, SWT.BORDER);
		companyText.setBounds(89, 34, 150, 21);
		
		Label lblAddress = new Label(this, SWT.NONE);
		lblAddress.setBounds(10, 64, 55, 15);
		lblAddress.setText("Address");
		
		addressText = new Text(this, SWT.BORDER);
		addressText.setBounds(89, 61, 337, 21);
		
		Label lblCity = new Label(this, SWT.NONE);
		lblCity.setText("City");
		lblCity.setBounds(10, 94, 55, 15);
		
		cityText = new Text(this, SWT.BORDER);
		cityText.setBounds(89, 88, 103, 21);
		
		Label lblState = new Label(this, SWT.NONE);
		lblState.setText("State");
		lblState.setBounds(211, 94, 37, 15);
		
		Label lblZip = new Label(this, SWT.NONE);
		lblZip.setText("Zip");
		lblZip.setBounds(311, 94, 24, 15);
		
		stateText = new Text(this, SWT.BORDER);
		stateText.setBounds(254, 88, 46, 21);
		
		zipText = new Text(this, SWT.BORDER);
		zipText.setBounds(335, 88, 91, 21);
		
		Label lblTitle = new Label(this, SWT.NONE);
		lblTitle.setText("Title");
		lblTitle.setBounds(255, 34, 37, 15);
		
		text_4 = new Text(this, SWT.BORDER);
		text_4.setBounds(301, 31, 125, 21);
		
		Label lblCountry = new Label(this, SWT.NONE);
		lblCountry.setText("Country");
		lblCountry.setBounds(10, 124, 55, 15);
		
		text_5 = new Text(this, SWT.BORDER);
		text_5.setBounds(89, 118, 211, 21);
		
		Label lblPhone = new Label(this, SWT.NONE);
		lblPhone.setText("Phone");
		lblPhone.setBounds(10, 152, 55, 15);
		
		text_6 = new Text(this, SWT.BORDER);
		text_6.setBounds(89, 149, 211, 21);
		
		Label lblEmail = new Label(this, SWT.NONE);
		lblEmail.setText("Email");
		lblEmail.setBounds(10, 180, 55, 15);
		
		text_7 = new Text(this, SWT.BORDER);
		text_7.setBounds(89, 176, 211, 21);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
