package com.simplifide.core.ui.wizard;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class ProjectStructureWizard extends FileNewWizard{

	
	
	public ProjectStructureWizard() {
		super("Project Structure");
	}

	protected HashMap<String,Object> createMap(String fn, String fe, IFile ifile) {
		HashMap<String,Object> map = super.createMap(fn, fe, ifile);
		
		map.put("XSD_LOC", "http://simplifide.com/projectStructure/");
		

		return map;
	}
	
	protected void createMainPage() {
		super.createMainPage();
		WizardNewFileCreationPage wiz = (WizardNewFileCreationPage)this.getPage(this.getPagename());
		wiz.setFileName("Structure.xml");
	}
	
	@Override
	public String getDefaultExtension() {
		return ".xml";
	}

	@Override
	public String getDefaultHeaderFile() {
		return null;
	}

	@Override
	public String getFileTemplate() {
		return null;
	}

	@Override
	public String getHeaderTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateDirectory() {
		return "file/xml/";
	}

	@Override
	public String getTemplateName() {
		return "project_structure";
	}

}
