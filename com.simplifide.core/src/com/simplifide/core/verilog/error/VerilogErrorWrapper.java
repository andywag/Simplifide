/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.error;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.core.error.ErrorWrapper;

public class VerilogErrorWrapper extends ErrorWrapper{

    public static final String POSTFIX = ".verilog";
    
    public VerilogErrorWrapper(TopError error) {
        super(error);
    }

    protected String getPostfix() {
        return POSTFIX;
    }
    
    
    

}
