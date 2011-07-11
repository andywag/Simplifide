/*
 * CodeGenerationInt.java
 *
 * Created on July 5, 2006, 1:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.codegeneration;

import com.simplifide.base.basic.struct.TopObjectBase;

/**
 *
 * @author awagner
 */
public interface CodeGenerator 
{

    public static int PACKAGEDECLARATION = 0;
    public static int COMPONENTDECLARATION = 3;
    public static int ENTITYDECLARATION = 4;
    public static int INSTANCEDECLARATION = 5;
    public static int SIGNALDECLARATION = 1;
    public static int SENSITIVITYGEN = 2;
    public static int LIBRARYGEN = 6;
    public static int USEGEN = 7;
    
    public static String PACKAGEDEC   = "packagedec";
    public static String COMPONENTDEC = "component";
    public static String GENERICLIST = "genericlist"; 
    public static String PORTLIST = "portlist"; 
    public static String CONTEXTDEC = "contextdec"; 
    public static String INSTANCEDEC = "instancedec";
    public static String SIGNALDEC = "signaldec";
       
    public ReplaceTextInterface getTemplate(TopObjectBase obj, String type);
    
    
}
