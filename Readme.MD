# Memory Class Compiler (MCC)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.schmittjoaopedro/mcc/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.github.schmittjoaopedro/mcc)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

The Memory Class Compiler (MCC) is a simple Java library used to compile Java classes in runtime.

## Purpose

The purpose of MCC is to provide a simple API to be used in applications that need to compile and execute Java source code on the fly, where the Java source code is represented as a String.

## License

This software is available under [MIT license](https://opensource.org/licenses/MIT).

## Technical Overview

The software was wrote in [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html) and tested with the following versions of Java Development Toolkit (JDK): JDK 7, JDK 8 and JDK 9. There is only one requirement, the library needs the program to be running under a JDK, because only JDK grants access to the compiler API.

The following features are available:
*	A simple library that facilitate the access to JavaCompiler implementation of JDK.
*	A simple library that facilitate the access to execute PMD validation in Java source code.
*	A custom ClassLoader to load java source code compiled in runtime.

## Getting Started

To start using MCC, first embed the library into your Java application via the following snippet
```xml
<dependency>
    <groupId>com.github.schmittjoaopedro</groupId>
    <artifactId>mcc</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Create a simple class

This is a simple example of compilation using MCC:

```java
//Create a simple String with the Java source code
String sourceCode = 
	"package comp.test;" +
	"public class Test {" + 
	"    public String sayHello() {" +
	"        return \"Hello World!\";" +
	"    }" +
	"}";

//Create a object to encapsulate the source code
SourceClass sourceClass = new SourceClass("comp.test", "Test", sourceCode);

//Compile the SourceClass object
MemoryClassCompiler compiler = new MemoryClassCompiler();
compiler.checkAndCompile(sourceClass);

//Create a class loader and define the compiled class
SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());
Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);

//Invoke the method with reflection
Object o = loadedClass.newInstance();
Method m = loadedClass.getDeclaredMethod("sayHello", null);
Assert.assertEquals(m.invoke(o, null), "Hello World!");
```

### Executing batch compilation

This is a example compiling one or more java classes with batch jobs:

```java
//Create a source task to execute batch compilation
SourceTask sourceTask = new SourceTask();

//Creating some classes
for(int i = 0; i < 10; i++) {
	String sourceCode = 
		"package comp.test;" + 
		"public class Test" + i + " {" +
		"	public String sayHello(Integer val) { " +
		"		return val + \" - Hello World!\";" +
		"	}" +
		"}";
	//Add the class to the source task
	sourceTask.createSourceClass("comp.test", "Test" + i, sourceCode);
}

//Create a memory compiler and execute passing the source task
MemoryClassCompiler compiler = new MemoryClassCompiler();
compiler.checkAndCompile(sourceTask);

//Create a custom class loader
SourceClassLoader classLoader = new SourceClassLoader(getClass().getClassLoader());
for(int i = 0; i < 10; i++) {
	//Invoker the classes using reflection
	SourceClass sourceClass = sourceTask.getSourcesClass().get(i);
	Class loadedClass = classLoader.loadSourceClassLoader(sourceClass);
	Object o = loadedClass.newInstance();
	Method m = loadedClass.getDeclaredMethod("sayHello", Integer.class);
	Assert.assertEquals(m.invoke(o, i), i + " - Hello World!");
}
```

### Managing errors

This is a simple example obtaining detailed information when errors are throw by compiler:

```java
//Example of class with problem (returning int but declared as void)
SourceClass sourceClass = new SourceClass();
sourceClass.setPackageName("teste");
sourceClass.setClassName("Teste");
sourceClass.setSourceCode("package teste; public class Teste { public void t() { return 2; } }");
        
MemoryClassCompiler compiler = new MemoryClassCompiler();
try {
	compiler.compile(sourceClass);
} catch (MemoryCompilerException ex) {
	MessageCompiler message = ex.getMessageCompiler();
	Assert.assertNull(sourceClass.getBytecode());
	Assert.assertEquals(message.getMessage(), "Error in compilation of class");
	Assert.assertEquals(message.getStatus(), MessageStatus.FAILED);
	Assert.assertEquals(message.getDiagnostics().get(0).getCode(), "compiler.err.prob.found.req");
	Assert.assertEquals(message.getDiagnostics().get(0).getColumnNumber(), 62);
	Assert.assertEquals(message.getDiagnostics().get(0).getEndPosition(), 62);
	Assert.assertEquals(message.getDiagnostics().get(0).getLineNumber(), 1);
	Assert.assertEquals(message.getDiagnostics().get(0).getMessage(null), "incompatible types: unexpected return value");
	Assert.assertEquals(message.getDiagnostics().get(0).getPosition(), 61);
	Assert.assertEquals(message.getDiagnostics().get(0).getStartPosition(), 61);
	Assert.assertEquals(message.getDiagnostics().get(0).getKind(), Diagnostic.Kind.ERROR);
}
```

### Managing PMD validations

This is a simple example obtaining detailed information when PMD errors are throw by compiler:

```java
SourceClass sourceClass = new SourceClass();
sourceClass.setPackageName("test");
sourceClass.setClassName("Test");
sourceClass.setSourceCode("package test; public class Test { private int t; }");
try {
	new MemoryPMDValidator().check(sourceClass);
} catch(MemoryCompilerException ex) {
	Assert.assertEquals(ex.getMessage(), "PMD_ERROR: PMD Validation failed\nTest : 1 : Avoid unused private fields such as 't'.\n");
}
```