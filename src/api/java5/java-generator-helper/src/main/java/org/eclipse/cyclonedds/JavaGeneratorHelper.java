package org.eclipse.cyclonedds;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.cyclonedds.builders.ConcreteDdsKeyDescriptorBuilder;
import org.eclipse.cyclonedds.builders.ConcreteMessageOptionsBuilder;
import org.eclipse.cyclonedds.builders.ConcreteMsgDescBuilder;
import org.eclipse.cyclonedds.builders.Remplacements;


import java.io.IOException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaGeneratorHelper {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    static DdscLexer lexer;
    static DdscParser parser;

	private String className = null;

    private void initAntlr(String file) {
        try {
            lexer = new DdscLexer(CharStreams.fromStream(new FileInputStream(file)));
            parser = new DdscParser(new CommonTokenStream(lexer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        Files.write(path, aLines, ENCODING);
    }

    public static void execCommands(List<String> cmds, String cmdFile) {
        Runtime runtime = Runtime.getRuntime();
        try {
            writeSmallTextFile(cmds, cmdFile);
            final Process chmod = runtime.exec("chmod +x " + cmdFile);
            try {
                chmod.waitFor();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(" executing " + cmdFile);
        String[] largs = {"/bin/sh", "-c", cmdFile};
        Process process = null;
        try {
            process = runtime.exec(largs);

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            System.out.println("Output: ");
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("Error[s]: ");
            while ((line = err.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception exc) {
            System.err.println("An error occurred while executing command! Error:\n" + exc);

        }
        try {
            process.waitFor();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void idl2c(String idlfile) {
        List<String> cmds = new ArrayList<String>();
        cmds.add("cp $(find ~/ -type f 2>/dev/null | grep \"cyclonedds/src/idlc/target/idlc-jar-with-dependencies.jar\") /tmp/");
        cmds.add("java -classpath /tmp/idlc-jar-with-dependencies.jar org.eclipse.cyclonedds.compilers.Idlc -d /tmp -I. "  + idlfile);
        execCommands(cmds,"/tmp/compile.sh");
    }

    private String getIdlHelperClassFrom(String file, String outputDir, String javaPackage) {
		String defaultClassName = file.substring(
            file.lastIndexOf("/")==-1 ? 0: file.lastIndexOf(File.separator)+1, 
            file.lastIndexOf(".")).replace(".", "") ;        
        
		initAntlr(file);
        ConcreteDdsKeyDescriptorBuilder ddsKdesc = new ConcreteDdsKeyDescriptorBuilder();
        ConcreteMessageOptionsBuilder ddsOps = new ConcreteMessageOptionsBuilder(defaultClassName, className);
        ConcreteMsgDescBuilder ddsTopicDescr = new ConcreteMsgDescBuilder(defaultClassName, className);
        ParseTreeWalker walker = new ParseTreeWalker();        
        walker.walk(
            new UserDdscListener(ddsKdesc, ddsOps, ddsTopicDescr),
            parser.main());
        
        StringBuilder javaCode = new StringBuilder();        
        javaCode.append("package "+javaPackage+";\n\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor;\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.DdscLibrary;\n");
        javaCode.append("import org.eclipse.cyclonedds.helper.NativeSize;\n");        
        javaCode.append("import com.sun.jna.ptr.IntByReference;\n");        
        javaCode.append("import com.sun.jna.Native;\n");
        javaCode.append("import java.lang.reflect.Method;\n");        
        javaCode.append("import com.sun.jna.Pointer;\n");
        javaCode.append("import com.sun.jna.Structure;\n");
        javaCode.append("import org.eclipse.cyclonedds.topic.DdsTopicDescriptor;\n");
        javaCode.append("import com.sun.jna.Memory;\n\n");

        javaCode.append("\npublic class "+getClassName(defaultClassName)+"_Helper extends DdsTopicDescriptor {\n");
        javaCode.append("\n"+ddsKdesc.getJavaCode());
        javaCode.append("\n"+ddsOps.getJavaCode());
        javaCode.append("\n"+ddsTopicDescr.getJavaCode());
        javaCode.append(Remplacements.getHelperUtilsCode(getClassName(defaultClassName)+"_Helper"));
        javaCode.append("\n}");

        try {
            String dir = outputDir + File.separator + javaPackage.replace(".", File.separator)+File.separator;
            if(!(new File(dir)).exists() && !new File(dir).mkdirs()){
                throw new Exception("Cannot create :" + outputDir + File.separator + javaPackage.replace(".", File.separator));
            }
            PrintWriter out = new PrintWriter(dir + getClassName(defaultClassName) + "_Helper.java");
            out.print(javaCode.toString());            
            out.flush();
            out.close();
        } catch (Exception e) {            
            e.printStackTrace();
		}

        return javaCode.toString();
    }
    
    public String getClassName(String defaultClassName) {
    	return className == null ? defaultClassName:className;
    }

    private JavaGeneratorHelper(String[] args) {
        idl2c (args[0] + ".idl");        
        
        //generate classes
        String cPath = "/tmp/" + (new File(args[0] + ".c")).getName();        
        getIdlHelperClassFrom(cPath, args[1], args[2]);
    }
    

	public JavaGeneratorHelper(String idl, String outputDir, String javaPackage, String className) {
        // compile with idl2c
        idl2c (idl + ".idl");        
        
        //generate classes
        String cPath = "/tmp/" + (new File(idl+ ".c")).getName();        
        getIdlHelperClassFrom(cPath, outputDir, javaPackage, className);
	}

	private void getIdlHelperClassFrom(String cPath, String outputDir, String javaPackage, String className) {
		this.className  = className;
		getIdlHelperClassFrom(cPath, outputDir, javaPackage);
	}

	public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java -jar target/java-generator-helper-<VERSION>.jar <idl_file> <output_directory> <java_package>");
            System.exit(0);
        } else if (args.length == 3) {
        	new JavaGeneratorHelper(args);
        } else  if (args.length == 4) {
        	new JavaGeneratorHelper(args[0], args[1], args[2], args[3]);
        }
    }
}
