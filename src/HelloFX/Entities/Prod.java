package HelloFX.Entities;
import java.util.*;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.time.* ;

public class Prod {
    private LocalDate date ;
    private LocalTime heure ;
    private Type_Prod type_prod ;
    private int quantite ;
    private String unite ; 

    public Prod(LocalDate date, LocalTime heure, Type_Prod type_prod, int quantite, String unite) {
        this.date = date;
        this.heure = heure;
        this.type_prod = type_prod;
        this.quantite = quantite;
        this.unite = unite;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public Type_Prod getType_prod() {
        return type_prod;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getUnite() {
        return unite;
    }

    public String display_prod(){
        StringBuilder res = new StringBuilder() ; 
        res.append("Date : ").append(date).append("\n");
        res.append("Heure : ").append(heure).append("\n");
        res.append("Type de production : ").append(type_prod).append("\n");
        res.append("Quantité : ").append(quantite).append(" ").append(unite).append("\n");
        return res.toString();
    }
}
