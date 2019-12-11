package org.javabip.spec.helloatom;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "plus", type = PortType.enforceable),
		@Port(name = "r1", type = PortType.enforceable),
		@Port(name = "r2", type = PortType.enforceable),
})
@ComponentType(initial = "zero", name = "org.bip.spec.helloatoom.Plus")
public class Plus {

	private int plusId;
	private int atomId1;
	private HelloAtomLoop atom1;
	private int atomId2;
	private HelloAtomLoop atom2;
	public int numberOfActive = 0;
	
	public Plus(int id) {
		// TODO Auto-generated constructor stub
		plusId = id;
		this.atomId1 = -1;
		this.atomId2 = -1;
	}
	
	public Plus(HelloAtomLoop a1, HelloAtomLoop a2) {
		this.atom1 = a1;
		this.atom2 = a2;
		r1R2(atom1.helloId(), atom2.helloId());
	}
	
	@Transition(name = "r1", source = "zero", target = "one")
	public void onR1(@Data(name = "helloId") Integer id) {
		this.atomId1 = id;
		if (atom1.helloId() == id) {
			numberOfActive = atom1.getActive();
			atom1.setActive(numberOfActive);
		}
		System.out.println("number of active = r1.d");
	}
	
	@Transition(name = "r2", source = "zero", target = "two")
	public void onR2(@Data(name = "helloId") Integer id) {
		this.atomId2 = id;
		if (atom2.helloId() == id) {
			numberOfActive = atom2.getActive();
			atom2.setActive(numberOfActive);
		}
		System.out.println("number of active = r2.d");
	}
	
	@Transition(name = "plus", source = "zero", target = "three")
	public void r1R2(@Data(name = "helloId") Integer id1, @Data(name = "helloId") Integer id2) {
		this.atomId1 = id1;
		this.atomId2 = id2;
		if (atom1.helloId() == id1 && atom2.helloId() == id2) {
			numberOfActive = atom1.getActive() + atom2.getActive();
			atom1.setActive(numberOfActive);
			atom2.setActive(numberOfActive);
		}
		System.out.println(numberOfActive);
		System.out.println("number of active = r1.d + r2.d");
	}
}
