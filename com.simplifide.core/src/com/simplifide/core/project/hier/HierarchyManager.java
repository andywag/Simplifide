/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.hier;

import java.util.HashSet;

import javax.swing.event.ChangeListener;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.reference.ReferenceItem;

public class HierarchyManager {
	
	private static HierarchyManager instance;
	private HashSet<ChangeListener> listeners = new HashSet<ChangeListener>();
	
	private  ReferenceItem<HierarchyList> hierList = new NoSortList().createReferenceItem();
	
	public HierarchyManager() {}
	
	public static HierarchyManager getInstance() {
		if (instance == null) instance = new HierarchyManager();
		return instance;
	}
	
        public void clearList() {
            this.setHierList(new NoSortList().createReferenceItem());
            this.fireChange();
        }
	
	public void changeHierList(ReferenceItem<HierarchyList> list) {
		this.setHierList(list);
		this.fireChange();
	}
	
	private void fireChange() {
		for (ChangeListener listen : this.listeners) {
			listen.stateChanged(null);
		}
	}
	
	public void addListener(ChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(ChangeListener listener) {
		this.listeners.remove(listener);
	}

	public void setHierList(ReferenceItem<HierarchyList> hierList) {
		this.hierList = hierList;
	}

	public  ReferenceItem<HierarchyList> getHierList() {
		return hierList;
	}
}
