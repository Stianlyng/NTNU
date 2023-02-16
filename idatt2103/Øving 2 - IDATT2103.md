>[!INFO]
>Tabellene og data som du skal bruke i oppgavene kan du hente fra scriptet som ligger ute i Blackboard. Kjør skriptet. Merk at du vil få feilmeldinger første gangen pga DROP-setningene i begynnelsen av scriptet.
>
>Skriptet består av følgende relasjoner (tabeller) hvor primærnøkler er understreket og fremmednøklene markert med en stjerne.
>
>KONSULENT(kons_id, fornavn, etternavn, epost)  
>BOK(bok_id, tittel, utgitt_aar, forlag_id*)  
>FORLAG(forlag_id, forlag_navn, adresse, telefon)  
>FORFATTER(forfatter_id, fornavn, etternavn, fode_aar, dod_aar, nasjonalitet)  
>BOK_FORFATTER(bok_id*, forfatter_id*)
>
>Tegn gjerne et diagram som viser sammenhengen mellom de ulike tabellene. Konsulentene er uavhengig av både forlag, forfattere og bøker.
# Diagram
  
  ![[Pasted_image_20220908131618_1666124254023_0.png]] 
  
  
  Hensikten med oppgavene er å bli kjent med de ulike operasjonene i relasjonsalgebraen. Operasjonene utføres ved å kjøre SQL-spørringer. Relasjonsalgebra er tema side 59-76 i læreboka. Boka viser imidlertid ikke tilhørende eksempler på SELECT SQL-spørringer før i kap. 4.3.2. Utdelt foilsett viser en mere direkte kobling mellom rel.alg. og SQL.
  
  Det er ikke meningen at du skal sette opp spørringene i algebra, du skal kun forholde deg til navnene på de ulike operasjonene. Spørringene skal, som sagt, settes opp i SQL.
# Oppgave 1
## a) Lag en SQL-spørring som utfører operasjonene seleksjon og projeksjon på tabellen Bok.


```sql
SELECT DISTINCT b.utgitt_aar
FROM ovning2.bok b 
WHERE b.utgitt_aar>1994 
```
## b) Lag en SQL-spørring som utfører operasjonen _produkt_ på tabellene Forlag og Bok.
- **Beskriv resultatet med egne ord.**
  
  ```sql
  SELECT *
  FROM ovning2.bok 
  CROSS JOIN ovning2.forlag
  ```
- Produserer er resultat hvor antall rader i den første tabellen multipliseres med antallet rader i den andre tabellen.
- Resulterer i et kartesisk produkt
## c) Lag SQL-spørringer som utfører operasjonene likhetsforening (equijoin) og naturlig forening (natural join) på tabellene Forlag og Bok.
- **Hva forteller resultatet?**
  
  ```sql
  SELECT * FROM ovning2.forlag, ovning2.bok WHERE ovning2.forlag.forlag_id = ovning2.bok.forlag_id;
  ```
## d) Finn eksempler på attributter eller kombinasjoner av attributter som er unionkompatible.
- Hvilke relasjonsoperasjoner krever at operandene er unionkompatible?
- Sett opp SQL-spørringer som utfører disse operasjonene, et eksempel på hver.
- Beskriv med egne ord hva spørringene gir deg svaret på.
### Union
Unionkompatiblel = like attributter

Hadde man eksempelvis hatt forskjellige select statements på de to tabellene hadde det ikke fungert.

```sql
SELECT f.fornavn, f.etternavn
FROM ovning2.forfatter f 
UNION 
SELECT k.fornavn, k.etternavn
FROM ovning2.konsulent k
```
### Snitt
- Henter ut tittel, ugitt_år fra bok tabell
- Henter ut forfatter_id fra forfatter_id
  
  ```sql
  SELECT b.tittel, b.utgitt_aar, bf.forfatter_id
  FROM ovning2.bok b 
	  INNER JOIN ovning2.bok_forfatter bf ON ( b.bok_id = bf.bok_id  )    
  ```
# Oppgave 2
## a) Bruk SQL til å finne navnene til alle forlagene. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?

```sql
SELECT f.forlag_navn
FROM ovning2.forlag f 
```
## b) Bruk SQL til å finne eventuelle forlag (forlag_id er nok) som ikke har gitt ut bøker. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?

```sql
SELECT f.forlag_id, f.forlag_navn
FROM ovning2.forlag f 
	LEFT OUTER JOIN ovning2.bok b ON ( f.forlag_id = b.forlag_id  )  
WHERE b.bok_id is null
GROUP BY f.forlag_id, f.forlag_navn
```
## c) Bruk SQL til å finne forfattere som er født i 1948. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?

```sql
SELECT f.fornavn, f.etternavn, f.fode_aar
FROM ovning2.forfatter f 
WHERE f.fode_aar = 1948
```

Jeg brukte seleksjon
## d) Bruk SQL til å finne navn og adresse til forlaget som har gitt ut boka 'Generation X'. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?

```sql
SELECT f.forlag_id, f.forlag_navn, b.tittel
FROM ovning2.forlag f 
	INNER JOIN ovning2.bok b ON ( f.forlag_id = b.forlag_id  )  
WHERE b.tittel = 'Generation X'
GROUP BY f.forlag_id, f.forlag_navn, b.tittel
```

Jeg brukte indre forening og seleksjon
## e) Bruk SQL til å finne titlene på bøkene som Hamsun har skrevet. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?

```sql
SELECT b.tittel, f1.etternavn
FROM ovning2.bok b 
	INNER JOIN ovning2.bok_forfatter bf ON ( b.bok_id = bf.bok_id  )  
	INNER JOIN ovning2.forfatter f1 ON ( bf.forfatter_id = f1.forfatter_id  )  
WHERE f1.etternavn = 'Hamsund'
GROUP BY b.tittel, f1.etternavn
```

Jeg brukte: differanse
## f) Bruk SQL til å finne informasjon om bøker og forlagene som har utgitt dem.
- Én linje i oversikten skal inneholde bokas tittel og utgivelsesår, samt forlagets navn, adresse og telefonnummer.
- Forlag som ikke har gitt ut noen bøker skal også med i listen.
- Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du?
  
  ```sql
  SELECT f.forlag_navn, f.adresse, f.telefon, b1.tittel, b1.utgitt_aar, b1.forlag_id
  FROM ovning2.forlag f 
	  LEFT OUTER JOIN ovning2.bok b1 ON ( f.forlag_id = b1.forlag_id  )  
  ```
  Jeg brukte ytre forening
# SQL  Script for oppsett av db

```SQL
/*
** bok-script-mysql.txt
/*
** DROP TABLE-setninger som sletter gamle tabeller
*/

DROP TABLE IF EXISTS bok_forfatter;
DROP TABLE IF EXISTS forfatter;
DROP TABLE IF EXISTS bok;
DROP TABLE IF EXISTS forlag;
DROP TABLE IF EXISTS konsulent; 

/*
** Oppretter tabeller med entitetsintegritet
*/
CREATE TABLE forlag
(
forlag_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
forlag_navn VARCHAR(30),
adresse VARCHAR(30),
telefon CHAR(15),
PRIMARY KEY(forlag_id)
)ENGINE=INNODB;

CREATE TABLE bok
(
bok_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
tittel VARCHAR(30),
utgitt_aar INT,
forlag_id INT UNSIGNED,
PRIMARY KEY(bok_id)
)ENGINE=INNODB;

CREATE TABLE forfatter
(
forfatter_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
fornavn VARCHAR(20),
etternavn VARCHAR(30),
fode_aar INT,
dod_aar INT,
nasjonalitet VARCHAR(20),
PRIMARY KEY(forfatter_id)
)ENGINE=INNODB;

CREATE TABLE bok_forfatter
(
bok_id INT UNSIGNED NOT NULL,
forfatter_id INT UNSIGNED NOT NULL,
PRIMARY KEY(bok_id, forfatter_id)
)ENGINE=INNODB;

CREATE TABLE konsulent
(
kons_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
fornavn VARCHAR(20),
etternavn VARCHAR(30),
epost VARCHAR(30),
PRIMARY KEY(kons_id)
)ENGINE=INNODB;

/*
** Legger på referanseintegritet (fremmednøkler)
*/
ALTER TABLE bok
 ADD FOREIGN KEY(forlag_id)REFERENCES forlag(forlag_id);

ALTER TABLE bok_forfatter
 ADD FOREIGN KEY(bok_id)REFERENCES bok(bok_id);

ALTER TABLE bok_forfatter
 ADD FOREIGN KEY(forfatter_id)REFERENCES forfatter(forfatter_id);


/*
** Legger inn gyldige data i tabellene
*/
INSERT INTO forlag VALUES(NULL,'Tapir','Trondheim','73590000');
INSERT INTO forlag VALUES(NULL, 'Gyldendal','Oslo','22220000');
INSERT INTO forlag VALUES(NULL, 'Cappelen','Oslo','22200000');
INSERT INTO forlag VALUES(NULL, 'Universitetsforlaget','Oslo','23230000');
INSERT INTO forlag VALUES(NULL, 'Aschehaug','Oslo','22000000');
INSERT INTO forlag VALUES(NULL, 'Oktober','Oslo','22002200');
INSERT INTO forlag VALUES(NULL, 'Tiden','Oslo','22232223');
INSERT INTO forlag VALUES(NULL, 'Harper Collins','USA',NULL);

INSERT INTO bok VALUES(NULL,'Tåpenes',1995,7);
INSERT INTO bok VALUES(NULL,'Rebecca',1981,3);
INSERT INTO bok VALUES(NULL,'Gutter er gutter',1995,5);
INSERT INTO bok VALUES(NULL,'Microserfs',1991,8);
INSERT INTO bok VALUES(NULL,'Generation X',1995,8);
INSERT INTO bok VALUES(NULL,'Klosterkrønike',1982,3);
INSERT INTO bok VALUES(NULL,'Universet',1988,3);
INSERT INTO bok VALUES(NULL,'Nålen',1978,3);
INSERT INTO bok VALUES(NULL,'Markens grøde',1917,2);
INSERT INTO bok VALUES(NULL,'Victoria',1898,2);
INSERT INTO bok VALUES(NULL,'Sult',1890,2);
INSERT INTO bok VALUES(NULL,'Benoni',1908,2);
INSERT INTO bok VALUES(NULL,'Rosa',1908,2);
INSERT INTO bok VALUES(NULL,'Et skritt',1997,2);
INSERT INTO bok VALUES(NULL,'Den femte',1996,2);
INSERT INTO bok VALUES(NULL,'Villspor',1995,2);
INSERT INTO bok VALUES(NULL,'Silkeridderen',1994,2);
INSERT INTO bok VALUES(NULL,'Den hvite hingsten',1992,2);
INSERT INTO bok VALUES(NULL,'Hunder',1992,2);
INSERT INTO bok VALUES(NULL,'Bridget Jones',1995,5);
INSERT INTO bok VALUES(NULL,'Se terapeuten',1998,3);
INSERT INTO bok VALUES(NULL,'Sa mor',1996,3);
INSERT INTO bok VALUES(NULL,'Jubel',1995,3);
INSERT INTO bok VALUES(NULL,'Tatt av kvinnen',1993,3);
INSERT INTO bok VALUES(NULL,'Supernaiv',1996,3);

INSERT INTO forfatter VALUES(NULL, 'John','Tool',1937, 1969, 'USA');
INSERT INTO forfatter VALUES(NULL,'Ken','Follet',NULL, NULL, 'Britisk');
INSERT INTO forfatter VALUES(NULL,'Stephen','Hawking',NULL, NULL, 'Britisk');
INSERT INTO forfatter VALUES(NULL,'Jose','Saramago',1942, NULL, 'Portugisisk');
INSERT INTO forfatter VALUES(NULL,'Douglas','Coupland',1961, NULL, 'Canadisk');
INSERT INTO forfatter VALUES(NULL,'Nick','Hornby',1857, NULL, 'Britisk');
INSERT INTO forfatter VALUES(NULL,'Knut','Hamsund',1859, 1952, 'Norsk');
INSERT INTO forfatter VALUES(NULL,'Henning','Mankell',1948, NULL, 'Svensk');
INSERT INTO forfatter VALUES(NULL,'Helen','Fielding',NULL, NULL, 'Britisk');
INSERT INTO forfatter VALUES(NULL,'Hal','Sirowitz',NULL, NULL, 'USA');
INSERT INTO forfatter VALUES(NULL,'Lars S.','Christensen',NULL, NULL, 'Norsk');
INSERT INTO forfatter VALUES(NULL,'Erlend','Loe',NULL, NULL, 'Norsk');

INSERT INTO bok_forfatter VALUES(1, 1);
INSERT INTO bok_forfatter VALUES(2, 2);
INSERT INTO bok_forfatter VALUES(3, 6);
INSERT INTO bok_forfatter VALUES(4, 5);
INSERT INTO bok_forfatter VALUES(5, 5);
INSERT INTO bok_forfatter VALUES(6, 4);
INSERT INTO bok_forfatter VALUES(7, 3);
INSERT INTO bok_forfatter VALUES(8, 2);
INSERT INTO bok_forfatter VALUES(9, 7);
INSERT INTO bok_forfatter VALUES(10, 7);
INSERT INTO bok_forfatter VALUES(11, 7);
INSERT INTO bok_forfatter VALUES(12, 1);
INSERT INTO bok_forfatter VALUES(13, 1);
INSERT INTO bok_forfatter VALUES(14, 8);
INSERT INTO bok_forfatter VALUES(15, 8);
INSERT INTO bok_forfatter VALUES(16, 8);
INSERT INTO bok_forfatter VALUES(17, 8);
INSERT INTO bok_forfatter VALUES(18, 8);
INSERT INTO bok_forfatter VALUES(19, 8);
INSERT INTO bok_forfatter VALUES(20, 9);
INSERT INTO bok_forfatter VALUES(21, 10);
INSERT INTO bok_forfatter VALUES(22, 10);
INSERT INTO bok_forfatter VALUES(23, 11);
INSERT INTO bok_forfatter VALUES(24, 12);
INSERT INTO bok_forfatter VALUES(25, 12);

INSERT INTO konsulent VALUES(NULL, 'Anne', 'Hansen', 'anne.hansen@xxx.com');
INSERT INTO konsulent VALUES(NULL, 'Bjørn', 'Jensen', 'bjornj@yyy.com');
INSERT INTO konsulent VALUES(NULL,'Anne', 'Ås', 'anne.as@zzz.com');

/*
** Avslutter transaksjonen og lagrer tabellene og data fysisk i databasen
*/

COMMIT;
```