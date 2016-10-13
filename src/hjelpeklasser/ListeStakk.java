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
public class ListeStakk<T> implements Stakk<T> {

    private EnkeltLenketListe<T> liste;

    public ListeStakk() {
        liste = new EnkeltLenketListe<>();
    }

    @Override
    public void leggInn(T verdi) {
        liste.leggInn(0, verdi);
    }

    @Override
    public T kikk() {
        return liste.hent(0);
    }

    @Override
    public T taUt() {
        return liste.fjern(0);
    }

    @Override
    public int antall() {
        return liste.antall();
    }

    @Override
    public boolean tom() {
        return liste.tom();
    }

    @Override
    public void nullstill() {
        liste.nullstill();
    }
    
    public String toString() {
        return liste.toString();
    }

}
