package com.simplifide.core.errorparser.newui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.CommandExtensionManager;
import com.simplifide.core.errorparser.RegexErrorPattern;

public class TargetComposite extends Composite {
	private TableViewer tableViewer;
	private Table table;
	
	private Button addButton;
	private Button deleteButton;
	private Button moveUpButton;
	private Button moveDownButton;
	
	private TableColumn commandColumn;
	private TableColumn typeColumn;
	private TableColumn parserColumn;
	private TableColumn saveColumn;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TargetComposite(Composite parent, int style) {
		super(parent, style);
		
		this.tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBounds(18, 10, 610, 280);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		commandColumn = new TableColumn(table, SWT.NONE);
		commandColumn.setWidth(300);
		commandColumn.setText("Command");
		
		typeColumn = new TableColumn(table, SWT.NONE);
		typeColumn.setWidth(150);
		typeColumn.setText("Type");
		
		parserColumn = new TableColumn(table, SWT.NONE);
		parserColumn.setWidth(100);
		parserColumn.setText("Error Parser");
		
		saveColumn = new TableColumn(table, SWT.NONE);
		saveColumn.setWidth(60);
		saveColumn.setText("Use On Save");
		
		addButton = new Button(this, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addErrorPattern();
			}
		});
		addButton.setBounds(635, 10, 75, 25);
		addButton.setText("Add");
		
		deleteButton = new Button(this, SWT.NONE);
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteErrorPattern();
			}
		});
		deleteButton.setText("Delete");
		deleteButton.setBounds(635, 41, 75, 25);
		
		
		
		moveUpButton = new Button(this, SWT.NONE);
		moveUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(true);
			}
		});
		moveUpButton.setText("Move Up");
		moveUpButton.setBounds(635, 91, 75, 25);
		
		moveDownButton = new Button(this, SWT.NONE);
		moveDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(false);
			}
		});
		moveDownButton.setBounds(635, 122, 75, 25);
		moveDownButton.setText("Move Down");
		
		this.createCommandColumn();
		this.createTypeColumn();
		this.createErrorColumn();
		this.createSaveColumn();
		
		this.initializeTable();

	}
	
	private void initializeTable() {
		List<CommandDefinition> commands = CommandExtensionManager.loadStore();
		int index = 0;
		for (CommandDefinition command : commands) {
			tableViewer.insert(command,index);
			index++;
		}
	}
	
	private void createCommandColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer,commandColumn);
		columnViewer.getColumn().setText("Target");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("Command Entry");
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof CommandDefinition) {
					return ((CommandDefinition) element).getCommand();
				}
				return "Error";
			}
		});

		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			
			protected CellEditor getCellEditor(Object element) {
				CellEditor editor = new TextCellEditor(tableViewer.getTable());
				return editor;
			} 
			
			protected Object getValue(Object element) {
				if (element instanceof CommandDefinition) {
					return ((CommandDefinition) element).getCommand();
				}
				return "Error";
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof CommandDefinition) {
					((CommandDefinition) element).setCommand((String)value);
				}
				tableViewer.update(element, null);
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
	}
	
	private void createTypeColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, typeColumn);
		columnViewer.getColumn().setText("Type");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("Type of Error");
		
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof CommandDefinition) {
					return ((CommandDefinition) element).getTextTypeDefinition();
				}
				return "Error";
			}
		});
		
		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			

			protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
				Object value = getValue(cell.getElement());
				cellEditor.setValue(value);
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getTable(), CommandDefinition.TYPES, SWT.READ_ONLY);
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof CommandDefinition) {
					CommandDefinition def = (CommandDefinition) element;
					return def.getType();
				}
				return 1;
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof CommandDefinition && value instanceof Integer) {
					CommandDefinition def = (CommandDefinition) element;
					Integer iv = (Integer) value;
					def.setType(iv);
				}
				tableViewer.update(element, null);
			}

		});
	}
	
	private void createErrorColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, parserColumn);
		columnViewer.getColumn().setText("Parser");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("Type of Parser");
		
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof CommandDefinition) {
					return ((CommandDefinition) element).getTextParserType();
				}
				return "Error";
			}
		});
		
		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getTable(), CommandDefinition.getErrorTypes(), SWT.READ_ONLY);
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof CommandDefinition) {
					CommandDefinition def = (CommandDefinition) element;
					return def.getParserType();
					
				}
				return 0;
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof CommandDefinition && value instanceof Integer) {
					CommandDefinition def = (CommandDefinition) element;
					Integer iv = (Integer) value;
					def.setParserType(iv);
				}
				tableViewer.update(element, null);
			}

		});
	}
	
	private void createSaveColumn() {
		final String EAT_NO = "No";
		//final String EAT_YES = "Yes";

		final String[] eatLineComboBoxArray = new String[] { EAT_NO, };
		final int EAT_YES_INDEX = 0;
		final int EAT_NO_INDEX = 1;

		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, saveColumn);
		columnViewer.getColumn().setText("On Save");
		columnViewer.getColumn().setResizable(true);

		
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					if (!regex.isEatProcessedLine())
						return EAT_NO;
				}
				return EAT_NO;
			}
		});
		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getTable(), eatLineComboBoxArray, SWT.READ_ONLY);
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof CommandDefinition) {
					CommandDefinition def = (CommandDefinition) element;
					if (def.isOnSave()) return EAT_YES_INDEX;
					else return EAT_NO_INDEX;
				}
				return "Error";
					
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof CommandDefinition && value instanceof String) {
					CommandDefinition def = (CommandDefinition) element;
					String value1 = (String) value;
					if (value1.equals(EAT_NO)) def.setOnSave(false);
					def.setOnSave(false);
				}
				tableViewer.update(element, null);
			}

		});
	}
	
	
	
	private void addErrorPattern() {
		int pos = table.getSelectionIndex();
		int last = table.getItemCount()-1;
		if (pos<0 || pos>last)
			pos = last;

		int newPos = pos + 1;
		tableViewer.insert(CommandDefinition.dummyCommand(), newPos);
		table.setSelection(newPos);
	}

	private void deleteErrorPattern() {
		int pos = table.getSelectionIndex();
		int last = table.getItemCount()-1;

		if (pos>=0 && pos<=last) {
			tableViewer.remove(tableViewer.getElementAt(pos));
			table.setSelection(pos);
		}
	}
	private void moveItem(boolean up) {
		int pos = table.getSelectionIndex();
		int count = table.getItemCount();
		int last = count-1;
		boolean selected = pos>=0 && pos<=last;

		if (!selected || (up && pos==0) || (!up && pos==last))
			return;

		Object item = tableViewer.getElementAt(pos);
		tableViewer.remove(item);
		int newPos = up ? pos-1 : pos+1;
		tableViewer.insert(item, newPos);
		table.setSelection(newPos);
	}
	
	
	
	public void updateButtons() {
		int pos = table.getSelectionIndex();
		int count = table.getItemCount();
		int last = count - 1;
		boolean selected = pos >= 0 && pos <= last;
		String id = (String)this.tableViewer.getElementAt(pos);

		addButton.setEnabled(true);
		//editButton.setEnabled(isErrorParsersEditable() && selected);
		deleteButton.setEnabled(selected);
		moveUpButton.setEnabled(selected && pos != 0);
		moveDownButton.setEnabled(selected && pos != last);
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected abstract class TargetEdittingSupport extends EditingSupport{

		public TargetEdittingSupport(ColumnViewer viewer) {
			super(viewer);
			
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			CellEditor editor = new TextCellEditor(tableViewer.getTable());
			return editor;
		}

		protected abstract Object getTargetValue(CommandDefinition command);
		protected abstract void setTargetValue(CommandDefinition command,Object ObjectValue);
		@Override
		protected Object getValue(Object element) {
			if (element instanceof CommandDefinition) {
				return getTargetValue((CommandDefinition)element);
			}
			return "Error";
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof CommandDefinition) {
				setTargetValue((CommandDefinition)element,value);
			}
		}

	}
	
	public List<CommandDefinition> performOK() {
		ArrayList<CommandDefinition> commands = new ArrayList<CommandDefinition>();
		for (TableItem tableItem : table.getItems()) {
			Object item = tableItem.getData();
			if (item instanceof CommandDefinition) {
				commands.add((CommandDefinition)item);
			}
		}
		return commands;
	}
	
	
}
