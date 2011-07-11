package com.simplifide.core.ui.wizard.suite_prjfile;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.List;


public class VerilogProjectTopComposite extends org.eclipse.swt.widgets.Composite {
	private List libraryList;
	private Button addButton;
	private Button editButton;
	private Button removeButton;

	
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/

	private String type;
	
	public VerilogProjectTopComposite(Composite parent, int style, String type) {
		super(parent, style);
		this.type = type;
		initGUI();
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(492, 411);
			{
				editButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData editButtonLData = new FormData();
				editButtonLData.left =  new FormAttachment(0, 1000, 360);
				editButtonLData.top =  new FormAttachment(0, 1000, 108);
				editButtonLData.width = 113;
				editButtonLData.height = 25;
				editButton.setLayoutData(editButtonLData);
				editButton.setText("Edit " + type);
			}
			{
				removeButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData removeButtonLData = new FormData();
				removeButtonLData.left =  new FormAttachment(0, 1000, 360);
				removeButtonLData.top =  new FormAttachment(0, 1000, 69);
				removeButtonLData.width = 113;
				removeButtonLData.height = 27;
				removeButton.setLayoutData(removeButtonLData);
				removeButton.setText("Remove " + type);
				removeButton.addSelectionListener(new RemoveLibraryListener());
			}
			{
				addButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData addButtonLData = new FormData();
				addButtonLData.left =  new FormAttachment(0, 1000, 360);
				addButtonLData.top =  new FormAttachment(0, 1000, 32);
				addButtonLData.width = 113;
				addButtonLData.height = 25;
				addButton.setLayoutData(addButtonLData);
				addButton.setText("Add " + type);
				addButton.addSelectionListener(new AddLibraryListener());
			}
			{
				FormData libraryListLData = new FormData();
				libraryListLData.left =  new FormAttachment(0, 1000, 39);
				libraryListLData.top =  new FormAttachment(0, 1000, 22);
				libraryListLData.width = 291;
				libraryListLData.height = 353;
				libraryList = new List(this, SWT.BORDER);
				libraryList.setLayoutData(libraryListLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class AddLibraryListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.open();
			libraryList.add(dialog.getFilterPath());
			
		}
	}
	
	public class RemoveLibraryListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			int[] sel = libraryList.getSelectionIndices();
			libraryList.remove(sel);
		}
	}
	
	public String[] getLibraries() {
		return libraryList.getItems();
	}
	
}
