package Entities;
import java.util.*;
import java.time.*;
import Zones.*;
import capteurs.*;
import releves.*;
import alertes.*;
import common.*;

import java.util.*;
import java.time.*;
public class Cult {
    private LocalDate date_plantation;
    private LocalDate date_recolte;
    private Famille type;           // enum Famille : CEREAL, LEGUMES, FRUITS
    private Croissance stade;       // enum Croissance : SEMIS, GERM, CROIS, MAT, REC
    private float min_ph;
    private float max_ph;
    private float min_hum;
    private float max_hum;

    //Constructeur
    public Cult(LocalDate date_plantation, LocalDate date_recolte, Famille type, float min_ph, float max_ph, float min_hum, float max_hum) {
        this.date_plantation = date_plantation;
        this.date_recolte = date_recolte;
        this.type = type;
        this.stade = Croissance.SEMIS; // stade initial
        this.min_ph = min_ph;
        this.max_ph = max_ph;
        this.min_hum = min_hum;
        this.max_hum = max_hum;
    }
    //Met à jour manuellement le stade de croissance.
    public void mettre_a_jour_stade_croissance(Croissance c) {
        this.stade = c;
    }
    //Affiche le stade de croissance actuel
    public String afficher_stade_croissance() {
        StringBuilder res = new StringBuilder("Stade de croissance : " + this.stade.toString() + "\n") ;
        return res.toString();
    }
//Retourne un résumé de l'état de la culture.
public String get_etat_cult() {
    return "Cult{" +
            "type=" + type +
            ", stade=" + stade +
            ", plantation=" + date_plantation +
            ", recolte=" + date_recolte +
            ", pH=[" + min_ph + "-" + max_ph + "]" +
            ", hum=[" + min_hum + "%-" + max_hum + "%]" +
            "}";
}
//Getters / Setters

public LocalDate getDate_plantation() { return date_plantation; }
    public void setDate_plantation(LocalDate date_plantation) { this.date_plantation = date_plantation; }

    public LocalDate getDate_recolte() { return date_recolte; }
    public void setDate_recolte(LocalDate date_recolte) { this.date_recolte = date_recolte; }

    public Famille getType() { return type; }
    public void setType(Famille type) { this.type = type; }

    public Croissance getStade() { return stade; }

    public float getMin_ph() { return min_ph; }
    public void setMin_ph(float min_ph) { this.min_ph = min_ph; }

    public float getMax_ph() { return max_ph; }
    public void setMax_ph(float max_ph) { this.max_ph = max_ph; }

    public float getMin_hum() { return min_hum; }
    public void setMin_hum(float min_hum) { this.min_hum = min_hum; }

    public float getMax_hum() { return max_hum; }
    public void setMax_hum(float max_hum) { this.max_hum = max_hum; }

    @Override
    public String toString() { return get_etat_cult(); }





}
