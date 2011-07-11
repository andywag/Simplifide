/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.error;

import java.util.Hashtable;

import com.simplifide.base.core.error.err.TopError;

public class ErrorHandler {
    
    private static ErrorHandler instance;
    
    private Hashtable<Integer,TopError> errorList;
    private int currentIndex = 0;
    
    public ErrorHandler() {
        errorList = new Hashtable<Integer,TopError>();
    }
    
    public static ErrorHandler getDefault() {
        if (instance == null) instance = new ErrorHandler();
        return instance;
    }
    
    public void clearList() {
        this.errorList.clear();
        this.currentIndex = 0;
    }
    
    public int addItem(TopError error) {
        if (error != null) this.errorList.put(Integer.valueOf(currentIndex), error);
        currentIndex++;
        return currentIndex-1;
    }
    
    public TopError getError(int index) {
        return this.errorList.get(Integer.valueOf(index));
    }
    
    
    
}
