## Oppgave 1 SQL inkl. VIEW  
Bruk dette scriptet levInfo_mysql.txt for å opprette tabeller med eksempeldata. Gjør deg kjent med databasen. Merk at vi ikke har NULL-verdier i denne databasen.
### a. List ut all informasjon (ordrehode og ordredetalj) om ordrer for leverandør nr 44.
  
  ![[Pasted_image_20221004110529_1666124680705_0.png]] 
  
  ```sql
  SELECT o.ordrenr, o.delnr, o.kvantum, o1.ordrenr, o1.dato, o1.levnr, o1.`status`
  FROM ovning4.ordredetalj o 
	  INNER JOIN ovning4.ordrehode o1 ON ( o.ordrenr = o1.ordrenr  )  
  WHERE o1.levnr = 44 AND
	  o1.ordrenr = o.ordrenr
  ```
### b. Finn navn og by ("LevBy") for leverandører som kan levere del nummer 1.
  ![[Pasted_image_20221004110827_1666124704228_0.png]] 
  ```sql
  SELECT l.navn, l.levby
  FROM ovning4.prisinfo p 
	  INNER JOIN ovning4.levinfo l ON ( p.levnr = l.levnr  )  
  WHERE p.delnr = 1
  ```
### c. Finn nummer, navn og pris for den leverandør som kan levere del nummer 201 til billigst pris.
  ![[Pasted_image_20221004113055_1666124716679_0.png]] 
  ```sql
  SELECT l.levnr, l.navn, p.pris 
  FROM ovning4.levinfo l, ovning4.prisinfo p
  WHERE l.levnr = p.levnr AND p.delnr = 201
  AND p.pris   = (SELECT MIN(pris)
			    FROM prisinfo
			    WHERE delnr = 201);
  ```
### d. Lag fullstendig oversikt over ordre nr 16, med ordrenr, dato, delnr, beskrivelse, kvantum, (enhets-)pris og beregnet beløp (=pris*kvantum).
  ![[Pasted_image_20221004122141_1666124730916_0.png]] 
  
  ```sql
  SELECT ordrehode.ordrenr, ordrehode.dato, ordredetalj.delnr, delinfo.beskrivelse, ordredetalj.kvantum, prisinfo.pris, prisinfo.pris*ordredetalj.kvantum  
  FROM ordrehode, ordredetalj, delinfo, prisinfo
  WHERE ordrehode.ordrenr = ordredetalj.ordrenr AND
  ordredetalj.delnr = prisinfo. delnr AND
  prisinfo.levnr = ordrehode.levnr AND
  ordredetalj.delnr = delinfo.delnr AND
  ordrehode.ordrenr = 16;
  ```
### e. Finn delnummer og leverandørnummer for deler som har en pris som er høyere enn  prisen for del med katalognr X7770.
  ![[Pasted_image_20221004122952_1666124765369_0.png]] 
  
  ```sql
  SELECT delnr, levnr
  FROM prisinfo
WHERE pris > (
  SELECT pris
  FROM prisinfo
  WHERE katalognr = 'X7770')
  ```
### f. i) Tenk deg at tabellen levinfo skal deles i to. Sammenhengen mellom by og fylke skal  tas ut av tabellen. Det er unødvendig å lagre fylkestilhørigheten for hver forekomst av by. Lag én ny tabell som inneholder byer og fylker. Fyll denne med data fra levinfo.  Lag også en tabell som er lik levinfo unntatt kolonnen Fylke. (Denne splittingen av  tabellen levinfo gjelder bare i denne oppgaven. I resten av oppgavesettet antar du at du  har den opprinnelige levinfo-tabellen.)
#### nb: klarte å miste queryen, så jeg tok en sql dump
```sql
CREATE  TABLE opgf.lokasjon ( 
	byen                 VARCHAR(20)  NOT NULL    PRIMARY KEY,
	fylke                VARCHAR(20)      
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE  TABLE opgf.levinfony ( 
	levnr                INT  NOT NULL    PRIMARY KEY,
	navn                 VARCHAR(20)  NOT NULL    ,
	adresse              VARCHAR(20)  NOT NULL    ,
	levby                VARCHAR(20)  NOT NULL    ,
	postnr               INT  NOT NULL    
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE INDEX fk_levinfo_lokasjon ON opgf.levinfony ( levby ) (`levby`);

ALTER TABLE opgf.levinfony ADD CONSTRAINT fk_levinfo_lokasjon FOREIGN KEY ( levby ) REFERENCES opgf.lokasjon( byen ) ON DELETE NO ACTION ON UPDATE NO ACTION;
```
#### Flytte data fra levinfo
  
  ![[Pasted_image_20221004141344_1666124783906_0.png]] 
  
  ```sql
  INSERT INTO levinfony (levnr, navn, adresse, levby, postnr)
  SELECT levnr, navn, adresse, levby, postnr
  FROM levinfo;
  
  select * from levinfony;
  ```
#### ii) Lag en virtuell tabell (view) slik at brukerne i størst mulig grad kan jobbe på  samme måte mot de to nye tabellene som den gamle. Prøv ulike kommandoer mot  tabellen (select, update, delete, insert). Hvilke begrensninger, hvis noen, har brukerne i  forhold til tidligere?
- å oppdatere tabellen fylke vil skape flere problemer, siden den nye levinfo tabellen kan ha flere verdier av fylke men ikke motsatt.
- ![[Pasted_image_20221004145129_1666124829977_0.png]] 
  
  ```sql
  CREATE VIEW virituellview
  AS
  SELECT l.levnr,l.navn,l.adresse,l.levby,b.fylke,l.postnr FROM levinfony l, lokasjon b
  WHERE l.levby = b.byen;
  ```
### g. Anta at en vurderer å slette opplysningene om de leverandørene som ikke er representert i Prisinfo-tabellen. Finn ut hvilke byer en i tilfelle ikke får leverandør i.  (Du skal ikke utføre slettingen.) (Tips: Svaret skal bli kun én by, "Ål".)
  
  ![[Pasted_image_20221004153537_1666124877538_0.png]] 
  
  ```sql
  SELECT DISTINCT l.levby FROM levinfo l WHERE l.levby NOT IN (
  SELECT l1.levby FROM prisinfo p, levinfo l1 WHERE p.levnr = l1.levnr);
  ```
### h. Finn leverandørnummer for den leverandør som kan levere ordre nr 18 til lavest totale beløp (vanskelig).
  ```sql
  CREATE VIEW opgf.view2 AS 
	  select prisinfo.levnr 
  AS del_nr,prisinfo.pris 
  AS pris,(prisinfo.pris * ordredetalj.kvantum) 
  AS total_pris 
  from prisinfo 
	  join ordredetalj 
	  where ((prisinfo.delnr = ordredetalj.delnr) 
	  and (ordredetalj.ordrenr = 18));
  ```
  
  ![[Pasted_image_20221004152150_1666124895703_0.png]] 
  
  ```sql
  select * from view2;
  ```
## Oppgave 2 SQL med NULL-verdier
### a) Sett opp en SELECT-setning som er UNION mellom alle forlag med Oslo-nummer  (telefonnummer begynner med 2) og alle som ikke er Oslo-nummer. Får du med  forlaget med NULL-verdi på telefonnummer? Hvis ikke, utvid unionen med en mengde til.
```sql
SELECT f.forlag_id, f.forlag_navn, f.adresse, f.telefon
FROM ovning4oppg2.forlag f 
WHERE f.telefon like '2%' AND
	f.telefon not like '%2'
```
### b. Sett opp SQL-setninger som finner gjennomsnittlig alder på forfattere der fødselsåret  er oppgitt. For forfattere der dødsåret ikke er oppgitt, skal du kun ta med de som er  født etter 1900. Tips for å få ut året i år:
- MySQL: SELECT YEAR(CURRENT_DATE) FROM ... hvilken tabell som helst ...
#### lage en view
```sql
create view aarogdato as
SELECT f.forfatter_id, f.fode_aar, year(current_date)
FROM ovning4oppg2.forfatter f 
WHERE f.fode_aar>1900 AND
	f.dod_aar is null
union
SELECT forfatter_id, fode_aar, dod_aar AS til_aar FROM forfatter WHERE fode_aar IS NOT NULL AND dod_aar IS NOT NULL;
```
#### finne avg
  
  ![[Pasted_image_20221004124814_1666124911410_0.png]] 
  
  ```sql
  SELECT a.forfatter_id, avg(a.`year(current_date)` -  a.fode_aar)
  FROM ovning4oppg2.aarogdato a 
  GROUP BY a.forfatter_id, a.fode_aar, a.`year(current_date)`
  ```
### c. Sett opp SQL-setninger som finner hvor stor andel av forfatterne som ble med i  beregningene under b).
  
  ![[Pasted_image_20221004125237_1666124928124_0.png]] 
  
  ```sql
  SELECT COUNT(a.forfatter_id)*100 / COUNT(f.forfatter_id)
  FROM forfatter f LEFT JOIN aarogdato a ON (f.forfatter_id = a.forfatter_id);
  ```