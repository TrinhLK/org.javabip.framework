package specification.streetlights.minitorsimple;

import java.util.HashMap;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.DataOut.AccessType;
import org.javabip.api.PortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "reqON", type = PortType.enforceable),
	@Port(name = "reqOFF", type = PortType.enforceable),
	@Port(name = "doON", type = PortType.enforceable),
	@Port(name = "doOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.Light")
public class Light {

	Logger logger = LoggerFactory.getLogger(Light.class);
	private int lightId;
	private int color;
	public static HashMap<Integer, Integer> lights = new HashMap<Integer, Integer>();
	
	public Light(int _lightId, int _color) {
		lightId = _lightId;
		color = _color;
		lights.put(_lightId, _color);
	}
	
	@Transition(name = "reqON", source = "0", target = "1")
	public void reqOn() {
//		System.out.println("The light " + lightId + " request monitor{" + mId + "} turn ON");
//		logger.info("The light " + lightId + " request monitor{" + mId + "} turn ON\n");
	}
	
	@Transition(name = "reqOFF", source = "2", target = "1")
	public void reqOff() {
//		System.out.println("The light " + lightId + " request monitor{" + mId + "} turn OFF");
//		logger.info("The light " + lightId + " request monitor{" + mId + "} turn ON\n");
	}
	
	@Transition(name = "doON", source = "1", target = "2", guard = "canOn")
	public void doOn() {
//		System.out.println("The light " + lightId + " is ON");
//		logger.info("The light " + lightId + " is ON\n");
		color = 1;
		lights.put(lightId, color);
	}
	
	@Transition(name = "doOFF", source = "1", target = "0", guard = "canOff")
	public void doOff() {
//		System.out.println("The light " + lightId + " is OFF");
//		logger.info("The light " + lightId + " is OFF\n");
		color = 0;
		lights.put(lightId, color);
	}
	
	@Data(name = "lightInfor", accessTypePort = AccessType.any)
	public String lightInfor() {
		return lightId + "-" + color;
	}
	
	@Guard(name = "canOn")
	public boolean canOn(@Data(name = "doActions") String accept) {
		String accepts[] = accept.split("-");
		return (lightId == Integer.parseInt(accepts[0]) && Integer.parseInt(accepts[1]) == 1);
	}
	
	@Guard(name = "canOff")
	public boolean canOff(@Data(name = "doActions") String accept) {
		String accepts[] = accept.split("-");
		return (lightId == Integer.parseInt(accepts[0]) && Integer.parseInt(accepts[1]) == 0);
	}
	

}
