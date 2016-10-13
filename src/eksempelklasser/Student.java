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
public class Student extends Person {
    private final Studium studium;
    
    public Student(String fornavn, String etternavn, Studium studium) {
        super(fornavn, etternavn);
        this.studium = studium;
    }
    
    public String toString() {
        return super.toString() + " " + studium.name();
    }
    
    public Studium studium() {
        return studium;
    }
}
