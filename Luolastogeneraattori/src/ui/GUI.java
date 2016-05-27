package ui;

import luolasto.Pelaaja;

/**
 *
 * @author Hannu
 */
public class GUI {

    /**
     *
     */
    public GUI() {
    }

    /**
     *
     * @param luola
     * @param pelaaja
     */
    public void piirra(boolean[][] luola, Pelaaja pelaaja) {
        for (int i = 0; i < luola[0].length; i++) {
            for (int j = 0; j < luola.length; j++) {
                if (j == pelaaja.getX() && i == pelaaja.getY()) {
                    System.out.print("@");
                } else if (luola[j][i]) {
                    System.out.print("x");
                } else {
                    System.out.print("o");
                }
            }
            System.out.println("");
        }
    }
}
