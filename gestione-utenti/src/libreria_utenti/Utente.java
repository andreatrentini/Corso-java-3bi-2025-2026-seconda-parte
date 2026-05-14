package libreria_utenti;

import java.time.LocalDate;

public class Utente {
    private String nome;
    private String cognome;
    private String comuneDiNascita;
    private String provinciaDiNascita;
    private LocalDate dataDiNascita;
    private Sesso sesso;

    public Utente(String nome, String cognome, String comuneDiNascita, String provinciaDiNascita,
            LocalDate dataDiNascita, Sesso sesso) {
        this.nome = nome.toUpperCase();
        this.cognome = cognome.toUpperCase();
        this.comuneDiNascita = comuneDiNascita.toUpperCase();
        this.provinciaDiNascita = provinciaDiNascita.toUpperCase();
        this.dataDiNascita = dataDiNascita;
        this.sesso = sesso;
    }

    private String getCodiceCognome() {
        String vocali = "";
        String consonanti = "";
        for (int i = 0; i < this.cognome.length(); i++) {
            char c = this.cognome.charAt(i);
            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                vocali += c;
            } else {
                consonanti += c;
            }
        }
        String codice = consonanti + vocali + "XXX";
        return codice.substring(0, 3);
    }

    private String getCodiceNome() {
        String vocali = "";
        String consonanti = "";
        for (int i = 0; i < this.nome.length(); i++) {
            char c = this.nome.charAt(i);
            if (Character.isLetter(c)) {
                if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                    vocali += c;
                } else {
                    consonanti += c;
                }
            }
        }
        String codice;
        if (consonanti.length() >= 4) {
            codice = consonanti.charAt(0) + "" + consonanti.charAt(2) + "" + consonanti.charAt(3) + vocali + "XXX";
        } else {
            codice = consonanti + vocali + "XXX";
        }
        return codice.substring(0, 3);
    }

    private String getMeseDiNascita() throws Exception {
        int mese = this.dataDiNascita.getMonthValue();
        switch (mese) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "H";
            case 7:
                return "L";
            case 8:
                return "M";
            case 9:
                return "P";
            case 10:
                return "R";
            case 11:
                return "S";
            case 12:
                return "T";
        }
        throw new Exception("Mese di nascita non valido");
    }

    private String getGiornoDiNascita() {
        int giorno = this.dataDiNascita.getDayOfMonth();
        if (this.sesso == Sesso.Femmina) {
            giorno += 40;
        }
        return String.format("%02d", giorno);
    }

    private String getAnnoDiNascita() {
        int anno = this.dataDiNascita.getYear();
        return String.format("%02d", anno % 100);
    }

    private String getComuneDiNascita(Comuni comuni) {
        return comuni.getCodiceComune(this.provinciaDiNascita, this.comuneDiNascita);
    }

    private String getCodiceControllo(Comuni comuni) throws Exception {
        String primi15 = getCodiceCognome() + getCodiceNome() + getAnnoDiNascita() + getMeseDiNascita()
                + getGiornoDiNascita() + getComuneDiNascita(comuni);
        if (primi15 == null || primi15.length() != 15) {
            throw new IllegalArgumentException("Il codice deve essere di 15 caratteri.");
        }

        int somma = 0;

        // Tabelle dei valori per i caratteri in posizione DISPARI (1, 3, 5...)
        // Usiamo un array dove l'indice è (carattere - '0') o (carattere - 'A' + 10)
        int[] valoriDispari = {
                1, 0, 5, 7, 9, 13, 15, 17, 19, 21, // 0-9
                1, 0, 5, 7, 9, 13, 15, 17, 19, 21, // A-J
                2, 4, 18, 20, 11, 3, 6, 8, 12, 14, // K-T
                16, 10, 22, 25, 24, 23 // U-Z
        };

        // Tabelle dei valori per i caratteri in posizione PARI (2, 4, 6...)
        // I valori corrispondono semplicemente alla posizione nell'alfabeto (A=0,
        // B=1...) o al numero stesso
        int[] valoriPari = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // 0-9
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // A-J
                10, 11, 12, 13, 14, 15, 16, 17, 18, 19, // K-T
                20, 21, 22, 23, 24, 25 // U-Z
        };

        for (int i = 0; i < 15; i++) {
            char c = primi15.charAt(i);
            int index;

            if (Character.isDigit(c)) {
                index = c - '0';
            } else {
                index = c - 'A' + 10;
            }

            // i+1 perché l'algoritmo ufficiale conta le posizioni da 1 a 15
            if ((i + 1) % 2 != 0) {
                somma += valoriDispari[index];
            } else {
                somma += valoriPari[index];
            }
        }

        // Il resto della divisione per 26 indica la lettera finale (0=A, 1=B...)
        return Character.toString((char) ('A' + (somma % 26)));
    }

    public String getCodiceFiscale(Comuni comuni) throws Exception {
        return getCodiceCognome() + getCodiceNome() + getAnnoDiNascita() + getMeseDiNascita() + getGiornoDiNascita()
                + getComuneDiNascita(comuni) + getCodiceControllo(comuni);
    }

    @Override
    public String toString() {
        return this.nome + ";" + this.cognome + ";" + this.comuneDiNascita + ";" + this.provinciaDiNascita + ";"
                + this.dataDiNascita + ";" + this.sesso;
    }
}
