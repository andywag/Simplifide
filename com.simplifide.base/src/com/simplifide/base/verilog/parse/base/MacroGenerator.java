package com.simplifide.base.verilog.parse.base;

import java.util.List;

import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.define.DefineCall;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class MacroGenerator {

	

	
	
	private static DefineObject getDefineObject(String name, ParseContext context) {
		CoreProjectSuite suite = context.getRefHandler().getGlobalReference().getObject();
		ReferenceItem<DefineObject> objRef = suite.getDefineHolder().findBaseReference(name, ReferenceUtilitiesInterface.REF_MODULEOBJECT );
		if (objRef == null) return null;
		return objRef.getObject();
	}

	
	private static String expandMacro(StringBuilder in, ParseContext context) {
		StringBuilder name = new StringBuilder();
		StringBuilder space = new StringBuilder();
		char uchar = in.charAt(0);
		while (Character.isJavaIdentifierPart(uchar)) {
			name.append(uchar);
			in.deleteCharAt(0);
			uchar = in.charAt(0);
		}
		while (Character.isWhitespace(uchar)) {
			space.append(uchar);
			in.deleteCharAt(0);
			uchar = in.charAt(0);
		}
		if (uchar == '(') {
			in.deleteCharAt(0);
			uchar = in.charAt(0);
			StringBuilder builder = new StringBuilder();
			while (uchar != ')') {
				builder.append(uchar);
				in.deleteCharAt(0);
				uchar = in.charAt(0);
			}
			builder.append(uchar);
			in.deleteCharAt(0);
			DefineObject def = MacroParser.parseFunction(name.toString(), builder);
			CoreProjectSuite suite = context.getRefHandler().getGlobalReference().getObject();
			DefineCall call = suite.getDefineHolder().getDefineCall(def);
			return  parseObject(call,context);
		}
		else {
			DefineObject obj = new DefineObject(name.toString());
			CoreProjectSuite suite = context.getRefHandler().getGlobalReference().getObject();
			DefineCall call = suite.getDefineHolder().getDefineCall(obj);
			return  parseObject(call,context) + space.toString();
		}
		
		
		
	}
	
	/*
	private static String replaceMacro(String in,DefineCall call) {
		String out = in;
		
		for (PortConnect port : ports) {
			out = in.replace(port.getname() + " ", port.getExternVar().getname() + " ");
		}
		
		return out;
	}*/
	
	public static String parseObject(DefineCall call, ParseContext context) {
		String utext = call.getText();
		utext = utext.replace("`\\", "\\"); // Handles escape character
		utext = utext.replace("`\"", "\""); // Handle embedded quotes
		//utext.replace("``", "`");
		StringBuilder in = new StringBuilder(utext);
		StringBuilder builder = new StringBuilder();
		char uchar = in.charAt(0);
		while (in.length() > 0) {
			uchar = in.charAt(0);
			if (uchar == '`') {
				in.deleteCharAt(0);
				uchar = in.charAt(0);
				if (uchar == '`') { // Handle `` case
					builder.append("``");
					
				}
				else { // Standard Case
					builder.append(expandMacro(in,context));
					//builder.append(in.charAt(0));
					in.insert(0, 'T'); // Dummy character to be deleted
				}
			}
			else {
				builder.append(uchar);
			}
			in.deleteCharAt(0);
			
		}
		String ustr =  call.convertString(builder.toString());
		return ustr.replace("``", "");
		
		
	}
	
}
