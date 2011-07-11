/*
 * TopObjectCookie.java
 *
 * Created on February 1, 2003, 2:10 PM
 */

package com.simplifide.base.basic.support;

/**
 *
 * @author  Andy Wagner
 */
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.simplifide.base.basic.newaction.TopObjectActionPerformer;

public interface TopObjectCookie extends TopObjectActionPerformer
{

    public void addChangeListener(ChangeListener l);
    public void removeChangeListener(ChangeListener l);
    public void fireChange(ChangeEvent e);

    /** Gets the appropriate comparator for the node type */
    public Comparator getDefaultComparator();
    
    /** Returns a valid node or null if the default is to be used */
    public Object getDefaultNode(); 

    public Object[] getActionCommands(boolean context);

    /** Method used to get the basic properties to be displayed in the property window 
     *  @todo : Need to support multiple property windows
     */
    public ArrayList getProperties();
    

    public String getName();
    public String getDisplayName();
    public String getHtmlDisplayName();

    public void deleteObject();
    
    public void performAction(String command);
    
    public Object[] getKeyObject();

    public void clean();
    
    public Image getIcon();
    public Image getOpenedIcon();

    Object[] getActionCommands();


}


