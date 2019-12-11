package org.javabip.spec.trackpeer;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.DataOut.AccessType;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "log", type = PortType.enforceable),
		@Port(name = "broadcast", type = PortType.enforceable) })
@ComponentType(initial = "zero", name = "org.bip.spec.trackpeer.Tracker")
public class Tracker {

	public int noOfTransitions = 0;
	
	Logger logger = LoggerFactory.getLogger(Tracker.class);
	private int trackerId;

	public Tracker(int id) {
		trackerId = id;
	}

	@Transition(name = "log", source = "zero", target = "zero")
	public void logging() {
		System.out.println("Peer has updates his status");
		noOfTransitions++;
	}

	@Transition(name = "broadcast", source = "zero", target = "zero")
	public void broadcasting() {
		noOfTransitions++;		
		System.out.println("Broadcasting " + trackerId);
	}

	@Data(name = "trackerId", accessTypePort = AccessType.any)
	public int trackerId() {
		return trackerId;
	}
	
}
