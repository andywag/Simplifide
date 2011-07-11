package com.simplifide.core.errorparser.newui;

import org.eclipse.swt.widgets.Composite;

public class ErrorBuildPreferenceComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ErrorBuildPreferenceComposite(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
