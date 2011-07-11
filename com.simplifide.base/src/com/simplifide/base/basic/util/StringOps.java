/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.util;

/**
 * <p>Title: Code Generation</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.util.StringTokenizer;

public final class StringOps
{

  private static final String space = "                                                                                              ";
  private static final String newline = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

  public StringOps(){}

  public static String capFirst(String inString)
  {
      String pre = inString.substring(0,1);
      String p = inString.substring(1,inString.length());
      return pre.toUpperCase() + p;
  }

  public static String surround(String inval,String sur)
  {
    return sur + inval + sur;
  }
  public static String surround(String inval,String beg,String end)
  {
    return beg + inval + end;
  }

  public static String addQuote(String inval)
  {
    return '"' + inval + '"';
  }

  public static String addParens(String inval)
  {
      return "(" + inval + ")";
  }

  public static String stringRep(String inval,int len)
  {
     if (len <= 0) return "";
      String outval = "";
    for (int i=0;i<len;i++)
      outval = outval + inval;

    return outval;
  }

  public static String unixFile(String inval)
  {
    StringTokenizer tok1 = new StringTokenizer(inval,"\\");
    String tcode = "";
    int alpha = tok1.countTokens();
    for (int i=0;i<alpha-1;i++)
    {
      tcode += tok1.nextToken() + "/";
    }
    tcode += tok1.nextToken();
    return tcode;
  }

  public static String trimExtension(String inval)
  {
    StringTokenizer tok1 = new StringTokenizer(inval,".");
    return tok1.nextToken();
  }

  public static String rightPad(String ustring, int value)
  {
      int ulen = ustring.length();
      return ustring + spaceString(value-ulen);
  }
  
  public static String htmlSpace(int len) {
      String sp = "&sp;";
      String outst = "";
      for (int i=0;i<len;i++) {
          outst += sp;
      }
      return outst;
  }
  
  public static String trimPreWhiteSpace(String instr) {
        StringBuilder outString = new StringBuilder(instr);
        outString.append('a');
        while(outString.charAt(0) == ' ') {
            outString.deleteCharAt(0);
        }
        return outString.toString().substring(0, outString.length()-1);
  }
  
  public static String spaceString(int i)
  {
    if (i >= 0) return  space.substring(0,i);
    else return "";
  }

  public static String newlineString(int i)
  {
      return newline.substring(0,i);
  }

}
