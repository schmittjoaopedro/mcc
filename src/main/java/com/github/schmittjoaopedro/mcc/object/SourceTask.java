package com.github.schmittjoaopedro.mcc.object;

import com.github.schmittjoaopedro.mcc.message.MessageCompiler;
import com.github.schmittjoaopedro.mcc.message.MessageStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Encapsulate a batch job of SourceClass compilation.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SourceTask {

    private static final String DOT = ".";

    private Map<String, SourceClass> sourceClasses;

    public SourceTask() {
        super();
        this.sourceClasses = new HashMap<String, SourceClass>();
    }


    /**
     * Add a valid source class in the batch list.
     *
     * @param sourceClass the input source class
     *
     * @return added the output is added
     */
    public Boolean addSourceClass(SourceClass sourceClass) {
        if(sourceClass.isValid()) {
            this.sourceClasses.put(this.mountClassName(sourceClass.getPackageName(), sourceClass.getClassName()), sourceClass);
            return Boolean.TRUE;
        } else {
            sourceClass.setStatus(new MessageCompiler("Invalid class to compilation", MessageStatus.FAILED));
            return Boolean.FALSE;
        }
    }

    /**
     * Create a class to be compiled
     *
     * @param packageName the input package name
     * @param className the input class name
     * @param sourceCode the input source code
     */
    public void createSourceClass(String packageName, String className, String sourceCode) {
        SourceClass sourceClass = new SourceClass(packageName, className, sourceCode);
        if(sourceClass.isValid()) {
            String fullClassName = mountClassName(packageName, className);
            if(!this.sourceClasses.containsKey(fullClassName)) {
                this.sourceClasses.put(fullClassName, sourceClass);
            }
        }
    }

    /**
     * Remove a class of the batch list
     *
     * @param packageName the input package name
     * @param className the input class name
     */
    public void removeSourceClass(String packageName, String className) {
        String fullClassName = mountClassName(packageName, className);
        if(this.sourceClasses.containsKey(fullClassName)) {
            this.sourceClasses.remove(fullClassName);
        }
    }

    /**
     * Returns a java source class of the batch list
     *
     * @param fullClassName the input full class name
     * @return sourceJavaClass the output source java class
     */
    public SourceClass findSourceClass(String fullClassName) {
        return this.sourceClasses.get(fullClassName);
    }

    /**
     * Concat the class name, like: "java.util" + "." + "List" = "java.util.List"
     *
     * @param packageName the input package name
     * @param className the input class name
     *
     * @return fullClassName the output full class name
     */
    private String mountClassName(String packageName, String className) {
        return packageName + DOT + className;
    }

    /**
     * Return all source class objects
     *
     * @return sourceClasses the output source classes
     */
    public List<SourceClass> getSourcesClass() {
        return new ArrayList<SourceClass>(this.sourceClasses.values());
    }

}
