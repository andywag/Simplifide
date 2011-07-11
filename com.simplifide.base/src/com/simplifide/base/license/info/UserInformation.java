package com.simplifide.base.license.info;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.simplifide.mac.UUIDGen;

public class UserInformation {
 
	public static final String FREE = "FREE";
	public static final String TRIAL = "TRIAL";
	public static final String ACADEMIC = "ACADEMIC";
	public static final String PRO = "PRO";
	
	
	private String username;
	private String osname;
	private String ipaddress;
	private String iphost;
	private String macAddress;
	private String feature;
	private String type;
	
	public UserInformation() {}
	
	public String createFileString() {
		String out = "UserName : " + this.username +"\n";
		out +=       "OS       : " + this.osname+"\n";
		out +=       "IP       : " + this.ipaddress+"\n";
		out +=       "Host     : " + this.iphost+"\n";
		out +=       "MAC      : " + this.macAddress+"\n";
		out +=       "Type     : " + this.feature+"\n";
		return out;
	}
	
	public List getSerialData() {
		ArrayList nlist = new ArrayList();
		nlist.add(username);
		nlist.add(osname);
		nlist.add(ipaddress);
		nlist.add(iphost);
		nlist.add(macAddress);
		nlist.add(feature);
		nlist.add(type);
		
 		return nlist;
	}
	
	public Object convertObject(Object obj) {
		if (obj == null) return "";
		return obj;
	}
	
	public HashMap getDataMapping() {
		HashMap map = new HashMap();
		map.put("user", convertObject(username));
		map.put("os", convertObject(osname));
		map.put("ip", convertObject(ipaddress));
		map.put("host", convertObject(iphost));
		map.put("mac", convertObject(macAddress));
		map.put("feature", convertObject(feature));
		map.put("type", convertObject(feature)); // Correct Type and Feature Mixed
		return map;
	}
	
	
	public static UserInformation getLocalUserInfo() {
		UserInformation info = new UserInformation();

		String user = System.getProperty("user.name");
		user = user.replace(" ", "");
		info.setUsername(user); 
		info.setOsname(System.getProperty("os.name")); 
		info.setMacAddress(UUIDGen.getMacAddress());
		try {
			info.setIpaddress(InetAddress.getLocalHost().getHostAddress());
			info.setIphost(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return info;

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setOsname(String osname) {
		this.osname = osname;
	}

	public String getOsname() {
		return osname;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIphost(String iphost) {
		this.iphost = iphost;
	}

	public String getIphost() {
		return iphost;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getFeature() {
		return feature;
	}
	
}
