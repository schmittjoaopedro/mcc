package memory.compiler.utils;

import memory.compiler.object.SourceClass;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods to help in creation and validation of SourceClasses
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MccUtils {

    private static final String IMPLEMENTS = "implements";
    private static final String EXTENDS = "extends";
    private static final String BRACKET = "{";
    private static final String CLASS = "class";
    private static final String PRIVATE = "private";
    private static final String PUBLIC = "public";
    private static final String CLASS_REG_EXP = "\\s*(public|private)\\s+class\\s+(\\w+)\\s+((extends\\s+\\w+)|(implements\\s+\\w+( ,\\w+)*))?\\s*\\{";
    private static final String DOT_COMMA = File.pathSeparator;
    private static final String PACKAGE = "package";

    /**
     * Create a SourceClass object based on a sourceCode
     *
     * @param sourceCode
     * @return sourceClass
     */
    public static SourceClass extractSourceClassFromSourceCode(String sourceCode) {
        return new SourceClass(MccUtils.extractPackageFromSourceCode(sourceCode), MccUtils.extractClassNameFromSourceCode(sourceCode), sourceCode);
    }

    /**
     * Extract the package from source code
     *
     * @param sourceCode
     * @return package
     */
    public static String extractPackageFromSourceCode(String sourceCode) {

        int indexStartWord = sourceCode.indexOf(PACKAGE) + PACKAGE.length();
        int endIndex = -1;
        for(int i = indexStartWord; !sourceCode.substring(i, ++i).equals(DOT_COMMA);) { endIndex = i; }
        String packageName = null;
        packageName = sourceCode.substring(indexStartWord, endIndex).trim();
        return packageName;

    }

    /**
     * Extract the class name from source code
     *
     * @param sourceCode
     * @return className
     */
    public static String extractClassNameFromSourceCode(String sourceCode) {

        String className = null;
        Pattern classPatter = Pattern.compile(CLASS_REG_EXP);
        Matcher m = classPatter.matcher(sourceCode);
        while (m.find()) {
            className = m.group(0);
            className = className.replace(CLASS, "").replace(BRACKET, "");
            if(className.contains(PUBLIC)) className = className.replace(PUBLIC, "");
            if(className.contains(PRIVATE)) className = className.replace(PRIVATE, "");
            if(className.contains(EXTENDS)) className = className.replace(className.substring(className.indexOf(EXTENDS)), "");
            if(className.contains(IMPLEMENTS)) className = className.replace(className.substring(className.indexOf(IMPLEMENTS)), "");
            className = className.trim();
            break;
        }
        return className;

    }

}
