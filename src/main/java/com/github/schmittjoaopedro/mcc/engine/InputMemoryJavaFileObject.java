package com.github.schmittjoaopedro.mcc.engine;

import com.github.schmittjoaopedro.mcc.object.SourceClass;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class implements the JavaFileObject. The objective of this class is to
 * create the source code to the java compiler.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class InputMemoryJavaFileObject implements JavaFileObject {

    /**
     * File object URI.
     */
    protected final URI uri;

    /**
     * The kind of this file, in this case is .java
     */
    protected final Kind kind = Kind.SOURCE;

    /**
     * Source class used do code of JavaFileObject
     */
    private SourceClass sourceClass;

    /**
     * Define a Java class name convention like "JavaClass.java" used to
     * execute the compilation.
     *
     * @param sourceClass The input source class
     * @throws IllegalArgumentException throws a IllegalArgumentException
     */
    public InputMemoryJavaFileObject(SourceClass sourceClass) {
        this.sourceClass = sourceClass;
        uri = parseToURITheUrl(sourceClass.getJavaFileClassName());
    }


    @Override
    public URI toUri() {
        return this.uri;
    }

    @Override
    public String getName() {
        return toUri().getPath();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the source code as CharSequence
     *
     * @return sourcecode The source code of the class to be compiled
     * @throws IOException throws a IOException
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.sourceClass.getSourceCode();
    }

    @Override
    public Writer openWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastModified() { return 0; }

    @Override
    public boolean delete() { return false; }

    @Override
    public Kind getKind() { return kind; }

    /**
     * This implementation compares the path of its URI to the given
     * simple name.  This method returns true if the given kind is
     * equal to the kind of this object, and if the path is equal to
     * {@code simpleName + kind.extension} or if it ends with {@code
     * "/" + simpleName + kind.extension}.
     *
     * <p>This method calls {@link #getKind} and {@link #toUri} and
     * does not access the fields {@link #uri} and {@link #kind}
     * directly.
     *
     * <p>Subclasses can change this behavior as long as the contract
     * of {@link JavaFileObject} is obeyed.
     */
    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        String baseName = simpleName + kind.extension;
        return kind.equals(getKind())
                && (baseName.equals(toUri().getPath())
                || toUri().getPath().endsWith("/" + baseName));
    }

    @Override
    public NestingKind getNestingKind() { return null; }

    @Override
    public Modifier getAccessLevel() { return null; }

    /**
     * Parse string class name to URI
     *
     * @param url the URL String
     * @return uri the URI
     */
    private static URI parseToURITheUrl(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + toUri() + "]";
    }


    public SourceClass getSourceClass() {
        return this.sourceClass;
    }

}
