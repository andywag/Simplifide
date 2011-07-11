package com.simplifide.core.ui.wizard.suite_import;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.project.generator.SuiteStructureGenerator;
import com.simplifide.core.project.library.har.EclipseHarProject;

public class FixedLibraryComposite extends Composite {

	private Button ieeeButton;
	private Button ieeeProposedButton;
	private Button stdButton;
	private Button unisimVhdlButton;
	
	private Button ovmButton;
	private Button uvmButton;
	private Button vmmButton;
	private Button unisimVerilogButton;

	private EclipseSuite suite;
	
	public static String[] LIBS = new String[] {"ovm.har","uvm.har","vmm.har","ieee.har",
		"ieee_proposed.har","unisim_vhdl.har","unisim_verilog.har"};
	
	
	public FixedLibraryComposite(Composite parent, int style, EclipseSuite suite) {
		super(parent,style);
		this.suite = suite;
		this.createControl(parent);
		
	}
	
	public void createControl(Composite parent)  {
		
		GridData data = new GridData(GridData.FILL_BOTH);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		this.setLayout(layout);
		this.setLayoutData(data);
	
		
		
		
		Group vhdl = new Group(this, SWT.SHADOW_ETCHED_IN);
		vhdl.setText("VHDL");
		vhdl.setLayout(new GridLayout());
		vhdl.setLayoutData(new GridData(GridData.FILL_BOTH));
		

		Group sv   = new Group(this, SWT.SHADOW_ETCHED_IN);
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
		
		//this.setControl(comp);
		this.initBasedOnSuite();
	}
	
	private void initBasedOnSuite() {
		if (suite == null) return;
		List<CoreProjectBasic> libs = this.suite.getLibraryList();
		for (CoreProjectBasic lib : libs) {
			String n = lib.getname();
			if (n.equalsIgnoreCase("ieee")) this.ieeeButton.setSelection(true);
			else if (n.equalsIgnoreCase("ieee_proposed"))  this.ieeeProposedButton.setSelection(true);
			else if (n.equalsIgnoreCase("ovm")) this.ovmButton.setSelection(true);
			else if (n.equalsIgnoreCase("uvm")) this.uvmButton.setSelection(true);
			else if (n.equalsIgnoreCase("vmm")) this.vmmButton.setSelection(true);
			else if (n.equalsIgnoreCase("unisim")) {
				if (lib instanceof EclipseHarProject) {
					EclipseHarProject proj = (EclipseHarProject) lib;
					String hname = proj.getResource().getName();
					if (hname.equalsIgnoreCase("unisim_vhdl.har")) this.unisimVhdlButton.setSelection(true);
					else this.unisimVerilogButton.setSelection(true);
				}
			}
		}
	}
	
	private void deleteLibraries(EclipseSuite suite) {
		try {
			List<IFolder> folders = suite.getLibraryFolders();
			for (IFolder folder : folders) {
				for (IResource member  : folder.members()) {
					for (String lib : LIBS) {
						if (member.getName().equalsIgnoreCase(lib)) member.delete(true, null);
					}
				}
				
			}
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
	}
	
	public void recreateLibraries(EclipseSuite suite) {
		this.deleteLibraries(suite);
		IFolder library = suite.getLibraryFolders().get(0);
		SuiteGeneratorOptions.Libraries libs = getLibraries();
		if (libs.ieee) SuiteStructureGenerator.getDefault().createLibraryLink(library,"ieee.har");
		else if (libs.ieee_proposed) SuiteStructureGenerator.getDefault().createLibraryLink(library,"ieee_proposed.har");
		else if (libs.ovm) SuiteStructureGenerator.getDefault().createLibraryLink(library,"ovm.har");
		else if (libs.vmm) SuiteStructureGenerator.getDefault().createLibraryLink(library,"vmm.har");
		else if (libs.uvm) SuiteStructureGenerator.getDefault().createLibraryLink(library,"uvm.har");
		else if (libs.unisimVerilog) SuiteStructureGenerator.getDefault().createLibraryLink(library,"unisim_verilog.har");
		else if (libs.unisimVHDL) SuiteStructureGenerator.getDefault().createLibraryLink(library,"unisim_vhdl.har");

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
