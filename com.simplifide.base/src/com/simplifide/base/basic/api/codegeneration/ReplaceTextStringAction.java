/*
 * ReplaceTextStringAction.java
 *
 * Created on March 29, 2006, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.codegeneration;

import com.simplifide.base.basic.struct.reference.LocalReference;

/**
 *
 * @author awagner
 */
public class ReplaceTextStringAction extends ReplaceTextAction{
    
    /** Creates a new instance of ReplaceTextStringAction */
    public ReplaceTextStringAction(String name, LocalReference ref, String text)
    {
        super(name,ref,new StringInt(text));
    }

    public static class StringInt implements ReplaceTextInterface
    {
        private String text;
        public StringInt(String text) {this.text = text;}

        public String getText()
        {
            return text;
        }
    }
    
}
