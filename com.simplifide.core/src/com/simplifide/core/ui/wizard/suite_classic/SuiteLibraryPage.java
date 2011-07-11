/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.simplifide.core.project.generator.SuiteGeneratorOptions;

public class SuiteLibraryPage extends WizardPage{

	private Button ieeeButton;
	private Button ieeeProposedButton;
	private Button stdButton;
	private Button unisimVhdlButton;
	
	private Button ovmButton;
	private Button uvmButton;
	private Button vmmButton;
	private Button unisimVerilogButton;

	
	
	public SuiteLibraryPage() {
		super("Libraries");
		this.setTitle("Libraries");
		this.setDescription("Add Libraries to Project");
	}
	
	public void createControl(Composite parent)  {
		Composite comp = new Composite(parent,SWT.NULL);
		GridData data = new GridData(GridData.FILL_BOTH);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		comp.setLayout(layout);
		comp.setLayoutData(data);
	
		
		
		
		Group vhdl = new Group(comp, SWT.SHADOW_ETCHED_IN);
		vhdl.setText("VHDL");
		vhdl.setLayout(new GridLayout());
		vhdl.setLayoutData(new GridData(GridData.FILL_BOTH));
		

		Group sv   = new Group(comp, SWT.SHADOW_ETCHED_IN);
		sv.setText("SystemVerilog");
		sv.setLayout(new GridLayout());
		sv.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		ieeeButton = new Button(vhdl,SWT.CHECK);
		ieeeButton.setText("ieee");
		
		ieeeProposedButton = new Button(vhdl,SWT.CHECK);
		ieeeProposedButton.setText("ieee_proposed");
		
		/*stdButton = new Button(vhdl,SWT.CHECK);
		stdButton.setText("std");*/
		
		unisimVhdlButton = new Button(vhdl,SWT.CHECK);
		unisimVhdlButton.setText("unisim Version 11.1 (VHDL)");
		
		ovmButton = new Button(sv,SWT.CHECK);
		ovmButton.setText("ovm");
		
		uvmButton = new Button(sv,SWT.CHECK);
		uvmButton.setText("uvm");
		
		vmmButton = new Button(sv,SWT.CHECK);
		vmmButton.setText("vmm");
		
		unisimVerilogButton = new Button(sv,SWT.CHECK);
		unisimVerilogButton.setText("unisim Versions 11.1 (Verilog)");
		
		this.setControl(comp);
		
	}
	
	public SuiteGeneratorOptions.Libraries getLibraries() {
		SuiteGeneratorOptions.Libraries libraries = new SuiteGeneratorOptions.Libraries();
		libraries.ieee = ieee();
		libraries.ieee_proposed = ieee_propsed();
		libraries.std = false;
		libraries.ovm = ovm();
		libraries.vmm = vmm();
		libraries.uvm = uvm();
		libraries.unisimVerilog = unisimVerilog();
		libraries.unisimVHDL = unisimVHDL();
		return libraries;
	}
	
	public boolean ieee() {
		return ieeeButton.getSelection();
	}
	public boolean ieee_propsed() {
		return ieeeProposedButton.getSelection();
	}
	
	
	public boolean unisimVHDL() {
		return unisimVhdlButton.getSelection();
	}
	
	public boolean ovm() {
		return ovmButton.getSelection();
	}
	public boolean uvm() {
		return uvmButton.getSelection();
	}
	public boolean vmm() {
		return vmmButton.getSelection();
	}
	
	public boolean unisimVerilog() {
		return unisimVerilogButton.getSelection();
	}
	
	
	
}
