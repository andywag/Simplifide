package com.simplifide.core.license.ui;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.base.license.info.CustomerInfo;
import com.simplifide.base.license.info.LicenseCheck;
import com.simplifide.base.license.info.LicenseRequest;
import com.simplifide.base.license.info.UserInformation;
import com.simplifide.core.HardwareLog;


public class LicenseDialog extends Dialog{

	private static int STATE_NONE = 0;
	private static int STATE_DONE = 1;
	
	private LicenseTopComposite comp;
	private LicenseCheck.Info   info;
	private int state = STATE_NONE;
	
	public static int EMAIL_ATTEMPT = 0;
	
	public LicenseDialog(Shell parentShell, LicenseCheck.Info info) {
		super(parentShell);
		this.info = info;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		comp = new LicenseTopComposite(container, SWT.None, info);
		return container;
	}

	private boolean valididateCustomerInfo(CustomerInfo info) {
		if (!info.validInformation()) {
			comp.getForm().setMessage("Please Fill out All of the Fields in the Form.",IMessageProvider.ERROR );
			return false;
		}
		return true;
	}
	
	private String getNewMessage() {
		String umess = "An email with instructions for installing you license has been sent\n";
		umess += "to your email address. ";
		return umess;
	}
	
	private String getExistingMessage() {
		String umess = "\nA trial license has already been issued for you. Please upgrade to a\n";
		umess += "profressional license, or continue using with limited features.\n ";
		umess += "If you feel this is an error please contact support@simplifide.com.";
		return umess;
	}
	
	private String getTooManyFreeMessage() {
		String umess = "\nTwo free licenses have already been sent to this account.\n";
		umess += "Please contact support@simplifide.com for an additional license.";
		return umess;
	}
	
	private String getEmailMessage() {
		String umess = "\nA file info.txt has been created at the base of the workspace.\n";
		umess += "Please send an email to support@simplifde.com including this file.\n";
		umess += "You should receive a response within 24 hours.";
		return umess;
	}
	
	private String getEmailMessage2() {
		String umess = "\nYour default email program should have opened with an email.\n";
		umess += "Please send this email and you should receive a response\n";
		umess += "within 24 hours. If your mail program doesn't open please try again.";
		return umess;
	}
	
	private String handleErrorCondition() {
		String umess = "\nThere was an issue with your request. Please try again or use email.\n";
		umess += "Please contact support@simplifde.com if the issue continues.\n";
		return umess;
	}
	private String handleNoGoogle() {
		String umess = "\nThere seems to be an issue with your internet connection.\n";
		umess += "or you are behind a firewall. Please request a license by email";
		return umess;
	}
	private String handleNoSimple() {
		String umess = "\nThere seems to be an issue with the Simplifide server. Please try later or \n";
		umess += "send an email to support@simplifide.com if the issue persists. \n";
		return umess;
	}
	
	private void handleNoState() {
		int reqType = comp.getRequestType();
		String lic = comp.getLicenseType();
		CustomerInfo info = comp.getCustomerInfo();
		boolean valid = this.valididateCustomerInfo(info);
		if (valid) {
			LicenseRequest req = new LicenseRequest();
			if (reqType == LicenseRequestRadioComposite.FORM) {
				comp.getForm().setMessage("Sending your request.",IMessageProvider.INFORMATION );
				Object obj = req.requestNewLicense(info, lic);
				if (obj instanceof HashMap) {
					HashMap<String,String> map = (HashMap) obj;
					String mess = map.get("message");
					if (mess.equalsIgnoreCase("NEW")) {
						comp.getForm().setMessage(this.getNewMessage(), IMessageProvider.INFORMATION);
						state = STATE_DONE;
					}
					else if (mess.equalsIgnoreCase("TOO_MANY_FREE")) {
						comp.getForm().setMessage(this.getTooManyFreeMessage(),IMessageProvider.ERROR);
						state = STATE_DONE;
					}
					else if (mess.equalsIgnoreCase("EXISTING")) {
						comp.getForm().setMessage(this.getExistingMessage(),IMessageProvider.ERROR);
						state = STATE_DONE;
					}
					else if (mess.equalsIgnoreCase("NOGOOGLE")) {
						comp.getForm().setMessage(this.handleNoGoogle(),IMessageProvider.ERROR);
						comp.setEmail();
					}
					else if (mess.equalsIgnoreCase("NOSIMPLE")) {
						comp.getForm().setMessage(this.handleNoSimple(),IMessageProvider.ERROR);
						comp.setEmail();
					}
					else {
						comp.getForm().setMessage(this.handleErrorCondition(),IMessageProvider.ERROR);
						state = STATE_DONE;
					}
					
				}
				else {
					comp.getForm().setMessage(this.handleErrorCondition(),IMessageProvider.ERROR);
				}
				
				
			}
		}
		
	}
	
	
	
	protected void okPressed() {
		if (comp.getRequestType() == LicenseRequestRadioComposite.NONE) {
			super.okPressed();
		}
		else if (comp.getRequestType() == LicenseRequestRadioComposite.FORM) {
			if (state == STATE_NONE) {
				comp.getForm().setMessage("Sending Request for License",IMessageProvider.WARNING);
				comp.getForm().update();
				comp.update();
				this.handleNoState();
			}
			else if (state == STATE_DONE) {
				super.okPressed();
			}
		}
		else if (comp.getRequestType() == LicenseRequestRadioComposite.EMAIL) {
			if (state == STATE_NONE) {
				this.handleEmailRequest();
			}
			else if (state == STATE_DONE) {
				super.okPressed();
			}
		}
		
	}
	
	private boolean handleDirectEmailRequest(String info1) {
		String info = info1.replace(" ", "%20");
		info = info.replace("\r", "");
		info = info.replace("\n", "%0A");
		String mail = "mailto:support@simplifide.com?subject=License%20Request&body=";
		mail += info;
		URI uri;
		
		try {
			uri = new URI(mail);
			Class desktop = Class.forName("java.awt.Desktop");
			//Method isDesktopSupported = desktop.getDeclaredMethod("isDeskTopSupported", new Class[] {});
			Method mailM       = desktop.getDeclaredMethod("mail", new Class[] {URI.class});
			Method getDesktop = desktop.getDeclaredMethod("getDesktop", new Class[] {});
			
			Object desk = getDesktop.invoke(null, new Object[] {});
			Object mailR = mailM.invoke(desk, new Object[] {uri});
			comp.getForm().setMessage(this.getEmailMessage2(), IMessageProvider.INFORMATION);
			return true;
			
		}
		catch (Exception e) {
			this.handleEmailFileRequest(info1);
		}
		
		/*
		
			if (java.awt.Desktop.isDesktopSupported()) {
				String mail = "mailto:support@simplifide.com?subject=License%20Request&body=";
				mail += info;
				URI uri;
				try {
					uri = new URI(mail);
					Desktop desk = java.awt.Desktop.getDesktop();
					desk.mail(uri);
					comp.getForm().setMessage(this.getEmailMessage2(), IMessageProvider.INFORMATION);
					return true;
				} catch (URISyntaxException e) {
					HardwareLog.logError(e);
					this.handleEmailFileRequest(info1);
				} catch (IOException e) {
					HardwareLog.logError(e);
					this.handleEmailFileRequest(info1);
				}
				
			}
			*/
		return false;
			
		
	}
	
	
	private void handleEmailFileRequest(String info) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		File ufile = new File(root.getLocationURI());
		File bfile = new File(ufile,"info.txt");
		FileWriter write;
		try {
			write = new FileWriter(bfile);
			write.write(info);
			write.close();
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		comp.getForm().setMessage(this.getEmailMessage(), IMessageProvider.INFORMATION);
		
	}
	
	private void handleEmailRequest() {
		CustomerInfo cust = comp.getCustomerInfo();
		UserInformation user = UserInformation.getLocalUserInfo();
		user.setType(comp.getLicenseType());
		user.setFeature(comp.getLicenseType());
		String info = "";
		info += cust.createFileString();
		info += user.createFileString();
		boolean direct = false;
		if (EMAIL_ATTEMPT == 0)  {
			direct = this.handleDirectEmailRequest(info);
			EMAIL_ATTEMPT++;
		}
		if (!direct) this.handleEmailFileRequest(info);
		
	}  
	
	
		
		


}
