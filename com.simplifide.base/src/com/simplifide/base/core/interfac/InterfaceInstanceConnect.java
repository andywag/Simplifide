package com.simplifide.base.core.interfac;

import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class InterfaceInstanceConnect extends ModInstanceConnect{

	 public InterfaceInstanceConnect(String name, ReferenceItem<EntityHolder> entR) {
		 super(name,entR);
	 }
	
	  public int getSearchType() {
	        return ReferenceUtilities.REF_MODINSTANCE_CONNECT;
	    }
	
}
