# Oppgave 2,5 og 6 - Debugging av eksisterende prosjekt og versjonskontroll med Git

## Beskrivelse debugging
I prosjektet har vi lagt inn en håndfull feil i koden. Din oppgave er å finne alle feilene, og rette opp disse. Målet med øvelsen er for deg å bli **kjent med debug-verktøyet i din IDE og trene på strategier for feilfinning**.

Hvor mange feil fant du i koden (du får høyere poeng jo nærmere svaret du kommer?

**Strategi**
1. [[#Lese igjennom koden]]
2. [[#Lese igjennom testkoden]]
3. [[#Vanlig kjøring av koden]]
4. [[#Kjøre debuggeren]]
5. [[#Kjøre debugger på test koden]]

## Lokal versjonskontroll med Git 
### Oppgave 5 - Hvordan det gikk

> git version 2.39.1

Har allerede jobbet masse med git, og har det innstallert fra før av. Det gikk fint. 
### Oppgave 6

#### git status
```zsh
RealestateApp on  master [?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .vscode/
        pom.xml
        src/
        target/7

nothing added to commit but untracked files present (use "git add" to track)
```

#### git add git add src/\*.java og git add pom.xml
```zsh
RealestateApp on  master [?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git add src/\*.java

RealestateApp on  master [+?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git add pom.xml

RealestateApp on  master [+?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git status
On branch master

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   pom.xml
        new file:   src/main/java/no/ntnu/idata2001/realestate/Property.java
        new file:   src/main/java/no/ntnu/idata2001/realestate/PropertyRegister.java
        new file:   src/main/java/no/ntnu/idata2001/realestate/RealestateApp.java
        new file:   src/test/java/no/ntnu/idata2001/realestate/PropertyRegisterTest.java
        new file:   src/test/java/no/ntnu/idata2001/realestate/PropertyTest.java

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .vscode/
        target/
```

#### git commit -m "initial commit"
```zsh
RealestateApp on  master [+?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git commit -m "Initial commit"                           
[master (root-commit) c8b1d94] Initial commit
 6 files changed, 1045 insertions(+)
 create mode 100644 pom.xml
 create mode 100644 src/main/java/no/ntnu/idata2001/realestate/Property.java
 create mode 100644 src/main/java/no/ntnu/idata2001/realestate/PropertyRegister.java
 create mode 100644 src/main/java/no/ntnu/idata2001/realestate/RealestateApp.java
 create mode 100644 src/test/java/no/ntnu/idata2001/realestate/PropertyRegisterTest.java
 create mode 100644 src/test/java/no/ntnu/idata2001/realestate/PropertyTest.java
```

#### Bugfix 1 - commit
```zsh
RealestateApp on  master [!?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 took 7m54s 
❯ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   src/main/java/no/ntnu/idata2001/realestate/PropertyRegister.java

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .vscode/
        target/

no changes added to commit (use "git add" and/or "git commit -a")

RealestateApp on  master [!?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git add src/main/java/no/ntnu/idata2001/realestate/PropertyRegister.java

RealestateApp on  master [+?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git status
On branch master
Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
        modified:   src/main/java/no/ntnu/idata2001/realestate/PropertyRegister.java

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .vscode/
        target/


RealestateApp on  master [+?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git commit -m "Bugfix: 1"
[master 03b9851] Bugfix: 1
 1 file changed, 2 insertions(+), 2 deletions(-)

RealestateApp on  master [?] is 📦 v1.0-SNAPSHOT via ☕ v19.0.2 
❯ git status
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .vscode/
        target/

nothing added to commit but untracked files present (use "git add" to track)
```
## Lese igjennom koden
### Property.java

#### Datafelt String name;
Ser allerede på linje 18 at feltet `name` aldri blir brukt. 
```java
private final String name;
```
#### getName()
Returnerer ikke `Property.name`, men en lokal variabel. En riktig get metode vil fikse 'feilen' på [[#LINJE 18]]
```java
public String getName()
    {
        String name = "Test";
        return name;
    }
```

### PropertyRegister.java

#### addProperty()
Boolean på linje 43 skal være `true`

```java
    public boolean addProperty(Property property)
    {
        boolean success = false; 
        if (this.properties.containsKey(property.getPropertyIDAsString()))
        {
            this.properties.put(property.getPropertyIDAsString(), property);
            success = false; // skal være true
        }
        return success;
    }
```

#### findProperty()

```java
    public Property findProperty(int municipalityNumber, int lotNumber, int sectionNumber)
    {
        Property foundProperty = null;

        String uniqueId = "" + municipalityNumber
                + "-" + lotNumber
                + "/" + sectionNumber;


        foundProperty = this.properties.get(uniqueId);

        return foundProperty;
    }
```

String uniqueId kunne brukt `getPropertyIDAsString()` metoden fra     `Property` klassen, i stedet for hardkoding..

#### findAllPropertiesWithLotNumber()
Feil operator i if-statementet i for løkka

```java
    public Iterator<Property> findAllPropertiesWithLotNumber(int lotNumber)
    {
        // Create a temperarely collection to store the found properties in
        HashSet<Property> foundProperties = new HashSet<>();
        for (Property property : this.properties.values())
        {
            if (property.getLotNumber() != lotNumber) // Skal være == ikke !=
            {
                foundProperties.add(property);
            }
        }
        return foundProperties.iterator();
    }
```

#### getAverageAreaOfProperties()
Henter bare ut getSumOfAreas() - Altså totalsum. Legg inn formel for gjennomsnitt.

```java
    public double getAverageAreaOfProperties()
    {
        double averageArea = 0;
        if (this.properties.size() > 0)
        {
            averageArea = getSumOfAreas();
        }
        return averageArea; // dele på size?
    }
```

### RealestateApp.java
Denne faller offer for mange av feilene funnet i _Property_ og _PropertyRegister_. 

Sparer meg derfor fra å lese for nøye i gjennom selv. 
## Lese gjennom testkoden
Leser kjappt igjennom testkoden for å se etter noen klare feil som jeg tar med meg i videre debugging.
## Vanlig kjøring av koden
Under vanlig kjøring ser man at koden kjører men at, ikke man ikke finner test eiendommene, eller har mulighet til å legge til ny..
## Kjøre debuggeren
Startet med å kjøre et breakpoint på main metoden i _RealestateApp.java_
![[Pasted image 20230128162742.png]]
#### app.init()
Ser allerede her at properties er tom etter kjøring. Jeg stepper inn og det ser ut som at det går fint å lage en instans av en _Property_.
```
app.init()
	⬇
this.fillRegisterWithPoperties()
	⬇
new Property()
	⬇
this.addProperty() → returnerer "false"
```

#### add.Property()

**Bugfix:** [[#E1 - add.Property()]] - fikser å legge til en eiendom i registeret. 

#### muncipalityName: null
Så videre etter kjøring at _muncipalityName_ er _null_ i alle objektene i hashmappet.
![[Pasted image 20230128173342.png|400]]

Ved kjøring i stepping i debuggeren blir det tidlig klart at _municipalityNumber_ aldri blir instansiert i konstruktøren.

**Bugfix:** [[#E2 - Property()]] og [[#E3 - Property()]]

### findRealestateByOwner()
Et par steps inn i denne funksjonen viser at funkjsonen ikke sjekker opp mot noe som helst.

**Bugfix:** [[#E4 - findRealestateByOwner()]], [[#E4 - findRealestateByOwner()]]

### findAllPropertiesByOwner()
Bruker ikke riktig comparison if-statementet.

**Bugfix: ** [[#E5 - findAllPropertiesByOwner()]]

### getAverageAreaOfProperties()
Denne deler ikke summen av alle arealene

**Bugfix:** [[#E6 - getAverageAreaOfProperties()]]

### getSumOFAreas()
Inkrementerer ikke verdiene i for-loopen.

**Bugfix:** [[#E7 - getSumOfAreas()]]
### Småtteri
#### app.showMenu()
Scanneren blir ikke avsluttet, noe som kan føre til en memory leak. Vet ikke om dette blir ansett som en bug

```java
Scanner sc = new Scanner(System.in);
```

## Kjøre debugger på test koden
### PropertyRegisterTest.java

Etter fiksene gjort med debuggeren gjennstår det kun en feil i denne testen. 
![[Pasted image 20230128193003.png]]

#### findAllPropertiesWithLotNumber()
Motsatt logikk i if-statementet. 

**Bugfix:** [[#E8 - findAllPropertiesWithLotNumber()]]

### PropertyTest.java

#### getName()

Get name henter ikke navnet fra konstantene i klassen.

**Bugfix:** [[#E9 - getName()]]


## Bugfixes:
### E1 - add.Property()
**Fra:**
```java
    public boolean addProperty(Property property)
    {
        boolean success = false;
        if (this.properties.containsKey(property.getPropertyIDAsString()))
        {
            this.properties.put(property.getPropertyIDAsString(), property);
            success = false;
        }
        return success;
    }
```

**Til:**
```java
    public boolean addProperty(Property property)
    {
        boolean success = false;
        if (!this.properties.containsKey(property.getPropertyIDAsString()))
        {
            this.properties.put(property.getPropertyIDAsString(), property);
            success = true;
        }
        return success;
    }
```

### E2 - Property() 

Legger til i _Property_ konstruktøren:

```java
this.municipalityName = municipalityName;
```

Noe som betyr at: [[#E3 - Property()]] må fiksen

### E3 - Property() 

```java
private final String municipalityName = null;
```

Må endres til:
```java
private final String municipalityName;
```

### E4 - findRealestateByOwner()

Legger til dette i else statementet

```java
else {
		Iterator<Property> properties = this.properties.findAllPropertiesByOwner(owner);
		// for each property found, display it to the user
		while (properties.hasNext()) {
			Property property = properties.next();
			displayProperty(property);
			System.out.println();
		}
	}
```

### E5 - findAllPropertiesByOwner()

Fra:
```java
    public Iterator<Property> findAllPropertiesByOwner(String nameOfOwner) {
        HashSet<Property> foundProperties = new HashSet<>();
        for (Property property : this.properties.values())
        {
            if (property.getNameOfOwner() == nameOfOwner)
            {
                foundProperties.add(property);
            }
        }
        return foundProperties.iterator();
    }
```

Til:
```java
    public Iterator<Property> findAllPropertiesByOwner(String nameOfOwner) {
        HashSet<Property> foundProperties = new HashSet<>();
        for (Property property : this.properties.values())
        {
            if (property.getNameOfOwner().equals(nameOfOwner))
            {
                foundProperties.add(property);
            }
        }
        return foundProperties.iterator();
    }
```

### E6 - getAverageAreaOfProperties()

Før:
```java
 public double getAverageAreaOfProperties()
    {
        double averageArea = 0;
        if (this.properties.size() > 0)
        {
            averageArea = getSumOfAreas();
        }
        return averageArea;
    }
```

Etter:
```java
public double getAverageAreaOfProperties()
    {
        double averageArea = 0;
        if (this.properties.size() > 0)
        {
            averageArea = getSumOfAreas();
        }
        return averageArea / this.properties.size();
    }
```

### E7 - getSumOfAreas()

Bytt:
```java 
sumOfAreas = property.getArea();
```

Til:
```java
sumOfAreas += property.getArea();
```

### E8 - findAllPropertiesWithLotNumber()

Bytt:
```java
if (property.getLotNumber() != lotNumber)
            {
                foundProperties.add(property);
            }
```

Til:
```java
if (property.getLotNumber() == lotNumber)
            {
                foundProperties.add(property);
            }
```

### E9 - getName()

Endres til:
```java
public String getName()
{
	return this.name;
}
```

# Oppgave 4
Opprett et Maven-prosjekt fra scratch, og flytte kode fra Eiendoms Registeret vi hadde i øvning 11 i fjor. 

 1. opprett et nytt tomt Maven-prosjekt (ikke bruk archetypes i denne omgang). 
	 - Her må du ta stilling til hvilken groupID, artifactID og version du mener er fornuftig for ditt prosjekt.
 2. Kopier Java-filene dine fra ditt gamle prosjekt over til maven prosjektet
	 - NB! Pass på pakker!! Dersom du ikke har benyttet pakker tidligere, må du innføre pakker nå (gjelder spesielt dere som har brukt BlueJ til nå).
3. Bygg prosjektet og kjør det. Rett opp eventuelle feil.

## 1. Hva valgte du som **groupID**, **artifactID** og **version** i ditt prosjekt, og hvorfor (begrunn)?
Brukte apache sine egne retningslinjer for 'naming conventions' som grunnlag
### groupID --->  ntnu.stiansen

Skal unikt identifisere prosjektet, og bør være et domene man selv eier. Nå har jeg bare tatt et fiktivt navn..
### artifactID ---> eiendomsregisteret

Navnet på JAR'en uten versjon.

### versjon ---> 0.0.2

Siden det er andre versjon, av programmet etter det ble flyttet til vaven
## 2.  Hvilke **pakker** opprettet du/benyttet du for klassene dine?
Pakken:
```java
package ntnu.stiansen;
```
## 3.  Hva står bokstavene **POM** for i prosjektfilen **pom.xml**?

Project Object Model
## 4.  Hvilke utfordringer møtte du på (hvis noen) når du konverterte ditt gamle prosjekt til Maven?
Det var en del styr med å få paths og slikt til å fungere 

# Oppgave 7

## En **mutator-metode** MÅ alltid starte med "set", som i _setPrice(int somePrice)_.

**Feil:** Det er ingen regel som sier det, men det er best practice..

# Oppgave 17

## påstand: en metode tilhoorer alltid et objekt

**Feil**
Statiske metoder tilhører klassen i stedet for instansen til klassen. Noe som betyr at du kan kalle metoden uten å skape et objekt av klassen. 

# Oppgave 24
## Påstand: Vi må bruke metoden **put()** for å legge til et ovjekt i en **HashMap.**
Kan bruke:
- putAll()
- putIfAbsent()