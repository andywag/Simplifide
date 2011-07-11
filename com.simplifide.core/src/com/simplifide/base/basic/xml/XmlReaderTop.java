/*
 * XmlWriterTop.java
 *
 * Created on May 17, 2006, 11:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.simplifide.base.BaseLog;

/**
 *
 * @author awagner
 */
public class XmlReaderTop extends XmlObjectTop{


    private Document doc;
    /** Creates a new instance of XmlWriterTop */
    public XmlReaderTop() {}
    public XmlReaderTop(InputStream stream)
    {
        this.createDocument(stream);
    }

    public void createDocument(InputStream stream)
    {
            try {
                this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            } catch (SAXException ex) {
                BaseLog.logError(ex);
            } catch (ParserConfigurationException ex) {
                BaseLog.logError(ex);
            } catch (IOException ex) {
                BaseLog.logError(ex);
            }  
    }

    public Element addElement(String name, Element parent)
    {
        Element el = getDoc().createElement(name);
        parent.appendChild(el);
        return el;
    }

    public void addTextElement(Element parent, String name, String value)
    {
        Element el = this.addElement(name,parent);
        el.appendChild(this.getDoc().createTextNode(value));
        
    }


    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }






    
}
