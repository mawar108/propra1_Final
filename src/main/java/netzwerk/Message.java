package netzwerk;

public class Message {

	private String message;

	public Message(String message) {
		this.message = message;
	}

	public int aktualisierung() { //Einer geht rein -> +1 wenn nicht muss er raus gehen -> -1
		if(message.equals("Einer geht rein!"))
			return 1;
		return -1;
	}
}
