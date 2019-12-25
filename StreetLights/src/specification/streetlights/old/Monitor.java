package specification.streetlights.old;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
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


@Ports({ @Port(name = "turnON", type = PortType.enforceable),
	@Port(name = "turnOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.Monitor")
public class Monitor {

	Logger logger = LoggerFactory.getLogger(Monitor.class);
	public static HashMap<Integer, Integer> listLights = new HashMap<Integer, Integer>();
	public static Semaphore semaphore = new Semaphore(1);
	private int lightId;
	private int monitorState;
	
	@Transition(name = "turnON", source = "0", target = "1", guard = "canOn")
	public void turnOn() {
		System.out.println("Monitor: accept to turn on [1] light{" + lightId + "} \t available: " +  semaphore.availablePermits());
		logger.info("Monitor: accept to turn on [1] light{" + lightId + "} \t available: " +  semaphore.availablePermits() + "\n");
		monitorState = 1;
	}
	
	@Transition(name = "turnOFF", source = "1", target = "0", guard = "canOff")
	public void turnOff() {

		System.out.println("Monitor: accept to turn off [0] light{" + lightId + "}\t available: " + semaphore.availablePermits());
		logger.info("Monitor: accept to turn off [0] light{" + lightId + "}\t available: " + semaphore.availablePermits() + "\n");
		monitorState = 0;
	}
	
	//////
	// GUARDS
	//////
	@Guard(name="canOn")
	public boolean canOn(@Data(name = "lightId") int _lightId) {
		this.lightId = _lightId;
		
		if (listLights.get(lightId) == 0 && semaphore.availablePermits() == 1) {
			System.out.println("Monitor checker: light{" + lightId + "} can turn ON: " + listLights);
			logger.info("Monitor checker: light{" + lightId + "} can turn ON: " + listLights + "\t available: " + semaphore.availablePermits() + "\n");
			return true;
		}
		
		return false;
	}
	
	@Guard(name="canOff")
	public boolean canOff(@Data(name = "lightId") int _lightId) {
		this.lightId = _lightId;
		
		if (listLights.get(lightId) == 1) {
			System.out.println("Monitor checker: light{" + lightId + "} can turn OFF: " + listLights);
			logger.info("Monitor checker: light{" + lightId + "} can turn OFF: " + listLights + "\n");
			return true;
		}
		
		return false;
	}
	
	//////
	// DATA
	//////
	@Data(name = "monitorState", accessTypePort = AccessType.any)
	public int getMonitorState() {
		return monitorState;
	}
}
