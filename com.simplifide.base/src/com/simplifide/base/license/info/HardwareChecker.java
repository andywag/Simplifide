package com.simplifide.base.license.info;

import com.simplifide.base.license.HardwareLicenseManager;

public class HardwareChecker {

	public static boolean isTemplateEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isCompletionEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isContextSensitiveCompletionEnabled() {
		return HardwareLicenseManager.getInstance().isProLicense();
	}
	
	public static boolean isFoldEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isHyperLinkEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isHyperLinkInstanceEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isHoverEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isHighlightEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isErrorEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	
	public static boolean isHierarchyEnabled() {
		//return HardwareLicenseManager.getInstance().isFreeLicense();
		return true;
	}
	
	public static boolean isClassHierarchyEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isObjectViewEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isQuickViewEnabled() {
		return HardwareLicenseManager.getInstance().isFreeLicense();
	}
	
	public static boolean isSearchEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isRefactoringEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isProjectEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isPythonEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isBuildEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isHarEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isWizardEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
	public static boolean isScalaEnabled() {
		return HardwareLicenseManager.getInstance().isTrialLicense();
	}
	
}
