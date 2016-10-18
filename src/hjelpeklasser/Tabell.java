/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import eksempelklasser.Komparator;
import java.util.*;

/**
 *
 * @author RudiAndre
 */
public class Tabell {

    private Tabell() {
    }

    public static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0) // fra er negativ
        {
            throw new ArrayIndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > tablengde) // til er utenfor tabellen
        {
            throw new ArrayIndexOutOfBoundsException("til(" + til + ") > tablengde(" + tablengde + ")");
        }

        if (fra > til) {
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    public static void vhKontroll(int tablengde, int v, int h) {
        if (v < 0) {
            throw new ArrayIndexOutOfBoundsException("v(" + v + ") < 0");
        }

        if (h >= tablengde) {
            throw new ArrayIndexOutOfBoundsException("h(" + h + ") >= tablengde(" + tablengde + ")");
        }

        if (v > h + 1) {
            throw new IllegalArgumentException("v = " + v + ", h = " + h);
        }
    }

    public static void bytt(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void bytt(char[] c, int i, int j) {
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }

    public static int[] randPerm(int n) // en effektiv versjon
    {
        Random r = new Random();         // en randomgenerator
        int[] a = new int[n];            // en tabell med plass til n tall

        Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

        for (int k = n - 1; k > 0; k--) // løkke som går n - 1 ganger
        {
            int i = r.nextInt(k + 1);        // en tilfeldig tall fra 0 til k
            bytt(a, k, i);                   // bytter om
        }

        return a;                        // permutasjonen returneres
    }

    public static void randPerm(int[] a) // stokker om a
    {
        Random r = new Random();     // en randomgenerator

        for (int k = a.length - 1; k > 0; k--) {
            int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
            bytt(a, k, i);
        }
    }

    public static int maks(int[] a, int fra, int til) {

        if (a == null) {
            throw new NullPointerException("Parametertabellen a er null!");
        }

        fratilKontroll(a.length, fra, til);

        if (fra == til) {
            throw new NoSuchElementException("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
        }

        int m = fra;              // indeks til største verdi i a[fra:til>
        int maksverdi = a[fra];   // største verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maksverdi) {
                m = i;                // indeks til største verdi oppdateres
                maksverdi = a[m];     // største verdi oppdateres
            }
        }
        return m;  // posisjonen til største verdi i a[fra:til>
    }

    public static int maks(int[] a) // bruker hele tabellen
    {
        return maks(a, 0, a.length);     // kaller metoden over
    }

    public static int min(int[] a, int fra, int til) {
        if (fra < 0 || til > a.length || fra >= til) {
            throw new IllegalArgumentException("Illegalt intervall!");
        }

        int m = fra;                // indeks til minste verdi i a[fra:til>
        int minverdi = a[fra];      // minste verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++) {
            if (a[i] < minverdi) {
                m = i;                 // indeks til minste verdi oppdateres
                minverdi = a[m];       // minste verdi oppdateres
            }
        }
        return m;       // posisjonen til minste verdi i a[fra:til>
    }

    public static int min(int[] a) {
        return min(a, 0, a.length);
    }

    public static void skriv(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        if (til - fra > 0) {
            System.out.print(a[fra]);
            for (int i = fra + 1; i < til; i++) {
                System.out.print(" " + a[i]);
            }
        }
    }

    public static void skriv(int[] a) {
        skriv(a, 0, a.length);
    }

    public static void skrivln(int[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }

    public static void skrivln(int[] a) {
        skrivln(a, 0, a.length);
    }

    public static int[] nestMaks(int[] a) {
        if (a.length < 2) {
            throw new NoSuchElementException("For få verdier");
        }

        int m = 0;
        int nm = 1;
        if (a[1] > a[0]) {
            m = 1;
            nm = 0;
        }

        int maksverdi = a[m];
        int nestmaksverdi = a[nm];

        for (int i = 2; i < a.length; i++) {
            if (a[i] > nestmaksverdi) {
                if (a[i] > maksverdi) {
                    nestmaksverdi = maksverdi;
                    nm = m;
                    maksverdi = a[i];
                    m = i;
                } else {
                    nestmaksverdi = a[i];
                    nm = i;
                }
            }
        }
        /*
            if (a[mv] >= a[mh]) nm = mv;
            else nm = mh;       // if else kan også skrives som:
                                //nm = a[mv] >= a[mh] ? mv : mh;*/
        return new int[]{m, nm};
    }

    public static int[] nestMin(int[] a) {
        int n = a.length;   // tabellens lengde

        if (n < 2) {
            throw new IllegalArgumentException("a.length(" + n + ") < 2!");
        }

        int m = Tabell.min(a);   // m er posisjonen til tabellens minste verdi

        int nm = 0;   // nm står for nestmin

        if (m == 0) {
            nm = Tabell.min(a, 1, n);              // leter i a[1:n>
        } else if (m == n - 1) {
            nm = Tabell.min(a, 0, n - 1);     // leter i a[0:n-1>
        } else {
            int mv = Tabell.min(a, 0, m);                   // leter i a[0:m>
            int mh = Tabell.min(a, m + 1, n);                 // leter i a[m+1:n>
            nm = a[mh] < a[mv] ? mh : mv;           // hvem er minst?
        }

        int[] b = {m, nm};
        return b;  // minste verdi i b[0], nest minste i b[1]
    }

    public static void kopier(int[] a, int i, int[] b, int j, int ant) {
        while (i < ant) {
            b[j++] = a[i++];
        }
    }

    public static void snu(int[] a, int v, int h) // snur intervallet a[v:h]
    {
        while (v < h) {
            bytt(a, v++, h--);
        }
    }

    public static void snu(int[] a, int v) // snur fra og med v og ut tabellen
    {
        snu(a, v, a.length - 1);
    }

    public static void snu(int[] a) // snur hele tabellen
    {
        snu(a, 0, a.length - 1);
    }

    public static boolean nestePermutasjon(int[] a) {
        int i = a.length - 2;                    // i starter nest bakerst
        while (i >= 0 && a[i] > a[i + 1]) {
            i--;   // går mot venstre
        }
        if (i < 0) {
            return false;                 // a = {n, n-1, . . . , 2, 1}
        }
        int j = a.length - 1;                    // j starter bakerst
        while (a[j] < a[i]) {
            j--;                 // stopper når a[j] > a[i] 
        }
        bytt(a, i, j);
        snu(a, i + 1);               // bytter og snur

        return true;                             // en ny permutasjon
    }

    public static int inversjoner(int[] a) {
        int antall = 0;

        for (int i = 0; i < a.length - 1; i++) {
            int x = a[i];
            for (int j = i + 1; j < a.length; j++) {
                int y = a[j];

                if (x > y) {
                    antall++;
                }
            }
        }
        return antall;
    }

    public static boolean erSortert(int[] a) {
        for (int i = 1; i < a.length; i++) // starter med i = 1
        {
            if (a[i - 1] > a[i]) {
                return false;      // en inversjon
            }
        }
        return true;
    }

    public static void utvalgssortering(int[] a) {  //1.3.4 a)
        for (int i = 0; i < a.length - 1; i++) {
            bytt(a, i, min(a, i, a.length));  // to hjelpemetoder
        }
    }

    public static void utvalgssortering(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        for (int i = fra; i < til - 1; i++) {
            bytt(a, i, min(a, i, a.length));  // to hjelpemetoder
        }
    }

    public static int lineærsøk(int[] a, int verdi) // legges i class Tabell
    {
        if (a.length == 0 || verdi > a[a.length - 1]) {
            return -(a.length + 1);  // verdi er større enn den største
        }
        int i = 0;
        for (; a[i] < verdi; i++);  // siste verdi er vaktpost

        return verdi == a[i] ? i : -(i + 1);   // sjekker innholdet i a[i]
    }

    public static int lineærsøk(int[] a, int k, int verdi) // legges i class Tabell
    {
        if (k < 1) {
            throw new IllegalArgumentException("Må ha k > 0!");
        }

        int j = k - 1;
        for (; j < a.length && verdi > a[j]; j += k);

        int i = j - k + 1;  // søker i a[j-k+1:j]
        for (; i < a.length && verdi > a[i]; i++);

        if (i < a.length && a[i] == verdi) {
            return i;  // funnet
        } else {
            return -(i + 1);
        }
    }

    public static int kvadratrotsøk(int[] a, int verdi) {
        return lineærsøk(a, (int) Math.sqrt(a.length), verdi);
    }

    public static int binærsøk(int[] a, int fra, int til, int verdi) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;  // v og h er intervallets endepunkter

        while (v < h) // obs. må ha v < h her og ikke v <= h
        {
            int m = (v + h) / 2;  // heltallsdivisjon - finner midten

            if (verdi > a[m]) {
                v = m + 1;   // verdi må ligge i a[m+1:h]
            } else {
                h = m;                   // verdi må ligge i a[v:m]
            }
        }
        if (h < v || verdi < a[v]) {
            return -(v + 1);  // ikke funnet
        } else if (verdi == a[v]) {
            return v;            // funnet
        } else {
            return -(v + 2);                       // ikke funnet
        }
    }

    public static int binærsøk(int[] a, int verdi) // søker i hele a
    {
        return binærsøk(a, 0, a.length, verdi);  // bruker metoden over
    }

    public static void innsettingssortering(int[] a) {
        for (int i = 1; i < a.length; i++) // starter med i = 1
        {
            int verdi = a[i], j = i - 1;      // verdi er et tabellelemnet, j er en indeks
            for (; j >= 0 && verdi < a[j]; j--) {
                a[j + 1] = a[j];  // sammenligner og flytter
            }
            a[j + 1] = verdi;                 // j + 1 er rett sortert plass
        }
    }

    public static void innsettingssortering(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        for (int i = fra + 1; i < til; i++) // starter med i = 1
        {
            int verdi = a[i], j = i - 1;      // verdi er et tabellelemnet, j er en indeks
            for (; j >= fra && verdi < a[j]; j--) {
                a[j + 1] = a[j];  // sammenligner og flytter
            }
            a[j + 1] = verdi;                 // j + 1 er rett sortert plass
        }
    }

    public static void shell(int[] a, int k) {
        for (int i = k; i < a.length; i++) {
            int temp = a[i], j = i - k;
            for (; j >= 0 && temp < a[j]; j -= k) {
                a[j + k] = a[j];
            }
            a[j + k] = temp;
        }
    }

    private static int parter0(int[] a, int v, int h, int skilleverdi) {
        while (true) // stopper når v >= h
        {
            while (v <= h && a[v] < skilleverdi) {
                v++;   // h er stoppverdi for v
            }
            while (v <= h && a[h] >= skilleverdi) {
                h--;  // v er stoppverdi for h      
            }
            if (v < h) {
                Tabell.bytt(a, v++, h--);          // bytter om a[v] og a[h]
            } else {
                return v;                             // partisjoneringen er ferdig
            }
        }
    }

    public static int parter(int[] a, int fra, int til, int skilleverdi) {
        fratilKontroll(a.length, fra, til);
        return parter0(a, fra, til - 1, skilleverdi);
    }

    public static int parter(int[] a, int skilleverdi) // hele tabellen
    {
        return parter0(a, 0, a.length - 1, skilleverdi);
    }

    private static int sParter0(int[] a, int v, int h, int indeks) {
        bytt(a, indeks, h);           // skilleverdi a[indeks] flyttes bakerst
        int pos = parter0(a, v, h - 1, a[h]);  // partisjonerer a[v:h − 1]
        bytt(a, pos, h);              // bytter for å få skilleverdien på rett plass
        return pos;                   // returnerer posisjonen til skilleverdien
    }

    public static int sParter(int[] a, int fra, int til, int indeks) {
        fratilKontroll(a.length, fra, til);

        if (fra == til) {
            throw new IllegalArgumentException("Intervallet a[" + fra + ": " + til + "> er tomt!");
        }

        if (indeks < fra || indeks >= til) {
            throw new IllegalArgumentException("indeks(" + indeks + ") er utenfor intervallet!");
        }

        return sParter0(a, fra, til - 1, indeks);
    }

    public static int sParter(int[] a, int indeks) {
        if (indeks < 0 || indeks >= a.length) {
            throw new IllegalArgumentException("indeks(" + indeks + ") er utenfor tabellen!");
        }

        return sParter0(a, 0, a.length - 1, indeks);
    }

    public static int antallOmbyttinger(int[] a, int s) {
        int antall = 0, m = s - 1;
        for (int i = 0; i < m; i++) {
            if (a[i] > m) {
                antall++;
            }
        }
        return antall;
    }

    private static void kvikksortering0(int[] a, int v, int h) // en privat metode
    {
        System.out.println("Kallet med [" + v + ":" + h + "] starter!");
        if (v >= h) {
            return;   // stopper på 20, men andre verdier kan være bedre
        }
        int k = sParter0(a, v, h, (v + h) / 2);  // bruker midtverdien
        if (v < k - 1) kvikksortering0(a, v, k - 1);          // sorterer intervallet a[v:k-1]
        if (k + 1 < h) kvikksortering0(a, k + 1, h);          // sorterer intervallet a[k+1:h]
        System.out.println("Kallet med [" + v + ":" + h + "] er ferdig!");
    }

    public static void kvikksortering(int[] a, int fra, int til) // a[fra:til>
    {
        fratilKontroll(a.length, fra, til);  // sjekker når metoden er offentlig
        kvikksortering0(a, fra, til - 1);    // v = fra, h = til - 1
        innsettingssortering(a, fra, til);   // avslutter med innsettingssortering
    }

    public static void kvikksortering(int[] a) // sorterer hele tabellen
    {
        if (a.length > 1) kvikksortering0(a, 0, a.length - 1);
        innsettingssortering(a);  // avslutter med innsettingssortering
    }

    public static int kvikksøk(int[] a, int m) {
        if (m < 0 || m >= a.length) {
            throw new IllegalArgumentException("m(" + m + ") er ulovlig!");
        }

        int v = 0, h = a.length - 1;  // intervallgrenser

        while (true) {
            int k = sParter0(a, v, h, (v + h) / 2);   // se Programkode 1.3.9 f)
            if (m < k) {
                h = k - 1;
            } else if (m > k) {
                v = k + 1;
            } else {
                return k;
            }
        }
    }

    public static double median(int[] a) {
        if (a.length == 0) {
            throw new NoSuchElementException("Tom tabell!");
        }

        int k = kvikksøk(a, a.length / 2);
        return (a.length & 1) == 0 ? (a[k - 1] + a[k]) / 2.0 : a[k];
    }

    public static int[] enkelFletting(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];  // en tabell av rett størrelse
        int k = Math.min(a.length, b.length);    // lengden på den korteste

        for (int i = 0; i < k; i++) {
            c[2 * i] = a[i];        // først en verdi fra a
            c[2 * i + 1] = b[i];    // så en verdi fra b
        }
        // vi må ta med resten
        System.arraycopy(a, k, c, 2 * k, a.length - k);
        System.arraycopy(b, k, c, 2 * k, b.length - k);

        return c;
    }

    public static String enkelFletting(String a, String b) {
        int k = Math.min(a.length(), b.length());  // lengden på den korteste  
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < k; i++) {
            s.append(a.charAt(i)).append(b.charAt(i));
        }

        s.append(a.substring(k)).append(b.substring(k));

        return s.toString();
    }

    public static int flett(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            c[k++] = a[i] <= b[j] ? a[i++] : b[j++];
        }

        while (i < m) {
            c[k++] = a[i++];   // tar med resten av a
        }
        while (j < n) {
            c[k++] = b[j++];   // tar med resten av b
        }
        return k;   // antallet verdier som er lagt inn i c
    }

    public static int flett(int[] a, int[] b, int[] c) // legges i samleklassen Tabell
    {
        return flett(a, a.length, b, b.length, c);
    }

    private static void flett(int[] a, int[] b, int fra, int m, int til) {
        int n = m - fra;                // antall elementer i a[fra:m>
        System.arraycopy(a, fra, b, 0, n);  // kopierer a[fra:m> over i b[0:n>

        int i = 0, j = m, k = fra;      // løkkeST0r og indekser

        while (i < n && j < til) // fletter b[0:n> og a[m:til> og
        {                               // legger resultatet i a[fra:til>
            a[k++] = b[i] <= a[j] ? b[i++] : a[j++];
        }

        while (i < n) {
            a[k++] = b[i++];  // tar med resten av b[0:n>
        }
    }

    private static void flettesortering(int[] a, int[] b, int fra, int til) {
        if (til - fra <= 1) {
            return;   // a[fra:til> har maks ett element
        }
        int m = (fra + til) / 2;        // midt mellom fra og til

        flettesortering(a, b, fra, m);   // sorterer a[fra:m>
        flettesortering(a, b, m, til);   // sorterer a[m:til>

        if (a[m - 1] > a[m]) {
            flett(a, b, fra, m, til);  // fletter a[fra:m> og a[m:til>
        }
    }

    public static void flettesortering(int[] a) {
        int[] b = Arrays.copyOf(a, a.length / 2);   // en hjelpetabell for flettingen
        flettesortering(a, b, 0, a.length);          // kaller metoden over
    }

    public static int union(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0;             // indekser for a, b og c
        while (i < m && j < n) {
            if (a[i] < b[j]) {
                c[k++] = a[i++];  // tar med a[i]
            } else if (a[i] == b[j]) // a[i] og b[j] er like
            {
                c[k++] = a[i++];
                j++;            // tar med a[i], men ikke b[j]
            } else {
                c[k++] = b[j++];             // tar med b[j]
            }
        }

        while (i < m) {
            c[k++] = a[i++];       // tar med resten av a[0:m>
        }
        while (j < n) {
            c[k++] = b[j++];       // tar med resten av b[0:n>
        }
        return k;                            // antall verdier i unionen
    }

    public static int union(int[] a, int[] b, int[] c) {
        return union(a, a.length, b, b.length, c);
    }

    public static int snitt(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0;             // indekser for a, b og c
        while (i < m && j < n) {
            if (a[i] < b[j]) {
                i++;              // hopper over a[i]
            } else if (a[i] == b[j]) // a[i] og b[j] er like
            {
                c[k++] = a[i++];
                j++;            // tar med a[i], men ikke b[j]
            } else {
                j++;                         // hopper over b[j]
            }
        }

        return k;                            // antall i snittet
    }

    public static int snitt(int[] a, int[] b, int[] c) // hele tabeller
    {
        return snitt(a, a.length, b, b.length, c);
    }

    public static int differens(int[] a, int m, int[] b, int n, int[] c) {
        if (m < 0 || m > a.length) {
            throw new IllegalArgumentException("a[0:m> er ulovlig!");
        }

        if (n < 0 || n > b.length) {
            throw new IllegalArgumentException("b[0:n> er ulovlig!");
        }

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            System.out.println("a[i]: " + a[i] + " & " + "b[j]: " + b[j]);
            if (a[i] < b[j]) {
                System.out.println(a[i] + " < " + b[j] + " er sant");
                c[k++] = a[i++];
            } else if (a[i] == b[j]) {
                System.out.println(a[i] + " == " + b[j] + " er sant");
                i++;
                j++;
            } else {
                System.out.println(a[i] + " > " + b[j] + " er sant");
                j++;
            }
        }
        while (i < m) {

            c[k++] = a[i++];
        }

        return k;
    }

    public static int differens(int[] a, int[] b, int[] c) {
        return differens(a, a.length, b, b.length, c);
    }

    public static boolean inklusjon(int[] a, int m, int[] b, int n) {
        if (m < 0 || m > a.length) {
            throw new IllegalArgumentException("a[0:m> er ulovlig!");
        }

        if (n < 0 || n > b.length) {
            throw new IllegalArgumentException("b[0:n> er ulovlig!");
        }

        int i = 0, j = 0;

        while (i < m && j < n) {
            if (a[i] < b[j]) {
                i++;
            } else if (a[i] == b[j]) {
                i++;
                j++;
            } else {
                return false;
            }
        }

        return j == n;
    }

    public static boolean inklusjon(int[] a, int[] b) {
        return inklusjon(a, a.length, b, b.length);
    }

    public static int xunion(int[] a, int m, int[] b, int n, int[] c) {
        if (m < 0 || m > a.length) {
            throw new IllegalArgumentException("a[0:m> er ulovlig!");
        }

        if (n < 0 || n > b.length) {
            throw new IllegalArgumentException("b[0:n> er ulovlig!");
        }

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (a[i] < b[j]) {
                c[k++] = a[i++];
            } else if (a[i] == b[j]) {
                i++;
                j++;
            } else {
                c[k++] = b[j++];
            }
        }
        while (i < m) {
            c[k++] = a[i++];
        }
        while (j < n) {
            c[k++] = b[j++];
        }

        return k;
    }

    public static int xunion(int[] a, int[] b, int[] c) {
        return xunion(a, a.length, b, b.length, c);
    }

    public static int maks(double[] a) // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        double maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i] > maksverdi) {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdaters
            }
        }
        return m;     // returnerer posisjonen til største verdi
    }

    public static int maks(String[] a) // legges i class Tabell
    {
        int m = 0;                          // indeks til største verdi
        String maksverdi = a[0];            // største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(maksverdi) > 0) {
                maksverdi = a[i];  // største verdi oppdateres
                m = i;             // indeks til største verdi oppdaters
            }
        }
        return m;  // returnerer posisjonen til største verdi
    }

    public static int maks(char[] a) // legges i class Tabell
    {
        int m = 0;                           // indeks til største verdi
        char maksverdi = a[0];             // største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i] > maksverdi) {
                maksverdi = a[i];     // største verdi oppdateres
                m = i;                // indeks til største verdi oppdaters
            }
        }
        return m;     // returnerer posisjonen til største verdi
    }

    public static int maks(Integer[] a) // legges i class Tabell
    {
        int m = 0;                          // indeks til største verdi
        Integer maksverdi = a[0];            // største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(maksverdi) > 0) {
                maksverdi = a[i];  // største verdi oppdateres
                m = i;             // indeks til største verdi oppdaters
            }
        }
        return m;  // returnerer posisjonen til største verdi
    }

    public static <T extends Comparable<? super T>> int maks(T[] a) // legges i class Tabell
    {
        int m = 0;                          // indeks til største verdi
        T maksverdi = a[0];            // største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(maksverdi) > 0) {
                maksverdi = a[i];  // største verdi oppdateres
                m = i;             // indeks til største verdi oppdaters
            }
        }
        return m;  // returnerer posisjonen til største verdi
    }

    public static <T extends Comparable<? super T>> void innsettingssortering(T[] a) {
        for (int i = 1; i < a.length; i++) // starter med i = 1
        {
            T verdi = a[i];        // verdi er et tabellelemnet 
            int j = i - 1;        // j er en indeks
            // sammenligner og forskyver:
            for (; j >= 0 && verdi.compareTo(a[j]) < 0; j--) {
                a[j + 1] = a[j];
            }

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    /* public static void bytt(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }*/
    
    public static <T> void bytt(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static Integer[] randPermInteger(int n) {
        Integer[] a = new Integer[n];               // en Integer-tabell
        Arrays.setAll(a, i -> i + 1);               // tallene fra 1 til n

        Random r = new Random();   // hentes fra  java.util

        for (int k = n - 1; k > 0; k--) {
            int i = r.nextInt(k + 1);  // tilfeldig tall fra [0,k]
            bytt(a, k, i);             // bytter om
        }
        return a;  // tabellen med permutasjonen returneres
    }

    public static void skriv(Object[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);

        for (int i = fra; i < til; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public static void skriv(Object[] a) {
        skriv(a, 0, a.length);
    }

    public static void skrivln(Object[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }

    public static void skrivln(Object[] a) {
        skrivln(a, 0, a.length);
    }

    public static <T> void innsettingssortering(T[] a, Comparator<? super T> c) {
        for (int i = 1; i < a.length; i++) { // starter med i = 1

            T verdi = a[i];        // verdi er et tabellelemnet 
            int j = i - 1;        // j er en indeks

            // sammenligner og forskyver:
            for (; j >= 0 && c.compare(verdi, a[j]) < 0; j--) {
                a[j + 1] = a[j];
            }

            a[j + 1] = verdi;      // j + 1 er rett sortert plass
        }
    }

    public static <T>
            int binærsøk(T[] a, int fra, int til, T verdi, Comparator<? super T> c) {
        Tabell.fratilKontroll(a.length, fra, til);  // se Programkode 1.2.3 a)
        int v = fra, h = til - 1;    // v og h er intervallets endepunkter

        while (v <= h) // fortsetter så lenge som a[v:h] ikke er tom
        {
            int m = (v + h) / 2;     // heltallsdivisjon - finner midten
            T midtverdi = a[m];  // hjelpevariabel for  midtverdien

            int cmp = c.compare(verdi, midtverdi);

            if (cmp > 0) {
                v = m + 1;        // verdi i a[m+1:h]
            } else if (cmp < 0) {
                h = m - 1;   // verdi i a[v:m-1]
            } else {
                return m;                 // funnet
            }
        }

        return -(v + 1);   // ikke funnet, v er relativt innsettingspunkt
    }

    public static <T> int binærsøk(T[] a, T verdi, Comparator<? super T> c) {
        return binærsøk(a, 0, a.length, verdi, c);  // bruker metoden over
    }

    public static <T>
            int parter(T[] a, int v, int h, T skilleverdi, Comparator<? super T> c) {
        while (v <= h && c.compare(a[v], skilleverdi) < 0) {
            v++;
        }
        while (v <= h && c.compare(skilleverdi, a[h]) <= 0) {
            h--;
        }

        while (true) {
            if (v < h) {
                Tabell.bytt(a, v++, h--);
            } else {
                return v;
            }
            while (c.compare(a[v], skilleverdi) < 0) {
                v++;
            }
            while (c.compare(skilleverdi, a[h]) <= 0) {
                h--;
            }
        }
    }

    public static <T> int parter(T[] a, T skilleverdi, Comparator<? super T> c) {
        return parter(a, 0, a.length - 1, skilleverdi, c);  // kaller metoden over
    }

    public static <T>
            int sParter(T[] a, int v, int h, int k, Comparator<? super T> c) {
        if (v < 0 || h >= a.length || k < v || k > h) {
            throw new IllegalArgumentException("Ulovlig parameterverdi");
        }

        bytt(a, k, h);   // bytter - skilleverdien a[k] legges bakerst
        int p = parter(a, v, h - 1, a[h], c);  // partisjonerer a[v:h-1]
        bytt(a, p, h);   // bytter for å få skilleverdien på rett plass

        return p;    // returnerer posisjonen til skilleverdien
    }

    public static <T>
            int sParter(T[] a, int k, Comparator<? super T> c) // bruker hele tabellen
    {
        return sParter(a, 0, a.length - 1, k, c); // v = 0 og h = a.lenght-1
    }

    private static <T>
            void kvikksortering(T[] a, int v, int h, Comparator<? super T> c) {
        if (v >= h) {
            return;  // hvis v = h er a[v:h] allerede sortert
        }
        int p = sParter(a, v, h, (v + h) / 2, c);
        kvikksortering(a, v, p - 1, c);
        kvikksortering(a, p + 1, h, c);
    }

    public static <T>
            void kvikksortering(T[] a, Comparator<? super T> c) // sorterer hele tabellen
    {
        kvikksortering(a, 0, a.length - 1, c);
    }

    private static <T>
            void flett(T[] a, T[] b, int fra, int m, int til, Comparator<? super T> c) {
        int n = m - fra;   // antall elementer i a[fra:m>
        System.arraycopy(a, fra, b, 0, n); // kopierer a[fra:m> over i b[0:n>

        int i = 0, j = m, k = fra;     // løkkevariabler og indekser

        while (i < n && j < til) // fletter b[0:n> og a[m:til>, legger
        {
            a[k++] = c.compare(b[i], a[j]) <= 0 ? b[i++] : a[j++];  // resultatet i a[fra:til>
        }
        while (i < n) {
            a[k++] = b[i++];  // tar med resten av b[0:n>
        }
    }

    public static <T>
            void flettesortering(T[] a, T[] b, int fra, int til, Comparator<? super T> c) {
        if (til - fra <= 1) {
            return;     // a[fra:til> har maks ett element
        }
        int m = (fra + til) / 2;          // midt mellom fra og til

        flettesortering(a, b, fra, m, c);   // sorterer a[fra:m>
        flettesortering(a, b, m, til, c);   // sorterer a[m:til>

        flett(a, b, fra, m, til, c);         // fletter a[fra:m> og a[m:til>
    }

    public static <T> void flettesortering(T[] a, Comparator<? super T> c) {
        T[] b = Arrays.copyOf(a, a.length / 2);
        flettesortering(a, b, 0, a.length, c);  // kaller metoden over
    }

}
