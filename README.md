# JavaBIP Framework
###### org.javabip.framework

This project re-organized the JavaBIP projects of [Simon Bliudze](https://github.com/sbliudze "Named link title").

In the old version, in order to implement an example, users need to:
1. Install & Compile ___JavaBDD___ library
2. Install & Compile ___JavaBIP core___ project
3. Install & Compile ___JavaBIP engine___ project
4. Write Specification and Configuration Files in javabip-core
5. Create a project to write test files.

Doing those steps is time-consumming as well as may contains some potential conflicts between those projects. Thus, I re-organized them as follows:
1. Move all modules from JavaBIP core project and JavaBIP engine project into JavaBIP Framework project.
2. Create a module named org.javabip.framework.core. In this module's pom files, I
	1. call the all modules as dependencies
	2. use ___Maven Shade___ plugins to generate a library named ___JavaBIP-Framework.jar___, which can be used in other projects (including maven and regular Java projects).

You can find the .jar file at the folder ___org.javabip.framework.core/target/___


This project also provides some runnable examples in the module org.javabip.framework.core:
* All the specifications are defined in packages: __org.javabip.spec.*__;
* The executors to run demos are defined in package __org.javabip.executor__
	* HanoiDemo: for hanoiTower example
	* MonitorTest: for test the resource access management between Clients and Servers
	* PetriNetTest: for the petrinet
	* SpiralsDemo: for the Camel Route
	* TestPubSub: for publisher-subscriber system
	* TrackPeerTest: for the boardcasting between Tracker and Peers

For those who are newbies, you should follow Track-Peer, SpiralsDemo and PetriNet first.