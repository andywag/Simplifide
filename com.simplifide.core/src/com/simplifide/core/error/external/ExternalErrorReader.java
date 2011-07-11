/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.core.HardwareLog;

public class ExternalErrorReader {

	
	public static String ELEMENT_BASE = "root";
	public static String ELEMENT_FILE = "file";
	public static String ELEMENT_ERROR = "error";
	
	private static int convertIntValue(String uvalue) {
		return Integer.parseInt(uvalue);
	}
	
	private static ExternalError parseErrorElement(Node node) {
		NamedNodeMap map = node.getAttributes();
		int line = convertIntValue( map.getNamedItem("line").getNodeValue() );
		int col  = convertIntValue( map.getNamedItem("column").getNodeValue() );
		int len  = convertIntValue( map.getNamedItem("length").getNodeValue() );
		int code = convertIntValue( map.getNamedItem("code").getNodeValue() );
		int severity = convertIntValue( map.getNamedItem("severity").getNodeValue() );
		String message = node.getFirstChild().getNodeValue().trim();
		
		NodePosition pos = new NodePosition(-1,len-1,line,col);
		ExternalError error = new ExternalError(pos,message);
		error.setSeverity(severity);
		error.setErrorCode(code);
		return error;
	
		
	}
	
	
	private static ExternalFileError  parseFileElement(Node node) {
		// Calculate the filename
		Node fnode = node.getAttributes().getNamedItem("name");
		String value = fnode.getNodeValue();
		ExternalFileError list = new ExternalFileError(value);
		// Calculate all of the individual errors
		NodeList nlist = ((Element)node).getElementsByTagName(ELEMENT_ERROR);
		for (int i=0;i<nlist.getLength();i++) {
			Node errornode = nlist.item(i);
			ExternalError error = parseErrorElement(errornode);
			list.getErrorList().add(error);
		}
		return list;
	}
	
	private static List<ExternalFileError> parseFileElements(Element element) {
		ArrayList<ExternalFileError> errorList = new ArrayList();
		
		NodeList fileList = element.getElementsByTagName( "file");
		//NodeList fileList = element.getChildNodes();
		for (int i=0;i<fileList.getLength();i++) {
			Node node = fileList.item(i);
			
			//String uname = node.getLocalName() + node.getNodeName() + node.getNodeValue();
			//HardwareLog.logInfo(uname);
			ExternalFileError err = parseFileElement(node);
			errorList.add(err);
		}
		return errorList;
	}
	
	public static List<ExternalFileError> readFile(InputStream file) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			return parseFileElements(doc.getDocumentElement());
		} catch (ParserConfigurationException e) {
			HardwareLog.logError(e);
		} catch (SAXException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return null;
		
	}
	
}
