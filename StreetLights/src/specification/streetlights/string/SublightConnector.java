package specification.streetlights.string;

public class SublightConnector {

	private String id;
	private String state;
	
	public SublightConnector() {
		// TODO Auto-generated constructor stub
	}
	
	public SublightConnector(String _id, String _state) {
		// TODO Auto-generated constructor stub
		id = _id;
		state = _state;
		Monitor.listLights.put(id, state);
	}

	public void switchOn() {
		state = "on";
		Monitor.listLights.put(id, state);
	}
	
	public void switchOff() {
		state = "off";
		Monitor.listLights.put(id, state);
	}
	
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
	
	
}
