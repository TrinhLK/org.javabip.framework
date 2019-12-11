package org.javabip.spec.petrinets;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "sync", type = PortType.enforceable),
	@Port(name = "get", type = PortType.enforceable),
	@Port(name = "free", type = PortType.enforceable)})
@ComponentType(initial = "g", name = "org.bip.spec.petrinets.AtomPetri")
public abstract class AtomPetri {

	protected int id;
	
	public AtomPetri(int _id) {
		// TODO Auto-generated constructor stub
		id = _id;
	}
	
	@Transition(name = "get", source = "g", target = "u")
	public abstract void getting();
	
	@Transition(name = "free", source = "u", target = "s")
	public abstract void freeing();
	
	@Transition(name = "sync", source = "s", target = "g")
	public abstract void sync();
	
	
}
