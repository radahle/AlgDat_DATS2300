/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

public interface Kø<T> // eng: Queue
{

    public boolean leggInn(T verdi); // eng: offer/add/enqueue    inn bakerst

    public T kikk();                 // eng: peek/element/front   den første

    public T taUt();                 // eng: poll/remove/dequeue  tar ut den første

    public int antall();             // eng: size                 køens antall

    public boolean tom();            // eng: isEmpty              er køen tom?

    public void nullstill();         // eng: clear                tømmer køen

} // interface Kø
