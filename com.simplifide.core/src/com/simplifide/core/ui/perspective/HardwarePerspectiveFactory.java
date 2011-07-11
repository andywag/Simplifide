/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.perspective;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.views.ClassView;
import com.simplifide.core.ui.views.HierarchyView;
import com.simplifide.core.ui.views.ItemView;
import com.simplifide.core.ui.wizard.VerilogFileWizard;
import com.simplifide.core.ui.wizard.VhdlFileWizard;
import com.simplifide.core.ui.wizard.project2.LibraryWizard2;
import com.simplifide.core.ui.wizard.project2.ProjectWizard2;
import com.simplifide.core.ui.wizard.suite_classic.SuiteWizard;
import com.simplifide.core.ui.wizard.suite_import.SuiteImportWizard;
import com.simplifide.core.ui.wizard.suite_prjfile.VerilogSuiteWizard;

public class HardwarePerspectiveFactory implements IPerspectiveFactory{

	public static String ID = CoreActivator.PLUGIN_ID + ".ui.perspective.HardwarePerspectiveFactory";
	public static String PID = "com.simplifide.core.views.navigatorView";
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		String editorArea = layout.getEditorArea();
		
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, .2f, editorArea);
		//IFolderLayout right = layout.createFolder("right", IPageLayout.RIGHT, .8f, editorArea);
		
		IFolderLayout leftbot = layout.createFolder("leftbot", IPageLayout.BOTTOM, .5f, "left");
		IFolderLayout rightbot = layout.createFolder("rightbot", IPageLayout.BOTTOM, .8f, editorArea);
		IFolderLayout rightright = layout.createFolder("rightcent", IPageLayout.RIGHT, .8f, editorArea);
		
		left.addView(IPageLayout.ID_RES_NAV);
		
		left.addView(PID);
		leftbot.addView(HierarchyView.ID);
		leftbot.addView(ItemView.ID);
		leftbot.addView(ClassView.ID);
		
		rightbot.addView(IPageLayout.ID_PROBLEM_VIEW);
		rightbot.addView(IPageLayout.ID_TASK_LIST);
		
		rightright.addView(IPageLayout.ID_OUTLINE);
		
		
		layout.addNewWizardShortcut(SuiteImportWizard.ID_WIZARD);
		layout.addNewWizardShortcut(VerilogSuiteWizard.ID_WIZARD);
		layout.addNewWizardShortcut(SuiteWizard.ID_WIZARD);
		layout.addNewWizardShortcut(ProjectWizard2.ID_WIZARD);
		layout.addNewWizardShortcut(LibraryWizard2.ID_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_ENTITY_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_PACKAGE_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_ARCH_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_ENT_ARCH_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_TESTBENCH_WIZARD);
		layout.addNewWizardShortcut(VhdlFileWizard.VHDL_TESTER);
		layout.addNewWizardShortcut(VerilogFileWizard.MODULE_WIZARD);
		layout.addNewWizardShortcut(VerilogFileWizard.PROGRAM_WIZARD);
		layout.addNewWizardShortcut(VerilogFileWizard.PACKAGE_WIZARD);
		layout.addNewWizardShortcut(VerilogFileWizard.INTERFACE_WIZARD);
		layout.addNewWizardShortcut(VerilogFileWizard.CLASS_WIZARD);
		layout.addNewWizardShortcut(VerilogFileWizard.PACKAGE_TESTBENCH);
		//layout.addView(IPageLayout.ID_RES_NAV, IPageLayout.LEFT, .25f, editorArea);
		
		
		
		//layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, .25f, editorArea);
		//layout.addView(IPageLayout.ID_PROBLEM_VIEW, IPageLayout.BOTTOM, .2f	, editorArea);
		
	}

}
