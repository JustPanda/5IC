# Nicolò Greggio
## Risponditore Automatico

### Servizi offerti
- #### Pizzeria
    - 8 tipi di pizze
    - 9 tipi di bibite
    - Scelta ora di ritiro


- #### Ristorante
    - 3 tipi di antipasti
    - 5 tipi di primi
    - 5 tipi di secondi
    - 4 tipi di dolci
    - 9 tipi di bibite
    - Scelta giorno e orario di prenotazione


- #### Hotel
    - Scelta data di arrivo e di partenza
    - Quattro tipi differenti di camere


- #### Appartamenti
    - Scelta data di arrivo
    - Scelta settimane di permaneza
    - Quattro tipi differenti di alloggi

***

### Funzionamento

#### Server
Il server viene messo in ascolto sulla porta 1723 e accetta fino ad un massimo di 10 client alla volta tramite l'utillizzo di executor e fixedThreadPool.
Quando un client viene connesso, viene creata un'istanza reception che servirà per la comunicazione che sfrutterà ai fini di questa BufferedReader e PrintWriter.
L'ultimo compito del server è quello di chiedere conferma all'utente per l'ordine che ha effettuato mostrandogli un riepilogo di quello che ha scelto ed un costo totale. Dopodichè disconnette il client.

#### Client
Il client si connette al server se ci sono posti disponibili sennò rimane in attesa, poi scambia informazioni con esso tramite BufferedReader e PrintWriter.
Il client attende istruzioni dal server che possono essere:
- ***receive***: Il server deve comunicare qualcosa, di conseguenza il client leggerà nuovamente e mostrerà all'utente
- ***send***: Il server attende un input dal client, di conseguenza, quest'ultimo leggerà l'input da tastiera dell'utente tramite un oggetto Scanner e lo invierà al server.
- ***exit***: La prenotazione è completata, ora il client verrà disconnesso.

#### Reception
Contiene tutti i dati relativi alle prenotazioni possibili, e si occupa della comunicazione col client una volta istanziata dal server.
Le opzioni sono divise in una mappa di funzioni, una per categoria (pizzeria, ristorante, hotel, appartamenti) e in più è presente una funzione statica a parte che si occupa delle bibite, che viene utilizzata sia da "pizzeria" che da "ristorante". I dati e i prezzi sono divisi, per categoria, in array di stringhe a due dimensioni, la prima rappresenta le opzioni, la seconda i costi ad esse collegati.

***

### Tecnologie utilizzate per lo sviluppo e la documentazione

Per lo sviluppo del progetto è stato utilizzato l'editor di testo atom, la compilazione è stata effettuata da terminale, il file README è stato scritto in markdown. Il diagramma di classe è stato realizzato tramite il programma starUML. Per l'automa a stati finiti si è utilizzato il linguaggio Graphviz (DOT), la compilazione di esso si è fatta con l'utilizzo del tool dot per la bash. Nella directory sono presenti la versione svg sia del diagramma di classe che dell'automa
