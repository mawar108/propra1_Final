import netzwerk.Message;
import netzwerk.Netzwerk;
import scanner.PersonCategory;
import scanner.Scanner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class KontrollsofwareNeu {

	private final int hoehstpersonenanzahl;
	private final int nutzungsreserve;
	private int aktuelleAnzahlAnPersonen;

	private final Scanner scanner;
	private final Netzwerk netzwerk;

	private Message message = null;


	public KontrollsofwareNeu(int hoehstpersonenanzahl, int nutzungsreserve, int aktuelleAnzahlAnPersonen, Netzwerk netzwerk,Scanner scanner) {
		this.hoehstpersonenanzahl = hoehstpersonenanzahl;
		this.nutzungsreserve = nutzungsreserve;
		this.aktuelleAnzahlAnPersonen = aktuelleAnzahlAnPersonen;
		this.netzwerk=netzwerk;
		this.scanner = scanner;
	}

	//Hier wird die Anzahl der Personen berechnet, dabei wird Angestellter
	// und Normal unterschieden, für Sonderrechte ist die Anzahl irrelevant
	private int berechneFreiePlaetze(){
		if (scanner.category() == PersonCategory.NORMAL){
			return  hoehstpersonenanzahl - nutzungsreserve;
		}else {
			return hoehstpersonenanzahl;
		}
	}

	//Angestellter und Normal sind sehr ähnlich, der einzige unterschied liegt in der Berechnung der Personenanzahl
	private void checkePersonNormalUndAngestellt() {
		if(aktuelleAnzahlAnPersonen <= berechneFreiePlaetze()) {
			scanner.scanCertificate();
			if(scanner.covidNegativeCertificate()){
				if( (-1)*(ChronoUnit.HOURS.between(LocalDateTime.now(), scanner.certificateDate()))+1<=5){
					personReinRaus(true); //Hier wird auf die jeweilige Anzahl berücksichtigt
				}else {
					scanner.temporaryDenyAccess();
				}

			}else {
				scanner.temporaryDenyAccess();
				scanner.sendAlert();
			}
		}else{
			scanner.temporaryDenyAccess();
		}

	}

	//Hier werden alle Drei Kategoriern gecheckt
	//Zuvor werden Nachrichten gecheckt, da veränderungen an der Anzahl möglich sind.
	public void checkePerson(){
		bearbeiteNachrichten();

		if(scanner.readyToProcessPerson()){
			if(scanner.personWantsToEnter()){
				if(scanner.category() == PersonCategory.SONDERRECHTE){
					personReinRaus(true);
				}else{
					checkePersonNormalUndAngestellt();
				}

			}else{
				personReinRaus(false);
			}
		}
	}

	//true person will rein, false die Person geht raus, Die anzahl wird aktualisiert
	private void personReinRaus(boolean bool){
		if(bool){
			scanner.allowAccess();
			netzwerk.broadcastMessage(PersonCategory.einerRein());
			aktuelleAnzahlAnPersonen++;
		}else{
			scanner.allowLeave();
			netzwerk.broadcastMessage(PersonCategory.einerRaus());
			aktuelleAnzahlAnPersonen--;
		}
	}

	private void bearbeiteNachrichten(){
		if(netzwerk.readyToReceive()){
			this.message = netzwerk.receiveMessageBlocking();
			aktuelleAnzahlAnPersonen += this.message.aktualisierung();
		}
	}

	//getter für Tests
	public int getAktuelleAnzahlAnPersonen() {
		return aktuelleAnzahlAnPersonen;
	}
}
