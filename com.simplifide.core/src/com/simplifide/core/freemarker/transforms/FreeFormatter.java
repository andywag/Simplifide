/*
 * FreeFormatter.java
 *
 * Created on April 16, 2007, 1:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker.transforms;

import com.simplifide.base.basic.util.StringOps;

/**
 *
 * @author Andy
 */
public class FreeFormatter {
    
    private int line=0;
    private int col=0;
    private int index=0;
    private StringBuilder builder;
    /** Creates a new instance of FreeFormatter */
    public FreeFormatter(String instring) {
        this.builder = new StringBuilder(instring);
        this.format();
    }
    
    private void resolveIndent() {
        int nindex = index + 4;
        while (nindex < builder.length()) {
            char uval = builder.charAt(nindex);
            if (uval == ']') break;
            nindex++;
        }
        
        int newindvalue = Integer.valueOf(builder.substring(index+4,nindex));
        int addSpace = newindvalue - col;
        if (addSpace > 0) {
            
            builder.replace(index, nindex+4, StringOps.spaceString(addSpace));
            col = newindvalue;
            index += addSpace;
        }
        else {
            builder.delete(index, nindex+4);
        }
        
    }
    
    private void format() {
        while (index < builder.length()) {
            
            
            char uval = builder.charAt(index);
            String compStr = "";
            if (builder.length() >= index + 4) {
                compStr = builder.substring(index, index+4);
            }
            if (compStr.equalsIgnoreCase("[[[[")) {
                this.resolveIndent();
            } else if (builder.charAt(index) == '\n') {
                line++;
                col = 0;
                index++;
            } else {
                col++;
                index++;
            }
            
        }
    }
    
    public String getString() {
        return this.builder.toString();
    }
    
}
