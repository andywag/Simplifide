/*
 * HdlDoc.java
 *
 * Created on April 13, 2007, 3:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.doc;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectNew;

/**
 *
 * @author Andy
 */
public class HdlDoc extends ModuleObjectNew{
    
    private String description;
    private String author;

   
    private String ret;
    
    private boolean latch;
    
    private List<Param> paramList;
    private List<Param> portList;
    private List<Param> genericList;
    private boolean singleLine = false;
    /** Creates a new instance of HdlDoc */
    public HdlDoc() {super("doc");}
    public HdlDoc(String description) {
    	super("doc");
    	this.addDescription(description);
    }
   
    /** Only copies the description */
    public HdlDoc copy() {
    	HdlDoc udoc = new HdlDoc(this.getDescription());
    	return udoc;
    }
    
    public String getText() {
        //String tstr = TemplateGenerator.generateTempate("hdl_doc", this);
        return "";
    }
   
    /** @todo : This doesn't handle cut off tags in the expression */
    public String getShortDescription() {
    	String shDesc = this.getDescription();
    	if (shDesc.length() < 75) return shDesc;
    	
    	return shDesc.substring(0,75) + "...";
    }
    
    
    
    /**
     * 
     * @param desc 
     */
    public void addDescription(String desc) {
        if (description == null) description = desc;
        else description += "\n" + desc;
    }
    
    /**
     * 
     * @param name 
     * @param text 
     */
   
    public void addParam(String name, String text) {
    	PortParam par = new PortParam(name,text,"");
        if (paramList == null) this.paramList = new ArrayList();
        paramList.add(par);
    }
    
    public void addPort(String name, String text) {
    	this.addPort(name, text,"");
    }
    
    public void addPort(String name, String text, String source) {
    	PortParam par = new PortParam(name,text,source);
        if (this.portList == null) this.portList = new ArrayList();
        portList.add(par);
    }
    
    public void addGeneric(String name, String text) {
    	PortParam par = new PortParam(name,text,"");
        if (this.genericList == null) this.genericList = new ArrayList();
        this.genericList.add(par);
    }
    
   
    
    
     public String getDescription() {
        return description;
    }
     
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Param> getParamList() {
        return paramList;
    }

  
    public void setParamList(List<Param> paramList) {
        this.paramList = paramList;
    }
    
    
     public String getAuthor() {
        return author;
    }

   
    public void setAuthor(String author) {
        this.author = author;
    }

   
    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }
    
      public List<Param> getPortList() {
        return portList;
    }

    public void setPortList(List<Param> portList) {
        this.portList = portList;
    }

    public List<Param> getGenericList() {
        return genericList;
    }

    public void setGenericList(List<Param> genericList) {
        this.genericList = genericList;
    }
    public void setLatch(boolean latch) {
        this.latch = latch;
    }
    public boolean isLatch() {
        return latch;
    }
    
    public void setSingleLine(boolean singleLine) {
		this.singleLine = singleLine;
	}
	public boolean isSingleLine() {
		return singleLine;
	}

	public static class Param {
        private String index;
        private String value;

        public Param(String index, String value) {
            this.index = index;
            this.value = value;
        }
        
         public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    
    public static class PortParam extends Param {
    	private String source;
    	public PortParam(String index, String value, String source) {
    		super(index,value);
    		this.setSource(source);
    	}
		public void setSource(String source) {
			this.source = source;
		}
		public String getSource() {
			return source;
		}
    	
    }
    
}
