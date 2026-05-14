import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import libreria_utenti.Comuni;
import libreria_utenti.Sesso;
import libreria_utenti.Utente;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            Comuni comuni = new Comuni("gi_comuni_nazioni_cf.csv");
            Utente utente = new Utente("Andrea", "Trentini", "Rovereto", "TN", LocalDate.parse("08/04/1968", DateTimeFormatter.ofPattern("dd/MM/yyyy")), Sesso.Maschio);
            System.out.println(utente.getCodiceFiscale(comuni));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
    }
}
