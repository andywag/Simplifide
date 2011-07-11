package com.simplifide.core.python;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.python.inter.StartupInterface;
import com.simplifide.core.python.inter.UtilitiesInterface;
import com.simplifide.core.python.inter.StartupInterface.SaveActionProvider;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class StartupLoader {

	
	private static String[] splitName(String pa) {
		String[] sp = pa.split("\\.");
		String[] out = new String[2];
		
		out[1] = sp[sp.length-1];
		out[0] = pa.substring(0, pa.length()-out[1].length()-1);
		return out;
	}
	
	public static StartupInterface.PathProvider getPaths() {
		try {
			String pa = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.SCRIPT_PATH_NAME);
			String[] pre = splitName(pa);
			
			JythonObjectFactory fact = new JythonObjectFactory(StartupInterface.PathProvider.class, 
					pre[0], 
					pre[1]);
			StartupInterface.PathProvider start = (StartupInterface.PathProvider) fact.createObject();
			return start;
		}
		catch(Exception e) {
			//HardwareLog.logError(e);
		}
		return null;	
	}
	
	public static StartupInterface getStartup() {
		try {
			String pa = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.SCRIPT_STARTUP_NAME);
			String[] pre = splitName(pa);
			
			JythonObjectFactory fact = new JythonObjectFactory(StartupInterface.class, 
					pre[0], 
					pre[1]);
			StartupInterface start = (StartupInterface) fact.createObject();
			SaveActionProvider[] provs =   start.getSaveActionProviders(); 
			return start;
		}
		catch(Exception e) {
			//HardwareLog.logError(e);
		}
		return null;	
	}
	
	public static UtilitiesInterface getUtilities() {
		try {
			JythonObjectFactory fact = new JythonObjectFactory(UtilitiesInterface.class, 
					"Simplifide.Interface2.Utilities", 
					"Main");
			UtilitiesInterface start = (UtilitiesInterface) fact.createObject();
			return start;
		}
		catch(Exception e) {
			//HardwareLog.logError(e);
		}
		return null;
		
	}
}
