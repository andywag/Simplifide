/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.source.design.DesignFile;

/** This class is used to attach information to the source file used
 *  for loading the file. This is used to attach this object to the description
 *  and correspondingly attach the design file to the file. 
 * 
 * @author andy
 * @todo A better description is required
 */
public abstract class SourceDescriber implements ITextContentDescriber{

	public int describe(Reader contents, IContentDescription description) throws IOException {
		if (description != null) {
			description.setProperty(SourceObject.SOURCE_FILE, this);
		}
		return ITextContentDescriber.VALID;
	}

	public int describe(InputStream contents, IContentDescription description) throws IOException {
		if (description != null) {
			description.setProperty(SourceObject.SOURCE_FILE, this);
		}
		return ITextContentDescriber.VALID;
	}

	public QualifiedName[] getSupportedOptions() {
		return new QualifiedName[] {SourceObject.SOURCE_FILE};
	}
	//public abstract DesignFile createDesignFile(IFile file);
	public abstract DesignFile createDesignFile(URI uri);
	
}
