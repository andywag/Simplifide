/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.error;

import com.simplifide.core.ui.preference.ErrorPreferencePage;

public class VhdlErrorPreferencePage extends ErrorPreferencePage{

    @Override
    protected String getConstantName(String constantName) {
        return constantName + ".vhdl";
    }

}
