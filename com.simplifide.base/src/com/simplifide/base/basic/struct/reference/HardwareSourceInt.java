/*
 * LocalRefChangeInt.java
 *
 * Created on March 20, 2006, 10:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct.reference;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author awagner
 */ 
public interface HardwareSourceInt extends ListObjectScalarInt
{
    public String getname();
    
    public void changeText(LocalReference ast, String text);
    //public Object getParseContext(int pos); // ParseContext
    public void attachProject(ReferenceItem<CoreProjectSuite> suite, ReferenceItem<CoreProjectBasic> project, ReferenceLocation loc);
    public void build(int pass);
    public void clearText();
    public void removeText(int pos, int length);
    public void addText(int pos, String text);
 
    public Object getSuper();   
    public void removeFolds();
    
    public void expandTemplates();
    public void close();
    
    
    public Object findUsages(Object ref); //ReferenceUsage output, ReferenceItem input

    
}
