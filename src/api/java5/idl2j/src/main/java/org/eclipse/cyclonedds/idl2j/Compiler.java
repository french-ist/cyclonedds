/**
 *                             Vortex Cafe
 *
 *    This software and documentation are Copyright 2010 to 2019 ADLINK
 *    Technology Limited, its affiliated companies and licensors. All rights
 *    reserved.
 *
 *    Licensed under the ADLINK Software License Agreement Rev 2.7 2nd October
 *    2014 (the "License"); you may not use this file except in compliance with
 *    the License.
 *    You may obtain a copy of the License at:
 *                        docs/LICENSE.html
 *
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.eclipse.cyclonedds.idl2j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.eclipse.cyclonedds.JavaGeneratorHelper;
import org.eclipse.cyclonedds.idl2j.KeyListDescriptor;
import org.eclipse.cyclonedds.idl2j.KeyListParser;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Compiler
{

	// Maven logger
	private Log log;

	// arguments
	private String[] args;

	// destination directory
	protected String destDir;

	// verbose mode
	private boolean verbose;

	// default package prefix
	private String defaultPkgPrefix;

	protected String className;

	// default constructor (uses SystemStreamLog as Maven logger)
	protected Compiler()
	{
		this(new SystemStreamLog());
	}

	// constructor called by Mojo
	protected Compiler(Log log)
	{
		this.log = log;
		this.destDir = "";
		this.verbose = false;
		this.defaultPkgPrefix = "";
	}

	private Compiler(String[] args) {
		try
		{
			// add idlj command as first argument
			String[] processArgs = new String[args.length + 1];
			processArgs[0] = "idlj";
			System.arraycopy(args, 0, processArgs, 1, args.length);


			// start Compiler
			Compiler compiler = new Compiler();
			compiler.parseArgs(processArgs);
			compiler.runIdlj();
			compiler.patchGeneratedCode();
			if ( !compiler.defaultPkgPrefix.isEmpty())
			{
				compiler.patchNonScopedGeneratedCode();
			}
			JavaGeneratorHelper jgh = new JavaGeneratorHelper();
			String idlFile = args[args.length - 1];	
			jgh.execute(idlFile, 
					compiler.destDir, 
					(new File(idlFile)).getName().replace(".idl", ""), 
					compiler.className);
			
		}
		catch (Throwable t)
		{
			System.err.println(t.getMessage());
			System.exit(1);
		}
	}

	// parse arguments
	protected void parseArgs(String[] args) throws Exception
	{
		this.args = args;

		// get destination dir and verbose arguments
		for (int i = 0; i < args.length; ++i)
		{
			if (args[i].equals("-td"))
			{
				destDir = args[ ++i] + File.separator;
			}
			if (args[i].equals("-defaultPkgPrefix"))
			{
				defaultPkgPrefix = args[ ++i];
			}
			if (args[i].equals("-v") || args[i].equals("-verbose"))
			{
				verbose = true;
			}
		}
	}

	// run JDK's idlj
	protected void runIdlj() throws Exception
	{
		List<String> idljArgs = new ArrayList<String>();
		if (verbose)
		{
			log.info("Running idlj compiler : ");
		}
		StringBuffer cmdLine = new StringBuffer();
		for (String arg : args)
		{
			if ( !arg.equals("-defaultPkgPrefix") && !arg.equals(defaultPkgPrefix))
			{
				cmdLine.append(arg + " ");
				idljArgs.add(arg);
			}
		}
		if (verbose)
		{
			log.info(cmdLine);
		}
		String[] result = new String[idljArgs.size()];
		ProcessBuilder pb = new ProcessBuilder(idljArgs.toArray(result));
		Process idljProc = null;
		int exitStatus = 1;
		boolean errorOccured = false;
		try
		{
			// start idlj process
			idljProc = pb.start();

			// consume outputs lines separate threads to avoid blocking situation
			// in case the OS redirection buffer in full (typically on Windows...)
			StdoutGobbler outGobbler = new StdoutGobbler(idljProc.getInputStream(), log);
			StderrGobbler errGobbler = new StderrGobbler(idljProc.getErrorStream(), log);
			outGobbler.start();
			errGobbler.start();

			// wait for process end
			exitStatus = idljProc.waitFor();

			outGobbler.close();
			errGobbler.close();

			// check if some error were printed
			// (it happens that idlj returns 0 as exit code despite of errors...)
			errorOccured = errGobbler.wasErrorPrinted();

		}
		catch (Throwable t)
		{
			if (verbose)
			{
				t.printStackTrace();
			}
			throw new Exception("Error running idlj: " + t.getMessage());
		}
		finally
		{
			if (idljProc != null)
			{
				idljProc.destroy();
			}
		}

		if (exitStatus != 0)
		{
			throw new Exception("Error running idlj: exit status is " + idljProc.exitValue());
		}
		if (errorOccured)
		{
			throw new Exception("Error running idlj: see above error logs");
		}

		if (verbose)
		{
			log.info("  Done.");
		}
	}

	// patch code generated by idlj
	List<Pair<String>> listParams = new ArrayList<Pair<String>>();
	protected void patchGeneratedCode() throws Exception
	{

		String idlFile = args[args.length - 1];

		try
		{
			if (verbose)
			{
				log.info("Parsing : " + idlFile);
			}

			Collection<KeyListDescriptor> keyLists = KeyListParser.jparse(idlFile);
			if (verbose)
			{
				System.out.println("  Done.");
			}

			for (KeyListDescriptor keyList : keyLists)
			{
				String[] elements = keyList.typeName().split("\\.");
				className = elements[elements.length - 1];
				String javaFile = destDir + keyList.typeName().replace('.', '/') + ".java";
				if (verbose)
				{
					System.out.println("Updating : " + javaFile);
				}
				try
				{
					StringBuilder buf = new StringBuilder();
					BufferedReader in = new BufferedReader(new FileReader(javaFile));

					try
					{
						String line = in.readLine();
						
						while (line != null)
						{
							line = getPublicFields(line);
							if (line.contains("public final class " + className))
							{
								line = line.replace("final class " + className, "class " + className + " extends Structure ");
								line = line.replace(" implements ", " implements UserClass, ");
								buf.append("/**\n");
								buf.append("* Updated by idl2j\n");
								buf.append("* from ").append(idlFile).append("\n");
								buf.append("* ")
								.append(
										DateFormat
										.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL)
										.format(new Date()))
								.append("\n");
								buf.append("*/\n");
								buf.append("\n");
								// for JNA
								buf.append("import java.util.Arrays;\n");
								buf.append("import java.util.List;\n");
								buf.append("import com.sun.jna.Pointer;\n");
								buf.append("import com.sun.jna.Structure;\n");
								buf.append("import org.eclipse.cyclonedds.core.UserClass;\n");
								buf.append("import org.eclipse.cyclonedds.dcps.keys.KeyList;\n");
								buf.append("import org.eclipse.cyclonedds.helper.NativeSize;\n");
								//buf.append("import "+className+"."+className+"_Helper;");
								//
								buf.append("\n");
								buf.append("\n");
								buf.append("@KeyList(\n");
								buf.append("    topicType = \"").append(className).append("\",\n");
								if ( !keyList.keyList().isEmpty())
								{
									buf.append("    keys = {\"")
									.append(keyList.keyList().replace(", ", "\", \""))
									.append("\"}\n");
								}
								else
								{
									buf.append("    keys = {}\n");
								}
								buf.append(")\n");
								buf.append(line).append("\n");
								line = in.readLine();
							}
							// JNA
							if (line.contains("public " + className + " ("))
							{
								buf.append(line).append("\n");
								line = in.readLine(); // {
								buf.append(line).append("\n");
								buf.append("    super();\n");
								//buf.append("    helper = new "+className+"_Helper();\n");
								line = in.readLine(); // {
							}
							if (line.contains("} // class " + className))
							{
								createGetFieldOrderMethod(buf);
								createNewStructureMethod(buf);
								createNewGetSizeMethod(buf);
								buf.append("  public "+className+"(Pointer peer) {\n    super(peer);\n  }\n\n");
								createGetDataMethod(buf);
								
								//buf.append("  private "+className+"_Helper helper;\n\n");
								buf.append("  public static class ByReference extends "+className+" implements Structure.ByReference {};\n\n");
								buf.append("  public static class ByValue extends "+className+" implements Structure.ByValue {};\n\n");
							}
							buf.append(line).append("\n");
							line = in.readLine();
						}
						in.close();

						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(javaFile), "UTF-8"));
						try
						{
							out.write(buf.toString());
						}
						finally
						{
							out.close();
						}
					}
					finally
					{
						in.close();
					}
				}
				catch (IOException e)
				{
					System.err.println("[ERROR] Unable to update file " + javaFile);
					e.printStackTrace();
				}

				if (verbose)
				{
					log.info("  Done.");
				}
			}
		}
		catch (Throwable t)
		{
			if (verbose)
			{
				t.printStackTrace();
			}
			throw new Exception("Error patching code generated for "
					+ idlFile + ": " + t.getMessage());
		}
	}

	private void createGetDataMethod(StringBuilder buf) {
		//getData method
		buf.append("  public Structure.ByReference getStructureReference(){\n");
		buf.append("    "+className+".ByReference bref = new "+className+".ByReference();\n");								
		for (int i=0;i<listParams.size();i++) {										
			buf.append("    bref."+listParams.get(i).second+" = "+listParams.get(i).second+";\n");
		}
		buf.append("    return bref;\n");
		buf.append("  }\n\n");
		//end getData method
	}

	private void createNewGetSizeMethod(StringBuilder buf) {
		//getFieldOrder method
		buf.append("  public int getNativeSize() {\n");								
		buf.append("    return size();\n");
		buf.append("  }\n\n");
		//end getFieldOrder method
	}
	
	
	private void createNewStructureMethod(StringBuilder buf) {
		//getFieldOrder method
		buf.append("  public Structure getNewStructureFrom(Pointer peer) {\n");								
		buf.append("    return new "+className+"(peer);\n");
		buf.append("  }\n\n");
		//end getFieldOrder method
	}


	private void createGetFieldOrderMethod(StringBuilder buf) {
		//getFieldOrder method
		buf.append("  protected List<String> getFieldOrder() {\n");								
		if(listParams.size() > 1) {
			buf.append("    return Arrays.asList(");
			for (int i=0;i<listParams.size();i++) {										
				buf.append("\""+listParams.get(i).second+"\"");
				if(i < listParams.size()-1) {
					buf.append(",");
				}
			}
			buf.append(");\n");
		} else if(listParams.size() == 1) {
			buf.append("    return Arrays.asList(\""+listParams.get(0).second+"\");\n");	
		}
		buf.append("  }\n\n");
		//end getFieldOrder method
	}

	private String getPublicFields(String line) {
		if (line.trim().startsWith("public") && line.trim().endsWith(";")) {
			String tmpLine =  line.trim().replace("public ", "");
			String varType;
			String varName;
			if(line.contains("=")) {
				String declarator = tmpLine.split("=")[0];
				String[] tabDeclarator = declarator.split("\\s+");
				varType = tabDeclarator[0];
				varName = tabDeclarator[1];
				if(line.contains("[]") && line.contains("null")) {
					line = line.replace("null", "new "+varType+"[128]");
				}
			} else {
				String[] tabDeclarator = tmpLine.split("\\s+");
				varType = tabDeclarator[0];
				varName = tabDeclarator[1];
			}
			listParams.add(new Pair<String>(varType, varName.replace("[", "").replace("]","")));
		}
		return line;
	}

	// patch code generated by idlj
	protected void patchNonScopedGeneratedCode() throws Exception
	{
		if (verbose)
		{
			log.info("Moving files from default package to " + defaultPkgPrefix + " package");
		}

		// find all *.java files in the destDir directory (no recursive search)
		String[] dir = new java.io.File(destDir).list(
				new FilenameFilter()
				{

					@Override
					public boolean accept(File dir, String name)
					{
						return name.endsWith(".java");
					}
				}
				);

		// create directories for package "defaultPkgPrefix"
		File defaultDir = new File(destDir + File.separator
				+ defaultPkgPrefix.replace('.', File.separatorChar));
		if ( !defaultDir.exists() && !defaultDir.mkdirs())
		{
			System.err.println("[ERROR] Unable to create directory: " + defaultDir);
		}

		// copy each java file to its new package directory,
		// adding the package declaration within
		for (int i = 0; i < dir.length; i++)
		{
			if (verbose)
			{
				log.info("  moving " + dir[i]);
			}
			Path in = Paths.get(destDir + File.separator + dir[i]);
			List<String> lines = Files.readAllLines(in, StandardCharsets.UTF_8);
			lines.add(0, "package " + defaultPkgPrefix + ";");
			Path out = Paths.get(defaultDir + File.separator + dir[i]);
			Files.write(out, lines, StandardCharsets.UTF_8);
			Files.delete(in);
		}
	}

	/**
	 * Invoke the idl compiler for the given IDL File
	 *
	 * @param arguments command line to be passed to idlj
	 */
	public static void main(String[] args)
	{
		new Compiler(args);

	}
}
