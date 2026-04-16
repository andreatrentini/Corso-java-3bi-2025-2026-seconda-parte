package libreria_utenti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Comuni {
    private Comune comuni[];

    public Comuni(String fileName) throws Exception {
        this(fileName, true);
    }

    public Comuni(String fileName, boolean intestazione) throws Exception {
        try {
            caricaFile(fileName, intestazione);
        } catch (Exception e) {
        }
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

    private void caricaFile(String fileName, boolean intestazione) throws Exception {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String riga;
            int dimArray = this.contaRighe(fileName, intestazione);
            this.comuni = new Comune[dimArray];
            if (intestazione) {
                riga = br.readLine();
            }
            int i = 0;
            while ((riga = br.readLine()) != null) {
                // System.out.println(riga);
                try {
                    Comune tmp = new Comune(riga);
                    comuni[i] = tmp;
                    i++;
                } catch (Exception e) {
                }
            }
            br.close();
            if (i < comuni.length) {
                this.comuni = Arrays.copyOf(this.comuni, i);
            }
        } catch (Exception e) {
            // Ripristinare i vecchi dati
            throw new Exception("Errore durante il caricamento del file. " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < comuni.length; i++) {
            tmp += comuni[i].toString() + "\n";
        }
        return tmp;
    }
}
