package luolasto;

import java.util.Random;
import tietorakenteet.Jono;
import tietorakenteet.Kaari;
import tietorakenteet.Keko;
import tietorakenteet.Lista;
import tietorakenteet.Matematiikka;
import tietorakenteet.Piste;
import tietorakenteet.Solmu;
import tietorakenteet.UnionFind;

/**
 * Luokka sisältää luolan seinien ja avointen alueiden generointiin tarvittavat
 * metodit.
 *
 * @author hanranti
 */
public class Luolageneraattori {

    private final Luolasto luolasto;
    private final Random random;
    private final int size, uusiaUloskaynteja;
    private int todennakoisyys;

    /**
     * Metodi luo Luolageneraattori -olion, joka voi generoida luoliin avoimia
     * alueita.
     *
     * @param luolasto
     * @param size
     * @param uusiaUloskaynteja
     */
    public Luolageneraattori(Luolasto luolasto, int size, int uusiaUloskaynteja) {
        random = new Random();
        this.luolasto = luolasto;
        this.size = size;
        this.uusiaUloskaynteja = uusiaUloskaynteja;
        this.todennakoisyys = 100;
    }

    /**
     * Metodi generoi luolaan seiniä ja avoimia alueita. Luolaan luodaan
     * satunnaisesti huoneita, uloskäyntejä ja näitä yhdistäviä käytäviä.
     *
     * @param luola
     */
    public void generoi(Luola luola) {
//        System.out.println("generoi");
        //        for (int i = 0; i < luola.length; i++) {
        //            for (int j = 0; j < luola[0].length; j++) {
        //                if (i == 0 || j == 0 || i == luola.length - 1 || j == luola[0].length - 1) {
        //                    luola[i][j] = true;
        //                }
        //            }
        //        }
        //        luola[0][(luola.length - 1) / 2] = false;
        //        luola[luola.length - 1][(luola.length - 1) / 2] = false;
        //        luola[(luola.length - 1) / 2][0] = false;
        //        luola[(luola.length - 1) / 2][luola.length - 1] = false;
        Jono qX = new Jono();
        Jono qY = new Jono();
        Jono dist = new Jono();
        int s = random.nextInt(8) + 1;
        int m = size;
        m /= 10;
        int m2 = m;
        m *= s;
        m2 *= (10 - s);
        m++;
        luoHuoneet(luola, qX, qY, dist, m);
        luoUloskaynnit(luola, qX, qY, dist);
        luoKaytavat(luola, qX, qY, dist);
        generoiAvoimetAlueet(luola, qX, qY, dist, m2);
        generoiReunat(luola);
        if (todennakoisyys > 0) {
            todennakoisyys -= uusiaUloskaynteja;
        }
        System.out.println("todennakoisyys: " + todennakoisyys);
    }

    private void luoUloskaynnit(Luola luola, Jono qX, Jono qY, Jono dist) {
        int luolaX = luola.getLuolaX();
        int luolaY = luola.getLuolaY();
        Lista huoneet = luola.getHuoneet();
//        System.out.println("luoUloskaynnit");
        boolean uusiUloskayntiLisatty = true;
        boolean viereinenNull[] = new boolean[4];
        int nullMaara = 0;
        int maara = 0;
        if (random.nextInt(101) <= todennakoisyys) {
            System.out.println("luodaan uusia luolia");
            maara = random.nextInt(5) + 1;
            uusiUloskayntiLisatty = false;
        }

        if (luolasto.getLuola(luolaX - 1, luolaY) != null) {
//            System.out.println("x-1");
            for (int i = 0; i < size; i++) {
                if (luolasto.getLuola(luolaX - 1, luolaY).getLuola()[size - 1][i]) {
                    qX.push(0);
                    qY.push(i);
                    dist.push(random.nextInt(1) + 1);
                    huoneet.add(new Piste(0, i));
                }
            }
        } else {
            while (maara > 0 && random.nextInt(4) < 3) {
                qX.push(0);
                int y = 1 + random.nextInt(size - 2);
                qY.push(y);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(0, y));
                maara--;
                uusiUloskayntiLisatty = true;
            }
            viereinenNull[0] = true;
            nullMaara++;
        }
        if (luolasto.getLuola(luolaX + 1, luolaY) != null) {
//            System.out.println("x+1");
            for (int i = 0; i < size; i++) {
                if (luolasto.getLuola(luolaX + 1, luolaY).getLuola()[0][i]) {
                    qX.push(size - 1);
                    qY.push(i);
                    dist.push(random.nextInt(1) + 1);
                    huoneet.add(new Piste(size - 1, i));
                }
            }
        } else {
            while (maara > 0 && random.nextInt(4) < 3) {
                qX.push(size - 1);
                int y = 1 + random.nextInt(size - 2);
                qY.push(y);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(size - 1, y));
                maara--;
                uusiUloskayntiLisatty = true;
            }
            viereinenNull[1] = true;
            nullMaara++;
        }
        if (luolasto.getLuola(luolaX, luolaY - 1) != null) {
//            System.out.println("y-1");
            for (int i = 0; i < size; i++) {
                if (luolasto.getLuola(luolaX, luolaY - 1).getLuola()[i][size - 1]) {
                    qX.push(i);
                    qY.push(0);
                    dist.push(random.nextInt(1) + 1);
                    huoneet.add(new Piste(i, 0));
                }
            }
        } else {
            while (maara > 0 && random.nextInt(4) < 3) {
                qY.push(0);
                int x = 1 + random.nextInt(size - 2);
                qX.push(x);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(x, 0));
                maara--;
                uusiUloskayntiLisatty = true;
            }
            viereinenNull[2] = true;
            nullMaara++;
        }
        if (luolasto.getLuola(luolaX, luolaY + 1) != null) {
//            System.out.println("y+1");
            for (int i = 0; i < size; i++) {
                if (luolasto.getLuola(luolaX, luolaY + 1).getLuola()[i][0]) {
                    qX.push(i);
                    qY.push(size - 1);
                    dist.push(random.nextInt(1) + 1);
                    huoneet.add(new Piste(i, size - 1));
                }
            }
        } else {
            while (maara > 0 && random.nextInt(3) < 2) {
                qY.push(size - 1);
                int x = 1 + random.nextInt(size - 2);
                qX.push(x);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(x, size - 1));
                maara--;
                uusiUloskayntiLisatty = true;
            }
            viereinenNull[3] = true;
            nullMaara++;
        }
        if (!uusiUloskayntiLisatty) {
            int xy = random.nextInt(nullMaara);
            if (!viereinenNull[3]) {
                xy++;
            }
            if (xy == 0) {
                qY.push(size - 1);
                int x = 1 + random.nextInt(size - 2);
                qX.push(x);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(x, size - 1));
                return;
            }
            if (!viereinenNull[2]) {
                xy++;
            }
            if (xy == 1) {
                qY.push(0);
                int x = 1 + random.nextInt(size - 2);
                qX.push(x);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(x, 0));
                return;
            }
            if (!viereinenNull[1]) {
                xy++;
            }
            if (xy == 2) {
                qX.push(size - 1);
                int y = 1 + random.nextInt(size - 2);
                qY.push(y);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(size - 1, y));
                return;
            }
            if (!viereinenNull[0]) {
                xy++;
            }
            if (xy == 3) {
                qX.push(0);
                int y = 1 + random.nextInt(size - 2);
                qY.push(y);
                dist.push(random.nextInt(1) + 1);
                huoneet.add(new Piste(0, y));
                return;
            }
        }
    }

    private void luoKaytavat(Luola luola, Jono qX, Jono qY, Jono dist) {
        Lista huoneet = luola.getHuoneet();
//        System.out.println("luoKaytavat");
//        int maara = random.nextInt(huoneet.size());
//        while (maara > 0) {
//            Piste huone1 = huoneet.get(random.nextInt(huoneet.size()));
//            Piste huone2 = huoneet.get(random.nextInt(huoneet.size()));
//            generoiKaytava(qX, qY, dist, huone1.getX(), huone1.getY(),
//                    huone2.getX(), huone2.getY());
//            maara--;
//        }
        UnionFind unionFind = new UnionFind();
        Keko keko = new Keko(false);
        Solmu solmu1 = huoneet.getFirst();
        while (solmu1 != null) {
            Piste h = (Piste) solmu1.getObject();
            unionFind.makeSet(h);
            Solmu solmu2 = huoneet.getFirst();
            while (solmu2 != null) {
                Piste h2 = (Piste) solmu2.getObject();
                solmu2 = solmu2.getOikea();
                if (h.equals(h2)) {
                    continue;
                }
                Kaari kO = new Kaari(h, h2,
                        (int) Matematiikka.hypotenuusanPituus(
                                Math.abs(h.getX() - h2.getX()),
                                Math.abs(h.getY() - h2.getY())));
                keko.insert(kO, kO.getLength());
            }
            solmu1 = solmu1.getOikea();
        }
        while (unionFind.getKomponentit() > 1 && !keko.tyhja()) {
            Kaari u = (Kaari) keko.poistaJuuri();
            if (unionFind
                    .find(
                            u
                            .getO1())
                    != unionFind.
                    find(
                            u
                            .getO2())) {
                unionFind.union(u.getO1(), u.getO2());
                generoiKaytava(qX, qY, dist, u.getO1().getX(), u.getO1().getY(),
                        u.getO2().getX(), u.getO2().getY());
            }
        }
    }

    private void generoiKaytava(Jono qX, Jono qY, Jono dist, int x, int y, int loppuX, int loppuY) {
//        System.out.println("generoiKaytava");
        if (x == loppuX && y == loppuY) {
            return;
        }
        if (x == 0) {
            qX.push(x + 1);
        } else if (x == size - 1) {
            qX.push(x - 1);
        } else {
            qX.push(x);
        }
        if (y == 0) {
            qY.push(y + 1);
        } else if (y == size - 1) {
            qY.push(y - 1);
        } else {
            qY.push(y);
        }
        dist.push(random.nextInt(1) + 1);
        if (Math.abs(loppuX - x) > Math.abs(loppuY - y)) {
            if (x > loppuX) {
                generoiKaytava(qX, qY, dist, x - 1, y, loppuX, loppuY);
            } else {
                generoiKaytava(qX, qY, dist, x + 1, y, loppuX, loppuY);
            }
        } else {
            if (y > loppuY) {
                generoiKaytava(qX, qY, dist, x, y - 1, loppuX, loppuY);
            } else {
                generoiKaytava(qX, qY, dist, x, y + 1, loppuX, loppuY);
            }
        }
    }

    private void luoHuoneet(Luola luola, Jono qX, Jono qY, Jono dist, int m) {
        Lista huoneet = luola.getHuoneet();
//        System.out.println("luohuoneet");
        int maara = random.nextInt(m) + 1;
        for (int i = 0; i < maara; i++) {
            Piste piste = new Piste(1 + random.nextInt(size - 2), 1 + random.nextInt(size - 2));
            huoneet.add(piste);
            qX.push(piste.getX());
            qY.push(piste.getY());
            dist.push(random.nextInt(5) + 8);
        }
//        Solmu solmu = huoneet.getFirst();
//        while (solmu != null) {
//            Piste huone = (Piste) solmu.getObject();
//            qX.push(huone.getX());
//            qY.push(huone.getY());
//            dist.push(random.nextInt(5) + 8);
//            solmu = solmu.getOikea();
//        }
    }

    private void generoiAvoimetAlueet(Luola l, Jono qX, Jono qY, Jono dist, int s) {
        boolean[][] luola = l.getLuola();
        int luolaX = l.getLuolaX();
        int luolaY = l.getLuolaY();
        char[][] asd = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                asd[i][j] = ' ';
            }
        }
        for (int i = 0; i < qX.koko(); i++) {
            int x = (int) qX.poll();
            int y = (int) qY.poll();
            asd[x][y] = 'o';
            qX.push(x);
            qY.push(y);
        }
        Solmu solmu = l.getHuoneet().getFirst();
        while (solmu != null) {
            Piste piste = (Piste) solmu.getObject();
            asd[piste.getX()][piste.getY()] = 'x';
            solmu = solmu.getOikea();
        }
        for (int i = 0; i < asd[0].length; i++) {
            for (int j = 0; j < asd.length; j++) {
                System.out.print(asd[j][i]);
            }
            System.out.println("");
        }
        System.out.println("generoiHuoneet");
        int[][] aloitusX = new int[size][size];
        int[][] aloitusY = new int[size][size];
        for (int i = 0; i < qX.koko(); i++) {
            int x = (int) qX.poll();
            int y = (int) qY.poll();
            aloitusX[x][y] = x;
            aloitusY[x][y] = y;
            qX.push(x);
            qY.push(y);
        }
        boolean[][] color = new boolean[size][size];
        while (!qX.tyhja()) {
            int x = (int) qX.poll();
            int y = (int) qY.poll();
            int d = (int) dist.poll();
            luola[x][y] = true;
            if ((x == size - 1 && luolasto.getLuola(luolaX + 1, luolaY) != null
                    && !luolasto.getLuola(luolaX + 1, luolaY).getLuola()[0][y])
                    || (y == size - 1 && luolasto.getLuola(luolaX, luolaY + 1) != null
                    && !luolasto.getLuola(luolaX, luolaY + 1).getLuola()[x][0])
                    || x == 0 && luolasto.getLuola(luolaX - 1, luolaY) != null
                    && !luolasto.getLuola(luolaX - 1, luolaY).getLuola()[size - 1][y]
                    || y == 0 && luolasto.getLuola(luolaX, luolaY - 1) != null
                    && !luolasto.getLuola(luolaX, luolaY - 1).getLuola()[x][size - 1]) {
                luola[x][y] = false;
                continue;
            }

            if (x + 1 < size - 1 && !color[x + 1][y] && (Matematiikka.hypotenuusanPituus(Math.abs(x - aloitusX[x][y]), Math.abs(y - aloitusY[x][y])) < d || random.nextInt(4) < 1)) {
                color[x + 1][y] = true;
                aloitusX[x + 1][y] = aloitusX[x][y];
                qX.push(x + 1);
                qY.push(y);
                dist.push(d);
            }
            if (x - 1 > 0 && !color[x - 1][y] && (Matematiikka.hypotenuusanPituus(Math.abs(x - aloitusX[x][y]), Math.abs(y - aloitusY[x][y])) < d || random.nextInt(4) < 1)) {
                color[x - 1][y] = true;
                aloitusX[x - 1][y] = aloitusX[x][y];
                qX.push(x - 1);
                qY.push(y);
                dist.push(d);
            }
            if (y + 1 < size - 1 && !color[x][y + 1] && (Matematiikka.hypotenuusanPituus(Math.abs(x - aloitusX[x][y]), Math.abs(y - aloitusY[x][y])) < d || random.nextInt(4) < 1)) {
                color[x][y + 1] = true;
                aloitusX[x][y + 1] = aloitusX[x][y];
                qX.push(x);
                qY.push(y + 1);
                dist.push(d);
            }
            if (y - 1 > 0 && !color[x][y - 1] && (Matematiikka.hypotenuusanPituus(Math.abs(x - aloitusX[x][y]), Math.abs(y - aloitusY[x][y])) < d || random.nextInt(4) < 1)) {
                color[x][y - 1] = true;
                aloitusX[x][y - 1] = aloitusX[x][y];
                qX.push(x);
                qY.push(y - 1);
                dist.push(d);
            }
            color[x][y] = true;
        }
    }

    private void generoiReunat(Luola luola) {
        boolean[][] color = new boolean[size][size];
        Jono qX = new Jono();
        Jono qY = new Jono();
        if (!luola.getLuola()[1][0] && !luola.getLuola()[0][1] && luola.getLuola()[1][1] && random.nextBoolean()) {
            qX.push(1);
            qY.push(1);
            color[1][1] = true;
        }
        if (!luola.getLuola()[size - 2][0] && !luola.getLuola()[size - 1][1] && luola.getLuola()[size - 2][1] && random.nextBoolean()) {
            qX.push(size - 2);
            qY.push(1);
            color[size - 2][1] = true;
        }
        if (!luola.getLuola()[size - 2][size - 1] && !luola.getLuola()[size - 1][size - 2] && luola.getLuola()[size - 2][size - 2] && random.nextBoolean()) {
            qX.push(size - 2);
            qY.push(size - 2);
            color[size - 2][size - 2] = true;
        }
        if (!luola.getLuola()[0][size - 2] && !luola.getLuola()[1][size - 1] && luola.getLuola()[1][size - 2] && random.nextBoolean()) {
            qX.push(1);
            qY.push(size - 2);
            color[1][size - 2] = true;
        }
        for (int i = 1; i < size - 2; i++) {
            if (!luola.getLuola()[i][0] && luola.getLuola()[i][1] && random.nextBoolean()) {
                qX.push(i);
                qY.push(1);
                color[i][1] = true;
            }
            if (!luola.getLuola()[0][i] && luola.getLuola()[1][i] && random.nextBoolean()) {
                qX.push(1);
                qY.push(i);
                color[1][i] = true;
            }
            if (!luola.getLuola()[i][size - 1] && luola.getLuola()[i][size - 2] && random.nextBoolean()) {
                qX.push(i);
                qY.push(size - 2);
                color[i][size - 2] = true;
            }
            if (!luola.getLuola()[size - 1][i] && luola.getLuola()[size - 2][i] && random.nextBoolean()) {
                qX.push(size - 2);
                qY.push(i);
                color[size - 2][i] = true;
            }
        }
        char[][] asd = new char[size][size];
        for (int i = 0; i < asd[0].length; i++) {
            for (int j = 0; j < asd.length; j++) {
                asd[j][i] = ' ';
            }
        }
        while (!qX.tyhja()) {
            int x = (int) qX.poll();
            int y = (int) qY.poll();
            asd[x][y] = 'k';
            luola.getLuola()[x][y] = false;
            if (!(x + 1 == size - 1 && luola.getLuola()[size - 1][y])
                    && (!vieressaSeinia(luola, color, x + 1, y)) && !color[x + 1][y]
                    && random.nextInt(3) < 1) {
                qX.push(x + 1);
                qY.push(y);
                color[x + 1][y] = true;
            }
            if (!(x - 1 == 0 && luola.getLuola()[0][y])
                    && (!vieressaSeinia(luola, color, x - 1, y)) && !color[x - 1][y]
                    && random.nextInt(3) < 1) {
                qX.push(x - 1);
                qY.push(y);
                color[x - 1][y] = true;
            }
            if (!(y + 1 == size - 1 && luola.getLuola()[x][size - 1])
                    && (!vieressaSeinia(luola, color, x, y + 1)) && !color[x][y + 1]
                    && random.nextInt(3) < 1) {
                qX.push(x);
                qY.push(y + 1);
                color[x][y + 1] = true;
            }
            if (!(y - 1 == 0 && luola.getLuola()[x][0])
                    && (!vieressaSeinia(luola, color, x, y - 1)) && !color[x][y - 1]
                    && random.nextInt(3) < 1) {
                qX.push(x);
                qY.push(y - 1);
                color[x][y - 1] = true;
            }
        }
        for (int i = 0; i < asd[0].length; i++) {
            for (int j = 0; j < asd.length; j++) {
                System.out.print(asd[j][i]);
            }
            System.out.println("");
        }
    }

    private boolean vieressaSeinia(Luola luola, boolean[][] color, int x, int y) {
        if (x > 1) {
            if (y > 1) {
                if (!luola.getLuola()[x - 1][y - 1] && !color[x - 1][y - 1]) {
                    return true;
                }
            }
            if (!luola.getLuola()[x - 1][y] && !color[x - 1][y]) {
                return true;
            }
            if (y < size - 2) {
                if (!luola.getLuola()[x - 1][y + 1] && !color[x - 1][y + 1]) {
                    return true;
                }
            }
        }
        if (y > 1) {
            if (!luola.getLuola()[x][y - 1] && !color[x][y - 1]) {
                return true;
            }
        }
        if (y < size - 2) {
            System.out.println("x: " + x + " y: " + y);
            if (!luola.getLuola()[x][y + 1] && !color[x][y + 1]) {
                return true;
            }
        }
        if (x < size - 2) {
            if (y > 1) {
                if (!luola.getLuola()[x + 1][y - 1] && !color[x + 1][y - 1]) {
                    return true;
                }
            }
            if (!luola.getLuola()[x + 1][y] && !color[x + 1][y]) {
                return true;
            }
            if (y < size - 2) {
                if (!luola.getLuola()[x + 1][y + 1] && !color[x + 1][y + 1]) {
                    return true;
                }
            }
        }
        return false;
    }
}
