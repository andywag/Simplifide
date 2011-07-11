package com.simplifide.base.sourcefile.antlr.parse;

import java.util.Comparator;
import java.util.List;

import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;

/** Class which controls the  
 * 
 * @author andy
 *
 */
public class SearchContext implements Comparator<ReferenceItem>{
	

	
	public int compare(ReferenceItem o1, ReferenceItem o2) {
		return o1.getname().compareToIgnoreCase(o2.getname());
	}
	
	public List<ReferenceItem> sortList(List<ReferenceItem> refList) {
		return refList;
	}
	
	public static class Signal extends SearchContext {
		
		protected boolean isVariable(ReferenceItem o1) {
			boolean var1 = false;
			if (ReferenceUtilities.checkType(o1.getSearchType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) var1 = true;
			if (ReferenceUtilities.checkType(o1.getSearchType(), ReferenceUtilities.REF_PORT_TOP) == 0) var1 = true;
			return var1;
		}
		
		
		
		protected int checkEqual(ReferenceItem o1,ReferenceItem o2) {
			return super.compare(o1, o2);
		}
		
		public int compare(ReferenceItem o1, ReferenceItem o2) {
		
			boolean var1 = isVariable(o1);
			boolean var2 = isVariable(o2);
			
			if (var1 == var2) {
				if (var1) return this.checkEqual(o1, o2);
				else super.compare(o1, o2);
			}
			else if (var1) return -1;
			else if (var2) return 1;
			return 1;
		}
	}
	
	public static class Type extends SearchContext {
		public int compare(ReferenceItem o1, ReferenceItem o2) {
			boolean var1 = false, var2 = false;
			if (ReferenceUtilities.checkType(o1.getSearchType(), ReferenceUtilities.REF_TYPEVAR) == 0) var1 = true;
			if (ReferenceUtilities.checkType(o2.getSearchType(), ReferenceUtilities.REF_TYPEVAR) == 0) var2 = true;

			if (var1 == var2) return super.compare(o1, o2);
			else if (var1) return -1;
			else if (var2) return 1;
			return super.compare(o1, o2);
		}
	}
	
	public static class SignalOfType extends Signal {
		
		private TypeVar type;
		
		public SignalOfType(TypeVar type) {
			this.type = type;
		}
		
		private TypeVar getType(ReferenceItem typeR) {
			if (ReferenceUtilities.checkType(typeR.getSearchType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
				SystemVar tvar = (SystemVar) typeR.getObject();
				return tvar.getType();
			}
			if (ReferenceUtilities.checkType(typeR.getSearchType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
				PortTop tvar = (PortTop) typeR.getObject();
				return tvar.getType();
			}
			return null;
		}
		
		public int checkEqual(ReferenceItem o1, ReferenceItem o2) {

			
			TypeVar t1 = this.getType(o1);
			TypeVar t2 = this.getType(o2);
			if (t1 == null && t2 == null) return super.checkEqual(o1, o2);
			if (t1 == null) return -1;
			if (t2 == null) return 1;
			
			int c1 = type.compareToForSearch(t1);
			int c2 = type.compareToForSearch(t2);
			
			if (c1 == 0 && c2 == 0 ) {
				return o1.getname().compareToIgnoreCase(o2.getname());
			}
			if (c1 != 0 && c2 != 0 ) {
				return o1.getname().compareToIgnoreCase(o2.getname()); 
			}
			if (c1 == 0) return -1;
			else if (c2 == 0) return 1;
			
			return o1.getname().compareToIgnoreCase(o2.getname());
			
		}
	}
	
	public static class SignalOfTypes extends Signal {
		
		private TypeVar[] types;
		
		public SignalOfTypes(TypeVar[] types) {
			this.types = types;
		}
		
		private TypeVar getType(ReferenceItem typeR) {
			if (ReferenceUtilities.checkType(typeR.getSearchType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
				SystemVar tvar = (SystemVar) typeR.getObject();
				return tvar.getType();
			}
			if (ReferenceUtilities.checkType(typeR.getSearchType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
				PortTop tvar = (PortTop) typeR.getObject();
				return tvar.getType();
			}
			return null;
		}
		
		public int checkEqual(ReferenceItem o1, ReferenceItem o2) {

			
			TypeVar t1 = this.getType(o1);
			TypeVar t2 = this.getType(o2);
			if (t1 == null && t2 == null) return super.checkEqual(o1, o2);
			if (t1 == null) return -1;
			if (t2 == null) return 1;
			
			int c1 = -1, c2 = -1;
			for (TypeVar type : types) {
				if (c1 != 0) c1 = type.compareToForSearch(t1);
				if (c2 != 0) c2 = type.compareToForSearch(t2);
			}
			
			
			if (c1 == 0 && c2 == 0 ) {
				return o1.getname().compareToIgnoreCase(o2.getname());
			}
			if (c1 != 0 && c2 != 0 ) {
				return o1.getname().compareToIgnoreCase(o2.getname()); 
			}
			if (c1 == 0) return -1;
			else if (c2 == 0) return 1;
			
			return o1.getname().compareToIgnoreCase(o2.getname());
			
		}
	}

	
}
