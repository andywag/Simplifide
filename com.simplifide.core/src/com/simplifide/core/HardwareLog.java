/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.simplifide.core.ui.preference.PreferenceConstants;

public class HardwareLog {
	
	
	   private static boolean getEnabled() {
		   return CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.DEBUG_INFO);
	   }
	/**
	    * Log the specified information.
	    * 
	    * @param message, a human-readable message, localized to the
	    *           current locale.
	    */
	   public static void logInfo(String message) {
               if (getEnabled())
                   log(IStatus.INFO, IStatus.OK, message, null);
              
	   }
           
           public static void logInfoAlways(String message) {
                   log(IStatus.INFO, IStatus.OK, message, null);
           }

	   /**
	    * Log the specified error.
	    * 
	    * @param exception, a low-level exception.
	    */
	   public static void logError(Throwable exception) {
               if (getEnabled())
                   logError("Unexpected Exception", exception);
	   }

	   /**
	    * Log the specified error.
	    * 
	    * @param message, a human-readable message, localized to the
	    *           current locale.
	    * @param exception, a low-level exception, or <code>null</code>
	    *           if not applicable.
	    */
	   public static void logError(String message, Throwable exception) {
               if (getEnabled())
                   log(IStatus.ERROR, IStatus.OK, message, exception);
	   }

	   /**
	    * Log the specified information.
	    * 
	    * @param severity, the severity; one of the following:
	    *           <code>IStatus.OK</code>,
	    *           <code>IStatus.ERROR</code>,
	    *           <code>IStatus.INFO</code>, or
	    *           <code>IStatus.WARNING</code>.
	    * @param pluginId. the unique identifier of the relevant
	    *           plug-in.
	    * @param code, the plug-in-specific status code, or
	    *           <code>OK</code>.
	    * @param message, a human-readable message, localized to the
	    *           current locale.
	    * @param exception, a low-level exception, or <code>null</code>
	    *           if not applicable.
	    */
	   public static void log(int severity, int code, String message,
	         Throwable exception) {

	      log(createStatus(severity, code, message, exception));
	   }

	   /**
	    * Create a status object representing the specified information.
	    * 
	    * @param severity, the severity; one of the following:
	    *           <code>IStatus.OK</code>,
	    *           <code>IStatus.ERROR</code>,
	    *           <code>IStatus.INFO</code>, or
	    *           <code>IStatus.WARNING</code>.
	    * @param pluginId, the unique identifier of the relevant
	    *           plug-in.
	    * @param code, the plug-in-specific status code, or
	    *           <code>OK</code>.
	    * @param message, a human-readable message, localized to the
	    *           current locale.
	    * @param exception, a low-level exception, or <code>null</code>
	    *           if not applicable.
	    * @return, the status object (not <code>null</code>).
	    */
	   public static IStatus createStatus(int severity, int code,
	         String message, Throwable exception) {

	      return new Status(severity, CoreActivator.PLUGIN_ID, code,
	            message, exception);
	   }

	   /**
	    * Log the given status.
	    * 
	    * @param status, the status to log.
	    */
	   public static void log(IStatus status) {
		   CoreActivator.getDefault().getLog().log(status);
	   }

}
