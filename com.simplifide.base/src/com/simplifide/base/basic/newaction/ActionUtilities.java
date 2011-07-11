/*
 * ActionFactory.java
 *
 * Created on March 23, 2007, 1:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.newaction;

import javax.swing.Action;

/**
 *
 * @author Andy
 */
public class ActionUtilities {
    
    /** Creates a new instance of ActionFactory */
    public ActionUtilities() {}
    
    public static Action[] convertActions(Object[] uobj) {

        Action[] uact = new Action[uobj.length];
        for (int i=0;i<uobj.length;i++)
        {
            if (uobj[i] == null) uact[i] = null; // Handle the 
            if (uobj[i] instanceof Action) uact[i] = (Action) uobj[i];
            if (uobj[i] instanceof ActionInformationNew)
            {
                ActionInformationNew command = (ActionInformationNew) uobj[i];
                if (command.getCommandType() == ActionInformationNew.GENERAL)
                {
                    uact[i] = new TopObjectActionNew(command,true);
                }
                //else if (command.getCommandType() == ActionInformationNew.EXPLORER) uact[i] = SystemAction.get(OpenLocalExplorerAction.class);
                //else if (command.getCommandType() == ActionInformationNew.PROPERTIES) uact[i] = SystemAction.get(PropertiesAction.class);
            }
        }
        return uact;
    }
    
    public static Action[] combineActions(Action[] act1, Action[] act2) {
        Action[] newAct = new Action[act1.length + act2.length];
        for (int i=0;i<act1.length;i++) {
            newAct[i] = act1[i];
        }
        for (int i=0;i<act2.length;i++) {
            newAct[i+act1.length] = act2[i];
        }
        return newAct;
        
    }
    
}
