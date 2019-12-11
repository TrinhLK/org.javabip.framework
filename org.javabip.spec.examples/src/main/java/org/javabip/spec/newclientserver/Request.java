package org.javabip.spec.newclientserver;

import org.javabip.api.BIPActor;

public class Request {

	private int id;
	private BIPActor source;
	private BIPActor target;
	private Command command;
	
	public Request(int _id, BIPActor _source, BIPActor _target, Command cmd) {
		// TODO Auto-generated constructor stub
		id = _id;
		source = _source;
		target = _target;
		command = cmd;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BIPActor getSource() {
		return source;
	}

	public void setSource(BIPActor source) {
		this.source = source;
	}

	public BIPActor getTarget() {
		return target;
	}

	public void setTarget(BIPActor target) {
		this.target = target;
	}
	
	
}
