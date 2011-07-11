package com.simplifide.core.refactor.component;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortConnectWrap;


public class InstanceContentProvider implements IStructuredContentProvider{

	public static final String[] COLS = new String[] {"I/O","Type","Local","External"};

	
	public InstanceContentProvider() {}
	
	public Object[] getElements(Object inputElement) {
		ModInstanceWrap wrap = (ModInstanceWrap) inputElement;
		ArrayList outList = new ArrayList();
		outList.addAll(wrap.getGenerics());
		outList.addAll(wrap.getPorts());
		
		return outList.toArray();
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Label implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			PortConnectWrap port = (PortConnectWrap) element;
			if (columnIndex == 0 ) return port.getVerilogIOString();
			else if (columnIndex == 1) return  port.getType();
			else if (columnIndex == 2) return port.getName();
			else if (columnIndex == 3) return port.getExternVar().getname();
			else return null;
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public static class CellModifier implements ICellModifier {

		private Viewer viewer;
		private boolean editable;
		
		public CellModifier(Viewer viewer, boolean editable) {
			this.viewer = viewer;
			this.editable = editable;
		}
		
		public boolean canModify(Object element, String property) {
			if (property.equalsIgnoreCase(COLS[3])) return true;
			else return false;
		}

		
		public Object getValue(Object element, String property) {
			// TODO Auto-generated method stub
			PortConnectWrap port = (PortConnectWrap) element;
			if (property.equalsIgnoreCase(COLS[0])) return port.getVerilogIOString() ;
			if (property.equalsIgnoreCase(COLS[1])) return port.getType();
			else if (property.equalsIgnoreCase(COLS[2])) return port.getName();
			else  if (property.equalsIgnoreCase(COLS[3])) return port.getExternVar().getname();
			

			return null;
		}

		
		public void modify(Object element, String property, Object value) {
			PortConnectWrap port = (PortConnectWrap) ((Item) element).getData();
			if (property.equalsIgnoreCase(COLS[0])) port.setType((String) value);
			else if (property.equalsIgnoreCase(COLS[1])) port.setName((String) value);
			else  if (property.equalsIgnoreCase(COLS[3])) {
				port.setExternVar(new ModuleObject( (String) value));
			}
			

			this.viewer.refresh();
		}
		
	}

	
	
	
}
