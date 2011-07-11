package com.simplifide.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.util.FileUtility;

public class ConfigurationDirectoryLoader {

	private static String CONFIG_LOCATION = "config";
	private static String CONFIG_ERROR_FILE = CONFIG_LOCATION + "/Error.xml";
	private static String MAKE_LOCATION = "templates/makefile/";
	private static String BASE_CONFIGURATION = "simplifide_configuration/";
	private static String STRUCTURE_LOCATION = "directory_structure/Structure.xml";
	private static String PYTHON_PATH = "script";
	
	public static File getBaseConfigFolder() {
		String loc = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.CONFIG_DIRECTORY);
		if (loc != null) {
			File ufile = new File(loc);
			if (ufile.exists()) return ufile.getParentFile();
		}
		return null;
	}
	
	public static File getConfigLocation() {
		return getFileFromConfigurationDirectory(CONFIG_LOCATION);
	}
	
	public static File getErrorParserXml() {
		return getFileFromConfigurationDirectory(CONFIG_ERROR_FILE);
	}
	
	public static File getWorkspaceStructure() {
		return  getFileFromConfigurationDirectory(STRUCTURE_LOCATION);
	}
	
	public static File getProjectMakeFileTemplatesDir() {
		File makeDir = getFileFromConfigurationDirectory(MAKE_LOCATION + "project/");
		return makeDir;
	}
	
	public static File getMakeFileSuiteTemplatesDir() {
		File makeDir = getFileFromConfigurationDirectory(MAKE_LOCATION + "suite/");
		return makeDir;
	}
	
	public static File getFileFromConfigurationDirectoryorNull(String relative) {
		//String loc = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.CONFIG_DIRECTORY);
		File baseFile = getBaseConfigFolder();
		if (baseFile == null) return null;
		return FileUtility.getFileFromInstall(BASE_CONFIGURATION + relative);
	}
	
	public static File getFileFromConfigurationDirectory(String relative) {
		//String loc = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.CONFIG_DIRECTORY);
		File baseFile = getBaseConfigFolder();
		if (baseFile != null) {
			File nfile = new File(baseFile,relative);
			if (nfile.exists()) return nfile;
		}
		return FileUtility.getFileFromInstall(BASE_CONFIGURATION + relative);
	}
	
	public static File getScriptDirectory() {
		File baseFile = getBaseConfigFolder();
		if (baseFile != null) {
			File nfile = new File(baseFile,PYTHON_PATH);
			if (nfile.exists()) return nfile;
		}
		return null;
	}
	
	/** Returns the text from the license file from the configuration directory */
	public static String getLicenseString() {
		File ufile = getBaseConfigFolder();
		if (ufile != null && ufile.exists()) {
			File licFile = new File(ufile,"license.txt");
			if (licFile != null && licFile.exists()) {
				 try {
				        BufferedReader in = new BufferedReader(new FileReader(licFile));
				        String lic = "";
				        String str;
				        while ((str = in.readLine()) != null) {
				           lic+= str;
				        }
				        in.close();
				        return lic;
				    } catch (IOException e) {
				    	HardwareLog.logError(e);
				    }
				}  
		}
		
		String lic = CoreActivator.getDefault().getPluginPreferences().getString(PreferenceConstants.LICENSE_STRING);
		return lic;

	}
	
}
