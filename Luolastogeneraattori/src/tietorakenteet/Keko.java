package tietorakenteet;

/**
 * Keko -luokka kuvaa keko -tietorakennetta.
 *
 * @author hanranti
 */
public class Keko {

    private int[] taulukko;
    public Object[] objects;
    private int size;
    private final boolean max;

    /**
     * Metodi luo keko -olion, johon voidaan lisätä olioita antaen oliolle arvon
     * ja poistaa aina suurimman tai pienimmän arvon saanut olio keosta riippuen
     * onko keko maksimi- vai minimikeko. Jos metodille annetaan parametrina
     * true, keko on maksimikeko, ja jos false, keko on minimikeko.
     *
     * @param max Jos max on true, keko on maksimikeko, muulloin minimikeko
     */
    public Keko(boolean max) {
        taulukko = new int[8];
        objects = new Object[8];
        size = 0;
        this.max = max;
    }

    /**
     * Metodi lisää kekoon olion ja tälle arvon. Jos olioiden määrä kasvaa yli
     * taulukon koon, taulukon kokoa kasvatetaan kasvataTaulukkoa -metodilla.
     *
     * @param object Olio, joka lisataan kekoon
     * @param k Lisattavan olion saama arvo, joka maarittaa olion sijainnin keossa
     */
    public void insert(Object object, int k) {
        size++;
        if (size >= taulukko.length - 1) {
            kasvataTaulukkoa();
        }
        if (max) {
            insertMax(object, k);
        } else {
            insertMin(object, k);
        }
    }

    private void insertMax(Object object, int k) {
        int i = size;
        while (i > 1 && taulukko[i / 2] < k) {
            taulukko[i] = taulukko[i / 2];
            objects[i] = objects[i / 2];
            i /= 2;
        }
        taulukko[i] = k;
        objects[i] = object;
    }

    private void insertMin(Object object, int k) {
        int i = size;
        while (i > 1 && taulukko[i / 2] > k) {
            taulukko[i] = taulukko[i / 2];
            objects[i] = objects[i / 2];
            i /= 2;
        }
        taulukko[i] = k;
        objects[i] = object;
    }

    /**
     * Metodi palauttaa olion, jonka saama arvo on suurin maksimikeossa tai
     * pienin minimikeossa.
     *
     * @return Palauttaa keon juuressa olevan olion
     */
    public Object juuri() {
        return objects[1];
    }

    /**
     * Metodi poistaa suurimman tai pienimmän arvon saaneen olion keosta
     * riippuen siitä onko keko maksimi vai minimikeko, järjestää keon uudelleen
     * heapify -metodilla ja palauttaa entisen juuren.
     *
     * @return Palauttaa keon juuressa olevan olion
     */
    public Object poistaJuuri() {
        if (size == 0) {
            return null;
        }
        Object juuri = objects[1];
        taulukko[1] = taulukko[size];
        objects[1] = objects[size];
        if (max) {
            taulukko[size] = Integer.MIN_VALUE;
        } else {
            taulukko[size] = Integer.MAX_VALUE;
        }
        objects[size] = null;
        heapify(1);
        size--;
        return juuri;
    }

    private void heapify(int i) {
        if (max) {
            heapifyMax(i);
        } else {
            heapifyMin(i);
        }
    }

    private void heapifyMax(int i) {
        int l = 2 * i;
        int r = 2 * i + 1;
        if (r <= size) {
            int largest;
            if (taulukko[l] > (taulukko[r])) {
                largest = l;
            } else {
                largest = r;
            }
            if (taulukko[i] < (taulukko[largest])) {
                vaihda(i, largest);
                heapifyMax(largest);
            }
        } else if (l == size && taulukko[i] < taulukko[l]) {
            vaihda(i, l);
        }
    }

    private void heapifyMin(int i) {
        int l = 2 * i;
        int r = 2 * i + 1;
        if (r <= size) {
            int smallest;
            if (taulukko[l] < (taulukko[r])) {
                smallest = l;
            } else {
                smallest = r;
            }
            if (taulukko[i] > (taulukko[smallest])) {
                vaihda(i, smallest);
                heapifyMin(smallest);
            }
        } else if (l == size && taulukko[i] > taulukko[l]) {
            vaihda(i, l);
        }
    }

    private void vaihda(int a, int b) {
        int arvo = taulukko[a];
        Object object = objects[a];
        taulukko[a] = taulukko[b];
        taulukko[b] = arvo;
        objects[a] = objects[b];
        objects[b] = object;
    }

    private void kasvataTaulukkoa() {
        int[] uusiTaulukko = new int[taulukko.length * 2];
        Object[] uusiObjects = new Object[taulukko.length * 2];
        for (int i = 0; i < taulukko.length; i++) {
            uusiTaulukko[i] = taulukko[i];
            uusiObjects[i] = objects[i];
        }
        taulukko = uusiTaulukko;
        objects = uusiObjects;
    }

    /**
     * Jos keko on tyhja, metodi palauttaa true, muulloin false.
     *
     * @return Jos keko on tyhja, palauttaa true, muulloin false
     */
    public boolean tyhja() {
        if (size == 0) {
            return true;
        }
        return false;
    }
}
