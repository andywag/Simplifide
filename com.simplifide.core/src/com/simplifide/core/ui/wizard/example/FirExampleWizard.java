package com.simplifide.core.ui.wizard.example;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.util.FileUtility;

public class FirExampleWizard extends ExampleTopWizard{
	
	public FirExampleWizard() {super();
		init();
	}

	private void init() {
		this.getMainPage().setTitle("Fir Example Project");
		this.getMainPage().setDescription("Fir Example Project");
	}
	@Override
	protected void generateProject(IProject handle) {
		FileUtility.copyInstallResource("resources/dsp_example/fir/libraries", "libraries", handle);
		FileUtility.copyInstallResource("resources/dsp_example/fir/projects", "projects", handle);
		FileUtility.copyInstallResource("resources/dsp_example/fir/script", "script", handle);
		FileUtility.copyInstallResource("resources/dsp_example/fir/test", "test", handle);
	}

}
