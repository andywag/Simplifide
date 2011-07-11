package com.simplifide.base.core.project;

import java.util.ArrayList;

public class SuiteProperties {

	private ArrayList<Clock> clockList = new ArrayList<Clock>();
	private String topModule;
	
	public SuiteProperties() {
		
	}
	
	public void addClock(String clk, String freq) {
		Clock cl = new Clock(clk,freq);
		this.clockList.add(cl);
	}
	
	
	public void setClockList(ArrayList clockList) {
		this.clockList = clockList;
	}


	public ArrayList getClockList() {
		return clockList;
	}


	public void setTopModule(String topModule) {
		this.topModule = topModule;
	}


	public String getTopModule() {
		return topModule;
	}


	public static class Clock {
		
		public String name;
		public String frequency;
		
		public Clock(String name, String frequency) {
			this.name = name;
			this.frequency = frequency;
		}
		
	}
	
}
