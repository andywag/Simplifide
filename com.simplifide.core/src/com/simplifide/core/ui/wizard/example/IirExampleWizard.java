package com.simplifide.core.ui.wizard.example;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.util.FileUtility;

public class IirExampleWizard extends ExampleTopWizard{
	
	public IirExampleWizard() {super();
		init();
	}

	private void init() {
		this.getMainPage().setTitle("IIR Example Project");
		this.getMainPage().setDescription("IIR Example Project");
	}
	@Override
	protected void generateProject(IProject handle) {
		FileUtility.copyInstallResource("resources/dsp_example/iir/libraries", "libraries", handle);
		FileUtility.copyInstallResource("resources/dsp_example/iir/projects", "projects", handle);
		FileUtility.copyInstallResource("resources/dsp_example/iir/script", "script", handle);
		FileUtility.copyInstallResource("resources/dsp_example/iir/test", "test", handle);
	}

}
