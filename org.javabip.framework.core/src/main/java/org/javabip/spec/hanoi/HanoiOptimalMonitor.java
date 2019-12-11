/*
 * Copyright (c) 2013 Crossing-Tech TM Switzerland. All right reserved.
 * Copyright (c) 2013, RiSD Laboratory, EPFL, Switzerland.
 *
 * Author: Simon Bliudze, Alina Zolotukhina, Anastasia Mavridou, and Radoslaw Szymanek
 * Date: 15/07/2013
 */
package org.javabip.spec.hanoi;

/*

For an even number of disks:

    make the legal move between pegs A and B
    make the legal move between pegs A and C
    make the legal move between pegs B and C
    repeat until complete

For an odd number of disks:

    make the legal move between pegs A and C
    make the legal move between pegs A and B
    make the legal move between pegs B and C
    repeat until complete

In each case, a total of 2ⁿ-1 moves are made.

*/

import org.javabip.annotations.ExecutableBehaviour;
import org.javabip.api.PortType;
import org.javabip.executor.BehaviourBuilder;
import org.javabip.spec.SwitchableRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanoiOptimalMonitor {

    //Logger logger = LoggerFactory.getLogger(SwitchableRoute.class);
    BehaviourBuilder behaviourBuilder;
    
    private int size;

    int numberOfMoves = 0;

    public HanoiOptimalMonitor(int size) {
        this.size = size;

    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    // LeftHanoiPeg is denoted as A.
    // MiddleHanoiPeg is denoted as B.
    // RightHanoiPeg is denoted as C.
    
    /*
    public void initialize() {
    	behaviourBuilder = new BehaviourBuilder(this);

    	behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );

        behaviourBuilder.addState("state-AB");
        behaviourBuilder.addState("state-AC");
        behaviourBuilder.addState("state-BC");
        behaviourBuilder.addState("state-BA");
        behaviourBuilder.addState("state-CA");
        behaviourBuilder.addState("state-CB");

        behaviourBuilder.addPort("ab", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("ac", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("bc", PortType.enforceable, this.getClass());
        
        behaviourBuilder.addPort("ba", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("ca", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("cb", PortType.enforceable, this.getClass());
        
        behaviourBuilder.setInitialState("state-AC");
    }
    
    public void TowerOfHanoi(int size, String a, String b, String c) throws NoSuchMethodException, SecurityException {
    	
    	if (size == 1) {
    		//System.out.println("here");
    		// behaviourBuilder.setInitialState("state-"+(a+c).toUpperCase());
            // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
    		behaviourBuilder.addTransitionAndStates(a+c, "state-"+(a+c).toUpperCase(), "state-"+(a+b).toUpperCase(), "", 
    				HanoiOptimalMonitor.class.getMethod("move"+(a+c).toUpperCase()));
    		System.out.println(a.toUpperCase() + " ---> " + c.toUpperCase());
            return;
    	}
    	TowerOfHanoi(size-1, a, c, b);
    	TowerOfHanoi(1, a, b, c);
    	TowerOfHanoi(size-1, b, a, c);
    }
    
    @ExecutableBehaviour
    public BehaviourBuilder running() {
    	initialize();
    	try {
        	
			TowerOfHanoi(size, "a", "b", "c");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return behaviourBuilder;
    }
    */
    @ExecutableBehaviour
    public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {

    	BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);

    	behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );

        behaviourBuilder.addState("state-AB");
        behaviourBuilder.addState("state-AC");
        behaviourBuilder.addState("state-BC");
        

        behaviourBuilder.addPort("ab", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("ac", PortType.enforceable, this.getClass());

        behaviourBuilder.addPort("bc", PortType.enforceable, this.getClass());


        if (size % 2 == 0) {
            
//            For an even number of disks:
//
//            make the legal move between pegs A and B
//            make the legal move between pegs A and C
//            make the legal move between pegs B and C
//            repeat until complete
            

        	behaviourBuilder.setInitialState("state-AB");

           // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveAB"));

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAC"));


            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
            behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveBC"));

        }
        else {

            
//             For an odd number of disks:
//
//             make the legal move between pegs A and C
//             make the legal move between pegs A and B
//             make the legal move between pegs B and C
//             repeat until complete
            

        	behaviourBuilder.setInitialState("state-AC");

            // ExecutorTransition=(name = off, source = on -> target = wait, guard = , method = public void org.bip.spec.SwitchableRoute.stopRoute() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("ac", "state-AC", "state-AB", "", HanoiOptimalMonitor.class.getMethod("moveAC"));

            // ExecutorTransition=(name = on, source = off -> target = on, guard = , method = public void org.bip.spec.SwitchableRoute.startRoute() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("ab", "state-AB", "state-BC", "", HanoiOptimalMonitor.class.getMethod("moveAB"));

            // ExecutorTransition=(name = end, source = wait -> target = done, guard = !isFinished, method = public void org.bip.spec.SwitchableRoute.spontaneousEnd() throws java.lang.Exception),
        	behaviourBuilder.addTransitionAndStates("bc", "state-BC", "state-AC", "", HanoiOptimalMonitor.class.getMethod("moveBC"));


        }

        return behaviourBuilder;
    }
    
    
    public void moveAB() {
        numberOfMoves++;
        //logger.debug("Left to Middle (AB) move is being triggered.");
        System.out.println("A --> B");
    }
    
    public void moveBA() {
        numberOfMoves++;
        //logger.debug("Left to Middle (AB) move is being triggered.");
        System.out.println("B --> A");
    }

    public void moveAC() {
        numberOfMoves++;
        //logger.debug("Left to Right (AC) move is being triggered.");
        System.out.println("A --> C");
    }
    
    public void moveCA() {
        numberOfMoves++;
        //logger.debug("Left to Right (AC) move is being triggered.");
        System.out.println("C --> A");
    }

    public void moveBC() {
        numberOfMoves++;
        //logger.debug("Middle to Right (BC) move is being triggered.");
        System.out.println("B --> C");
    }
    
    public void moveCB() {
        numberOfMoves++;
        //logger.debug("Middle to Right (BC) move is being triggered.");
        System.out.println("C --> B");
    }

}

