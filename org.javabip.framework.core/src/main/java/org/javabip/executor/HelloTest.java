package org.javabip.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.GlueBuilder;
import org.javabip.spec.hello.*;

import akka.actor.ActorSystem;

public class HelloTest {
	private ActorSystem system;
    private EngineFactory engineFactory;
    
    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }
    
    private void cleanup() {
        system.shutdown();
    }
    
    private BIPGlue createGlue(String bipGlueFilename) {
		BIPGlue bipGlue = null;

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(bipGlueFilename);

			bipGlue = GlueBuilder.fromXML(inputStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return bipGlue;
	}
    
    public void runningTrackerPeer() {
    	//BIPGlue bipGlue = createGlue("src/test/resources/trackerPeerGlue.xml");
    	BIPGlue bipGlue = new HelloGlueBuilder().build();
    	
//    	try {
//			bipGlue.toXML(new FileOutputStream(new File("Tracker-Peer.xml")));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	//Component
//		HelloAtom hello1 = new HelloAtom(1);
//		HelloAtom hello2 = new HelloAtom(2);
//		HelloAtom hello3 = new HelloAtom(3);
//		
//		//Connector
//		final BIPActor executor1 = engine.register(hello1, "1", true);
//		final BIPActor executor2 = engine.register(hello2, "2", true);
//		final BIPActor executor3 = engine.register(hello3, "3", true);
		
		HelloSender helloSender = new HelloSender(0);
		HelloReceiver helloReceiver1 = new HelloReceiver(1);
		HelloReceiver helloReceiver2 = new HelloReceiver(2);
		HelloReceiver helloReceiver3 = new HelloReceiver(3);

		final BIPActor executor = engine.register(helloSender, "0", true);
		final BIPActor executor1 = engine.register(helloReceiver1, "1", true);
		final BIPActor executor2 = engine.register(helloReceiver2, "2", true);
		final BIPActor executor3 = engine.register(helloReceiver3, "3", true);
		
		engine.specifyGlue(bipGlue);
		engine.start();
		
		engine.execute();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.stop();
		engineFactory.destroy(engine);
		
		//assertTrue("Hello 1 has not made any transitions", hello1.noOfTransitions > 0);
		
    }
    
    public static void main (String[] args) {
    	HelloTest testTP = new HelloTest();
		testTP.initialize();
		testTP.runningTrackerPeer();
		testTP.cleanup();
    }
}
