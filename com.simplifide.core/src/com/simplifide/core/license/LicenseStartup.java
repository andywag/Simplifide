package com.simplifide.core.license;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.license.ui.LicenseDialog;


public class LicenseStartup {

	public static void initializeLicense() {
		
		String license = ConfigurationDirectoryLoader.getLicenseString();
		LicenseCheck.Info info = LicenseCheck.checkLicense(license);
		
		if (info.issueType != LicenseCheck.LICENSE_VALID &&
			info.issueType != LicenseCheck.LICENSE_ACADEMIC_VALID) {
			final IWorkbench workbench = PlatformUI.getWorkbench();
	    	if (workbench.getActiveWorkbenchWindow() == null) return;
	            if (workbench.getActiveWorkbenchWindow().getShell() == null) return;
	            final LicenseDialog dialog = new LicenseDialog(workbench.getActiveWorkbenchWindow().getShell(),
	            		info);
	            workbench.getDisplay().syncExec(new Runnable() {
	        	public void run() {
	                dialog.open();
	        		
	        	    }
	    	});
		}
		
	}
	
}
