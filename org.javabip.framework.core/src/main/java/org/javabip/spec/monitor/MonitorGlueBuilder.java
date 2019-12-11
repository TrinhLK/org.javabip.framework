package org.javabip.spec.monitor;

import org.javabip.glue.GlueBuilder;

public class MonitorGlueBuilder extends GlueBuilder {

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
		port(Client.class, "download").requires(Monitor.class,"downReq");
		port(Client.class, "releash").requires(Monitor.class, "downFinish");
		port(Server.class, "run").requires(Monitor.class, "downReq");
		port(Server.class, "run").requires(Monitor.class, "moveReq");
		port(Server.class, "run").requires(Monitor.class, "moveFinish");
		port(Server.class, "move").requires(Monitor.class, "moveReq");
		port(Server.class, "close").requires(Monitor.class, "moveFinish");
		
		port(Monitor.class, "downReq").requires(Client.class, "download");
		port(Monitor.class, "downReq").requires(Server.class, "run");
		port(Monitor.class, "downFinish").requires(Client.class, "releash");
		port(Monitor.class, "moveReq").requires(Server.class, "move");
		port(Monitor.class, "moveReq").requires(Server.class, "run");
		port(Monitor.class, "moveFinish").requires(Server.class, "close");
		port(Monitor.class, "moveFinish").requires(Server.class, "run");
		
		
		//port(Monitor.class, "")
		port(Client.class, "download").accepts(Monitor.class,"downReq");
		port(Client.class, "releash").accepts(Monitor.class, "downFinish");
		port(Server.class, "run").accepts(Monitor.class, "moveFinish", "downReq", "moveReq");
		port(Server.class, "move").accepts(Monitor.class, "moveReq");
		port(Server.class, "close").accepts(Monitor.class, "moveFinish");
		port(Monitor.class, "downReq").accepts(Client.class, "download", Server.class, "run");
		port(Monitor.class, "downFinish").accepts(Client.class, "releash");
		port(Monitor.class, "moveReq").accepts(Server.class, "move", "run");
		port(Monitor.class, "moveFinish").accepts(Server.class, "close", "run");
		
		data(Monitor.class, "serverId").to(Client.class, "serverId");
		data(Monitor.class, "newServerId").to(Server.class, "newServerId");
		data(Client.class, "clientResourceId").to(Monitor.class, "clientResourceId");
//		data(Server.class, "rID").to(Monitor.class, "rID");
//		data(Server.class, "sID").to(Monitor.class, "sID");
		data(Monitor.class, "resourceId").to(Server.class, "resourceId");
		data(Client.class, "clientId").to(Monitor.class,"clientId");
		
	}

}
