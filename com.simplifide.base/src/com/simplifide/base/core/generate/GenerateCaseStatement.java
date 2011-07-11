package com.simplifide.base.core.generate;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

public class GenerateCaseStatement extends GenerateStatement{

	private ReferenceItem caseCondition;
	
	public GenerateCaseStatement(String name) {
		super(name);
	}
	
	
	public void setCaseCondition(ReferenceItem caseCondition) {
		this.caseCondition = caseCondition;
	}


	public ReferenceItem getCaseCondition() {
		return caseCondition;
	}


	public static class Item extends GenerateStatement {
		
		private NoSortList<ModuleObject> expressions;
		private ReferenceItem object;
		
		public Item(String name, NoSortList expressions, ReferenceItem object) {
			super(name);
			this.expressions = expressions;
			this.object = object;
			
		}
		
		public boolean hasNavigatorChildren() {return true;}
		
		public List<ReferenceItem> getNavigatorList() {
			if (object.getObject() != null)
				return object.getObject().getNavigatorList();
			return new ArrayList<ReferenceItem>();
		}
		
		
		public String getDisplayName() {
			if (expressions == null) return "default";
			String ustr = "case_item(";
			if (object.getObject() != null && object.getObject() instanceof GenerateBlock) {
				ustr = object.getname() + "(";
			}
			boolean first = true;
			for (ReferenceItem ref : expressions.getGenericSelfList()) {
				if (!first) ustr += ","; first = false;
				ustr += ref.getname();
			}
			ustr += ")";
			return ustr;
		}
		
	}
	
}
