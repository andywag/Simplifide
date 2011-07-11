/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.util;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.var.realvars.DelegateVar;
import com.simplifide.base.core.var.realvars.SystemParameter;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.node.NumberASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenCopy;
import com.simplifide.base.sourcefile.antlr.tok.TopASTTokenDelegate;
import com.simplifide.base.sourcefile.antlr.walk.PositionWalker;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;
import com.simplifide.base.verilog.parse.nodes.VerilogRootASTNode;
import com.simplifide.base.vhdl.parse.node.function.FunctionInterfaceNode;
import com.simplifide.base.vhdl.parse.node.string.VhdlStringASTNode;

public class EditorUtilities {


	
	public static InstanceModule getInstanceModule(ParseDescriptor desc, int pos) {
		List<TopASTNode> nodes = PositionWalker.getPathTo(desc.getRoot(),pos);
		ParseContext context = desc.createContext();
		for (TopASTNode node : nodes) {
			node.resolveContext(context);
			if (context.getActiveReference() == null || context.getActiveReference().getObject() == null)
				return null;
			if (context.getActiveReference().getObject() instanceof InstanceModule) {
				return (InstanceModule) context.getActiveReference().getObject();
			}
			else if (context.getActiveReference().getObject() instanceof EntityHolder) {
				EntityHolder ent = (EntityHolder) context.getActiveReference().getObject();
				return (InstanceModule) ent.getInstanceModRef().getObject();
			}
		}
		return null;
	}
	
	
	public ParseContext updateContextOnPosition(ParseDescriptor desc, int docPos) {
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),docPos);
		ParseContext context = desc.createContext();
		for (TopASTNode node : nodeList) {
			ReferenceItem ritem = node.findItemResolveContext(context, docPos);
		}
		return context;
	}

	// Only Meaningful for verilog
	public static ReferenceUsage[] getBeginEndItem(ParseDescriptor desc,int docPos) {
		
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),docPos);
		boolean verilog = false;
		TopASTNode lastNode = null;
		for (TopASTNode node : nodeList) {
			if (node instanceof VerilogRootASTNode) verilog = true;
			if (node.getType() == SystemVerilogTokenTypes.LITERAL_begin ||
				node.getType() == SystemVerilogTokenTypes.LITERAL_end) {
				
				ParseContext context = desc.createContext();

				TopASTNode begin = lastNode.getFirstASTChild();
				TopASTNode end = lastNode.getLastASTChild();
				
				ReferenceLocation bloc = context.createReferenceLocation(begin);
				ReferenceLocation eloc = context.createReferenceLocation(end);
				ReferenceUsage buse = new ReferenceUsage(new ReferenceItem("begin",0),"begin",bloc);
				ReferenceUsage euse = new ReferenceUsage(new ReferenceItem("end",0),"end",eloc);
				return new ReferenceUsage[] {buse,euse};
			}
			lastNode = node;
		}
		
		return null;
	}
	
	public static SystemParameter getNumberNode(ParseDescriptor desc, int docPos) {
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),docPos);
		for (TopASTNode node : nodeList) {
			if (node instanceof NumberASTNode) {
				ReferenceItem<SystemParameter> par = (ReferenceItem<SystemParameter>) node.generateModuleSmallNew(null);
				return par.getObject();
			}
			if (node instanceof VhdlStringASTNode) {
				String base = node.getRealText().replace("\"", "");
				SystemParameter par = new SystemParameter(base,SystemParameter.RADIX_BINARY);
				return par;
			}
		}
		return null;
	}
	
	public static EditorFindItem getEditorFindItem(ParseDescriptor desc,
			int docPos) {

		ParseContext context = desc.createContext();
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),
				docPos);
		EditorFindItem item = null;
		for (TopASTNode node : nodeList) {
			ReferenceItem ritem = node.findItemResolveContext(context, docPos);
			if (node.getToken() != null && node.getToken() instanceof TopASTTokenCopy) {
				TopASTTokenCopy copy = (TopASTTokenCopy) node.getToken();
				String realText = ((TopASTTokenDelegate)copy.getRealtok()).getRealText();
				copy.getDefineR().getObject().setRealText(realText);
				ritem = copy.getDefineR();
			}
			if (ritem != null) {
				int spos = node.getPosition().getStartPos();
				int epos = node.getPosition().getStartPos() + node.getPosition().getLength();
				ReferenceLocation loc= ritem.getLocation();
				if (loc != null) {
					int spos1 = loc.getDocPosition();
					int epos1 = spos1 + loc.getLength();
					if (docPos > spos1 && docPos < epos1) {
						spos = spos1; epos = epos1;
					}
				}
				
				if (ritem.getObject().getBaseSearchClass() != null) {
					ReferenceItem ref = ritem.getObject().getBaseSearchClass().createReferenceItem();
					item = new EditorFindItem(ref, context.getActiveReference(),spos, epos);
				}
				
				break;
			}
		}
		if (item == null) {
			DefineObject def = checkPositionForDefines(context, docPos);
			if (def != null) {
				
				return def.getFindItem(context, docPos);
			}
		}
		return item;
	}

       
	public static TopASTNode getPortListNode(ParseDescriptor desc, int docPos) {
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),docPos);
		for (TopASTNode node : nodeList) {
			TopASTNode pnode = node.getPortListNode();
			if (pnode != null) return pnode;
		}
		return null;
	}
        
	/** Converts a list of referenceitems to editor find items
	 * 
	 * Used for rename refactoring which has a different output than the goto operations
	 */
	public static List<EditorFindItem> listReference2listFindItem(EditorFindItem baseItem, 
			List<ReferenceItem> refList) {
		ArrayList<EditorFindItem> list = new ArrayList<EditorFindItem>();
		
			for (ReferenceItem uitem : refList) {
				if (uitem != null) // Really shouldn't be adding more of this
									// usage
				{
					EditorFindItem findItem = new EditorFindItem(uitem,
							baseItem.getEnclosingItem(),
							baseItem.getPosition()[0],
							baseItem.getPosition()[1]);
					list.add(findItem);
				}
			}
			return list;

		}
	/** Returns a list of items which correspond to this base item for use mainly 
	 * with the goto and hyperlink operations
	 */
	public static List<EditorFindItem> getHyperlinkList(EditorFindItem baseItem) {
		if (baseItem != null) { 
			List<ReferenceItem> refList = baseItem.getItem().findHyperlinksList();
			return listReference2listFindItem(baseItem, refList);
		}
		return new ArrayList<EditorFindItem>(); 
	}
	/** Returns a list of items which correspond to this base item for use mainly 
	 *  rename refactoring. This is a complete list of objects
	 */
	public static List<EditorFindItem> getRenameList(EditorFindItem baseItem) {
		if (baseItem != null) {
			List<ReferenceItem> refList = baseItem.getItem().findRenameList(baseItem.getEnclosingItem());
			return listReference2listFindItem(baseItem, refList);
		}
		return new ArrayList<EditorFindItem>();
	}
	

	/** Returns a list of find items from the location in the document */
	public static List<EditorFindItem> getEditorFindItemList(
			ParseDescriptor desc, int docPos) {
		EditorFindItem baseItem = getEditorFindItem(desc, docPos);
		return getHyperlinkList(baseItem);
	}
	
	private static DefineObject checkPositionForDefines(ParseContext context, int offset) {
		List<DefineObject> defines = context.getDescriptor().getDefines();
		for (DefineObject define : defines) {
			if (define.containsPosition(offset)) return define;
		}
		return null;
	}
	
	
	public static EditorFindItem getHoverItem(ParseDescriptor desc, int docPos) {
		ParseContext context = desc.createContext();
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),
				docPos);
		EditorFindItem item = null;
		ReferenceItem<InstanceFunction> functionR = null;
		for (TopASTNode node : nodeList) {
			if (node instanceof FunctionInterfaceNode) {
				FunctionInterfaceNode func = (FunctionInterfaceNode) node;
				functionR = func.returnInstanceFunction(context);
			}
			ReferenceItem ritem = null;
			if (node.getToken() != null && node.getToken() instanceof TopASTTokenCopy) {
				TopASTTokenCopy copy = (TopASTTokenCopy) node.getToken();
				String realText = ((TopASTTokenDelegate)copy.getRealtok()).getRealText();
				copy.getDefineR().getObject().setRealText(realText);
				ritem = copy.getDefineR();
			}
			else {
				 ritem = node.findItemResolveContext(context, docPos);
			}
			
			if (ritem != null && ritem.getObject() != null) {
				if (functionR != null && ritem.getObject() instanceof FunctionHolder && ritem.getname().equalsIgnoreCase(functionR.getname())) {
					ritem = functionR;
				}
				int spos = node.getPosition().getStartPos();
				int epos = node.getPosition().getStartPos() + node.getPosition().getLength();
				item = new EditorFindItem(ritem.getObject().getBaseSearchClass().createReferenceItem(), context.getActiveReference(),
						spos, epos);
				return item;
			}
		}
		if (functionR != null) {
			TopASTNode node = nodeList.get(nodeList.size() - 1);
			if (node.getRealText().equalsIgnoreCase(functionR.getname())) {
				int spos = node.getPosition().getStartPos();
				int epos = node.getPosition().getStartPos() + node.getPosition().getLength();
				item = new EditorFindItem(functionR, context.getActiveReference(),
						spos, epos);
				return item;
			}
			
		}
		if (item == null) {
			DefineObject def = checkPositionForDefines(context, docPos);
			if (def != null) {
				
				return def.getFindItem(context,docPos);
			}
		}
		
		
		return item;
	}
	
	/** Method which returns the instance module which encloses this position in the editor */
	public static InstanceModuleTop getEnclosingInstanceModule(ParseDescriptor desc, int docPos) {
		ParseContext context = desc.createContext();
		List<TopASTNode> nodeList = PositionWalker.getPathTo(desc.getRoot(),docPos);
		for (TopASTNode node : nodeList) {
			node.findItemResolveContext(context, docPos);
		}
		if (context.getRefHandler().getEntityReference() != null) {
			InstanceModuleTop inst = (InstanceModuleTop) context.getRefHandler().getEntityReference().getObject().getInstanceModRef().getObject();
			return inst;
		}
		if (context.getRefHandler().getModuleReference() != null) {
			InstanceModuleTop inst = (InstanceModuleTop) context.getRefHandler().getModuleReference().getObject().getInstModRef().getObject();
			return inst;
		}
		return null;
	}
	

	/** @todo : Needs to be fixed */
	public static ParseContext getParseContext(ParseDescriptor parse, int pos) {
		ParseContext context = parse.createContext();
		PositionWalker.updatePathContext(parse.getUsableRoot(), pos, context);
		return context;

	}

}
