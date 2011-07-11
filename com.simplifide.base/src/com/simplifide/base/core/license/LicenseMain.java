/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.license;

import java.io.File;
import java.util.Date;

import com.simplifide.base.license.HardwareLicenseManager;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;

public class LicenseMain {

	public final static int PROBLEM_NONE = 0;
	public final static int PROBLEM_INVALID = 1;
	public final static int PROBLEM_EXPIRE = 2;
	public final static int PROBLEM_EARLY = 3;
	
	public static Info validateLicense(File file) {
		LicenseManager manager = new LicenseManager(ClientLicenseParam.getInstance());
		try {
			LicenseContent content = manager.install(file);
		} catch (Exception e) {
			return new Info(false,e.getLocalizedMessage(),e);
		}
		return new Info(true,"",null);
	}
	
	public static Info validateLicense(String file) {
		HardwareLicenseManager man = HardwareLicenseManager.getInstance();
		man.validate(file);
		
		LicenseManager manager = new LicenseManager(ClientLicenseParam.getInstance());
		try {
			LicenseContent content = manager.install(file);
		} catch (Exception e) {
			return new Info(false,e.getLocalizedMessage(),e);
		}
		return new Info(true,"",null);
	}
	
	public static class Info {
		public boolean valid;
		public String problem;
		public Exception exception;
		public Date date;
		public int type;
		
		public Info(boolean valid, String problem, Exception e) {
			this.valid = valid;
			this.problem = problem;
			this.exception = e;
			if (e == null)
				this.type = PROBLEM_NONE;
			else 
				this.type = PROBLEM_INVALID;
			if (e instanceof LicenseContentException) {
				LicenseContentException cont = (LicenseContentException)e;
				this.date = cont.date;
				if (cont instanceof LicenseContentException.Expired) {
					this.type = PROBLEM_EXPIRE;
				}
				else this.type = PROBLEM_INVALID;
			}
			
		}
	}
	
}
