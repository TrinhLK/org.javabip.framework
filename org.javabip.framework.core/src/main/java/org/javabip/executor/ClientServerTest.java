package org.javabip.executor;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.spec.newclientserver.*;

import akka.actor.ActorSystem;

public class ClientServerTest {
	private ActorSystem system;
    private EngineFactory engineFactory;

    private void initialize() {
        system = ActorSystem.create("MySystem");
        engineFactory = new EngineFactory(system);
    }

    private void cleanup() {
        system.shutdown();
    }
    
    public void runningHanoiSample(BIPGlue bipGlue) {

    	BIPEngine engine = engineFactory.create("myEngine", bipGlue);

		//HanoiOptimalMonitor hanoiMonitor;
		try {
			
			Client client = new Client(11, 1);
			Server serverA = new ServerA(21, 1);
			Server serverB = new ServerB(22);
			Monitor monitor = new Monitor(21,22);
			//NewServer nServer = new NewServer(22);
			
					
			BIPActor actor1 = engine.register(client, "client", true);
			BIPActor actor2 = engine.register(serverA, "serverA", true);
			BIPActor actor3 = engine.register(serverB, "ServerB", true);
			BIPActor actor4 = engine.register(monitor, "monitor", true);
			
			engine.specifyGlue(bipGlue);
			engine.start();

			engine.execute();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//System.out.println("Finished test, number of transitions " + petriAtom.getNumberOfMoves());
			System.out.flush();
			
			engine.stop();
			engineFactory.destroy(engine);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
    public static void main (String[] args) {
        ClientServerTest demo = new ClientServerTest();

		BIPGlue bipGlue = new ClientServerGlueBuilder()
				.build();

        demo.initialize();
        demo.runningHanoiSample(bipGlue);
        demo.cleanup();
    }
}
