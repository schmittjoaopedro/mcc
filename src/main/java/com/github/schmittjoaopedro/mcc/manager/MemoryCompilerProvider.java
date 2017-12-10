package com.github.schmittjoaopedro.mcc.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manage the thread pool and return instances
 * of compiler free to process. If a class not is available
 * the current process is pushed in a wait stack.
 *
 * @author jschmitt
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MemoryCompilerProvider {


    /**
     * Available compilers
     */
    private List<MemoryCompilerTask> availableCompiler;

    /**
     * Unavailable compilers
     */
    private List<MemoryCompilerTask> unavailableCompiler;

    /**
     * Unavailable compilers
     */
    private List<MemoryCompilerTask> allCompilers;

    /**
     * Thread pools object
     */
    private static int pool_size = 10;

    private static final MemoryCompilerProvider INSTANCE = new MemoryCompilerProvider();

    /**
     * Provide a list of available instances of objects pool following the pool_size
     */
    private MemoryCompilerProvider() {
        this.reset();
    }

    /**
     * @return compilerProvider
     */
    public static MemoryCompilerProvider getInstance() {
        return MemoryCompilerProvider.INSTANCE;
    }

    /**
     * Release a compiler task
     *
     * @param compilerTask
     */
    public synchronized void putOnAvailableList(MemoryCompilerTask compilerTask) {
        this.unavailableCompiler.remove(compilerTask);
        this.availableCompiler.add(compilerTask);
    }

    /**
     * Return the next available compiler
     *
     * @return compilerTask
     */
    public synchronized MemoryCompilerTask getAvailable() {
        if(!this.availableCompiler.isEmpty()) {
            MemoryCompilerTask available = this.availableCompiler.get(0);
            this.availableCompiler.remove(available);
            this.unavailableCompiler.add(available);
            return available;
        } else {
            return null;
        }
    }

    /**
     * Define the class path
     *
     * @param classpath
     */
    public void setClasspath(String classpath) {
        this.allCompilers.forEach(item -> {
            item.setClassPath(classpath);
        });
    }

    /**
     * Reset the list of available compilers.
     * Provide a list of available instances of objects pool (pool_size).
     */
    public void reset() {
        this.availableCompiler = new ArrayList<MemoryCompilerTask>();
        this.unavailableCompiler = new ArrayList<MemoryCompilerTask>();
        this.allCompilers = new ArrayList<MemoryCompilerTask>();
        for(int i = 0; i < pool_size; i++) {
            MemoryCompilerTask compilerTask = new MemoryCompilerTask(this);
            availableCompiler.add(compilerTask);
            allCompilers.add(compilerTask);
        }
    }

}
