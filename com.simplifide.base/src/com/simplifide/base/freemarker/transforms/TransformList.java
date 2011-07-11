/*
 * TransformList.java
 *
 * Created on April 15, 2007, 1:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.freemarker.transforms;

import java.util.Map;

import com.simplifide.base.basic.util.StringOps;

import freemarker.template.SimpleNumber;

/**
 *
 * @author Andy
 */
public class TransformList {
    
    /**
     * Class which surrounds the block with ${...}
     */
    public static TransformInterface TEMPLATE = new Template();
    public static TransformInterface MININDENT = new MinIndent();
    /** Creates a new instance of TransformList */
    
    private static class Template implements TransformInterface{
        public Template() {}
        public String transformString(String inval, Map args) {
            return "${" + inval + "}";
        }
    }
    
    private static class MinIndent implements TransformInterface{
        public MinIndent() {}
        public String transformString(String inval, Map args) {
            SimpleNumber number = (SimpleNumber) args.get("value");
            int intValue = number.getAsNumber().intValue();
            String uval = StringOps.trimPreWhiteSpace(inval);
            String outval = StringOps.spaceString(intValue) + uval;
            return outval;
        }
        
    }
    
}
