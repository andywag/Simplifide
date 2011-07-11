package com.simplifide.core.refactor.function.change;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.FocusCellHighlighter;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.refactor.port.PortContentProvider;
import com.simplifide.core.refactor.port.TextCellEditorWithContentProposal;

public class AddPortTableComposite extends Composite {
	protected Table table;
	protected TableViewer tableViewer;
	protected TableCursor cursor;
	private InstanceModule instanceModule;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AddPortTableComposite(Composite parent, int style, ModInstanceWrap wrap, boolean editable,InstanceModule instanceModule) {
		super(parent, style);
		{
			this.instanceModule = instanceModule;
			//this.setBounds(0, 0, 1270, 250);
			TableColumnLayout tableColumnLayout = new TableColumnLayout();
			this.setLayout(tableColumnLayout);
			{
				tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
				table = tableViewer.getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				
				
				{
					TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tblclmnType, new ColumnPixelData(200, true, true));
					tblclmnType.setText(PortContentProvider.COLS[0]);
					
				}
				{
					TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tblclmnName, new ColumnPixelData(200, true, true));
					tblclmnName.setText(PortContentProvider.COLS[1]);
				}
				{
					TableColumn tableColumn = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tableColumn, new ColumnPixelData(75, true, true));
					tableColumn.setText(PortContentProvider.COLS[2]);
				}
				{
					TableColumn tableColumn = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tableColumn, new ColumnPixelData(75, true, true));
					tableColumn.setText(PortContentProvider.COLS[3]);
				}
				{
					TableColumn tableColumn = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tableColumn, new ColumnPixelData(650, true, true));
					tableColumn.setText(PortContentProvider.COLS[4]);
				}
				this.createExtraColumn(tableColumnLayout);
				/*{
					TableColumn tableColumn = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tableColumn, new ColumnPixelData(50, true, true));
					tableColumn.setText(PortContentProvider.COLS[5]);
				}*/
				
			}
		}
		
		

		//editors[5] = new CheckboxCellEditor(this.table);
		

		this.tableViewer.setCellEditors(this.getEditors());
		tableViewer.setContentProvider(new PortContentProvider());
		tableViewer.setLabelProvider(new PortContentProvider.Label());
		tableViewer.setCellModifier(new PortContentProvider.CellModifier(this.tableViewer,editable));
		tableViewer.setColumnProperties(PortContentProvider.COLS);
				
		tableViewer.setInput(wrap);
		
	
		// Table Viewer Activation Support
		ColumnViewerEditorActivationStrategy activationSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == KeyLookupFactory
								.getDefault().formalKeyLookup(IKeyLookup.ENTER_NAME));
			}
		};
		activationSupport.setEnableEditorActivationWithKeyboard(true);
		FocusCellHighlighter focusCellHighlighter = new FocusCellOwnerDrawHighlighter(tableViewer);
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer, focusCellHighlighter);

		/*
		TableViewerEditor.create(tableViewer, 
				new ColumnViewerEditorActivationStrategy(tableViewer),
				ColumnViewerEditor.TABBING_HORIZONTAL|ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR|ColumnViewerEditor.TABBING_VERTICAL);
		*/
		TableViewerEditor.create(tableViewer, 
				focusCellManager,
				activationSupport, 
				ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION |
				ColumnViewerEditor.TABBING_HORIZONTAL|ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR);

		
	}
	
	protected void createExtraColumn(TableColumnLayout tableColumnLayout) {
		
	}
	
	
	protected CellEditor[] getEditors() {
		CellEditor[] editors = new CellEditor[6];
		String[] ulist = new String[0];
		if (instanceModule != null) {
			ModuleObjectWithList<ModuleObject> list = this.instanceModule.getCompletionContextList(ReferenceUtilities.REF_TYPEVAR);
			ulist = new String[list.getnumber()];
			int ind = 0;
			for (ReferenceItem ref : list.getGenericSelfList()) {
				ulist[ind] = ref.getname();
				ind++;
			}
			
		}
		
		
		SimpleContentProposalProvider prop = new SimpleContentProposalProvider(ulist);
		prop.setFiltering(true);
		editors[0] = new TextCellEditorWithContentProposal(this.table,prop,null,null);
		editors[1] = new TextCellEditor(this.table);
		editors[2] = new ComboBoxCellEditor(table, PortContentProvider.IOTYPES, SWT.READ_ONLY);
		editors[3] = new TextCellEditor(this.table);
		editors[4] = new TextCellEditor(this.table);
		return editors;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	

	
	
	
}
