/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.port;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.HdlDoc.Param;
import com.simplifide.base.core.doc.HdlDoc.PortParam;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.CodeSegmentInt;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar.IOVar;
import com.simplifide.base.core.var.types.TypeVar;




/**
 * 
 * @author Andy
 * @param T 
 */
public class PortTop<T extends PortTop> extends ModuleObjectNew<T> implements CodeSegmentInt {


    private ReferenceItem defaultValue;
    /** Classification between generic and regular port */
    private boolean generic;
    /** Reference to the System Var Contained in this port */
    private ReferenceItem<? extends SystemVar> localVarReference;
    /** Number of Port in List */
    private int portNumber;
    
    private String source = "";
     

    public PortTop(){super();}

    /**
     * 
     * @param name 
     */
    public PortTop(String name) {super(name);}
    
    
  
    /** Converts a default port to a connect port */
    public PortConnect convertToConnectPort() {
    	ReferenceItem<SystemVar> varRef = (ReferenceItem<SystemVar>) this.getLocalVarReference();
    	PortConnect connect = new PortConnect(varRef,new ModuleObject(varRef.getname()).createReferenceItem());
    	connect.setDoc(this.getDoc());
    	return connect;
    }
    
    public ModuleObject getRealExternalVar() {
    	if (localVarReference != null)
    		return localVarReference.getObject();
    	else 
    		return new ModuleObject(this.getname());
    }
    
    public String getVhdlTypeName() {
    	if (this.getVariable() != null) {
            OperatingTypeVar tvar = this.getVariable().getOpTypeVar();
            if (tvar instanceof OperatingTypeVar.IOVar) {
            	OperatingTypeVar.IOVar iovar = (OperatingTypeVar.IOVar) tvar;
            	return (String) iovar.getVhdlTypeName();
            }
    	}
    	return "unknown";
    }
    

    public void updateHdlDoc(ModuleObject parent) {
        HdlDoc doc = parent.getDoc();
        if (doc != null) {
            if (doc.getPortList() != null) {
                for (Param param : doc.getPortList()) {
                    if (param.getIndex().equalsIgnoreCase(this.getname())) {
                    	PortParam pparm = (PortParam) param;
                        HdlDoc ndoc = new HdlDoc(param.getValue());
                        this.setDoc(ndoc);
                        this.setSource(pparm.getSource());
                        return;
                    }
                }
            }
            if (doc.getGenericList() != null) {
                for (Param param : doc.getGenericList()) {
                    if (param.getIndex().equalsIgnoreCase(this.getname())) {
                    	PortParam pparm = (PortParam) param;
                        HdlDoc ndoc = new HdlDoc(param.getValue());
                        this.setDoc(ndoc);
                        this.setSource(pparm.getSource());
                        return;
                    }
                }
            }
            if (doc.getParamList() != null) {
                for (Param param : doc.getParamList()) {
                    if (param.getIndex().equalsIgnoreCase(this.getname())) {
                    	PortParam pparm = (PortParam) param;
                        HdlDoc ndoc = new HdlDoc(param.getValue());
                        this.setDoc(ndoc);
                        this.setSource(pparm.getSource());
                        return;
                    }
                }
            }
        }

    }

    public int getWidth() {
    	return this.getLocalVar().getWidth();
    }
    
   
    
    public PortTop(ReferenceItem<? extends SystemVar> localVarReference) {
        super();
        if (localVarReference != null) {
        	this.setname(localVarReference.getname());
        	localVarReference.getObject().setDoc(this.getDoc());
        }
        else setname("Undefined");
        this.localVarReference = localVarReference;
    }

    
    /** Convenience Method to return the variable associated with the port */
    public SystemVar getLocalVar() {
        if (this.localVarReference != null) {
            return this.localVarReference.getObject();
        }
        return null;
    }

    public String getTemplateName() {return "port";}

    public int getSearchType() {
        if (this.getVariable() != null) {
            OperatingTypeVar tvar = this.getVariable().getOpTypeVar();
            int utype = ReferenceUtilities.REF_PORT_TOP;
            if (tvar instanceof OperatingTypeVar.IOVar) {
                OperatingTypeVar.IOVar uvar = (IOVar) tvar;
                if (uvar.getType() == OperatingTypeVar.IOVar.INPUT) utype = ReferenceUtilities.REF_PORT_INPUT;
                else if (uvar.getType() == OperatingTypeVar.IOVar.OUTPUT) utype = ReferenceUtilities.REF_PORT_OUTPUT;
            }
            return utype;
        }
        return ReferenceUtilities.REF_PORT_TOP;
    }

    /**
     * 
     * @return 
     */
    public String getPortTypeName() {
        ReferenceItem<? extends TypeVar> type = this.getTypeReference();
        if (type != null) {
            return type.getDisplayName() + " " + this.getname();
        }
        return this.getname();
    }

    public ReferenceItem<? extends TypeVar> getTypeReference() {
        if (this.localVarReference != null) {
            return this.localVarReference.getObject().getTypeReference();
        }
        return null;
    }

   
    
    /**
     * 
     * @param item 
     * @param type 
     * @return 
     */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.localVarReference != null) {
            return this.localVarReference.findPrefixItemList(item,type);
        }
        return new ArrayList<ReferenceItem>();

    }

    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        if (this.localVarReference != null) {
            return this.localVarReference.findSliceReference(top);
        }
        return null;
    }

    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        if (this.localVarReference != null) {
            return this.localVarReference.findBaseReference(item);
        }
        return null;
    }



    
    /** Create a connection port */

    public String getDisplayName() {
        if (this.getLocalVarReference() != null)
            return this.getLocalVarReference().getDisplayName();
        else return this.getname();
    }



    /**
     * 
     * @return 
     */
    public SystemVar getVariable() {
        if (this.getLocalVarReference() != null) {
            ModuleObject tvar = this.getLocalVarReference().getObject();
            if (tvar instanceof SystemVar) return (SystemVar) tvar;
            return null;
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public TypeVar getType() {
        SystemVar tvar = this.getLocalVarReference().getObject();
        if (tvar != null && tvar.getTypeReference() != null) {
            return tvar.getTypeReference().getObject();
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public Integer getNumericValue() {
        return Integer.valueOf(0);
    }

  




    /**
     * 
     * @return 
     */
    public ReferenceItem<? extends SystemVar> getLocalVarReference() {return this.localVarReference;}
    /**
     * 
     * @param localReference 
     */
    public void setLocalVarReference(ReferenceItem<? extends SystemVar> localReference) {this.localVarReference = localReference;}

    /**
     * 
     * @return 
     */
    public boolean isGeneric() {
        return generic;
    }

    /**
     * 
     * @param generic 
     */
    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    /**
     * 
     * @return 
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * 
     * @param portNumber 
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
    /**
     * 
     * @return 
     */
    public ReferenceItem getDefaultValue() {
        return defaultValue;
    }

    /**
     * 
     * @param defaultValue 
     */
    public void setDefaultValue(ReferenceItem defaultValue) {
        this.defaultValue = defaultValue;
    }

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setDoc(HdlDoc doc) {
		super.setDoc(doc);
		if (this.localVarReference != null) {
			this.localVarReference.getObject().setDoc(doc);
		}
	}


	





}
