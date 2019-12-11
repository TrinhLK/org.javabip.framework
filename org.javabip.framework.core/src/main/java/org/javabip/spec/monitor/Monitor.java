package org.javabip.spec.monitor;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.DataOut.AccessType;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "downReq", type = PortType.enforceable),
	@Port(name = "downFinish", type = PortType.enforceable),
	@Port(name = "moveReq", type = PortType.enforceable),
	@Port(name = "moveFinish", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.monitor.Monitor")
public class Monitor {

	Logger logger = LoggerFactory.getLogger(Monitor.class);
	private int resourceId;
	private Server serverId;
	private int clientId;
	private Server newServerId;
	
	public Monitor(Server _sID, Server _nSID) {
		// TODO Auto-generated constructor stub
		serverId = _sID;
		newServerId = _nSID;
	}
	
	public Monitor(Server _sID, Server _nSID, int _cID) {
		// TODO Auto-generated constructor stub
		serverId = _sID;
		newServerId = _nSID;
		clientId = _cID;
	}
	
	@Transition(name = "downReq", source = "0", target = "1", guard = "canDownload")
	public void downRequest() {
		
		logger.info("Monitor: Client{" + clientId + "} is DOWNLOADING resource{" + resourceId + "} from server{" + serverId.getServerId() + "}.\n");
		//System.out.println("Monitor: Client{" + clientId + "}: is DOWNLOADING resource{" + resourceId + "} from server{" + serverId.getServerId() + "}");
	}
	
	@Transition(name = "downFinish", source = "1", target = "0")
	public void downFinish() {
		logger.info("Monitor: DOWNLOADING is finished.\n");
		//System.out.println("Monitor: DOWNLOADING is finished");
	}
	
	@Transition(name = "moveReq", source = "0", target = "2", guard = "canMove")
	public void moveRequest() {
		//resourceId = srID;
		logger.info("Monitor: Server{" + serverId.getServerId() + "}: is MOVING resource{" + resourceId + "} to server{" + newServerId.getServerId() + "}.\n");
		//System.out.println("Monitor: Server{" + serverId.getServerId() + "}: is MOVING resource{" + resourceId + "} to server{" + newServerId.getServerId() + "}");
	}
	
	@Transition(name = "moveFinish", source = "2", target = "0")
	public void moveFinish() {
		logger.info("Monitor: MOVING is finished.\n");
		//System.out.println("Monitor:  MOVING is finished");
		serverId = newServerId;
	}
	
	@Guard(name = "canDownload")
	public boolean canDownload(@Data(name = "clientResourceId") Integer crID, @Data(name = "clientId") Integer cId) {
		resourceId = crID;
		clientId = cId;
//		if (crID == serverId.getResourceId()) {
//			System.out.println("Monitor Guard: you can down");
//		}else {
//			System.out.println("Monitor Guard: you CANNOT down");
//		}
		//return (crID == srID);
		return (crID == serverId.getResourceId());
	}
	
	@Guard(name = "canMove")
	public boolean canMove() {
//		if (serverId.getResourceId() >= 0 && serverId != newServerId) {
//			System.out.println("Monitor Guard: you can move from server{" + serverId.getServerId() + "} to {" + newServerId.getServerId() + "}.");
//		}else {
//			System.out.println("Monitor Guard: you CANNOT move from server{" + serverId.getServerId() + "} to {" + newServerId.getServerId() + "}.");
//		}
		
		return (serverId.getResourceId() >= 0 && serverId != newServerId);
	}
	@Data(name = "serverId", accessTypePort = AccessType.any)
	public int getServerId() {
		return serverId.getServerId();
	}
	
	@Data(name = "newServerId", accessTypePort = AccessType.any)
	public int getNewServerId() {
		return newServerId.getServerId();
	}
	
	@Data(name = "resourceId", accessTypePort = AccessType.any)
	public int getResourceId() {
		return resourceId;
	}
}
