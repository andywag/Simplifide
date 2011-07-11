package com.simplifide.core.project.library.har;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipOutputStream;

import com.simplifide.core.HardwareLog;

public class LibraryHarGenerator {

	
	public static void createHarFile(String filename, String baseDir) {
		JarOutputStream zip;
		try {
			
			zip = new JarOutputStream(new FileOutputStream(filename));
			zipDir(new File(baseDir), baseDir, zip);
			zip.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}

	}

	private static String relative( final File base, final File file ) {
	    final int rootLength = base.getAbsolutePath().length();
	    final String absFileName = file.getAbsolutePath();
	    final String relFileName = absFileName.substring(rootLength + 1);
	    return relFileName;
	}

	
	
	public static void zipDir(File base, String dir2zip, ZipOutputStream zos) 
	{ 
	    try 
	   { 
	        //create a new File object based on the directory we have to zip File    
	        File zipDir = new File(dir2zip); 
	        //get a listing of the directory content 
	        String[] dirList = zipDir.list(); 
	        byte[] readBuffer = new byte[2156]; 
	        int bytesIn = 0; 
	        //loop through dirList, and zip the files 
	        for(int i=0; i<dirList.length; i++) 
	        { 
	            File f = new File(zipDir, dirList[i]); 
	        if(f.isDirectory()) 
	        { 
	                //if the File object is a directory, call this 
	                //function again to add its content recursively 
	            zipDir(base,f.getPath(), zos); 
	                //loop again 
	            continue; 
	        } 
	            //if we reached here, the File object f was not a directory 
	            //create a FileInputStream on top of f 
	            FileInputStream fis = new FileInputStream(f); 
	            String filePath = relative(base,new File(f.getPath())); 
	            JarEntry anEntry = new JarEntry(filePath); 
	            //place the zip entry in the ZipOutputStream object 
	            zos.putNextEntry(anEntry); 
	            //now write the content of the file to the ZipOutputStream 
	            while((bytesIn = fis.read(readBuffer)) != -1) 
	            { 
	                zos.write(readBuffer, 0, bytesIn); 
	            } 
	           //close the Stream 
	           fis.close(); 
	    } 
	} 
	catch(Exception e) 
	{ 
	    //handle exception 
	} 
	}
	
}
