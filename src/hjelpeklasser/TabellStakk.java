/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;

public class TabellStakk<T> implements Stakk<T> {

    private T[] a;                     // en T-tabell
    private int antall;                // antall verdier på stakken

    public TabellStakk() // konstruktør - tabellengde 8
    {
        this(8);
    }

    @SuppressWarnings("unchecked")     // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde) // valgfri tabellengde
    {
        if (lengde < 0) {
            throw new IllegalArgumentException("Negativ tabellengde!");
        }

        a = (T[]) new Object[lengde];     // oppretter tabellen
        antall = 0;                      // stakken er tom
    }

    @Override
    public void leggInn(T verdi) {
        if (antall == a.length) {
            a
                    = Arrays.copyOf(a, a.length == 0 ? 1 : 2 * a.length);
        }
        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        if (tom()) {
            throw new NoSuchElementException("Stakken er tom");
        }

        return a[antall - 1];
    }

    @Override
    public T taUt() {
        if (tom()) {
            throw new NoSuchElementException("Stakken er tom");
        }

        antall--;
        T verdi = a[antall];
        a[antall] = null;

        return verdi;
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
        while (antall > 0) {
            a[antall--] = null;
        }
    }
    
    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        for (int i = antall - 1; i >= 0; i--) 
            s.add(a[i] == null ? "null" : a[i].toString());
        return s.toString();
    }

}  // class TabellStakk
