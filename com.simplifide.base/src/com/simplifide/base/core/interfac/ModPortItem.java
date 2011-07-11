package com.simplifide.base.core.interfac;

import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar.IOVar;

public class ModPortItem extends PortDefault{

	private OperatingTypeVar operatingVar;
	
	public ModPortItem(ReferenceItem<SystemVar> svar) {
		super(svar);
		
	}

	public int getSearchType() {
        OperatingTypeVar tvar = this.getOperatingVar();
        int utype = ReferenceUtilities.REF_PORT_TOP;
        if (tvar instanceof OperatingTypeVar.IOVar) {
            OperatingTypeVar.IOVar uvar = (IOVar) tvar;
            if (uvar.getType() == OperatingTypeVar.IOVar.INPUT) utype = ReferenceUtilities.REF_PORT_INPUT;
            else if (uvar.getType() == OperatingTypeVar.IOVar.OUTPUT) utype = ReferenceUtilities.REF_PORT_OUTPUT;
            else if (uvar.getType() == OperatingTypeVar.IOVar.INOUT) utype = ReferenceUtilities.REF_PORT_INOUT;
        }
        return utype;
    }
	
	public void setOperatingVar(OperatingTypeVar operatingVar) {
		this.operatingVar = operatingVar;
	}

	public OperatingTypeVar getOperatingVar() {
		return operatingVar;
	}
}
