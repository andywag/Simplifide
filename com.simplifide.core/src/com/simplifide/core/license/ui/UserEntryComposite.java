package com.simplifide.core.license.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.simplifide.base.license.info.CustomerInfo;

public class UserEntryComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private Text nameText;
	private Text companyText;
	private Text addressText;
	private Text cityText;
	private Text stateText;
	private Text zipText;
	private Text titleText;
	private Text countryText;
	private Text phoneText;
	private Text emailText;

	private ArrayList<Label> labels = new ArrayList<Label>();
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserEntryComposite(Composite parent, int style) {
		super(parent, style);
		Display display = Display.getCurrent();
		setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		/*
		Form form = toolkit.createForm(this);
		form.setBounds(0,0,580,240);
		form.setText("User Information");
		*/
		
		int lblCol = 10;
		int lblCol2 = 410;
		int lblCol3 = 530;
		
	
		
		Label lblName = toolkit.createLabel(this, "Name:*");
		lblName.setBounds(lblCol, 10, 55, 15);
		
		Label lblCompany =  toolkit.createLabel(this, "Company:*");
		lblCompany.setBounds(lblCol, 40, 55, 15);
		
		Label lblTitle = toolkit.createLabel(this, "Title:*");
		lblTitle.setBounds(lblCol2, 40, 37, 15);
		
		Label lblAddress = toolkit.createLabel(this, "Address:");
		lblAddress.setBounds(lblCol, 70, 55, 15);
		 
		Label lblCity = toolkit.createLabel(this, "City:");
		lblCity.setBounds(lblCol, 100, 55, 15);
		
		Label lblState = toolkit.createLabel(this, "State:"); 
		lblState.setBounds(lblCol2, 100, 37, 15);
		
		Label lblZip = toolkit.createLabel(this, "Zip:");
		lblZip.setBounds(lblCol3, 100, 24, 15);
		
		Label lblCountry = toolkit.createLabel(this, "Country:");
		lblCountry.setBounds(lblCol, 130, 55, 15);

		Label lblPhone = toolkit.createLabel(this, "Phone:");
		lblPhone.setBounds(lblCol, 160, 55, 15);
		
		Label lblEmail = toolkit.createLabel(this, "Email:*");
		lblEmail.setBounds(lblCol, 190, 55, 15);
		
		this.labels.add(lblEmail);
		this.labels.add(lblPhone);
		this.labels.add(lblState);
		this.labels.add(lblAddress);
		this.labels.add(lblCity);
		this.labels.add(lblCompany);
		this.labels.add(lblZip);
		this.labels.add(lblTitle);
		this.labels.add(lblName);
		this.labels.add(lblCountry);
		
		int txtCol = 100;
		int txtCol2 = 470;
		int txtCol3 = 560;
		
		nameText = toolkit.createText(this, "");
		nameText.setBounds(txtCol, 7, 552, 21);
		
		companyText = toolkit.createText(this, "");
		companyText.setBounds(txtCol, 37, 300, 21);
		
		titleText = toolkit.createText(this, "");
		titleText.setBounds(txtCol2, 37, 183, 21);
		
		addressText = toolkit.createText(this, "");
		addressText.setBounds(txtCol, 67, 552, 21);
		
		cityText = toolkit.createText(this, "");
		cityText.setBounds(txtCol, 97, 300, 21);
		
		stateText = toolkit.createText(this, "");
		stateText.setBounds(txtCol2, 97, 46, 21);

	
		zipText = toolkit.createText(this, "");
		zipText.setBounds(txtCol3, 97, 93, 21);

		countryText = toolkit.createText(this, "");
		countryText.setBounds(txtCol, 127, 300, 21);
		
		phoneText = toolkit.createText(this, "");
		phoneText.setBounds(txtCol, 157, 300, 21);

		
		emailText = toolkit.createText(this, "");
		emailText.setBounds(txtCol, 187, 300, 21);

	}
	
	public CustomerInfo getCustomerInformation() {
		CustomerInfo info = new CustomerInfo();
		info.name    = nameText.getText();
		info.company = companyText.getText();
		info.title   = titleText.getText();
		info.address = addressText.getText();
		info.city    = cityText.getText();
		info.state   = stateText.getText();
		info.zip     = zipText.getText();
		info.country = countryText.getText();
		info.phone   = phoneText.getText();
		info.email   = emailText.getText();
		return info;
	}
	
	public void toggleEnable(boolean enable) {
		nameText.setEnabled(enable);
		companyText.setEnabled(enable);
		titleText.setEnabled(enable);
		addressText.setEnabled(enable);
		cityText.setEnabled(enable);
		stateText.setEnabled(enable);
		zipText.setEnabled(enable);
		countryText.setEnabled(enable);
		phoneText.setEnabled(enable);
		emailText.setEnabled(enable);
		for (Label lab : this.labels) {
			lab.setEnabled(enable);
		}
	}

}
