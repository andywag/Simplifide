/*
 * ActionInformationNew.java
 *
 * Created on June 27, 2006, 9:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.newaction;

/**
 *
 * @author awagner
 */
public class ActionInformationNew {
    
    public static final int GENERAL    = 0;
    public static final int EXPLORER   = 1;
    public static final int PROPERTIES = 2;
    
    private int commandType;
    private String command;
    private TopObjectActionPerformer reference;
    /** Creates a new instance of ActionInformationNew */
    public ActionInformationNew(String command, TopObjectActionPerformer reference, int commandType) 
    {
        this.command = command;
        this.setReference(reference);
        this.commandType = commandType;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public TopObjectActionPerformer getReference() {
        return reference;
    }

    public void setReference(TopObjectActionPerformer reference) {
        this.reference = reference;
    }

   
    
}
