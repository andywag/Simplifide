/*
 * LocalReference.java
 *
 * Created on December 30, 2005, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct.reference;

import com.simplifide.base.basic.struct.NodePosition;

/**
 *
 * @author awagner
 */
public class LocalReference extends LocalReferenceTop{
    
    private String library;


    private HardwareSourceInt source; // Interface to Hardware Source  
    private NodePosition position;
    
    /** Creates a new instance of LocalReference */
    public LocalReference() {}
    public LocalReference(NodePosition position) 
    {
        this(null,null,position);
    }
    
    public LocalReference(String library, HardwareSourceInt filename, NodePosition position)
    {
        this.library = library;
        this.source = filename;
        this.setPosition(position);
    }
    

    /** @deprecated : Changed the way locations are stored for memory usage */
    public void goToPosition()
    {
       
    }

    public void changeText(String text)
    {
        //this.getSource().changeText(this,text);
    }

    /*
    public Object getParseContext()
    {
        return this.getSource().getParseContext(this.getPosition().getStartPos());
    }
     */
    

    public String toErrorString()
    {
        return "Not Defined Anymore";
       // return this.source.getname() + StringOps.addParens(this.getPosition().toString());
    }
    



    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getFilename() {
        return this.source.getname();
    }

  

    public NodePosition getPosition() {
        return position;
    }

    public void setPosition(NodePosition position) {
        this.position = position;
    }

    public HardwareSourceInt getSource() {
        return source;
    }

    public void setSource(HardwareSourceInt source) {
        this.source = source;
    }
    
    
}
