package executor.streetlights;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.GlueBuilder;

import akka.actor.ActorSystem;
import specification.streetlights.withoutmonitor.*;


public class LighterTest {

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
    	BIPGlue bipGlue = new LightGlueBuilder().build();
    	
//    	try {
//			bipGlue.toXML(new FileOutputStream(new File("Tracker-Peer.xml")));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);


    	Light l1 = new Light(1, 0);
    	Light l2 = new Light(2, 0);
//    	Light l3 = new Light(3, 0);
//    	Light l4 = new Light(4, 0);
		
		final BIPActor executor1 = engine.register(l1, "l1", true);
		final BIPActor executor2 = engine.register(l2, "l2", true);
//		final BIPActor executor3 = engine.register(l3, "l3", true);
//		final BIPActor executor4 = engine.register(l4, "l4", true);
    	
		engine.specifyGlue(bipGlue);
		engine.start();
		
		engine.execute();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.stop();
		engineFactory.destroy(engine);
		
    }
    
    public static void main (String[] args) {
    	LighterTest testTP = new LighterTest();
		testTP.initialize();
		testTP.runningTrackerPeer();
		testTP.cleanup();
    }
}
