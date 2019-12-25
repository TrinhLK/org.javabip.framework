package specification.streetlights.string;

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

@Ports({ @Port(name = "doON", type = PortType.enforceable),
	@Port(name = "reqON", type = PortType.enforceable),
	@Port(name = "doOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.streetlight.Light")
public class LightConnector {

	Logger logger = LoggerFactory.getLogger(LightConnector.class);
	private String id;
	private String state;
	private SublightConnector mySub;
	
	public LightConnector() {
		// TODO Auto-generated constructor stub
	}
	
	public LightConnector(String _id, String _state) {
		// TODO Auto-generated constructor stub
		id = _id;
		state = _state;
		mySub = new SublightConnector(id + "-sub", _state);
		Monitor.listLights.put(id, state);
	}
	
	@Transition(name = "reqON", source = "0", target = "1")
	public void reqOn() {
		System.out.println(id + ": Requesting to turn On");
//		logger.debug(id + ": Requesting to turn On" + "\n");
		logger.trace(id + ": Requesting to turn On" + "\n");
	}
	
	@Transition(name = "doON", source = "1", target = "2", guard = "canOn")
	public void doOn() {
		state = "on";
		Monitor.listLights.put(id, state);
		mySub.switchOn();
		try {
			Monitor.semaphore.acquire();
			System.out.println(id + ": ON - " + Monitor.listLights);
//			logger.debug(id + ": ON - " + Monitor.listLights + "\n");
			logger.trace(id + ": ON - " + Monitor.listLights + "\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Transition(name = "doOFF", source = "2", target = "0", guard = "canOff")
	public void doOff() {
		state = "off";
		Monitor.listLights.put(id, state);
		mySub.switchOff();
		Monitor.semaphore.release();
		System.out.println(id + ": OFF - " + Monitor.listLights);
//		logger.debug(id + ": OFF - " + Monitor.listLights + "\n");
		logger.trace(id + ": OFF - " + Monitor.listLights + "\n");
	}
	
	//////
	// GUARDS
	//////
	@Guard(name = "canOn")
	public boolean canOn(@Data(name = "monitorState") int morState) {
		return (morState == 1);
	}
	
	@Guard(name = "canOff")
	public boolean canOff(@Data(name = "monitorState") int morState) {
		return (morState == 0);
	}
	
//	@Guard(name = "canReq")
//	public boolean canReq() {
//		return (Monitor.semaphore.availablePermits() == 1);
//	}
	
	//////
	// GETTERS & SETTERS
	//////
	@Data(name = "lightId", accessTypePort = AccessType.any)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SublightConnector getMySub() {
		return mySub;
	}

	public void setMySub(SublightConnector mySub) {
		this.mySub = mySub;
	}
	
	
}
