package org.javabip.spec.trackpeer;

import org.javabip.glue.GlueBuilder;


public class TrackPeerBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		port(Peer.class, "register").requires(Tracker.class,"log");
		port(Peer.class, "speak").requires(Tracker.class,"broadcast");
		port(Peer.class, "listen").requires(Tracker.class,"broadcast");
		port(Peer.class, "unregister").requires(Tracker.class,"log");
		port(Tracker.class, "broadcast").requires();
		port(Tracker.class, "log").requires(Peer.class,"register");
		port(Tracker.class, "log").requires(Peer.class,"unregister");
		
		
		
		port(Peer.class, "register").accepts(Tracker.class,"log");
		port(Peer.class, "speak").accepts(Tracker.class,"broadcast", Peer.class, "listen");
		port(Peer.class, "listen").accepts(Tracker.class,"broadcast", Peer.class, "speak", "listen");
		port(Peer.class, "unregister").accepts(Tracker.class,"log");
		port(Tracker.class, "log").accepts(Peer.class,"register", "unregister");
		port(Tracker.class, "broadcast").accepts(Peer.class,"speak", "listen", Tracker.class,"broadcast");
		
		
		data(Tracker.class, "trackerId").to(Peer.class, "trackerId");
	}

}
/**
 * requires: đăng ký những cổng sẽ tương tác.
 * accepts: để hạn chế những cổng cần tương tác. VD: register yêu cầu log, nhưng nó có thể tương tác với log, log1, log2, ... Tuy nhiên, để chỉ tương tác vào log
 * thì khai báo accepts(log).
 * */
