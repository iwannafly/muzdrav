package ru.nkz.ivcgzo.projLocChg;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

public class ProjectsRootFolderJList<E extends ProjectsRootFolder> extends JList<E> {
	private static final long serialVersionUID = -8543684181611667103L;
	private List<E> folderList;
	
	public ProjectsRootFolderJList() {
		super();
		
		setModel(new ProjectsRootFolderListModel());
	}
	
	public ProjectsRootFolderJList(List<E> folderList) {
		this();
		
		setData(folderList);
	}
	
	public void setData(List<E> folderList) {
		this.folderList = folderList;
		((ProjectsRootFolderListModel)getModel()).fireContentsChanged();
	}
	
	class ProjectsRootFolderListModel extends AbstractListModel<E> {
		private static final long serialVersionUID = -7179579924556654578L;

		@Override
		public int getSize() {
			return (folderList != null) ? folderList.size() : 0;
		}
		
		public void fireContentsChanged() {
			fireContentsChanged(ProjectsRootFolderJList.this, 0, 0);
			if (getSize() > 0) {
				setSelectedIndex(0);
				fireSelectionValueChanged(0, 0, false);
			}
		}

		@Override
		public E getElementAt(int index) {
			return folderList.get(index);
		}
	}
}
