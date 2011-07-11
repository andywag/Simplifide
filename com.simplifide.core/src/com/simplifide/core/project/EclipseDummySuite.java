/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;

public class EclipseDummySuite extends EclipseSuite{

	private static EclipseDummySuite instance;
	
	private EclipseDummySuite() {}
	
	public static EclipseDummySuite getInstance() {
		if (instance == null) instance = new EclipseDummySuite();
		return instance;
	}
	
}
