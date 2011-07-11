package com.simplifide.core.project.suitecontents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.simplifide.core.HardwareLog;



public class SuiteContentLoader {
	

	private static String ELEMENT_ROOT = "suite";
	private static String ELEMENT_LIBRARY = "library";
	private static String ELEMENT_PROJECT = "project";
	private static String ELEMENT_DESIGN = "design";
	
	private static String ATTRIBUTE_NAME = "name";
	private static String ATTRIBUTE_LOCATION = "location";
	private static String ATTRIBUTE_SOURCE_ONLY = "sourceOnly";
	private static String ATTRIBUTE_LINK = "link";
	
	
	/** Create the contents of the xml file --- Called from the wizard and new generator. 
	 *  TODO Should be called on a save */
	public static String createContents(ArrayList<SuiteContentObject> objects) {
		Element root = new Element(ELEMENT_ROOT);
		for (SuiteContentObject obj : objects) {
			Element proj = new Element(ELEMENT_PROJECT);
			if (obj.getType() == SuiteContentObject.LIBRARY) proj = new Element(ELEMENT_LIBRARY);
			proj.setAttribute(ATTRIBUTE_NAME,obj.getName());
			if (obj.getLocation() != null) proj.setAttribute(ATTRIBUTE_LOCATION,obj.getLocation());
			if (obj.isLink()) proj.setAttribute(ATTRIBUTE_LINK,"true");
			if (obj.isSourceOnly()) proj.setAttribute(ATTRIBUTE_SOURCE_ONLY,"true");
			root.addContent(proj);
		}
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat());
		return output.outputString(root);
		
	}
	
	

	/** Loads the suite content object from the xml file */
	private static SuiteContentObject parseProjectElement(Element child) {
		
		String name        = TopContentLoader.checkString(child, ATTRIBUTE_NAME);
		String location    = TopContentLoader.checkString(child, ATTRIBUTE_LOCATION);
		boolean sourceOnly = TopContentLoader.checkBoolean(child, ATTRIBUTE_SOURCE_ONLY);
		boolean link       = TopContentLoader.checkBoolean(child, ATTRIBUTE_LINK);

		SuiteContentObject obj = new SuiteContentObject(name,location);
		obj.setSourceOnly(sourceOnly);
		obj.setLink(link);
		
		// Parse the design element to add the default files
		ArrayList<FileContentObject> files = TopContentLoader.parseDesignElement(child);
		obj.setFiles(files);
		
		return obj;
	}
	
	
	private static ArrayList<SuiteContentObject> parseRootFile(Element el) {
		ArrayList<SuiteContentObject> objects = new ArrayList<SuiteContentObject>();
		List<Element> children = el.getChildren();
		for (Element child : children) {
			SuiteContentObject obj = null;
			if (child.getName().equalsIgnoreCase(ELEMENT_LIBRARY)) {
				obj = parseProjectElement(child);
				obj.setType(SuiteContentObject.LIBRARY);
				objects.add(obj);
			}
			else if (child.getName().equalsIgnoreCase(ELEMENT_PROJECT)) {
				obj = parseProjectElement(child);
				obj.setType(SuiteContentObject.PROJECT);
				objects.add(obj);
			}
			ArrayList<FileContentObject> files = TopContentLoader.parseDesignElement(child);
			if (files.size() > 0) obj.setFiles(files);
		}
		return objects;
	}
	
	/** Main body which returns a list of contents of the file */
	private static ArrayList<SuiteContentObject> parseContents(File xmlFile) {
		SAXBuilder parser = new SAXBuilder();
		 try {
		
			 Document doc = parser.build(xmlFile);
			 Element el = doc.getRootElement();
			 return  parseRootFile( el);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return new ArrayList<SuiteContentObject>();
			 
	}
	
	/** Load the contents of the xml file  base on the file type. 
	 *  Should only be called on suite creation
	 **/
	public static ArrayList<SuiteContentObject> loadContents(File xmlFile) {
		if (xmlFile == null) return new ArrayList<SuiteContentObject>();
		if (!xmlFile.exists()) return  new ArrayList<SuiteContentObject>();
		return parseContents(xmlFile);	
	}
	/** Same as above with an IFile instead of a file */
	public static ArrayList<SuiteContentObject> loadContents(IFile xmlFile) {
		if (xmlFile == null) return new ArrayList<SuiteContentObject>();
		if (!xmlFile.exists()) return  new ArrayList<SuiteContentObject>();
		return parseContents(xmlFile.getLocation().toFile());	
	}
	
}
