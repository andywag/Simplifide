/*
 * FileGenerator.java
 *
 * Created on May 17, 2006, 6:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

import java.io.File;

/**
 *
 * @author awagner
 */
public interface FileGenerator {
    public FileHolder createFileHolder(File fin);
}
