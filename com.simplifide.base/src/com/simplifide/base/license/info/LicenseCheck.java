package com.simplifide.base.license.info;

import java.util.Date;

import com.simplifide.base.license.HardwareLicenseManager;

public class LicenseCheck {

	public static final int LICENSE_NONE           = 0;
	public static final int LICENSE_INVALID        = 1;
	public static final int LICENSE_NOMATCH        = 2;
	public static final int LICENSE_FREE           = 3;
	public static final int LICENSE_TRIAL          = 4;
	public static final int LICENSE_TRIAL_EXPIRED  = 5;
	public static final int LICENSE_VALID          = 6;
	public static final int LICENSE_VALID_EXPIRED  = 7;
	public static final int LICENSE_ACADEMIC_VALID          = 8;
	public static final int LICENSE_ACADEMIC_VALID_EXPIRED  = 9; 

	
	/*
	public static Info checkLicense(String file) {
		Info info = HardwareLicenseManager.getInstance().validate(file);
		
		return info;
	}
	*/
	
	public static class Info {
		public int issueType;
		public String message;
		public Date expiration;
		public UserInformation user;
		
		public Info() {}
		public Info(int type, String message) {
			this.issueType = type;
			this.message = message;
		}
	}
	
	public static class None extends Info {
		public None() {
			super(LICENSE_NONE,"");
		}
	}
	
	
	
}
