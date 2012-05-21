package ru.nkz.ivcgzo.projLocChg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectDescriptor {
	private String path;
	private boolean external;
	private byte[] bin0;
	private String refPath;
	private byte[] bin1;
	private String rootFolder;
	private String relRefPath;
	
	public ProjectDescriptor(String path) throws FileNotFoundException, IOException, URISyntaxException {
		this.path = path;
		this.refPath = "";
		
		readLocFile();
	}
	
	private void readLocFile() throws FileNotFoundException, IOException, URISyntaxException {
		File file = new File(path, ".location");
		path = file.getCanonicalPath();
		
		if (file.exists()) {
			try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
				bin0 = new byte[0x10];
				raf.read(bin0);
				refPath = raf.readUTF();
				bin1 = new byte[(int) (raf.length() - raf.getFilePointer())];
				raf.read(bin1);
				
				if (refPath.length() > 0) {
					URI uri = new URI(refPath.replaceFirst("URI//", ""));
					refPath = new File(uri).getCanonicalPath();
					external = true;
				}
			}
		}
		
		if (refPath.length() == 0) {
			file = file.getParentFile();
			String projName = file.getName();
			refPath = new File(file.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile(), projName).getCanonicalPath();
		}
	}
	
	public boolean checkRootPath(String rootPath) {
		try {
			checkRefPath(new File(rootPath, getRelativeReferencePath()).getCanonicalPath());
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	private void checkRefPath(String refPath) throws FileNotFoundException {
		File file = new File(refPath);
		
		if (!file.exists())
			throw new FileNotFoundException(String.format("Project folder '%s' not found.", refPath));
		
		if (!new File(file, ".classpath").exists())
			throw new FileNotFoundException(String.format("Folder '%s' doesn't contain project.", refPath));
		
		if (!new File(file, ".project").exists())
			throw new FileNotFoundException(String.format("Folder '%s' doesn't contain project.", refPath));
	}

	public String getPath() {
		return path;
	}
	
	public String getReferencePath() {
		return refPath;
	}

	public String getRootPath() {
		return rootFolder;
	}
	
	public String getRelativeReferencePath() {
		return relRefPath;
	}
	
	public boolean isExternal() {
		return external;
	}
	
	public void setRootFolder(String rootFolder) {
		try {
			Path refPath = Paths.get(getReferencePath());
			Path rootPath = Paths.get(rootFolder);
			Path relRefPath = rootPath.relativize(refPath);
			
			this.rootFolder = rootPath.toFile().getCanonicalPath();
			this.relRefPath = relRefPath.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void saveLocFile(String newRootPath) throws FileNotFoundException, IOException, URISyntaxException {
		if (!isExternal())
			return;
		
		File refPathfiFile = new File(newRootPath, getRelativeReferencePath()); 
		refPath = refPathfiFile.getCanonicalPath();
		checkRefPath(refPath);
		
		try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
			raf.write(bin0);
			raf.writeUTF("URI//" + refPathfiFile.toURI());
			raf.write(bin1);
		}
	}
	
	@Override
	public String toString() {
		return getRelativeReferencePath();
	}
}
