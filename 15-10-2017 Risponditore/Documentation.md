# Risponditore Automatico

### Progettista: Manuele Lucchi
### Classe: 5IC
### Istituto: ITIS C.Zuccante
### Strategie: Ho usato un albero classico come automa. Per attraversarlo non ho usato la ricorsione ma un semplice ciclo che ogni volta assegnava a Root il nodo successivo, questo per aumentare le prestazioni. Per tornare al nodo iniziale all'inizio faccio una copia di Root che dopo assegno a quest'ultima. Semplicemente si sceglie la categoria di prodotto, il prodotto e se si vuole altro, si torna all'inizio, dove verrà visualizzata la spesa corrente
