/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.realvars;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.doc.HdlDoc.Param;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.CodeSegmentInt;
import com.simplifide.base.core.var.types.TypeVar;

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:33:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SystemVar extends ModuleObjectNew implements CodeSegmentInt {
    
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int INOUT = 2;
    public static final int SIGNAL = 3;
    public static final int VARIABLE = 4;
    public static final int BUFFER = 5;
    public static final int CONSTANT = 6;
    public static final int LOCAL_CONSTANT = 7;
    public static final int REG = 8;
    public static final int WIRE = 9;
    public static final int PARAMETER = 10;
	
    
    private ReferenceLocation declarationLocation;
    
    private OperatingTypeVar opTypeVar;
    private ReferenceItem<? extends TypeVar> typeReference;
    private ReferenceItem defaultValue;
    private String defaultString;
    
    private boolean generic;
    
    private boolean used;
    private boolean assigned;
    
    public SystemVar() {}
    public SystemVar(String name) {super(name);}
    public SystemVar(String name, ReferenceItem type, OperatingTypeVar op) {
        super(name);
        this.typeReference = type;
        this.opTypeVar = op;
      
    }
    
    /** Returns what type of variable this is */
    public int getVariableType() {
    	if (this.opTypeVar == null) return SystemVar.VARIABLE;
    	else return this.opTypeVar.getVariableType();
    }
    
   
    public boolean isInput() {
    	if (this.opTypeVar instanceof OperatingTypeVar.IOVar) {
    		OperatingTypeVar.IOVar io = (OperatingTypeVar.IOVar)this.opTypeVar;
    		if (io.isInput()) return true;
    	}
    	return false;
    }
    
    
    /** Convenience Method describing this method as an I/O variable */
    public boolean isInputorOutput() {
    	if (this.opTypeVar instanceof OperatingTypeVar.IOVar) return true;
    	return false;
    }
    
    /** Convenience Method to return the default value */
     public ModuleObject getDefault() {
    	if (this.defaultValue != null) {
    		return this.defaultValue.getObject();
    	}
    	return null;
    }
    
    /** Convenience Method to return the type */
    public TypeVar getType() {
        if (typeReference != null) {
            return this.typeReference.getObject();
        }
        return null;
    }
     
   
    
    @Override
	public void updateHdlDoc(ModuleObject parent) {
    	
		HdlDoc doc = parent.getDoc();
    	if (doc != null) {
			if (doc.getParamList() != null) {
				for (Param param : doc.getParamList()) {
					if (param.getIndex().equalsIgnoreCase(this.getname())) {
						HdlDoc ndoc = new HdlDoc(param.getValue());
						this.setDoc(ndoc);
					}
				}
			}
		}
	}
	
    public String createVerilogWireDeclaration() {
    	if (this.getWidth() == 0) return "\r\nwire " + this.getname() + ";";
    	return "\r\nwire [" + this.getWidth() + ":0] " + this.getname() + ";";
    }
    
    /*@deprecated */
    public String createVhdlWireDeclaration() {
    	return "signal " + this.getname() + " : " + this.getType().createVhdlWireDeclaration() + ";";
    }
	
    public String createRegDeclaration(String uname) {
    	return "signal " + uname + " : " + this.getType().createVhdlWireDeclaration() + ";";
    }
    
    public String createWireDeclaration(String uname) {
    	return "signal " + uname + " : " + this.getType().createVhdlWireDeclaration() + ";";
    }
    
    public String createInputDeclaration(String uname) {
    	return uname + " : in " + this.getType().createVhdlWireDeclaration();
    }
    
    public String createOutputDeclaration(String uname) {
    	return uname + " : out " + this.getType().createVhdlWireDeclaration();
    }
    
    public String getTemplateName() {
        return "variable";
    }
    
    public int getSearchType() {
        int utype = ReferenceUtilities.REF_SYSTEMVAR;
        OperatingTypeVar type = this.getOpTypeVar();
        if (type instanceof OperatingTypeVar.ConstantVar)
            utype = ReferenceUtilities.REF_CONSTANT;
        else if (type instanceof OperatingTypeVar.SignalVar || type instanceof OperatingTypeVar.IOVar)
            utype = ReferenceUtilities.REF_SIGNAL;
        else if (type instanceof OperatingTypeVar.VariableVar)
            utype = ReferenceUtilities.REF_VARIABLE;
        return utype;
    }
    
    /** Convenience Mtehod used to get the width of the variable */
    public int getWidth() {
    	return this.getType().getWidth();
    }
    
    
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        if (this.typeReference != null) {
            if (this.typeReference.getObject() != null) {
                return this.typeReference.getObject().findBaseVarRef(item);
            }
        }
        return null;
    }
    
     public ReferenceItem findSliceReference(ModuleObjectIndexTop item) {
         if (this.typeReference != null) {
            if (this.typeReference.getObject() != null) {
                return this.typeReference.getObject().findSliceVarRef(item,this);
            }
        }
        return null;
    }
    
    public List<? extends ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.typeReference != null) {
            if (this.typeReference.getObject() != null) {
                return this.typeReference.getObject().findVarPrefixItemList(item,type);
            }
        }
        return new ArrayList<ReferenceItem>();
    }

    public List<? extends ReferenceItem> findParenItemList(ModuleObjectFindItem item, int type) {
        if (this.typeReference != null) {
            if (this.typeReference.getObject() != null) {
                return this.typeReference.getObject().findVarParenItemList(item,type);
            }
        }
        return new ArrayList<ReferenceItem>();
    }

    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        if (this.getSearchType() == ReferenceUtilities.REF_SIGNAL) {
            return ModuleObjectWithList.singleton(this).createReferenceItem();
        }
        return super.getOutputs();
    }

    public ReferenceItem<ModuleObjectWithList> getDependants() {
        //if (this.getSearchType() == ReferenceUtilities.REF_SIGNAL) {
            return ModuleObjectWithList.singleton(this).createReferenceItem();
        //}
        //return super.getDependants();
    }
    
    
    public boolean isCompleteItem() {return true;}
    
   
    
    
    
    
  
    
  
    
    public Integer getNumericValue() {
        if (this.getOpTypeVar() instanceof OperatingTypeVar.ConstantVar)
            return ((OperatingTypeVar.ConstantVar)this.getOpTypeVar()).getValue().getNumericValue();
        return null;
    }
    
    public static class IOComparator implements Comparator<ModuleObjectNew> {

		
		public int compare(ModuleObjectNew arg0, ModuleObjectNew arg1) {
			if (arg1 instanceof SystemVar && arg1 instanceof SystemVar) {
				int ty = ((SystemVar)arg0).getVariableType() - ((SystemVar)arg1).getVariableType();
				if (ty != 0) return ty;
			}
			
			return arg0.getname().compareTo(arg1.getname());
		}
    
    }
   
    
    
    
    public OperatingTypeVar getOpTypeVar() {
        return opTypeVar;
    }
    
    public void setOpTypeVar(OperatingTypeVar opTypeVar) {
        this.opTypeVar = opTypeVar;
    }
    
    
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        return typeReference;
    }
    
    public void setTypeReference(ReferenceItem<? extends TypeVar> typeReference) {
        this.typeReference = typeReference;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public boolean isUsed() {
        return used;
    }
    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
    public boolean isAssigned() {
        return assigned;
    }
	public void setDefaultValue(ReferenceItem defaultValue) {
		this.defaultValue = defaultValue;
	}
	public ReferenceItem getDefaultValue() {
		return defaultValue;
	}
	public void setGeneric(boolean generic) {
		this.generic = generic;
	}
	 /** Convenience Method to determine if this variable is a generic */
    public boolean isGeneric() {
    	return this.generic;
    }
	public void setDeclarationLocation(ReferenceLocation declarationLocation) {
		this.declarationLocation = declarationLocation;
	}
	public ReferenceLocation getDeclarationLocation() {
		return declarationLocation;
	}
	public void setDefaultString(String defaultString) {
		this.defaultString = defaultString;
	}
	public String getDefaultString() {
		return defaultString;
	}
    
    

   
    
    
}
