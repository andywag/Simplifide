package com.simplifide.core.project.suitecontents;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;

public class TopContentLoader {
	
	public static final String ELEMENT_FILE = "file";
	public static final String ELEMENT_FOLDER = "folder";
	public static final String ELEMENT_DESIGN = "design";

	public static final String ATTRIBUTE_LOCATION = "location";
	public static final String ATTRIBUTE_LINK     = "link";

	
	public static boolean checkBoolean(Element child, String aname) {
		Attribute attr = child.getAttribute(aname);
		if (attr != null) {
			if (attr.getValue().equalsIgnoreCase("true")) return true;
		}
		return false;
	}
	
	public static String checkString(Element child, String aname) {
		Attribute attr = child.getAttribute(aname);
		if (attr != null) {
			return attr.getValue();
		}
		return "";
	}
	
	public static FileContentObject parseFileElement(Element element) {
		
		String location = checkString(element,ATTRIBUTE_LOCATION);
		boolean link    = checkBoolean(element, ATTRIBUTE_LINK);
		
		if (!location.equalsIgnoreCase("")) {
			FileContentObject obj;
			if (element.getName() == ELEMENT_FILE) {
				 obj = new FileContentObject.File(location,link);
			}
			else {
				obj = new FileContentObject.Folder(location,link);
			}
			return obj;
		}
		return null;
	}
	
	public static ArrayList<FileContentObject> parseDesignElement(Element el) {
		ArrayList<FileContentObject> files = new ArrayList<FileContentObject>();
		Element design = el.getChild(ELEMENT_DESIGN);
		if (design != null) {
			List<Element> elements = design.getChildren();
			for (Element element : elements ) {
				FileContentObject obj = parseFileElement(element);
				files.add(obj);
			}
		}
		return files;
	}
	
}
