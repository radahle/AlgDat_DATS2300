/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 *
 * @author RudiAndre
 */
public class TabellKø<T> implements Kø<T> {

    private T[] a;
    private int fra, til;

    @SuppressWarnings("unchecked")
    public TabellKø(int størrelse) {
        if (størrelse < 1) {
            throw new IllegalArgumentException("Må ha størrelse > 0");
        }
        a = (T[]) new Object[størrelse];
        fra = til = 0;
    }

    public TabellKø() {
        this(8);
    }

    private T[] utvid(int nystørrelse) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) new Object[nystørrelse];

        // kopierer intervallet a[fra:a.length> over i b
        System.arraycopy(a, fra, b, 0, a.length - fra);

        // Kopierer intervallet a[0:fra> over i b
        System.arraycopy(a, 0, b, a.length - fra, fra);

        fra = 0;
        til = a.length;
        return b;
    }

    @Override
    public boolean leggInn(T verdi) {
        a[til++] = verdi;
        if (til == a.length) {
            til = 0;
        }

        if (fra == til) {
            a = utvid(2 * a.length);
        }
        return true;
    }

    @Override
    public T kikk() {
        if (tom()) {
            throw new NoSuchElementException("Tom kø");
        }
        return a[fra];
    }

    @Override
    public T taUt() {
        if (tom()) {
            throw new NoSuchElementException("Tom kø");
        }
        T temp = a[fra];
        a[fra] = null;
        fra++;
        if (fra == a.length) {
            fra = 0;
        }
        return temp;
    }

    @Override
    public int antall() {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    @Override
    public boolean tom() {
        return fra == til;
    }

    @Override
    public void nullstill() {
        while (fra != til) {
            a[fra++] = null;
            if (fra == a.length) {
                fra = 0;
            }
        }
    }

    public int indeksTil(T verdi) {
        int k = fra;

        while (k != til) {
            if (verdi.equals(a[k])) {
                return fra <= k ? k - fra : a.length + k - fra;
            }

            k++;
            if (k == a.length) {
                k = 0;
            }
        }
        return -1;  // ikke funnet
    }
    
    public static <T> void snu(Stakk<T> A) {
        Kø<T> B = new TabellKø<T>();
        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) A.leggInn(B.taUt());
    }
    
    public static <T> void snu(Kø<T> A) {
        Stakk<T> B = new TabellStakk<T>();
        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) A.leggInn(B.taUt());
    }

    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        for (int i = fra; i != til;) {
            s.add(a[i].toString());
            i++;
            if (i == a.length) {
                i = 0;
            }
        }
        return s.toString();
    }

}
