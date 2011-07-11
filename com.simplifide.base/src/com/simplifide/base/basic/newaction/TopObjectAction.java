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
public class TopObjectAction extends AbstractAction 
{
      

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String command;
    private String namepattern;
    
   private JMenuItem menuPresenter;    

   private ActionInformation actionInfo;


    /** Creates a new instance of TopObjectAction */
    public TopObjectAction() {}
    public TopObjectAction(ActionInformation act, Object look, boolean en)
    {
        this.actionInfo = act;
       
        this.setEnabled(en);
        this.putValue(Action.NAME,act.getCommand());
    }
    
    public final void actionPerformed( ActionEvent e ) {
  
        //actionPerformed( getLookup(),e.getActionCommand());
    }
    
    protected void actionPerformed( Object context,String command )
    {
    	/*
        Collection ob = context.lookup(new Lookup.Template(TopObjectCookie.class)).allInstances();
        if (ob != null)
        {
            if (ob.size() > 0)
            {
                TopObjectCookie cook = (TopObjectCookie) ob.toArray()[0];
                cook.performAction(command);
            }
        }
        */
        

    }
    
    public JMenuItem getMenuPresenter () {
        
        if ( menuPresenter == null ) {
            menuPresenter = new JMenuItem(this.command,null);
            menuPresenter.setAction(this);
        }
        return menuPresenter;        
    }
    



    

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getNamepattern() {
        return namepattern;
    }

    public void setNamepattern(String namepattern) {
        this.namepattern = namepattern;
    }

    public ActionInformation getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(ActionInformation actionInfo) {
        this.actionInfo = actionInfo;
    }


    
}
