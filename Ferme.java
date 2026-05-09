import java.util.*;
//Gère l'ensemble des zones et des alertes.
public class Ferme implements Activable {
    //attributs
    private Collection<Zone> all_zones;
    //private Collection<Alerte> alertes;
    private Hist_Alerte alertes;
    private Hist_Alerte_GPS alertes_gps ;

    //constructeur
    public Ferme() {
        this.all_zones = new ArrayList<>();
        this.alertes = new Hist_Alerte();
        this.alertes_gps = new Hist_Alerte_GPS() ;
    }

    // Ajoute une zone à la ferme.


    public void ajouter_zone(Zone z) {
        all_zones.add(z);
    }

    public void activer(Zone z) {
        z.activer();
    }

    public void suspendue(Zone z) {
        z.suspendre();
    }

    //Retourne un affichage de toutes les zones de la ferme.

    public String afficher_all_zones() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Zones de la ferme ===\n");
        for (Zone z : all_zones) {
            sb.append(z.afficher_zone()).append("\n");
        }
        return sb.toString();
    }


//Gestion des alertes
    //Acquitte une alerte (acknowledge).

public void engistrer_alerte(Alerte a) {
    alertes.Enregistrer_Alerte(a);
}

public void engistrer_alerte_gps(Alerte_GPS a) {
    alertes_gps.Enregistrer_Alerte_GPS(a);
}

//method that searches for an alerte in alertes
private boolean find_alerte(Alerte a) {
    for (Alerte al : alertes.getContent()) {
        if (al.equals(a)) {
            return true;
        }
    }
    return false;
}

//method that searches for an alerte_gps in alertes_gps
private boolean find_alerte_gps(Alerte_GPS a) {
    for (Alerte_GPS al : alertes_gps.getContent()) {
        if (al.equals(a)) {
            return true;
        }
    }
    return false;
}
    
public void aquitter_alerte(Alerte a) {
    if (find_alerte(a)){
        a.acquitter();
    }else{
        System.out.println("Alerte not found in the list of alertes.");
    }
}

public void aquitter_alerte_gps(Alerte_GPS a) {
    if (find_alerte_gps(a)){
        a.acquitter();
    }else{
        System.out.println("Alerte_GPS not found in the list of alertes_gps.");
    }
}

    public void supprimer_alerte(Alerte a) {
        alertes.supprimer_alerte(a);
    }

    public void supprimer_alerte_gps(Alerte_GPS a) {
        alertes_gps.supprimer_alerte_gps(a);
    }

    public void activer() {
        for (Zone z : all_zones) z.activer();
    }

    public void suspendre() {
        for (Zone z : all_zones) z.suspendre();
    }


    public boolean estActif() {
        return all_zones.stream().anyMatch(Zone::estActif);
    }
//getters

    public Collection<Zone> getAll_zones() { return all_zones; }
    public Collection<Alerte> getAlertes() { return alertes.getContent(); }
    public Collection<Alerte_GPS> getAlertes_GPS() { return alertes_gps.getContent(); }

}

