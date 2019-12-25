package specification.streetlights.minitorsimple;

import org.javabip.glue.GlueBuilder;

public class LightGlueBuilder extends GlueBuilder{

	@Override
	public void configure() {
		
//		port(Monitor.class, "acceptON").requires(Light.class, "reqON", "doON");
//		port(Monitor.class, "acceptOFF").requires(Light.class, "reqOFF", "doOFF");
		port(SubLight.class, "switchON").requires(Light.class, "doON");
		port(SubLight.class, "switchOFF").requires(Light.class, "doOFF");
//		port(Light.class, "doON").requires(SubLight.class, "switchON");
//		port(Light.class, "doOFF").requires(SubLight.class, "switchOFF");
//		port(Light.class, "doON").requires(Monitor.class, "acceptON");
//		port(Light.class, "doOFF").requires(Monitor.class, "acceptOFF");
//		port(Light.class, "reqON").requires(Monitor.class, "acceptON");
//		port(Light.class, "reqOFF").requires(Monitor.class, "acceptOFF");
//		
//		
//		port(Monitor.class, "acceptON").accepts(Light.class, "reqON", "doON");
//		port(Monitor.class, "acceptOFF").accepts(Light.class, "reqOFF", "doOFF");
//		port(SubLight.class, "switchON").accepts(Light.class, "doON");
//		port(SubLight.class, "switchOFF").accepts(Light.class, "doOFF");
		port(Light.class, "doON").accepts(Monitor.class, "acceptON", SubLight.class, "switchON");
		port(Light.class, "doOFF").accepts(Monitor.class, "acceptOFF", SubLight.class, "switchOFF");
//		port(Light.class, "reqON").accepts(Monitor.class, "acceptON");
//		port(Light.class, "reqOFF").accepts(Monitor.class, "acceptOFF");
		
		
		data(Light.class,"lightInfor").to(SubLight.class, "lightInfor");
		data(Light.class,"lightInfor").to(Monitor.class, "lightInfor");
//		data(Monitor.class,"monitorId").to(Light.class, "monitorId");
//		data(Monitor.class,"doActions").to(Light.class, "doActions");
	}
}
