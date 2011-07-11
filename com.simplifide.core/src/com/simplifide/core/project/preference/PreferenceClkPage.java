package com.simplifide.core.project.preference;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class PreferenceClkPage extends Composite {

	private Table table;
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public PreferenceClkPage(Composite parent, int style) {
		super(parent, style);

		table = new Table(this, SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 314, 100);

		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(100);
		newColumnTableColumn.setText("Clock Name");

		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(201);
		newColumnTableColumn_1.setText("Clock Frequency");
		//
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
