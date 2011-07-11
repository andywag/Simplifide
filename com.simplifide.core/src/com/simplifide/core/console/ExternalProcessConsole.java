package com.simplifide.core.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.pythonext.console.BuildConsole;

public class ExternalProcessConsole {

	public static ArrayList runCommandNew(String[] command, boolean wait, File location) {
		ExternalProcessConsole process = new ExternalProcessConsole();
		BuildConsole.getDefault().makeConsoleActive();
		BuildConsole.getDefault().writeInputMessage(command + "\n");
		try {
			ArrayList lis =  process.runCommandInternalNew(command,wait,location);
			return lis;
		}
		catch (IOException ex) {
			BuildConsole.getDefault().writeErrorMessage(ex.getMessage() + "\n");
		}
		return null;
		
	}
	
	private ArrayList runCommandInternalNew(String[] command,boolean wait, File location) throws IOException {
	
		OutputMessageList messageList = new OutputMessageList();
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			//builder.redirectErrorStream(true);
			builder.directory(location);
			//Map map = builder.environment();
			Process pr = builder.start();
			//Process pr = Runtime.getRuntime().exec(command);

			InputReader inRead = new ExternalProcessConsole.InputReader(pr.getInputStream(),messageList);
			ErrorReader errRead = new ExternalProcessConsole.ErrorReader(pr.getErrorStream(),messageList);
			
			inRead.start();
			errRead.start();
			if (wait) {
				int val = pr.waitFor();
			}
			
		/*} catch (IOException e) {
			HardwareLog.logError(e);*/
		} catch (InterruptedException e) {
			HardwareLog.logError(e);
		}
	
		return messageList.messageList;
	}
	
	
	public static ArrayList runCommand(String command, boolean wait) {
		ExternalProcessConsole process = new ExternalProcessConsole();
		BuildConsole.getDefault().makeConsoleActive();
		BuildConsole.getDefault().writeInputMessage(command + "\n");
		try {
			ArrayList lis =  process.runCommandInternal(command,wait);
			return lis;
		}
		catch (IOException ex) {
			BuildConsole.getDefault().writeErrorMessage(ex.getMessage() + "\n");
		}
		return null;
		
	}
	
	private ArrayList runCommandInternal(String command,boolean wait) throws IOException {
	
		OutputMessageList messageList = new OutputMessageList();
		try {
			Process pr = Runtime.getRuntime().exec(command);

			InputReader inRead = new ExternalProcessConsole.InputReader(pr.getInputStream(),messageList);
			ErrorReader errRead = new ExternalProcessConsole.ErrorReader(pr.getErrorStream(),messageList);
			
			inRead.start();
			errRead.start();
			if (wait) {
				int val = pr.waitFor();
			}
			
		/*} catch (IOException e) {
			HardwareLog.logError(e);*/
		} catch (InterruptedException e) {
			HardwareLog.logError(e);
		}
	
		return messageList.messageList;
	}
	
	protected abstract static class StreamReader extends Thread {
		private InputStream stream;
		private OutputMessageList messageList;

		public StreamReader(InputStream stream,OutputMessageList messageList) {
			this.stream = stream;
			this.messageList = messageList;
		}
		public void close () throws IOException  {
			stream.close();
		}
		protected abstract OutputMessage createMessage(String message);
		public void run() {
			try
			{
				InputStreamReader isr = new InputStreamReader(this.stream);
				BufferedReader br = new BufferedReader(isr);
				String line=null;
				while ( (line = br.readLine()) != null)
					this.messageList.messageList.add(this.createMessage(line));   
			} catch (IOException ioe)
			{
				ioe.printStackTrace();  
			}
		}

	}

	
	protected class InputReader extends StreamReader {
		public InputReader(InputStream stream,OutputMessageList list) {
			super(stream,list);
		}
		protected  OutputMessage createMessage(String message){
			BuildConsole.getDefault().writeOutputMessage(message + "\n");
			return new OutputMessage(message,false);
		}

	}
	
	protected class ErrorReader extends StreamReader {
		public ErrorReader(InputStream stream,OutputMessageList list) {
			super(stream,list);
		}
		protected  OutputMessage createMessage(String message){
			BuildConsole.getDefault().writeErrorMessage(message + "\n");

			return new OutputMessage(message,true);
		}

	}
	
	public static class OutputMessage {
		public String message;
		public boolean error;
		public OutputMessage(String message, boolean error) {
			this.message = message;
			this.error = error;
		}	
	}
	
	public static class OutputMessageList {
		public ArrayList<OutputMessage> messageList = new ArrayList<OutputMessage>();
	}


}
	
	


