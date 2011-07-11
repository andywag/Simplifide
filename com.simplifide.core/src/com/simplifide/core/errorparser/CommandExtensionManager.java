package com.simplifide.core.errorparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;


public class CommandExtensionManager {

	private static final String STORAGE_ERRORPARSER_EXTENSIONS = "command.extensions.xml"; //$NON-NLS-1$
	private static final String ROOT    = "root";
	private static final String COMMAND = "command";
	
	private static final String TARGET = "target";
	private static final String TYPE   = "type";
	private static final String PARSER = "parser";
	private static final String SAVE   = "save";
	

	
	private static IPath getStoreLocation() {
		return CoreActivator.getDefault().getStateLocation().append(STORAGE_ERRORPARSER_EXTENSIONS);
	}
	
	private static String createBoolean(boolean bool) {
		if (bool) return "true";
		else return "false";
	}
	
	private static boolean decodeBoolean(String bool) {
		if (bool.equalsIgnoreCase("true")) return true;
		else return false;
	}
	
	private static void createCommand(CommandDefinition command, Element root) {
		Element com = new Element(COMMAND);
		com.setAttribute(TARGET, command.getCommand());
		com.setAttribute(TYPE,   Integer.toString(command.getType()));
		com.setAttribute(PARSER, command.getActualParserType());
		com.setAttribute(SAVE,   createBoolean(command.isOnSave()));
		root.addContent(com);
		
		
	}
	
	
	public static void createStore(List<CommandDefinition> commands) {
		Document doc = new Document();
		Element root = new Element(ROOT);
		doc.setRootElement(root);
		for (CommandDefinition command : commands) {
			createCommand(command, root);
		}

	      try {
	    	java.io.File storeFile = getStoreLocation().toFile();
	  		if (!storeFile.exists()) {
	  			storeFile.createNewFile();
	  		}
	  		OutputStream fileStream = new FileOutputStream(storeFile);
	  	      XMLOutputter serializer = new XMLOutputter();
			serializer.output(doc, fileStream);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
	}
	
	private static CommandDefinition parseCommand(Element command) {
		String target = command.getAttributeValue(TARGET);
		int type      = Integer.parseInt(command.getAttributeValue(TYPE));
		String parser = command.getAttributeValue(PARSER);
		boolean save  = decodeBoolean(command.getAttributeValue(SAVE));
		int parser1 = ErrorParserManager.getIndexOfParser(parser);
		
		CommandDefinition command1 = new CommandDefinition(target, type, parser1, save);
		return command1;
		
	}
	private static List<CommandDefinition> parseRootFile(Element root) {
		ArrayList<CommandDefinition> commands = new ArrayList<CommandDefinition>();
		List<Element> children = root.getChildren(COMMAND);
		for (Element child : children) {
			commands.add(parseCommand(child));
		}
		return commands;
	}
	
	public static List<CommandDefinition> loadStore() {
		java.io.File storeFile = getStoreLocation().toFile();
		if (!storeFile.exists()) return new ArrayList<CommandDefinition>();
			
		SAXBuilder parser = new SAXBuilder();
		 try {
		
			 Document doc = parser.build(storeFile);
			 Element el = doc.getRootElement();
			 return  parseRootFile( el);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return new ArrayList<CommandDefinition>();
	}
	
	
	public static List<CommandDefinition> getProjectCommands() {
		ArrayList<CommandDefinition> out = new ArrayList<CommandDefinition>();
		List<CommandDefinition> commands = loadStore();
		for (CommandDefinition command : commands) {
			if (command.isProject()) out.add(command);
		}
		
		return out;
	}

	public static List<CommandDefinition> getSuiteCommands() {
		ArrayList<CommandDefinition> out = new ArrayList<CommandDefinition>();
		List<CommandDefinition> commands = loadStore();
		for (CommandDefinition command : commands) {
			if (command.isSuite()) out.add(command);
		}
		
		return out;
	}
	
	
	
	
}
