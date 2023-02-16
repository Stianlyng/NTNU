- ### Oppgave a)
	- **Lag en datamodell (ER-modell der dere bruker UML-notasjon).**
	- ![image.png](image_1666697227388_0.png)
- ### Oppgave b)
	- **Oversett til relasjonsmodellen. Strek under primærnøkler og marker fremmednøkler med stjerne.**
		- bedrift(==org_nr==, navn, telefon, epost)
		- oppdrag(==oppdrag_id==, est_startdato, est_sluttdato, est_timer, act_startdato, act_sluttdato, act_timer, sluttattest, fk_org_nr*, 
			fk_kandidat_id*, fk_kval_id*)
		- kandidat(==kandidat_id==, fornavn, etternavn, telefon, epost)
		- kvalifikasjon(==kvalifikasjon_id==, kvalifikasjon_beskrivelse)
		- kandidat_has_kvalifikasjon(==fk_kvalifikasjon_id*==, ==fk_kandidat_id*==)
	- **Er det rimelig at noen av fremmednøklene kan være NULL? Hva betyr det i tilfelle?**
		- Ja, mye informasjon i oppdrag som må oppdateres senere
- ### Oppgave c)
	- **Opprett databasetabellene med primær- og fremmednøkler i MySQL,  dvs. lag CREATE TABLE og evt. ALTER TABLE-setninger. (Prøv å) bruk datatypen DATE for dato.**
		- ```sql
		  DROP SCHEMA;
		  CREATE SCHEMA ovning6new;
		  
		  CREATE  TABLE ovning6new.bedrift ( 
			org_nr               CHAR(15)  NOT NULL    PRIMARY KEY,
			navn                 VARCHAR(20)      ,
			telefon              CHAR(12)      ,
			epost                VARCHAR(30)      
		   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
		  
		  CREATE  TABLE ovning6new.kandidat ( 
			kandidat_id          INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
			fornavn              VARCHAR(20)      ,
			etternavn            VARCHAR(20)      ,
			telefon              CHAR(12)      ,
			epost                VARCHAR(30)      
		   ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
		  
		  CREATE  TABLE ovning6new.kvalifikasjon ( 
			kvalifikasjon_id     INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
			kvalifikasjon_beskrivelse VARCHAR(30)      
		   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
		  
		  CREATE  TABLE ovning6new.kandidat_has_kvalificasjon ( 
			fk_kvalifikasjon_id  INT  NOT NULL    ,
			fk_kandidat_id       INT  NOT NULL    ,
			CONSTRAINT pk_kval_kandidat PRIMARY KEY ( fk_kvalifikasjon_id, fk_kandidat_id ),
			CONSTRAINT kval_kandidat_fk2 UNIQUE ( fk_kandidat_id ) ,
			CONSTRAINT unq_kval_kandidat_kval_id UNIQUE ( fk_kvalifikasjon_id ) 
		   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
		  
		  CREATE  TABLE ovning6new.oppdrag ( 
			oppdrag_id           INT  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
			est_startdato        DATE      ,
			est_sluttdato        DATE      ,
			est_timer            INT      ,
			act_startdato        DATE      ,
			act_sluttdato        DATE      ,
			act_timer            INT      ,
			sluttattest          VARCHAR(200)      ,
			fk_org_nr            CHAR(15)      ,
			fk_kandidat_id       INT      ,
			fk_kval_id           INT      
		   ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
		  
		  ALTER TABLE ovning6new.kandidat_has_kvalificasjon ADD CONSTRAINT kval_kandidat_fk1 FOREIGN KEY ( fk_kvalifikasjon_id ) REFERENCES ovning6new.kvalifikasjon( kvalifikasjon_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
		  
		  ALTER TABLE ovning6new.kandidat_has_kvalificasjon ADD CONSTRAINT kval_kandidat_fk2 FOREIGN KEY ( fk_kandidat_id ) REFERENCES ovning6new.kandidat( kandidat_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
		  
		  ALTER TABLE ovning6new.oppdrag ADD CONSTRAINT fk_oppdrag_bedrift FOREIGN KEY ( fk_org_nr ) REFERENCES ovning6new.bedrift( org_nr ) ON DELETE NO ACTION ON UPDATE NO ACTION;
		  
		  ALTER TABLE ovning6new.oppdrag ADD CONSTRAINT fk_oppdrag_kandidat FOREIGN KEY ( fk_kandidat_id ) REFERENCES ovning6new.kandidat( kandidat_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
		  
		  ALTER TABLE ovning6new.oppdrag ADD CONSTRAINT fk_oppdrag_kval_kandidat FOREIGN KEY ( fk_kval_id ) REFERENCES ovning6new.kandidat_has_kvalificasjon( fk_kvalifikasjon_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
		  
		  INSERT INTO ovning6new.bedrift( org_nr, navn, telefon, epost ) VALUES ( '123', 'Telsa', '112212', 'tesla@mail.com');
		  INSERT INTO ovning6new.bedrift( org_nr, navn, telefon, epost ) VALUES ( '124', 'Amazon', '666', 'ama@boom.com');
		  INSERT INTO ovning6new.kandidat( fornavn, etternavn, telefon, epost ) VALUES ( 'Elon', 'Musk', '11111111', 'elon@tesla.com');
		  INSERT INTO ovning6new.kandidat( fornavn, etternavn, telefon, epost ) VALUES ( 'Jeff', 'Bezos', '22222222', 'jeff@amazon.com');
		  INSERT INTO ovning6new.kandidat( fornavn, etternavn, telefon, epost ) VALUES ( 'Stian', 'Lyng', '92055335', 'stian@ntnu.no');
		  INSERT INTO ovning6new.kvalifikasjon( kvalifikasjon_beskrivelse ) VALUES ( 'python');
		  INSERT INTO ovning6new.kvalifikasjon( kvalifikasjon_beskrivelse ) VALUES ( 'java');
		  INSERT INTO ovning6new.kvalifikasjon( kvalifikasjon_beskrivelse ) VALUES ( 'js');
		  INSERT INTO ovning6new.kvalifikasjon( kvalifikasjon_beskrivelse ) VALUES ( 'rust');
		  INSERT INTO ovning6new.kandidat_has_kvalificasjon( fk_kvalifikasjon_id, fk_kandidat_id ) VALUES ( 1, 1);
		  INSERT INTO ovning6new.kandidat_has_kvalificasjon( fk_kvalifikasjon_id, fk_kandidat_id ) VALUES ( 2, 1);
		  INSERT INTO ovning6new.oppdrag( est_startdato, est_sluttdato, est_timer, act_startdato, act_sluttdato, act_timer, sluttattest, fk_org_nr, fk_kandidat_id, fk_kval_id ) VALUES ( '2021-11-01', '2021-12-01', 200, '2021-11-02', '2021-12-02', 100, 'Veldig bra jobba kis', '123', 1, null);
		  INSERT INTO ovning6new.oppdrag( est_startdato, est_sluttdato, est_timer, act_startdato, act_sluttdato, act_timer, sluttattest, fk_org_nr, fk_kandidat_id, fk_kval_id ) VALUES ( '2020-01-01', '2021-01-01', 1000, '2020-01-01', '2021-01-01', 1300, 'Kunne blitt gjort bedre', '124', 2, null);
		  INSERT INTO ovning6new.oppdrag( est_startdato, est_sluttdato, est_timer, act_startdato, act_sluttdato, act_timer, sluttattest, fk_org_nr, fk_kandidat_id, fk_kval_id ) VALUES ( '2022-10-11', '2022-10-30', null, null, null, null, null, '123', null, 1);
		  ```
- ## Oppgave d)
  Sett opp SELECT-setninger som gjør følgende:
	- **1. Lag en liste over alle bedriftene. Navn, telefon og epost til bedriften skal skrives ut.**
		- ```sql
		  SELECT b.org_nr, b.navn, b.mob, b.mail
		  FROM oving6.bedrift b
		  ```
		- ![image.png](image_1666691520732_0.png)
	- **2. Lag en liste over alle oppdragene. Om hvert oppdrag skal du skrive ut oppdragets nummer samt navn og telefonnummer til bedriften som tilbyr oppdraget.**
		- ```sql
		  SELECT o.oppdrag_id, o.beskrivelse, b.navn, b.mob
		  FROM oving6.oppdrag o 
		  INNER JOIN oving6.bedrift b ON ( b.org_nr = o.fk_org_nr  )  
		  ```
		- ![image.png](image_1666691765720_0.png)
	- **3. Lag en liste over kandidater og kvalifikasjoner. Kandidatnavn og kvalifikasjonsbeskrivelse skal med i utskriften i tillegg til løpenumrene som identifiserer kandidat og kvalifikasjon.**
		- ```sql
		  SELECT k.kandidat_id, k.fornavn, k.etternavn, k1.kvalifikasjon_beskrivelse
		  FROM ovning6new.kandidat k 
			INNER JOIN ovning6new.kval_kandidat kk ON ( kk.kandidat_id = k.kandidat_id  )  
			INNER JOIN ovning6new.kvalifikasjon k1 ON ( k1.kval_id = kk.kval_id  )  
		  ```
		- ![image.png](image_1666695273163_0.png)
	- **4. Som oppgave 3d), men få med de kandidatene som ikke er registrert med kvalifikasjoner.**
		- ```sql
		  SELECT k.kandidat_id, k.fornavn, k.etternavn, kk.kval_id, k1.kvalifikasjon_beskrivelse
		  FROM ovning6new.kandidat k 
			LEFT OUTER JOIN ovning6new.kval_kandidat kk ON ( kk.kandidat_id = k.kandidat_id  )  
			LEFT OUTER JOIN ovning6new.kvalifikasjon k1 ON ( k1.kval_id = kk.kval_id  )  
		  ```
		-
		- ![image.png](image_1666697200861_0.png)
	- **5. Skriv ut jobbhistorikken til en bestemt vikar, gitt kandidatnr. Vikarnavn, sluttdato, oppdragsnr og bedriftsnavn skal med.**
		- ```sql
		  SELECT o.oppdrag_id, o.virkelig_sluttdato, o.fk_kandidat_id, k.fornavn, k.etternavn, b.bedrift_navn
		  FROM ovning6new.oppdrag o 
			INNER JOIN ovning6new.kandidat k ON ( k.kandidat_id = o.fk_kandidat_id  )  
			INNER JOIN ovning6new.bedrift b ON ( b.org_nr = o.fk_org_nr  )  
		  WHERE o.fk_kandidat_id = 1
		  ```
		- ![image.png](image_1666696520571_0.png)
		-