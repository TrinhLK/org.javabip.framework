package org.javabip.spec.helloatom;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;

@Ports({ @Port(name = "p", type = PortType.enforceable),
		@Port(name = "init", type = PortType.enforceable)
})
@ComponentType(initial = "LOOP", name = "org.bip.spec.hello.HelloAtomLoop")
public class HelloAtomLoop {

	private int helloId;
	private int active;
	//public int noOfActive = 0;
	
	public HelloAtomLoop(int id) {
		// TODO Auto-generated constructor stub
		helloId = id;
		active = 1;
	}
	
	@Transition(name = "p", source = "LOOP", target = "LOOP")
	public void activate() {
		System.out.println("I'm " + helloId + ", active " + active);
		active++;
		//noOfActive++;
		//active = noOfActive;
	}
	
	@Data(name = "helloId", accessTypePort = AccessType.any)
	public int helloId() {
		return helloId;
	}
	
	public int getActive() {
		return active;
	}
	
	public void setActive(int n) {
		this.active = n;
	}
}
