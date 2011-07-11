package com.simplifide.core.project;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.simplifide.core.HardwareLog;

public class ProjectXmlHandler {
	
	private EclipseBaseProject project;
	private Document document;
	
	public ProjectXmlHandler(EclipseBaseProject project) {
		this.setProject(project);
	}

	public void parseDoc() {
		SAXBuilder parser = new SAXBuilder();
		 try {
			 File ufile = project.getDetailXmlFileLocation();
			 Document doc = parser.build(ufile);
			 this.setDocument(doc);
			 
		} catch (JDOMException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public void setProject(EclipseBaseProject project) {
		this.project = project;
	}

	public EclipseBaseProject getProject() {
		return project;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}
	
}
