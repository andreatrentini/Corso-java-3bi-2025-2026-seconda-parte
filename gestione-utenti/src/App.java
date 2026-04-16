import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import libreria_utenti.Comune;
import libreria_utenti.Comuni;

public class App {
    public static void main(String[] args) throws Exception {
        try {

            Comune c1 = new Comune("PD;\"ABANO TERME\";A001;27/02/1929;30/11/1944");
            System.out.println(c1.toString());
            
            String dataStringa = "17/03/1944";
            
            // 1. Definiamo il formato (pattern)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // 2. Trasformiamo la stringa in un oggetto LocalDate
            LocalDate data = LocalDate.parse(dataStringa, formatter);
            
            Comune c2 = new Comune("\"ABANO TERME\"", data);
            
            System.out.println(c1.equals(c2));

            Comuni c = new Comuni("gi_comuni_nazioni_cf.csv", true);
            System.out.println(c.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
