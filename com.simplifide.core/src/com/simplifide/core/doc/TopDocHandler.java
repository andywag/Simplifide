/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.doc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.freemarker.TemplateGenerator;

public abstract class TopDocHandler {

    
    public abstract void generateDoc();
    
    protected void createPage(IFile file, String templatepage, Object object) {
        String tstr = TemplateGenerator.generateTemplate(templatepage, object);
        StringBuffer StringBuffer1 = new StringBuffer(tstr);
        try {
            if (file.exists()) file.delete(true, null);
            ByteArrayInputStream Bis1 = new ByteArrayInputStream(StringBuffer1.toString().getBytes("UTF-8"));
           
            file.create(Bis1, IFile.FORCE, null);
            Bis1.close();
        } catch (UnsupportedEncodingException e) {
            HardwareLog.logError(e);
        } catch (CoreException e) {
            HardwareLog.logError(e);
        } catch (IOException e) {
            HardwareLog.logError(e);
        }
    }
    
}
