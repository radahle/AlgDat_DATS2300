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
        for (int i = antall - 1; i >= 0; i--) {
            s.add(a[i] == null ? "null" : a[i].toString());
        }
        return s.toString();
    }

    public static <T> void snu(Stakk<T> A) {
        Stakk<T> B = new TabellStakk<T>();
        Stakk<T> C = new TabellStakk<T>();

        while (!A.tom()) {
            B.leggInn(A.taUt());
        }
        while (!B.tom()) {
            C.leggInn(B.taUt());
        }
        while (!C.tom()) {
            A.leggInn(C.taUt());
        }
    }

    public static <T> void snu2(Stakk<T> A) {
        Stakk<T> B = new TabellStakk<T>();
        int n = A.antall() - 1;

        while (n > 0) {
            T temp = A.taUt();

        }
    }

    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {
        Stakk<T> C = new TabellStakk<T>();

        while (!A.tom()) {
            C.leggInn(A.taUt());
        }
        while (!C.tom()) {
            T t = C.taUt();
            B.leggInn(t);
            A.leggInn(t);
        }

    }

    public static <T> void kopier2(Stakk<T> A, Stakk<T> B) {
        int n = A.antall();

        while (n > 0) {
            for (int j = 0; j < n; j++) {
                B.leggInn(A.taUt());
            }
            T temp = B.kikk();
            for (int j = 0; j < n; j++) {
                A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            n--;
        }
    }

    public static <T> void sorter(Stakk<T> A, Comparator<T> c) {
        Stakk<T> B = new TabellStakk<T>();
        T temp;
        int n = 0;

        while (!A.tom()) {
            temp = A.taUt();
            n = 0;
            while (!B.tom() && c.compare(temp, B.kikk()) < 0) {
                n++;
                A.leggInn(B.taUt());
            }
            B.leggInn(temp);
            for (int i = 0; i < n; i++) {
                B.leggInn(A.taUt());
            }
            while (!B.tom()) {
                A.leggInn(B.taUt());
            }
        }
    }

}  // class TabellStakk
