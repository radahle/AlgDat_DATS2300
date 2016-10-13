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
public interface Stakk<T> {

    public void leggInn(T verdi);    // eng: push

    public T kikk();                 // eng: peek

    public T taUt();                 // eng: pop

    public int antall();             // eng: size

    public boolean tom();            // eng: isEmpty

    public void nullstill();         // eng: clear
    
} // interface Stakk
