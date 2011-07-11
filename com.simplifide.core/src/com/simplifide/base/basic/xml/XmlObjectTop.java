/*
 * XmlObjectTop.java
 *
 * Created on May 17, 2006, 11:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.xml;

import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.api.file.FileHolder;

/**
 *
 * @author awagner
 */
public class XmlObjectTop {

    private Document doc;
    /** Creates a new instance of XmlObjectTop */
    public XmlObjectTop() {}


    public void writeDocument(FileHolder holder)
    {
         OutputStream stream = holder.getOutputStream();
         TransformerFactory tranFactory = TransformerFactory.newInstance();
         try {
            Transformer trans = tranFactory.newTransformer();
            //trans.setOutputProperty(OutputKeys.INDENT, "yes");
            Source source  = new DOMSource(this.getDoc());
            Result result  = new StreamResult(stream);
            trans.transform(source,result);
            }
          catch (TransformerConfigurationException ex) {
            BaseLog.logError(ex);
         }
        catch (TransformerException ex) {
            BaseLog.logError(ex);
        }   

         holder.closeOutputStream();
        
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    
}
