/*
 * TimerThread.java
 *
 * Created on December 20, 2005, 11:34 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author awagner
 */
public class TimerThread {
    
    /** Creates a new instance of TimerThread */
    private int time;
    private Timer waitTimer;
    private ThreadRun runItem;
    
    public TimerThread(int time) 
    {
        this.time = time;
        this.initTimer();
        this.runItem = new ThreadRun();
    }
    
    private void initTimer()
    {
        waitTimer = new Timer(this.time, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeActionInAWT();
            }
        });
        waitTimer.setRepeats(false);
    }
    
    public void resetTimer() 
    {
        this.waitTimer.restart();
    }
    
    public void executeActionInAWT() 
    {
        if (!this.runItem.isAlive())
            this.runInAWT(this.runItem);
    }
    
    public void runAction()
    {
        
    }
    
     private void runInAWT(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }
    
    private class ThreadRun extends Thread
    {
        public ThreadRun()
        {
            this.setPriority(Thread.MIN_PRIORITY);
        }
        
        public void run()
        {
            runAction();
        }
    }
    
}
