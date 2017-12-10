package com.github.schmittjoaopedro.mcc.object;

import com.github.schmittjoaopedro.mcc.message.MessageCompiler;

/**
 * This class is used by the consumer to create a class
 * to be compiled in memory. The same object is used to
 * return the generated bytecode.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SourceClass {

    private static final String DOT = ".";

    /**
     * Java file extension
     */
    private static final String JAVA_EXT = ".java";

    /**
     * Simple class name, without the package prefix. Ex: List
     */
    private String className;

    /**
     * Class package name, without the class name suffix. Ex: "java.util"
     */
    private String packageName;

    /**
     * Source code of class to be compiled
     */
    private String sourceCode;

    /**
     * The bytecode generated by the compiler
     */
    private byte[] bytecode;

    /**
     * Compilation status
     */
    private MessageCompiler status;

    public SourceClass() {
        super();
    }

    public SourceClass(String packetName, String className, String sourceCode) {
        super();
        this.setPackageName(packetName);
        this.setClassName(className);
        this.setSourceCode(sourceCode);
    }

    /**
     * Validate the source class, checks all necessary attributes required
     * by the compiler as: package name, class name and source code.
     *
     * @return isValid
     */
    public Boolean isValid() {
        if( this.getPackageName() != null && !this.getPackageName().trim().equals("") &&
                this.getClassName() != null && !this.getClassName().trim().equals("") &&
                this.getSourceCode() != null && !this.getSourceCode().trim().equals("")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Convert the class name in a file class name, add the prefix of .java.
     * The compiler required the .java extension.
     *
     * @return javaFileClassName
     */
    public String getJavaFileClassName() {
        return this.className + JAVA_EXT;
    }

    /**
     * Return the full className, combining the package name with className
     * ex: "java.util.List"
     *
     * @return fullClassName
     */
    public String getFullClassName() {
        return this.getPackageName() + DOT + this.getClassName();
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public byte[] getBytecode() {
        return bytecode;
    }

    public void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

    public MessageCompiler getStatus() {
        return status;
    }

    public void setStatus(MessageCompiler status) {
        this.status = status;
    }

}