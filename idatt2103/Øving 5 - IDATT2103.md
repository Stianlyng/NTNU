
- ### Del 1 - Normalisering
	- **Oppgave**
	  Foreslå kandidatnøkler for denne tabellen. Anta at en person kun kan leie en eiendom av gangen, og at en eiendom kan leies ut til kun en person av gangen
		- **Svar**
		  Siden en person ikke kan ha flere leieforhold på samme tid, må man ha en kolonne som knytter
			- kunde_id, fra_uke
			- kunde_id, til_uke
	- **Oppgave**
	  Tabellen er ikke problemfri mht til registrering og
	  sletting av data. Forklar hvorfor.
		- **Svar**
		  Som følge av at kunde_id, eier_id og eiendoms_id entydig identifiserer en person og en eiendom vil:
			- Ikke være mulig å **legge inn** en eiendom før man har et leieforhold på den.
			- Ikke være mulig å fjerne leieforholdene, uten å fjerne eiendommen.
	- **Oppgave**
	  Tegn ett (kun ett) diagram (tilsvarende figur som i
	  læreboka kap. 3) som viser funksjonelle avhengigheter mellom alle attributtene.
		- **Svar**
		- ![[Øving 5 - IDATT2103.excalidraw]]
	- **Oppgave**
	  Du skal nå bruke funksjonelle avhengigheter og BCNF til å
	  foreslå en oppsplitting av tabellen i mindre tabeller slik at problemene vedr.
	  registrering og sletting av data unngås.
		- **Svar**
		- `leieforhold (kunde_id, eiendom_id, fra_uke, til_uke, pris)`
		- `kunde (kunde_id, kunde_navn, kunde_adresse, kunde_tlf)`
		- `eiendom (eiendom_id, eiendom_adresse,  eier_id)`
		- `eier (eier_id, eier_navn, eier_adresse, eier_tlf)`
	- **Oppgave**
	  Kan vi løse denne oppgaven ved å gjennomføre prosessen 1NF
	  --> 2NF --> 3NF? Begrunn svaret.
		- **1NF**
			- Krever at tabellen har en primærnøkkel og maksimum en verdi pr attributt(atomsk). Siden alle kolonnene er unike har vi oppnådd 1NF
		- **2NF**
			- Oppfyller kravet fordi de har verken 
			  repeterende grupper (1NF) eller partielle 
			  determineringer.
		- **3NF**
		  For 3NF må alle kolonnene bestemmes av nøkkelen og ingen andre verdier. I dette tilfellet er eiendom også koblet til eier_id.
			- ==FIKS==
	-
- ### Del 2 - Transaksjoner
  collapsed:: true
	- ### Transaksjoner
		- **Teori**
			- **Hvilke typer låser har databasesystemene?**
				- **delt/shared lås**
					- Brukes kun ved lesing av data og godtar at flere transaksjoner leser de samme dataene samtidig.
					- En delt lås kan settes selv om andre transaksjoner allerede har satt delt lås på samme data.
				- **Eksklusiv/exclusive lås**
					- Kan kun settes av én transaksjon om gangen.
					- Andre transaksjoner har ikke tilgang til dataene før låsen oppheves.
					- Det hindrer at en transaksjon oppdaterer (write) data mens andre enten leser eller oppdaterer de samme dataene.
					- En eksklusiv lås kan ikke settes hvis det finnes andre laser, enten delte eller andre eksklusive, på dataene fra før.
					- En eksklusiv lås krever altså at ingen andre transaksjoner leser eller skriver på dataene i det øyeblikket den settes.
			- **Hva er grunnen til at at man gjerne ønsker lavere
			  isolasjonsnivå enn SERIALIZABLE?**
				- For å unngå range lock. Mer detaljert på oppgave 1 a
			- **Hva skjer om to pågående transaksjoner med isolasjonsnivå
			  serializable prøver select sum(saldo) from konto?**
				- Kan ikke lese data som er modifisert av en annen transaksjon som ikke har blitt comitta
			- **Hva er to-fase-låsing?**
				- En metode som garanterer korrekt utførelse av transaksjoner
					- En transaksjon opp i to faser.
						- I første fase er det bare tillatt å sette låser.
							- Det er ikke lov å oppheve noen laser i denne fasen.
							- Selv om en lås ikke trengs lenger i transaksjonen, må oppheving av alle laser vente til fase to.
						- I fase to er det bare tillatt å oppheve låser
			- **Samtidighets problematikk**
				- **Hvilke typer samtidighetsproblemer (de har egne navn) kan
				  man få ved ulike isolasjonsnivåer?**
					- **read uncomitted**
						- dirty read
						- nonrepeatable read
						- phantoms
					- **read committed**
						- nonrepeatable read
						- phantoms
					- **reapeatable read**
						- phantoms
					- **serializable**
						- ingen tilatt
				- **Hva er optimistisk låsing/utførelse?**
					- Når man leser data, tas et versjonsnr(kan være hash, checksum) og sjekker om versjonen ikke er endret før man skriver kopien i minnet.
					- Når dialogen er avsluttet synkroniseres endringene med databasen
				- **Hva kan grunnen til å bruke dette være?**
					- Unngår mye av problematikken man ser i punktet under..
			- **Hvorfor kan det være dumt med lange transaksjoner (som tar
			  lang tid)?**
				- Går på kompromiss med isolasjons modellen
				- Kan eksponere resultater som er ufullstendige
				- lock issues
				- tilkoblingsproblemer/timeout/closing
			- **Vil det være lurt å ha en transaksjon hvor det kreves input fra
			  bruker?**
			-
		- **Oppgave1**
		  | Tid | Klient 1 | Klient 2 |
		  | :---: | :--- | :--- |
		  | 1 | Sett isolasjonsnivå til read uncommitted. | Sett isolasjonsnivå til serializable. |
		  | 2 | Start transaksjon. | Start transaksjon. |
		  | 3 |  | select * from konto where kontonr=1; |
		  | 4 | select * from konto where kontonr=1; |  |
		  | 5 | update konto set saldo=1 where kontonr=1; |  |
		  | 6 |  | commit; |
		  | 7 | commit; |  |
			- Siden serialize plasserer en 'range lock' eller leselås på datasettet frem til transaksjonen er fullført, vil andre operasjoner ikke kunne oppdatere datasettet.
			- Med andre ord. Klient 1 stopper på linje 5.
			- Ved bruk av et **annet isloasjonsnivå** på klient 2 ville man kunne kjøre på ; )
		- **Oppgave 2**
		  | Tid | Klient 1 | Klient 2 |
		  | :---: | :--- | :--- |
		  | 1 | Sett isolasjonsnivå til read uncommitted. | Sett isolasjonsnivå til read uncommitted. |
		  | 2 | Start transaksjon | Start transaksjon |
		  | 3 | update konto set saldo=1 where kontonr=1; |  |
		  | 4 |  | update konto set saldo=2 where kontonr=1; |
		  | 5 | update konto set saldo=1 where kontonr=2; |  |
		  | 6 | commit; | update konto set saldo=2 where kontonr=2; |
		  | 7 |  | commit; |
			- **a)**
			  Siden isolasjonsnivået 'read uncomitted' tillater 'dirty reads' kan det skape problemer, men i dette tilfellet er operasjonen en oppdatering og isolasjon vil ikke ha noe å si.
				- Klient 1 vil først bli ferdig, men klient 2 sine verdier vil bli oppdater siden denne utfører sist.
			- **b)**
			  Resulterer i en deadlock da begge operasjonene ønsker tilgang til resurser låst av den andre.  I dette tilfellet resulterer det i at begge klientene venter på hverandre, og databasen vil identifisere de 'ofrene' til transaksjonene og kjøre en rollback av handlingene.
			- Slik som i oppgave a, vil ikke isolasjonsnivået ha noen betydning på oppdateringer.
		- **Oppgave 3**
		  | Tid | Klient 1 | Klient 2 |
		  | :---: | :--- | :--- |
		  | 1 | Sett isolasjonsnivå til read uncommitted. | Sett isolasjonsnivå til serializable. |
		  | 2 | Start transaksjon. | Start transaksjon. |
		  | 3 | select sum(saldo) from konto; |  |
		  | 4 |  | update konto set saldo=saldo + 10 where kontonr=1; |
		  | 5 | select sum(saldo) from konto; |  |
		  | 6 |  | commit; |
		  | 7 | select sum(saldo) from konto; |  |
		  | 8 | commit; |  |
			- **Hva skjer?:** Ligger litt i navnet(read uncommited) at verdiene som ikke er comitted kan leses.
			- **Hva vil skjer dersom klient 1 bruker:
			- **read comitted**
				- Vil vise det samme som repeatable read på linje 5
				- Vil vise resultatet etter commit i klient 2 på linje 7
			- **repeatable read**
				- Vil vise det samme som read commited på linje 5
				- Men siden det er et høyere isolasjonsnivå, som også garanterer for at data som er lest ikke kan endres, vil den finne den tidligere leste dataen klar for lesing
			- **serializable**
				- Her vil ikke linje 4 i klient 2 kjøre før range lock fra klient 1 er opphørt
			- Ved bruk av InnoDb


[[Databaser]]
	