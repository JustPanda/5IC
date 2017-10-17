# Risponditore
Questo progetto ha il compito di prendere le ordinazioni di una pizzeria d'asporto chiamata "Il pizza vendolo", situata in "Via Palazzo" a mestre. Tutti i clienti che si connettono dovrannno rispondere attraverso delle domande prestabilite che fanno parte della transazione. Finita si dovrà inserire l'indirizzo dove far arrivare l'ordinazione.

### Spiegazione del progetto
#### Client
* Client.java
> Ha il compito di connettersi al server, attraverso i socket, e permette all'utente di comunicare con esso. La prima informazione che scambia con lui è il nome, poi parte la transazione, in cui l'utente sceglie cosa ordinare, e in seguito dovrà inserire il proprio indirizzo di casa, così da potersi far arrivare la pizza a casa
#### Server
* Server.java
> Ha il compito di accettare le richieste dei client e di far partire dei thread appositi per ognuno di essi, dandogli le informazioni di partenza. Queste informazioni di partenza vengono prese da due file: Questions.txt e Products.txt

   * Questions.txt
   > Contiene tutte le domande, le risposte e su quali nodi posizionarsi in base alla risposta. La prima riga è adibita per l'indirizzo postale, mentre le altre sono tutti i nodi. Il primo nodo che si fa prtire è quello con chiave "start". <br />
   Ogni nodo ha questa sintatti: <br />
           key:Type <br />
                Questions <br />
                    [Answers]... <br />
    Key: è il nome del nodo e questo deve corrispondere ad una possibile risposta in un altro nodo, tranne per il nodo "start". Ogni key è univoca. <br />
    Type: Il tipo del nodo che fa capire al server cosa fare con esso. I tipi sono: <br />
        1. Choose. Fa capire al server che si tratta di una scelta, perciò la stringa verrà formatta in un modo opportuno e il client farà dei controlli sulle risposte che può dare il cliente. <br />
        2. Item. Fa capire al server che si tratta di un elemento da comprare e perciò lo aggiunge allo scontrino e salta subito nel nodo successivo indicatogli. <br />
        3. Insert. Fa capire al server che il client vuole inserire un elemento da rimuovere dallo scontrino. In seguito salta subito sul nodo successivo indicatogli. <br />
        4. Show. Fa caipre al server che si vuole mostrare la spesa completa e perciò la invia al client. In seguito salta subito sul nodo successivo indicatogli. <br />
        5. Exit. Fa capire al server che la transazione è conclusa e fa passare il server ed il client al momento degli scambi degli indirizzi. <br />
    Questions: domanda che il server pone al cliente. <br />
    Answers: possibili risposte che il cliente potrebbe dare alla domanda. Ognuno di esse corrispondono ad un'altra chiave di un altro nodo.

  * Products.txt
  > Contiene i prodotti da vendere nel seguente formato: <br />
          [Product,Price\n]...

* Service.java
> Contiene tutti i metodi che gestiscono il servizio della pizzeria e sta in ascolto per messaggi inviati dal client. Ad ogni messaggio lui esegue un'operazione cambiando nodo e richiamando una funzione da una mappa di esse contenuta nell'oggetto di classe Operations condiviso tra tutti i thread

* Operations.java
> Contiene oggetti e metodi che riguardano le operazioni che il server deve fare. Contiene le due mappe di funzioni (exec per le operazioni sullo scontrino e printToClient per le operazioni che bisogna fare quando si comunica con il client), il metodo per scrivere gli ordini ricevuti (con data e ora relativa) e il metodo che sfrutta e api di maps per calcolare il tempo impiegato per la ricevuta della consegna dell'ordine

* Receipt.java
> Contiene i prodotti comprati dall'utente con relativo prezzo complessivo

* Product.java
> Classe creata per comodità che consiste nel prodotto da vendere. Ha l'attributo "name" che corrisponde al nome del prodotto, mentre l'attributo "price" consiste nel suo prezzo

* Type.java
> Contiene tutti i nomi dei tipi dei nodi per comodità

### Tecnologie usate
##### Le tecnologie sono:
* Ide intellij idea per lo sviluppo del software
* Star uml per la creazione dei diagrammi di classe
* Latex per la creazione dell'automa a stati finiti
