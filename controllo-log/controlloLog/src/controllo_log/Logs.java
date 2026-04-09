package controllo_log;

import java.io.BufferedReader;
import java.io.FileReader;

public class Logs {
    private Log dati[];
    private double incremento;
    private String fileName;
    private String headers[];
    private String separatore;
    private boolean intestazione;
    private String rawData[];
    private int count;

    public Logs(int nrLogs, double incremento) {
        this.incremento = incremento;
        this.dati = new Log[nrLogs];
    }

    public Logs(int nrLogs) {
        this(nrLogs, 0.25);
    }

    public Logs() {
        // Se non viene specificato il numero di log creo un array con 1000 elementi
        this(1000);
    }

    public Logs(String fileName, boolean intestazione, String separatore) {
        this.incremento = 0.25;
        this.caricaFile(fileName, intestazione, separatore, TipoOperazione.overwrite);
    }

    // Getter e Setter

    public Log getLog(int indice) throws Exception{
        if (indice >= 0 && indice < this.count) {
            return this.dati[indice];
        }
        else {
            throw new Exception("Indice furi range.");
        }
    }

    public double getIncremento() {
        return this.incremento;
    }

    // .. completate

    public void setIncremento(double nuovoIncremento) throws Exception {
        if (nuovoIncremento >= 0 && nuovoIncremento <= 1) {
            this.incremento = nuovoIncremento;
        }
        else {
            throw new Exception("Il valore dell'incremento deve essere compreso fra 0 e 1");
        }
    }

    public int getSpazioLibero() {
        return this.dati.length - this.count;
    }

    private void aumentaDimensione() {
        this.aumentaDimensione(0);
    }

    private void aumentaDimensione(int nrNuoviElementi) {
        int nuovaDimensione = (int) (this.dati.length * this.incremento) + this.dati.length + nrNuoviElementi;
        Log tmp[] = new Log[nuovaDimensione];
        for (int i = 0; i < this.count; i++) {
            tmp[i] = this.dati[i];
        }
        this.dati = tmp;
    }

    private int contaRighe(String fileName, boolean intestazione) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            int nrRighe = 0;

            while (br.readLine() != null) {
                nrRighe++;
            }

            br.close();

            if (intestazione) {
                nrRighe--;
            }
            return nrRighe;

        } catch (Exception e) {
            return -1;
        }
    }

    public void caricaFile(String fileName, boolean intestazione, String separatore, TipoOperazione tipoOperazione) {
        // Creo una copia di dati
        Log tmp[] = null;
        if (this.count > 0) {
            tmp = new Log[this.count];
            for (int i = 0; i < this.count; i++) {
                tmp[i] = this.dati[i];
            }
        }
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            this.fileName = fileName;
            this.intestazione = intestazione;
            this.separatore = separatore;
            String riga;
            int nrNuoveRighe = this.contaRighe(fileName, intestazione);
            if (tipoOperazione == TipoOperazione.overwrite) {
                int dimArray = (int) (nrNuoveRighe * (1 + this.incremento));
                this.dati = new Log[dimArray];
                this.count = 0;
            }
            else {                
                this.aumentaDimensione(nrNuoveRighe);
            }
            if (intestazione) {
                riga = br.readLine();
                this.headers = riga.split(separatore);
            }
            this.rawData = new String[nrNuoveRighe];
            while ((riga = br.readLine()) != null) {
                this.dati[this.count] = new Log(riga, separatore);
                this.rawData[this.count] = riga;
                this.count++;
            }
            br.close();
        } catch (Exception e) {
            // Ripristinare i vecchi dati
            
            if (tmp != null) {
                this.dati = new Log[(int)(tmp.length * (1+this.incremento))];
                for (int i = 0; i < tmp.length; i++) {
                    this.dati[i] = tmp[i];
                }
                this.count = tmp.length;
            }
        }
    }

    public void add(Log log) {
        if (this.getSpazioLibero() == 0) {
            this.aumentaDimensione(1);           
        }
        this.dati[this.count] = log;
        this.count++;
    }

    public void add(Log logs[]) {
        if (this.getSpazioLibero() < logs.length) {
            this.aumentaDimensione(logs.length);
        }
        for (int i = 0; i < logs.length; i++) {
            this.dati[this.count] = logs[i];
            this.count++;
        }
    }

    public void compatta() {
        if (this.count == 0) {
            this.dati = new Log[1000];
        }
        else {
            Log tmp[] = new Log[this.count];
            for (int i = 0; i < this.count; i++) {
                tmp[i] = this.dati[i];
            }
            this.dati = tmp;
        }
    }
}
