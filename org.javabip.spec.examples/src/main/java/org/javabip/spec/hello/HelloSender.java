package org.javabip.spec.hello;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "s", type = PortType.enforceable) })
@ComponentType(initial = "START", name = "org.bip.spec.hello.HelloSender")
public class HelloSender {

	Logger logger = LoggerFactory.getLogger(HelloSender.class);
	private int sendId;
	
	public HelloSender(int id) {
		sendId = id;
	}
	
	@Transition(name = "s", source = "START", target = "END")
	public void sending() {
		System.out.println("I'm " + sendId + ", sending Hello World ... ");
	}
	
	@Data(name = "sendId", accessTypePort = AccessType.any)
	public int sendId() {
		return sendId;
	}
}
