package com.simplifide.core.project.structure;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class WorkspaceDirectoryStructure {

	public static String WORKSPACELOCATIONNAME = "workspaceLocation.xml";
	public static String ELEMENT_BASE = "basedir";
	
	public static String ELEMENT_SUITE = "suite";
	public static String ELEMENT_LIBRARY = "library";
	public static String ELEMENT_PROJECT = "project";
	public static String ELEMENT_SUBPROJECT = "subproject";

	
	
	private SuiteStructureHolder suiteStructure;
	private ProjectStructureHolder projectStructure;
	private LibraryStructureHolder libraryStructure;
	private SubProjectStructureHolder subProjectStructure;
	

	private static Element createElement(StructureBase base) {
		Element el;
		if (base.isDirectory()) el = new Element("dir");
		else el = new Element("file");

		el.setAttribute("name",base.getName());
		
		String linkName = base.getLinkName();
		if (linkName != null && !linkName.equals("")) el.setAttribute("link",linkName);
		
		if (base.isDirectory()) {
			StructureDirectory d = (StructureDirectory) base;
			for (StructureBase child : d.getChildren()) {
				el.addContent(createElement(child));
			}
		}
		
		return el;
	}
	
	private static Element createBaseElement(StructureDirectory struct, String type) {
		Element top = new Element(type);
		Element base = new Element(ELEMENT_BASE);
		for (StructureBase child : struct.getChildren()) {
			base.addContent(createElement(child));
		}
		top.addContent(base);
		return top;
	}

	public static String getFileContents(StructureDirectory suite, StructureDirectory project, 
			StructureDirectory library, StructureDirectory subproject) {
		Element root = new Element("root");  
		Document doc = new Document(root);
		if (suite != null) root.addContent(createBaseElement(suite, ELEMENT_SUITE));
		if (project != null) root.addContent(createBaseElement(project, ELEMENT_PROJECT));
		if (library != null) root.addContent(createBaseElement(library, ELEMENT_LIBRARY));
		if (subproject != null) root.addContent(createBaseElement(subproject, ELEMENT_SUBPROJECT));
		XMLOutputter output = new XMLOutputter();
		output.setFormat(Format.getPrettyFormat());
		
		return output.outputString(doc);
	}
	
	private boolean parseRootFile(Element el) {
		List<Element> children = el.getChildren();
		for (Element child : children) {
			
			Element base = child.getChild(ELEMENT_BASE,child.getNamespace());
			RootStructureDirectory root = RootStructureDirectory.create(base);
			if (child.getName().equalsIgnoreCase(ELEMENT_SUITE)) {
				this.setSuiteStructure(new SuiteStructureHolder(root));
			}
			else if (child.getName().equalsIgnoreCase(ELEMENT_LIBRARY)) {
				this.setLibraryStructure(new LibraryStructureHolder(root));
			}
			else if (child.getName().equalsIgnoreCase(ELEMENT_PROJECT)) {
				this.setProjectStructure(new ProjectStructureHolder(root));
			}
			else if (child.getName().equalsIgnoreCase(ELEMENT_SUBPROJECT)) {
				this.setSubProjectStructure(new SubProjectStructureHolder(root));
			}
		}
		return true;
	}
	
	private boolean parseWorkspaceStructure(File xmlFile) {
		SAXBuilder parser = new SAXBuilder();
		 try {
		
			 Document doc = parser.build(xmlFile);
			 Element el = doc.getRootElement();
			 this.parseRootFile( el);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return true;
			 
	}
	
	public void loadWorkspaceStructure(String data) {
		SAXBuilder parser = new SAXBuilder();
		 try {
		
			 Document doc = parser.build(new StringReader(data));
			 Element el = doc.getRootElement();
			 this.parseRootFile( el);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public boolean loadWorkspaceStructure(File xmlFile) {
		if (xmlFile == null) return false;
		if (!xmlFile.exists()) return false;
		this.parseWorkspaceStructure(xmlFile);
		return true;
	}
	
	
	
	/** Create the directory structure for the workspace or the suite. Search the
	 *  1. Preferences
	 *  2. File Location
	 *  3. Use Default
	 * 
	 * */
	public void createWorkspaceStructure() {
		String suiteDir = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.SUITE_DIR_CONFIG);
		File suiteFile = new File(suiteDir);
		
		boolean succeed = this.loadWorkspaceStructure(suiteFile);
		
		if (!succeed) {
			File outDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
			File outFile = new File(outDir,WORKSPACELOCATIONNAME);
			succeed = this.loadWorkspaceStructure(outFile);
		}
		if (this.getSuiteStructure() == null) {
			RootStructureDirectory dir = this.getSuiteStructure().createDefaultSuiteStructure();
			this.setSuiteStructure(new SuiteStructureHolder(dir));
		}
		if (this.getProjectStructure() == null) {
			RootStructureDirectory dir = this.getProjectStructure().createDefaultSuiteStructure();
			this.setProjectStructure(new ProjectStructureHolder(dir));
		}
		if (this.getLibraryStructure() == null) {
			RootStructureDirectory dir = this.getLibraryStructure().createDefaultSuiteStructure();
			this.setLibraryStructure(new LibraryStructureHolder(dir));
		}
		if (this.getSubProjectStructure() == null) {
			RootStructureDirectory dir = this.getSubProjectStructure().createDefaultSuiteStructure();
			this.setSubProjectStructure(new SubProjectStructureHolder(dir));
		}
	}

	public void loadEmptySuite() {
		if (this.getSuiteStructure() == null) {
			RootStructureDirectory dir = this.getSuiteStructure().createDefaultSuiteStructure();
			this.setSuiteStructure(new SuiteStructureHolder(dir));
		}
	}
	
	public void loadEmptyProject() {
		if (this.getProjectStructure() == null) {
			RootStructureDirectory dir = this.getProjectStructure().createDefaultSuiteStructure();
			this.setProjectStructure(new ProjectStructureHolder(dir));
		}
	}
	
	public void loadEmptyLibrary() {
		if (this.getLibraryStructure() == null) {
			RootStructureDirectory dir = this.getLibraryStructure().createDefaultSuiteStructure();
			this.setLibraryStructure(new LibraryStructureHolder(dir));
		}
	}
	
	public void loadEmptySubProject() {
		if (this.getSubProjectStructure() == null) {
			RootStructureDirectory dir = this.getSubProjectStructure().createDefaultSuiteStructure();
			this.setSubProjectStructure(new SubProjectStructureHolder(dir));
		}
	}
	
	
	public void loadDefaultEmptyDirectories() {
		this.loadEmptySuite();
		this.loadEmptyProject();
		this.loadEmptyLibrary();
		this.loadEmptySubProject();
	}
	
	public void setSuiteStructure(SuiteStructureHolder suiteStructure) {
		this.suiteStructure = suiteStructure;
	}

	public SuiteStructureHolder getSuiteStructure() {
		return suiteStructure;
	}

	public void setProjectStructure(ProjectStructureHolder projectStructure) {
		this.projectStructure = projectStructure;
	}

	public ProjectStructureHolder getProjectStructure() {
		return projectStructure;
	}

	public void setLibraryStructure(LibraryStructureHolder libraryStructure) {
		this.libraryStructure = libraryStructure;
	}

	public LibraryStructureHolder getLibraryStructure() {
		return libraryStructure;
	}

	public void setSubProjectStructure(SubProjectStructureHolder subProjectStructure) {
		this.subProjectStructure = subProjectStructure;
	}

	public SubProjectStructureHolder getSubProjectStructure() {
		return subProjectStructure;
	}
	
	
}
