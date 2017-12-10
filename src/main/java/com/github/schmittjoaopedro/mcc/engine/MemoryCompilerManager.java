package com.github.schmittjoaopedro.mcc.engine;

import com.github.schmittjoaopedro.mcc.object.SourceTask;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

/**
 * Define the current class that will receive the byte code.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MemoryCompilerManager extends ForwardingJavaFileManager<JavaFileManager> {

    /**
     * Used to write the byte code generated in source list
     */
    private OutputMemoryJavaFileObject outputJavaClass;

    /**
     * Classes list to generate and receive the bytecode
     */
    private SourceTask sourceJavaClass;

    /**
     * Constructor
     *
     * @param fileManager
     * @param sourceJavaClass
     */
    protected MemoryCompilerManager(JavaFileManager fileManager, SourceTask sourceJavaClass) {
        super(fileManager);
        this.sourceJavaClass = sourceJavaClass;
        this.outputJavaClass = new OutputMemoryJavaFileObject();
    }

    /**
     * Method that intercept each class compilation. Will define the source code to be compiled.
     *
     * @param location
     * @param className
     * @param kind
     * @param sibling
     *
     * @return outputJavaClass
     *
     * @throws IOException
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        outputJavaClass.setUri(className);
        outputJavaClass.setCurrentSourceClass(this.sourceJavaClass.findSourceClass(className));
        return outputJavaClass;
    }
}
