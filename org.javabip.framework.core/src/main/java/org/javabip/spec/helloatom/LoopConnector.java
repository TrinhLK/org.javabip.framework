package org.javabip.spec.helloatom;

import java.util.ArrayList;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "12", type = PortType.enforceable)})
@ComponentType(initial = "state-1st", name = "org.bip.spec.hello.LoopConnector")
public class LoopConnector {

	Logger logger = LoggerFactory.getLogger(LoopConnector.class);

    private ArrayList<HelloAtomLoop> listAtom;
    int numberOfActive = 0;

    public LoopConnector(ArrayList<HelloAtomLoop> _listAtom) {
    	listAtom = _listAtom;
    }

    public int getNumberOfMoves() {
        return numberOfActive;
        
    }
    
//    @ExecutableBehaviour
//    public BehaviourBuilder initializeBehavior() throws NoSuchMethodException {
//    	
//    	BehaviourBuilder behaviourBuilder = new BehaviourBuilder(this);
//    	behaviourBuilder.setComponentType( this.getClass().getCanonicalName() );
//    	
//    	behaviourBuilder.addState("state-1st");
//        behaviourBuilder.addState("state-2nd");
//        
//        behaviourBuilder.addPort("12", PortType.enforceable, this.getClass());
//        
//        behaviourBuilder.setInitialState("state-1st");
//        behaviourBuilder.addTransitionAndStates("12", "state-1st", "state-2nd", "", LoopConnector.class.getMethod("plus12"));
//        
//    	return behaviourBuilder;
//    }
    
    @Transition(name = "12", source = "state-1st", target = "state-2nd")
    public void plus12(@Data(name = "helloId") Integer id1, @Data(name = "helloId") Integer id2) {
    	numberOfActive = checkExist(id1).getActive() + checkExist(id2).getActive();
        logger.debug("add 1st.active and 2nd.active");
        checkExist(id1).setActive(numberOfActive);
        checkExist(id2).setActive(numberOfActive);
    }
    
    
    public void plus12_old(HelloAtomLoop a1, HelloAtomLoop a2) {
    	numberOfActive = a1.getActive() + a2.getActive();
        logger.debug("add 1st.active and 2nd.active");
        a1.setActive(numberOfActive);
        a2.setActive(numberOfActive);
    }
    
    private HelloAtomLoop checkExist(int id) {
    	for (int i=0 ; i<listAtom.size() ; i++) {
    		if (listAtom.get(i).helloId() == id)
    			return listAtom.get(i);
    	}
    	return null;
    }
}
