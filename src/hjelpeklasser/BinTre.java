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
public class BinTre<T> // et generisk binærtre
{

    private static final class Node<T> // en indre nodeklasse
    {

        private T verdi;            // nodens verdi
        private Node<T> venstre;    // referanse til venstre barn/subtre
        private Node<T> høyre;      // referanse til høyre barn/subtre

        private Node(T verdi, Node<T> v, Node<T> h) // konstruktør
        {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) {
            this.verdi = verdi;
        }  // konstruktør

    } // class Node<T>

    private Node<T> rot;      // referanse til rotnoden
    private int antall;       // antall noder i treet

    public BinTre() {
        rot = null;
        antall = 0;
    }          // konstruktør

    public final void leggInn(int posisjon, T verdi) {
       if (posisjon < 1) throw new IllegalArgumentException("posisjon (" + posisjon + ") er < 1!");
       
       Node<T> p = rot, q = null;
       int filter = Integer.highestOneBit(posisjon) >> 1;
       
       while (p != null && filter > 0) {
           q = p;
           p = (filter & posisjon) == 0 ? p.venstre : p.høyre;
           filter >>= 1;
       } 
       
       if (p != null) {
           throw new IllegalArgumentException("posisjon (" + posisjon + ") finnes fra før!");
       } else if (filter > 0) {
           throw new IllegalArgumentException("posisjon (" + posisjon + ") har ingen foreldre!");
       }
       
       p = new Node<>(verdi);
       
       if (q == null) rot = p;
       else if ((posisjon & 1) == 0) q.venstre = p;
       else q.høyre = p;
       
       antall++;
    }  

    public int antall() {
        return antall;
    }               // returnerer antallet

    public boolean tom() {
        return antall == 0;
    }         // tomt tre?

} // class BinTre<T>

