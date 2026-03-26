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
        this.count = nrLogs;
        this.dati = new Log[nrLogs];
    }

    public Logs(int nrLogs) {
        this(nrLogs, 0.25);
    }

    public Logs() {
        // Se non viene specificato il numero di log creo un array con 1000 elementi
        this(1000);
    }

    public Logs(String fileName) {

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

            if(intestazione) {
                nrRighe--;
            }
            return nrRighe;
            
        } catch (Exception e) {
            return -1;
        }
    }

    public void caricaFile(String fileName, boolean intestazione, String separatore, TipoOperazione tipoOperazione) {
        try {
            if (tipoOperazione == TipoOperazione.overwrite) {
                int dimArray = (int)(this.contaRighe(fileName, intestazione) * ( 1 + this.incremento));
                this.dati = new Log[dimArray];
                
            }
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String riga;
            int i = 0;
            if (intestazione) {
                riga = br.readLine();
                this.headers = riga.split(separatore);
            }
            while ((riga = br.readLine()) != null) {
                this.dati[i] = new Log(riga, separatore);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
