/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

/**
 *
 * @author RudiAndre
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> implements Beholder<T> {

    private static final class Node<T> // en indre nodeklasse
    {

        private T verdi;                 // nodens verdi
        private Node<T> venstre, høyre;  // venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h) // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) // konstruktør
        {
            this(verdi, null, null);
        }
    } // class Node

    private Node<T> rot;                       // peker til rotnoden
    private int antall;                        // antall noder
    private final Comparator<? super T> comp;  // komparator

    public SBinTre(Comparator<? super T> c) // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public int antall() // antall verdier i treet
    {
        return antall;
    }

    public boolean tom() // er treet tomt?
    {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null) {
            q = p;  // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
            /*  if (cmp < 0) p = p.venstre;
            else p = p.høyre; */
        }
        Node<T> ny = new Node<>(verdi);

        if (q == null) {
            rot = ny;
        } else if (cmp < 0) {
            q.venstre = ny;
        } else {
            q.høyre = ny;
        }

        antall++;

        return true;
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.høyre;
            } else {
                return true;
            }
        }
        return false;
    }

    public T gulv(T verdi) {
        Objects.requireNonNull(verdi, "Verdi er null!");

        Node<T> p = rot;
        T gulv = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                gulv = p.verdi;
                p = p.høyre;
            } else {
                return p.verdi;
            }
        }
        return gulv;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> p = rot, q = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            
            if (cmp < 0) {
                q = p; p = p.venstre;
            }
            else if (cmp > 0) {
                q = p; p = p.høyre;
            }
            else break;
        }
        if (p == null) return false;
        
        if (p.venstre == null || p.høyre == null) {   // tilfelle 1 og 2
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        } else {    // tilfelle 3
            Node<T> r = p.høyre, s = p;
            
            while (r.venstre != null) {
                s = r; r = r.venstre;
            }
            p.verdi = r.verdi;
            
            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }
        antall--;
        return true;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static int høyde(Node<?> p) // ? betyr vilkårlig type
    {
        if (p == null) {
            return -1;          // et tomt tre har høyde -1
        }
        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);                 // kaller hjelpemetoden
    }

    private static <T> void toString(Node<T> p, StringJoiner sj) {
        if (p.venstre != null) {
            toString(p.venstre, sj);
        }
        sj.add(p.verdi.toString());
        if (p.høyre != null) {
            toString(p.høyre, sj);
        }
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        if (!tom()) {
            toString(rot, sj);
        }
        return sj.toString();
    }

} // class SBinTre 

