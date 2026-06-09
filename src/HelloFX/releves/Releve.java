package HelloFX.releves;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.time.*;

public class Releve extends Releve_Generale{
     
    private float valeur ; 
    private String unite ;

    public Releve(LocalDate date, Capteur capteur , float valeur, String unite) {
        super(date, capteur);
        this.valeur = valeur;
        this.unite = unite;

        if (est_hors_critique()){
            setNiveau_releve(Niveau_Releve.CRIT) ;
        } else if (est_hors_avertissement()){
            setNiveau_releve(Niveau_Releve.AVERT) ;
        } else {
            setNiveau_releve(Niveau_Releve.NORMAL) ;
        }
        
       
    }

    public String getUnite() {
        return unite;
    }

    public float getValeur() {
        return valeur;
    }

    public String display_releve() {
        StringBuilder res = new StringBuilder();
        res.append("Date : ").append(getDate()).append("\n");
        res.append("Valeur : ").append(getValeur()).append(" ").append(getUnite()).append("\n");
        res.append("Capteur : ").append(getCapteur().getClass().getSimpleName()).append("\n");
        res.append("Zone associée : ").append(getCapteur().getZone().getNom()).append("\n") ; 
        res.append("Niveau : ").append(getNiveau_releve()).append("\n");
        return res.toString();
    }

    /*public boolean est_hors_avertissement() {
        return getCapteur().est_hors_avertissement() ; 
    }

    public boolean est_hors_critique(){
        return getCapteur().est_hors_critique() ; 
    }*/

}
