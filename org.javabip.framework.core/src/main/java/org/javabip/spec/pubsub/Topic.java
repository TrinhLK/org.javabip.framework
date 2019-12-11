package org.javabip.spec.pubsub;

import java.util.HashMap;
import java.util.HashSet;

import org.javabip.annotations.*;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Transition;
import org.javabip.api.*;

@Ports({ @Port(name = "getName", type = PortType.spontaneous), @Port(name = "addClient", type = PortType.spontaneous),
	@Port(name = "removeClient", type = PortType.spontaneous), @Port(name = "publish", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.Topic")
public class Topic {

	private String name;
	private HashSet<BIPActor> clients;
	
	public Topic (String name) {
		this.name = name;
		this.clients = new HashSet<BIPActor>();
	}
	
	//Add clients to sub list, client sends an inform
	@Transition(name = "addClient", source = "0", target = "0")
	public void addClient(@Data(name="client") BIPActor client) {
		if (!clients.contains(client)) {
			clients.add(client);
			try {
				
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("topic", name);
				client.inform("addTopic", data);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	//remove clients from sub list, client sends an inform
	@Transition(name = "removeClient", source = "0", target = "0")
	public void removeClient(@Data(name="client") BIPActor client) {
		if (clients.contains(client)) {
			this.clients.remove(client);
			
			try {
				
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("topic", name);
				client.inform("removeTopic", data);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	//publish actor
	@Transition(name = "publish", source = "0", target = "0")
	public void publish(@Data(name = "client") BIPActor publishingClient, @Data(name = "msg") String message) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("msg", message);
		
		for (BIPActor currentClient : clients) {
			try {
				currentClient.inform("write", data);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
