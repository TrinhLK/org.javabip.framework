package executor.streetlights;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.GlueBuilder;

import akka.actor.ActorSystem;
import specification.streetlights.string.*;


public class StreetLightTest4 {
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
    	BIPGlue bipGlue = new StreetLightGlueBuilder().build();
    	
//    	try {
//			bipGlue.toXML(new FileOutputStream(new File("Tracker-Peer.xml")));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	LightConnector l1 = new LightConnector("1", "off");
    	LightConnector l2 = new LightConnector("2", "off");
    	Monitor monitor = new Monitor();	
    	
		final BIPActor executor1 = engine.register(l1, "l1", true);
		final BIPActor executor2 = engine.register(l2, "l2", true);
		final BIPActor executor0 = engine.register(monitor, "monitor", true);
    	
		engine.specifyGlue(bipGlue);
		engine.start();
		
		engine.execute();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		engine.stop();
		engineFactory.destroy(engine);
		
    }
    
    public static void main (String[] args) {
    	StreetLightTest4 testTP = new StreetLightTest4();
		testTP.initialize();
		testTP.runningTrackerPeer();
		testTP.cleanup();
    }
}
