package com.simplifide.core.ui.wizard.suite_import.project;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class ProjectComposite2 extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProjectComposite2(Composite parent, int style) {
		super(parent, style);
		
		Group grpVhdl = new Group(this, SWT.NONE);
		grpVhdl.setText("VHDL");
		grpVhdl.setBounds(10, 10, 206, 280);
		
		Button button = new Button(grpVhdl, SWT.CHECK);
		button.setBounds(10, 30, 93, 16);
		button.setText("Check Button");
		
		Button button_1 = new Button(grpVhdl, SWT.CHECK);
		button_1.setText("Check Button");
		button_1.setBounds(10, 52, 93, 16);
		
		Button button_2 = new Button(grpVhdl, SWT.CHECK);
		button_2.setText("Check Button");
		button_2.setBounds(10, 74, 93, 16);
		
		Group grpVerilog = new Group(this, SWT.NONE);
		grpVerilog.setText("Verilog");
		grpVerilog.setBounds(222, 10, 206, 280);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
