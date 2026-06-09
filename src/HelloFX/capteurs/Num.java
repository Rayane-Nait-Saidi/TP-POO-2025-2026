package HelloFX.capteurs;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.time.* ;

public class Num extends Capteur{
    private float valeur_min_avert ; 
    private float valeur_max_avert ;
    private float valeur_min_critique ;
    private float valeur_max_critique ;
    private float valeur_actuelle ;
    private String unite ; 
    //private Hist_Releve historique_releve ; 

    public Num(int code, Stat_Capt statut, Zone zone, float valeur_min_avert, float valeur_max_avert, float valeur_min_critique, float valeur_max_critique, float valeur_actuelle, String unite) {
        super(code, statut, zone);
        this.valeur_min_avert = valeur_min_avert;
        this.valeur_max_avert = valeur_max_avert;
        this.valeur_min_critique = valeur_min_critique;
        this.valeur_max_critique = valeur_max_critique;
        this.valeur_actuelle = valeur_actuelle;
        this.unite = unite;
        //this.historique_releve = new Hist_Releve();
    }



    public float getValeur_min_avert() {
        return valeur_min_avert;
    }

    public float getValeur_max_avert() {
        return valeur_max_avert;
    }

    public float getValeur_min_critique() {
        return valeur_min_critique;
    }

    public float getValeur_max_critique() {
        return valeur_max_critique;
    }

    public float getValeur_actuelle() {
        return valeur_actuelle;
    }

    public String getUnite() {
        return unite;
    }

   

    public boolean est_hors_avertissement(){
        return (this.valeur_actuelle < this.valeur_min_avert || this.valeur_actuelle > this.valeur_max_avert) ; 
    }

    public boolean est_hors_critique(){
        return (this.valeur_actuelle < this.valeur_min_critique || this.valeur_actuelle > this.valeur_max_critique) ; 
    }

    public String display_capteur() {
        StringBuilder res = new StringBuilder(super.display_capteur().trim());
        res.append(" | Valeur actuelle : ").append(this.valeur_actuelle).append(" ").append(this.unite);
        res.append(" | Plage avert : [").append(this.valeur_min_avert).append(" ; ").append(this.valeur_max_avert).append("]");
        res.append(" | Plage crit : [").append(this.valeur_min_critique).append(" ; ").append(this.valeur_max_critique).append("]");
        res.append(" | Etat danger: ").append(est_hors_critique() ? "CRIT" : est_hors_avertissement() ? "AVERT" : "NORMAL");
        return res.toString();
    }

    
    public Releve_Generale generer_releve(){
        Releve_Generale  releve = new Releve(LocalDate.now(), this , this.valeur_actuelle, this.unite) ; //polymorphisme
        getHistorique_releve().Enregistrer_Releve(releve) ;
        return releve ;  
    }

}
