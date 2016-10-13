package hjelpeklasser;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnkeltLenketListe<T> implements Liste<T>, Kø<T> {

    @Override
    public T kikk() {
        if (tom()) {
            throw new NoSuchElementException("Køen er tom!");
        }
        return hent(0);
    }

    @Override
    public T taUt() {
        if (tom()) {
            throw new NoSuchElementException("Køen er tom!");
        }
        return fjern(0);
    }

    private static final class Node<T> // en indre nodeklasse
    {

        private T verdi;                       // nodens verdi
        private Node<T> neste;                 // den neste noden

        private Node(T verdi, Node<T> neste) // konstruktør
        {
            this.verdi = verdi;
            this.neste = neste;
        }
    }  // Node

    private Node<T> hode, hale;  // pekere til første og siste node
    private int antall;          // antall verdier/noder i listen
    private int antallEndringer; // endringer i listen

    private Node<T> finnNode(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) {
            p = p.neste;
        }
        return p;
    }

    public EnkeltLenketListe() // standardkonstruktør
    {
        hode = hale = null;        // hode og hale til null
        antall = 0;                // ingen verdier - listen er tom
        antallEndringer = 0;        // ingen endringer når vi starer
    }

    public EnkeltLenketListe(T... a) {
        this();  // alle variabelene er nullet

        // Finner den første i a som ikke er null
        int i = 0;
        for (; i < a.length && a[i] == null; i++);

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null);  // den første noden
            antall = 1;                                 // vi har minst en node

            for (i++; i < a.length; i++) {
                if (a[i] != null) {
                    p = p.neste = new Node<>(a[i], null);   // en ny node
                    antall++;
                }
            }
            hale = p;
        }
        hode = hale = null;
        antall = 0;
    }

    @Override
    public boolean leggInn(T verdi) // verdi legges bakerst
    {
        Objects.requireNonNull(verdi, "Tabellen er null!");

        if (tom()) {
            hale = hode = new Node<>(verdi, null);
        } else {
            hale = hale.neste = new Node<>(verdi, null);
        }
        antall++;
        antallEndringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) // verdi til posisjon indeks
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);        // true: indeks = antall er lovlig

        if (tom()) {
            hale = hode = new Node<>(verdi, null);
        } else if (indeks == 0) {
            hode = new Node<>(verdi, hode);
        } else if (indeks == antall) {
            hale = hale.neste = new Node<>(verdi, null);
        } else {
            Node<T> p = hode;
            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }

            p.neste = new Node<>(verdi, p.neste);
        }
        antall++;
        antallEndringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;
        }

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall; indeks++) {
            if (p.verdi.equals(verdi)) {
                return indeks;
            }
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "nullverdi");
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T temp = p.verdi;

        p.verdi = verdi;

        antallEndringer++;
        return temp;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if (indeks == 0) {                       // skal første verdi fjernes?
            temp = hode.verdi;                   // tar vare på verdien som skal fjernes
            hode = hode.neste;                   // hode flyttes til neste node
            if (antall == 1) {
                hale = null;        // det var kun en verdi i listen
            }
        } else {
            Node<T> p = finnNode(indeks - 1);       // p er noden foran den som skal fjernes
            Node<T> q = p.neste;                    // q skal fjernes
            temp = q.verdi;                         // tar vare på verdien som skal fjernes

            if (q == hale) {
                hale = p;                // q er siste node
            }
            p.neste = q.neste;                      // hopper over q
        }
        antall--;               // reduserer antallet
        antallEndringer++;
        return temp;            // returnerer fjernet verdi
    }

    @Override
    public boolean fjern(T verdi) // verdi skal fjernes
    {
        if (verdi == null) {
            return false;          // ingen nullverdier i listen
        }
        Node<T> q = hode, p = null;               // hjelpepekere

        while (q != null) // q skal finne verdien t
        {
            if (q.verdi.equals(verdi)) {
                break;       // verdien funnet
            }
            p = q;
            q = q.neste;                     // p er forgjengeren til q
        }

        if (q == null) {
            return false;              // fant ikke verdi
        } else if (q == hode) {
            hode = hode.neste;    // går forbi q
        } else {
            p.neste = q.neste;                   // går forbi q
        }
        if (q == hale) {
            hale = p;                  // oppdaterer hale
        }
        q.verdi = null;                           // nuller verdien til q
        q.neste = null;                           // nuller nestepeker

        antall--;                                 // en node mindre i listen
        antallEndringer++;

        return true;                              // vellykket fjerning
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        Node<T> p = hode, q = null;

        while (p != null) {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        antallEndringer++;
    }

    @Override
    public Iterator<T> iterator() {
        return new EnkeltLenketListeIterator();
    }

    private class EnkeltLenketListeIterator implements Iterator<T> {

        private Node<T> p = hode;
        private boolean fjernOK = false;
        private int forventetAntallEndringer = antallEndringer;

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("ikke flere verdier");
            }

            if (forventetAntallEndringer != antallEndringer) {
                throw new ConcurrentModificationException("Listen er endret");
            }
            fjernOK = true;         // nå kan remove() kalles
            T temp = p.verdi;       // tar vare på verdien i p
            p = p.neste;            // flytter p til den neste noden

            return temp;
        }

        public void remove() {
            if (!fjernOK) {
                throw new IllegalStateException("Ulovlig tilstand!");
            }

            if (forventetAntallEndringer != antallEndringer) {
                throw new ConcurrentModificationException("Listen er endret");
            }

            fjernOK = false;               // remove() kan ikke kalles på nytt
            Node<T> q = hode;              // hjelpepeker

            if (hode.neste == p) // skal den første fjernes?
            {
                hode = hode.neste;           // den første fjernes
                if (p == null) {
                    hale = null;  // dette var den eneste noden
                }
            } else {
                Node<T> r = hode;            // må finne forgjengeren
                // til forgjengeren til p
                while (r.neste.neste != p) {
                    r = r.neste;               // flytter r
                }

                q = r.neste;                 // det er q som skal fjernes
                r.neste = p;                 // "hopper" over q
                if (p == null) {
                    hale = r;     // q var den siste
                }
            }

            q.verdi = null;                // nuller verdien i noden
            q.neste = null;                // nuller nestepeker

            antall--;                      // en node mindre i listen
        }
    }

    public boolean fjernHvis(Predicate<? super T> predikat) {
        Objects.requireNonNull(predikat, "null-predikat!");

        Node<T> p = hode, q = null;
        int antallFjernet = 0;

        while (p != null) {
            if (predikat.test(p.verdi)) {
                antallFjernet++;
                antallEndringer++;

                if (p == hode) {
                    if (p == hale) {
                        hale = null;
                    }
                    hode = hode.neste;
                } else if (p == hale) {
                    q.neste = null;
                } else {
                    q.neste = p.neste;
                }
            }
            q = p;
            p = p.neste;
        }

        antall -= antallFjernet;
        return antallFjernet > 0;
    }

    public void forEach(Consumer<? super T> handling) {
        Objects.requireNonNull(handling, "handling er null!");

        Node<T> p = hode;
        while (p != null) {
            handling.accept(p.verdi);
            p = p.neste;
        }
    }

    public void forEachRemaining(Consumer<? super T> handling) {
        Objects.requireNonNull(handling, "handling er null!");

        Node<T> p = hode;
        while (p != null) {
            handling.accept(p.verdi);
            p = p.neste;
        }
    }

    @Override
    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = hode;
        while (p != null) {
            s.add(p.verdi.toString());
            p = p.neste;
        }
        return s.toString();

    }

}  // EnkeltLenketListe
