/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class GenUtil {
  

  public static int decodeInt(Object in1)
  {
    if (in1 instanceof Integer)
      return ( (Integer) in1).intValue();
    else
      return Integer.parseInt((String)in1);
  }
}
