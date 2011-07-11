package com.simplifide.base.basic.struct;

public class ModuleObjectWithListSearchType extends ModuleObjectWithList{

	private int searchType;
	
	public ModuleObjectWithListSearchType(String name, int stype) {
		super(name);
	}
	
	public int getSearchType() {
		return searchType;
	}
	
}
