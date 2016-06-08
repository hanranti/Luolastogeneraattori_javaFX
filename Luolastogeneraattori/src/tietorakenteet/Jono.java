package tietorakenteet;

/**
 * Jono -luokka kuvaa jono -tietorakennetta, jossa jonon loppuun voidaan lisätä
 * olioita ja jonon alusta voidaan poistaa olioita.
 *
 * @author hanranti
 */
public class Jono {

    private Object[] taulukko;
    private int alku, loppu, koko;

    /**
     * Metodi luo jono -olion. Jono sisältää taulukon johon jono tallennetaan.
     * Jonon loppuun voidaan tallentaa olioita ja alusta voidaan poistaa
     * olioita.
     *
     */
    public Jono() {
        taulukko = new Object[10];
        alku = 0;
        loppu = 0;
        koko = 0;
    }

    /**
     * Metodi lisää olion jonon loppuun. Jos taulukko on lisäyksen jälkeen täysi,
     * taulukon kokoa kasvatetaan kasvataTaulukkoa -metodilla.
     *
     * @param object
     */
    public void push(Object object) {
        if (loppu >= taulukko.length) {
            loppu = 0;
        }
        taulukko[loppu] = object;
        loppu++;
        if (loppu == alku) {
            kasvataTaulukkoa();
        }
        koko++;
    }

    /**
     * Metodi poistaa ja palauttaa jonon ensimmäisen alkion.
     *
     * @return
     */
    public Object poll() {
        if (this.tyhja()) {
            return null;
        }
        Object object = taulukko[alku];
        alku++;
        koko--;
        return  object;
    }

    private void kasvataTaulukkoa() {
        Object[] uusiTaulukko = new Object[taulukko.length * 2];
        int i = 0;
        while (alku != loppu) {
            uusiTaulukko[i] = taulukko[alku];
            if (alku >= taulukko.length) {
                alku = 0;
            }
            alku++;
            i++;
        }
        alku = 0;
        loppu = koko;
    }

    /**
     * Jos jono on tyhjä, metodi palauttaa true, muulloin false.
     *
     * @return
     */
    public boolean tyhja() {
        if (koko == 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodi palauttaa montako alkioita jonossa on.
     *
     * @return
     */
    public int koko() {
        return koko;
    }
}