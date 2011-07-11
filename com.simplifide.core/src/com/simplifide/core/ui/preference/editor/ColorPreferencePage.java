/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.editor;



import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;


public class ColorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage{

	private List list;
	private ColorSelector color;
	private Button bold;
	private Button italic;
	
	private IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
	
	
	
	
	private RGB[] colors = new RGB[PreferenceConstants.COLOR_LIST.length];
	private boolean[] bolds = new boolean[PreferenceConstants.COLOR_LIST.length];
	private boolean[] italics = new boolean[PreferenceConstants.COLOR_LIST.length];
	
	public ColorPreferencePage() {
		this.setPreferenceStore(store);
	}
	
	public boolean performOk() {
		for (int i=0;i<PreferenceConstants.COLOR_LIST_KEY.length;i++) {
			String value = PreferenceConstants.COLOR_LIST_KEY[i];
			
			PreferenceConverter.setValue(store, PreferenceConstants.COLOR_BASE + value, colors[i]);
			store.setValue(PreferenceConstants.BOLD_BASE + value, bolds[i]);
			store.setValue(PreferenceConstants.ITALIC_BASE + value, italics[i]);
			
			
		}
		return true;
	}
	
	
	private Composite createFontEntry(Composite parent) {
		Composite composite = new Composite(parent,SWT.RIGHT);
    	composite.setBackground(parent.getBackground());
    	GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        composite.setLayout(layout);
        
     
        this.color = new ColorSelector(composite);
        this.color.addListener(new ColorListener());
        
        this.bold = new Button(composite,SWT.CHECK);
        this.bold.addListener(SWT.Selection, new BoldListener());
        this.bold.setText("Bold");
        
        this.italic = new Button(composite,SWT.CHECK);
        this.italic.addListener(SWT.Selection, new ItalicListener());
        this.italic.setText("Italic");
        return composite;
	}
	
	private Composite createList(Composite parent) {
		Composite composite = new Composite(parent,SWT.LEFT);
    	composite.setBackground(parent.getBackground());
    	GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        composite.setLayout(layout);
		list = new List(parent, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		list.setItems(PreferenceConstants.COLOR_LIST);
		list.setLayoutData(new GridData(100 , 200));
		list.addSelectionListener(new ListListener());
		return composite;
	}
	
	private void initializeValues() {
		for (int i=0;i<PreferenceConstants.COLOR_LIST_KEY.length;i++) {
			String value = PreferenceConstants.COLOR_LIST_KEY[i];
			String preferenceColor = PreferenceConstants.COLOR_BASE + value;
			String uvalue = PreferenceConstants.COLOR_DEFAULT;
			colors[i] = PreferenceConverter.getColor(store, PreferenceConstants.COLOR_BASE + value);
			bolds[i] = store.getBoolean(PreferenceConstants.BOLD_BASE + value);
			italics[i] = store.getBoolean(PreferenceConstants.ITALIC_BASE + value);
		}
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent,SWT.NULL);
    	composite.setBackground(parent.getBackground());
    	GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        composite.setLayout(layout);
        this.initializeValues();
        this.createList(composite);
        this.createFontEntry(composite);
        
         return composite;
	}

	public void init(IWorkbench workbench) {}
	
	private void listChanged() {
		int index = list.getSelectionIndex();
		this.color.setColorValue(colors[index]);
		
		this.bold.setSelection(bolds[index]);
		this.italic.setSelection(italics[index]);
	}
	
	public class ListListener implements SelectionListener{

		private ListListener() {}
		public void widgetDefaultSelected(SelectionEvent e) {
			listChanged();
		}

		public void widgetSelected(SelectionEvent e) {
			listChanged();
		}	
	}
	
	public class ColorListener implements IPropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			int index = list.getSelectionIndex();
			colors[index] = color.getColorValue();
		}
		
	}
	
	public class BoldListener implements Listener {

		public void handleEvent(Event event) {
			int index = list.getSelectionIndex();
			bolds[index] =  bold.getSelection();
		}
		
	}
	
	public class ItalicListener implements Listener {

		public void handleEvent(Event event) {
			int index = list.getSelectionIndex();
			italics[index] =  italic.getSelection();
		}
		
	}
	

}
