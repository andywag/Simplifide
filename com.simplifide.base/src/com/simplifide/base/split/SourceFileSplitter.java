/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.split;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.simplifide.base.BaseLog;

public abstract class SourceFileSplitter {

	
	public static String SPLIT_EXTENSION = "_split";
	
	private File folder;
	private File inputFile;
	private File contentFile;
	
	private String moduleName;
	private String moduleExtension;
	private String inputFileName;
	
	public SourceFileSplitter(File inputFile) {
		this.inputFile = inputFile;
		File base = inputFile.getParentFile();
		this.createInputFileName();
		this.folder = new File(base,this.inputFileName+SPLIT_EXTENSION);
		
	}
	
	private void createInputFileName() {
		String fname = inputFile.getName();
		String[] ustr = fname.split("\\.");
		this.inputFileName = ustr[0];
	}
	
	
	private void createContentFile() {
		this.contentFile = new File(folder, "content");
	}
	
	private File createTemporaryFile() throws IOException{
		File tempFile = new File(folder,"temp");
		tempFile.createNewFile();
		return tempFile;
	}
	
	private void moveFile(BufferedWriter content, BufferedWriter temp, File tempFile) throws IOException{
		
		content.write(this.getModuleName() + "." + this.getModuleExtension()+"\n");
		temp.close();
		File copyFile = new File(this.folder, this.getModuleName() + "." + this.getModuleExtension());
		tempFile.renameTo(copyFile);
		
		
	}
	
	
	
	private boolean checkNeedToSplit() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.getInputFile()));
			String inline;
			int number = 0;
			while ( (inline = in.readLine()) != null) {
				if (this.checkLine(inline)) number = number + 1;
				if (number >= 2) return true;
			}
		} catch (FileNotFoundException e) {
			BaseLog.logError(e);
		} catch (IOException e) {
			BaseLog.logError(e);
		}
		return false;
	}
	
	public boolean split() {
		if (!this.checkNeedToSplit()) return false;
		File tempFile;
		try {
			this.folder.mkdir();
			tempFile = this.createTemporaryFile();
			this.createContentFile();
			contentFile.createNewFile();
			BufferedWriter contentWriter = new BufferedWriter(new FileWriter(this.contentFile));
			BufferedWriter tempWriter    = new BufferedWriter(new FileWriter(tempFile));
			BufferedReader in = new BufferedReader(new FileReader(this.getInputFile()));
			String inline;
			while ( (inline = in.readLine()) != null) {
				tempWriter.write(inline + "\n");
				if (this.checkLine(inline)) {
					this.moveFile(contentWriter, tempWriter,tempFile);
					tempFile = this.createTemporaryFile();
					tempWriter    = new BufferedWriter(new FileWriter(tempFile));
					
				}
			}
			
			in.close();
			contentWriter.close();
			return true;
		} catch (IOException e) {
			BaseLog.logError(e);
		}
		return false;
		
	}
	
	protected abstract boolean checkLine(String instring);
	
	
	private ArrayList<File> readContentFile() {
		ArrayList<File> ulist = new ArrayList<File>();
		try {
			this.createContentFile();
			BufferedReader in = new BufferedReader(new FileReader(this.contentFile));
			String uline;
			while ( (uline = in.readLine()) != null) {
				ulist.add(new File(this.folder,uline));
			}
			in.close();
		} catch (FileNotFoundException e) {
			BaseLog.logError(e);
		} catch (IOException e) {
			BaseLog.logError(e);
		}
		return ulist;
		
		
	}
	
	public void combine() {
		
		try {
			ArrayList<File> cont = this.readContentFile();
			BufferedWriter tempWriter    = new BufferedWriter(new FileWriter(this.inputFile));
			for (File ufile : cont) {
				BufferedReader tempReader = new BufferedReader(new FileReader(ufile));
				String uline;
				while ( (uline = tempReader.readLine()) != null) {
					tempWriter.write(uline + "\n");
				}
				tempReader.close();
			}
			tempWriter.close();
			
		} catch (IOException e) {
			BaseLog.logError(e);
		}
	}
	
	
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public File getFolder() {
		return folder;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleExtension(String moduleExtension) {
		this.moduleExtension = moduleExtension;
	}

	public String getModuleExtension() {
		return moduleExtension;
	}
	
}
