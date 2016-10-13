/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Objects;

/**
 *
 * @author RudiAndre
 */
public final class Heltall implements Comparable<Heltall> {

    private final int verdi;    // et heltall som instansvariabel

    public Heltall(int verdi) {
        this.verdi = verdi;
    }   // konstruktør

    public int intVerdi() {
        return verdi;
    }             // aksessor

    public int compareTo(Heltall h) // Heltall som parameter
    {
        return verdi < h.verdi ? -1 : (verdi == h.verdi ? 0 : 1);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;   // sammenligner med seg selv
        }
        if (!(o instanceof Heltall)) {
            return false;  // feil datatype
        }
        return verdi == ((Heltall) o).verdi;
    }

    public boolean equals(Heltall h) {
        return verdi == h.verdi;
    }

    public int hashCode() {
        return 31 + verdi;
    }

    public String toString() {
        return Integer.toString(verdi);
    }

} // class Heltall
