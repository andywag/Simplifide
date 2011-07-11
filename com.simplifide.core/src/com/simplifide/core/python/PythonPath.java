package com.simplifide.core.python;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.SyspathArchive;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.python.inter.StartupInterface;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.util.FileUtility;

public class PythonPath {

	private static List<String> getBasePaths() {
		
		ArrayList<String> list = new ArrayList<String>();
		/*
		File fjython = FileUtility.getFileFromInstall("external/jython.jar");
        File flib    = FileUtility.getFileFromInstall("jython_2.5/Lib");
        File fscript = FileUtility.getFileFromInstall("jython_2.5/script");
        list.add(fjython.getAbsolutePath());
        list.add(flib.getAbsolutePath());
        list.add(fscript.getAbsolutePath());
        */
		
		try {
			URL fjythonU = FileUtility.getURLFromInstall("external/jython.jar");
	        URL flibU    = FileUtility.getURLFromInstall("jython_2.5/Lib");
	        URL fscriptU = FileUtility.getURLFromInstall("jython_2.5/Script");
	        list.add(fjythonU.getPath());
	        list.add(flibU.getPath());
	        list.add(fscriptU.getPath());
		}
		catch (Exception e) {
			HardwareLog.logError(e);
		}

        
        
        
        
        return list;
	}
	
	private static List<String> getOverridePaths() {
		boolean rel = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.SCRIPT_RELEASE);
        String rdir = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.SCRIPT_RELEASE_PATH);
        
        ArrayList<String> list = new ArrayList<String>();
        if (rel) {
        	list.add(rdir);
        }
        return list;
	}
	
	private static List<String> getConfigPath() {
		ArrayList<String> list = new ArrayList<String>();
		File script = ConfigurationDirectoryLoader.getScriptDirectory();
		if (script != null && script.exists()) {
			list.add(script.getAbsolutePath());
		}
		
		return list;
	}
	
	private static List<String> getSuitePaths() {
		EclipseSuite activeSuite = ActiveSuiteHolder.getDefault().getSuite();
		ArrayList<String> list = new ArrayList<String>();
		if (activeSuite != null && activeSuite.getScriptFolder() != null && activeSuite.getScriptFolder().getLocation() != null) {
         	File tfile = activeSuite.getScriptFolder().getLocation().toFile();
         	list.add(tfile.getAbsolutePath());
         }
		return list;
	}
	
	public static List<String> getPaths(boolean withStartup) {
		ArrayList<String> paths = new ArrayList<String>();
		
		if (withStartup) {
			StartupInterface.PathProvider pathProvider = StartupLoader.getPaths();
				if (pathProvider != null) {
					for (String path : pathProvider.getPaths()) {
						paths.add(path);
					}
				}
			}
		
		paths.addAll(getSuitePaths());
		paths.addAll(getConfigPath());
		paths.addAll(getOverridePaths());
		paths.addAll(getBasePaths());
		return paths;
	}
	
	public static String getSystemPath(String sep, boolean withStartup) {
		
		List<String> paths = getPaths(withStartup);
		
		StringBuilder builder = new StringBuilder();
		for (String path : paths) {
			builder.append("'" + path + "'" + sep);
		}
		return builder.toString();
	}
	
	public static PyList getPathList(boolean withStartup) {
		List<String> paths = getPaths(withStartup);
		PyList pylist = new PyList();
		for (String path : paths) {
			if (path.endsWith("jar")) {
				SyspathArchive arch;
				try {
					arch = new SyspathArchive(path);
					pylist.add(arch);
				} catch (IOException e) {
				}
				
			}
			else {
				pylist.add(new PyString(path));
			}
		}
		
		pylist.add(new PyString("__classpath__"));
		pylist.add(new PyString("__pyclasspath__/"));
		return pylist;
	}
	
}
