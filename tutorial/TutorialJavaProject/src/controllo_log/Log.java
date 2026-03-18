package controllo_log;

public class Log {
// Dichiarazione degli attributi
    private int id;
    private String tipoUtente;
    private String allarmeIDS;

    // Definizione dei costruttore
    /**
     * Costruisce un log a partire da una stringa che contiene
     * id,tipoUtente,allarmeIDS
     * 
     * @param riga
     * @param separatore
     */
    public Log(String riga, String separatore) throws Exception {
        try {
            String datiGrezzi[] = riga.split(separatore);
            int id = Integer.parseInt(datiGrezzi[0]);
            this.setId(id);
            this.setTipoUtente(datiGrezzi[1]);
            this.setAllarmeIDS(datiGrezzi[2]);
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("I dati sono sbagliati, impossibile creare log. " + e.getMessage());
        }
    }

    public Log(String riga) throws Exception {
        // L'invocazione di un altro costruttore deve essere la prima riga
        // di un costruttore
        this(riga, ",");        
    }


    public Log(int id, String tipoUtente, String allarmeIDS) throws Exception {
        try {
            this.setId(id);
            this.setTipoUtente(tipoUtente);
            this.setAllarmeIDS(allarmeIDS);
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("I dati sono sbagliati, impossibile creare log. " + e.getMessage());
        }
    }

    // Getter

    public int getId() {
        return this.id;
    }

    public String getTipoUtente() {
        return this.tipoUtente;
    }

    public String getAllarmeIDS() {
        return this.allarmeIDS;
    }

    /*
     * setter: metodo che consente di impostare il valore di un attributo
     */
    /**
     * 
     * @param value
     * @throws Exception
     */
    public void setId(int value) throws Exception {
        if (value > 0) {
            this.id = value;
        } else {
            throw new Exception("Valore per id non consentito");
        }
    }

    /**
     * 
     * @param value
     * @throws Exception
     */
    public void setTipoUtente(String value) throws Exception {
        if (value.equals("Dipendente") || value.equals("Hacker")) {
            this.tipoUtente = value;
        } else {
            throw new Exception("Tipo utente non corretto");
        }
    }

    /**
     * 
     * @param value
     * @throws Exception
     */
    public void setAllarmeIDS(String value) throws Exception {
        if (value.equals("Sì") || value.equals("No")) {
            this.allarmeIDS = value;
        } else {
            throw new Exception("AllarmeIDs non corretto.");
        }
    }

    /**
     * 
     * @return
     */
    public boolean veroPositivo() {
        return this.tipoUtente.equals("Hacker") && this.allarmeIDS.equals("Sì");
    }

    public boolean veroNegativo() {
        return this.tipoUtente.equals("Dipendente") && this.allarmeIDS.equals("No");
    }

    public boolean falsoPositivo() {
        return this.tipoUtente.equalsIgnoreCase("Dipendente") && this.allarmeIDS.equalsIgnoreCase("Sì");
    }

    public boolean falsoNegativo() {
        return this.tipoUtente.equalsIgnoreCase("Hacker") && this.allarmeIDS.equalsIgnoreCase("No");
    }

    @Override
    public String toString() {
        String separatore = ", ";
        return this.id + separatore + this.tipoUtente + separatore + this.allarmeIDS;
    }

}
