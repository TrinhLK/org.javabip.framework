package org.javabip.executor;

import org.javabip.api.BIPActor;
import org.javabip.api.BIPEngine;
import org.javabip.api.BIPGlue;
import org.javabip.engine.factory.EngineFactory;
import org.javabip.spec.hanoi.*;

import akka.actor.ActorSystem;

public class HanoiDemo {

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

		int size = 3;

		HanoiOptimalMonitor hanoiMonitor;
		try {
			hanoiMonitor = new HanoiOptimalMonitor(size);
			BIPActor actor1 = engine.register(hanoiMonitor, "hanoiMonitor", false);
			
			LeftHanoiPeg leftHanoiPeg = new LeftHanoiPeg(size);
			BIPActor actor2 = engine.register(leftHanoiPeg, "LeftHanoiPeg", false);

			MiddleHanoiPeg middleHanoiPeg = new MiddleHanoiPeg(size);
			BIPActor actor3 = engine.register(middleHanoiPeg, "MiddleHanoiPeg", false);

			RightHanoiPeg rightHanoiPeg = new RightHanoiPeg(size);
			BIPActor actor4 = engine.register(rightHanoiPeg, "RightHanoiPeg", false);

			engine.specifyGlue(bipGlue);
			engine.start();

			engine.execute();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Finished test, number of transitions " + hanoiMonitor.getNumberOfMoves());
			System.out.flush();
			
			engine.stop();
			engineFactory.destroy(engine);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
    
    public static void main (String[] args) {
        HanoiDemo demo = new HanoiDemo();

		BIPGlue bipGlue4Hanoi = new HanoiOptimalGlueBuilder()
				.build();

        demo.initialize();
        demo.runningHanoiSample(bipGlue4Hanoi);
        demo.cleanup();
    }
}
