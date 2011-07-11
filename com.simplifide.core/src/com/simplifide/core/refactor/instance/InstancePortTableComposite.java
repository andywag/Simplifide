package com.simplifide.core.refactor.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellHighlighter;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.PortConnectWrap;
import com.simplifide.core.refactor.port.TextCellEditorWithContentProposal;

public class InstancePortTableComposite extends Composite {
	protected Table table;
	protected TableViewer tableViewer;
	protected TableCursor cursor;
	private InstanceModule instanceModule;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InstancePortTableComposite(Composite parent, int style, 
			ModInstanceConnectWrap wrap, InstanceModule instanceModule) {
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
					tblclmnType.setText(InstanceContentProvider.COLS[0]);
				}
				{
					TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tblclmnType, new ColumnPixelData(200, true, true));
					tblclmnType.setText(InstanceContentProvider.COLS[1]);
				}
				{
					TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tblclmnName, new ColumnPixelData(200, true, true));
					tblclmnName.setText(InstanceContentProvider.COLS[2]);
				}
				{
					TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
					tableColumnLayout.setColumnData(tblclmnName, new ColumnPixelData(200, true, true));
					tblclmnName.setText(InstanceContentProvider.COLS[3]);
				}
				
				this.createExtraColumn(tableColumnLayout);
			
				
			}
		}
		
		

		//editors[5] = new CheckboxCellEditor(this.table);
		

		this.tableViewer.setCellEditors(this.getEditors());
		tableViewer.setContentProvider(new InstanceContentProvider());
		tableViewer.setLabelProvider(new InstanceContentProvider.Label());
		tableViewer.setCellModifier(new InstanceContentProvider.CellModifier(this.tableViewer,true,null));
		tableViewer.setColumnProperties(InstanceContentProvider.COLS);
				
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
		
		this.createPopUpMenu();

		
	}
	

	private void createPopUpMenu() {
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.button != 3) return;
				
				Point pt = new Point(event.x, event.y);
				TableItem item = table.getItem(pt);
				
				
				if (item == null)
					return;
				for (int i = 3; i < 4; i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						 Menu menu = new Menu(table.getShell(), SWT.POP_UP);
						 String[] strings = getPossibleExternal();
						 for (String str : strings) {
							 MenuItem item1 = new MenuItem(menu, SWT.PUSH);
							 item1.setText(str);
							 item1.addSelectionListener(new ItemListener(str,item));
						 }
						 menu.setVisible(true);
						 
						 

					}
				}
			}
		});

	}

	
	
    public void updateInstanceModule(ModInstanceConnectWrap wrap) {
    	this.tableViewer.setInput(wrap);
    	this.update();
    }
	
	
	protected void createExtraColumn(TableColumnLayout tableColumnLayout) {
		
	}
	
	private String[] getPossibleExternal() {
		List<SystemVar> vars = this.instanceModule.getAllVars();
		List<String> stringList = new ArrayList<String>();
		for (SystemVar var : vars) {
			stringList.add(var.getname());
		}
		Collections.sort(stringList);
		return stringList.toArray(new String[stringList.size()]);
	}
	
	protected CellEditor[] getEditors() {
		CellEditor[] editors = new CellEditor[4];
		String[] ulist = new String[0];
		if (instanceModule != null) {
			List<SystemVar> list = instanceModule.getAllVars();
			//list.addAll(instanceModule.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilitiesInterface.REF_PORT_TOP));
			ulist = new String[list.size()];
			int ind = 0;
			for (SystemVar ref : list) {
				ulist[ind] = ref.getname();
				ind++;
			}
			
		}
		
		
		SimpleContentProposalProvider prop = new SimpleContentProposalProvider(ulist);
		prop.setFiltering(true);
		editors[0] = new TextCellEditor(this.table);
		editors[1] = new TextCellEditor(this.table);
		editors[2] = new TextCellEditor(this.table);
		editors[3] = new TextCellEditorWithContentProposal(this.table,prop,null,null); 
		
		//editors[3] = new ComboBoxCellEditor(table, g);
		return editors;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public class ItemListener extends SelectionAdapter {
		
		private String text;
		private TableItem item;
		
		public ItemListener(String text, TableItem item) {
			this.text = text;
			this.item = item;
		}
		
		public void widgetSelected(SelectionEvent event) {
			item.setText(3, text);
			PortConnectWrap wrap = (PortConnectWrap) item.getData();
			wrap.setExternVar(new ModuleObject(text));
			
			
		}
		
	}

	
	
	
}
