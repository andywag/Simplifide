package com.simplifide.base.core.project.define;

import java.util.List;
import java.util.regex.Pattern;

import com.simplifide.base.core.port.PortTop;

public class DefineCall {

	private DefineObject obj;
	
	public DefineCall(DefineObject obj) {
		this.setObj(obj);
	}

	public String getText() {
		return getObj().getText();
	}
	
	public String convertString(String input) {
		return input;
	}
	
	public void setObj(DefineObject obj) {
		this.obj = obj;
	}

	public DefineObject getObj() {
		return obj;
	}

	public static class Macro extends DefineCall {
		
		private DefineObject call;
		public Macro(DefineObject obj, DefineObject call) {
			super(obj);
			this.call = call;
		}
		
		public String convertString(String input) {
			String output = input;
			if (getObj() instanceof DefineMacro &&
				call instanceof DefineMacro) {
				DefineMacro params = (DefineMacro) getObj();
				DefineMacro temps = (DefineMacro) call;
				
				
				List<PortTop> tempPorts = temps.getPorts();
				int index = 0;
				for (PortTop port : params.getPorts()) {
					if (index < tempPorts.size()) {
						PortTop temp = tempPorts.get(index);
						String reg = "([\\W])" + port.getname() + "([\\W])";
						Pattern p = Pattern.compile(reg);
						output  = p.matcher(output).replaceAll("$1" + temp.getname() + "$2");
						
						//output = output.replace(reg, temp.getname());
						//output.re
						//output = output.replace(" " + port.getname() + " ", " " + temp.getname() + " ");
					}
					index++;
				}
			}
			return output;

		}
		
	}
	
}
