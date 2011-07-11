package com.simplifide.core.python.template;

import java.util.ArrayList;

public class Writer {

	private static Writer instance;
	
	private ArrayList<CodeGenerator> codeList = new ArrayList<CodeGenerator>();
	
	private Writer() {
	}
	
	public static Writer getInstance() {
		if (instance == null) instance = new Writer();
		return instance;
	}
	
	public void appendString(String generator) {
		codeList.add(new StringGen(generator));
	}
	
	public void appendCode(CodeGenerator generator) {
		codeList.add(generator);
	}
	
	public void clearCode() {
		this.codeList.clear();
	}
	
	public String createVerilog() {
		StringBuilder output = new StringBuilder();
		for (CodeGenerator gen : codeList) {
			output.append(gen.writeVerilog());
		}
		return output.toString();
	}
	
	public String createVHDL() {
		StringBuilder output = new StringBuilder();
		for (CodeGenerator gen : codeList) {
			output.append(gen.writeVHDL());
		}
		return output.toString();
	}
	
	public class StringGen implements CodeGenerator {
		
		private String code;
		public StringGen(String code) {
			this.code = code;
		}
		
		public String writeVerilog() {
			return code;
		}
		
		public String writeVHDL() {
			return code;
		}
		
	}
	
	
}