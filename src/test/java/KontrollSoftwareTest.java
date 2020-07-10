import netzwerk.Netzwerk;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scanner.PersonCategory;
import scanner.Scanner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class KontrollSoftwareTest {

	@Test
	public void keinerAmEingangAnzahlBleibtGleich(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.SONDERRECHTE);
		when(scannerMock.readyToProcessPerson()).thenReturn(false);
		when(scannerMock.personWantsToEnter()).thenReturn(true);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,2,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(2);
		verify(scannerMock, times(0)).allowAccess();
	}

	@Test
	public void sonderrechteReinAnzahlUmEinsErhoeht(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.SONDERRECHTE);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,0,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(1);
		verify(scannerMock, times(1)).allowAccess();
	}

	@Test
	public void angestellterReinAnzahlUmEinsErhoeht(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.ANGESTELLT);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(true);
		when(scannerMock.certificateDate()).thenReturn(LocalDateTime.now());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,0,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(1);
		verify(scannerMock, times(1)).allowAccess();
	}

	@Test
	public void normalePersonReinAnzahlUmEinsErhoeht(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(true);
		when(scannerMock.certificateDate()).thenReturn(LocalDateTime.now());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,0,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(1);
		verify(scannerMock, times(1)).allowAccess();
	}

	@Test
	public void sonderrechteReinVollAnzahlUmEinsErhoeht(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.SONDERRECHTE);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(true);
		when(scannerMock.certificateDate()).thenReturn(LocalDateTime.now());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,20,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(21);
		verify(scannerMock, times(1)).allowAccess();

	}

	@Test
	public void angestellterReinVollAnzahlGleich(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.ANGESTELLT);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,20,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(20);
		verify(scannerMock, times(1)).temporaryDenyAccess();
		verify(scannerMock, times(0)).allowAccess();
	}


	@Test
	public void normalReinVollAnzahlGleich(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,10,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(10);
		verify(scannerMock, times(1)).temporaryDenyAccess();
		verify(scannerMock, times(0)).allowAccess();
	}

	@Test
	public void normalRausAnzahlUmEinsWeniger(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(false);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,20,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(19);
		verify(scannerMock, times(1)).allowLeave();

	}

	@Test
	public void normalRausVollAnzahlUmEinsWeniger(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(false);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,22,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(21);
		verify(scannerMock, times(1)).allowLeave();

	}

	@Test
	public void normallReinPositivAnzahlGleichAlarmUndBlockieren(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(false);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,2,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(2);
		verify(scannerMock,times(1)).sendAlert();
		verify(scannerMock, times(0)).allowAccess();
	}

	@Test
	public void angestellterReinPositivAnzahlGleichAlarmUndBlockieren(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.ANGESTELLT);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(false);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,2,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(2);
		verify(scannerMock,times(1)).sendAlert();
		verify(scannerMock, times(0)).allowAccess();
	}

	@Test
	public void normalReinPositivAnzahlGleichAlarmUndBlockieren(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.NORMAL);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(false);

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,2,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(2);
		verify(scannerMock,times(1)).sendAlert();
		verify(scannerMock, times(0)).allowAccess();
	}


	@Test
	public void angestellterReinVollFuerNormalAnzahlUmEinsErhoeht(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.ANGESTELLT);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(true);
		when(scannerMock.certificateDate()).thenReturn(LocalDateTime.now());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(20);
		verify(scannerMock, times(1)).allowAccess();
	}

	@Test
	public void angestellterReinZertifikatUngueltigAnzahlGleichBlockieren(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(scannerMock.category()).thenReturn(PersonCategory.ANGESTELLT);
		when(scannerMock.readyToProcessPerson()).thenReturn(true);
		when(scannerMock.personWantsToEnter()).thenReturn(true);
		when(scannerMock.covidNegativeCertificate()).thenReturn(true);
		when(scannerMock.certificateDate()).thenReturn(LocalDateTime.now().plusHours(-6));

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(19);
		verify(scannerMock,times(1)).temporaryDenyAccess();
		verify(scannerMock, times(0)).allowAccess();
	}

	@Test
	public void netzwerkNachrichtEinerReinAnzahlErhoehtUmEins(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(netzwerkMock.readyToReceive()).thenReturn(true);
		when(netzwerkMock.receiveMessageBlocking()).thenReturn(PersonCategory.einerRein());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(20);
	}

	@Test
	public void netzwerkNachrichtEinerRausAnzahlUmEinsWeniger(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(netzwerkMock.readyToReceive()).thenReturn(true);
		when(netzwerkMock.receiveMessageBlocking()).thenReturn(PersonCategory.einerRaus());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(18);
	}

}
