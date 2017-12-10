package com.github.schmittjoaopedro.mcc.manager;

import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.object.SourceTask;
import org.junit.Assert;
import org.junit.Test;

public class TestEngine {

    /**
     * Test thread pool that is started with 10 instances
     */
    @Test
    public void testManager() {

        MemoryCompilerTask memoryCompilerTask1 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask2 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask3 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask4 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask5 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask6 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask7 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask8 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask9 = MemoryCompilerProvider.getInstance().getAvailable();
        MemoryCompilerTask memoryCompilerTask10 = MemoryCompilerProvider.getInstance().getAvailable();

        Assert.assertNull(MemoryCompilerProvider.getInstance().getAvailable());

        SourceClass s1 = new SourceClass("teste", "Teste0", "package teste; public class Teste0 {}");
        SourceClass s2 = new SourceClass("teste", "Teste1", "package teste; public class Teste1 {}");
        SourceClass s3 = new SourceClass("teste", "Teste2", "package teste; public class Teste2 {}");
        SourceClass s4 = new SourceClass("teste", "Teste3", "package teste; public class Teste3 {}");
        SourceClass s5 = new SourceClass("teste", "Teste4", "package teste; public class Teste4 {}");
        SourceClass s6 = new SourceClass("teste", "Teste5", "package teste; public class Teste5 {}");
        SourceClass s7 = new SourceClass("teste", "Teste6", "package teste; public class Teste6 {}");
        SourceClass s8 = new SourceClass("teste", "Teste7", "package teste; public class Teste7 {}");
        SourceClass s9 = new SourceClass("teste", "Teste8", "package teste; public class Teste8 {}");
        SourceTask s10 = new SourceTask();
        s10.createSourceClass("teste", "Teste9", "package teste; public class Teste9 {}");

        memoryCompilerTask1.executeCompilation(s1);
        memoryCompilerTask2.executeCompilation(s2);
        memoryCompilerTask3.executeCompilation(s3);
        memoryCompilerTask4.executeCompilation(s4);
        memoryCompilerTask5.executeCompilation(s5);
        memoryCompilerTask6.executeCompilation(s6);
        memoryCompilerTask7.executeCompilation(s7);
        memoryCompilerTask8.executeCompilation(s8);
        memoryCompilerTask9.executeCompilation(s9);
        memoryCompilerTask10.executeCompilation(s10);

        Assert.assertEquals(s1.getBytecode().length, 192);
        Assert.assertEquals(s2.getBytecode().length, 192);
        Assert.assertEquals(s3.getBytecode().length, 192);
        Assert.assertEquals(s4.getBytecode().length, 192);
        Assert.assertEquals(s5.getBytecode().length, 192);
        Assert.assertEquals(s6.getBytecode().length, 192);
        Assert.assertEquals(s7.getBytecode().length, 192);
        Assert.assertEquals(s8.getBytecode().length, 192);
        Assert.assertEquals(s9.getBytecode().length, 192);
        Assert.assertEquals(s10.getSourcesClass().get(0).getBytecode().length, 192);

        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNotNull(MemoryCompilerProvider.getInstance().getAvailable());
        Assert.assertNull(MemoryCompilerProvider.getInstance().getAvailable());

    }

}
