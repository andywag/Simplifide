package com.simplifide.core.pythonext.basic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.simplifide.core.HardwareLog;



public class PythonFileInterface {
	
	public static String readFileContents(String fname) {
		String ustr = "";
		try {
	        BufferedReader in = new BufferedReader(new FileReader(fname));
	        String str;
	        while ((str = in.readLine()) != null) {
	            ustr += str + "\r\n";
	        }
	        in.close();
	    } catch (IOException e) {
	    	HardwareLog.logError(e);
	    }
	    return ustr;
	}
}

