package com.simplifide.core.project.structure;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;

public class RootStructureDirectory extends StructureDirectory {

	public static String ELEMENT_ROOT = "root";
	public static String ELEMENT_BASE = "basedir";
	public static String ELEMENT_DIR = "dir";
	public static String ELEMENT_FILE = "file";
	
	public static String ATTRIBUTE_NAME = "name";
	public static String ATTRIBUTE_LINK = "link";
	public static String ATTRIBUTE_ELINK = "elink";
	public static String ATTRIBUTE_DLINK = "dlink";
	public static String ATTRIBUTE_COPY = "copy";

	
	public static String LINK_DESIGN = "design";
	public static String LINK_PROJECT = "projects";
	public static String LINK_LIBRARY = "libraries";
	public static String LINK_SUBPROJECT = "subprojects";
	public static String LINK_TEST = "test";
	public static String LINK_DOCS = "docs";
	public static String LINK_SYNTHESIS = "synthesis";
	public static String LINK_BUILD = "build";
	public static String LINK_ROUTE = "route";
	public static String LINK_SCRIPT = "script";
	
	private HashMap<String,StructureDirectory> linkMap = new HashMap<String,StructureDirectory>();
	
	public RootStructureDirectory() {
		super("root");
	}
	
	public static RootStructureDirectory create(Element element) {
		RootStructureDirectory root = new RootStructureDirectory();
		root.parseBaseElement(root, element);
		return root;
	}
	

	public void reload()  {
		super.reload();
		this.linkMap.clear();
	}
	
	
	protected void handleParse(StructureDirectory dir, String linkName) {
		this.linkMap.put(linkName, dir);
	}
	
	protected void parseDone() {
		
	}
	
	private void handleSingleElement(StructureBase base, Element child ) {
		String elink = child.getAttributeValue(ATTRIBUTE_ELINK, child.getNamespace());
		String dlink = child.getAttributeValue(ATTRIBUTE_DLINK, child.getNamespace());
		String copy = child.getAttributeValue(ATTRIBUTE_COPY, child.getNamespace());
		
		if (elink != null && !elink.equalsIgnoreCase("")) {
			base.setLinkType(StructureBase.LINK_ECLIPSE);
			base.setLocation(elink);
		}
		if (dlink != null && !dlink.equalsIgnoreCase("")) {
			base.setLinkType(StructureBase.LINK_ECLIPSE);
			base.setLocation(dlink);
		}
		if (copy != null && !copy.equalsIgnoreCase("")) {
			base.setLinkType(StructureBase.LINK_ECLIPSE);
			base.setLocation(copy);
		}

	}
	
	private StructureFile parseSingleFileElement(StructureDirectory parent, Element child) {
		String dirName = child.getAttributeValue(ATTRIBUTE_NAME);
		String linkName = child.getAttributeValue(ATTRIBUTE_LINK);
		this.setLinkName(linkName);
		StructureFile dir = new StructureFile(dirName);
		parent.getChildren().add(dir);
		if (linkName != null && !linkName.equalsIgnoreCase("")) {
			dir.setLinkName(linkName);
		}
		this.handleSingleElement(dir, child);
		return dir;
		

	}
	private StructureDirectory parseSingleDirElement(StructureDirectory parent, Element child) {
		String dirName = child.getAttributeValue(ATTRIBUTE_NAME);
		String linkName = child.getAttributeValue(ATTRIBUTE_LINK);
		this.setLinkName(linkName);
		StructureDirectory dir = new StructureDirectory(dirName);
		parent.getChildren().add(dir);
		if (linkName != null && !linkName.equalsIgnoreCase("")) {
			dir.setLinkName(linkName);
		}
		this.handleSingleElement(dir, child);
		return dir;
		

	}
	private void parseBaseElement(StructureDirectory parent, Element el) {
		if (el == null) return; // Don't know why 
		List<Element> elList = el.getChildren("dir",el.getNamespace());
		for (Element child : elList) {
			StructureDirectory dir = this.parseSingleDirElement(parent, child); // Handle the Individual Element
			dir.setParent(parent);
			this.parseBaseElement(dir, child); // Recursively Search other Directories
		}
		List<Element> fileList = el.getChildren("file",el.getNamespace());
		for (Element child : fileList) {
			StructureFile dir = this.parseSingleFileElement(parent, child); // Handle the Individual Element
			dir.setParent(parent);
			//this.parseBaseElement(dir, child); // Recursively Search other Directories
		}
		// Ignore files for the time being but should exist here
	}
	
	
	
	private void parseRootFile(StructureDirectory parent, Element el) {
		List<Element> elList = el.getChildren("dir");
		for (Element child : elList) {
			String dirName = child.getAttributeValue("name");
			String linkName = child.getAttributeValue("link");
			StructureDirectory dir = new StructureDirectory(dirName);
			parent.getChildren().add(dir);
			if (linkName != null && !linkName.equalsIgnoreCase("")) {
				this.handleParse(dir, linkName);
			}
		}
		parseDone();
	}
	
	protected void loadDirectoryStructure(File file) {
		 SAXBuilder parser = new SAXBuilder();
		 try {
			 Document doc = parser.build(file);
			 Element el = doc.getRootElement();
			 this.parseRootFile(this, el);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
	}
	
	/** Convenience Method for adding a child directory */
	protected final StructureDirectory addDefaultChild(String name) {
		return this.addDefaultChild(name,null);
	}
	
	protected final StructureDirectory addDefaultChild(String name, String link) {
		StructureDirectory child = new StructureDirectory(name);
		//this.getChildren().add(child);
		this.addChild(child);
		if (link != null)
			child.setLinkName(link);
		return child;		
	}
	
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected IFolder getStructureFolder(IProject proj, String name) {
		
		String fname = this.getStructureName(name);
		if (fname != null) {
			IFolder folder = proj.getFolder(fname);
			return folder;
		}
		return null;
	}
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected String getStructureName(String name) {
		StructureDirectory dir = this.getLinkMap().get(name);
		if (dir == null) return null;
		else return dir.getName();
	}
	
	/** Convenience method for determing whether to add a directory */
	protected final boolean getUseDir(String pref) {
		return CoreActivator.getDefault().getPreferenceStore().getBoolean(pref);
	}
	
	public void setLinkMap(HashMap<String,StructureDirectory> linkMap) {
		this.linkMap = linkMap;
	}

	public HashMap<String,StructureDirectory> getLinkMap() {
		return linkMap;
	}



	
	
}
