/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksempelklasser;

/**
 *
 * @author RudiAndre
 */
  @FunctionalInterface
  public interface Funksjon<T,R>    // T for argumenttype, R for returtype
  {
    R anvend(T t);
  }