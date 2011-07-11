/*
 * GoToReferenceAction.java
 *
 * Created on March 23, 2006, 11:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.error.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.simplifide.base.basic.struct.reference.LocalReference;

/**
 *
 * @author awagner
 */
public class GoToReferenceAction extends AbstractAction
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalReference ref;
    /** Creates a new instance of GoToReferenceAction */
    public GoToReferenceAction(String name, LocalReference ref)
    {
        super(name);
        this.ref = ref;
        if (ref != null) this.setEnabled(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        this.ref.goToPosition();
    }
    
}
