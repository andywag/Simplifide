package com.simplifide.core.navigator.action;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.console.CommandProcessConsole;
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public abstract class CommandAction extends Action{

	protected CommandDefinition command;
	
	protected abstract void runMake();
	protected abstract void runCommand();
	
	public CommandAction(CommandDefinition command) {
		super(command.getCommand());
		this.command = command;
		
		
		
	}
	
	public void run() {
		if (command.isMakeAction()) runMake();
		else runCommand();
	}
	
	
	public static class Suite extends CommandAction {

		private EclipseSuite suite;
		public Suite(EclipseSuite suite, CommandDefinition command) {
			super(command);
			this.suite = suite;
		}
		
		public void runMake() {
			IFile file = suite.getMakefile();
			if (file.exists()) {
				File ufile = suite.getProject().getLocation().toFile();
				CommandProcessConsole.runCommandNew(new String[] {"make"}, true, ufile,command);
			}
			else {
				HardwareLog.logInfo("Makefile Doesn't Exist");
			}
		}
		
		public void runCommand() {
			
		}
		
	}
	
	public static class Project extends CommandAction {

		private EclipseBaseProject project;
		public Project(EclipseBaseProject project,CommandDefinition command) {
			super(command);
			this.project = project;
			this.setEnabled(HardwareChecker.isBuildEnabled());
		}
		
		public void runMake() {
			IFile file = project.getMakefile();
			if (file.exists()) {
				File ufile = project.getBaseFolder().getLocation().toFile();
				CommandProcessConsole.runCommandNew(new String[] {"make"}, true, ufile,command);
			}
			else {
				HardwareLog.logInfo("Makefile Doesn't Exist");
			}
		}
		
		public void runCommand() {
			
		}
		
	}
	
}
