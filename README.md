# Documentazione Gestionale Treni

## Help

Per ottenere la guida ai comandi disponibili

````
http://localhost:8090/help
````

## Importante*

Tutti i comandi put non hanno alcun campo obbligatorio, tranne che ````elementNumber````(che è ti tipo ```` int ````),
che è il numero dei dell'elemento all'interno della struttura dati in cui è inizializzato nel server. _(Ovviamente
nel **POST** dato che aggiungiamo un elemento non ci serve avere la posizione, dato che non ancora istanziato)._

Quando negli esempi vieni utilizzata la dicitura ````%s```` ci si riferisci alla variabile verrà inserita al suo posto

## Biglietti

Gli attributi di biglietto sono:

````java
roadPath = int;
day = Date().getTime(); // La data in millisecondi
classNumber = int; // La classe a cui è riferito il biglietto dove per classe, la classe del treno (es. Prima Classe, che nel nostro caso sarà -> 1)
````

### Aggiorna Biglietto (PUT)

Permette di aggiornare il biglietto passando nel url i paramatri utili a inizializzare un biglietto:

````
http://localhost:8090/updateTicket?elementNumber=%s&roadPath=%s&day=%s&classNumber=%s
````

### Aggiungi Biglietto (POST)

Permette di aggiungere un biglietto

````
http://localhost:8090/addTicket?roadPath=%s&day=%s&classNumber=%s
````

### Rimuovi Biglietto (DELETE)

Permette di eliminare un biglietto

````
http://localhost:8090/removeTicket?elementNumber=%s
````

### Ottenere tutti i Biglietti (GET)

Permette di ottenere tutti i biglietti salvati

````
http://localhost:8090/tickets
````

## Classi

Gli attributi della classe sono:

````java
classNumber = int; // La classe a cui viene riferito ogni biglietto
multiplier = double; // Il moltiplicatore che determinerà il prezzo del biglietto in base alla classe
````

### Aggiorna Classe (PUT)

Permette di aggiornare la classe passando nel url i paramatri utili a inizializzare una classe:

````
http://localhost:8090/updateClass?elementNumber=%s&classNumber=%s&multiplier=%s
````

### Aggiungi Classe (POST)

Permette di aggiungere una classe

````
http://localhost:8090/addClass?classNumber=%s&multiplier=%s
````

### Rimuovi Classe (DELETE)

Permette di eliminare una classe

````
http://localhost:8090/removeClass?elementNumber=%s
````

### Ottenere tutte le Classi (GET)

Permette di ottenere tutte le classi salvate

````
http://localhost:8090/classes
````

## Collegamenti

Gli attributi di collegamento sono:

````java
startStation = int; // L'indice della prima stazione del collegamento tra due stazioni
endStation = int; // L'indice della seconda stazione del collegamento tra due stazioni
cost = double; // Il tempo in cui viene percorso il collegamento
pathNumber = int; // L'indice della tratta in cui si trova il collegamento
````

### Aggiorna Collegamento (PUT)

Permette di aggiornare il collegamento passando nel url i paramatri utili a inizializzare un collegamento:

````
http://localhost:8090/updateLink?elementNumber=%s&cost=%s&startStation=%s&endStation=%s&pathNumber=%s
````

### Aggiungi Collegamento (POST)

Permette di aggiungere un collegamento tra due stazioni

````
http://localhost:8090/addLink?cost=%s&startStation=%s&endStation=%s&pathNumber=%s
````

### Rimuovi Collegamento (DELETE)

Permette di eliminare un collegamento tra due stazioni

````
http://localhost:8090/removeLink?elementNumber=%s
````

### Ottenere tutti i Collegamenti (GET)

Permette di ottenere tutti i collegamenti tra le stazioni

````
http://localhost:8090/links
````

## Stazioni

Gli attributi di stazioni sono:

````java
stationName = String; // Il nome della stazione
````

### Aggiorna Stazioni (PUT)

Permette di aggiornare gli elementi riguardanti le stazioni:

````
http://localhost:8090/updateStation?elementNumber=%s&stationName=%s
````

### Aggiungi Stazione (POST)

Permette di aggiungere una stazione

````
http://localhost:8090/addStation?stationName=%s
````

### Rimuovi Stazione (DELETE)

Permette di eliminare la stazione

````
http://localhost:8090/removeStation?elementNumber=%s
````

### Ottenere tutte le Stazioni (GET)

Permette di ottenere tutte le stazioni salvate

````
http://localhost:8090/stations
````

## Percorso

Gli attributi di percorso sono:

````java
pathNumber = int; // Numero del percorso
name = String; // Nome del percorso
startDate = Date().getTime(); // La data di partenza in millisecondi
endDate = Date().getTime(); // La data di arrivo in millisecondi
seats = int; // Numero massimo di posti per quel percorso
````

### Aggiorna Percorso (PUT)

Permette di aggiornare gli elementi riguardanti i percorsi:

````
http://localhost:8090/updatePath?elementNumber=%s&pathName=%s&endDate=%s&startDate=%s&seats=%s
````

### Aggiungi Percorso (POST)

Permette di aggiungere un percorso

````
http://localhost:8090/addPath?pathName=%s&endDate=%s&startDate=%s&seats=%s
````

### Rimuovi Percorso (DELETE)

Permette di eliminare un percorso

````
http://localhost:8090/removePath?elementNumber=%s
````
### Ottenere tutti i percorsi (GET)

Permette di ottenere tutti i percorsi

````
http://localhost:8090/paths
````