/*
 * BasicFileEvent.java
 *
 * Created on July 13, 2006, 9:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

/**
 *
 * @author awagner
 */
public class BasicFileEvent {
    
    public static final int FOLDERCREATED = 0;
    public static final int FILECREATED = 1;
    public static final int FILEDELETED = 2;
    public static final int FILECHANGED = 3;
    public static final int FILERENAMED = 4;
    
    
    private int type;
    
    private FileHolder holder;
    

    /** Creates a new instance of BasicFileEvent */
    public BasicFileEvent() {}
    public BasicFileEvent(int type, FileHolder holder)
    {
        this.type = type;
        this.holder = holder;
    }
    public BasicFileEvent(String name, String ext)
    {
        this.type = FILERENAMED;
        
    }
    
    public String toString()
    {
        return "File Event(" + decodeType() + ")" + holder;
    }
    
    private String decodeType()
    {
        if (type == FOLDERCREATED) return "Folder Created";
        if (type == FILECREATED) return "File Created";
        if (type == FILECHANGED) return "File Changed";
        if (type == FILERENAMED) return "File Renamed";
        return "Other";
    }
    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FileHolder getHolder() {
        return holder;
    }

    public void setHolder(FileHolder holder) {
        this.holder = holder;
    }
    
}
