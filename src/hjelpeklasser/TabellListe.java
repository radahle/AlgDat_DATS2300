/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;
import java.util.function.*;

/**
 *
 * @author RudiAndre
 */
public class TabellListe<T> implements Liste<T> {

    private T[] a;
    private int antall;
    private int antallEndringer;

    // konstruktører og metoder kommer her
    @SuppressWarnings("unchecked")          // pga. konverteringen: Object[] -> T[]
    public TabellListe(int størrelse) // konstruktør
    {
        a = (T[]) new Object[størrelse];       // oppretter tabellen
        antall = 0;                           // foreløpig ingen verdier
    }

    public TabellListe() // standardkonstruktør
    {
        this(10);                             // startstørrelse på 10
    }

    public TabellListe(T[] b) // en T-tabell som parameter
    {
        this(b.length);                            // kaller den andre konstruktøren

        for (T verdi : b) {
            if (verdi != null) {
                a[antall++] = verdi;  // hopper over null-verdier
            }
        }
    }

    // inneholder(), hent(), indeksTil(), antall() og tom() er aksessorer + toString()
    public int antall() {
        return antall;          // returnerer antallet
    }

    public boolean tom() {
        return antall == 0;     // listen er tom hvis antall er 0
    }

    public T hent(int indeks) {
        indeksKontroll(indeks, false);   // false: indeks = antall er ulovlig
        return a[indeks];                // returnerer er tabellelement
    }

    public int indeksTil(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (a[i].equals(verdi)) {
                return i;   // funnet!
            }
        }
        return -1;   // ikke funnet!
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public boolean leggInn(T verdi) {  // inn bakerst
        Objects.requireNonNull(verdi, "null er ulovlig!");

        if (antall == a.length) {  // En full tabell utvides med 50%
            a = Arrays.copyOf(a, (3 * antall) / 2 + 1);
        }

        a[antall++] = verdi;    // setter inn ny verdi
        antallEndringer++;
        return true;            // vellykket innlegging
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, true); // true: indeks = antall er lovlig

        //En full tabell utvides med 50%
        if (antall == a.length) {
            a = Arrays.copyOf(a, (3 * antall) / 2 + 1);
        }

        // rydder plass til den nye verdien
        System.arraycopy(a, indeks, a, indeks + 1, antall - indeks);

        a[indeks] = verdi;  // setter inn ny verdi
        antall++;           // vellykket innlegging
        antallEndringer++;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, false);  // false: indeks = antall er ulovlig

        T gammelverdi = a[indeks];      // tar vare på den gamle verdien
        a[indeks] = verdi;              // oppdaterer
        antallEndringer++;
        return gammelverdi;             // returnerer den gamle verdien
    }

    @Override
    public boolean fjern(T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");

        for (int i = 0; i < antall; i++) {
            if (a[i].equals(verdi)) {
                antall--;
                System.arraycopy(a, i + 1, a, i, antall - i);

                a[antall] = null;

                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // false: indeks = antall er uovlig
        T verdi = a[indeks];

        antall--;       // sletter ved å flytte verdier mot venstre
        System.arraycopy(a, indeks + 1, a, indeks, antall - indeks);
        a[antall] = null;   //tilrettelegger for "søppeltømming"
        antallEndringer++;

        return verdi;
    }

    @Override
    public void nullstill() {
        if (a.length > 10) {
            a = (T[]) new Object[10];
        } else {
            for (int i = 0; i < antall; i++) {
                a[i] = null;
            }
            antall = 0;
        }
        antallEndringer++;
    }

    @Override
    public String toString() {
        if (antall == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[').append(a[0]);

        for (int i = 1; i < antall; i++) {
            sb.append(',').append(' ').append(a[i]);
        }
        sb.append(']');

        return sb.toString();
    }

    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }

    private class TabellListeIterator implements Iterator<T> {

        private int denne = 0;       // instansvariabel
        private boolean fjernOK = false;
        private int forventetAntallEndringer = antallEndringer;

        public boolean hasNext() // sjekker om det er flere igjen
        {
            return denne < antall;     // sjekker verdien til denne
        }

        public T next() // returnerer aktuell verdi
        {
            if (forventetAntallEndringer != antallEndringer) {
                throw new ConcurrentModificationException("Listen er endret!");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");
            }

            T denneVerdi = a[denne];    // henter aktuell verdi
            denne++;                    // flytter på indeksen
            fjernOK = true;             // nå kan remove() kalles
            return denneVerdi;           //returnerer verdien
        }

        public void remove() {          // ny versjon
            if (forventetAntallEndringer != antallEndringer) {
                throw new ConcurrentModificationException("Listen er endret!");
            }

            if (!fjernOK) {
                throw new IllegalStateException("Ulovlig tilstand!");
            }

            fjernOK = false;            // remove() kan ikke kalles på nytt

            // verdien i denne - 1 skal fjernes da den ble returnert i siste kall
            // på next(), verdiene fra og med denne flyttes derfor en mot venstre
            antall--;               // en verdi vil bli fjernet
            denne--;                // denne må flyttes til venstre

            // tetter igjen
            System.arraycopy(a, denne + 1, a, denne, antall - denne);

            a[antall] = null;       // verdien som lå lengst til høyre nulles

            antallEndringer++;
            forventetAntallEndringer++;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (denne < antall) {
                action.accept(a[denne++]);
            }
        }

    }  // TabellListeIterator

    @Override
    public boolean fjernHvis(Predicate<? super T> p) // betingelsesfjerning
    {
        Objects.requireNonNull(p);                       // kaster unntak

        int nyttAntall = antall;

        for (int i = 0, j = 0; j < antall; j++) {
            if (p.test(a[j])) {
                nyttAntall--;   // a[j] skal fjernes
            } else {
                a[i++] = a[j];               // forskyver 
            }
        }

        for (int i = nyttAntall; i < antall; i++) {
            a[i] = null;   // tilrettelegger for "søppeltømming"
        }

        boolean fjernet = nyttAntall < antall;

        antall = nyttAntall;
        return fjernet;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < antall; i++) {
            action.accept(a[i]);
        }
    }

}
