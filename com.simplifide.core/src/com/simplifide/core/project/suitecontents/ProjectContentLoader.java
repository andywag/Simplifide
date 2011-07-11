package com.simplifide.core.project.suitecontents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.structure.StructureFile;



public class ProjectContentLoader {
	

	private static String ELEMENT_ROOT = "root";
	
		
	
	private static ArrayList<FileContentObject> parseContents(File xmlFile) {
		SAXBuilder parser = new SAXBuilder();
		 try {
		
			 Document doc = parser.build(xmlFile);
			 Element el = doc.getRootElement();
			 return TopContentLoader.parseDesignElement(el);
			 
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return new ArrayList<FileContentObject>();
			 
	}
	
	/** Returns the contents of this file whether it is a general list of 
	 *  files or an xml version of files 
	 *  
	 *  TODO Only supports standard text input
	 */
	public static ArrayList<String> getFileContents(File ufile) {
		ArrayList<String> files = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(ufile));
			String str;
			while ((str = in.readLine()) != null) {
				files.add(str.trim());
			}
			in.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);	
		}
		return files;
	}
	
	/** Load the file contents from an xml file describing the project */
	public static ArrayList<FileContentObject> loadContents(File xmlFile) {
		if (xmlFile == null) return new ArrayList<FileContentObject>();
		if (!xmlFile.exists()) return  new ArrayList<FileContentObject>();
		return parseContents(xmlFile);
	}
	
	/** Converts a list of files to the proper xml format for this project */
	public static String convertListToXml(String[] list) {
		Element root = new Element(ELEMENT_ROOT);
		Element des  = new Element(TopContentLoader.ELEMENT_DESIGN);
		root.addContent(des);
		
		for (String str : list) {
			Element file = new Element(TopContentLoader.ELEMENT_FILE);
			file.setAttribute(TopContentLoader.ATTRIBUTE_LOCATION,str.trim());
			des.addContent(file);
		}
				
			
		Document doc = new Document();
		doc.setRootElement(root);
		XMLOutputter output = new XMLOutputter();
		StringWriter fwrite;
		try {
			fwrite = new StringWriter();
			output.setFormat(org.jdom.output.Format.getPrettyFormat());
			output.output(doc, fwrite);
			return fwrite.toString();
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return "";
	}
	
	
	private static String convertTextToXml(String textFile) {
		Element root = new Element(ELEMENT_ROOT);
		Element des  = new Element(TopContentLoader.ELEMENT_DESIGN);
		root.addContent(des);
		
		String[] files = textFile.split("\n");
			
		for (String filea : files) {
			Element file = new Element(TopContentLoader.ELEMENT_FILE);
			file.setAttribute(TopContentLoader.ATTRIBUTE_LOCATION,filea.trim());
			des.addContent(file);
		}
			
		
		Document doc = new Document();
		doc.setRootElement(root);
		XMLOutputter output = new XMLOutputter();
		StringWriter fwrite;
		try {
			fwrite = new StringWriter();
			output.setFormat(org.jdom.output.Format.getPrettyFormat());
			output.output(doc, fwrite);
			return fwrite.toString();
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return "";
		

	}
	
	
	private static String getTextFromFile(File textFile) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(textFile));
			StringBuilder build = new StringBuilder();
			String str;
			while ((str = in.readLine()) != null) {
				build.append(str);
				build.append("\n");
				return build.toString();
			}
			in.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);	
		}
		return "";
	}
	
	public static String convertText(StructureFile textFile) {
		if (textFile.getLinkType() == StructureFile.LINK_NEW) {
			return convertTextToXml(textFile.getContents());
		}
		else {
			String ustr = getTextFromFile(new File(textFile.getLocation()));
			return convertTextToXml(ustr);
		}
	}
	
	public static String convertText(File textFile) {
		Element root = new Element(ELEMENT_ROOT);
		Element des  = new Element(TopContentLoader.ELEMENT_DESIGN);
		root.addContent(des);
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(textFile));
			String str;
			while ((str = in.readLine()) != null) {
				Element file = new Element(TopContentLoader.ELEMENT_FILE);
				file.setAttribute(TopContentLoader.ATTRIBUTE_LOCATION,str.trim());
				des.addContent(file);
			}
			in.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);	
		}
		Document doc = new Document();
		doc.setRootElement(root);
		XMLOutputter output = new XMLOutputter();
		StringWriter fwrite;
		try {
			fwrite = new StringWriter();
			output.setFormat(org.jdom.output.Format.getPrettyFormat());
			output.output(doc, fwrite);
			return fwrite.toString();
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return "";
		

	}
	
	
}
