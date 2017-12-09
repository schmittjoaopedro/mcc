package memory.compiler.engine;


import memory.compiler.manager.MemoryCompilerProvider;
import memory.compiler.manager.MemoryCompilerTask;
import memory.compiler.message.MemoryCompilerException;
import memory.compiler.object.SourceClass;
import memory.compiler.object.SourceClassLoader;
import memory.compiler.object.SourceTask;
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

        String sourceCode = "package weg.net; public class Teste { public String t() { return \"Hello World!\"; } };";
        SourceClass sourceClass = new SourceClass("weg.net", "Teste", sourceCode);
        SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());

        MemoryClassCompiler compiler = new MemoryClassCompiler();
        compiler.checkAndCompile(sourceClass);

        Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);

        Object o = loadedClass.newInstance();
        Method m = loadedClass.getDeclaredMethod("t", null);
        Assert.assertEquals(m.invoke(o, null), "Hello World!");


        Class loadedClass2 = classLoader.loadSourceClassLoader(sourceClass);

        Object o2 = loadedClass.newInstance();
        Method m2 = loadedClass.getDeclaredMethod("t", null);
        Assert.assertEquals(m2.invoke(o, null), "Hello World!");

    }

    @Test
    public void simpleTestList() throws Exception {

        SourceTask sourceTask = new SourceTask();

        for(int i = 0; i < 10; i++) {
            String sourceCode = "package weg.net; public class Teste" + i + " { public String t(Integer val) { return val + \" - Hello World!\"; } };";
            sourceTask.createSourceClass("weg.net", "Teste" + i, sourceCode);
        }
        MemoryClassCompiler compiler = new MemoryClassCompiler();
        compiler.checkAndCompile(sourceTask);
        SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());

        for(int i = 0; i < 10; i++) {
            SourceClass sourceClass = sourceTask.getSourcesClass().get(i);
            Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);
            Object o = loadedClass.newInstance();
            Method m = loadedClass.getDeclaredMethod("t", Integer.class);
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
