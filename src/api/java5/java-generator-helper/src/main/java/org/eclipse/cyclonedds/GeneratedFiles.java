package org.eclipse.cyclonedds;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class GeneratedFiles {

	HashMap<String, GenerateFile> generatedFiles= new HashMap<String, GenerateFile>();
	
	public void addGeneratedFile(String idlPath,
			String td,
			String pkgPrefix,
			String typeName,
			String javaFile
			) {
		
		if (pkgPrefix == null || pkgPrefix.isEmpty()) {
			String pkg[] = typeName.split("\\.");
			if(pkg.length > 1) {
				pkgPrefix = String.join(".", Arrays.copyOfRange(pkg, 0, pkg.length-1));
			} else {
				pkgPrefix = typeName;
			}
		}
		generatedFiles.put(typeName, new GenerateFile(idlPath, td, pkgPrefix, typeName, javaFile));
	}
	
	public String getJavaFile(String typeName) {
		return generatedFiles.get(typeName).javaFile;
	}
	
	public String getJavaFilePath(String typeName) {
		File f = new File(getJavaFile(typeName));
		try {
			return f.getParentFile().getCanonicalPath()+File.separator;
		} catch (IOException e) {
			return null;
		}
	}
	
	public String getJavaPackage(String typeName) {		
		return generatedFiles.get(typeName).pkgPrefix;
	}
	
	public String getJavaHelperPath(String typeName) {
		return generatedFiles.get(typeName).javaHelperPath;
	}
	
	public String getJavaHelperFile(String typeName) {
		return generatedFiles.get(typeName).javaHelperFile;
	}
	
	public Set<String> getTypeNames(){
		return generatedFiles.keySet();
	}
	
	public HashMap<String, GenerateFile> getGeneratedFiles(){
		return generatedFiles;
	}

	public String getOldClassName(String typeName) {
		return generatedFiles.get(typeName).oldClassName;
	}

	public String getNewClassName(String typeName) {
		return generatedFiles.get(typeName).newClassName;
	}
	
	@Override
	public String toString() {

		Set<String> typeNames = getTypeNames();
		
		
		for (String typeName : typeNames) {			
			
			System.out.println("\n**************\ntypeName: "+typeName);
			
			print(getJavaFilePath(typeName),
					 getJavaPackage(typeName),
					 getJavaHelperPath(typeName),
					 getJavaHelperFile(typeName),
					 getOldClassName(typeName),
					 getNewClassName(typeName)
			);
		}
		
		return super.toString();
	}

	private void print(String javaFilePath, String javaPackage, String javaHelperPath, String javaHelperFile, String oldClassName, String newClassName) {
		System.out.printf(
				"javaFilePath:%s\n\tjavaPackage:%s\n\tjavaHelperPath:%s\n\tjavaHelperFile:%s\n\toldClassName:%s\n\tnewClassName:%s\n",
				javaFilePath, javaPackage, javaHelperPath, javaHelperFile, oldClassName, newClassName);
	}
}
