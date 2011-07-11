/*
 * ReferenceLocation.java
 *
 * Created on August 21, 2006, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;



import java.io.Serializable;
import java.net.URI;

import com.simplifide.base.basic.util.StringOps;

/**
 *
 * @author awagner
 */
public class ReferenceLocation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7096908359058196740L;
	private URI uri;
    private int docPosition;
    private int length;
    private int line;
    /** Creates a new instance of ReferenceLocation */
    
      public String toString() {
    	  return StringOps.addParens(this.uri.toString() + "," + getDocPosition());
      }
        
         
        public ReferenceLocation(URI uri, int docPosition, int length, int line) {
        	this.setUri(uri);
        	//this.projectIndex = projectIndex;
            //this.modIndex = modIndex;
            this.docPosition = docPosition;
            this.length = length;
            this.line = line;
        }
        
        public ReferenceLocation copyLocation(int docPosition, int length, int line)
        {
            return new ReferenceLocation(this.getUri(), docPosition, length, line);
        }
        
        
        public boolean isSameFile(ReferenceLocation loc) {
        	return this.getUri().equals(loc.getUri());
        }
        
        public boolean compareLocation(ReferenceLocation loc) {
            if (loc == null) return false;
            if (!loc.getUri().equals(this.getUri())) return false;
            if (loc.getDocPosition() != this.getDocPosition()) return false;
            if (loc.getLength() != this.getLength()) return false;
            return true;
        }
       
        
    
    /**
     * 
     * @return 
     */
    public int getDocPosition() {
        return docPosition;
    }

    /**
     * 
     * @param docPosition 
     */
    public void setDocPosition(int docPosition) {
        this.docPosition = docPosition;
    }

   

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}




	public void setUri(URI uri) {
		this.uri = uri;
	}


	public URI getUri() {
		return uri;
	}
   
}
