package HelloFX.Zones;
import java.util.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.util.*;

public abstract class Zone implements Activable {
   //attributs
   protected int code;
    protected String nom;
    protected Collection<Capteur> all_capteurs;
    protected STATUS status;
    protected Hist_Prod historique_prod;
    protected static int cpt = 0 ; 


    //getters / setters
    public int getCode() { return code; }
    

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public STATUS getStatus() { return status; }
    public Collection<Capteur> getAll_capteurs() { return all_capteurs; }
    public Hist_Prod getHistorique_prod() { return historique_prod; }




    //constructeur
 public Zone(String nom) {
    this.code = cpt; cpt++ ; 
    this.nom = nom;
    this.status = STATUS.ACTIF;
    this.all_capteurs = new ArrayList<>();
    this.historique_prod = new Hist_Prod();
}
    public void activer() {
        this.status = STATUS.ACTIF;
        for (Capteur c : all_capteurs) {
            if (c.getStatut() == Stat_Capt.SUSP) {
                c.activer();
            }
        }
    }

    public void suspendre() {
        this.status = STATUS.SUSP;
        for (Capteur c : all_capteurs) {
            if (c.getStatut() == Stat_Capt.ACTIF) {
                c.suspendre();
            }
        }
    }
    public boolean estActif() {
        return this.status == STATUS.ACTIF;
    }
//Ajoute un capteur à la zone.
public void ajouter_capteur(Capteur c) {
    all_capteurs.add(c);
}
//Enregistre une production dans l'historique de la zone.
public void ajouter_prod(Prod p) {
    historique_prod.Enregistrer_Prod(p);
}

//Retourne un tableau texte de tous les relevés des capteurs de la zone.

    public String tableau_releve() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Relevés Zone [").append(nom).append("] ===\n");
              
        for (Capteur c : all_capteurs){
            for (Releve_Generale r : c.getHistorique_releve().getContent()){
                sb.append(r.display_releve()).append("\n");
            }
        }
        return sb.toString();
    }
//Affiche les informations générales de la zone.
/*public String afficher_zone() {
    return "Zone{code=" + code + ", nom='" + nom + '\'' + ", status=" + status + ", nbCapteurs=" + all_capteurs.size() + "}";
}*/

public abstract String afficher_zone() ; 
}