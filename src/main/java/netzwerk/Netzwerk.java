package netzwerk;

public interface Netzwerk {

	// Sendet eine Nachricht an alle anderen Zugangspunkte
	void broadcastMessage(Message message);

	// true <=> eine Nachricht ist vorhanden
	boolean readyToReceive();

	// Nächste Nachricht empfangen
	// Nur aufrufen, wenn eine Nachricht vorhanden ist
	Message receiveMessageBlocking();

}