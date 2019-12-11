package org.javabip.spec.monitor;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "run", type = PortType.enforceable),
	@Port(name = "move", type = PortType.enforceable),
	@Port(name = "close", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.monitor.Server")
public class Server {

	Logger logger = LoggerFactory.getLogger(Server.class);
	private int sID;
	private int rID;
	
	public Server(int _sId) {
		// TODO Auto-generated constructor stub
		sID = _sId;
		rID = -1;
	}
	
	public Server(int _sId, int _rId) {
		// TODO Auto-generated constructor stub
		sID = _sId;
		rID = _rId;
	}
	
	@Transition(name = "run", source = "0", target = "0", guard = "canRun")
	public void run() {
		logger.info("Server{" + sID + "}: is RUNNING.\n");
		//System.out.println("Server{" + sID + "}: is RUNNING.\n");
	}
	
	@Transition(name = "move", source = "0", target = "1", guard = "canMove")
	public void move(@Data(name = "newServerId") Integer newSId) {
		logger.info("Server{" + sID + "}: is REQUESTING to move resource{" + rID + "} to server{" + newSId + "}.\n");
		//System.out.println("Server{" + sID + "}: is REQUESTING to move resource{" + rID + "} to server{" + newSId + "}");
	}
	
	@Transition(name = "close", source = "1", target = "0")
	public void close() {
		logger.info("Server{" + sID + "}: is CLOSED.\n");
		//System.out.println("Server{" + sID + "}: is CLOSED.\n");
		rID = -1;
		sID = -1;
	}
	
	@Data(name = "rID", accessTypePort = AccessType.any)
	public int getResourceId() {
		return rID;
	}
	
	@Data(name = "sID", accessTypePort = AccessType.any)
	public int getServerId() {
		return sID;
	}
	
	@Guard(name = "canRun")
	public boolean canRun(@Data(name = "resourceId") Integer resourceId) {
		return (sID >= 0 && resourceId == rID && rID >= 0);
	}
	
	@Guard(name = "canMove")
	public boolean canMove(@Data(name = "newServerId") Integer newSId) {
		//if (sID != newSId && newSId >= 0)
		return (sID != newSId && newSId >= 0 && rID >= 0);
	}
}
