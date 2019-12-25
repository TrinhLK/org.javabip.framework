package specification.streetlights.old;

public class SubLight {

	private int id;
	private int state;
	
	public SubLight(int _id, int _state) {
		// TODO Auto-generated constructor stub
		id = _id;
		state = _state;
		Monitor.listLights.put(id, state);
	}
	
	public void switchOn() {
		state = 1;
		Monitor.listLights.put(id, state);
	}
	
	public void switchOff() {
		state = 0;
		Monitor.listLights.put(id, state);
	}

	//////
	// GETTERS & SETTERS
	//////
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
	
	
}
