package libreria_utenti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comune {
    private String sigla_provincia;
    private String denominazione_ita;
    private String codice_belfiore;
    private LocalDate data_inizio_validita;
    private LocalDate data_fine_validita;
    private DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Comune(String rigaCSV) throws Exception {
        // PD;ABANO;A001;19/11/1866;13/11/1924

        try {            
            String tmp[] = rigaCSV.split(";");
            if (tmp.length <= 2) {
                throw new Exception("Dati insufficienti per creare il comune.");
            }
            this.sigla_provincia = tmp[0];
            this.denominazione_ita = tmp[1].replace("\"", "");
            this.codice_belfiore = tmp[2];

            // Controllo se ci sono le date...

            if (tmp.length > 3) {
                this.data_inizio_validita = LocalDate.parse(tmp[3], this.formatoData);
            }
            if (tmp.length > 4) {
                this.data_fine_validita = LocalDate.parse(tmp[4], this.formatoData);
            }
        } catch (Exception e) {
            throw new Exception("Errore durante la creazione dell'oggetto. " + e.getMessage());
        }
    }

    public Comune(String sigla_provincia, String denominazione_ita, LocalDate data) throws Exception {
        try {             
            this.sigla_provincia = sigla_provincia;
            this.denominazione_ita = denominazione_ita.replace("\"", "");
            this.data_inizio_validita = data;
        } catch (Exception e) {
            throw new Exception("Errore durante la creazione dell'oggetto. " + e.getMessage());
        }
    }

    public Comune(String sigla_provincia, String denominazione_ita, String data) throws Exception {
        try {
            this.denominazione_ita = denominazione_ita.replace("\"", "");
            this.data_inizio_validita = LocalDate.parse(data, this.formatoData);
        } catch (Exception e) {
            throw new Exception("Errore durante la creazione dell'oggetto. " + e.getMessage());
        }
    }

    public String getCodiceComune() {
        return this.codice_belfiore;
    }

    @Override
    public String toString() {
        String tmp = this.sigla_provincia + ";" + this.denominazione_ita + ";" + this.codice_belfiore;
        tmp += this.data_inizio_validita != null ? ";" + this.data_inizio_validita.format(this.formatoData) : "";
        tmp += this.data_fine_validita != null ? ";" + this.data_fine_validita.format(this.formatoData) : "";
        return tmp;
    }

    @Override
    public boolean equals(Object obj) {

        if (! (obj.getClass() == Comune.getClass())) {
            return false;
        }
        Comune tmp = (Comune) obj;

        if (this.data_inizio_validita == null) {
            return this.denominazione_ita.toUpperCase().equals(tmp.denominazione_ita.toUpperCase()) &&
            this.sigla_provincia.toUpperCase().equals(tmp.sigla_provincia.toUpperCase());
        } else {
            if (this.data_fine_validita == null) {
                return this.denominazione_ita.toUpperCase().equals(tmp.denominazione_ita.toUpperCase()) &&
                       this.sigla_provincia.toUpperCase().equals(tmp.sigla_provincia.toUpperCase()) &&
                       tmp.data_inizio_validita.isAfter(this.data_inizio_validita) || tmp.data_inizio_validita.equals(this.data_inizio_validita);
            } else {
                return ( this.denominazione_ita.toUpperCase().equals(tmp.denominazione_ita.toUpperCase()) &&
                         this.sigla_provincia.toUpperCase().equals(tmp.sigla_provincia.toUpperCase()) &&
                        tmp.data_inizio_validita.isAfter(this.data_inizio_validita) || tmp.data_inizio_validita.equals(this.data_inizio_validita)) &&
                        (tmp.data_inizio_validita.isBefore(this.data_fine_validita) || tmp.data_inizio_validita.equals(this.data_fine_validita));
            }
        }
    }
}
