package org.javabip.spec.clientserver;

import java.util.ArrayList;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.DataOut.AccessType;
import org.javabip.spec.trackpeer.Peer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.javabip.api.PortType;

@Ports({ @Port(name = "request", type = PortType.enforceable),
	@Port(name = "download", type = PortType.enforceable),
	@Port(name = "releash", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.clientserver.Client")
public class Client {

	Logger logger = LoggerFactory.getLogger(Peer.class);
	private int clientId;
	private int serverId;
	private int resourceId;
	private boolean takingResource;
	
	public Client(int _id) {
		// TODO Auto-generated constructor stub
		clientId = _id;
		this.serverId = -1;
		this.resourceId = -1;
		//System.out.println("Create Client: " + clientId);
		//this.myRs = null;
		//listResource = new ArrayList<Integer>();
	}
	
	public Client(int _id, int _rId) {
		// TODO Auto-generated constructor stub
		clientId = _id;
		this.serverId = -1;
		this.resourceId = _rId;
		System.out.println("Create Client: " + clientId);
		this.takingResource = false;
		//listResource = new ArrayList<Integer>();
	}
	
	@Transition(name = "request", source = "0", target = "1")
	public void requesting(@Data(name = "serverId") Integer sid, @Data(name = "newServer") Integer nsid) {
		//this.myRs = resource;
		//this.serverId = sid;
		if (sid >= 0) {
			this.serverId = sid;
		}else {
			this.serverId = nsid;
		}
		System.out.println("Client {" + clientId + "}: is REQUESTING {" + serverId + "} to access resource {" + resourceId + "}.");
	}
	
	@Transition(name = "download", source = "1", target = "1", guard = "canAccess")
	public void downloading() {
		System.out.println("Client {" + clientId + "}: is DOWNLOADING resource {" + resourceId + "} from Server{" + serverId + "}");
		takingResource = true;
	}
	@Transition(name = "releash", source = "1", target = "0", guard = "canReleash")
	public void releashing() {
		System.out.println("Client {" + clientId + "}: is RELEASING resource {" + resourceId + "}.\nLogged out");
		this.takingResource = false;
	}
	
	@Data(name = "resourceIdToUse", accessTypePort = AccessType.any)
	public int getResourceId() {
		return resourceId;
	}
	
	@Data(name = "takingRs", accessTypePort = AccessType.any)
	public boolean isTakingResource() {
		return takingResource;
	}
	
	@Guard(name = "canReleash")
	public boolean canReleash() {
		return takingResource;
	}
	@Guard(name = "canAccess")
	public boolean canAccess(@Data(name = "serverId") Integer sid, @Data(name = "accessed") Boolean canAccess) {
		serverId = sid;
		if (serverId >= 0 && canAccess == true) {
			return true;
		}
		return false;
	}
	


//	@Guard(name = "canAccess")
//	public boolean canAccess(@Data(name = "serverId") Integer sid, @Data(name = "listResource") ArrayList<Resource> listResource) {
//		// System.out.println("Peer " + peerId + " registered with " + trackerId
//		// + ", interacting with " + id + ": "
//		// + (trackerId >= 0 && id == trackerId));
//		if (serverId >= 0 && sid == serverId) {
//			for (Resource r : listResource) {
//				if (r.getId() == resourceId && r.resourceAvailable() == true) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
}
