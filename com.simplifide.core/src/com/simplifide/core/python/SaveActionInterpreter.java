package com.simplifide.core.python;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.python.context.SaveContext;
import com.simplifide.core.python.context.SaveInterface;
import com.simplifide.core.python.inter.StartupInterface;
import com.simplifide.core.source.design.DesignFile;

public class SaveActionInterpreter {

	private static SaveActionInterpreter instance;
	private DesignFile dfile;
	
	private SaveActionInterpreter() {}
	public static SaveActionInterpreter getDefault() {
		if (instance == null) instance = new SaveActionInterpreter();
		return instance;
	}
	
	private StartupInterface.SaveActionProvider[] getActionProviders() {
		StartupInterface start = PythonStartup.getDefault().getStartup();
		if (start != null) {
			StartupInterface.SaveActionProvider[] providers =  start.getSaveActionProviders();
			return providers;
		}
		return new StartupInterface.SaveActionProvider[0];
	}
	
	private SaveInterface getSaveInterface() {
		return new SaveContext(this.dfile);
	}
	
	public void expand() {
		Job ujob = new Job("Expand Templates") {

			protected IStatus run(IProgressMonitor monitor) {
				try { 
					StartupInterface.SaveActionProvider[] providers = getActionProviders();
					for (StartupInterface.SaveActionProvider provider : providers) {
						provider.expand(getSaveInterface());
					}
				}
				catch (Exception e) {
					HardwareLog.logError("Template Error",e);
				}
				return Status.OK_STATUS;
			}
			
		};
		ujob.schedule();
	}
	
	public void save(DesignFile dfile) {
		this.dfile = dfile;
		Job ujob = new Job("Save") {
			
			protected IStatus run(IProgressMonitor monitor) {
				try { 
					StartupInterface.SaveActionProvider[] providers = getActionProviders();
					for (StartupInterface.SaveActionProvider provider : providers) {
						provider.save(getSaveInterface());
					}
				}
				catch (Exception e) {
					HardwareLog.logError("Template Error",e);
				}
				return Status.OK_STATUS;
			}
			
		};
		ujob.schedule();
	}
	
	public void clean() {
		Job ujob = new Job("Clean") {

			protected IStatus run(IProgressMonitor monitor) {
				try { 
					StartupInterface.SaveActionProvider[] providers = getActionProviders();
					for (StartupInterface.SaveActionProvider provider : providers) {
						provider.clean(getSaveInterface());
					}
				}
				catch (Exception e) {
					HardwareLog.logError("Template Error",e);
				}
				return Status.OK_STATUS;
			}
			
		};
		ujob.schedule();
		
	}
	
}
