package com.simplifide.core.ui.wizard.suite_prjfile;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class VerilogProjectTopPage extends WizardPage{

	private VerilogProjectTopComposite libraryComposite;
	private String type;
	
	protected VerilogProjectTopPage(String name, String type) {
		super(name);
		this.type = type;
		this.setTitle("Libraries");
		this.setDescription("Set the source directory for the libraries which are used by the project.");
	}

	public void createControl(Composite parent) {
		this.libraryComposite = new VerilogProjectTopComposite(parent, SWT.None, type); 
		this.setControl(this.libraryComposite);
	}
	
	public String[] getLibraries() { 
		return libraryComposite.getLibraries();
	}
	
	public static class Project extends VerilogProjectTopPage {
		public Project() {
			super("Projects","Project");
		}
	}
	
	public static class Library extends VerilogProjectTopPage {
		public Library() {
			super("Libraries","Library");
		}
	}

}
