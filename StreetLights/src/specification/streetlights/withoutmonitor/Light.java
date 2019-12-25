package specification.streetlights.withoutmonitor;

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

@Ports({ @Port(name = "doON", type = PortType.enforceable),
	@Port(name = "doOFF", type = PortType.enforceable)})
@ComponentType(initial = "0", name = "specification.monitor.Light")
public class Light{

	public static HashMap<Integer, Integer> lights = new HashMap<Integer, Integer>();
	static Semaphore semaphore = new Semaphore(1);

	Logger logger = LoggerFactory.getLogger(Light.class);
	private int lightId;
	private int color;
	
	public Light(int _lightId, int _color) {
		// TODO Auto-generated constructor stub
		lightId = _lightId;
		color = _color;
		lights.put(lightId, color);
	}
	
//	@Transition(name = "doON", source = "0", target = "0")
//	public void run() {
//		try {
////			System.out.println(lightId + ": available Semaphore permits now: " 
////					+ semaphore.availablePermits());
//
//			semaphore.acquire();
//			System.out.println(lightId + ": is turning on!");
//			color = 1;
//			lights.put(lightId, color);
//			for (int i = 5; i >= 1; i--) {
//
//				System.out.println(lightId + ": turns off in " + i + "s");
//
//				// sleep 1 second
//				Thread.sleep(1000);
//
//			}
//		}catch (InterruptedException e) {
//
//			e.printStackTrace();
//
//		}finally {
//			System.out.println(lights);
//			semaphore.release();
//			color = 0;
//			lights.put(lightId, color);
//			System.out.println(lights);
//		}
//	}
	@Transition(name = "doON", source = "0", target = "1", guard = "canOn")
	public void run() {
		try {
			semaphore.acquire();
			lights.put(lightId, 1);
			System.out.println(lights);
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaphore.release();
			lights.put(lightId, 0);

		}
		
	}
	
	@Transition(name = "doOFF", source = "1", target = "0")
	public void releash() {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
		}
		
		
	}
	
	@Guard(name = "canOn")
	public boolean canOn() {
		return (semaphore.availablePermits() == 1);
	}

}
