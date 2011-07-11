/*
 * IDEout.java
 *
 * Created on February 3, 2003, 10:01 PM
 */

package com.simplifide.base.basic.util;



import com.simplifide.base.basic.ReleaseVersion;



/**
 *
 * @author  Andy Wagner
 */



public final class IDEout {

  private static boolean on = true;
  private static String PARSEERROR = "   Parse Error";


    /** Creates a new instance of IDEout */

    public static void printlnDebug(final String inval)
    {
        if (isOn()) printOutput(inval);
    }

    public static void printlnOver(final String inval)
    {
         /*if (isOn()) {
        	 HardwareConsole.writeDebugConsole("debug", inval);
         }*/
            
    }

    public static void printlnDis(final String inval)
    {
        if (isOn()) printOutput(inval);
    }


    public static void printlnAlways(String inval)
    {
        printOutput(inval);
    }

    public static void printlnError(String s)
    {
        printOutput(s);
    }

    public static void printTopError(String tab,int len, Exception e)
    {
       
    }
    
    public static String printCompressedError2(String s, Exception e)
    {
     
        StackTraceElement[] a = e.getStackTrace();
        String out = "";
        for (int i=0;i<10;i++)
        {
            if (i >= a.length) break;
             out += printErrorTab("Parse",PARSEERROR + e.getClass().getName()+ a[i].getClassName() + "(" + a[i].getLineNumber() + ")\n");
             out += e.toString();
        }
       /* if (isOn()) {
        	HardwareConsole.writeDebugConsole("parse", out);
        }*/

        return out;
    }

    public static String printCompressedError(String s, Exception e)
    {
     
        StackTraceElement[] a = e.getStackTrace();
        String out = "";
        out += printErrorTab("Parse",PARSEERROR + e.getClass().getName()+ a[0].getClassName() + "(" + a[0].getLineNumber() + ")");
        out += printErrorTab("Parse",PARSEERROR + e.getClass().getName()+ a[1].getClassName() + "(" + a[1].getLineNumber() + ")");
        out += printErrorTab("Parse",PARSEERROR + e.getClass().getName()+ a[2].getClassName() + "(" + a[2].getLineNumber() + ")");
       
         return out;
    }

   

    public static void printOutput(String inval)
    {
           //if (isOn())
           //   IOProvider.getDefault().getIO("Output",false).getOut().println(inval);
    }
    
    public static void printError(String inval)
    {
          //if (isOn()) IOProvider.getDefault().getIO("Output",false).getErr().println(inval);
    }
    
    public static void printFinder(String inval)
    {
         //if (isOn()) IOProvider.getDefault().getIO("Search",false).getErr().println(inval);
    }
    
    public static void printOutputTab(String tab, String value)
    {
        //if (isOn()) IOProvider.getDefault().getIO(tab,false).getOut().println(value);
    }
    
    public static String printErrorTab(String tab, String value)
    {
    	/*
        if (isOn()) HardwareConsole.writeDebugConsole("parse", value);
        return "";
        */
    	return "";
    }
    
    

    public static void println(String inval)
    {
      //if (isOn())
      //  System.out.println(inval);
    }
    public static void print(String inval)
    {
      //  System.out.print(inval);
    }
   

    public static void enableDebug() {setOn(true);}
    public static void disableDebug() {setOn(false);}

    public static boolean isOn() 
    {
        if (ReleaseVersion.getRelease() == ReleaseVersion.VERSION_RELEASE) return false;
        else return on;
    }

    public static void setOn(boolean aOn) {
        on = aOn;
    }


}
