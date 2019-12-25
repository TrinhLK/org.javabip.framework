package specification.streetlights.old;

import org.javabip.glue.GlueBuilder;

public class StreetLightGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
//		port(Light.class, "reqON").requires(Monitor.class, "turnON");
//		port(Light.class, "doON").requires(Monitor.class, "turnON");
////		port(Light.class, "doOFF").requires(Monitor.class, "turnOFF");
//		port(Monitor.class, "turnON").requires(Light.class, "reqON");
//		port(Monitor.class, "turnON").requires(Light.class, "doON");
//		port(Monitor.class, "turnOFF").requires(Light.class, "doOFF");
		
//		port(Light.class, "doON").accepts(Monitor.class, "turnON");
//		port(Light.class, "reqON").accepts(Monitor.class, "turnON");
//		port(Monitor.class, "turnON").accepts(Light.class, "reqON", "doON");
//		port(Light.class, "doOFF").accepts(Monitor.class, "turnOFF");
//		port(Monitor.class, "turnOFF").accepts(Light.class, "doOFF");
		
		data(Light.class, "lightId").to(Monitor.class, "lightId");
		data(Monitor.class, "monitorState").to(Light.class, "monitorState");
	}

}
