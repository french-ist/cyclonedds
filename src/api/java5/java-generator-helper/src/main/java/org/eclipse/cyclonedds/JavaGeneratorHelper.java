package org.eclipse.cyclonedds;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.cyclonedds.builders.ConcreteDdsKeyDescriptorBuilder;
import org.eclipse.cyclonedds.builders.ConcreteMessageOptionsBuilder;
import org.eclipse.cyclonedds.builders.ConcreteMsgDescBuilder;
import org.eclipse.cyclonedds.builders.Remplacements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    //private static final Logger LOG = LoggerFactory.getLogger(JavaGeneratorHelper.class
    //        .getName());

    static DdscLexer lexer;
    static DdscParser parser;

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
        String idljar = "../../../../../cyclonedds/build/idlc/target/idlc-jar-with-dependencies.jar ";
        List<String> cmds = new ArrayList<String>();
        cmds.add("java -classpath " + idljar  + " org.eclipse.cyclonedds.compilers.Idlc -d . -I. "  + idlfile);
        execCommands(cmds,"../../../../../cyclonedds/compile.sh");
    }

    private String getIdlHelperClassFrom(String file, String outputDir, String javaPackage) {
        initAntlr(file);
        ConcreteDdsKeyDescriptorBuilder ddsKdesc = new ConcreteDdsKeyDescriptorBuilder();
        ConcreteMessageOptionsBuilder ddsOps = new ConcreteMessageOptionsBuilder();
        ConcreteMsgDescBuilder ddsMsgDescr = new ConcreteMsgDescBuilder();
        ParseTreeWalker walker = new ParseTreeWalker();        
        walker.walk(
            new UserDdscListener(ddsKdesc, ddsOps, ddsMsgDescr),
            parser.main());
                
        String className = file.substring(
            file.lastIndexOf("/")==-1 ? 0: file.lastIndexOf(File.separator)+1, 
            file.lastIndexOf(".")).replace(".", "") ;
        StringBuilder javaCode = new StringBuilder();

        javaCode.append("package "+javaPackage+";\n\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor;\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_key_descriptor.ByReference;\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.dds_topic_descriptor;\n");
        javaCode.append("import org.eclipse.cyclonedds.ddsc.dds_public_impl.DdscLibrary;\n");
        javaCode.append("import org.eclipse.cyclonedds.helper.NativeSize;\n");        
        javaCode.append("import com.sun.jna.ptr.IntByReference;\n");        
        javaCode.append("import com.sun.jna.Native;\n");
        javaCode.append("import java.util.List;\n");
        javaCode.append("import java.lang.reflect.Method;\n");        
        javaCode.append("import com.sun.jna.Pointer;\n");
        javaCode.append("import com.sun.jna.Structure;\n");
        javaCode.append("import com.sun.jna.Memory;\n\n");        
        javaCode.append("\npublic class "+className+"_Helper {\n");
        javaCode.append("\n"+ddsKdesc.getJavaCode());
        javaCode.append("\n"+ddsOps.getJavaCode());
        javaCode.append("\n"+ddsMsgDescr.getJavaCode());
        javaCode.append(Remplacements.getHelperUtilsCode(className+"_Helper"));
        javaCode.append("\n}");

        try {
            String dir = outputDir + File.separator + javaPackage.replace(".", File.separator)+File.separator;
            if(!(new File(dir)).exists() && !new File(dir).mkdirs()){
                throw new Exception("Cannot create :" + outputDir + File.separator + javaPackage.replace(".", File.separator));
            }
            PrintWriter out = new PrintWriter(dir + className + "_Helper.java");
            out.print(javaCode.toString());            
            out.flush();
            out.close();
        } catch (Exception e) {            
            e.printStackTrace();
		}

        return javaCode.toString();
    }

    private JavaGeneratorHelper(String[] args) {
        // compile with idl2c
        idl2c (args[0] + ".idl");
        getIdlHelperClassFrom(args[0] + ".c", args[1], args[2]);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java -jar target/java-generator-helper-<VERSION>.jar <idl_file> <output_directory> <java_package>");
            System.exit(0);
        }
        new JavaGeneratorHelper(args);
    }
}
