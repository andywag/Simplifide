package com.simplifide.base.verilog.parse.base;

import java.util.ArrayList;

import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.project.define.DefineMacro;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.var.realvars.SystemVar;

public class MacroParser {

	

	
	public static DefineObject parseFunction(String name, StringBuilder head) {
		FunctionPortList ports = new FunctionPortList();
		char uchar = head.charAt(0);
		
		int index = 0;
		while (uchar != ')') {
			uchar = head.charAt(0);
			StringBuilder param = new StringBuilder();
			int open = 0;
			while (open != 0 || (uchar != ',' && uchar != ')')) {
				if (uchar == '(') open++;
				if (uchar == ')') open--;
				param.append(uchar);
				head.deleteCharAt(0);
				uchar = head.charAt(0);
			}
			SystemVar tvar = new SystemVar(param.toString());
			PortDefault port = new PortDefault(tvar.createReferenceItem());
			ports.addObject(port.createReferenceItem());
			port.setPortNumber(index);
			if (uchar == ',') head.deleteCharAt(0);
			index++;
		}
		head.deleteCharAt(0);
		FunctionDeclaration dec = new FunctionDeclaration(name,null,ports.createReferenceItem());
		DefineMacro mac = new DefineMacro(name,dec);
		mac.setText(head.toString());
		return mac;
	}
	
	private static DefineObject parseHeader(StringBuilder text) {
		StringBuilder name = new StringBuilder();
		char uc = text.charAt(0);
		while (!Character.isWhitespace(uc) && uc != '(') {
			name.append(uc);
			text.deleteCharAt(0);
			if (text.length() == 0) return new DefineObject(name.toString());
			uc = text.charAt(0);
			
		}
		if (uc == '(') {
			text.deleteCharAt(0);
			return parseFunction(name.toString(), text);
		}
		else {
			DefineObject obj = new DefineObject(name.toString());
			obj.setText(text.toString().trim());
			return obj;
		}
		
	}
	
	public static DefineObject generateMacro(String text) {
		// Replace escaped new line with new lines
		String macro = text.trim().replace("\\n", "\n");
		return parseHeader(new StringBuilder(macro));
		
		
		
	}
	
}
