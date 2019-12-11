package org.javabip.spec.newclientserver;

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

@Ports({ @Port(name = "downReq", type = PortType.enforceable),
	@Port(name = "downFinish", type = PortType.enforceable),
	@Port(name = "moveReq", type = PortType.enforceable),
	@Port(name = "moveFinish", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.newclientserver.Monitor")
public class Monitor {

	Logger logger = LoggerFactory.getLogger(Monitor.class);
	private int resourceId;
	private int serverId;
	private int clientId;
	private int newServerId;
	
	public Monitor(int _sID, int _nSID) {
		// TODO Auto-generated constructor stub
		serverId = _sID;
		newServerId = _nSID;
	}
	
	public Monitor(int _sID, int _nSID, int _cID) {
		// TODO Auto-generated constructor stub
		serverId = _sID;
		newServerId = _nSID;
		clientId = _cID;
	}
	
	@Transition(name = "downReq", source = "0", target = "1", guard = "canDownload")
	public void downRequest(@Data(name = "clientId") Integer cId) {
		clientId = cId;
		logger.debug("Monitor: Client{" + clientId + "} is DOWNLOADING resource{" + resourceId + "} from server{" + serverId + "}.\n");
		System.out.println("Monitor: Client{" + clientId + "}: is DOWNLOADING resource{" + resourceId + "} from server{" + serverId + "}");
	}
	
	@Transition(name = "downFinish", source = "1", target = "0")
	public void downFinish(@Data(name = "clientId") Integer cId) {
		clientId = cId;
		logger.debug("Monitor: DOWNLOADING is finished.\n");
		System.out.println("Monitor: DOWNLOADING is finished");
	}
	
	@Transition(name = "moveReq", source = "0", target = "2")
	public void moveRequest() {
		logger.debug("Monitor: Server{" + serverId + "}: is MOVING resource{" + resourceId + "} to server{" + newServerId + "}.\n");
		System.out.println("Monitor: Server{" + serverId + "}: is MOVING resource{" + resourceId + "} to server{" + newServerId + "}");
	}
	
	@Transition(name = "moveFinish", source = "2", target = "0")
	public void moveFinish() {
		logger.debug("Monitor: MOVING is finished.\n");
		System.out.println("Monitor:  MOVING is finished");
		serverId = newServerId;
	}
	
	@Guard(name = "canDownload")
	public boolean canDownload(@Data(name = "clientResourceId") Integer crID, @Data(name = "rID") Integer srID) {
		resourceId = srID;
		return (crID == srID);
	}
	
	@Data(name = "serverId", accessTypePort = AccessType.any)
	public int getServerId() {
		return serverId;
	}
	
	@Data(name = "newServerId", accessTypePort = AccessType.any)
	public int getNewServerId() {
		return newServerId;
	}
	
	@Data(name = "resourceId", accessTypePort = AccessType.any)
	public int getResourceId() {
		return resourceId;
	}
}
