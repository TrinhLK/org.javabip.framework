package org.javabip.spec.helloatom;

import org.javabip.glue.GlueBuilder;
import org.javabip.spec.trackpeer.Peer;
import org.javabip.spec.trackpeer.Tracker;


public class HelloAtomGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
//		port(HelloAtomLoop.class, "LOOP").accepts(HelloAtomLoop.class, "LOOP");
//		port(HelloAtomLoop.class, "LOOP").requires(HelloAtomLoop.class, "LOOP");
//		port(HelloAtomLoop.class, "LOOP").requires(LoopConnector.class, "12");
		port(HelloAtomLoop.class, "p").requires(LoopConnector.class, "12");
		port(HelloAtomLoop.class, "p").accepts(LoopConnector.class, "12");
		port(LoopConnector.class, "12").requires(HelloAtomLoop.class, "p");
		port(LoopConnector.class, "12").accepts(HelloAtomLoop.class, "p");
		
		data(HelloAtomLoop.class, "helloId").to(LoopConnector.class, "helloId");
	}

}
