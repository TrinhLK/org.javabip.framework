package org.javabip.spec.newclientserver;

import java.util.HashMap;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.BIPActor;

@Ports({ @Port(name = "handleCommand", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.untyped.CommandHandler")
public class RequestHandler {

	private BIPActor monitor;
	
	public RequestHandler(BIPActor _monitor) {
		this.monitor = _monitor;
	}
	
	@Transition(name = "handleRequest", source = "0", target = "0")
	public void handleRequest(@Data(name = "request") Request request) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("request", request);
		monitor.inform("executeRequest", data);
	}
}
