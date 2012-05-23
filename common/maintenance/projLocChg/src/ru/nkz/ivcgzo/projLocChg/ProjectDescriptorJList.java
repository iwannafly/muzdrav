package ru.nkz.ivcgzo.projLocChg;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

public class ProjectDescriptorJList<E extends ProjectDescriptor> extends JList<E> {
	private static final long serialVersionUID = -5928798910374266250L;
	private List<E> descriptorList;
	
	public ProjectDescriptorJList() {
		super();
		
		setModel(new ProjectDescriptorListModel());
	}
	
	public ProjectDescriptorJList(List<E> folderList) {
		this();
		
		setData(folderList);
	}
	
	public void setData(List<E> folderList) {
		this.descriptorList = folderList;
		((ProjectDescriptorListModel)getModel()).fireContentsChanged();
	}
	
	class ProjectDescriptorListModel extends AbstractListModel<E> {
		private static final long serialVersionUID = -7179579924556654578L;

		@Override
		public int getSize() {
			return (descriptorList != null) ? descriptorList.size() : 0;
		}
		
		public void fireContentsChanged() {
			fireContentsChanged(ProjectDescriptorJList.this, 0, 0);
			if (getSize() > 0) {
				setSelectedIndex(0);
				fireSelectionValueChanged(0, 0, false);
			}
		}

		@Override
		public E getElementAt(int index) {
			return descriptorList.get(index);
		}
	}
}
