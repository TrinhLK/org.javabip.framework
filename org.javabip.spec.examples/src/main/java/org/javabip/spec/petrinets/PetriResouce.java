package org.javabip.spec.petrinets;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "get1", type = PortType.enforceable),
	@Port(name = "get2", type = PortType.enforceable),
	@Port(name = "free1", type = PortType.enforceable),
	@Port(name = "free2", type = PortType.enforceable)})
@ComponentType(initial = "r", name = "org.bip.spec.petrinets.AtomPetri")
public class PetriResouce {

	@Transition(name = "get1", source = "r", target = "u1")
	public void get1() {
		//System.out.println("R: 1 is taking me");
	}
	
	@Transition(name = "get2", source = "r", target = "u2")
	public void get2() {
		//System.out.println("R: 2 is taking me");
	}
	
	@Transition(name = "free1", source = "u1", target = "r")
	public void free1() {
		//System.out.println("R: now I'm free from 1");
	}
	
	@Transition(name = "free2", source = "u2", target = "r")
	public void free2() {
		//System.out.println("R: now I'm free from 2");
	}
}
