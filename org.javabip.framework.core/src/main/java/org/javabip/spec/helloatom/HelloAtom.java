package org.javabip.spec.helloatom;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;

@Ports({ @Port(name = "p", type = PortType.enforceable) })
@ComponentType(initial = "START", name = "org.bip.spec.hello.HelloAtom")
public class HelloAtom {

	private int helloId;
	
	public HelloAtom(int id) {
		// TODO Auto-generated constructor stub
		helloId = id;
	}
	
	@Transition(name = "p", source = "START", target = "END")
	public void sayHello() {
		System.out.println("Hello World from " + helloId);
	}
}
