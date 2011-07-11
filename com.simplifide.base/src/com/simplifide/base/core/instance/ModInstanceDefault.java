/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.HdlDoc.Param;
import com.simplifide.base.core.general.StringObject;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;

public class ModInstanceDefault<T extends PortDefault> extends ModInstanceTop<T> implements java.io.Serializable {
    
   
	private static final long serialVersionUID = 1L;
	protected static  final int HTML_TOP_INDENT = 6;
    protected static final int HTML_PORT_INDENT = 14;
    
    /** Pointer to the instance module parent */
    private ReferenceItem<InstanceModule> instanceModRef;
   
    
    
    public ModInstanceDefault(){super();}
    public ModInstanceDefault(String moduleName) {
        super(moduleName);
        this.setModuleName(moduleName);
    }
    public ModInstanceDefault(String moduleName, ReferenceItem<PortList> generic, ReferenceItem<PortList> port) {
        super(moduleName);
        this.setGenericListRef(generic);
        this.setPortListRef(port);
    }
    
    /** Create an initial instance for this module */
    public ModInstanceConnect createDefaultConnection(EntityHolder entity) {
    		
    	
    	ModInstanceConnect connect = new ModInstanceConnect("i" + entity.getname(),entity.createReferenceItem());
    	
    	if (this.getGenericListRef() != null) {
    		PortList generics = this.getGenericListRef().getObject();
        	connect.setGenericListRef(generics.copyWithConnectPorts().createReferenceItem());
    	}
    	else {
    		connect.setGenericListRef(new PortList("generics").createReferenceItem());
    	}
    	
    	if (this.getPortListRef() != null) {
    		PortList ports = this.getPortListRef().getObject();
        	connect.setPortListRef(ports.copyWithConnectPorts().createReferenceItem());
    	}
    	else {
    		connect.setPortListRef(new PortList("ports").createReferenceItem());
    	}
    	
    	
    	return connect;
    }
    
    public void updateHdlDoc(ModuleObject parent) {
    	if (this.getPortListRef() != null) this.getPortListRef().getObject().updateHdlDoc(parent);
    	if (this.getGenericListRef() != null) this.getGenericListRef().getObject().updateHdlDoc(parent);
    }
    
    private String getPortTemplatePattern(String pname, PortList<PortTop> list) {
        if (list.getnumber() == 0) return "";
        String out = StringOps.spaceString(HTML_TOP_INDENT) + pname + "map (";
        
        boolean first = true;
        for (PortTop port : list.getInputOutputOrderedList()) {
            
            if (!first) out += ",\n" + StringOps.spaceString(HTML_PORT_INDENT+4);
            out += port.getname();
            String eqInd = StringOps.spaceString(32-port.getname().length());
            out +=  eqInd + "=> ${" + port.getname() + "}";
            first = false;
        }
        out += ");\n";
        return out;
        
    }
    
    public String getTemplatePattern() {
    	String outType = "${i" + this.getname() + "} : " + this.getname() + "\n";
    	if (this.getGenericListRef() != null)
            outType += this.getPortTemplatePattern("generic ", this.getGenericListRef().getObject());
        if (this.getPortListRef() != null)
            outType += this.getPortTemplatePattern("port   ", this.getPortListRef().getObject());
		return outType;
	}
    
    private String getPortHtml(String pname, PortList<PortTop> list) {
        if (list.getnumber() == 0) return "";
        String out = StringOps.spaceString(HTML_TOP_INDENT) + pname;
        out += "(";
        boolean first = true;
        for (ReferenceItem<? extends PortTop> pitem : list.getGenericSelfList()) {
            PortTop port = pitem.getObject();
            if (!first) out += ",\n" + StringOps.spaceString(HTML_PORT_INDENT);
            out += "<i>" + port.getDisplayName() + "</i>";
            first = false;
        }
        out += ");\n";
        return out;
        
    }
    
    
    public void attachDocument(HdlDoc doc) {
        List<Param> portDocList = doc.getPortList();
        List<Param> genericDocList = doc.getGenericList();
        
        if (this.getPortListRef() != null) {
            this.getPortListRef().getObject();
        }
    }
    
     public String getHtmlDocName() {
        String outType = "";
        if (this.getGenericListRef() != null)
            outType += this.getPortHtml("generic", this.getGenericListRef().getObject());
        if (this.getPortListRef() != null)
            outType += this.getPortHtml("port   ", this.getPortListRef().getObject());
       
        return outType ;
     }
    
     private String tempSurround(String name) {
         return "${" + name + " }";
     }
     
      private String getPortTemplate(String pname, PortList<PortTop> list) {
        if (list.getnumber() == 0) return "";
        String out = StringOps.spaceString(HTML_TOP_INDENT) + pname;
        out += "(";
        boolean first = true;
        for (ReferenceItem<? extends PortTop> pitem : list.getGenericSelfList()) {
            PortTop port = pitem.getObject();
            if (!first) out += ",\n" + StringOps.spaceString(HTML_PORT_INDENT);
            String hint = "";
            if (port.getDefaultValue() != null) {
                // Embarrasing Hack
                String outName = port.getDefaultValue().getObject().getname();
                if (port.getDefaultValue().getObject() instanceof StringObject) {
                    hint = "default = " + outName.substring(0,1) +  "&&&&&" + outName.substring(1,outName.length()-1)+
                            "&&&&&" + outName.substring(outName.length()-1,outName.length());
                }
                else {
                    hint = "default = " +  outName;
                }

            }
            out +=  port.getname() + " => " + this.tempSurround(port.getname() + " " + hint + " ");
            first = false;
        }
        out += ")";
        return out;
        
    }
    
     
     
     
    
    
    public ReferenceItem<InstanceModule> getInstanceModRef() {return this.instanceModRef;}
    public void setInstanceModRef(ReferenceItem<InstanceModule> ref) {this.instanceModRef = ref;}

   

    
	
    
    
}
