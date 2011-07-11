/*
 * IndentTransform.java
 *
 * Created on April 15, 2007, 12:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.freemarker.transforms;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

/**
 *
 * @author Andy
 */
public class GenericTransform implements TemplateTransformModel{
    
    private Writer writer;
    private TransformInterface transform;
    private Map args;
    /** Creates a new instance of IndentTransform 
     * @param transform 
     */
    public GenericTransform(TransformInterface transform) {
        this.transform = transform;
    }

    /**
     * 
     * @param writer 
     * @param args 
     * @return 
     * @throws freemarker.template.TemplateModelException 
     * @throws java.io.IOException 
     */
    public Writer getWriter(Writer writer, Map args) throws TemplateModelException,
                                                          IOException {
        this.writer = writer;
        this.args = args;
        return new Write();

    }

    private class Write extends Writer {

        

        
        public Write() {}
        
        public void write(char[] arg0, int arg1, int arg2) throws IOException {
            String ustr = new String(arg0,arg1,arg2);
            //IDEout.printlnOver("Calling Writer" + arg0 + "-" + arg1 + "-" + arg2);
            String outstr = transform.transformString(ustr, args);
            writer.write(outstr, arg1, outstr.length());
        }

        public void flush() throws IOException {
            writer.flush();
        }

        public void close() throws IOException {

        }
        
    }
    
}
