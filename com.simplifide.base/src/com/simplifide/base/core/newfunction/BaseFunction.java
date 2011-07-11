/*
 * BaseFunction.java
 *
 * Created on March 6, 2007, 8:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.ClassTypeVar;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author awagner
 */
public class BaseFunction extends SuperFunction{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Holds the function declaration which contains the output type and the ports */
    private ReferenceItem<FunctionDeclaration> declarationRef;
    private ReferenceItem<InstanceFunction> instanceRef;
    
    
    /** Creates a new instance of BaseFunction */
    public BaseFunction() {}
    public BaseFunction(String name) {super(name);}
    public BaseFunction(ReferenceItem<FunctionDeclaration> declRef) {
        if (declRef.getObject().isClassMethod()) {
        	this.setname(declRef.getname());
        	this.setClassPrefix(declRef.getObject().getClassPrefix());
        }
        else {
        	this.setname(declRef.getname());
        }
         this.declarationRef = declRef;
    }
    
    public String getTemplateName() {
        return "function";
    }
    
    public ModuleObject getBaseSearchClass() {
    	if (this.getInstanceRef() != null && this.getInstanceRef().getObject() != null)
    		return this.getInstanceRef().getObject();
    	return this;
    }
    
    public ReferenceItem findBaseReference(ReferenceItem item) {
        ReferenceItem ref = super.findBaseReference(item);
        if (ref == null) {
        	return declarationRef.getObject().findBaseReference(item);
        }
        return ref;
    }
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        List<ReferenceItem> preList = super.findPrefixItemList(item, type);
        preList.addAll(declarationRef.findPrefixItemList(item, type));
        
        return preList;
    }
    
    public TypeVar getOutputType() {
    	if (this.getFunctionDeclaration() != null) {
    		ReferenceItem outRef = this.getFunctionDeclaration().getOutputType();
    		if (outRef != null && outRef.getObject() instanceof ClassEntity) {
    			ClassEntity ent = (ClassEntity) outRef.getObject();
    			ClassTypeVar type = new ClassTypeVar(ent.getInstanceModRef());    			
    			outRef = type.createReferenceItemWithLocation(outRef.getLocation());
    		}
    		if (outRef != null) {
    			return (TypeVar) outRef.getObject();
    		}
    	}
    	return null;
    }
    
    /** Convenience operator to get a list of ports. Used by the template generation */
    public List<PortTop> getPorts() {
    	if (this.declarationRef != null) {
    		return this.declarationRef.getObject().getOrderedList();
    	}
    	return new ArrayList<PortTop>();
    }
    /** Convenience operator to get the function declaration */
    public FunctionDeclaration getFunctionDeclaration() {
    	if (this.declarationRef != null) {
    		return this.declarationRef.getObject();
    	}
    	return null;
    }
    
    public String getExtraGoToInformation() {
    	return this.getDisplayName();
    }
    
    public String getDisplayName() {
        if (this.declarationRef != null) {
            return this.declarationRef.getDisplayName();
        }
        return super.getDisplayName();
    }
    
    public String getHtmlDisplayName() {
        if (this.declarationRef != null) {
            return this.declarationRef.getHtmlDisplayName();
        }
        return null;
    }
    
    public String getHtmlDocName() {
         if (this.declarationRef != null) {
             if (this.declarationRef.getObject() != null) {
                return this.declarationRef.getObject().getHtmlDocName();
             }
        }
        return null;
    }
    
    
    
    /** Method which returns the type of this object. Used for creating type mismatch errors */
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        return this.declarationRef.getObject().getOutputType();
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_FUNCTION_BASE;
    }
    
    
    
    
    public static class Head extends BaseFunction {
        public Head(String name) {}
        
        public Head(ReferenceItem<FunctionDeclaration> decl) {
            super(decl);
        }
        
        public int getSearchType() {
            return ReferenceUtilities.REF_FUNCTION_HEAD;
        }
        
    }
    
    public static class Body extends BaseFunction {
        public Body(String name) {}
        
        public Body(ReferenceItem<FunctionDeclaration> decl) {
            super(decl);
        }
        public int getSearchType() {
            return ReferenceUtilities.REF_FUNCTION_BODY;
        }
    }
    
    
    
    public ReferenceItem<FunctionDeclaration> getDeclarationRef() {
        return declarationRef;
    }
    
    public void setDeclarationRef(ReferenceItem<FunctionDeclaration> declarationRef) {
        this.declarationRef = declarationRef;
    }
	public void setInstanceRef(ReferenceItem<InstanceFunction> instanceRef) {
		this.instanceRef = instanceRef;
	}
	public ReferenceItem<InstanceFunction> getInstanceRef() {
		return instanceRef;
	}

	
    
}
