package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.core.resources.IProject;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.wizard.example.ExampleTopWizard;
import com.simplifide.core.util.FileUtility;

public class XilinxExampleWizard extends ExampleTopWizard{

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.suite.XilinxExampleWizard";
	
	
	
	public XilinxExampleWizard() {super();
		init();
	}
	
	private void init() {
	   
		this.getMainPage().setTitle("Xilinx Example Project");
		this.getMainPage().setDescription("Xilinx Example Project");
		
	}
	
	@Override
	protected void generateProject(IProject handle) {
		FileUtility.copyInstallResource("resources/xilinx_example/Virtex_6/Libraries", "Libraries", handle);
		FileUtility.copyInstallResource("resources/xilinx_example/Virtex_6/Projects", "Projects", handle);
		FileUtility.copyInstallResource("resources/xilinx_example/Virtex_6/Scripts", "Scripts", handle);
		FileUtility.copyInstallResource("resources/xilinx_example/Virtex_6/ZipFiles", "ZipFiles", handle);
		FileUtility.copyInstallResourceFile("resources/xilinx_example/Virtex_6/Structure.xml", "Structure.xml", handle);

	}
	
	protected void afterCreation(IProject projectHandle) {
    	super.afterCreation(projectHandle);
    	/*try {
			projectHandle.build(IncrementalProjectBuilder.FULL_BUILD,null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}*/
    }
	
	

}
