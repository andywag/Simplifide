package com.simplifide.base.license;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.simplifide.base.BaseLog;
import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.base.license.info.UserInformation;


public class HardwareLicenseManager {

	public static int NONE     = 0;
	public static int FREE     = 1;
	public static int TRIAL    = 2;
	public static int PRO      = 3;
	public static int ACADEMIC = 4;
	
	private static HardwareLicenseManager instance;
	
	//private int licenseType;
	
	private HardwareLicenseManager() {}
	
	public static HardwareLicenseManager getInstance() {
		if (instance == null) instance = new HardwareLicenseManager();
		return instance;
	}

	
	public boolean isFreeLicense() {
		
		return true;
	}
	public boolean isTrialLicense() {
		
		return true;
	}
	public boolean isProLicense() {
		
		return true;
	}
	
}
