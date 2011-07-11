package com.simplifide.base.sourcefile.antlr.tok;

import com.simplifide.base.core.project.define.DefineObject;

public class DefineASTToken extends TopASTToken{

	private DefineObject object;
	
	public DefineASTToken(DefineObject object) {
		this.setObject(object);
	}

	public void setObject(DefineObject object) {
		this.object = object;
	}

	public DefineObject getObject() {
		return object;
	}
	
}
