package com.simplifide.base.core.doc;

import com.simplifide.base.core.reference.ReferenceLocation;

public class ToDoObject {

	private String description;
	private ReferenceLocation location;
	
	public ToDoObject(String description, ReferenceLocation location) {
		this.description = description;
		this.location = location;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setLocation(ReferenceLocation location) {
		this.location = location;
	}
	public ReferenceLocation getLocation() {
		return location;
	}
	
}
