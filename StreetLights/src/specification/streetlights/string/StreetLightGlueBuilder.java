package specification.streetlights.string;

import org.javabip.glue.GlueBuilder;

public class StreetLightGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		// TODO Auto-generated method stub
//		port(LightConnector.class, "reqON").requires(Monitor.class, "turnON");
//		port(LightConnector.class, "doON").requires(Monitor.class, "turnON");
////		port(Light.class, "doOFF").requires(Monitor.class, "turnOFF");
//		port(Monitor.class, "turnON").requires(LightConnector.class, "reqON");
//		port(Monitor.class, "turnON").requires(LightConnector.class, "doON");
//		port(Monitor.class, "turnOFF").requires(Light.class, "doOFF");
		
//		port(Light.class, "doON").accepts(Monitor.class, "turnON");
//		port(Light.class, "reqON").accepts(Monitor.class, "turnON");
//		port(Monitor.class, "turnON").accepts(Light.class, "reqON", "doON");
//		port(LightConnector.class, "doOFF").accepts(Monitor.class, "turnOFF");
//		port(Monitor.class, "turnOFF").accepts(LightConnector.class, "doOFF");
		
		data(LightConnector.class, "lightId").to(Monitor.class, "lightId");
		data(Monitor.class, "monitorState").to(LightConnector.class, "monitorState");
	}

}
