package libreria_utenti;

public class Comune {
    private String sigla_provincia;
    private String denominazione_ita;
    private String codice_belfiore;

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

        } catch (Exception e) {
            throw new Exception("Errore durante la creazione dell'oggetto. " + e.getMessage());
        }
    }

    public Comune(String sigla_provincia, String denominazione_ita) {

            this.sigla_provincia = sigla_provincia;
            this.denominazione_ita = denominazione_ita.replace("\"", "");
    }

    public String getCodiceComune() {
        return this.codice_belfiore;
    }

    @Override
    public String toString() {
        return this.sigla_provincia + ";" + this.denominazione_ita + ";" + this.codice_belfiore;        
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Comune tmp = (Comune) obj;

        return this.denominazione_ita.toUpperCase().equals(tmp.denominazione_ita.toUpperCase()) &&
               this.sigla_provincia.toUpperCase().equals(tmp.sigla_provincia.toUpperCase());
    }
}
