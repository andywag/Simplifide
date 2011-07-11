/*
 * VhdlIeeeProjectBasic.java
 *
 * Created on September 21, 2006, 4:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.project;

import com.simplifide.base.basic.api.file.FileHolder;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;


/**
 *
 * @author awagner
 */
public class VhdlStandardProjectBasic extends CoreProjectBasic{
    
    /** Creates a new instance of VhdlIeeeProjectBasic */
    public VhdlStandardProjectBasic(FileHolder holder,ReferenceItem<CoreProjectSuite> suite) {super(holder,suite);}
    
    public static VhdlStandardProjectBasic getDefault(ReferenceItem<CoreProjectSuite> suite)
    {
        //FileObject object = Repository.getDefault().getDefaultFileSystem().getRoot().getFileObject("VhdlLibrary/standard");
        //NetBeanFileObjectHolder holder = new NetBeanFileObjectHolder(object);
        return new VhdlStandardProjectBasic(null,suite);
    }
    
}
