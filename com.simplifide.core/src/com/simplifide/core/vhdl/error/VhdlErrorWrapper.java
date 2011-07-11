/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.error;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.core.error.ErrorWrapper;

public class VhdlErrorWrapper extends ErrorWrapper{

    public VhdlErrorWrapper(TopError error) {
        super(error);
    }

    protected String getPostfix() {
        return ".vhdl";
    }
}
