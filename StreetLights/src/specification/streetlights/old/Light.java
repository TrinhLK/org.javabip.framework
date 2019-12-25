package specification.streetlights.old;

import org.javabip.annotations.ComponentType;
import org.javabip.annotations.Data;
import org.javabip.annotations.Guard;
import org.javabip.annotations.Port;
import org.javabip.annotations.Ports;
import org.javabip.annotations.Transition;
import org.javabip.api.DataOut.AccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.javabip.api.PortType;

@Ports({ @Port(name = "doON", type = PortType.enforceable),
	@Port(name = "reqON", type = PortType.enforceable),
	@Port(name = "doOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.Light")
public class Light {

	Logger logger = LoggerFactory.getLogger(Light.class);
	private int id;
	private int state;
	private SubLight mySub;
	
	public Light(int _id, int _state) {
		id = _id;
		state = _state;
		Monitor.listLights.put(id, state);
		mySub = new SubLight(20+id, state);
	}
	
	@Transition(name = "reqON", source = "0", target = "1")
	public void reqOn() {
		System.out.println(id + ": Requesting to turn On");
		logger.info(id + ": Requesting to turn On" + "\n");
	}
	
	@Transition(name = "doON", source = "1", target = "2", guard = "canOn")
	public void doOn() {
		state = 1;
		Monitor.listLights.put(id, state);
		mySub.switchOn();
		try {
			Monitor.semaphore.acquire();
			System.out.println(id + ": ON - " + Monitor.listLights);
			logger.info(id + ": ON - " + Monitor.listLights + "\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Transition(name = "doOFF", source = "2", target = "0", guard = "canOff")
	public void doOff() {
		state = 0;
		Monitor.listLights.put(id, state);
		mySub.switchOff();
		Monitor.semaphore.release();
		System.out.println(id + ": OFF - " + Monitor.listLights);
		logger.info(id + ": OFF - " + Monitor.listLights + "\n");
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
	//////
	// GETTERS & SETTERS
	//////
	@Data(name = "lightId", accessTypePort = AccessType.any)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public SubLight getMySub() {
		return mySub;
	}

	public void setMySub(SubLight mySub) {
		this.mySub = mySub;
	}
	
	
}
