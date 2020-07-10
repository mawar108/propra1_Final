package netzwerk;

public class Message {

	private String message;

	public Message(String message) {
		this.message = message;
	}

	public int aktualisierung() {
		if(message.equals("Einer geht rein!"))
			return 1;
		return -1;
	}
}
