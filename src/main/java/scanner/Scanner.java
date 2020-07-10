package scanner;

import java.time.LocalDateTime;

public interface Scanner {

	// true <=> eine Person ist am Zugangspunkt
	boolean readyToProcessPerson();

	// Nur aufrufen, wenn eine Person da ist
	// true: Person will rein
	// false: Person will raus
	boolean personWantsToEnter();

	// Nur aufrufen, wenn eine Person verlassen will
	// Muss immer gestattet werden!
	void allowLeave();

	// Starte den Scanning Prozess
	// Nur aufrufen, wenn eine Person eintreten will
	void scanCertificate();

	// Nur aufrufen, wenn das Zertifikat gescannt wurde
	// true <=> letzter Test ist negativ
	boolean covidNegativeCertificate();

	// Nur aufrufen, wenn das Zertifikat gescannt wurde
	// Zeitpunkt des Tests
	LocalDateTime certificateDate();

	// Status der Person (normal, angestellt, sonderrechte)
	// Nur aufrufen, wenn eine Person eintreten will
	PersonCategory category();

	// Zugang gestatten
	void allowAccess();

	// Zugang temporär verweigern (zu voll)
	void temporaryDenyAccess();

	// Alarm auslösen (positiv getestete Person)
	void sendAlert();

}