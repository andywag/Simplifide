/*
 * TopObjectAction.java
 *
 * Created on July 24, 2005, 10:35 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.basic.newaction;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;



/**
 *
 * @author Andy Wagner
 */
public class TopObjectActionNew extends AbstractAction 
{
    
    
   private JMenuItem menuPresenter;    
   private ActionInformationNew actionInfo;


    /** Creates a new instance of TopObjectAction */
    public TopObjectActionNew() {}
    public TopObjectActionNew(ActionInformationNew act, boolean en)
    {
        this.setActionInfo(act);
        this.setEnabled(en);
        this.putValue(Action.NAME,act.getCommand());
    }
    
    public final void actionPerformed( ActionEvent e ) 
    {
        this.actionInfo.getReference().performAction(e.getActionCommand());
    }
    
    
    public JMenuItem getMenuPresenter () {
        
        if ( menuPresenter == null ) {
            menuPresenter = new JMenuItem(this.actionInfo.getCommand(),null);
            menuPresenter.setAction(this);
        }
        return menuPresenter;        
    }
    
   



    public ActionInformationNew getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInformationNew actionInfo) {
        this.actionInfo = actionInfo;
    }

   


    
}
