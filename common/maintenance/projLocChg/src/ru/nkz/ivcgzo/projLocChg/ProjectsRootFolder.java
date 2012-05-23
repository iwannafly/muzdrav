package ru.nkz.ivcgzo.projLocChg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectsRootFolder {
	private String path;
	private List<ProjectDescriptor> projectDescriptors;
	
	public ProjectsRootFolder(String path, List<ProjectDescriptor> projectDescriptors) {
		this.path = path;
		this.projectDescriptors = projectDescriptors;
	}
	
	public String getPath() {
		return path;
	}
	
	public List<ProjectDescriptor> getProjectDescriptors() {
		return projectDescriptors;
	}
	
	@Override
	public String toString() {
		return getPath();
	}
	
	public static List<ProjectsRootFolder> getFolders(List<ProjectDescriptor> projectDescriptorList) throws IOException {
		List<ProjectsRootFolder> folderList = new ArrayList<>();
		
		while (projectDescriptorList.size() > 0) {
			List<ProjectDescriptor> tempProjDescList = new ArrayList<>();
			
			ProjectDescriptor compareProjDesc = projectDescriptorList.get(0);
			File compProjFullPath = new File(compareProjDesc.getReferencePath());
			File compProjPrevPath = compProjFullPath.toPath().getRoot().toFile();
			File compProjNextPath;
			projectDescriptorList.remove(0);
			
			itemFoundLoop:
			while (tempProjDescList.size() == 0) {
				compProjNextPath = getNextPath(compProjFullPath, compProjPrevPath);
				for (ProjectDescriptor projDesc : projectDescriptorList) {
					if (projDesc.getReferencePath().indexOf(compProjNextPath.getCanonicalPath()) > -1) {
						compProjPrevPath = compProjNextPath;
						continue itemFoundLoop;
					}
				}
				
				tempProjDescList.add(compareProjDesc);
				compareProjDesc.setRootFolder(compProjPrevPath.getCanonicalPath());
				for (ProjectDescriptor projDesc : projectDescriptorList) {
					if (projDesc.getReferencePath().indexOf(compProjPrevPath.getCanonicalPath()) == 0) {
						projDesc.setRootFolder(compProjPrevPath.getCanonicalPath());
						tempProjDescList.add(projDesc);
					}
				}
				
				for (ProjectDescriptor tempProjDesc : tempProjDescList) {
					projectDescriptorList.remove(tempProjDesc);
				}
				
				folderList.add(new ProjectsRootFolder(compProjPrevPath.getAbsolutePath(), tempProjDescList));
			}
		}
		
		return folderList;
	}
	
	private static File getNextPath(File fullPath, File path) {
		File prevPath = fullPath;
		File fullPathParent = fullPath.getParentFile();
		
		while (fullPathParent != null) {
			if (fullPathParent.equals(path))
				break;
			prevPath = fullPathParent;
			fullPathParent = fullPathParent.getParentFile();
		}
		
		return prevPath;
	}
}
