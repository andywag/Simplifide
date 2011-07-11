package com.simplifide.base.license.info;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomerInfo {

	public String name = "";
	public String company= "";
	public String title= "";
	public String address= "";
	public String city= "";
	public String state= "";
	public String zip= "";
	public String country= "";
	public String phone= "";
	public String email= "";
	
	/** Returns a string with information used to create a file */
	public String createFileString() {
		String out = "Name    : " + this.name   +"\n";
		out +=       "Company : " + this.company+"\n";
		out +=       "Title   : " + this.title+"\n";
		out +=       "Address : " + this.address+"\n";
		out +=       "City    : " + this.city+"\n";
		out +=       "State   : " + this.state+"\n";
		out +=       "Zip     : " + this.zip+"\n";
		out +=       "Country : " + this.country+"\n";
		out +=       "Phone   : " + this.phone+"\n";
		out +=       "Email   : " + this.email+"\n";
		return out;
	}
	
	private boolean checkField(String field) {
		if (field != null && !field.equalsIgnoreCase("")) return true;
		return false;
	}
	
	public boolean validInformation() {
		if (!checkField(name)) return false;
		if (!checkField(company)) return false;
		if (!checkField(title)) return false;
		if (!checkField(email)) return false;

		return true;
	}
	
	public Object convertObject(Object obj) {
		if (obj == null) return "";
		return obj;
	}
	
	public HashMap getDataMapping() {
		HashMap map = new HashMap();
		map.put("name", convertObject(name));
		map.put("company", convertObject(company));
		map.put("title", convertObject(title));
		map.put("address", convertObject(address));
		map.put("city", convertObject(city));
		map.put("state", convertObject(state));
		map.put("zip", convertObject(zip));
		map.put("country", convertObject(country));
		map.put("phone", convertObject(phone));
		map.put("email", convertObject(email));
		return map;
	}
	
	
}
