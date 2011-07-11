/*
 * TemplateInterpreter.java
 *
 * Created on July 24, 2006, 10:43 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.python.template;

import org.python.util.PythonInterpreter;

import com.simplifide.base.basic.util.IDEout;
import com.simplifide.base.core.reference.ReferenceHandler;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.python.PythonStartup;
import com.simplifide.core.pythonext.basic.PythonSearch;
import com.simplifide.core.pythonext.console.TemplateConsole;

/**
 * Class which is used to write out internal python 
 * commans from source files
 * 
 * @author awagner
 */
public class TemplateInterpreter {
    
    
    /** Creates a new instance of TemplateInterpreter */
    public TemplateInterpreter(ReferenceHandler descriptor) {
        init(descriptor);
    }
    
    private void init( ReferenceHandler descriptor) {
        PythonSearch.getInstance().setReference(descriptor);
    }
   
    
    public void close() {
        //PythonInterpreterHolder.getInstance().executeFile(PythonInterpreterHolder.INITPATH + "CloseWriter.py");
    }
    
    public String executeVerilog(String command) {
    	this.executeString(command);
    	return Writer.getInstance().createVerilog();
    }
    
    public String executeVHDL(String command) {
    	this.executeString(command);
    	return Writer.getInstance().createVHDL();
    }
    
    private void executeString(String command) {
    	Writer.getInstance().clearCode();
        String tstring = "";
        try {
        	PythonInterpreter interp = PythonStartup.getDefault().getInterpreter();
        	command = "import Simplifide.Generator.Writer as Writer\r\n" + command;
        	interp.exec(command);
        } catch (Exception e) {
        	HardwareLog.logError("Template Error",e);
            IDEout.printErrorTab("Python",e.toString());
            TemplateConsole.getDefault().writeErrorMessage(e.toString());
            
            tstring += "";
        }
    }
    
   
    
    
    
    
    
}
