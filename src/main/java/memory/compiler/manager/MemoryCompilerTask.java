package memory.compiler.manager;

import memory.compiler.engine.MemoryClassCompiler;
import memory.compiler.message.MemoryCompilerException;
import memory.compiler.object.SourceClass;
import memory.compiler.object.SourceTask;

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
     * @param provider
     */
    public MemoryCompilerTask(MemoryCompilerProvider provider) {
        this.compiler = new MemoryClassCompiler();
        this.provider = provider;
    }

    /**
     * Constructor with class path
     *
     * @param provider
     * @param classpath
     */
    public MemoryCompilerTask(MemoryCompilerProvider provider, List<String> classpath) {
        this.compiler = new MemoryClassCompiler(classpath);
        this.provider = provider;
    }

    /**
     * Create and execute a task to custom compiler with a single SourceClass
     *
     * @param sourceClass
     * @return sourceClass
     * @throws MemoryCompilerException
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
     * @param sourceTask
     * @return sourceTask
     * @throws MemoryCompilerException
     */
    public SourceTask executeCompilation(SourceTask sourceTask) {
        this.compiler.compile(sourceTask);
        this.provider.putOnAvailableList(this);
        return sourceTask;
    }

    /**
     * Set the class path
     */
    public void setClassPath(String classpaths) {
        this.compiler.setClassPath(classpaths);
    }

}
