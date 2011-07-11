/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.error;

import com.simplifide.core.ui.preference.ErrorPreferencePage;

public class VerilogErrorPreferencePage extends ErrorPreferencePage{

    @Override
    protected String getConstantName(String constantName) {
        return constantName + ".verilog";
    }

}
