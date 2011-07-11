/*
 * GoToReferenceAction.java
 *
 * Created on March 23, 2006, 11:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.codegeneration;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.simplifide.base.basic.struct.reference.LocalReference;


/**
 *
 * @author awagner
 */
public class ReplaceTextAction extends AbstractAction
{

    private LocalReference ref;
    private ReplaceTextInterface generator;
    private String prefix = "";
    private String postfix = "";

    /** Creates a new instance of GoToReferenceAction */
 

    public ReplaceTextAction(String name, LocalReference ref) {this(name,ref,null);}

    public ReplaceTextAction(String name, LocalReference ref, ReplaceTextInterface rint)
    {
        this(name,ref,rint,"","");    
    }

    public ReplaceTextAction(String name, LocalReference ref, ReplaceTextInterface gen,
            String prefix, String postfix)
    {
        super(name);
        this.ref = ref;
        this.generator = gen;
        this.prefix = prefix;
        this.postfix = postfix;
        if (ref != null) this.setEnabled(true);
    }


    public void actionPerformed(ActionEvent e)
    {
      
        this.ref.changeText(this.prefix + generator.getText() + this.postfix);
    }

    public ReplaceTextInterface getGenerator() {
        return generator;
    }

    public void setGenerator(ReplaceTextInterface generator) {
        this.generator = generator;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
    
}
