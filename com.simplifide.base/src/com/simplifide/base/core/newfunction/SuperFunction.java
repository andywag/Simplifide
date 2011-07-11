/*
 * SuperFunction.java
 *
 * Created on March 6, 2007, 5:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 *
 * Enclosing class for all functions. 
 */
public class SuperFunction extends ModuleObjectWithList {
    
	private String classPrefix;
    /** Creates a new instance of SuperFunction */
	
	public void createName(String instring) {
		if (instring.contains("::")) {
			String[] scopeA = instring.split("::");
			String className = scopeA[0];
			String funcName = scopeA[1];
			this.setname(funcName);
			this.setClassPrefix(className);
		}
		else {
			this.setname(instring);
		}

		
	}
	
    public SuperFunction() {}
    public SuperFunction(String name) {
    	this.createName(name);
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_FUNCTION;
    }
    
    public boolean isClassMethod() {
    	if (this.classPrefix != null) return true;
    	return false;
    }
    
	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}
	public String getClassPrefix() {
		return classPrefix;
	}

	
    
}
