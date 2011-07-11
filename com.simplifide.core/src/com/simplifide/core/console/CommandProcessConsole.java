package com.simplifide.core.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.ErrorParserManager;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.pythonext.console.BuildConsole;

public class CommandProcessConsole {

	public static void runCommandNew(final String[] command,final  boolean wait, 
			final File location, final CommandDefinition def) {
		final CommandProcessConsole process = new CommandProcessConsole();
		BuildConsole.getDefault().makeConsoleActive();
		BuildConsole.getDefault().writeInputMessage(command + "\n");
		Job job = new Job("Makefile Command " + def.getCommand()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				try {
					EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
					ErrorParserManager manager = new ErrorParserManager(suite.getProject(),def);
					process.runCommandInternalNew(command,wait,location, manager);
					manager.getErrorHolder().writeErrors();
				}
				catch (IOException ex) {
					BuildConsole.getDefault().writeErrorMessage(ex.getMessage() + "\n");
				}
				return Status.OK_STATUS;
			}

		};
		//job.setUser(true);
		job.schedule();
		

		
		/*
		CommandProcessConsole process = new CommandProcessConsole();
		BuildConsole.getDefault().makeConsoleActive();
		BuildConsole.getDefault().writeInputMessage(command + "\n");
		try {
			ArrayList lis =  process.runCommandInternalNew(command,wait,location, def);
			return lis;
		}
		catch (IOException ex) {
			BuildConsole.getDefault().writeErrorMessage(ex.getMessage() + "\n");
		}
		
		
		
		return null;
		*/
	}
	
	private ArrayList runCommandInternalNew(String[] command,boolean wait, 
			File location, ErrorParserManager manager) throws IOException {
	
		OutputMessageList messageList = new OutputMessageList();
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			//builder.redirectErrorStream(true);
			builder.directory(location);
			//Map map = builder.environment();
			Process pr = builder.start();
			
			
			//Process pr = Runtime.getRuntime().exec(command);

			//EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
			//ErrorParserManager manager = new ErrorParserManager(suite.getProject(),def);
			InputReader inRead = new CommandProcessConsole.InputReader(pr.getInputStream(),messageList,manager);
			ErrorReader errRead = new CommandProcessConsole.ErrorReader(pr.getErrorStream(),messageList,manager);
			
			inRead.start();
			errRead.start();
			if (wait) {
				int val = pr.waitFor();
			}
			
			manager.getErrorHolder().writeErrors();
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
		private ErrorParserManager manager;
		public InputReader(InputStream stream,OutputMessageList list, ErrorParserManager man) {
			super(stream,list);
			this.manager = man;
		}
		protected  OutputMessage createMessage(String message){
			this.manager.processLine(message);
			BuildConsole.getDefault().writeOutputMessage(message + "\n");
			return new OutputMessage(message,false);
		}

	}
	
	protected class ErrorReader extends StreamReader {
		private ErrorParserManager manager;
		public ErrorReader(InputStream stream,OutputMessageList list, ErrorParserManager man) {
			super(stream,list);
			this.manager = man;
		}
		protected  OutputMessage createMessage(String message){
			this.manager.processLine(message);
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
	
	


