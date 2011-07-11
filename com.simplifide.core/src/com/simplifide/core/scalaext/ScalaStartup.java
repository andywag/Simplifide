package com.simplifide.core.scalaext;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.simplifide.core.HardwareLog;
import com.simplifide.scala2.command.Command;
import com.simplifide.scala2.command.CommandSection;
import com.simplifide.scala2.top.InterfaceCommands;

public class ScalaStartup {

	public static String ID = "com.simplifide.core.generator.provider";
	
	public static List<Command> loadCommands() {
		ArrayList<Command> commands = new ArrayList<Command>();
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(ID);
		for (IConfigurationElement e : config) {
			Object o;
			try {
				o = e.createExecutableExtension("client");
				if (o instanceof ScalaProvider) {
					ScalaProvider prov = (ScalaProvider) o;
					commands.addAll(prov.getCommands());
					InterfaceCommands.it().addCommands(prov.getCommands());
				}
			} catch (Exception e1) {
				HardwareLog.logError(e1);
			}
		}
		return commands;
	}

	public static List<String> getJavaKeyWords() {
		ArrayList<String> keywords = new ArrayList<String>();
		for (Command command : loadCommands()) {
			CommandSection sect = (CommandSection) command;
			keywords.addAll(sect.getJavaKeywords());	
		}
		return keywords;
	}
	
}

