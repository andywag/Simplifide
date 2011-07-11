package com.simplifide.core.python;

import java.util.Properties;

import org.python.core.Py;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.simplifide.core.python.inter.StartupInterface;
import com.simplifide.core.python.inter.UtilitiesInterface;


public class PythonStartup {

	private static PythonStartup instance;
	
	private PythonInterpreter interpreter;
	private StartupInterface startup;
	
	private PythonStartup() {
		this.initPython();
	}
	
	
	public static PythonStartup getDefault() {
		if (instance == null) instance = new PythonStartup();
		return instance;
	}
	
	
	public void initPython() {
    	Properties props = new Properties();       
    	String path = PythonPath.getSystemPath(";",false);
        props.setProperty("python.path", path);
        PySystemState.initialize(System.getProperties(),props,new String[] {""});
        this.updatePath();
        this.updatePath(); // Kludge because the initial path is not set correctly
        this.loadStartUp();
        this.interpreter = new PythonInterpreter();
        
        
	}
	
	public void loadStartUp() {
		this.startup = StartupLoader.getStartup();
	}
	
	public void updatePath() {
		Py.getSystemState().path = PythonPath.getPathList(true);
	}
	
	public void cleanUp() {	
		UtilitiesInterface uint = StartupLoader.getUtilities();
		if (uint != null) uint.runGC();
		this.updatePath();
		this.loadStartUp();
	}
	

	public void setStartup(StartupInterface startup) {
		this.startup = startup;
	}


	public StartupInterface getStartup() {
		return startup;
	}


	public void setInterpreter(PythonInterpreter interpreter) {
		this.interpreter = interpreter;
	}


	public PythonInterpreter getInterpreter() {
		return interpreter;
	}
	
	
}
