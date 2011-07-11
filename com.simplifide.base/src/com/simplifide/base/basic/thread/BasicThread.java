/*
 * BasicThread.java
 *
 * Created on March 27, 2006, 3:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.thread;

import javax.swing.SwingUtilities;

/**
 *
 * @author awagner
 */
public class BasicThread {

    private BasicThreadInt threadInt;
    /** Creates a new instance of BasicThread */
    public BasicThread(BasicThreadInt threadInt)
    {
        this.threadInt = threadInt;
    }

    public void run()
    {
        UserThread thr = new UserThread();
        this.runInAWT(thr);
    }

    public static void runThread(BasicThreadInt thrInt)
    {
        BasicThread thr = new BasicThread(thrInt);
        thr.run();
    }

     private void runInAWT(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    private class UserThread extends Thread
    {
        public UserThread() {}
        public void run()
        {
            threadInt.runCommand();
        }
    }

    public BasicThreadInt getThreadInt() {
        return threadInt;
    }

    public void setThreadInt(BasicThreadInt threadInt) {
        this.threadInt = threadInt;
    }
    
}
