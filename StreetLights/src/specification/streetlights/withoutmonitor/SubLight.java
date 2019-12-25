package specification.streetlights.withoutmonitor;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.PortType;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ports({ @Port(name = "switchON", type = PortType.enforceable),
	@Port(name = "switchOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.SubLight")
public class SubLight {

	Logger logger = LoggerFactory.getLogger(SubLight.class);
	private int subLightId;
	private int subLightColor;
	private boolean switchedOn;
	
	public SubLight(int _sLightId, int _sLightColor) {
		// TODO Auto-generated constructor stub
		subLightId = _sLightId;
		subLightColor = _sLightColor;
		Light.lights.put(subLightId, subLightColor);
	}
	
	@Transition(name = "switchON", source = "0", target = "1")
	public void switchOn(@Data(name = "LightInfo") String lightInfor) {
		
//		System.out.println("The light " + subLightId + " is ON");
//		logger.debug("The light " + subLightId + " is ON\n");
		String[] lightInforElem = lightInfor.split("-");
		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 1)
				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 1)) {
			System.out.println("switchOn: {" + subLightId + "} follow light{" + lightInfor + "}");
//			System.out.println("FlagOn: " + Light.flag);
			subLightColor = 1;
			Light.lights.put(subLightId, subLightColor);
			System.out.println("switchOn:\t" + Light.lights);
			switchedOn = true;
		}
		
//		subLightColor = 1;
//		Light.lights.put(subLightId, subLightColor);
//		System.out.println(Light.lights);
	}
	
	@Transition(name = "switchOFF", source = "1", target = "0")
	public void switchOff(@Data(name = "LightInfo") String lightInfor) {
//		System.out.println("The light " + subLightId + " is OFF");
//		logger.debug("The light " + subLightId + " is OFF\n");
		
		String[] lightInforElem = lightInfor.split("-");
		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 0)
				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 0)) {
			System.out.println("switchOff: {" + subLightId + "} follow light{" + lightInfor + "}");
//			System.out.println("FlagOff: " + Light.flag);
			subLightColor = 0;
			Light.lights.put(subLightId, subLightColor);
			
			System.out.println("switchOff:\t" + Light.lights);
			switchedOn = false;
		}
	}
	
	@Data(name = "isSwitchedOn", accessTypePort = AccessType.any)
	public boolean checkSwitchedOn() {
		return switchedOn;
	}
	
	@Guard(name = "canOff")
	public boolean canOff() {
		return switchedOn;
	}
}
