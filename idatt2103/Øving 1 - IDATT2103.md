# Problemstilling:

**Database for boligbyggerlaget Bygg & Bo.**

Du skal lage en database for boligbyggerlaget Bygg & Bo (BB). BB administrerer byggeaktiviteter og etablerte borettslag. Følgende data om borettslagene er nødvendig for administrasjonen, og skal lagres i en egen database:
- BB eier mange borettslag.
- Hvert borettslag er bestemt ved navn, adresse, antall hus og/eller blokkenheter og etableringsår.
  
  BB har et medlemsregister over samtlige andelseiere i borettslagene. Av disse utgjør leietakerne i ulike borettslag ca. 75 %. (Det er mulig å være medlem i et borettslag uten å eie en leilighet der, for eksempel kan det være folk som ønsker å flytte dit, men som venter på at en leilighet skal bli ledig for salg.)
  
  Hvert borettslag er oppdelt i hus/blokker med egne adresser, og i hvert hus/blokk finnes det et antall leiligheter. Hus/blokk beskrives ved antall etasjer og antall leiligheter. Leilighetene beskrives vha av antall rom og antall m2, etasje, og et leilighetsnummer.
  
  En andelseier kan bare eie én leilighet. En leilighet eies av en andelseier, men i perioder kan den være uten eier.
## Innledende oppgave a: Sett opp mulige tabeller

Sett opp noen mulige tabeller med fornuftige attributtnavn. Et minimum må være å opprette tabeller for henholdsvis borettslag, bygninger, andelseiere og leiligheter. Du bør tenke litt igjennom hvilke data som skal lagres før tabellene lages. Unngå å lagre samme data i flere tabeller for å minske faren for duplisering av data. Unngå også å lagre data som kan avledes (regnes ut) av andre data.
### Svar
![[Drawing 2022-09-05 20.53.11.excalidraw]]

```sql
borettslag(navn, adresse, etablert)
bygninger(id, adresse)
leiligheter(id, ant_rom,kvm,etasje)
andelseiere(id, fornavn, etternavn, telefon)
```

Jeg velger å ikke ha en attributt vedrerørende antall etasjer i 'bygninger' tabellen, da jeg tenker en slik database gjerne ønsker å ha utfylt metadata i leiligheter tabellen. Da vil dette kunne regnes ut ved en query basert på hvilke etasjer som er lagt inn i leiligheter tabellen.

Primærnøkkelen 'id' i andelseiere tabellen, vil også kunne brukes for å rangere ansinitet. Akuratt slik det gjøres med medlemsnr i obos i dag.
## Innledende oppgave b: Entitetsintegritet (primærnøkkel)

For hver tabell du satte opp i oppgave a skal du nå finne mulige _kandidatnøkler_. For hver kandidatnøkkel bør du begrunne hvorfor du mener at dette er en nøkkel. Velg deretter en passende _primærnøkkel_ for hver tabell.
#### Kandidatnøkler

```sql
Kandidatnøkkel markeres med 👨‍💻
Om flere merkes i samme tabell er disse i kombinasjon en nøkkel.

borettslag(👨‍💻navn, 👨‍💻adresse, etablert) 
bygninger(👨‍💻id, adresse)
leiligheter(👨‍💻id, ant_rom, kvm, etasje)
andelseiere(👨‍💻id, fornavn, etternavn, telefon)
```
#### Primærnøkkel

```sql
Primærnøkkel markeres med 🔑

borettslag(🔑navn, adresse, etablert) 
bygninger(🔑id, adresse)
leiligheter(🔑id, ant_rom, kvm, etasje)
andelseiere(🔑id, fornavn, etternavn, telefon)
```
# Innledende oppgave c: Referanseintegritet (fremmednøkkel)

```sql
Primærnøkkel markeres med 🔑
Fremmednøkkel markeres med 🔐

borettslag(🔑navn, adresse, etablert) 
bygninger(🔑id, adresse, 🔐fk_borettslag_navn)
leiligheter(🔑id, ant_rom, kvm, etasje, 🔐fk_bygning_id,🔐fk_andelseier_id)
andelseiere(🔑id, fornavn, etternavn, telefon)
```
- Borettslag = 1:n forhold til bygninger
- Bygninger = 1:n forhold til leiligheter
- Leilighet = **1:0 or 1** (en til null eller en) forhold til andelseier
# Oppgave som skal vises fram for godkjenning
## a) Vis tabellene (relasjonene) du har kommet fram til i de innledende oppgavene.
  
  ![[Pasted_image_20220905214153_1666124095242_0.png]]
## b) Opprett CREATE TABLE -setninger i din egen database i MySQL der du definerer primær- og fremmednøkler slik du er kommet fram til i de innledende oppgavene.

```sql
CREATE SCHEMA ovning1;

CREATE  TABLE ovning1.andelseiere ( 
	id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	fornavn              VARCHAR(60)  NOT NULL    ,
	etternavn            VARCHAR(60)  NOT NULL    ,
	telefon              INT  NOT NULL    
 ) engine=InnoDB;

CREATE  TABLE ovning1.borettslag ( 
	navn                 VARCHAR(60)  NOT NULL    PRIMARY KEY,
	adresse              VARCHAR(60)  NOT NULL    ,
	etablert             INT  NOT NULL    
 ) engine=InnoDB;

CREATE  TABLE ovning1.bygninger ( 
	id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	adresse              VARCHAR(60)  NOT NULL    ,
	fk_borettslag_navn   VARCHAR(60)  NOT NULL    
 ) engine=InnoDB;

CREATE  TABLE ovning1.leiligheter ( 
	id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	ant_rom              INT  NOT NULL    ,
	kvm                  INT  NOT NULL    ,
	etasje               INT  NOT NULL    ,
	fk_bygning_id        INT  NOT NULL    ,
	fk_andelseier_id     INT      ,
	CONSTRAINT unq_leiligheter_fk_andelseier_id UNIQUE ( fk_andelseier_id ) 
 ) engine=InnoDB;

ALTER TABLE ovning1.bygninger ADD CONSTRAINT fk_bygninger_borettslag FOREIGN KEY ( fk_borettslag_navn ) REFERENCES ovning1.borettslag( navn ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE ovning1.leiligheter ADD CONSTRAINT fk_leiligheter_bygninger FOREIGN KEY ( fk_bygning_id ) REFERENCES ovning1.bygninger( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE ovning1.leiligheter ADD CONSTRAINT fk_leiligheter_andelseiere FOREIGN KEY ( fk_andelseier_id ) REFERENCES ovning1.andelseiere( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
```
## c) Lag INSERT-setninger der du legger inn noen gyldige (test-)data i tabellene. Lag også INSERT-setninger som gir brudd på entitets- og referanseintegritetsreglene.

```sql
INSERT INTO ovning1.andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Stian', 'Lyng', 92055335);
INSERT INTO ovning1.andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Ola', 'Normann', 12345567);
INSERT INTO ovning1.andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Petter', 'Stordalen', 12312312);
INSERT INTO ovning1.andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Odd', 'Nordstogga', 12312312);
INSERT INTO ovning1.borettslag( navn, adresse, etablert ) VALUES ( 'Elva', 'elveveien', 1993);
INSERT INTO ovning1.borettslag( navn, adresse, etablert ) VALUES ( 'Kanalen', 'kanaveien', 2003);
INSERT INTO ovning1.bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien1', 'Elva');
INSERT INTO ovning1.bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien2', 'Elva');
INSERT INTO ovning1.bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien3', 'Elva');
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 4, 55, 1, 1, 1);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 5, 66, 2, 1, 2);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 44, 2, 1, 3);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 5, 66, 4, 2, 4);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 14, 200, 5, 2, null);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 44, 5, 2, null);
INSERT INTO ovning1.leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 55, 6, 3, null);
```
### Eksempel på brudd på enititesregler
Brudd da det ikke finnes noen borettslag med navn Fisken
```sql
INSERT INTO ovning1.bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Fiskeveien', 'Fisken');
```
## d) Fra forelesningene har vi at en fremmednøkkel kan være NULL. Er det fornuftig å tillate dette for de fremmednøklene du har kommet fram til her? Kan primærnøkler være NULL?

Ikke fornuftig på noen andre enn fk_andelseier_id som følge av en leilighet kan eksistere uten en andelseier
## Når vi skal implementere referanseintegriteten (fremmednøkkel) er det mulig å sette ON DELETE/UPDATE CASCADE (se under for nærmere forklaring). Vurder konsekvensen av dette for hver enkelt fremmednøkkel.
#### fk_borettslag_navn
- Dersom et borettslag fjernes vil alle bygninger fjernes
- Om dette er ønskelig kommer ann på
#### fk_bygning_id
- Dersom en bygning fjernes vil alle leiligheter også fjernes
- Om dette er ønskelig kommer ann på
#### fk_andelseier_id
- Dersom en andelseier fjernes vil også leiligheten bli fjernet
- Ikke bra dersom andeiseiere skal være i et register, med ansinitet og medlemsnummer.
# Relevante queries
## Se alle andelseiere
  
  ```sql
  SELECT a.id, a.fornavn, a.etternavn, a.telefon, b.fk_borettslag_navn
  FROM ovning1.andelseiere a 
	  INNER JOIN ovning1.leiligheter l ON ( a.id = l.fk_andelseier_id  )  
	  INNER JOIN ovning1.bygninger b ON ( l.fk_bygning_id = b.id  )  
	  INNER JOIN ovning1.borettslag b1 ON ( b.fk_borettslag_navn = b1.navn  )  
  ```
  
  ![[Pasted_image_20220906120930_1666124142181_0.png]]
# Antall bygninger pr borettslag
  
  ```sql
  SELECT b.navn, b.adresse, b.etablert, count(*)
  FROM ovning1.borettslag b 
	  INNER JOIN ovning1.bygninger b1 ON ( b.navn = b1.fk_borettslag_navn  )  
  GROUP BY b.navn, b.adresse, b.etablert
  ```
  
  ![[Pasted_image_20220906121529_1666124156952_0.png]]
# Komplett SQL dump

```sql
CREATE  TABLE andelseiere ( 

id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,

fornavn              VARCHAR(60)  NOT NULL    ,

etternavn            VARCHAR(60)  NOT NULL    ,

telefon              INT  NOT NULL    

 ) engine=InnoDB;



CREATE  TABLE borettslag ( 

navn                 VARCHAR(60)  NOT NULL    PRIMARY KEY,

adresse              VARCHAR(60)  NOT NULL    ,

etablert             INT  NOT NULL    

 ) engine=InnoDB;



CREATE  TABLE bygninger ( 

id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,

adresse              VARCHAR(60)  NOT NULL    ,

fk_borettslag_navn   VARCHAR(60)  NOT NULL    

 ) engine=InnoDB;



CREATE  TABLE leiligheter ( 

id                   INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,

ant_rom              INT  NOT NULL    ,

kvm                  INT  NOT NULL    ,

etasje               INT  NOT NULL    ,

fk_bygning_id        INT  NOT NULL    ,

fk_andelseier_id     INT      ,

CONSTRAINT unq_leiligheter_fk_andelseier_id UNIQUE ( fk_andelseier_id ) 

 ) engine=InnoDB;



ALTER TABLE bygninger ADD CONSTRAINT fk_bygninger_borettslag FOREIGN KEY ( fk_borettslag_navn ) REFERENCES borettslag( navn ) ON DELETE NO ACTION ON UPDATE NO ACTION;



ALTER TABLE leiligheter ADD CONSTRAINT fk_leiligheter_bygninger FOREIGN KEY ( fk_bygning_id ) REFERENCES bygninger( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;



ALTER TABLE leiligheter ADD CONSTRAINT fk_leiligheter_andelseiere FOREIGN KEY ( fk_andelseier_id ) REFERENCES andelseiere( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;



INSERT INTO andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Stian', 'Lyng', 92055335);

INSERT INTO andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Ola', 'Normann', 12345567);

INSERT INTO andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Petter', 'Stordalen', 12312312);

INSERT INTO andelseiere( fornavn, etternavn, telefon ) VALUES ( 'Odd', 'Nordstogga', 12312312);

INSERT INTO borettslag( navn, adresse, etablert ) VALUES ( 'Elva', 'elveveien', 1993);

INSERT INTO borettslag( navn, adresse, etablert ) VALUES ( 'Kanalen', 'kanaveien', 2003);

INSERT INTO bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien1', 'Elva');

INSERT INTO bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien2', 'Elva');

INSERT INTO bygninger( adresse, fk_borettslag_navn ) VALUES ( 'Eleveveien3', 'Elva');

INSERT INTO bygninger( adresse, fk_borettslag_navn ) VALUES ( 'fiskebakken', 'Kanalen');

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 4, 55, 1, 1, 1);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 5, 66, 2, 1, 2);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 44, 2, 1, 3);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 5, 66, 4, 2, 4);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 14, 200, 5, 2, null);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 44, 5, 2, null);

INSERT INTO leiligheter( ant_rom, kvm, etasje, fk_bygning_id, fk_andelseier_id ) VALUES ( 3, 55, 6, 3, null);
```