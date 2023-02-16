En produsent av stoler ønsker seg et ordre- og produksjonssystem for å administrere bestillinger av ulike stoler fra kunder, samt å overvåke produksjonen av stolene.

### ER
![[EERDiagram.drawio.png]]

### Relasjon

>**kunde**(==kundenr==, navn, adresse)
>**ordre**(==ordrenr==, fk_kundenr*, ordredato, est_levering, betalingsstatus, reell_levering, rabatt)
>**bestilling**(==bestilling_id==, fk_ordrenr*, fk_stolnr*, antall)
>**stol**(==stolnr==, fk_stoltype_id*, pris)iu
>**stoltype**(==stoltype_id==, navn)
>**standardstol**(fk_stolnr*, antall)
>**spesialstol**(fk_stolnr*)
>**spesialstol_del**(fk_stolnr*, fk_delenr*, antall)
>**del**(delenr, arbeidsstasjon_id*, navn, beskrivelse, farge, pris, antall, stoff_id*, stoffbehov)
>**arbeidsstasjon**(arbeidsstasjon_id, plassering)
>**stoff**(fk_delenr*)
>**rull**(rull_id, fk_delenr*, rest_meter)

### Litt bakgrunn
- **ordre**(==ordrenr==, fk_kundenr*, ordredato, est_levering, betalingsstatus, reell_levering, rabatt)
	- Består av flere bestillinger 
		- **bestilling**(==bestilling_id==, fk_ordrenr*, fk_stolnr*, antall)
			- Består av standardstoler og spesialstoler av ulike typer, der det er et antall pr. type.
				- **stol**(==stolnr==, fk_stoltype_id*, pris)
					- **stoltype**(==stoltype_id==, navn)
					- **standardstol**(fk_stolnr*, antall)
					- **spesialstol**(fk_stolnr*)
						- En spesialstol består av flere deler.
							- **spesialstol_del**(fk_stolnr*, fk_delenr*, antall)
								- **del**(delenr, arbeidsstasjon_id*, navn, beskrivelse, farge, pris, antall, stoff_id*, stoffbehov)
									- **arbeidsstasjon**(arbeidsstasjon_id, plassering)
									- **stoff**(fk_delenr*)
										- **rull**(rull_id, fk_delenr*, rest_meter)

## SELECT-setninger

1. Finn hvor mange (antallet) stolmodeller som finnes av hver stoltype.
   ```sql
   SELECT navn, COUNT(*) as "Antall"
    FROM stoltype
    LEFT JOIN stol ON stol.fk_stoltype_id = stoltype.stoltype_id;
   ```

2. Ut fra alle registrerte ordre (bestillinger): Finn gjennomsnittlig antall bestilte stoler av hver stoltype.
   ```sql
   SELECT navn, AVG(bestilling.antall) AS "Snitt"
    FROM stoltype
    LEFT JOIN stol ON stol.fk_stoltype_id = stoltype.stoltype_id
    JOIN bestilling ON stol.fk_stolnr = bestilling.fk_stolnr
    GROUP BY stoltype.navn;
   ```
   
3. Finn det totale antallet stoler som finnes i bestilling, og som enda ikke er levert kunder. Tips: Sjekk på reell leveringsdato (dvs. om ordren er effektuert).
   ```sql
   SELECT sum(bestilling.antall)
    FROM bestilling
    JOIN ordre ON bestilling.fk_ordrenr = ordre.ordrenr AND ordre.reell_levering IS NULL;
   ```
   
4. Finn ut hvor mange (antallet) av stolene i spørring 3 over som er standardstoler.
   ```sql
   SELECT sum(bestilling.antall)
    FROM bestilling
    JOIN standardstol ON bestilling.fk_stolnr = standardstol.fk_stolnr
    JOIN ordre ON bestilling.fk_ordrenr = ordre.ordrenr AND ordre.reell_levering IS NULL;
   ```