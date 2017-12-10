package com.github.schmittjoaopedro.mcc.manager;

import com.github.schmittjoaopedro.mcc.engine.MemoryClassCompiler;
import com.github.schmittjoaopedro.mcc.message.MemoryCompilerException;
import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.object.SourceTask;

import java.util.List;

/**
 * This class manages the MemoryClassCompiler. Manage if the
 * threads are available to compilation in ThreadPool or used as a factory.
 *
 * @author jschmitt
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MemoryCompilerTask {

    /**
     * Memory compiler
     */
    private MemoryClassCompiler compiler;

    /**
     * Provider to manage the class pool
     */
    private MemoryCompilerProvider provider;

    /**
     * Constructor
     *
     * @param provider the input provider
     */
    public MemoryCompilerTask(MemoryCompilerProvider provider) {
        this.compiler = new MemoryClassCompiler();
        this.provider = provider;
    }

    /**
     * Constructor with class path
     *
     * @param provider the input provider
     * @param classpath the input classpath
     */
    public MemoryCompilerTask(MemoryCompilerProvider provider, List<String> classpath) {
        this.compiler = new MemoryClassCompiler(classpath);
        this.provider = provider;
    }

    /**
     * Create and execute a task to custom compiler with a single SourceClass
     *
     * @param sourceClass the input source class
     * @return sourceClass the output source class
     * @throws MemoryCompilerException throws a memory compiler exception
     */
    public SourceClass executeCompilation(SourceClass sourceClass) {
        try {
            this.compiler.compile(sourceClass);
        } finally {
            this.provider.putOnAvailableList(this);
        }
        return sourceClass;
    }

    /**
     * Create and execute a task to custom compiler for single SourceTask
     *
     * @param sourceTask the input source task
     * @return sourceTask the output source task
     * @throws MemoryCompilerException throws a memory compiler exception
     */
    public SourceTask executeCompilation(SourceTask sourceTask) {
        this.compiler.compile(sourceTask);
        this.provider.putOnAvailableList(this);
        return sourceTask;
    }

    /**
     * Set the class path
     *
     * @param classpath the input classpath
     */
    public void setClassPath(String classpath) {
        this.compiler.setClassPath(classpath);
    }

}
