package org.javabip.executor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.spec.monitor.*;

import akka.actor.ActorSystem;

public class MonitorTest {
	private ActorSystem system;
    private EngineFactory engineFactory;
    
    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }

    private void cleanup() {
        system.shutdown();
    }
    
    public void runningMonitorExample() {
    	BIPGlue bipGlue = new MonitorGlueBuilder().build();
    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);
    	
    	Server serverA = new Server(11, 1);
		Server serverB = new Server(12);
		Client client = new Client(21, 1);
		Monitor monitor = new Monitor(serverA, serverB);	
		
		final BIPActor executor1 = engine.register(serverA, "11", true);
		final BIPActor executor2 = engine.register(serverB, "12", true);
		final BIPActor executor3 = engine.register(client, "21", true);
		final BIPActor executor4 = engine.register(monitor, "monitor", true);
    	
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
        MonitorTest demo = new MonitorTest();

        demo.initialize();
        demo.runningMonitorExample();
        demo.cleanup();
    }
}
