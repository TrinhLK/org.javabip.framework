package org.javabip.executor;

import java.io.*;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.glue.GlueBuilder;
import org.javabip.spec.trackpeer.Peer;
import org.javabip.spec.trackpeer.TrackPeerBuilder;
import org.javabip.spec.trackpeer.Tracker;

import akka.actor.ActorSystem;

public class TrackPeerTest {

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
    	BIPGlue bipGlue = new TrackPeerBuilder().build();
    	
    	try {
			bipGlue.toXML(new FileOutputStream(new File("Tracker-Peer.xml")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	Tracker tracker1 = new Tracker(1);
		Peer peer1a = new Peer(11);
		Peer peer1b = new Peer(12);
		Tracker tracker2 = new Tracker(2);
		Peer peer2a = new Peer(21);
		Peer peer2b = new Peer(22);

		final BIPActor executor1 = engine.register(tracker1, "1", true);
		final BIPActor executor1a = engine.register( peer1a, "11", true);
		final BIPActor executor1b = engine.register( peer1b, "12", true);
		final BIPActor executor2 = engine.register( tracker2, "2", true);
		final BIPActor executor2a = engine.register( peer2a, "21", true);
		final BIPActor executor2b = engine.register( peer2b, "22", true);
		
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
    	TrackPeerTest testTP = new TrackPeerTest();
		testTP.initialize();
		testTP.runningTrackerPeer();
		testTP.cleanup();
    }
}
