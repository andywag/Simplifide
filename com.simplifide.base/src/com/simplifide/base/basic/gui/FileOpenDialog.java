/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.gui;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 5, 2004
 * Time: 5:50:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileOpenDialog
{

    private JFileChooser choose1;

    public  FileOpenDialog()
    {
        super();
        create();
    }

    private void create()
    {
        JPanel pane1 = new JPanel();
        choose1 = new JFileChooser();
        choose1.setMultiSelectionEnabled(true);
        choose1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        pane1.add(choose1);
        choose1.setVisible(true);
        //DialogDescriptor desc = new DialogDescriptor(pane1,"Add Files");
        //Dialog diag = DialogDisplayer.getDefault().createDialog(desc);
        //diag.setVisible(true);

    }

    public File[] getSelectedFiles()
    {
        return choose1.getSelectedFiles();
    }




}
