package org.eclipse.cyclonedds.builders;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Remplacements {

    public static String getHelperUtilsCode(String className){        
        String path = File.separator+"tmp"+File.separator+"Helper.java.txt";
        File file = new File(path);
        StringBuilder str = new StringBuilder();
        
        str.append("\n    public "+className+"(){}\n");                
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                str.append("\n    ").append(line);
            }
            scanner.close();
        } catch (IOException e) {
            scanner.close();
            e.printStackTrace();
        }
        return str.toString();
    }


    public static String replace(String txt){
        if(txt.indexOf("sizeof")!=-1){
            if(txt.indexOf("char*") != -1){
                return txt.replace("(", "(\"").replace(")", "\")");
            } else {
                return txt.replace("sizeof", "getIntSize").replace("(", "(\"").replace(")", "\")");
            }
        } else if(txt.indexOf("offsetof")!=-1){
            return txt.replace("(", "(\"").replace(")", "\")").replace(",","\",\"");
        }
        return txt;
    }

    public static String cToJavaNumber(String txt){
        return "cToJavaNumber(\""+txt+"\")";
    }

    public static boolean isCNumber(String txt){         
        try {            
            int i = Integer.parseInt(""+txt.charAt(0));            
            try {
                i = Integer.parseInt(""+txt.charAt(txt.length()-1));            
                if(i<=9 && i>=0){                    
                    return false;
                }
                return false;
            } catch (Exception a) {                
                return true;
            }
        } catch (Exception b) {
            return false;
        }
    }

    public static boolean isNumber(String txt){
        try {            
            new Integer(txt);
            return true;            
        } catch (Exception e) {
            return false;
        }
    }


	public static String stringToPointer(String text) {
		return "stringToPointer("+text+")";
	}

}