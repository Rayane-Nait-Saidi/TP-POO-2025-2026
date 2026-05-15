package Entities;
import java.util.*;
import java.time.*;
import Zones.*;
import capteurs.*;
import releves.*;
import alertes.*;
import common.*;

import java.util.*;
public class Animal implements Programmable {
    //attributs
    private int id;
    private int age;
    private Type espece;
    private float poid;
    private Etat_Sante etat_sante;
    private Prog_Ali programme_aliment;
    private Collection<Event_Sanitaire> list_event_sanit;
    private GPS collierGPS;
    private static int cpt = 0 ; 

    //constructeur
    public Animal(int age, Type espece, float poid) {
        this.id = cpt; cpt++ ;
        this.age = age;
        this.espece = espece;
        this.poid = poid;
        this.etat_sante = Etat_Sante.SAIN;
        this.list_event_sanit = new ArrayList<>();
        this.programme_aliment = null;
        this.collierGPS = null;
    }
    public void definir_programme(String type_aliment, int quantite) {
        this.programme_aliment = new Prog_Ali(type_aliment, quantite);
    }


    public String afficher_prog() {
        StringBuilder sb = new StringBuilder();
        if (programme_aliment != null) {
            sb.append(programme_aliment.displayProgram());
        } else {
            sb.append("Aucun programme alimentaire défini pour animal #").append(id);
        }
        return sb.toString();
    }

    public void consigne_event_sanitaire(Event_Sanitaire e) {
        list_event_sanit.add(e);
        if (e.getType() == Type_Event_Sant.MALADIES) {
            this.etat_sante = Etat_Sante.MALADE;
        }
    }
    public void attacherCollierGPS(GPS gps) { this.collierGPS = gps; }
    public void retirerCollierGPS() { this.collierGPS = null; }
    public boolean possedeCapteurGPS() { return collierGPS != null; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Type getEspece() { return espece; }
    public void setEspece(Type espece) { this.espece = espece; }

    public float getPoid() { return poid; }
    public void setPoid(float poid) { this.poid = poid; }

    public Etat_Sante getEtat_sante() { return etat_sante; }
    public void setEtat_sante(Etat_Sante etat_sante) { this.etat_sante = etat_sante; }

    public Prog_Ali getProgramme_aliment() { return programme_aliment; }
    public Collection<Event_Sanitaire> getList_event_sanit() { return list_event_sanit; }
    public GPS getCollierGPS() { return collierGPS; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Animal{");
        sb.append("id=").append(id);
        sb.append(", espece=").append(espece);
        sb.append(", age=").append(age).append(" mois");
        sb.append(", poid=").append(poid).append(" kg");
        sb.append(", etat=").append(etat_sante);
        sb.append(", GPS=").append(possedeCapteurGPS() ? "oui" : "non");
        sb.append(", nbEvents=").append(list_event_sanit.size());
        sb.append("}");
        return sb.toString();
    }


}
