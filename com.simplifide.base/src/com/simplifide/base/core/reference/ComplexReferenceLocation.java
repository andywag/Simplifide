package com.simplifide.base.core.reference;

import java.net.URI;

/** Reference Location with understanding of how complex nodes meaning 
 *  NameNodes ie a.b.c
 **/
public class ComplexReferenceLocation extends ReferenceLocation {

	private static final long serialVersionUID = 1L;

	public ComplexReferenceLocation(URI uri, int docPosition, int length,int line) {
		super(uri, docPosition, length, line);
	}


}
