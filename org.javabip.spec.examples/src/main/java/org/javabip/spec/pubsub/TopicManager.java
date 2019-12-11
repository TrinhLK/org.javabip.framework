package org.javabip.spec.pubsub;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.HashMap;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.BIPActor;
import org.javabip.api.PortType;

@Ports({ @Port(name = "executeCommand", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.TopicManager")
public class TopicManager {

	private HashMap<String, BIPActor> topics;
	
	public TopicManager(HashMap<String, BIPActor> topics) {
		// TODO Auto-generated constructor stub
		this.topics = topics;
	}
	
	public void executeCommand(@Data(name = "command") Command command) {
		switch (command.getId()) {
		case SUBSCRIBE:
			subscribe(command.getClient(), command.getTopic());
			break;
		case UNSUBSCRIBE:
			unsubscribe(command.getClient(), command.getTopic());
			break;
		case PUBLISH:
			publish(command.getClient(), command.getTopic(), command.getMessage());
			break;
		default:
			break;
		}
	}
	
	private void subscribe(BIPActor client, String topicName) {
		BIPActor topic = topics.get(topicName);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		topic.inform("addClient", data);
	}
	
	private void unsubscribe(BIPActor client, String topicName) {
		BIPActor topic = topics.get(topicName);

        HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		topic.inform("removeClient", data);
	}
	
	private void publish(BIPActor client, String topicName, String message) {
		BIPActor topic = topics.get(topicName);
		
        HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("client", client);
		data.put("msg", message);
		topic.inform("publish", data);
	}
}
