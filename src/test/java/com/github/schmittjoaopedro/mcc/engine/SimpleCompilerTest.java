package com.github.schmittjoaopedro.mcc.engine;


import com.github.schmittjoaopedro.mcc.manager.MemoryCompilerProvider;
import com.github.schmittjoaopedro.mcc.manager.MemoryCompilerTask;
import com.github.schmittjoaopedro.mcc.message.MemoryCompilerException;
import com.github.schmittjoaopedro.mcc.object.SourceClassLoader;
import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.object.SourceTask;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;

@SuppressWarnings("all")
public class SimpleCompilerTest {

    @Test
    public void simpleTest() throws Exception {

        String sourceCode = "package comp.test; public class Test { public String sayHello() { return \"Hello World!\"; } };";
        SourceClass sourceClass = new SourceClass("comp.test", "Test", sourceCode);
        SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());

        MemoryClassCompiler compiler = new MemoryClassCompiler();
        compiler.checkAndCompile(sourceClass);

        Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);

        Object o = loadedClass.newInstance();
        Method m = loadedClass.getDeclaredMethod("sayHello", null);
        Assert.assertEquals(m.invoke(o, null), "Hello World!");


        Class loadedClass2 = classLoader.loadSourceClassLoader(sourceClass);

        Object o2 = loadedClass.newInstance();
        Method m2 = loadedClass.getDeclaredMethod("sayHello", null);
        Assert.assertEquals(m2.invoke(o, null), "Hello World!");

    }

    @Test
    public void simpleTestList() throws Exception {

        SourceTask sourceTask = new SourceTask();

        for(int i = 0; i < 10; i++) {
            String sourceCode = "package comp.test; public class Test" + i + " { public String sayHello(Integer val) { return val + \" - Hello World!\"; } };";
            sourceTask.createSourceClass("comp.test", "Test" + i, sourceCode);
        }
        MemoryClassCompiler compiler = new MemoryClassCompiler();
        compiler.checkAndCompile(sourceTask);
        SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());

        for(int i = 0; i < 10; i++) {
            SourceClass sourceClass = sourceTask.getSourcesClass().get(i);
            Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);
            Object o = loadedClass.newInstance();
            Method m = loadedClass.getDeclaredMethod("sayHello", Integer.class);
            Assert.assertEquals(m.invoke(o, i), i + " - Hello World!");
        }

    }

    @Test
    public void testClassLoad() {

        SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());

        try {
            SourceClass c1 = new SourceClass();
            classLoader.loadSourceClassLoader(c1);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "COMPILER_ERROR: Class null.null have problems and can not be loaded");
        }

        try {
            SourceClass c1 = new SourceClass("teste", "Teste", "package teste; public class Teste {}");
            classLoader.loadSourceClassLoader(c1);
        } catch (MemoryCompilerException ex) {
            Assert.assertEquals(ex.getMessage(), "COMPILER_ERROR: Class teste.Teste have problems and can not be loaded");
        }

    }

    @Test
    public void testLoadClasspath() throws IOException {
        String path = Paths.get(new File(".").getCanonicalPath(), "src", "test", "resources", "gson-2.2.4.jar").toString();
        SourceClass sourceClass = new SourceClass("teste", "Teste", "package teste; import com.google.gson.Gson; public class Teste {}");
        MemoryClassCompiler compiler = new MemoryClassCompiler(Arrays.asList(path));
        try {
            compiler.compile(sourceClass);
        } catch (Exception ex) {
            Assert.assertFalse(true);
        }
    }


    @Test
    public void testLoadClasspathManager() throws IOException {
        String path = Paths.get(new File(".").getCanonicalPath(), "src", "test", "resources", "gson-2.2.4.jar").toString();
        SourceClass sourceClass = new SourceClass("teste", "Teste", "package teste; import com.google.gson.Gson; public class Teste {}");
        try {
            MemoryCompilerProvider.getInstance().reset();
            MemoryCompilerProvider.getInstance().setClasspath(path);
            MemoryCompilerProvider.getInstance().getAvailable().executeCompilation(sourceClass);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertFalse(true);
        }
    }


    @Test
    public void testLoadClasspathManagerTask() throws IOException {
        String path = Paths.get(new File(".").getCanonicalPath(), "src", "test", "resources", "gson-2.2.4.jar").toString();
        SourceClass sourceClass = new SourceClass("teste", "Teste", "package teste; import com.google.gson.Gson; public class Teste {}");
        SourceTask sourceTask = new SourceTask();
        sourceTask.addSourceClass(sourceClass);
        try {
            MemoryCompilerTask task = new MemoryCompilerTask(MemoryCompilerProvider.getInstance(), Arrays.asList(path));
            task.executeCompilation(sourceTask);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertFalse(true);
        }
    }


}
