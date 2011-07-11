/*
 * CodeGeneratorUtil.java
 *
 * Created on July 5, 2006, 1:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.codegeneration;

/**
 *
 * @author awagner
 */
public class CodeGeneratorUtil {
    
    private CodeGenerator generator;
    private static CodeGeneratorUtil instance;
    /** Creates a new instance of CodeGeneratorUtil */
    public CodeGeneratorUtil() {}
    
    public static CodeGeneratorUtil getDefault()
    {
        if (instance == null) instance = new CodeGeneratorUtil();
        return instance;
    }

    public CodeGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(CodeGenerator generator) {
        this.generator = generator;
    }
    
}
