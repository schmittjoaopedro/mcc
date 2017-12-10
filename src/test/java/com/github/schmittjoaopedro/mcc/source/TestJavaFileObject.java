package com.github.schmittjoaopedro.mcc.source;

import com.github.schmittjoaopedro.mcc.engine.InputMemoryJavaFileObject;
import com.github.schmittjoaopedro.mcc.engine.OutputMemoryJavaFileObject;
import com.github.schmittjoaopedro.mcc.object.SourceClass;
import org.junit.Assert;
import org.junit.Test;

import javax.tools.JavaFileObject;

public class TestJavaFileObject {


    @Test
    public void fitCoverageInput() {

        InputMemoryJavaFileObject fileObject = null;
        SourceClass sourceClass = new SourceClass("teste", "Teste", "");

        try {
            fileObject = new InputMemoryJavaFileObject(new SourceClass("teste", "http://finance.yahoo.com/q/h?s=^IXIC", ""));
        } catch (Exception ex) {
            Assert.assertEquals(RuntimeException.class, ex.getClass());
        }

        try {
            fileObject = new InputMemoryJavaFileObject(sourceClass);
            fileObject.openInputStream();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openOutputStream();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openReader(true);
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openWriter();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        Assert.assertFalse(fileObject.isNameCompatible("Teste", JavaFileObject.Kind.HTML));
        Assert.assertFalse(fileObject.isNameCompatible("Teste2", JavaFileObject.Kind.SOURCE));
        Assert.assertEquals(0, fileObject.getLastModified());
        Assert.assertFalse(fileObject.delete());
        Assert.assertNull(fileObject.getNestingKind());
        Assert.assertNull(fileObject.getAccessLevel());
        Assert.assertEquals("com.github.schmittjoaopedro.mcc.engine.InputMemoryJavaFileObject[Teste.java]", fileObject.toString());
        Assert.assertEquals(sourceClass, fileObject.getSourceClass());

        sourceClass = new SourceClass("teste", "java/Teste", "");
        fileObject = new InputMemoryJavaFileObject(sourceClass);
        Assert.assertFalse(fileObject.isNameCompatible("Teste2", JavaFileObject.Kind.SOURCE));
        Assert.assertTrue(fileObject.isNameCompatible("Teste", JavaFileObject.Kind.SOURCE));

    }

    @Test
    public void fitCoverageOutput() {

        OutputMemoryJavaFileObject fileObject = null;

        try {
            fileObject = new OutputMemoryJavaFileObject();
            fileObject.setUri("http://finance.yahoo.com/q/h?s=^IXIC");
        } catch (Exception ex) {
            Assert.assertEquals(RuntimeException.class, ex.getClass());
        }

        try {
            fileObject = new OutputMemoryJavaFileObject();
            fileObject.openInputStream();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openOutputStream();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openReader(true);
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.openWriter();
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.isNameCompatible("", JavaFileObject.Kind.CLASS);
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        try {
            fileObject.getCharContent(false);
        } catch (Exception ex) {
            Assert.assertEquals(UnsupportedOperationException.class, ex.getClass());
        }

        fileObject.setUri("Teste");
        Assert.assertEquals("Teste", fileObject.toUri().getPath());
        Assert.assertEquals("Teste", fileObject.getName());
        Assert.assertEquals(JavaFileObject.Kind.CLASS, fileObject.getKind());
        Assert.assertEquals(0, fileObject.getLastModified());
        Assert.assertFalse(fileObject.delete());
        Assert.assertNull(fileObject.getNestingKind());
        Assert.assertNull(fileObject.getAccessLevel());
        Assert.assertNull(fileObject.getCurrentSourceClass());
        Assert.assertEquals("com.github.schmittjoaopedro.mcc.engine.OutputMemoryJavaFileObject[Teste]", fileObject.toString());
    }

}
