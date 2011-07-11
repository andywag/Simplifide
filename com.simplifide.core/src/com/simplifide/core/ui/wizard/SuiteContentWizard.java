package com.simplifide.core.ui.wizard;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class SuiteContentWizard extends FileNewWizard{

	
	
	public SuiteContentWizard() {
		super("Suite Contents");
	}

	protected HashMap<String,Object> createMap(String fn, String fe, IFile ifile) {
		HashMap<String,Object> map = super.createMap(fn, fe, ifile);
		


		return map;
	}
	
	protected void createMainPage() {
		super.createMainPage();
		WizardNewFileCreationPage wiz = (WizardNewFileCreationPage)this.getPage(this.getPagename());
		wiz.setFileName("Content.xml");
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
		return "suite_contents";
	}

}
