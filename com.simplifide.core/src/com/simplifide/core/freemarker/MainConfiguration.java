/*
 * MainConfigurationClass.java
 *
 * Created on April 14, 2007, 11:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker;


import java.io.File;
import java.io.IOException;

import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.freemarker.storage.StorageLocations;
import com.simplifide.core.freemarker.transforms.GenericTransform;
import com.simplifide.core.freemarker.transforms.TransformList;
import com.simplifide.core.util.FileUtility;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;



/**
 *
 * @author Andy
 */
public class MainConfiguration {
    
    private static MainConfiguration instance;
    
    private Configuration configuration;
    private Configuration installConfiguration;
    private Configuration storageConfiguration;

   
    /** Creates a new instance of MainConfigurationClass */
    public MainConfiguration() {
        init();
    }
    
    public static MainConfiguration getInstance() {
        if (instance == null) instance = new MainConfiguration();
        return instance;
    }
    
    public static Template getTemplate(String tname) {

    	MainConfiguration main = getInstance();
    	Configuration local   = main.getConfiguration();
    	Configuration storage = main.storageConfiguration;
    	Configuration install = main.getInstallConfiguration();
    	
    	try {
    		if (local != null) {
    			Template template = local.getTemplate(tname);
    			if (template != null) return template;
    		}
    	} catch (IOException e) {

    	}
    	try {
    		if (storage != null) {
    			Template template;
    			template = storage.getTemplate(tname);
     			if (template != null) return template;
    		}
    	} catch (IOException e) {
    		//HardwareLog.logError(e);
    	}
    	try {
    		if (install != null) {
    			Template template;
    			template = install.getTemplate(tname);
    			if (template != null) return template;
    		}
    	} catch (IOException e) {
    		HardwareLog.logError(e);
    	}

    	return null;

}
    
    private void setupConfiguration(Configuration config) {
    	config.setObjectWrapper(new DefaultObjectWrapper());
    	config.setSharedVariable("template", new GenericTransform(TransformList.TEMPLATE));
    	config.setSharedVariable("ind", new GenericTransform(TransformList.MININDENT));
    	config.setSharedVariable("hyp", new GenericTransform(TransformList.HYPERLINK));
    	config.setTemplateExceptionHandler(new MainErrorHandler());
    }
    
    private void init() {
        try     {
        	File nfile = ConfigurationDirectoryLoader.getFileFromConfigurationDirectoryorNull("templates");  
        	if (nfile != null) {
            	this.configuration = new freemarker.template.Configuration();
            	this.configuration.setDirectoryForTemplateLoading(nfile);
            	this.setupConfiguration(this.configuration);
        	}
        	nfile = FileUtility.getFileFromInstall("simplifide_configuration/templates");
        	if (nfile != null) {
            	this.installConfiguration = new freemarker.template.Configuration();
            	this.installConfiguration.setDirectoryForTemplateLoading(nfile);
            	this.setupConfiguration(this.installConfiguration);
        	}
            nfile = StorageLocations.getInstance().getTemplateLocation();
            if (nfile != null) {
            	this.storageConfiguration = new freemarker.template.Configuration();
            	this.storageConfiguration.setDirectoryForTemplateLoading(nfile);
            	this.setupConfiguration(this.storageConfiguration);
        	}
        
        }
        catch (IOException ex) {
        	HardwareLog.logError(ex);
        } 
	
}
    
     public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

	public void setInstallConfiguration(Configuration installConfiguration) {
		this.installConfiguration = installConfiguration;
	}

	public Configuration getInstallConfiguration() {
		return installConfiguration;
	}
    
}
