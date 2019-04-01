package org.eclipse.cyclonedds;

import java.io.File;

public class GenerateFile {
	String idlPath;
	String td;
	String pkgPrefix;
	String typeName;
	String javaFile;
	String javaHelperPath;
	String javaHelperFile;
	String oldClassName;
	String newClassName;

	public GenerateFile(String idlPath,
			String td,
			String pkgPrefix,
			String typeName,
			String javaFile
			) {
		this.idlPath = idlPath;
		this.td = td;
		this.pkgPrefix = pkgPrefix;
		this.typeName = typeName;
		this.javaFile = javaFile;
		javaHelperPath = javaFile.replace(".java", "_Helper.java");
		
		String folders[] = javaHelperPath.split(File.separator);
		javaHelperFile = folders.length > 1 ? folders[folders.length-1]:javaHelperPath;
		
		oldClassName = typeName.replace(".", "_");		
		String[] tName = typeName.split("\\.");
		newClassName = tName[tName.length-1];
	}
}