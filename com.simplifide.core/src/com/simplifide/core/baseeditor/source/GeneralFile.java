package com.simplifide.core.baseeditor.source;

import java.net.URI;

import com.simplifide.core.baseeditor.GeneralEditor;

public abstract class GeneralFile extends SourceFile{

	private GeneralEditor generalEditor;
	private GeneralFileBuilder builder;
	
	public GeneralFile(URI uri) {
		super(uri);
	}
	
	public boolean isAloneFile() {
		return false;
	}
	
	protected abstract GeneralFileBuilder createBuilder();

	
	protected void baseInit() {
		this.builder = this.createBuilder();
	}

	public void setGeneralEditor(GeneralEditor generalEditor) {
		this.generalEditor = generalEditor;
	}

	public GeneralEditor getGeneralEditor() {
		return generalEditor;
	}

	public void setBuilder(GeneralFileBuilder builder) {
		this.builder = builder;
	}

	public GeneralFileBuilder getBuilder() {
		return builder;
	}

}
