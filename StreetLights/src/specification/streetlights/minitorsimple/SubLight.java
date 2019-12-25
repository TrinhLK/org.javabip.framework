package specification.streetlights.minitorsimple;

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
	private String lightInforation;
	
	public SubLight(int _sLightId, int _sLightColor) {
		// TODO Auto-generated constructor stub
		subLightId = _sLightId;
		subLightColor = _sLightColor;
		Light.lights.put(subLightId, subLightColor);
	}
	
	@Transition(name = "switchON", source = "0", target = "1", guard = "canOn")
	public void switchOn() {
		
//		System.out.println("The light " + subLightId + " is ON");
//		logger.debug("The light " + subLightId + " is ON\n");
//		String[] lightInforElem = lightInforation.split("-");
//		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 1)
//				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 1)) {
			System.out.println("switchOn: {" + subLightId + "} follow light{" + lightInforation + "}");
			subLightColor = 1;
			Light.lights.put(subLightId, subLightColor);
			System.out.println("switchOn:\t" + Light.lights);
//		}
		
//		subLightColor = 1;
//		Light.lights.put(subLightId, subLightColor);
//		System.out.println(Light.lights);
	}
	
	@Transition(name = "switchOFF", source = "1", target = "0", guard = "canOff")
	public void switchOff() {
//		System.out.println("The light " + subLightId + " is OFF");
//		logger.debug("The light " + subLightId + " is OFF\n");
		
//		String[] lightInforElem = lightInfor.split("-");
//		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 0)
//				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 0)) {
			System.out.println("switchOff: {" + subLightId + "} follow light{" + lightInforation + "}");
			subLightColor = 0;
			Light.lights.put(subLightId, subLightColor);
			
			System.out.println("switchOff:\t" + Light.lights);
//		}
		
		
	}
	
	@Guard(name = "canOn")
	public boolean canOn(@Data(name = "lightInfor") String lightInfor) {
		lightInforation = lightInfor;
		String[] lightInforElem = lightInfor.split("-");
		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 1)
				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 1)) {
			return true;
		}

		return false;
	}
	
	@Guard(name = "canOff")
	public boolean canOff(@Data(name = "lightInfor") String lightInfor) {
		lightInforation = lightInfor;
		String[] lightInforElem = lightInfor.split("-");
		if ((subLightId == 3 && Integer.parseInt(lightInforElem[0]) == 1 && Integer.parseInt(lightInforElem[1]) == 0)
				|| (subLightId == 4 && Integer.parseInt(lightInforElem[0]) == 2 && Integer.parseInt(lightInforElem[1]) == 0)) {
			return true;
		}
		return false;
	}
}
