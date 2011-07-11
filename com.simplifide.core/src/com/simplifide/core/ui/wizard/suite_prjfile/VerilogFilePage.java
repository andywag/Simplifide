package com.simplifide.core.ui.wizard.suite_prjfile;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.source.ProjectSourceList;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.suitecontents.ProjectContentLoader;

public class VerilogFilePage extends WizardPage{

	private VerilogFileComposite fileComposite;
	
	protected VerilogFilePage() {
		super("Files");
		this.setTitle("Files");
		this.setDescription("Select the files which are used in this project.");
	}

	public void createControl(Composite parent) {
		this.fileComposite = new VerilogFileComposite(parent, SWT.None); 
		this.setControl(this.fileComposite);
	}
	
	public StructureFile getSourceXML() {
		String[] files = this.getFiles();
		String contents = ProjectContentLoader.convertListToXml(files);
		StructureFile struct = new StructureFile(ProjectSourceList.SOURCE_LOCATION);
		struct.setLinkType(StructureFile.LINK_NEW);
		struct.setContents(contents);
		return struct; 
	}
	
	public String[] getFiles() {
		return fileComposite.getFiles();
	}

}
