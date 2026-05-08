import java.util.*;
//Gère l'ensemble des zones et des alertes.
public class Ferme implements Activable {
    //attributs
    private Collection<Zone> all_zones;
    private Collection<Alerte> alertes;

    //constructeur
    public Ferme() {
        this.all_zones = new ArrayList<>();
        this.alertes = new ArrayList<>();
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
public void aquitter_alerte(Alerte a) {
    a.acquitter();
}

    public void supprimer_alerte(Alerte a) {
        alertes.remove(a);
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
    public Collection<Alerte> getAlertes() { return alertes; }

}

