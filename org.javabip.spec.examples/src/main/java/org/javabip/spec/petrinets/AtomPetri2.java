package org.javabip.spec.petrinets;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "sync", type = PortType.enforceable),
	@Port(name = "get", type = PortType.enforceable),
	@Port(name = "free", type = PortType.enforceable)})
@ComponentType(initial = "g", name = "org.bip.spec.petrinets.AtomPetri2")
public class AtomPetri2{

	public AtomPetri2() {
		// TODO Auto-generated constructor stub
	}

	@Transition(name = "get", source = "g", target = "u")
	public void getting() {
		// TODO Auto-generated method stub
		System.out.println(2 + ": get resource");
	}

	@Transition(name = "free", source = "u", target = "s")
	public void freeing() {
		// TODO Auto-generated method stub
		System.out.println(2 + ": free resource");
		
	}

	@Transition(name = "sync", source = "s", target = "g")
	public void sync() {
		// TODO Auto-generated method stub
		System.out.println("1 & 2: synchronize");
	}
}
