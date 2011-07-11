package com.simplifide.core.baseeditor.source;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.runtime.IProgressMonitor;


public abstract class GeneralFileBuilder {

	private GeneralFile generalFile;
	
	public GeneralFileBuilder(GeneralFile general) {
		this.generalFile = general;
	}
	
	public abstract void build(int kind);
	public abstract void build(int kind,Reader reader);
	public abstract void build(int kind,IProgressMonitor monitor);
	
	public void build(int kind, String contents) {
		StringReader reader = new StringReader(contents);
		this.build(kind,reader);
	}
	public abstract void expandTemplates();

	public void setGeneralFile(GeneralFile generalFile) {
		this.generalFile = generalFile;
	}

	public GeneralFile getGeneralFile() {
		return generalFile;
	}
	
}
