package org.javabip.spec.clientserver;

import java.util.ArrayList;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;

@Ports({ @Port(name = "start", type = PortType.enforceable),
	@Port(name = "provide", type = PortType.enforceable),
	@Port(name = "move", type = PortType.enforceable),
	@Port(name = "close", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "org.bip.spec.clientserver.Server")
public class Server {

	public static ArrayList<Resource> listResource;
	private int serverId;
	private int newSid;
	private int resourceId;
	private boolean canBeAccessed;
	
	public Server(int _id) {
		// TODO Auto-generated constructor stub
		serverId = _id;
		listResource = new ArrayList<Resource>();
		
	}
	
	public Server(int _id, Resource... args) {
		serverId = _id;
		listResource = new ArrayList<Resource>();
		System.out.println("Create Server: " + serverId);
		for (Resource res : args) {
			if (!isExistedInResourceList(res)) {
				listResource.add(res);
			}
		}
	}
	
	@Transition(name = "start", source = "0", target = "1", guard = "canActive")
	public void starting() {
		System.out.println("Server {" + serverId + "} is starting....");
	}
	
	@Transition(name = "provide", source = "1", target = "1", guard = "canActive")
	public void running(@Data(name = "resourceIdToUse")Integer id, @Data(name = "takingRs") Boolean takingRs) {
		System.out.println("Server {" + serverId + "} is running....");
		resourceId = id;
		if (getResourceWithId(id) != null && takingRs == false) {
			System.out.println("Server {" + serverId + "}: Resource{" + id + "} can be accessed");
			canBeAccessed = true;
		}else {
			canBeAccessed = false;
		}
		
	}
	
	@Transition(name = "move", source = "1", target = "2", guard = "canMove")
	public void moving() {
		//Resource res = getResourceWithId(rId);
		//if (getResourceWithId(rId) != null && takingRs == false) {
		System.out.println("Server {" + serverId + "}: is MOVING resource{" + resourceId + "} to Server{" + newSid + "}");
		//}
	}
	
	@Transition(name = "close", source = "2", target = "0", guard = "canActive")
	public void closing() {
		System.out.println("Server {" + serverId + "}: Closed");
		listResource = null;
		serverId = -1;
	}
	
	@Guard(name = "canMove")
	public boolean canMove(@Data(name = "newServer") Integer newsId) {
		if (canBeAccessed == true && newsId >=0) {
			newSid = newsId;
			return true;
		}
		return false;
	}
	
	@Guard(name = "canActive")
	public boolean canActive() {
		return (serverId >= 0);
	}
	
//	@Guard(name = "resourceIsExist")
//	public boolean resIsExist(@Data(name = "resourceId") int resourceId) {
//		for (Resource res : listResource) {
//			if (resourceId == res.getId()) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public Resource getResource(@Data(name = "resourceId") int resourceId) {
//		return listResource.get(resourceId);
//	}
	
	@Data (name = "resourceId", accessTypePort = AccessType.any)
	public int getResourceId() {
		return resourceId;
	}
	
	@Data (name = "accessed", accessTypePort = AccessType.any)
	public boolean isAccessed() {
		return canBeAccessed;
	}
	
	@Data(name = "serverId", accessTypePort = AccessType.any)
	public int getServerId() {
		return serverId;
	}
	
	
	//@Data(name = "resource", accessTypePort = AccessType.any)
	public Resource getResourceWithId(int id) {
		for (Resource cur : listResource) {
			if (cur.getId() == id) {
				return cur;
			}
		}
		return null;
	}
	
	public boolean isExistedInResourceList(Resource res) {
		for (Resource cur : listResource) {
			if (cur.getId() == res.getId())
				return true;
		}
		return false;
	}
	
	
}
