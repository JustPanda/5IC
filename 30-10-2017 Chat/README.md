# Chat TCP

### Progettista: Manuele Lucchi
### Classe: 5IC
### Istituto: ITIS C.Zuccante

### Strategie Client
Il client è sviluppato in Electron, React.js, WinJS, Bootstrap e ES6, per le spiegazioni dei seguenti framework premere [qui](#spiegazioni-tecniche). 
Il client riceve dal server una stringa Json, ne controlla il contenuto e in base a questo fa le seguenti azioni:
* Aggiornare i messaggi
* Aggiornare i contatti esistenti
* Confermare Login/Signup

### Strategie Server
Il Server è composto da 4 file: 
* SQL Manager, per gestire tutti i comandi SQL
* Message per le classi dei messagi
* User per le classi degli utenti e infine
* Server che è la classe principale.
Il server fa partire un oggetto Mixer che contiene tutte le connessioni, l'oggetto per interagire con l'sql e la lista con tutti i messaggi. Dopo aver stabilito la connessione e aver aggiornato la lista di connessioni, il server riceve stringhe che convertirà in json, e in base al loro contenuto farà determinate azioni 

## Spiegazioni Tecniche 
