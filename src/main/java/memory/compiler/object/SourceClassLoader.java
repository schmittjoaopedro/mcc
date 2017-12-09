package memory.compiler.object;

import memory.compiler.message.MemoryCompilerException;
import memory.compiler.message.MessageStatus;

/**
 * Provide a custom class loader customized to work with SourceClass.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class SourceClassLoader extends ClassLoader {

    /**
     * Create with parent class loader
     *
     * @param classLoader
     */
    public SourceClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * Load a sourceClass using the byte code generated of SourceClass
     *
     * @param sourceClass
     * @return class
     */
    @SuppressWarnings("rawtypes")
    public Class loadSourceClassLoader(SourceClass sourceClass) {
        try {
            return this.loadClass(sourceClass.getFullClassName());
        } catch (ClassNotFoundException ex) {
            if(sourceClass.isValid() && sourceClass.getBytecode() != null) {
                return this.defineClass(sourceClass.getFullClassName(), sourceClass.getBytecode(), 0, sourceClass.getBytecode().length);
            }
            throw new MemoryCompilerException("Class " + sourceClass.getFullClassName() + " have problems and can not be loaded", MessageStatus.COMPILER_ERROR);
        }
    }

}
