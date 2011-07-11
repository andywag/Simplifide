package com.simplifide.core.freemarker.storage;

import java.io.File;

import org.eclipse.core.runtime.IPath;

import com.simplifide.core.CoreActivator;

public class StorageLocations {
	 public static String PROJECT_MAKE = "project.makefile.ftl"; //$NON-NLS-1$

	 private static StorageLocations instance;
	 
	 private StorageLocations() {}
	 
	 public static StorageLocations getInstance() {
		 if (instance == null) instance = new StorageLocations();
		 return instance;
	 }
	 
	 public File getBaseLocation() {
		 IPath path = CoreActivator.getDefault().getStateLocation();
		 return path.toFile();
	 }
	 
	 public File getConfigurationDirectory() {
		 return new File(getBaseLocation(),"simplifide_configuration");
	 }
	 
	 public File getTemplateLocation() {
		 return new File(this.getBaseLocation(),"simplifide_configuration/templates");
	 }
	 
}
