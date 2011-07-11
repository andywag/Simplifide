package com.simplifide.core.errorparser.newui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.ErrorParserManager;
import com.simplifide.core.errorparser.IErrorParserNamed;
import com.simplifide.core.errorparser.RegexErrorParser;

public class ParserListComposite extends Composite {
	
	private static final int DEFAULT_HEIGHT = 130;
	private static final int BUTTON_ADD = 0;
	private static final int BUTTON_EDIT = 1;
	private static final int BUTTON_DELETE = 2;
	// there is a separator instead of button = 3
	private static final int BUTTON_MOVEUP = 4;
	private static final int BUTTON_MOVEDOWN = 5;
	
	private Button addButton;
	private Button deleteButton;
	private Button editButton;
	private Button moveUpButton;
	private Button moveDownButton;
	
	private Table fTable;
	private CheckboxTableViewer fTableViewer;
	
	private RegExpEntryTable tableComposite;
	
	private final Map<String, IErrorParserNamed> fAvailableErrorParsers = new LinkedHashMap<String, IErrorParserNamed>();
	


	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ParserListComposite(Composite parent, int style) {
		super(parent, style);
		
		addButton = new Button(this, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addErrorParser();
				updateButtons();
			}
		});
		addButton.setBounds(645, 10, 75, 25);
		addButton.setText("Add");
		
		editButton = new Button(this, SWT.NONE);
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editErrorParser();
				updateButtons();
			}
		});
		editButton.setBounds(645, 41, 75, 25);
		editButton.setText("Edit");
		
		deleteButton = new Button(this, SWT.NONE);
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteErrorParser();
				updateButtons();
			}
		});
		deleteButton.setBounds(645, 72, 75, 25);
		deleteButton.setText("Delete");
		
		moveUpButton = new Button(this, SWT.NONE);
		moveUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(true);
				updateButtons();
			}
		});
		moveUpButton.setBounds(645, 103, 75, 25);
		moveUpButton.setText("Move Up");
		
		moveDownButton = new Button(this, SWT.NONE);
		moveDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveItem(false);
				updateButtons();
			}
		});
		moveDownButton.setBounds(645, 134, 75, 25);
		moveDownButton.setText("Move Down");
		
		fTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER | SWT.FULL_SELECTION);
		fTable = fTableViewer.getTable();
		fTable.setBounds(10, 10, 619, 240);
		
		fTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		fTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displaySelectedOptionPage();
				updateButtons();
		}});
		fTableViewer = new CheckboxTableViewer(fTable);
		
		tableComposite = new RegExpEntryTable(this, SWT.NONE, null);
		tableComposite.setBounds(0, 280, 720, 300);
		fTableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return (Object[])inputElement;
			}
			public void dispose() {}
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		});
		fTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof String) {
					String id = (String)element;
					IErrorParserNamed errorParser = fAvailableErrorParsers.get(id);
					if (errorParser!=null) {
						String name = errorParser.getName();
						if (name!=null && name.length()>0) {
							return name;
						}
					}
					return "Error";
				}
				return "Error";
			}
		});

		/*fTableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent e) {
				saveChecked();
			}});*/ 
		this.initMapParsers();

	}
	
	private void initMapParsers() {
		fAvailableErrorParsers.clear();
		//fOptionsPageMap.clear();
		for (String id : ErrorParserManager.getErrorParserAvailableIds()) {
			IErrorParserNamed errorParser = ErrorParserManager.getErrorParserCopy(id);
			fAvailableErrorParsers.put(id, errorParser);
			initializeOptionsPage(id);
		}

		String ids[];
		
		fTableViewer.setInput(fAvailableErrorParsers.keySet().toArray(new String[0]));
		ids = ErrorParserManager.getDefaultErrorParserIds();
		fTableViewer.setCheckedElements(ids);

		displaySelectedOptionPage();
	}
	
	
	

	// Move item up / down
	private void moveItem(boolean up) {
		int n = fTable.getSelectionIndex();
		if (n < 0 || (up && n == 0) || (!up && n+1 == fTable.getItemCount()))
			return;

		String id = (String)fTableViewer.getElementAt(n);
		boolean checked = fTableViewer.getChecked(id);
		fTableViewer.remove(id);
		n = up ? n-1 : n+1;
		fTableViewer.insert(id, n);
		fTableViewer.setChecked(id, checked);
		fTable.setSelection(n);

		//saveChecked();
	}


	private String makeId(String name) {
		return CoreActivator.PLUGIN_ID+'.'+name;
	}
	
	private void addErrorParser() {
		IInputStatusValidator inputValidator = new IInputStatusValidator() {
			public IStatus isValid(String newText) {
				return Status.OK_STATUS;
			}

		};
		InputStatusDialog addDialog = new InputStatusDialog(this.getShell(),
				"Add Parser", //$NON-NLS-1$
				"Enter Parser Name", //$NON-NLS-1$
				"Default" , //$NON-NLS-1$
				inputValidator);
		addDialog.setHelpAvailable(false);

		if (addDialog.open() == Window.OK) {
			String newName = addDialog.getValue();
			String newId = makeId(newName);
			IErrorParserNamed errorParser = new RegexErrorParser(newId, newName);
			fAvailableErrorParsers.put(newId, errorParser);

			fTableViewer.add(newId);
			fTableViewer.setChecked(newId, true);
			fTable.setSelection(fTable.getItemCount()-1);

			initializeOptionsPage(newId);
			displaySelectedOptionPage();
			updateButtons();
		}
	}

	private void editErrorParser() {
		int n = fTable.getSelectionIndex();
		Assert.isTrue(n>=0);

		String id = (String)fTableViewer.getElementAt(n);
		IErrorParserNamed errorParser = fAvailableErrorParsers.get(id);

		IInputStatusValidator inputValidator = new IInputStatusValidator() {
			public IStatus isValid(String newText) {
				return Status.OK_STATUS;
			}

		};
		InputStatusDialog addDialog = new InputStatusDialog(this.getShell(),
				"Edit Parser Name", //$NON-NLS-1$
				"Parser Name", //$NON-NLS-1$
				errorParser.getName(),
				inputValidator);
		addDialog.setHelpAvailable(false);

		if (addDialog.open() == Window.OK) {
			errorParser.setName(addDialog.getValue());
			fTableViewer.refresh(id);
		}
	}

	private void deleteErrorParser() {
		int n = fTable.getSelectionIndex();
		if (n < 0)
			return;

		TableItem[] items = fTable.getSelection();
		String key = (String) items[0].getData();
		fAvailableErrorParsers.remove(key);
		
		fTableViewer.remove(fTableViewer.getElementAt(n));

		int last = fTable.getItemCount() - 1;
		if (n>last)
			n = last;
		if (n>=0)
			fTable.setSelection(n);
		
		//fAvailableErrorParsers.remove(key)
		//saveChecked();
	}
	
	private boolean isErrorParsersEditable() {
		return true;
	}
	
	public void updateButtons() {
		int pos = fTable.getSelectionIndex();
		int count = fTable.getItemCount();
		int last = count - 1;
		boolean selected = pos >= 0 && pos <= last;
		String id = (String)fTableViewer.getElementAt(pos);

		addButton.setEnabled(isErrorParsersEditable());
		editButton.setEnabled(isErrorParsersEditable() && selected);
		deleteButton.setEnabled(isErrorParsersEditable() && selected);
		moveUpButton.setEnabled(selected && pos != 0);
		moveDownButton.setEnabled(selected && pos != last);
		
	}
	
	private boolean userKey(String key) {
		String[] ids = ErrorParserManager.getDefaultErrorParserIds();
		for (String id : ids) {
			if (key.equalsIgnoreCase(id)) return false;
		}
		return true;
	}
	
	public void saveState() {
		
		try {
			this.tableComposite.performApply(null);
		} catch (CoreException e1) {
			HardwareLog.logError(e1);
		}
		ArrayList<IErrorParserNamed> parsers = new ArrayList<IErrorParserNamed>();
		Set<String> keys = fAvailableErrorParsers.keySet();
		for (String key : keys) {
			parsers.add(fAvailableErrorParsers.get(key));
		}
		
		try {
			ErrorParserManager.setUserDefinedErrorParsers(parsers.toArray(new IErrorParserNamed[parsers.size()]));
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	private void initializeOptionsPage(String id) {
		
		IErrorParserNamed errorParser = fAvailableErrorParsers.get(id);
		if (errorParser!=null) {
			String name = errorParser.getName();
			if (name!=null && name.length()>0) {
				// RegexErrorParser has an Options page
				if (errorParser instanceof RegexErrorParser) {
					tableComposite.changeErrorParser((RegexErrorParser)errorParser);
				}
			}
		}
	}
	
	private void displaySelectedOptionPage() {
		if (tableComposite != null)
			tableComposite.setVisible(false);

		int pos = fTable.getSelectionIndex();
		if (pos<0)
			return;

		String parserId = (String)fTable.getItem(pos).getData();
		this.initializeOptionsPage(parserId);
		
		this.tableComposite.setVisible(true);
		/*
		ICOptionPage optionsPage = fOptionsPageMap.get(parserId);
		if (optionsPage != null) {
			optionsPage.setVisible(true);
		}
		fCurrentOptionsPage = optionsPage; */
		
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
