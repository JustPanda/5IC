# Nicolò Greggio
## Chat TCP

### Servizi offerti
Permette la comunicazione con altre persone connesse al server in maniera individuale. Gli utenti si possono registrare, dopodichè potranno accedere e uscire a loro piacimento, e anche cancellare il proprio account. All'avvio si può scegliere se effettuare il login se si possiede un account, o crearne uno e poi accedere direttamente. Una volta effettuato l'accesso si può scegliere la persona con cui chattare tra quelle online sul menu a sinistra. Quando si riceve un mesaggio da una chat non aperta, si riceve una notifica in alto a sinistra.

***
### Funzionamento

#### Server
Il server viene messo in ascolto sulla porta 1723 e accetta fino ad un massimo di 100 client alla volta tramite l'utillizzo di executor e fixedThreadPool. I client si connettono per effettuare una richiesta di login o di registrazione. Se queste vanno a buon fine, l'utente viene aggiunto alla lista di client online venendo così a disposizione degli altri utenti connessi. Quando un client richiede la disconnessione viene rimosso dalla lista, e non sarà più a disposizione degli altri utenti.

#### Client
Il client all'avvio lancia l'interfaccia grafica con la possibilità di login o registrazione. La connessione al serer avviene solo quando tutti i campi richiesti sono stati compilati rispettando gli eventuali vincoli. Se la richiesta di accesso o registrazione va a buon fine la connessione viene mantenuta, e si avviano i metodi per la comunicazione col server, che tramite stringhe che rappresentano diverse funzioni, gestiranno il programma.

***

### Strumenti utilizzati per lo sviluppo e la documentazione

Per lo sviluppo del progetto sono stati utilizzati:
- **Atom**: Editor di testo, per la scrittura del codice di client e server, esclusi gli fxml; per la scrittura del README e dell'automa tramite il linguaggio graphviz dot.
- **SceneBuilder**: Per la realizzazione delle interfacce grafiche in fxml.
- **StarUML**: Per la produzione del diagramma di classe.
- **bash**: Per la compilazione e l'esecuzione di client e server. Per la compilazione dell'automa in svg.


#### Librerie esterne utilizzate
- **sqlite-jdbc**: Per l'integrazione dei database nel progetto.
- **jfoenix**: Per il material design.
