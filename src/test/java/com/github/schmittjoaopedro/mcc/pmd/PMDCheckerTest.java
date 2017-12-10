package com.github.schmittjoaopedro.mcc.pmd;


import java.util.Scanner;

import com.github.schmittjoaopedro.mcc.message.MemoryCompilerException;
import com.github.schmittjoaopedro.mcc.object.SourceClass;
import com.github.schmittjoaopedro.mcc.object.SourceTask;
import org.junit.Assert;
import org.junit.Test;

public class PMDCheckerTest {

	@Test
	public void testeSamplePMD() {

		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode("package teste; public class Teste { private int t; }");
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(), "PMD_ERROR: PMD Validation failed\nTeste : 1 : Avoid unused private fields such as 't'.\n");
		}
		
	}
	
	@Test
	public void testePMDCheck() {
		
		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode("package teste; public class Teste { private int t;\nprivate int x; }");
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(), "PMD_ERROR: PMD Validation failed\nTeste : 1 : Avoid unused private fields such as 't'.\nTeste : 2 : Avoid unused private fields such as 'x'.\n");
		}
		
	}
	
	@Test
	public void testeUnusedPmdCheck() {
		
			String source = ""
				+ "package teste;\n"
				+ "\n"
				+ "public class Teste {\n"
				+ "\n"
				+ "		private static int FOO = 2;\n"
				+ "		private int i = 5;\n"
				+ "\n"
				+ "		public void doSomething() {\n"
				+ "			int i = 5;\n"
				+ "		}\n"
				+ "\n"
				+ "		private void foo() {}\n"
				+ "\n"
				+ "		private void bar(String howdy) { }\n"
				+ "\n"
				+ "}\n";
			
			SourceClass sourceClass = new SourceClass();
			sourceClass.setPackageName("teste");
			sourceClass.setClassName("Teste");
			sourceClass.setSourceCode(source);
			try {
				new MemoryPMDValidator().check(sourceClass);
			} catch(MemoryCompilerException ex) {
				Assert.assertEquals(ex.getMessage(), 
						  "PMD_ERROR: PMD Validation failed\n"
						+ "Teste : 5 : Avoid unused private fields such as 'FOO'.\n"
						+ "Teste : 6 : Avoid unused private fields such as 'i'.\n"
						+ "Teste : 9 : Avoid unused local variables such as 'i'.\n"
						+ "Teste : 12 : Avoid unused private methods such as 'foo()'.\n"
						+ "Teste : 14 : Avoid unused method parameters such as 'howdy'.\n"
						+ "Teste : 14 : Avoid unused private methods such as 'bar(String)'.\n");
			}
		
	}
	


	@Test
	@SuppressWarnings("all")
	public void testeBasicPmdCheck() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String source = new Scanner(classloader.getResourceAsStream("BasicPMD.txt")).useDelimiter("\\Z").next();
		
		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode(source);
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(), "PMD_ERROR: PMD Validation failed\n"
					+ "Teste : 3 : This class has too many methods, consider refactoring it.\n"
					+ "Teste : 5 : Avoid instantiating Boolean objects; reference Boolean.TRUE or Boolean.FALSE or call Boolean.valueOf() instead.\n"
					+ "Teste : 6 : Avoid instantiating Boolean objects; reference Boolean.TRUE or Boolean.FALSE or call Boolean.valueOf() instead.\n"
					+ "Teste : 9 : Empty initializer was found\n"
					+ "Teste : 9 : Empty static initializer was found\n"
					+ "Teste : 15 : Double checked locking is not thread safe in Java.\n"
					+ "Teste : 28 : Avoid unused local variables such as 'fis'.\n"
					+ "Teste : 29 : Avoid empty catch blocks\n"
					+ "Teste : 35 : Avoid empty if statements\n"
					+ "Teste : 41 : Avoid empty while statements\n"
					+ "Teste : 47 : Avoid empty try blocks\n"
					+ "Teste : 55 : Avoid unused local variables such as 'x'.\n"
					+ "Teste : 56 : Avoid empty finally blocks\n"
					+ "Teste : 63 : Avoid empty switch statements\n"
					+ "Teste : 71 : Avoid modifying an outer loop incrementer in an inner loop for update expression\n"
					+ "Teste : 78 : This for loop could be simplified to a while loop\n"
					+ "Teste : 83 : Avoid unnecessary temporaries when converting primitives to Strings\n"
					+ "Teste : 83 : Avoid unused local variables such as 'foo'.\n"
					+ "Teste : 88 : Ensure you override both equals() and hashCode()\n"
					+ "Teste : 98 : Avoid returning from a finally block\n"
					+ "Teste : 103 : Avoid empty synchronized blocks\n"
					+ "Teste : 109 : Avoid unused local variables such as 'x'.\n"
					+ "Teste : 110 : Avoid unnecessary return statements\n"
					+ "Teste : 114 : Avoid empty if statements\n"
					+ "Teste : 114 : Do not use if statements that are always true or always false\n"
					+ "Teste : 121 : An empty statement (semicolon) not part of a loop\n"
					+ "Teste : 124 : An empty statement (semicolon) not part of a loop\n"
					+ "Teste : 127 : Avoid unused private methods such as 'unnecessaryFinalModifier()'.\n"
					+ "Teste : 132 : Avoid empty if statements\n"
					+ "Teste : 132 : These nested if statements could be combined\n");
		}
	}
	
	@Test
	@SuppressWarnings("all")
	public void testeCodeSizePmdCheck() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String source = new Scanner(classloader.getResourceAsStream("CodeSizePMD.txt")).useDelimiter("\\Z").next();
		
		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode(source);
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(),
					  "PMD_ERROR: PMD Validation failed\n"
						  + "The class 'Teste' has a Cyclomatic Complexity of 4 (Highest = 10).\n"
						  + "The class 'Teste' has a Modified Cyclomatic Complexity of 4 (Highest = 10).\n"
						  + "The class 'Teste' has a Standard Cyclomatic Complexity of 4 (Highest = 10).\n"
						  + "Teste : 5 : The method 'bar' has a Cyclomatic Complexity of 10.\n"
						  + "Teste : 5 : The method 'bar' has a Modified Cyclomatic Complexity of 10.\n"
						  + "Teste : 5 : The method 'bar' has a Standard Cyclomatic Complexity of 10.\n"
						  + "Teste : 27 : Avoid really long methods.\n"
						  + "Teste : 27 : The method doSomething() has an NCSS line count of 101\n"
						  + "Teste : 28 : The String literal \"Hello world!\" appears 100 times in this file; the first occurrence is on line 28\n"
						  + "Teste : 130 : Avoid long parameter lists.\n");
		}
	}
	
	@Test
	@SuppressWarnings("all")
	public void testeStringPmdCheck() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String source = new Scanner(classloader.getResourceAsStream("StringPMD.txt")).useDelimiter("\\Z").next();
		
		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode(source);
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(), 
					  "PMD_ERROR: PMD Validation failed\n"
						  	+ "Teste : 3 : This class has too many methods, consider refactoring it.\n"
						  	+ "Teste : 5 : Avoid unused private fields such as 'memoryLeak'.\n"
						  	+ "Teste : 5 : StringBuffers can grow quite a lot, and so may become a source of memory leak (if the owning class has a long life time).\n"
						  	+ "Teste : 6 : StringBuffers can grow quite a lot, and so may become a source of memory leak (if the owning class has a long life time).\n"
						  	+ "Teste : 8 : Avoid unused private methods such as 'bar()'.\n"
						  	+ "Teste : 9 : The String literal \"Howdy\" appears 4 times in this file; the first occurrence is on line 9\n"
						  	+ "Teste : 14 : Avoid unused method parameters such as 'x'.\n"
						  	+ "Teste : 16 : Avoid unused private methods such as 'baz()'.\n"
						  	+ "Teste : 18 : Avoid calling toString() on String objects; this is unnecessary.\n"
						  	+ "Teste : 22 : Avoid concatenating nonliterals in a StringBuffer/StringBuilder constructor or append().\n"
						  	+ "Teste : 29 : Using equalsIgnoreCase() is cleaner than using toUpperCase/toLowerCase().equals().\n"
						  	+ "Teste : 34 : Avoid empty if statements\n"
						  	+ "Teste : 34 : This is an inefficient use of StringBuffer.toString; call StringBuffer.length instead.\n"
						  	+ "Teste : 35 : Avoid empty if statements\n"
						  	+ "Teste : 40 : Avoid appending characters as strings in StringBuffer.append.\n"
						  	+ "Teste : 45 : Avoid unused private methods such as 'bak()'.\n"
						  	+ "Teste : 46 : StringBuffer constructor is initialized with size 16, but has at least 22 characters appended.\n"
						  	+ "Teste : 47 : Avoid appending characters as strings in StringBuffer.append.\n"
						  	+ "Teste : 47 : StringBuffer (or StringBuilder).append is called 4 consecutive times with literal Strings. Use a single append with a single combined String.\n"
						  	+ "Teste : 47 : StringBuffer (or StringBuilder).append is called consecutively without reusing the target variable.\n"
						  	+ "Teste : 52 : StringBuffer constructor is initialized with size 16, but has at least 60 characters appended.\n"
						  	+ "Teste : 53 : StringBuffer (or StringBuilder).append is called 2 consecutive times with literal Strings. Use a single append with a single combined String.\n"
						  	+ "Teste : 53 : StringBuffer (or StringBuilder).append is called 2 consecutive times with literal Strings. Use a single append with a single combined String.\n"
						  	+ "Teste : 60 : No need to call String.valueOf to append to a string.\n"
						  	+ "Teste : 66 : Use equals() to compare strings instead of '==' or '!='\n");
		}
	}
	
	@Test
	public void testCompiledClass() {
		SourceClass sourceClass = new SourceClass();
		sourceClass.setPackageName("teste");
		sourceClass.setClassName("Teste");
		sourceClass.setSourceCode("package teste; public class Teste { }");
		try {
			new MemoryPMDValidator().check(sourceClass);
		} catch(MemoryCompilerException ex) {
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testCompileSouceTask() {
		
		SourceTask sourceTask = new SourceTask();
		sourceTask.createSourceClass("teste", "Teste", "package teste; public class Teste { }");
		sourceTask.createSourceClass("teste", "Teste2", "package teste; public class Teste2 { }");
		sourceTask.createSourceClass("teste", "Teste3", "package teste; public class Teste3 { }");
		try {
			new MemoryPMDValidator().check(sourceTask);
		} catch(MemoryCompilerException ex) {
			Assert.assertTrue(false);
		}
		
	}
	
	@Test
	public void testErrorSouceTask() {
		
		SourceTask sourceTask = new SourceTask();
		sourceTask.createSourceClass("teste", "Teste", "package teste; public class Teste { }");
		sourceTask.createSourceClass("teste", "Teste2", "package teste; public class Teste2 { private int i; }");
		sourceTask.createSourceClass("teste", "Teste3", "package teste; public class Teste3 { }");
		try {
			new MemoryPMDValidator().check(sourceTask);
		} catch(MemoryCompilerException ex) {
			Assert.assertEquals(ex.getMessage(), "PMD_ERROR: PMD Validation failed\nTeste2 : 1 : Avoid unused private fields such as 'i'.\n");
		}
		
	}
	
}
