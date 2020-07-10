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
	public void test0(){
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
	public void test(){
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
	public void test2(){
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
	public void test3(){
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
	public void test4(){
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
	public void test5(){
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
	public void test6(){
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
	public void test7(){
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
	public void test8(){
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
	public void test9(){
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
	public void test10(){
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
	public void test11(){
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
	public void test12(){
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
	public void test13(){
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
	public void test14(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(netzwerkMock.readyToReceive()).thenReturn(true);
		when(netzwerkMock.receiveMessageBlocking()).thenReturn(PersonCategory.einerRein());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(20);
	}

	@Test
	public void test15(){
		Scanner scannerMock= mock(Scanner.class);
		Netzwerk netzwerkMock= mock(Netzwerk.class);
		when(netzwerkMock.readyToReceive()).thenReturn(true);
		when(netzwerkMock.receiveMessageBlocking()).thenReturn(PersonCategory.einerRaus());

		KontrollsofwareNeu kontrollsofwareNeu= new KontrollsofwareNeu(20,10,19,netzwerkMock,scannerMock);
		kontrollsofwareNeu.checkePerson();

		assertThat(kontrollsofwareNeu.getAktuelleAnzahlAnPersonen()).isEqualTo(18);
	}

}
