# Nicolò Greggio
## android
---
#### Questa cartella contiene i progetti android

---
# Safe
## Descrizione
L'applicazione coniste in una specie di gioco, in cui l'utente deve indovinare un codice a 4 cifre generato casualmente tramite l'utilizzo di un tastierino numerico. Sono presenti anche le opzioni "del" per cancellare l'ultimo numero digitato e "clear" per svuotare tutto il campo.
Fintantoche il codice inserito è sbagliato il testo di questo è il bordo che lo contorna rimangono rossi, ma non appena il codice viene indovinato, questi diventano verdi, e succesivamente viene lascaito spazio ad una nuova activity con un messaggio di vittoria, dove sono presenti inotlre due bottoni, uno per uscire, e uno per giocare di nuovo. All'avvio viene mostrato uno splash screen di benvenuto della durata di 1 secondo.
Dopo 3 tentativi, all'utente viene suggerito il primo numero tramite un toast, dopo ulteriori 3 viene suggerito anche il secondo numero e così via, fino a quando si avranno ricevuto i suggerimenti per tutti e 4 e a questo punto se l'utente conitnuerà ad inserire un codice sbagliato, verrà suggerito quello corretto per intero.

---

# FlatDice
## Descrizione
L'applicazione consiste in una sorta di lancio del dado casuale, in quanto la faccia che appare viene generata in modo casuale e non rispetta la sequenza di un dado normale.
All'apertura l'utente si trova davanti una schermata di benvenuto, con una sorta di istruzioni. Effettuando uno swipe verso destra, sinistra, alto o in basso verrà mostrata la faccia di un dado con animazione coerente al gesto effettuato. Il cambio faccia e l'animazione vengono effettuate tramite la classe FragmentTransaction, ma in realtà il fragment caricato per le facce è sempre lo stesso, ma al momento dello swipe viene cambiato il riferimento alla risorsa drawable da utilizzare per disegnare la faccia. Ci sono quindi due fragment, quello iniziale, che da il benvenuto all'utente e fornisce un breve suggerimento sull'utilizzo dell'applicazione, e il fragment DiceFragment, che contiene la definizione del fragment per contenere la faccia del dado, e verrà cambiata la risorsa indicata da src nell'ImageVIew di tale fragment. Le facce sono state realizzate con inkscape, creando degli svg poi importati in android studio grazie a vector asset
