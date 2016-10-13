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
        System.arraycopy(a, fra, b, 0, a.length - fra);
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
        a = utvid(2*a.length);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
