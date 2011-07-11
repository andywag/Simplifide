package com.simplifide.core.errorparser.newui;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.FindReplaceDocumentAdapterContentProposalProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.errorparser.IMarkerGenerator;
import com.simplifide.core.errorparser.RegexErrorParser;
import com.simplifide.core.errorparser.RegexErrorPattern;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RegExpEntryTable extends Composite {
	
	private static final int BUTTON_ADD = 0;
	private static final int BUTTON_DELETE = 1;
	private static final int BUTTON_MOVEUP = 2;
	private static final int BUTTON_MOVEDOWN = 3;
	
	private Table table;
	private TableViewer tableViewer;
	private boolean fEditable = true;
	
	private RegexErrorParser fErrorParser;
	
	private Button addButton;
	private Button deleteButton;
	private Button moveUpButton;
	private Button moveDownButton;
	
	private TableColumn severityColumn;
	private TableColumn expressionColumn;
	private TableColumn fileColumn;
	private TableColumn lineColumn;
	private TableColumn descColumn;
	private TableColumn consumeColumn;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RegExpEntryTable(Composite parent, int style, RegexErrorParser parser) {
		super(parent, style);
		this.fErrorParser = parser;
		
		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setUseHashlookup(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		table = tableViewer.getTable();
		table.setBounds(10, 10, 620, 265);
		table.setHeaderVisible(true);
		severityColumn = new TableColumn(table, SWT.NONE);
		severityColumn.setWidth(70);
		severityColumn.setText("Severity");
		
		
		
		expressionColumn = new TableColumn(table, SWT.NONE);
		expressionColumn.setWidth(270);
		expressionColumn.setText("Expression");
		
		fileColumn = new TableColumn(table, SWT.NONE);
		fileColumn.setWidth(70);
		fileColumn.setText("File");
		
		lineColumn = new TableColumn(table, SWT.NONE);
		lineColumn.setWidth(70);
		lineColumn.setText("Line");
		
		descColumn = new TableColumn(table, SWT.NONE);
		descColumn.setWidth(70);
		descColumn.setText("Description");
		
		consumeColumn = new TableColumn(table, SWT.NONE);
		consumeColumn.setWidth(70);
		consumeColumn.setText("Consume");
		
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
		deleteButton.setBounds(635, 41, 75, 25);
		deleteButton.setText("Delete");
		
		moveUpButton = new Button(this, SWT.NONE);
		moveUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(true);
			}
		});
		moveUpButton.setBounds(635, 72, 75, 25);
		moveUpButton.setText("Move Up");
		
		moveDownButton = new Button(this, SWT.NONE);
		moveDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(false);
			}
		});
		moveDownButton.setBounds(635, 104, 75, 25);
		moveDownButton.setText("Move Down");

		
		this.createSeverityColumn();
		this.createPatternColumn();
		this.createFileColumn();
		this.createLineColumn();
		this.createDescriptionColumn();
		this.createEatLineColumn();
		
		initializeTable();
	}
	
	public void changeErrorParser(RegexErrorParser parser) {
		try {
			this.performApply(null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		this.fErrorParser = parser;
		this.initializeTable();
		this.setVisible(true);
	}
	
	private void initializeTable() {
		if (fErrorParser == null) return;
		RegexErrorPattern[] errorParserPatterns = fErrorParser!=null
			? errorParserPatterns = fErrorParser.getPatterns()
			: new RegexErrorPattern[0];

		int len = errorParserPatterns.length;
		int newLen = len;

		RegexErrorPattern[] tablePatterns = new RegexErrorPattern[newLen];
		System.arraycopy(errorParserPatterns, 0, tablePatterns, 0, len);

		tableViewer.setInput(tablePatterns);
		tableViewer.refresh();
	}
	
	private void createSeverityColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, severityColumn);
		columnViewer.getColumn().setText("Severity");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("Severity of Marker");
		columnViewer.setLabelProvider(new ColumnLabelProvider() {
			final ISharedImages images = PlatformUI.getWorkbench().getSharedImages();

			@Override
			public Image getImage(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regexErrorPattern = (RegexErrorPattern) element;
					switch (regexErrorPattern.getSeverity()) {
					case IMarkerGenerator.SEVERITY_INFO:
						return images.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
					case IMarkerGenerator.SEVERITY_WARNING:
						return images.getImage(ISharedImages.IMG_OBJS_WARN_TSK);
					case IMarkerGenerator.SEVERITY_ERROR_BUILD:
					case IMarkerGenerator.SEVERITY_ERROR_RESOURCE:
						return images.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
					case RegexErrorPattern.SEVERITY_SKIP:
							return images.getImage(ISharedImages.IMG_ELCL_REMOVE_DISABLED);
					}
				}
				return null;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					return severityToString(regex.getSeverity());
				}
				return severityToString(RegexErrorPattern.SEVERITY_SKIP);
			}
		});
		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			final String[] severityComboBoxArray = new String[] {
					severityToString(IMarkerGenerator.SEVERITY_ERROR_RESOURCE),
					severityToString(IMarkerGenerator.SEVERITY_WARNING),
					severityToString(IMarkerGenerator.SEVERITY_INFO),
					severityToString(RegexErrorPattern.SEVERITY_SKIP),
				};

			private int severityToIndex(int severity) {
				String strSeverity = severityToString(severity);
				for (int i = 0; i < severityComboBoxArray.length; i++) {
					if (strSeverity.equals(severityComboBoxArray[i]))
						return i;
				}
				return 0;
			}

			private int indexToSeverity(int index) {
				String strCombo = severityComboBoxArray[index];
				for (int i = 0; i < severityComboBoxArray.length; i++) {
					if (severityToString(i).equals(strCombo))
						return i;
				}
				return RegexErrorPattern.SEVERITY_SKIP;
			}

			@Override
			protected boolean canEdit(Object element) {
				return (element instanceof RegexErrorPattern);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getTable(), severityComboBoxArray, SWT.READ_ONLY);
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof RegexErrorPattern)
					return severityToIndex(((RegexErrorPattern) element).getSeverity());
				return RegexErrorPattern.SEVERITY_SKIP;
			}

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof RegexErrorPattern && (value instanceof Integer)) {
					((RegexErrorPattern) element).setSeverity(indexToSeverity(((Integer) value).intValue()));
					tableViewer.update(element, null);
				}
			}

		});
	}
	
	private void createPatternColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer,expressionColumn);
		columnViewer.getColumn().setText("Pattern");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("Error Pattern");
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					String pattern = regex.getPattern();
					return pattern;
				}
				return "Oops";
			}
		});

		columnViewer.setEditingSupport(new RegexPatternEditingSupport(tableViewer, true) {
			@Override
			protected Object getFromPattern(RegexErrorPattern regexErrorPattern) {
				return regexErrorPattern.getPattern();
			}

			@Override
			protected void setToPattern(RegexErrorPattern regexErrorPattern, String value) {
				if (!fEditable)
					return;
				try{
					regexErrorPattern.setPattern(value);
				} catch (Exception e) {
					// to avoid recursive edits. the dialog is needed to ensure valid pattern on losing focus.
					// this looks ugly and likely incorrect
					fEditable = false;
					/*RegularExpressionStatusDialog dialog= new RegularExpressionStatusDialog(getShell(), value);
					if (dialog.open() == Window.OK) {
						regexErrorPattern.setPattern(dialog.getValue());
					}*/
					fEditable = true;
				}
			}
		});
	}
	
	private void createFileColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, fileColumn);
		columnViewer.getColumn().setText("File");
		columnViewer.getColumn().setToolTipText("");
		columnViewer.getColumn().setResizable(true);
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					return regex.getFileExpression();
				}
				return "Oops";
			}
		});
		columnViewer.setEditingSupport(new RegexPatternEditingSupport(tableViewer, false) {
			@Override
			protected Object getFromPattern(RegexErrorPattern regexErrorPattern) {
				return regexErrorPattern.getFileExpression();
			}

			@Override
			protected void setToPattern(RegexErrorPattern regexErrorPattern, String value) {
				regexErrorPattern.setFileExpression(value);
			}
		});
	}

	private void createLineColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, lineColumn);
		columnViewer.getColumn().setText("Line");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("");
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					return regex.getLineExpression();
				}
				return "Oops";
			}
		});
		columnViewer.setEditingSupport(new RegexPatternEditingSupport(tableViewer, false) {
			@Override
			protected Object getFromPattern(RegexErrorPattern regexErrorPattern) {
				return regexErrorPattern.getLineExpression();
			}

			@Override
			protected void setToPattern(RegexErrorPattern regexErrorPattern, String value) {
				regexErrorPattern.setLineExpression(value);
			}
		});
	}

	private void createDescriptionColumn() {
		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, descColumn);
		columnViewer.getColumn().setText("Description");
		columnViewer.getColumn().setResizable(true);
		columnViewer.getColumn().setToolTipText("");
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					return regex.getDescriptionExpression();
				}
				return "Oops";
			}
		});
		columnViewer.setEditingSupport(new RegexPatternEditingSupport(tableViewer, false) {
			@Override
			protected Object getFromPattern(RegexErrorPattern regexErrorPattern) {
				return regexErrorPattern.getDescriptionExpression();
			}

			@Override
			protected void setToPattern(RegexErrorPattern regexErrorPattern, String value) {
				regexErrorPattern.setDescriptionExpression(value);
			}
		});
	}
	
	private void createEatLineColumn() {
		final String EAT_NO = "No";
		final String EAT_YES = "Yes";

		final String[] eatLineComboBoxArray = new String[] { EAT_YES, EAT_NO, };
		final int EAT_YES_INDEX = 0;
		final int EAT_NO_INDEX = 1;

		TableViewerColumn columnViewer = new TableViewerColumn(tableViewer, consumeColumn);
		columnViewer.getColumn().setText("Consume");
		columnViewer.getColumn().setResizable(true);

		
		columnViewer.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RegexErrorPattern) {
					RegexErrorPattern regex = (RegexErrorPattern) element;
					if (!regex.isEatProcessedLine())
						return EAT_NO;
				}
				return EAT_YES;
			}
		});
		columnViewer.setEditingSupport(new EditingSupport(tableViewer) {
			@Override
			protected boolean canEdit(Object element) {
				return (element instanceof RegexErrorPattern) && fEditable;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getTable(), eatLineComboBoxArray, SWT.READ_ONLY);
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof RegexErrorPattern)
					if (!((RegexErrorPattern) element).isEatProcessedLine())
						return EAT_NO_INDEX;
				return EAT_YES_INDEX;
			}

			@Override
			protected void setValue(Object element, Object value) {
				if ((element instanceof RegexErrorPattern) && (value instanceof Integer)) {
					((RegexErrorPattern) element).setEatProcessedLine((Integer) value != EAT_NO_INDEX);
					tableViewer.update(element, null);
				}
			}

		});
	}
	
	private void updateButtons() {
		
		
			int pos = table.getSelectionIndex();
			int count = table.getItemCount();
			int last = count-1;
			boolean selected = pos>=0 && pos<=last;

			this.addButton.setEnabled(true);
			this.deleteButton.setEnabled(selected);
			this.moveUpButton.setEnabled(selected && pos != 0);
			this.moveDownButton.setEnabled(selected && pos != last);
	}
	
	private void buttonPressed (int button) {
		switch (button) {
		case BUTTON_ADD:
			addErrorPattern();
			break;
		case BUTTON_DELETE:
			deleteErrorPattern();
			break;
		case BUTTON_MOVEUP:
			moveItem(true);
			break;
		case BUTTON_MOVEDOWN:
			moveItem(false);
			break;
		default:
			break;
		}
		updateButtons();
	}
	
	private static RegexErrorPattern newDummyPattern() {
		return new RegexErrorPattern(null, null, null, null, null, IMarker.SEVERITY_ERROR, true);
	}
	
	private void addErrorPattern() {
		int pos = table.getSelectionIndex();
		int last = table.getItemCount()-1;
		if (pos<0 || pos>last)
			pos = last;

		int newPos = pos + 1;
		tableViewer.insert(newDummyPattern(), newPos);
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
	
	public void performApply(IProgressMonitor monitor) throws CoreException {
		if (fErrorParser!=null && fEditable) {
			fErrorParser.clearPatterns();
			for (TableItem tableItem : table.getItems()) {
				Object item = tableItem.getData();
				if (item instanceof RegexErrorPattern) {
					fErrorParser.addPattern((RegexErrorPattern)item);
				}
			}
		}
	}
	
	
	private static String severityToString(int severity) {
		switch (severity) {
		case IMarkerGenerator.SEVERITY_INFO:
			return "Info";
		case IMarkerGenerator.SEVERITY_WARNING:
			return "Warning";
		case IMarkerGenerator.SEVERITY_ERROR_BUILD:
		case IMarkerGenerator.SEVERITY_ERROR_RESOURCE:
			return "Error";
		}
		return "Ignore";
	}

	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private abstract class RegexPatternEditingSupport extends EditingSupport {
		private final TableViewer tableViewer;

		/**
		 * Constructor.
		 *
		 * @param viewer - table viewer where to provide editing support.
		 * @param isFindStyle - if "find" or "replace" style for potential content assist,
		 *     see {@link FindReplaceDocumentAdapterContentProposalProvider}.
		 */
		public RegexPatternEditingSupport(TableViewer viewer, boolean isFindStyle) {
			super(viewer);
			tableViewer = viewer;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
		 */
		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		/**
		 * The intention of RegexPatternEditingSupport is to provide Regex content assist
		 * during in-table editing. However having problems with mouse selection and
		 * {@link ContentAssistCommandAdapter} using {@link FindReplaceDocumentAdapterContentProposalProvider}
		 * is removed for time being. See bug 288982 for more details.
		 *
		 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
		 */
		@Override
		protected CellEditor getCellEditor(Object element) {
			CellEditor editor = new TextCellEditor(tableViewer.getTable());
			return editor;
		}

		/**
		 * Get value from {@link RegexErrorPattern}. This is column-specific value.
		 *
		 * @param regexErrorPattern - pattern to query.
		 * @return retrieved value
		 */
		abstract protected Object getFromPattern(RegexErrorPattern regexErrorPattern);

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
		 */
		@Override
		protected Object getValue(Object element) {
			if (element instanceof RegexErrorPattern) {
				RegexErrorPattern regexErrorPattern = (RegexErrorPattern) element;
				return getFromPattern(regexErrorPattern);
			}
			return "Oops";
		}

		/**
		 * Set value into one of the pattern's field. Which field - it's column-specific.
		 *
		 * @param regexErrorPattern - pattern to set the field
		 * @param value - value to set
		 */
		abstract protected void setToPattern(RegexErrorPattern regexErrorPattern, String value);

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
		 */
		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof RegexErrorPattern && (value instanceof String)) {
				String stringValue = (String) value;
				RegexErrorPattern errorPattern = (RegexErrorPattern) element;
				setToPattern(errorPattern, stringValue);
				tableViewer.update(element, null);
			}
		}
	}
	
	
	
}
