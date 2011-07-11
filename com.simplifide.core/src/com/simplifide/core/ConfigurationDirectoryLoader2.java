package com.simplifide.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.IPath;

import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.util.FileUtility;

public class ConfigurationDirectoryLoader2 {

	public static final String CONFIG_INTERNAL = "simplifide_configuration";
	public static final String DIR_TEMPLATES = "templates";
	public static final String DIR_TEMPLATES_MAKE = "make";
	
	public static final String MAKEFILE_DIR = "templates/make";
	public static final String MAKEFILE_LOCATION = "templates/makefile/Makefile.ftl";
	public static final String TEMPLATE_MAKEFILE = "makefile/Makefile";
	
	/** External Config Folder */
	public static File getBaseConfigFolder() {
		String loc = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.CONFIG_DIRECTORY);
		if (loc != null) {
			File ufile = new File(loc);
			if (ufile.exists()) return ufile.getParentFile();
		}
		return null;
	}
	
	/** Internal Config folder */
	public static File getInstallConfigFolder() {
		return FileUtility.getFileFromInstall(CONFIG_INTERNAL);
	}
	
	/** Storage Config Folder */
	public static File getStorageConfigFolder() {
		IPath path = CoreActivator.getDefault().getStateLocation().append(CONFIG_INTERNAL);
		return path.toFile();
	}

	/** Return the fiel from this path */
	public static File getFileFromPath(String path) {
		File afile = new File(getBaseConfigFolder(),path);
		if (!afile.exists()) afile = new File(getStorageConfigFolder(),path);
		if (!afile.exists()) afile = new File(getInstallConfigFolder(),path);
		return afile;
	}
	
	private static File createFile(File fromFile,  File baseDir, String path) {
		String[] items = path.split("/");
		File current = baseDir;
		if (!current.exists()) current.mkdir();
		for (int i=0;i<items.length-1;i++) {
			String item = items[i];
			current = new File(current,item);
			if (!current.exists()) current.mkdir();
		}
		File nfile = new File(current,items[items.length -1]);
		InputStream in;
		try {
			in = new FileInputStream(fromFile);
			OutputStream out = new FileOutputStream(nfile); 
			// Transfer bytes from in to out
			byte[] buf = new byte[1024]; 
			int len; 
			while ((len = in.read(buf)) > 0) { 
				out.write(buf, 0, len); 
			} 
			in.close(); 
			out.close(); 
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		} 
		
		return nfile;
	}
	
	/** Return the file */
	public static File getOrCreateFromPath(String path) {
		
		File toFile = new File(getBaseConfigFolder(),path);
		if (!toFile.exists()) toFile = new File(getStorageConfigFolder(),path);
		if (!toFile.exists()) { // Copy the file into the storage configuration
			File fromFile = new File(getInstallConfigFolder(),path);
			if (fromFile.exists()) {
				if (getBaseConfigFolder() != null && getBaseConfigFolder().exists()) 
					toFile =createFile(fromFile,getBaseConfigFolder(),path);
				else 
					toFile = createFile(fromFile,getStorageConfigFolder(),path);
			}
		}
	
		return toFile;
	}
	
	public File getProjectMakefileDirectory() {
		return getFileFromPath(MAKEFILE_DIR);
	}
	
	
	
	private static String CONFIG_LOCATION = "config";
	private static String CONFIG_ERROR_FILE = CONFIG_LOCATION + "/Error.xml";
	private static String MAKE_LOCATION = "templates/makefile/";
	private static String BASE_CONFIGURATION = "simplifide_configuration/";
	private static String STRUCTURE_LOCATION = "directory_structure/Structure.xml";
	private static String PYTHON_PATH = "script";
	
	
	
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
