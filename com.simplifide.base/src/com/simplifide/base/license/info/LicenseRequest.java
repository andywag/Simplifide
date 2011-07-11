package com.simplifide.base.license.info;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcSun15HttpTransportFactory;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.osgi.util.tracker.ServiceTracker;

import com.simplifide.base.BaseActivator;
import com.simplifide.base.BaseLog;


public class LicenseRequest {
    
	
	private static String HTTPS_STRING = "https://simplifide.com/drupal6/xmlrpc.php";
	private static String HTTP_STRING = "http://simplifide.com/drupal6/xmlrpc.php";
	private static URI HTTPS;
	private static URI HTTP;
	
	static {
		try {
			HTTPS = new URL(HTTPS_STRING).toURI();
			HTTP  = new URL(HTTP_STRING).toURI();
		} catch (MalformedURLException e) {
			BaseLog.logError(e);
		} catch (URISyntaxException e) {
			BaseLog.logError(e);
		}
	}
	
	private static IProxyData getEclipseProxySettings() {
		
		ServiceTracker tracker = new ServiceTracker(BaseActivator.getDefault().getBundle().getBundleContext(), IProxyService.class.getName(), null); 
        tracker.open();  
        IProxyService service = (IProxyService	) tracker.getService();
        IProxyData[] data2 = service.getProxyData();
        for (IProxyData data : data2) {
        	if (data.getType().equalsIgnoreCase("HTTPS")) {
        		tracker.close();
        		return data;
        	}
        }
        tracker.close();
        return null;
	}
	
	
	public Object requestNewLicense(CustomerInfo cust, String type) {
		UserInformation user = UserInformation.getLocalUserInfo();
		user.setFeature(type);
		return requestLicense(user, cust);
	}
	
	private boolean checkConnection(String address) {
		
		try {
			URL simple = new URL(address);
			URLConnection sim = simple.openConnection();
			InputStream stream = sim.getInputStream();
			if (stream.available() > 0) {
				stream.close();
				return true;
			}
		} catch (MalformedURLException e) {
			BaseLog.logError(e);
			return false;
		} catch (IOException e) {
			BaseLog.logError(e);
			return false;
		}
		return false;
	}
	
	private Object checkErrorType() {
		HashMap map = new HashMap();
		if (!this.checkConnection("http://google.com")) {
			map.put("message", "NOGOOGLE");
			return map;
		}
		if (!this.checkConnection("http://simplifide.com")) {
			map.put("message", "NOSIMPLE");
			return map;
		}
		return map;
	}
	
	/** TODO test operation */
	private Object requestLicense(UserInformation info, CustomerInfo cust) {
		
		IProxyData data = LicenseRequest.getEclipseProxySettings();
		
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		if (data.getUserId() != null) config.setBasicUserName(data.getUserId());
		if (data.getPassword() != null) config.setBasicPassword(data.getPassword());
	    try {
			config.setServerURL(new URL("https://simplifide.com/drupal6/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			XmlRpcSun15HttpTransportFactory tran = new XmlRpcSun15HttpTransportFactory(client);
			if (data != null && data.getPort() > 0) {
				tran.setProxy(data.getHost(), data.getPort());
			}
			
		    client.setConfig(config);
		    Object[] params = new Object[] {convertInformation(info, cust)};
		    Object obj = client.execute("simplifide.license", params);
		    
		    return obj;
		} catch (MalformedURLException e) {
			BaseLog.logError(e);
		} catch (XmlRpcException e) {
			BaseLog.logError(e);
			return this.checkErrorType();
		}
		return null;
	}
	
	private HashMap convertInformation(UserInformation info, CustomerInfo cust) {
		HashMap map = info.getDataMapping();
		map.putAll(cust.getDataMapping());
		return map;
	}
	
	
	

}
