/*
 * FunctionDeclaration.java
 *
 * Created on March 19, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy
 */
public class FunctionDeclaration extends SuperFunction  implements TemplateCompletionInterface{
    
	public static final int FUNCTION  = 0;
	public static final int PROCEDURE = 1;
	
    private ReferenceItem<TypeVar> outputType;
    private ReferenceItem<FunctionPortList> portsR;
    
    private int functionType = FUNCTION;
    
    /** Creates a new instance of FunctionDeclaration */
    public FunctionDeclaration() {}
    
 
    public FunctionDeclaration(String name, ReferenceItem<TypeVar> typeRef, ReferenceItem<FunctionPortList> portsR) {
        super(name);
        this.outputType = typeRef;
        this.portsR = portsR;
    }

 
    public String getReturnString() {
    	if (outputType != null) {
    		return outputType.getname();
    	}
    	return null;
    }
    
    public String getVhdlFunctionType() {
    	if (functionType == FUNCTION) return "function";
    	else return "procedure";
    }
    
    
    public void updateHdlDoc(ModuleObject parent) {
    	if (this.portsR != null && this.portsR.getObject() != null)
    		this.portsR.getObject().updateHdlDoc(this);
    }
    
    public ReferenceItem findBaseReference(ReferenceItem item) {
        ReferenceItem ref = super.findBaseReference(item);
        if (ref == null) {
        	return portsR.getObject().findBaseReference(item);
        }
        return ref;
    }
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
    	return portsR.getObject().findPrefixItemList(item, type);
    }
    
    public List<PortTop> getOrderedList() {
    	if (portsR == null || portsR.getObject() == null) {
    		return new ArrayList<PortTop>();
    	}
    	return portsR.getObject().getOrderedPortList();
    }
 
    
    /** Convenience operator to get a list of ports. Used by the template generation */
    public List<PortTop> getPorts() {
    	if (portsR != null) return portsR.getObject().getRealSelfList();
    	return new ArrayList<PortTop>();
    }
    
    public boolean isAutoComplete() {
    	return false;
    }
    
    public String getTemplateDescription() {
		return "";
	}

	public String getTemplateDisplayName() {
		return this.getDisplayName();
	}

	public String getTemplatePattern() {
		if (this.portsR == null || 
			this.portsR.getObject() == null || 
			this.portsR.getObject().getnumber() == 0) {
			return this.getname();
		}
		
		String outPattern = this.getname() + "(";
		boolean first = true;
		PortList<PortTop> ports = this.getPortsR().getObject();
		for (PortTop port : ports.getPorts()) {
			if (!first) outPattern += ", ";
			outPattern += "${" + port.getname() +"}";
			first = false;
		}
		
		outPattern +=  ")";
		return outPattern;
	}
    
     
    
    private String getDecName() {
        String totStr = "";
        if (this.portsR != null) {
        	PortList<PortTop> ports = this.getPortsR().getObject();
            for (PortTop port : ports.getPorts()) {
                String uname = port.getPortTypeName();
                totStr += uname + ",";
            }
            if (totStr.length() > 0) {
                totStr = totStr.substring(0,totStr.length()-1);
            }
        }
        totStr += "";
        /*if (this.getOutputType() != null) {
            totStr += " = " + this.getOutputType().getDisplayName();
        }*/
        return totStr;
    }
    
    public String getDisplayName() {
        
        String totStr = this.getname() + "(" + this.getDecName() + ")";
        return totStr;
    }
    
    public String getHtmlDisplayName() {
        //return null;
        
        return this.getname() + "(<font color='!controlShadow'>" + this.getDecName() + "</font>)"; 
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_FUNCTION_DECLARATION;
    }

    public ReferenceItem<TypeVar> getOutputType() {
        return outputType;
    }

    public void setOutputType(ReferenceItem<TypeVar> outputType) {
        this.outputType = outputType;
    }

    

	public void setPortsR(ReferenceItem<FunctionPortList> portsR) {
		this.portsR = portsR;
	}

	public ReferenceItem<FunctionPortList> getPortsR() {
		return portsR;
	}


	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}


	public int getFunctionType() {
		return functionType;
	}

	
    
}
