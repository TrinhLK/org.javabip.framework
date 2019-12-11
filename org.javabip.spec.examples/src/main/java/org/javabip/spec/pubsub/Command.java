package org.javabip.spec.pubsub;

import lsr.concurrence.provided.server.*;
import org.javabip.api.BIPActor;

public class Command {

	private CommandID id;
	//private String id;
	private String topic;
	private String message;
	private BIPActor client;	//BIP Actor representing client issing the command.
	
	public Command(BIPActor _client, CommandID _id, String _topic, String _msg) {
		// TODO Auto-generated constructor stub
		this.client = _client;
		this.id = _id;
		this.topic = _topic;
		this.message = _msg;
	}
	
	public BIPActor getClient() {
		return this.client;
	}
	
	public CommandID getId() {
		return this.id;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public String getMessage() {
		return this.message;
	}
}
