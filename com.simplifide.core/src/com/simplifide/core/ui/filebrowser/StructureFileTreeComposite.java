package com.simplifide.core.ui.filebrowser;

import java.io.File;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class StructureFileTreeComposite extends org.eclipse.swt.widgets.Composite {
	

	
	private FileTreeComposite fileTreeComposite1;
	private List list1;
	private Button linkButton;
	private Button unLinkButton;
	private Label label1;
	private Button clearButton;
	private Button browseButton;
	private Text templateDirectoryLocation;
	private Button button1;

	private StructureFileTreeCompositeData data = new StructureFileTreeCompositeData();
	
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/

	public StructureFileTreeComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void updateList() {
		this.list1.add(StructureFileTreeCompositeData.LINK_BUILD);
		this.list1.add(StructureFileTreeCompositeData.LINK_DESIGN);
		this.list1.add(StructureFileTreeCompositeData.LINK_DOC);
		this.list1.add(StructureFileTreeCompositeData.LINK_LIBRARIES);
		this.list1.add(StructureFileTreeCompositeData.LINK_PROJECTS);
		this.list1.add(StructureFileTreeCompositeData.LINK_SCRIPT);
		this.list1.add(StructureFileTreeCompositeData.LINK_SUB);
		this.list1.add(StructureFileTreeCompositeData.LINK_TEST);
	}
	
	public void setInputStructure(StructureDirectory dir) {
		this.fileTreeComposite1.getTreeViewer().setInput(dir);
		this.fileTreeComposite1.getTreeViewer().refresh();
	}
	
	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(600, 360);
			{
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 17);
				label1LData.top =  new FormAttachment(0, 1000, 16);
				label1LData.width = 150;
				label1LData.height = 15;
				label1.setLayoutData(label1LData);
				label1.setText("Template Directory Location");
			}
			{
				clearButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData saveButtonLData = new FormData();
				saveButtonLData.left =  new FormAttachment(0, 1000, 509);
				saveButtonLData.top =  new FormAttachment(0, 1000, 127);
				saveButtonLData.width = 54;
				saveButtonLData.height = 25;
				clearButton.setLayoutData(saveButtonLData);
				clearButton.setText("Clear");
				clearButton.addSelectionListener(new ClearListener());
			}
			{
				browseButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData browseButtonLData = new FormData();
				browseButtonLData.left =  new FormAttachment(0, 1000, 509);
				browseButtonLData.top =  new FormAttachment(0, 1000, 16);
				browseButtonLData.width = 50;
				browseButtonLData.height = 25;
				browseButton.setLayoutData(browseButtonLData);
				browseButton.setText("Browse");
				browseButton.addSelectionListener(new BrowseListener());
			}
			{
				templateDirectoryLocation = new Text(this, SWT.BORDER);
				FormData templateDirectoryLocationLData = new FormData();
				templateDirectoryLocationLData.left =  new FormAttachment(0, 1000, 175);
				templateDirectoryLocationLData.top =  new FormAttachment(0, 1000, 16);
				templateDirectoryLocationLData.width = 310;
				templateDirectoryLocationLData.height = 15;
				templateDirectoryLocation.setLayoutData(templateDirectoryLocationLData);
			}
			{
				unLinkButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData unLinkButtonLData = new FormData();
				unLinkButtonLData.left =  new FormAttachment(0, 1000, 509);
				unLinkButtonLData.top =  new FormAttachment(0, 1000, 90);
				unLinkButtonLData.width = 54;
				unLinkButtonLData.height = 25;
				unLinkButton.setLayoutData(unLinkButtonLData);
				unLinkButton.setText("UnLink");
				unLinkButton.addSelectionListener(new UnLinkListener());
			}
			{
				linkButton = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData linkButtonLData = new FormData();
				linkButtonLData.left =  new FormAttachment(0, 1000, 509);
				linkButtonLData.top =  new FormAttachment(0, 1000, 53);
				linkButtonLData.width = 54;
				linkButtonLData.height = 25;
				linkButton.setLayoutData(linkButtonLData);
				linkButton.setText("Link");
				linkButton.addSelectionListener(new LinkListener());
			}
			
			{
				FormData list1LData = new FormData();
				list1LData.left =  new FormAttachment(0, 1000, 320);
				list1LData.top =  new FormAttachment(0, 1000, 45);
				list1LData.width = 170;
				list1LData.height = 187;
				list1 = new List(this, SWT.BORDER);
				list1.setLayoutData(list1LData);
			} 
			{
				FormData fileTreeComposite1LData = new FormData();
				fileTreeComposite1LData.left =  new FormAttachment(0, 1000, 10);
				fileTreeComposite1LData.top =  new FormAttachment(0, 1000, 40);
				fileTreeComposite1LData.width = 300;
				fileTreeComposite1LData.height = 200;
				fileTreeComposite1 = new FileTreeComposite(this, SWT.NONE);
				fileTreeComposite1.setLayoutData(fileTreeComposite1LData);
				fileTreeComposite1.getTreeViewer().addSelectionChangedListener(new TreeListener());
				
			}
			{
				button1 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 717);
				button1LData.top =  new FormAttachment(0, 1000, 423);
				button1LData.width = 54;
				button1LData.height = 25;
				button1.setLayoutData(button1LData);
				button1.setText("Link");
			}
			this.updateList();
			this.layout();
			this.fileTreeComposite1.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enableLink(boolean enable) {
		this.browseButton.setEnabled(enable);
		this.linkButton.setEnabled(enable);
		this.unLinkButton.setEnabled(enable);
		this.clearButton.setEnabled(enable);
	}
	
	public StructureDirectory getStructureBase() {
		Object obj = this.fileTreeComposite1.getTreeViewer().getInput();
		if (obj instanceof File) {
			File file = (File) obj;
			
			StructureDirectory struct = (StructureDirectory) StructureDirectory.createStructure(file, 
					this.data.getLinks(), file);
			return struct;
			
		}
		else if (obj instanceof StructureBase) { // This means the default which is ignored
			return null;
		}
		return null;
	}
	
	public class TreeListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
		
			File ufile = fileTreeComposite1.getSelectedFile();
			if (ufile != null) {
				String link = data.getLink(ufile);
				list1.setSelection(new String[] {link});
			}
		}
		
	}
	
	public class ClearListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			data.clearLinks();
			fileTreeComposite1.getTreeViewer().refresh();
		}
	}
	
	public class BrowseListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			DirectoryDialog dialog = new DirectoryDialog(getShell());
			dialog.open();
			String filterPath = dialog.getFilterPath();
			templateDirectoryLocation.setText(filterPath);
			fileTreeComposite1.setRootFile(filterPath);
			data.clearLinks();
			fileTreeComposite1.getTreeViewer().refresh();
		}
	}
	
	public class LinkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			File selFile = fileTreeComposite1.getSelectedFile();
			if (selFile == null) return; // Possible with No Selection or Structure File
			String[] sel = list1.getSelection();
			if (sel.length > 0 && selFile != null) {
				data.addLink(sel[0], selFile);
			}
			fileTreeComposite1.getTreeViewer().refresh();
		}
	}
	
	public class UnLinkListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			File selFile = fileTreeComposite1.getSelectedFile();
			if (selFile != null) data.removeLink(selFile);
			fileTreeComposite1.getTreeViewer().refresh();
		}
	}
	

	
	
	
}
