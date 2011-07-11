/*
 * XmlWriterTop.java
 *
 * Created on May 17, 2006, 11:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;

import com.simplifide.base.BaseLog;

/**
 *
 * @author awagner
 */
public class XmlWriterTop extends XmlObjectTop{



    /** Creates a new instance of XmlWriterTop */
    public XmlWriterTop() {}
    public XmlWriterTop(String rootElement)
    {
        this.createDocument(rootElement);
    }

    public void createDocument(String rootElement)
    {
        DOMImplementation imp;
        try {
            imp = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
            this.setDoc(imp.createDocument(null,rootElement,null));
        } catch (ParserConfigurationException ex) {
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


   






    
}
