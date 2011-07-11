/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.util;

import com.simplifide.base.BaseLog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class TimeUtil {

  private static long start;
  private static long stop;

  private final String name;
  private final long st;
  private long end;

  public TimeUtil(String name1)
  {
    this.name = name1;
    this.st = System.currentTimeMillis();
    BaseLog.logInfo("Start " + name);
    
  }

  public final void close()
  {
    end = System.currentTimeMillis();
    BaseLog.logInfo("Stopped " + name + (end-st));
  }

  public static void startTime(String name)
  {
    start = System.currentTimeMillis();
    BaseLog.logInfo("Start" + name + " " + start);
  }

  public static void stopTimeOver(String name)
  {
    stop = System.currentTimeMillis();
    BaseLog.logInfo("Stop" + name + " " + (stop-start));
  }



}
