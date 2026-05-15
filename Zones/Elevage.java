package Zones;
import java.util.*;
import Entities.*;
import capteurs.*;
import releves.*;
import alertes.*;
import common.*;

import java.util.*;
public class Elevage extends Zone {
    private Collection<Animal> list_animaux;
    //Constructeur
    public Elevage(String nom) {
        super(nom);
        this.list_animaux = new ArrayList<>();
    }

    public void ajouter_animal(Animal a) {
        list_animaux.add(a);
    }

    public String afficher_zone() {
        return "Elevage{code=" + code +
                ", nom='" + nom + '\'' +
                ", status=" + status +
                ", nbAnimaux=" + list_animaux.size() +
                ", nbCapteurs=" + all_capteurs.size() + "}";
    }
//getters
public Collection<Animal> getList_animaux() { return list_animaux; }

}
