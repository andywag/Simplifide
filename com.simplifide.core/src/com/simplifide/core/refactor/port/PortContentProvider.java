package com.simplifide.core.refactor.port;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;

import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.base.refactor.model.PortWrap;


public class PortContentProvider implements IStructuredContentProvider{

	public static final String[] COLS = new String[] {"Type","Name","I/O","Initial","Description","Selected"};
	public static final String[] IOTYPES = new String[] {"Generic","Input","Output","InOut","Buffer"};

	
	public PortContentProvider() {}
	
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
			PortWrap port = (PortWrap) element;
			if (columnIndex == 0) return port.getType();
			else if (columnIndex == 1) return port.getName();
			else if (columnIndex == 2) return IOTYPES[port.getIoType()];
			else if (columnIndex == 3) return port.getInitial();
			else if (columnIndex == 4) return port.getDescription();
			else if (columnIndex == 5) return Boolean.toString(port.isSelected());
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
			// TODO Auto-generated method stub
			if (property.equalsIgnoreCase(COLS[5])) return true;
			else return this.editable;
		}

		
		public Object getValue(Object element, String property) {
			// TODO Auto-generated method stub
			PortWrap port = (PortWrap) element;
			if (property.equalsIgnoreCase(COLS[0])) return port.getType();
			else if (property.equalsIgnoreCase(COLS[1])) return port.getName();
			else  if (property.equalsIgnoreCase(COLS[2])) return port.getIoType();
			else  if (property.equalsIgnoreCase(COLS[3])) return port.getInitial();
			else  if (property.equalsIgnoreCase(COLS[4])) return port.getDescription();
			else  if (property.equalsIgnoreCase(COLS[5])) return Boolean.valueOf(port.isSelected());

			return null;
		}

		
		public void modify(Object element, String property, Object value) {
			PortWrap port = (PortWrap) ((Item) element).getData();
			if (property.equalsIgnoreCase(COLS[0])) port.setType((String) value);
			else if (property.equalsIgnoreCase(COLS[1])) port.setName((String) value);
			else  if (property.equalsIgnoreCase(COLS[2])) port.setIoType((Integer) value);
			else  if (property.equalsIgnoreCase(COLS[3])) port.setInitial((String) value);
			else  if (property.equalsIgnoreCase(COLS[4])) port.setDescription((String) value);
			else  if (property.equalsIgnoreCase(COLS[5])) port.setSelected(((Boolean) value).booleanValue());

			this.viewer.refresh();
		}
		
	}

	
	
	
}
