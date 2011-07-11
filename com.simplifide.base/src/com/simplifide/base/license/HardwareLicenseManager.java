package com.simplifide.base.license;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.simplifide.base.BaseLog;
import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.base.license.info.UserInformation;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class HardwareLicenseManager {

	public static int NONE     = 0;
	public static int FREE     = 1;
	public static int TRIAL    = 2;
	public static int PRO      = 3;
	public static int ACADEMIC = 4;
	
	private static HardwareLicenseManager instance;
	
	private int licenseType;
	
	private HardwareLicenseManager() {}
	
	public static HardwareLicenseManager getInstance() {
		if (instance == null) instance = new HardwareLicenseManager();
		return instance;
	}
	

	/** Check that license is valid on today's date */
	private boolean checkValidDate(LicenseContent content) {
		Date now = new Date();
		Date before = content.getNotBefore();
		Date after  = content.getNotAfter();
		
		// Set an extended time limit for the license
		
		after = this.getExpirationDate(after);
		
		if (!(now.after(before) && now.before(after))) {
			return false;
		}
		return true;
	}
	
	/** Check that license is valid on today's date */
	private LicenseCheck.Info checkExtra(LicenseContent content) {
		UserInformation user = (UserInformation) content.getExtra();
		UserInformation local = UserInformation.getLocalUserInfo();
		LicenseCheck.Info info = new LicenseCheck.Info();
		
		if (user.getType().equalsIgnoreCase(UserInformation.FREE)) { // Free Version with limited restrictions
			if (user.getUsername().equalsIgnoreCase(local.getUsername())) {
				info.issueType = LicenseCheck.LICENSE_FREE;
			}
		}
		else if (user.getType().equalsIgnoreCase(UserInformation.TRIAL)) {
			if (user.getUsername().equalsIgnoreCase(local.getUsername())) {
				if (checkValidDate(content)) {
					info.issueType = LicenseCheck.LICENSE_TRIAL;
				}
				else {
					info.issueType = LicenseCheck.LICENSE_TRIAL_EXPIRED;
				}
			}
			else {
				info.issueType = LicenseCheck.LICENSE_NOMATCH;
			}
		}
		else if (user.getType().equalsIgnoreCase(UserInformation.PRO) ||
				 user.getType().equalsIgnoreCase(UserInformation.ACADEMIC)) {
			if (user.getUsername().equalsIgnoreCase(local.getUsername()) &&
				(user.getMacAddress().equalsIgnoreCase(local.getMacAddress()) ||
				 user.getIphost().equalsIgnoreCase(local.getIphost()))) {
				
				if (checkValidDate(content)) {
					if (user.getType().equalsIgnoreCase(UserInformation.PRO)) {
						info.issueType = LicenseCheck.LICENSE_VALID;
					}
					else if (user.getType().equalsIgnoreCase(UserInformation.ACADEMIC)) {
						info.issueType = LicenseCheck.LICENSE_ACADEMIC_VALID;
					}
				}
				else {
					if (user.getType().equalsIgnoreCase(UserInformation.PRO)) {
						info.issueType = LicenseCheck.LICENSE_VALID_EXPIRED;
					}
					else if (user.getType().equalsIgnoreCase(UserInformation.ACADEMIC)) {
						info.issueType = LicenseCheck.LICENSE_ACADEMIC_VALID_EXPIRED;
					}
				}
			}
			else {
				info.issueType = LicenseCheck.LICENSE_NOMATCH;
			}
		}
		return info;
	} 
	
	private void setLicenseData(LicenseCheck.Info info) {
		switch(info.issueType) {
			case  LicenseCheck.LICENSE_FREE:
			case  LicenseCheck.LICENSE_TRIAL_EXPIRED :
			case  LicenseCheck.LICENSE_ACADEMIC_VALID_EXPIRED :
			case  LicenseCheck.LICENSE_VALID_EXPIRED : 
				  this.licenseType = FREE;
			  	  break;
			case LicenseCheck.LICENSE_TRIAL : 
				this.licenseType = TRIAL;
				break;
			case LicenseCheck.LICENSE_VALID :
			case LicenseCheck.LICENSE_ACADEMIC_VALID : 
				this.licenseType = PRO;
				break;
			default : 
				this.licenseType = NONE;
				break;
		}
		
	}
	
	private Date getExpirationDate(Date after) {
		// Set an extended time limit for the license
		Calendar xmas = new GregorianCalendar(2010, Calendar.AUGUST, 31);
		Date exp = xmas.getTime();
		if (after.before(exp)) return exp;
		return after;
	}
	
	public LicenseCheck.Info validateContent(LicenseContent content) {
		LicenseCheck.Info info = checkExtra(content); 
		if (info == null) info = new LicenseCheck.Info(LicenseCheck.LICENSE_VALID, "");
		info.expiration = this.getExpirationDate(content.getNotAfter());
		info.user = (UserInformation) content.getExtra();
		this.setLicenseData(info);
		return info;
		
	}
	
	public LicenseCheck.Info validate(String file1) {
		String file = file1.trim();
		if (file == null || file.length() <= 1) { // Check if License Exists
			return new LicenseCheck.None();
		}
		
		LicenseManager manager = new LicenseManager(ClientLicenseParam.getInstance());
		try {
			LicenseContent cont = manager.install(file);
			return this.validateContent(cont);
			
		} catch (Exception e) {
			BaseLog.logError(e);
			LicenseCheck.Info info = new LicenseCheck.Info(LicenseCheck.LICENSE_INVALID,e.getLocalizedMessage());
			return info;
		}
	}
	
	public boolean isFreeLicense() {
		if (licenseType == FREE ||
			licenseType == TRIAL || 
			licenseType == PRO)
			return true;
		return false;
	}
	public boolean isTrialLicense() {
		if (licenseType == TRIAL || 
			licenseType == PRO)
			return true;
		return false;
	}
	public boolean isProLicense() {
		if (licenseType == PRO ||
			licenseType == ACADEMIC)
			return true;
		return false;
	}
	
}
