package com.simplifide.core.ui.wizard.example;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.util.FileUtility;

public class ComplexMultExampleWizard extends ExampleTopWizard{
	
	public ComplexMultExampleWizard() {super();
		init();
	}

	private void init() {
		this.getMainPage().setTitle("Complex Multiplier Example Project");
		this.getMainPage().setDescription("Complex Multiplier Example Project");
	}
	@Override
	protected void generateProject(IProject handle) {
		FileUtility.copyInstallResource("resources/dsp_example/complex_mult/libraries", "libraries", handle);
		FileUtility.copyInstallResource("resources/dsp_example/complex_mult/projects", "projects", handle);
		FileUtility.copyInstallResource("resources/dsp_example/complex_mult/script", "script", handle);
		FileUtility.copyInstallResource("resources/dsp_example/complex_mult/test", "test", handle);
	}

}
