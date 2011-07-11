/*
 * ActionInformation.java
 *
 * Created on July 26, 2005, 10:46 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.basic.newaction;

import javax.swing.Icon;

/**
 *
 * @author awagner
 */



public class ActionInformation {

    
    public static final int GENERAL = 0;
    public static final int EXPLORE = 1;
    public static final int PROPERTIES = 2;
    
    public static final String OPEN   = "Open";
    public static final String ADD    = "Add";
    public static final String REMOVE = "Remove";
    public static final String DELETE = "Delete";
    public static final String NEW    = "New";
    
    private int commandType;
    private String command;
    private Icon   icon;
    
    public static TopObjectAction getActionInfo(String type, boolean en)
    {
        ActionInformation info;
        if (type.equalsIgnoreCase(OPEN)) info =  new ActionInformation(type,null);
        else if (type.equalsIgnoreCase(ADD)) info =  new ActionInformation(type,null);
        else if (type.equalsIgnoreCase(REMOVE)) info =  new ActionInformation(type,null);
        else if (type.equalsIgnoreCase(DELETE)) info =  new ActionInformation(type,null);
        else info =  new ActionInformation(type,null);
        return new TopObjectAction(info,null,en);
    }
    
    public ActionInformation(String command, Icon icon)
    {
        this.setCommand(command);
        this.setIcon(icon);
    }
    

    

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }
    
   
    
    
    
    
    
}
