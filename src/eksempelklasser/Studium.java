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
public enum Studium {
    Data("Ingeniørfag - data"), // enumkonstanten Data
    IT("Informasjonsteknologi"), // enumkonstanten IT
    Anvendt("Anvendt datateknologi"), // enumkonstanten Anvendt
    Elektro("Ingeniørfag - elektronikk og informasjonsteknologi"),
    Enkeltemne("Enkeltemnestudent");   // enumkonstanten Enkeltemne

    private final String fulltnavn;     //instansvariabel

    private Studium(String fulltnavn) {
        this.fulltnavn = fulltnavn;
    }
    
    public String toString() {
        return fulltnavn;
    }

}
