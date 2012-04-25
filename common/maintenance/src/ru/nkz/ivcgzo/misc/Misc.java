package ru.nkz.ivcgzo.misc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Set;

public class Misc {
	
	public static String createTempFile(String userName) throws Exception {
		Path tmp = createTempFile();
		setReadWriteFilePermissions(tmp, userName);
		return tmp.toString();
	}
	
	public static Path createTempFile() throws Exception {
		Path tmp = Files.createTempFile("tmp", ".tmp");
		return tmp;
	}
	
	public static void setReadWriteFilePermissions(Path file, String userName) throws Exception {
		if (isWindows()) {
			AclFileAttributeView attrView = Files.getFileAttributeView(file, AclFileAttributeView.class);
			List<AclEntry> aclList = attrView.getAcl();
			UserPrincipal principal = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(userName);
			AclEntry perms = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(principal).setPermissions(AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_DATA, AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.APPEND_DATA, AclEntryPermission.SYNCHRONIZE).build();
			aclList.add(perms);
			attrView.setAcl(aclList);
		} else {
			PosixFileAttributeView attrView = Files.getFileAttributeView(file, PosixFileAttributeView.class);
			Set<PosixFilePermission> permsSet = attrView.readAttributes().permissions();
			permsSet.add(PosixFilePermission.GROUP_READ);
			permsSet.add(PosixFilePermission.OTHERS_READ);
			permsSet.add(PosixFilePermission.GROUP_WRITE);
			permsSet.add(PosixFilePermission.OTHERS_WRITE);
			attrView.setPermissions(permsSet);
		}
	}
	
	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}
}
