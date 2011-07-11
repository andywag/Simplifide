package com.simplifide.core.project.preference;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MainProjectPreferenceComposite extends Composite {

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public MainProjectPreferenceComposite(Composite parent, int style) {
		super(parent, style);

		final TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, 500, 375);

		final TabItem simulationTabItem = new TabItem(tabFolder, SWT.NONE);
		simulationTabItem.setText("Signals");

		final PreferenceClkPage preferenceClkPage = new PreferenceClkPage(tabFolder, SWT.NONE);
		simulationTabItem.setControl(preferenceClkPage);

		new TabItem(tabFolder, SWT.NONE);
		//
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
