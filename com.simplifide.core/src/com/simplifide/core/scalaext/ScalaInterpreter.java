package com.simplifide.core.scalaext;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.pythonext.console.TemplateConsole;
import com.simplifide.core.scalaext.context.ScalaDesignContext;
import com.simplifide.core.scalaext.writer.ScalaWriter;
import com.simplifide.core.source.design.DesignFileBuilder;
import com.simplifide.scala2.context.CurrentContext;
import com.simplifide.scala2.top.InterfaceError;
import com.simplifide.scala2.top.InterfaceMessage;
import com.simplifide.scala2.top.InterfaceMessageItem;
import com.simplifide.scala2.top.InterfaceTop;
import com.simplifide.scala2.top.ObjectWriter;

public class ScalaInterpreter {

	 private static List<InterfaceError> getErrorList(InterfaceMessage mess) {
		 ArrayList<InterfaceError> errors = new ArrayList<InterfaceError>();
		 ArrayList<InterfaceMessageItem> messages = mess.getMessages();
		 for (InterfaceMessageItem item : messages) {
			 if (item.isTopError()) {
				 errors.add((InterfaceError)item);
			 }
		 }
		 return errors;
	 }
	
     private static String processMessage(InterfaceMessage mess) {
    	 
    	 ArrayList<InterfaceMessageItem> messages = mess.getMessages();
		 	StringBuilder builder = new StringBuilder();
		 	for (InterfaceMessageItem item : messages) {
		 		if (item.isCode()) {
		 			String code = item.getCode();
		 			if (!code.endsWith("\n")) code = code + "\n"; 
			 	builder.append(code);
		 		}
		    	
		 	}
		 	return builder.toString();
     }
	
     private static void initScala() {
    	 ObjectWriter.setInstance(new ScalaWriter());
     }
     
	 public static String executeVerilog(CurrentContext context, String command, NodePosition pos) {
		 if (HardwareChecker.isScalaEnabled()) {
			 initScala();
			 InterfaceMessage mess = InterfaceTop.createVerilog(context,command);
			 List<InterfaceError> errors = getErrorList(mess);
			 ScalaDesignContext sc = (ScalaDesignContext) context.activeFile();
			 ((DesignFileBuilder)sc.getDesign().getBuilder()).addScalaMarkers(errors,pos);
			 return processMessage(mess);
		 }
		 else {
			 TemplateConsole.getDefault().makeConsoleActive();
		 	 TemplateConsole.getDefault().writeErrorMessage("Not Supported in Free Version");
			 return "";
		 }
		 	
	    }
	    
	    public static String executeVHDL(CurrentContext context, String command, NodePosition pos) {
	    	if (HardwareChecker.isScalaEnabled()) {
	    		initScala();
	    		InterfaceMessage mess = InterfaceTop.createVhdl(context,command);
	    		List<InterfaceError> errors = getErrorList(mess);
	    		ScalaDesignContext sc = (ScalaDesignContext) context.activeFile();
				((DesignFileBuilder)sc.getDesign().getBuilder()).addScalaMarkers(errors,pos);
	    		return processMessage(mess);
	    	}
	    	else {
				 TemplateConsole.getDefault().makeConsoleActive();
			 	 TemplateConsole.getDefault().writeErrorMessage("Not Supported in Free Version");
				 return "";
			 }
	    }
	    
	    private static void executeString(String command) {
	    	
	    }
	    
	   
}


