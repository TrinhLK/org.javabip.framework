package org.javabip.spec.newclientserver;

import java.util.LinkedList;

import org.javabip.annotations.Guard;
import org.javabip.annotations.Transition;
import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.api.PortType;

@Ports({ @Port(name = "putRequest", type = PortType.enforceable),
	@Port(name = "getRequest", type = PortType.enforceable) })
@ComponentType(initial = "0", name = "org.bip.spec.newclientserver.RequestList")
public class RequestList {
	
	private LinkedList<Request> requestList;
	private int buffSize;
	
	public RequestList(int size) {
		// TODO Auto-generated constructor stub
		buffSize = size;
		requestList = new LinkedList<Request>();
	}
	
	@Transition(name = "getRequest", source = "0", target = "0", guard = "isBufferNotEmpty")
	public void getCommandToHandler() {
		requestList.remove();
	}
	
	@Transition(name = "putRequest", source = "0", target = "0", guard = "isBufferNotFull")
	public void putCommandFromReader(@Data(name = "input") Request request) {
		requestList.add(request);
	}
	
	@Guard(name = "isBufferNotEmpty")
	public boolean isBufferNotEmpty() {
		return !requestList.isEmpty();
	}
	
	@Guard(name = "isBufferNotFull")
	public boolean isBufferNotFull() {
		return requestList.size() != buffSize;
	}
	
	@Data(name = "request")
	public Request getNextRequest() {
		return requestList.get(0);
	}
}
