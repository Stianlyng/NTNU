#### av: stian lyng stræte
# Problemstilling
Jeg har i et par år jobbet med flerkameraproduksjon for et firma her i Trondheim. Vi leverer for det meste kamera og video produksjon for live konserter/festivaler. Selv om vi får mange av de samme oppdragene år etter år, er det ofte endringer på hvilke kamerapakker som kreves iht krav fra artister og slikt. Dette skaper et problem med tanke på at vi ofte repererer arbeidet i å velge utstyr for jobben.

En tidligere øving ga meg inspirasjonen til å lage en enkel database, som styres via en flask appliksasjon for å sammenligne type konsert, scene eller  artist opp mot hvilke kamerapakker som fungerer.

- Hver oppdragtype registreres med en enkel beskrivelse. Eksempelvis "Marinen Hovedsene"
- Hver kamerapakke krever en spesifikasjon, og databasen skal kunne utvides til at et enkelt oppdrag skal kunne ha flere oppdragstyper.(festival)
- Hvert kamera kan ha flere spesifikasjoner, slik at man kan matche riktig utstyr til riktig type oppdrag.
# Relasjonsmodell

oppdrags_type(<u>oppdragstype_id</u>, beskrivelse)
kamera_pakke(<u>pakke_id</u>, oppdrags_type_id*, spesifikasjon_id*, kamera_har_spesifikasjon*)
kamera(<u>kamera_id</u>, modell,beskrivelse,serienummer)
spesifikasjon(<u>spesifikasjon_id</u>, beskrivelse)
kamera_har_spesifikasjon(<u>spesifikasjon_id*</u>, <u>kamera_id*</u>)
# EER 

![[Pasted image 20221115131905.png]]

# Spørringer

Henter ut hvilke kamera som har spesifikasjons_id 1, som i dette tilfellet er 4K kamera 
```sql
SELECT k.modell, k.beskrivelse, k.serienummer
FROM oving8.kamera k 
	INNER JOIN oving8.kamera_har_spesifikasjon khs ON ( khs.kamera_id = k.kamera_id  )  
WHERE khs.spesifikasjon_id = 1
```
![[Pasted image 20221115145957.png|400]]

For å se beskrivelse av spesifikasjonen
```sql
SELECT k.modell, k.beskrivelse, k.serienummer, s.beskrivelse
FROM oving8.kamera k 
	INNER JOIN oving8.kamera_har_spesifikasjon khs ON ( khs.kamera_id = k.kamera_id  )  
	INNER JOIN oving8.spesifikasjon s ON ( s.spesifikasjon_id = khs.spesifikasjon_id  )  
WHERE khs.spesifikasjon_id = 1
```
![[Pasted image 20221115145938.png|400]]

Se hvilken kamerapakke som passer med oppdragstypen 2, eller "Marinen" for et enklere format.
```sql
SELECT ot.oppdragstype_id, ot.beskrivelse, s.beskrivelse, k.modell, k.beskrivelse
FROM oving8.oppdrags_type ot 
	INNER JOIN oving8.kamera_pakke kp ON ( kp.oppdrags_type_id = ot.oppdragstype_id  )  
	INNER JOIN oving8.spesifikasjon s ON ( s.spesifikasjon_id = kp.spesifikasjon_id  )  
	INNER JOIN oving8.kamera_har_spesifikasjon khs ON ( khs.spesifikasjon_id = s.spesifikasjon_id  )  
	INNER JOIN oving8.kamera k ON ( k.kamera_id = khs.kamera_id  )  
WHERE ot.beskrivelse like '%Marinen%'
```
![[Pasted image 20221115150352.png|400]]

Se alle kamera og om hvilke spesifikasjoner disse har, eller ikke har
```sql
SELECT k.kamera_id, k.modell, k.beskrivelse, k.serienummer, khs.spesifikasjon_id
FROM oving8.kamera k 
	LEFT OUTER JOIN oving8.kamera_har_spesifikasjon khs ON ( khs.kamera_id = k.kamera_id  )  
```
![[Pasted image 20221115150807.png|400]]


# XML
## Lage xml tabell
```sql
CREATE TABLE xml_4k_kamera (
	xml_id INT NOT NULL,
	xml_tekst TEXT,
	PRIMARY KEY (xml_id)
);
```

## Legge til alle kamera med 4K spesifikasjonen
```sql
-- add all cameras with 4K to xml table
INSERT INTO xml_4k_kamera (xml_id, xml_tekst)
SELECT kamera.kamera_id, CONCAT('<kamera><modell>', kamera.modell, '</modell><beskrivelse>', kamera.beskrivelse, '</beskrivelse><serienummer>', kamera.serienummer, '</serienummer></kamera>')
FROM oving8.kamera
INNER JOIN oving8.kamera_har_spesifikasjon ON kamera.kamera_id = kamera_har_spesifikasjon.kamera_id
INNER JOIN oving8.spesifikasjon ON kamera_har_spesifikasjon.spesifikasjon_id = spesifikasjon.spesifikasjon_id
WHERE spesifikasjon.beskrivelse = '4K';
```
**hva som legges inn (eksempel på hva som skjer i select statementet):**
```xml
<kamera><modell>A120</modell><beskrivelse>Bra i mørket</beskrivelse><serienummer>33223344</serienummer></kamera>
```

## Hente ut modellnavn på alle 4K kamera i xml_4k_kamera tabellen
```sql
SELECT xml_4k_kamera.xml_id, extractvalue(xml_4k_kamera.xml_tekst, '/kamera/modell') AS modell
FROM xml_4k_kamera;
```
**output:** 
```terminal
xml_id modell  
====== ======= 
     1 EOS 500 
     2 A120    
```

# XML drøfting
XML er i essensen tekstfiler som er nødt til å bli lest, parset og skrevet. Alt dette er langsomme prosesser, og bruker gjerne et program for å utføre handlingene. Som følge av sin semi-strukturelle natur, passer XML fint når man har med data med noe struktur, men ikke iht til strengt skjema. Dette betyr at det kan være noen fordeler. Eksempel på dette kan være konfigurasjonsfiler, og muligheten til å supplementere med mer metadata.   

Med tanke på firmaets faktiske utlån av kamera ser man gjerne på flere tusen kamerakombinasjoner i løpet av et år, her ville vi måtte parse oss igjennom veldig mye, i kontrast til et system med indeksering.

I dette tilfellet er det for det meste lagring/modifisering av data, og prosessering som utgjør viktighet. Og det vil naturligvis være bedre bruke mySQL database. Det vil derimot kunne argumenteres for å lagre generere rapporter, eller spesifikke tektstdokument for videre mapping til nettisider, excelark eller PDF filer for å nevne noen..

# noSQL
Skalerbarhet og fremtidsikring er i all hovedsak de viktigste grunnene for å velge noSQL databaser. Selv om SQL kan skaleres det også, vil det by på høy kostnad i form av hardware kapasitet og tidsbruk( master-slave arkitektur). noSQL er bygd med masterless og p2p arkitektur noe som gjør at man for mye bedre hastighet og muligheten til å skalere enkelt ved  å utvide serverkapasiteten. I dette tilfellet vil ikke at skalerbarhet være noe  tema, som følge av datamangden. 

I denne oppgaven er det i all hovedsak lagring av transaksjonell data som lagres og prosesseres. Med lite behov for analyse vil det også her være SQL som er det beste valget.

# Script
```sql
CREATE SCHEMA oving8;

CREATE  TABLE oving8.kamera ( 
	kamera_id            INT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	modell               VARCHAR(20)       ,
	beskrivelse          VARCHAR(20)       ,
	serienummer          CHAR(12)       
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE  TABLE oving8.oppdrags_type ( 
	oppdragstype_id      CHAR(15)  NOT NULL     PRIMARY KEY,
	beskrivelse          VARCHAR(20)       
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE  TABLE oving8.spesifikasjon ( 
	spesifikasjon_id     INT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	beskrivelse          VARCHAR(30)       
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE  TABLE oving8.xml_4k_kamera ( 
	xml_id               INT  NOT NULL     PRIMARY KEY,
	xml_tekst            TEXT       
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE  TABLE oving8.kamera_har_spesifikasjon ( 
	spesifikasjon_id     INT  NOT NULL     ,
	kamera_id            INT  NOT NULL     ,
	CONSTRAINT pk_kval_kandidat PRIMARY KEY ( spesifikasjon_id, kamera_id )
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE INDEX kval_kandidat_fk2 ON oving8.kamera_har_spesifikasjon ( kamera_id );

CREATE  TABLE oving8.kamera_pakke ( 
	pakke_id             INT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	oppdrags_type_id     CHAR(15)  NOT NULL     ,
	spesifikasjon_id     INT  NOT NULL     ,
	kamera_har_spesifikasjon_id INT       
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE INDEX oppdrag_fk1 ON oving8.kamera_pakke ( oppdrags_type_id );

CREATE INDEX oppdrag_fk3 ON oving8.kamera_pakke ( spesifikasjon_id, kamera_har_spesifikasjon_id );

ALTER TABLE oving8.kamera_har_spesifikasjon ADD CONSTRAINT kval_kandidat_fk1 FOREIGN KEY ( spesifikasjon_id ) REFERENCES oving8.spesifikasjon( spesifikasjon_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE oving8.kamera_har_spesifikasjon ADD CONSTRAINT kval_kandidat_fk2_0 FOREIGN KEY ( kamera_id ) REFERENCES oving8.kamera( kamera_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE oving8.kamera_pakke ADD CONSTRAINT oppdrag_fk1 FOREIGN KEY ( oppdrags_type_id ) REFERENCES oving8.oppdrags_type( oppdragstype_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE oving8.kamera_pakke ADD CONSTRAINT oppdrag_fk2 FOREIGN KEY ( spesifikasjon_id ) REFERENCES oving8.spesifikasjon( spesifikasjon_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE oving8.kamera_pakke ADD CONSTRAINT oppdrag_fk3 FOREIGN KEY ( spesifikasjon_id, kamera_har_spesifikasjon_id ) REFERENCES oving8.kamera_har_spesifikasjon( spesifikasjon_id, kamera_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

INSERT INTO oving8.kamera( modell, beskrivelse, serienummer ) VALUES ( 'EOS 500', 'DSLR', '11223344');
INSERT INTO oving8.kamera( modell, beskrivelse, serienummer ) VALUES ( 'A120', 'Bra i mørket', '33223344');
INSERT INTO oving8.kamera( modell, beskrivelse, serienummer ) VALUES ( 'x100', 'Auto robo', '44223344');
INSERT INTO oving8.kamera( modell, beskrivelse, serienummer ) VALUES ( 'NK100', 'Scenecam', 'AD775');
INSERT INTO oving8.oppdrags_type( oppdragstype_id, beskrivelse ) VALUES ( '1', 'Spektrum Hovescene');
INSERT INTO oving8.oppdrags_type( oppdragstype_id, beskrivelse ) VALUES ( '2', 'Marinen Hovedscene');
INSERT INTO oving8.oppdrags_type( oppdragstype_id, beskrivelse ) VALUES ( '3', 'Marinen Elvescene');
INSERT INTO oving8.spesifikasjon( beskrivelse ) VALUES ( '4K');
INSERT INTO oving8.spesifikasjon( beskrivelse ) VALUES ( 'Integrert mikrofon');
INSERT INTO oving8.spesifikasjon( beskrivelse ) VALUES ( '8K');
INSERT INTO oving8.xml_4k_kamera( xml_id, xml_tekst ) VALUES ( 1, '<kamera><modell>EOS 500</modell><beskrivelse>DSLR</beskrivelse><serienummer>11223344</serienummer></kamera>');
INSERT INTO oving8.xml_4k_kamera( xml_id, xml_tekst ) VALUES ( 2, '<kamera><modell>A120</modell><beskrivelse>Bra i mørket</beskrivelse><serienummer>33223344</serienummer></kamera>');
INSERT INTO oving8.kamera_har_spesifikasjon( spesifikasjon_id, kamera_id ) VALUES ( 1, 1);
INSERT INTO oving8.kamera_har_spesifikasjon( spesifikasjon_id, kamera_id ) VALUES ( 1, 2);
INSERT INTO oving8.kamera_har_spesifikasjon( spesifikasjon_id, kamera_id ) VALUES ( 2, 2);
INSERT INTO oving8.kamera_har_spesifikasjon( spesifikasjon_id, kamera_id ) VALUES ( 3, 2);
INSERT INTO oving8.kamera_pakke( oppdrags_type_id, spesifikasjon_id, kamera_har_spesifikasjon_id ) VALUES ( '1', 1, null);
INSERT INTO oving8.kamera_pakke( oppdrags_type_id, spesifikasjon_id, kamera_har_spesifikasjon_id ) VALUES ( '1', 2, null);
INSERT INTO oving8.kamera_pakke( oppdrags_type_id, spesifikasjon_id, kamera_har_spesifikasjon_id ) VALUES ( '2', 1, null);
```