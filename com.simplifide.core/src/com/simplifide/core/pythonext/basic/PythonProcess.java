package com.simplifide.core.pythonext.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.simplifide.core.HardwareLog;

public class PythonProcess {

	public static ArrayList runCommand(String command) {
		PythonProcess process = new PythonProcess();
		return process.runCommandInternal(command);
	}
	
	private ArrayList runCommandInternal(String command) {
	
		OutputMessageList messageList = new OutputMessageList();
		try {
			Process pr = Runtime.getRuntime().exec(command);
			InputReader inRead = new PythonProcess.InputReader(pr.getInputStream(),messageList);
			ErrorReader errRead = new PythonProcess.ErrorReader(pr.getErrorStream(),messageList);
			
			inRead.start();
			errRead.start();
			
			int val = pr.waitFor();
			
			
		} catch (IOException e) {
			HardwareLog.logError(e);
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
			return new OutputMessage(message,false);
		}

	}
	
	protected class ErrorReader extends StreamReader {
		public ErrorReader(InputStream stream,OutputMessageList list) {
			super(stream,list);
		}
		protected  OutputMessage createMessage(String message){
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
	
	


