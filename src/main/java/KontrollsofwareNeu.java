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

	private int berechneFreiePlaetze(){
		if (scanner.category() == PersonCategory.NORMAL){
			return  hoehstpersonenanzahl - nutzungsreserve;
		}else {
			return hoehstpersonenanzahl;
		}
	}


	public void checkePersonNormalUndAngestellt() {
		if(aktuelleAnzahlAnPersonen <= berechneFreiePlaetze()) {
			scanner.scanCertificate();
			if(scanner.covidNegativeCertificate()){
				if( (-1)*(ChronoUnit.HOURS.between(LocalDateTime.now(), scanner.certificateDate()))+1<=5){
					personReinRaus(true);
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

	public int getAktuelleAnzahlAnPersonen() {
		return aktuelleAnzahlAnPersonen;
	}
}
