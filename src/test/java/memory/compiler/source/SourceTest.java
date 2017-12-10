package memory.compiler.source;

import memory.compiler.engine.MemoryClassCompiler;
import memory.compiler.message.MemoryCompilerException;
import memory.compiler.message.MessageStatus;
import memory.compiler.object.SourceClass;
import memory.compiler.object.SourceTask;
import org.junit.Assert;
import org.junit.Test;

public class SourceTest {

    @Test
    public void testCreateSourceClass() {

        SourceClass sourceClass = new SourceClass();

        sourceClass.setPackageName("teste");
        sourceClass.setClassName("Teste");
        sourceClass.setSourceCode("package teste; public class Teste { };");

        MemoryClassCompiler memoryClassCompiler = new MemoryClassCompiler();
        memoryClassCompiler.compile(sourceClass);


        Assert.assertTrue(sourceClass.isValid());
        Assert.assertEquals(sourceClass.getJavaFileClassName(), "Teste.java");
        Assert.assertEquals(sourceClass.getFullClassName(), "teste.Teste");
        Assert.assertEquals(sourceClass.getClassName(), "Teste");
        Assert.assertEquals(sourceClass.getPackageName(), "teste");
        Assert.assertEquals(sourceClass.getSourceCode(), "package teste; public class Teste { };");
        Assert.assertNotNull(sourceClass.getBytecode());
        Assert.assertEquals(sourceClass.getBytecode().length, 190);
        Assert.assertEquals(sourceClass.getStatus().getMessage(), "Compiled with success!");
        Assert.assertEquals(sourceClass.getStatus().getStatus(), MessageStatus.COMPILED);

        SourceClass sourceClassEmpty = new SourceClass();
        Assert.assertFalse(sourceClassEmpty.isValid());
        Assert.assertNull(sourceClassEmpty.getStatus());

        SourceClass sourceClassInvalidClass = new SourceClass();
        sourceClassInvalidClass.setPackageName("teste");
        sourceClassInvalidClass.setSourceCode("package teste; public class Teste { };");
        Assert.assertFalse(sourceClassInvalidClass.isValid());
        Assert.assertNull(sourceClassInvalidClass.getStatus());

        SourceClass sourceClassInvalidPackage = new SourceClass();
        sourceClassInvalidPackage.setClassName("Teste");
        sourceClassInvalidPackage.setSourceCode("package teste; public class Teste { };");
        Assert.assertFalse(sourceClassInvalidPackage.isValid());
        Assert.assertNull(sourceClassInvalidPackage.getStatus());

        SourceClass sourceClassInvalidCode = new SourceClass();
        sourceClassInvalidCode.setClassName("Teste");
        sourceClassInvalidCode.setPackageName("teste");
        Assert.assertFalse(sourceClassInvalidCode.isValid());
        Assert.assertNull(sourceClassInvalidCode.getStatus());

        SourceClass sourceClassInvalidClassTrim = new SourceClass("teste", "", "package teste; public class Teste { };");
        Assert.assertFalse(sourceClassInvalidClassTrim.isValid());
        Assert.assertNull(sourceClassInvalidClassTrim.getStatus());

        SourceClass sourceClassInvalidPkgTrim = new SourceClass("", "Teste", "package teste; public class Teste { };");
        Assert.assertFalse(sourceClassInvalidPkgTrim.isValid());
        Assert.assertNull(sourceClassInvalidPkgTrim.getStatus());

        SourceClass sourceClassInvalidCodeTrim = new SourceClass("teste", "Teste", "");
        Assert.assertFalse(sourceClassInvalidCodeTrim.isValid());
        Assert.assertNull(sourceClassInvalidCodeTrim.getStatus());

    }

    @Test
    public void testeCompiledWrongClass() {

        MemoryClassCompiler memoryClassCompiler = new MemoryClassCompiler();

        SourceClass sourceClassEmpty = new SourceClass();
        memoryClassCompiler.compile(sourceClassEmpty);
        Assert.assertEquals(sourceClassEmpty.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassEmpty.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidClass = new SourceClass();
        sourceClassInvalidClass.setPackageName("teste");
        sourceClassInvalidClass.setSourceCode("package teste; public class Teste { };");
        memoryClassCompiler.compile(sourceClassInvalidClass);
        Assert.assertEquals(sourceClassInvalidClass.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidClass.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidPackage = new SourceClass();
        sourceClassInvalidPackage.setClassName("Teste");
        sourceClassInvalidPackage.setSourceCode("package teste; public class Teste { };");
        memoryClassCompiler.compile(sourceClassInvalidPackage);
        Assert.assertEquals(sourceClassInvalidPackage.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidPackage.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidCode = new SourceClass();
        sourceClassInvalidCode.setClassName("Teste");
        sourceClassInvalidCode.setPackageName("teste");
        memoryClassCompiler.compile(sourceClassInvalidCode);
        Assert.assertEquals(sourceClassInvalidCode.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidCode.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidClassTrim = new SourceClass("teste", "", "package teste; public class Teste { };");
        memoryClassCompiler.compile(sourceClassInvalidClassTrim);
        Assert.assertEquals(sourceClassInvalidClassTrim.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidClassTrim.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidPkgTrim = new SourceClass("", "Teste", "package teste; public class Teste { };");
        memoryClassCompiler.compile(sourceClassInvalidPkgTrim);
        Assert.assertEquals(sourceClassInvalidPkgTrim.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidPkgTrim.getStatus().getStatus(), MessageStatus.FAILED);

        SourceClass sourceClassInvalidCodeTrim = new SourceClass("teste", "Teste", "");
        memoryClassCompiler.compile(sourceClassInvalidCodeTrim);
        Assert.assertEquals(sourceClassInvalidCodeTrim.getStatus().getMessage(), "Invalid class to compilation");
        Assert.assertEquals(sourceClassInvalidCodeTrim.getStatus().getStatus(), MessageStatus.FAILED);

    }

    @Test
    public void testSourceTask() {

        MemoryClassCompiler memoryClassCompiler = new MemoryClassCompiler();

        SourceTask sourceTask = new SourceTask();
        try {
            memoryClassCompiler.compile(sourceTask);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "FAILED: You do not pass anything to be compiled");
            Assert.assertEquals(ex.getSuppressed().length, 1);
            Assert.assertEquals(ex.getSuppressed()[0].getMessage(), "FAILED: You do not pass anything to be compiled");
        }
        Assert.assertTrue(sourceTask.getSourcesClass().isEmpty());

        SourceTask sourceTaskInvalidClass = new SourceTask();
        sourceTaskInvalidClass.createSourceClass("teste", "", "package teste; public class Teste { };");
        try {
            memoryClassCompiler.compile(sourceTaskInvalidClass);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "FAILED: You do not pass anything to be compiled");
            Assert.assertEquals(ex.getSuppressed().length, 1);
            Assert.assertEquals(ex.getSuppressed()[0].getMessage(), "FAILED: You do not pass anything to be compiled");
        }
        Assert.assertTrue(sourceTaskInvalidClass.getSourcesClass().isEmpty());

        SourceTask sourceClassInvalidPackage = new SourceTask();
        sourceClassInvalidPackage.createSourceClass("", "Teste", "package teste; public class Teste { };");
        try {
            memoryClassCompiler.compile(sourceClassInvalidPackage);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "FAILED: You do not pass anything to be compiled");
            Assert.assertEquals(ex.getSuppressed().length, 1);
            Assert.assertEquals(ex.getSuppressed()[0].getMessage(), "FAILED: You do not pass anything to be compiled");
        }
        Assert.assertTrue(sourceClassInvalidPackage.getSourcesClass().isEmpty());

        SourceTask sourceClassInvalidCode = new SourceTask();
        sourceClassInvalidCode.createSourceClass("teste", "Teste", "");
        try {
            memoryClassCompiler.compile(sourceClassInvalidCode);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "FAILED: You do not pass anything to be compiled");
            Assert.assertEquals(ex.getSuppressed().length, 1);
            Assert.assertEquals(ex.getSuppressed()[0].getMessage(), "FAILED: You do not pass anything to be compiled");
        }
        Assert.assertTrue(sourceClassInvalidCode.getSourcesClass().isEmpty());

        //INVALID CLASSES, NO ARE ADD IN LIST FOR COMPILATION
        SourceClass sourceClassInvalidClassTrim = new SourceClass("teste", "", "package teste; public class Teste { };");
        SourceClass validsourceClass = new SourceClass("teste", "Teste0", "package teste; public class Teste0 { };");
        SourceClass sourceClassInvalidPkgTrim = new SourceClass("", "Teste", "package teste; public class Teste { };");
        SourceClass sourceClassInvalidCodeTrim = new SourceClass("teste", "Teste", "");
        SourceTask sourceTaskBattery = new SourceTask();
        sourceTaskBattery.createSourceClass("teste", "Teste1", "package teste; public class Teste1 { };");
        sourceTaskBattery.createSourceClass("teste", "Teste1", "package teste; public class Teste1 { };");
        sourceTaskBattery.addSourceClass(sourceClassInvalidClassTrim);
        sourceTaskBattery.addSourceClass(validsourceClass);
        sourceTaskBattery.addSourceClass(sourceClassInvalidPkgTrim);
        sourceTaskBattery.addSourceClass(sourceClassInvalidCodeTrim);
        Assert.assertEquals(sourceTaskBattery.getSourcesClass().size(), 2);

        memoryClassCompiler.compile(sourceTaskBattery);

        for(SourceClass sourceClsItem : sourceTaskBattery.getSourcesClass()) {
            Assert.assertEquals(sourceClsItem.getBytecode().length, 192);
        }

        sourceTaskBattery.removeSourceClass("teste", "Teste");
        Assert.assertEquals(sourceTaskBattery.getSourcesClass().size(), 2);
        sourceTaskBattery.removeSourceClass("teste", "Teste1");
        Assert.assertEquals(sourceTaskBattery.getSourcesClass().size(), 1);
        Assert.assertNull(sourceTaskBattery.findSourceClass("teste.Teste1"));
        Assert.assertNotNull(sourceTaskBattery.findSourceClass("teste.Teste0"));

    }

    @Test
    public void testEnumMessage() {
        Assert.assertEquals(MessageStatus.valueOf("COMPILED"), MessageStatus.COMPILED);
        Assert.assertEquals(MessageStatus.valueOf("COMPILER_ERROR"), MessageStatus.COMPILER_ERROR);
        Assert.assertEquals(MessageStatus.valueOf("FAILED"), MessageStatus.FAILED);
        Assert.assertEquals(MessageStatus.valueOf("NO_COMPILER_AVAILABLE"), MessageStatus.NO_COMPILER_AVAILABLE);
        Assert.assertEquals(MessageStatus.valueOf("PMD_ERROR"), MessageStatus.PMD_ERROR);

    }

}
