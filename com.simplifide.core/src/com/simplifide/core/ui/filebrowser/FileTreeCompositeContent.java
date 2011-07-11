package com.simplifide.core.ui.filebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.resources.BasicIconManager;

public class FileTreeCompositeContent {

	public static class Content implements ITreeContentProvider {
		
		public Content() {}
		
		public Object[] getChildren(Object arg0) {
			if (arg0 instanceof File) {
				File file = (File) arg0;
				return file.listFiles();
			}
			else if (arg0 instanceof StructureDirectory) {
				StructureDirectory base = (StructureDirectory) arg0;
				return base.getChildren().toArray();
			}
			return null;

		}

		public Object getParent(Object arg0) {
			if (arg0 instanceof File) {
				return ((File) arg0).getParentFile();
			}
			else if (arg0 instanceof StructureBase) {
				return ((StructureBase) arg0).getParent();
			}
			return null;
		}

		public boolean hasChildren(Object arg0) {
			Object[] obj = getChildren(arg0);
			return obj == null ? false : obj.length > 0;
		}

		public Object[] getElements(Object arg0) {
			if (arg0 instanceof File) {
				return getChildren(arg0);
			}
			else if (arg0 instanceof StructureDirectory) {
				return getChildren(arg0);
			}
			else if (arg0 instanceof StructureBase) {
				return null;
			}
			return File.listRoots();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}

	}

	public static class Label implements ILabelProvider {
		private List listeners;

		private Image file;
		private Image dir;

		private StructureFileTreeCompositeData data;

		
		public Label() {
			listeners = new ArrayList();
			dir = BasicIconManager.getRealImage(BasicIconManager.MAIN_FOLDER);
			file = BasicIconManager.getRealImage(BasicIconManager.MAIN_FORM);
		}
		
		

		public Image getImage(Object arg0) {
			if (arg0 instanceof File) {
				return ((File) arg0).isDirectory() ? dir : file;
			}
			else if (arg0 instanceof StructureBase) {
				StructureBase struct = (StructureBase) arg0;
				if (struct.isDirectory()) return dir;
				return file;
			}
			return null;

		}

		public String getText(Object arg0) {
			if (arg0 instanceof File) {
				File file = (File) arg0;
				String text = file.getName();
				if (data != null) {
					String link = data.getLink(file);
					if (link != null) text += "(" + link + ")";
				}
				return text;
			}
			else if (arg0 instanceof StructureBase) {
				StructureBase struct = (StructureBase) arg0;
				String name = struct.getName();
				String link = struct.getLinkName();
				if (link != null && !link.equalsIgnoreCase("")) {
					name += "(" + link + ")";
				}
				return name;
			}

			return "";

		}

		public void addListener(ILabelProviderListener arg0) {
			listeners.add(arg0);
		}

		public void dispose() {
			// Dispose the images
			if (dir != null)
				dir.dispose();
			if (file != null)
				file.dispose();
		}

		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}

		public void removeListener(ILabelProviderListener arg0) {
			listeners.remove(arg0);
		}



		public void setData(StructureFileTreeCompositeData data) {
			this.data = data;
		}



		public StructureFileTreeCompositeData getData() {
			return data;
		}

	}

}
