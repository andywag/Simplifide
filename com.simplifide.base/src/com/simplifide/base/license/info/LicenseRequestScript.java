package com.simplifide.base.license.info;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.simplifide.base.BaseLog;

/** External Class used to create license files */
public class LicenseRequestScript {

	private CustomerInfo customer = new CustomerInfo();
	private UserInformation user = new UserInformation(); 
	
	private void process(String ustr) {
		String[] us = ustr.split(":");
		if (us.length <= 1) return;
		if (us[0].trim().equalsIgnoreCase("NAME")) customer.name = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("COMPANY")) customer.company = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("TITLE")) customer.title = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("ADDRESS")) customer.address = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("CITY")) customer.city = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("STATE")) customer.state = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("ZIP")) customer.zip = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("COUNTRY")) customer.country = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("PHONE")) customer.phone = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("EMAIL")) customer.email = us[1].trim();
		else if (us[0].trim().equalsIgnoreCase("USERNAME")) {
			String usa = us[1].trim();
			usa = usa.replace(" ", "");
			user.setUsername(usa);
		}
		else if (us[0].trim().equalsIgnoreCase("OS")) user.setOsname(us[1].trim());
		else if (us[0].trim().equalsIgnoreCase("IP")) user.setIpaddress(us[1].trim());
		else if (us[0].trim().equalsIgnoreCase("HOST")) user.setIphost(us[1].trim());
		else if (us[0].trim().equalsIgnoreCase("MAC")) {
			user.setMacAddress(ustr.substring(us[0].length()+1).trim());
		}
		else if (us[0].trim().equalsIgnoreCase("TYPE")) {
			user.setType(us[1].trim());
			user.setFeature(us[1].trim());
		}

		
	}
	private void convertInfoFile() {
		try {
		    //BufferedReader in = new BufferedReader(new FileReader("C:/simplifide_base/customer_info/info.txt"));
		    BufferedReader in = new BufferedReader(new FileReader("/home/andy/simplifide_base/customer_info/info.txt"));

		    String str;
		    while ((str = in.readLine()) != null) {
		        process(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		

	}
	
	public static void main(String [ ] args) {
		LicenseRequestScript scr = new LicenseRequestScript();
		scr.convertInfoFile();
		scr.requestLicense(scr.user, scr.customer);
	}
	

	
	private HashMap convertInformation(UserInformation info, CustomerInfo cust) {
		HashMap map = info.getDataMapping();
		map.putAll(cust.getDataMapping());
		return map;
	}
	/** TODO test operation */
	private Object requestLicense(UserInformation info, CustomerInfo cust) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    try {
			config.setServerURL(new URL("https://simplifide.com/drupal6/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
		    client.setConfig(config);
		    Object[] params = new Object[] {convertInformation(info, cust)};
		    Object obj = client.execute("simplifide_andy.license", params);
		    
		    return obj;
		} catch (MalformedURLException e) {
			BaseLog.logError(e);
		} catch (XmlRpcException e) {
			BaseLog.logError(e);
			return null;
		}
		return null;
	}
	
}
