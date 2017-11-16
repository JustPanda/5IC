Progetto: Chat TCP

Progettista: Orfei Samuele

Descrizione: Chat TCP realizzata con parte server in java e parte client in Electron(JavaScript, HTML, CSS).
             Il server utilizza gli oggetti di classe BufferedReader e PrintWriter per comunicare mentre il client utilizza il modulo "net". Il server crea 1 thread per ogni client utilizzando il metodo "cachedThreadPool" di executor per non limitare la creazione di client. Ogni volta che viene creato un client, viene anche richiesto a chi si vuole messaggiare; questa informazione viene salvata in una mappa che tiene sia la stringa con il ricevitore, sia il relativo oggetto PrintWriter a lui collegato. In questo modo il server fa da tramite inviando i messaggi al giusto ricevitore.