package controllo_log;

import java.io.BufferedReader;
import java.io.FileReader;

public class Logs {
    private Log dati[];
    private String fileName;
    private String headers[];

    public Logs(int nrLogs) {
        this.dati = new Log[nrLogs];
    }

    public Logs() {
        // Se non viene specificato il numero di log creo un array con 1000 elementi
        this(1000);
    }

    public Logs(String fileName) {

    }

    public void caricaFile(String fileName, boolean intestazione, String separatore) {
        try {
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
