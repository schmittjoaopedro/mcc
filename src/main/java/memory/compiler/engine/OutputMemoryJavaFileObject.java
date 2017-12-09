package memory.compiler.engine;

import memory.compiler.message.MessageCompiler;
import memory.compiler.message.MessageStatus;
import memory.compiler.object.SourceClass;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Implements the JavaFileObject to dispatch the generated bytecode.
 * The bytecode is saved in a SourceClass, and the binary is generated
 * using a ByteArrayOutputStream
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class OutputMemoryJavaFileObject extends ByteArrayOutputStream implements JavaFileObject {


    private static final String COMPILED_WITH_SUCCESS = "Compiled with success!";

    /**
     * File object URI.
     */
    protected URI uri;


    /**
     * The kind of file object (in this case is a .class)
     */
    protected Kind kind = Kind.CLASS;

    /**
     *
     */
    private SourceClass currentSourceClass;

    @Override
    public URI toUri() {
        return uri;
    }

    @Override
    public String getName() {
        return uri.getPath();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * This methods return the own reference, because this class is a OutputStream
     *
     * @return this
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return this;
    }

    @Override
    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Writer openWriter() throws IOException {
        return new OutputStreamWriter(openOutputStream());
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NestingKind getNestingKind() {
        return null;
    }

    @Override
    public Modifier getAccessLevel() {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + toUri() + "]";
    }


    /**
     * Parse string class name to URI
     *
     * @param className
     * @return uri
     */
    public void setUri(String className) {
        try {
            this.uri = new URI(className);
        } catch(URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This methods beyond close the OutputStream, too set the byte code generate to the
     * current compiled class, and reset the byte flag to restart the buffer.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        super.close();
        this.currentSourceClass.setBytecode(this.toByteArray());
        this.currentSourceClass.setStatus(new MessageCompiler(COMPILED_WITH_SUCCESS, MessageStatus.COMPILED));
        this.reset();
    }

    public SourceClass getCurrentSourceClass() {
        return currentSourceClass;
    }

    public void setCurrentSourceClass(SourceClass currentSourceClass) {
        this.currentSourceClass = currentSourceClass;
    }

}
