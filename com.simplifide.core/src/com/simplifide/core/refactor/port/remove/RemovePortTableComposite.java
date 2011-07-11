package com.simplifide.core.refactor.port.remove;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.refactor.port.PortContentProvider;
import com.simplifide.core.refactor.port.add.AddPortTableComposite;

public class RemovePortTableComposite extends AddPortTableComposite{

	public RemovePortTableComposite(Composite parent, int style,
			ModInstanceWrap wrap, boolean editable,InstanceModule instanceModule) {
		super(parent, style, wrap, editable,instanceModule);
	}

	protected void createExtraColumn(TableColumnLayout tableColumnLayout) {
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumnLayout.setColumnData(tableColumn, new ColumnPixelData(50, true, true));
		tableColumn.setText(PortContentProvider.COLS[5]);
	}
	protected CellEditor[] getEditors() {
		CellEditor[] editors = super.getEditors();
		editors[5] = new CheckboxCellEditor(this.table);
		return editors;
	}
}
