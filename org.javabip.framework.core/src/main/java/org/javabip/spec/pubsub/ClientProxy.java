package org.javabip.spec.pubsub;

/**
 * Client wants to sub and unsub topic 
 * 
 * */
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.BIPActor;
import org.javabip.api.BIPActorAware;
import org.javabip.api.DataOut.AccessType;
import org.javabip.api.PortType;

@Ports({ @Port(name = "addTopic", type = PortType.spontaneous), 
	 @Port(name = "removeTopic", type = PortType.spontaneous),
	 @Port(name = "write", type = PortType.spontaneous) })
@ComponentType(initial = "0", name = "org.bip.spec.pubsub.ClientProxy")
public class ClientProxy implements BIPActorAware{

	private PrintWriter output;
	private ArrayList<String> topics;
	private BIPActor bipActor;
	private long id;
	private Socket socket;
	private int noOfTrans;
	
	public ClientProxy(int id, OutputStream outputStream) {
		// TODO Auto-generated constructor stub
		this.topics = new ArrayList<String>(0);
		this.output = new PrintWriter(outputStream, true);
		this.id = id;
	}
	
	public ClientProxy(int id, ServerSocket tcpAccepter) throws IOException {
		// TODO Auto-generated constructor stub
		this.topics = new ArrayList<String>(0);
		this.socket = tcpAccepter.accept();
		this.output = new PrintWriter(this.socket.getOutputStream(), true);
		this.id = id;
	}
	
	//Client proxy ... receives msg ...
	@Transition(name="write", source="0", target="0")
	public void write (@Data(name="msg") String msg) {
		output.println(msg);
	}
	
	//Client proxy ... subscribes to topic ...
	@Transition(name = "addTopic", source = "0", target = "0")
	public void addTopic(@Data(name="topic") String topic) {
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
			this.write("subscribe_ack epfl");
		}
	}
	
	//Client proxy ... unsubscribes from topic ...
	@Transition(name = "removeTopic", source = "0", target = "0")
	public void removeTopic(@Data(name="topic") String topic) {
		if (!this.topics.contains(topic)) {
			this.topics.add(topic);
			this.write("unsubscribe_ack epfl");
		}
	}
	
	@Override
	public void setBIPActor(BIPActor actor) {
		// TODO Auto-generated method stub
		this.bipActor = actor;
	}
	
	@Data(name = "bipActor", accessTypePort = AccessType.any)
	public BIPActor getBIPActor() {
		return bipActor;
	}

}
