/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksempelklasser;

import java.util.*;

/**
 *
 * @author RudiAndre
 */
public class Person implements Comparable<Person> {

    private final String fornavn;         // personens fornavn
    private final String etternavn;       // personens etternavn

    public Person(String fornavn, String etternavn) // konstruktør
    {
        Objects.requireNonNull(fornavn, "fornavn er null");
        Objects.requireNonNull(etternavn, "etternavn er null");
        
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public String fornavn() {
        return fornavn;
    }       // aksessor

    public String etternavn() {
        return etternavn;
    }   // aksessor

    public int compareTo(Person p) // pga. Comparable<Person>
    {
        int cmp = etternavn.compareTo(p.etternavn);     // etternavn
        if (cmp != 0) {
            return cmp;             // er etternavnene ulike?
        }
        return fornavn.compareTo(p.fornavn);  // sammenligner fornavn
    }

    public boolean equals(Object o) // vår versjon av equals
    {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Person p = (Person)o;
        return etternavn.equals(p.etternavn) && fornavn.equals(p.fornavn);
    }
    
    public boolean equals(Person p) {
        if (p == this) {
            return true;
        }
        if (p == null) {
            return false;
        }
        return etternavn.equals(p.etternavn) && fornavn.equals(p.fornavn);
    }

    public int hashCode() {
        return Objects.hash(etternavn, fornavn);
    }

    public String toString() {
        return String.join(" ", fornavn, etternavn);//fornavn + " " + etternavn;
    }

} // class Person
