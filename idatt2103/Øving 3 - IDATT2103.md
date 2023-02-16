- **Oppgave 1 SQL**
  
  **_Øving 3: SQL del 1 (obligatorisk)_**
  
  I denne oppgaven skal vi bruke en borettslag-databasen. Bruk følgende sql-script: byggOgBo_mysql.sql (tekstfil som finnes i BB). Scriptet inneholder data som oppgavene nedenfor spør etter.
  
  Sett opp SELECT-setninger som besvarer spørsmålene nedenfor. Kun én setning pr oppgave.
### 1. Finn alle borettslag etablert i årene 1975-1985.

```sql
SELECT b.bolag_navn, b.bolag_adr, b.etabl_aar, b.postnr
FROM ovning3.borettslag b 
WHERE b.etabl_aar between 1975 and 1985
```

| ab bolag_navn | ab bolag_adr | 12 etabl_aar | 12 postnr |
| :--- | :--- | ---: | ---: |
| Tertitten | Åsveien 100 | 1,980 | 7,020 |
### 2. Skriv ut en liste over andelseiere.
- Listen skal ha linjer som ser slik ut (tekster i kursiv er data fra databasen):"fornavn etternavn, ansiennitet: ansiennitet år".
- Listen skal være sortert på ansiennitet, de med lengst ansiennitet øverst.
  
  ```sql
  SELECT CONCAT(a.fornavn, ' ',  a.etternavn,  ', ansiennitet: ', a.ansiennitet)
  FROM ovning3.andelseier a 
  ORDER BY a.ansiennitet DESC
  ```
  
  ![[Pasted_image_20220927092040_1666124351573_0.png]]
### 3. I hvilket år ble det eldste borettslaget etablert?
  ```sql
  SELECT min(b.etabl_aar)
  FROM ovning3.borettslag b 
  ```
  ![[Pasted_image_20220927092432_1666124364318_0.png]]
### 4. Finn adressene til alle bygninger som inneholder leiligheter med minst tre rom.
  ```sql
  SELECT DISTINCT b.bygn_adr
  FROM ovning3.bygning b 
	  INNER JOIN ovning3.leilighet l ON ( b.bygn_id = l.bygn_id  )  
WHERE l.ant_rom>=3
  ```
  ![[Pasted_image_20220927093514_1666124378804_0.png]]
### 5. Finn antall bygninger i borettslaget "Tertitten".
  ```sql
  SELECT count(*)
  FROM ovning3.bygning b 
  WHERE b.bolag_navn = 'Tertitten'
  ```
  
  ![[Pasted_image_20220927094108_1666124397904_0.png]]
### 6. Lag en liste som viser antall bygninger i hvert enkelt borettslag.
- Listen skal være sortert på borettslagsnavn.
- Husk at det kan finnes borettslag uten bygninger - de skal også med.
  ```sql
  SELECT b.bolag_navn, count( distinct b1.bygn_id)
  FROM ovning3.borettslag b 
	  LEFT OUTER JOIN ovning3.bygning b1 ON ( b.bolag_navn = b1.bolag_navn  )  
  GROUP BY b.bolag_navn
  ```
  
  ![[Pasted_image_20220927094522_1666124410963_0.png]]
###  7. Finn antall leiligheter i borettslaget "Tertitten".
  
  ```sql
  SELECT count(*)
  FROM ovning3.leilighet l 
	  INNER JOIN ovning3.bygning b ON ( l.bygn_id = b.bygn_id  )  
  WHERE b.bolag_navn = 'Tertitten'
  ```
  
  ![[Pasted_image_20220927095006_1666124429436_0.png]]
### 8. Hvor høyt kan du bo i borettslaget "Tertitten"?
  ```sql
  SELECT max(l.etasje), b.bolag_navn
  FROM ovning3.leilighet l 
	  INNER JOIN ovning3.bygning b ON ( l.bygn_id = b.bygn_id  )  
  WHERE b.bolag_navn = 'Tertitten'
  GROUP BY b.bolag_navn
  ```
  ![[Pasted_image_20220927095149_1666124442074_0.png]]
### 9. Finn navn og nummer til andelseiere som ikke har leilighet.
  ```sql
  SELECT a.fornavn, a.etternavn, a.telefon
  FROM ovning3.andelseier a 
  WHERE  NOT EXISTS ( 
	  SELECT l.leil_nr
	  FROM ovning3.leilighet l 
	  WHERE a.and_eier_nr = l.and_eier_nr  ) 
  ```
  ![[Pasted_image_20220927095803_1666124451472_0.png]]
### 10. Finn antall andelseiere pr borettslag, sortert etter antallet. Husk at det kan finnes borettslag uten andelseiere - de skal også med.
  
  ```sql
  SELECT b.bolag_navn, count( distinct a.and_eier_nr) as "antall"
  FROM ovning3.borettslag b 
	  LEFT OUTER JOIN ovning3.andelseier a ON ( b.bolag_navn = a.bolag_navn  )  
  GROUP BY b.bolag_navn
  ORDER BY "antall" DESC;
  ```
  
  ![[Pasted_image_20220927102008_1666124466489_0.png]]
### 11. Skriv ut en liste over alle andelseiere. For de som har leilighet, skal leilighetsnummeret skrives ut.
  ```sql
  SELECT a.and_eier_nr, a.fornavn, a.etternavn, l.leil_nr
  FROM ovning3.andelseier a 
	  LEFT OUTER JOIN ovning3.leilighet l ON ( a.and_eier_nr = l.and_eier_nr  )  
  ```
  
  ![[Pasted_image_20220927102402_1666124479006_0.png]]
### 12. Hvilke borettslag har leiligheter med eksakt 4 rom?
  
  ```sql
  select count(bolag_navn)
  from bygning, leilighet
  where leilighet.ant_rom = 4
  ```
  
  ![[Pasted_image_20220927103140_1666124494607_0.png]]
### 13. Skriv ut en liste over antall andelseiere pr postnr og poststed, begrenset til de som bor i leiligheter tilknyttet et borettslag.
- Husk at postnummeret til disse er postnummeret til bygningen de bor i, og ikke postnummeret til borettslaget.
- Du trenger ikke ta med poststeder med 0 andelseiere. 
  
  ```sql
  SELECT count(*), b.postnr, p.poststed
  FROM ovning3.leilighet l 
	  INNER JOIN ovning3.bygning b ON ( l.bygn_id = b.bygn_id  )  
	  INNER JOIN ovning3.poststed p ON ( b.postnr = p.postnr  )  
  GROUP BY b.postnr, p.poststed
  ```
  
  ![[Pasted_image_20220927105329_1666124514518_0.png]]
### (Ekstraoppgave: Hva hvis vi vil ha med poststeder med 0 andelseiere?)
  ```sql
  SELECT  p.postnr, p.poststed,count(l.and_eier_nr)
  FROM ovning3.leilighet l 
	  JOIN ovning3.bygning b ON ( l.bygn_id = b.bygn_id  )  
	  right JOIN ovning3.poststed p ON ( b.postnr = p.postnr  )  
  GROUP BY p.postnr, p.poststed;
  ```
  
  ![[Pasted_image_20220927111609_1666124524756_0.png]]