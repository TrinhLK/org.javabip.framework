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

import java.util.LinkedList;
import java.util.Queue; 


@Ports({ @Port(name = "acceptON", type = PortType.enforceable),
	@Port(name = "acceptOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.Monitor")
public class Monitor {

	Logger logger = LoggerFactory.getLogger(Monitor.class);
	private int monitorId;
	private int lightId;
	private int acceptOnOff;
	private boolean flagReqOn;
	Queue<String> listRequest = new LinkedList<String>();
	
	public Monitor(int _morId) {
		monitorId = _morId;
		lightId = -1;
		acceptOnOff = -1;
		flagReqOn = true; //point that can req to on
	}
	
	@Transition(name = "acceptON", source = "0", target = "1")
	public void acceptOn(@Data(name = "lightInfor") String lightInfor) {
//		logger.info("Monitor: accept " + lightId + " to ON\n");
//		System.out.println("Monitor: accept " + lightId + " to ON");
		lightId = Integer.parseInt(lightInfor.split("-")[0]);
		if (flagReqOn) {
			if ((lightId == 1 && Light.lights.get(1) == 0 && Light.lights.get(2) == 0)
					|| (lightId == 2 && Light.lights.get(2) == 0 && Light.lights.get(1) == 0)) {
//				System.out.println("Check doOn: " + Light.lights);
				acceptOnOff = 1;
				flagReqOn = false;
			}
		}
		
		
	}
	
	@Transition(name = "acceptOFF", source = "1", target = "0")
	public void acceptOff(@Data(name = "lightInfor") String lightInfor) {
//		logger.info("Monitor: accept " + lightId + " to OFF\n");
//		System.out.println("Monitor: accept " + lightId + " to OFF");
		lightId = Integer.parseInt(lightInfor.split("-")[0]);
		if (Light.lights.get(lightId) == 1) {
			acceptOnOff = 0;
			flagReqOn = true;
		}
		
	}
	
	@Guard(name = "isOnable")
	public boolean isOnable(@Data(name = "lightInfor") String lightInfor) {
		
		lightId = Integer.parseInt(lightInfor.split("-")[0]);
		System.out.println(lightInfor);
		if (lightId == 1 && Light.lights.get(1) == 0 && Light.lights.get(2) == 0) {
			System.out.println("Check doOn: " + Light.lights);
			return true;
		}
//		if (lightId == 1 && Light.lights.get(1) == 0 && Light.lights.get(2) == 1) {
//			return true;
//		}
		if (lightId == 2 && Light.lights.get(2) == 0 && Light.lights.get(1) == 0) {
			System.out.println("Check doOn: " + Light.lights);
			return true;
		}
//		if (lightId == 2 && Light.lights.get(2) == 0 && Light.lights.get(1) == 1) {
//			return true;
//		}
		return false;
	}
	
	@Guard(name = "isOffable")
	public boolean isOffable(@Data(name = "lightInfor") String lightInfor) {
		lightId = Integer.parseInt(lightInfor.split("-")[0]);
		if (Light.lights.get(lightId) == 1) {
			return true;
		}
		return false;
	}
	
	@Data(name = "doActions", accessTypePort = AccessType.any)
	public String acceptAction() {
		return lightId + "-" + acceptOnOff;
	}
}
