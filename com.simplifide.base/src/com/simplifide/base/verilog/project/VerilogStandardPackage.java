/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.project;

import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.verilog.core.VerilogSystemTask;

public final  class VerilogStandardPackage extends PackageModule{

	private static final String[] COMMANDS = new String[] {
		"$display","$displayb","$displayh","$displayo",
		"$monitor","$monitorb","$monitorh","$monitoro",
		"$strobe","$strobeb","$strobeh","$strobeo",
		"$write","$writeb","$writeh","$writeo",
		"$fdisplay","$fdisplayb","$fdisplayh","$fdisplayo",
		"$fmonitor","$fmonitorb","$fmonitorh","$fmonitoro",
		"$fstrobe","$fstrobeb","$fstrobeh","$fstrobeo",
		"$fwrite","$fwriteb","$fwriteh","$fwriteo",
	
		"$finish","$stop",
		"$realtime","$time","$stime","$random"
		
	};
	
	private static VerilogStandardPackage instance;
	
	public static VerilogStandardPackage getInstance() {
		if (instance == null) {
			instance = new VerilogStandardPackage();
		}
		return instance;
	}
	
	private VerilogStandardPackage() {
		super();
		init();
	}
	
	private void init() {
		this.createFunctions();
	}
	
	private void createFunctions() {
		for (String command : COMMANDS) {
		    VerilogSystemTask task = new VerilogSystemTask(command);
			this.addReferenceItem(task.createReferenceItem());
		}
	}
	
}
