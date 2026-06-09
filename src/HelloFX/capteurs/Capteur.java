package HelloFX.capteurs;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

public abstract class Capteur implements Activable{
    private int code ; 
    private Stat_Capt statut ;
    private Zone zone ;
    private Hist_Releve_Generale historique_releve ;
    private static int compteur = 0 ;
    
    public Capteur(int code, Stat_Capt statut, Zone zone) {
        this.code = compteur;
        compteur++;
        this.statut = statut;
        this.zone = zone;
        this.historique_releve = new Hist_Releve_Generale() ;
    }

    public int getCode() {
        return code;
    }

    public Stat_Capt getStatut() {
        return statut;
    }

    public Zone getZone() {
        return zone;
    }

    public Hist_Releve_Generale getHistorique_releve(){
        return historique_releve ;
    }

    public void changeStatus(Stat_Capt newStatus){
        this.statut = newStatus ;
    }

    public void activer(){
        this.statut = Stat_Capt.ACTIF ; 
    }
    
    public void suspendre(){
        this.statut = Stat_Capt.SUSP ; 
    }

    public String display_capteur(){
        StringBuilder res = new StringBuilder();
        res.append("Code : ").append(this.code).append(" | ");
        res.append("Statut : ").append(this.statut).append(" | ");
        res.append("Zone : ").append(this.zone.getNom()).append("\n");
        return res.toString();
    }

    //detect if the captor has reached or supassed the warning frontiere!
    public abstract boolean est_hors_avertissement() ;

    //detect if the captor has reached or supassed the critical frontiere!
    public abstract boolean est_hors_critique() ;

    //add an abstract method for generate a Releve_Generale 
    public abstract Releve_Generale generer_releve() ;
}