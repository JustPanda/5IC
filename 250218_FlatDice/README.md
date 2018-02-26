# FlatDice

Questo progetto consiste in un'applicazione android che offre ad un utente di terze parti di simulare il tiro di un dado grazie ad uno swipe.

### Spiegazione del progetto

#### MainActivity
Classe che contiene la activity principale ed è quella che gestisce la questione della simulazione del tiro di un dado.<br />
Questa classe ha il compito di indentificare il tipo di swipe e, in base ad esso, generare un'animazione che scambia la faccia del dado presente con una nuova faccia generata in base ad un numero casuale.

#### DiceFace
Classe che rappresenta la faccia di un dado e consiste in un fragment gestito dalla MainActivity.

### Tecnologie usate
##### Le tecnologie usate sono: 
* Android studio come IDE per lo sviluppo dell'applicazione
* Kotlin come main language come sostituto a java