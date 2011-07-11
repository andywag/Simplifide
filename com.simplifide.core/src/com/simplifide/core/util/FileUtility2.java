package com.simplifide.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.simplifide.core.HardwareLog;

public class FileUtility2 {

	public static void copyFile(File original, File newFile) {
		// if (newFile.exists()) return;

		FileInputStream from;
		FileOutputStream to;

		try {
			from = new FileInputStream(original);
			to = new FileOutputStream(newFile);

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
			from.close();
			to.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		

	}

}
