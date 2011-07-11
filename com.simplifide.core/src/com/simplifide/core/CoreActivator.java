/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.editors.format.FormatIndentProvider;
import com.simplifide.core.license.LicenseStartup;
import com.simplifide.core.scalaext.ScalaStartup;
import com.simplifide.core.ui.preference.SourcePreferenceInitializer;

/**
 * The activator class controls the plug-in life cycle
 */
public class CoreActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.simplifide.core";

	public static final String ERROR_PARSER_SIMPLE_ID = "eparser";

	// The shared instance
	private static CoreActivator plugin;
	
	private ScopedPreferenceStore preferenceStore;
        private CoreWorkspaceListener listener;

	/**
	 * The constructor
	 */
	public CoreActivator() { 
		plugin = this;
	}

	

	
	
	
	
	public void start(BundleContext context) throws Exception {
	    super.start(context);
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		LicenseStartup.initializeLicense();
		
	    try {
	    	BuildHandler.buildInitialProject();
	    }
	    catch (Exception e) {
	    	HardwareLog.logError(e);
	    }
	    
            
        listener = new CoreWorkspaceListener();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
        SourcePreferenceInitializer.latePreferenceLoader();

        SourcePreferenceInitializer.LOADED = true;  
        FormatSupport.getInstance().setSupport(FormatIndentProvider.getInstance());
		
	}

	

	public void stop(BundleContext context) throws Exception {
		plugin = null;
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
        listener = null;
		super.stop(context);
	}

	protected void initializeImageRegistry(ImageRegistry reg) {
		
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static CoreActivator getDefault() {
		return plugin;
	}

	
	
	public IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
            preferenceStore = new ScopedPreferenceStore(new InstanceScope(),getBundle().getSymbolicName());

        }
        return preferenceStore;
    }

	
}
