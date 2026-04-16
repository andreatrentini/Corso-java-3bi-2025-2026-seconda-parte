# Gestione utenti
Il programma deve essere in grado di gestire una collezione di Utenti.
Ogni utente deve disporre delle informazioni necessario al calcolo del codice fiscale.
NB: il codice fiscale NON deve essere memorizzato, ma restituito da un metodo.
I codici dei comuni possono essere trovati in Internet. La classe Comuni deve disporre di un metodo in grado di leggere il file txt/csv per caricare i dati dei comuni.

## Classi
- Utente:
  - nome: String
  - cognome: String
  - ...
- Comune:
  - codice: String
  - nome: String
  - ...
- Comuni:
  - array di Comune
  - caricaDati()
- Utenti:
  - array di Utente
  - salva(nomefile): salva i dati degli utenti su un file CSV
