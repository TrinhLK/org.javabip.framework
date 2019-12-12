# JavaBIP Framework
######org.javabip.framework

This project re-organized the JavaBIP projects of [Simon Bliudze](https://github.com/sbliudze "Named link title").

In the old version, in order to implement an example, users need to:
1. Install & Compile ___BDD___ library
2. Install & Compile ___JavaBIP core___ project
3. Install & Compile ___JavaBIP engine___ project
4. Write Specification and Configuration Files in javabip-core
5. Create a project to write test files.

Doing those steps is time-consumming. Thus, I re-organized it as follows:
1. Move all modules from JavaBIP core project and JavaBIP engine project into JavaBIP Framework project.
2. Create a module named org.javabip.framework.core. In this module's pom files, I
	1. call the all modules as dependencies
	2. use ___Maven Shade___ plugins to generate a library named ___JavaBIP-Framework.jar___, which can be used in other project (including maven and regular Java projects).

You can find the .jar file at the folder ___org.javabip.framework.core/target/___


This project also provides some runnable examples in the module org.javabip.framework.core:
* All the specifications are defined in packages: __org.javabip.spec.*__;
* The executors to run demos are defined in package __org.javabip.executor__