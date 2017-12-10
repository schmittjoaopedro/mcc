package com.github.schmittjoaopedro.mcc.engine;

import com.github.schmittjoaopedro.mcc.message.MemoryCompilerException;
import com.github.schmittjoaopedro.mcc.message.MessageStatus;
import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.object.SourceTask;
import org.junit.Assert;
import org.junit.Test;

import javax.tools.Diagnostic;

public class CompilerErrorTest {


    @Test
    public void testSingleError() {

        SourceClass sourceClass = new SourceClass();
        sourceClass.setPackageName("teste");
        sourceClass.setClassName("Teste");
        sourceClass.setSourceCode("package test");

        MemoryClassCompiler compiler = new MemoryClassCompiler();
        boolean throwError = false;

        try {
            compiler.compile(sourceClass);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.toString(),
                    "FAILED: Error in compilation of class\n" +
                            "Diagnostic: [Teste.java:1: error: reached end of file while parsing\n" +
                            "package test\n" +
                            "            ^]");
            Assert.assertNull(sourceClass.getBytecode());
            Assert.assertEquals(ex.getMessageCompiler().getMessage(), "Error in compilation of class");
            Assert.assertEquals(ex.getMessageCompiler().getStatus(), MessageStatus.FAILED);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getCode(), "compiler.err.premature.eof");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getColumnNumber(), 13);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getEndPosition(), 12);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getLineNumber(), 1);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getMessage(null), "reached end of file while parsing");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getPosition(), 12);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getStartPosition(), 12);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getKind(), Diagnostic.Kind.ERROR);
            throwError = true;
        }
        Assert.assertTrue(throwError);

    }


    @Test
    public void testSingleError2() {

        SourceClass sourceClass = new SourceClass();
        sourceClass.setPackageName("teste");
        sourceClass.setClassName("Teste");
        sourceClass.setSourceCode("package teste; public class Teste { public void t() { return 2; } }");
        boolean throwError = false;

        MemoryClassCompiler compiler = new MemoryClassCompiler();
        try {
            compiler.compile(sourceClass);
        } catch (MemoryCompilerException ex) {
            Assert.assertNull(sourceClass.getBytecode());
            Assert.assertEquals(ex.getMessageCompiler().getMessage(), "Error in compilation of class");
            Assert.assertEquals(ex.getMessageCompiler().getStatus(), MessageStatus.FAILED);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getCode(), "compiler.err.prob.found.req");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getColumnNumber(), 62);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getEndPosition(), 62);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getLineNumber(), 1);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getMessage(null), "incompatible types: unexpected return value");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getPosition(), 61);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getStartPosition(), 61);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getKind(), Diagnostic.Kind.ERROR);
            throwError = true;
        }
        Assert.assertTrue(throwError);

    }

    @Test
    public void testSingleError3() {

        SourceClass sourceClass = new SourceClass();
        sourceClass.setPackageName("teste");
        sourceClass.setClassName("Teste");

        String code =
                "package teste;\n"
                        + "\n"
                        + "public class Teste {\n"
                        + "\n"
                        + "		public void mostrar(int value) {\n"
                        + "			System.out.println(value);\n"
                        + "		}\n"
                        + "\n"
                        + "\n"
                        + "		public void soma(int val1, int val2) {\n"
                        + "			this.mostrar(val1 + a);\n"
                        + "		}\n"
                        + "\n"
                        + "}\n";

        sourceClass.setSourceCode(code);
        boolean throwError = false;

        MemoryClassCompiler compiler = new MemoryClassCompiler();
        try {
            compiler.compile(sourceClass);
        } catch (MemoryCompilerException ex) {
            Assert.assertNull(sourceClass.getBytecode());
            Assert.assertEquals(ex.getMessageCompiler().getMessage(), "Error in compilation of class");
            Assert.assertEquals(ex.getMessageCompiler().getStatus(), MessageStatus.FAILED);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getCode(), "compiler.err.cant.resolve.location");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getColumnNumber(), 45);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getEndPosition(), 174);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getLineNumber(), 11);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getMessage(null), "cannot find symbol\n  symbol:   variable a\n  location: class teste.Teste");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getPosition(), 173);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getStartPosition(), 173);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getKind(), Diagnostic.Kind.ERROR);
            throwError = true;
        }
        Assert.assertTrue(throwError);
    }

    @Test
    public void testTaskListError() {

        SourceTask sourceTask = new SourceTask();
        sourceTask.createSourceClass("teste", "Teste0", "package teste; public class Teste0 {}");
        sourceTask.createSourceClass("teste", "Teste1", "public class Teste0 {}");
        sourceTask.createSourceClass("teste", "Teste2", "package teste; public class Teste2 {}");

        MemoryClassCompiler compiler = new MemoryClassCompiler();

        boolean throwError = false;

        try {
            compiler.compile(sourceTask);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(sourceTask.getSourcesClass().size(), 3);
            SourceClass sourceClass1 = sourceTask.findSourceClass("teste.Teste0");
            Assert.assertNull(sourceClass1.getBytecode());
            SourceClass sourceClass2 = sourceTask.findSourceClass("teste.Teste1");
            Assert.assertNull(sourceClass2.getBytecode());
            SourceClass sourceClass3 = sourceTask.findSourceClass("teste.Teste2");
            Assert.assertNull(sourceClass3.getBytecode());

            Assert.assertEquals(ex.getMessageCompiler().getMessage(), "Error in compilation of class");
            Assert.assertEquals(ex.getMessageCompiler().getStatus(), MessageStatus.FAILED);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getCode(), "compiler.err.class.public.should.be.in.file");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getColumnNumber(), 8);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getEndPosition(), 22);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getLineNumber(), 1);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getMessage(null), "class Teste0 is public, should be declared in a file named Teste0.java");
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getPosition(), 7);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getStartPosition(), 0);
            Assert.assertEquals(ex.getMessageCompiler().getDiagnostics().get(0).getKind(), Diagnostic.Kind.ERROR);
            throwError = true;
        }
        Assert.assertTrue(throwError);
    }

}
