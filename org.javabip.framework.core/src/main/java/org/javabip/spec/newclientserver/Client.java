package org.javabip.spec.newclientserver;


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


@Ports({@Port(name = "download", type = PortType.enforceable),
	@Port(name = "releash", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.newclientserver.Client")

public class Client {

	Logger logger = LoggerFactory.getLogger(Client.class);
	private int clientId;
	private int serverId;
	private int resourceId;
	
	public Client(int _cId) {
		// TODO Auto-generated constructor stub
		clientId = _cId;
		resourceId = -1;
	}
	
	public Client(int _cId, int _rID) {
		// TODO Auto-generated constructor stub
		clientId = _cId;
		resourceId = _rID;
	}
	
	@Transition(name = "download", source = "0", target = "1", guard = "canConnect")
	public void download() {
		logger.debug("Client{" + clientId + "}: is REQUESTING to download resource{" + resourceId + "} from server{" + serverId + "}.\n");
		System.out.println("Client{" + clientId + "}: is requesting to download resource{" + resourceId + "} from server{" + serverId + "}");
	}
	
	@Transition(name = "releash", source = "1", target = "0")
	public void releash() {
		logger.debug("Client{" + clientId + "}: RELEASHES resource{" + resourceId + "} from server{" + serverId + "}.\n");
		System.out.println("Client{" + clientId + "}: RELEASHES resource{" + resourceId + "} from server{" + serverId + "}");
	}
	
	@Data(name = "clientId", accessTypePort = AccessType.any)
	public int clientID() {
		return clientId;
	}
	
	@Data(name = "clientResourceId", accessTypePort = AccessType.any)
	public int getResourceId() {
		return resourceId;
	}
	
	@Guard(name = "canConnect")
	public boolean canConnect(@Data(name = "serverId") Integer sId) {
		serverId = sId;
		return (serverId >= 0);
	}

}
