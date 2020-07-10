package scanner;

import netzwerk.Message;
import netzwerk.Netzwerk;

public enum PersonCategory {
	NORMAL,
	ANGESTELLT,
	SONDERRECHTE;

	//public Message

	public static Message einerRein() {
		return new Message("Einer geht rein!");
	}

	public static Message einerRaus() {
		return new Message("Einer geht raus");
	}
}
