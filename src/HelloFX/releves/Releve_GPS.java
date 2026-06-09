package HelloFX.releves;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.time.*;

public class Releve_GPS extends Releve_Generale {
    
    private float latitude;
    private float longitude;
    

    public Releve_GPS(LocalDate date , Capteur capteur, float latitude, float longitude) {
        super(date, capteur);
        this.latitude = latitude;
        this.longitude = longitude;
        if (est_hors_critique()){
            setNiveau_releve(Niveau_Releve.CRIT);
        } else if (est_hors_avertissement()){
            setNiveau_releve(Niveau_Releve.AVERT);
        } else {
            setNiveau_releve(Niveau_Releve.NORMAL);
        }
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }   
  

    public String display_releve(){
        StringBuilder res = new StringBuilder();
        res.append("Date : ").append(getDate()).append("\n");
        res.append("Latitude : ").append(getLatitude()).append("\n");
        res.append("Longitude : ").append(getLongitude()).append("\n");
        res.append("Capteur : ").append(getCapteur().getClass().getSimpleName()).append("\n");
        res.append("Zone associée : ").append(getCapteur().getZone().getNom()).append("\n") ; 
        res.append("Niveau : ").append(getNiveau_releve()).append("\n");
        return res.toString();
    }

    /*public boolean est_hors_avertissement(){
        return getCapteur().est_hors_avertissement() ; 
    }

    public boolean est_hors_critique(){
        return getCapteur().est_hors_critique() ;
    }  */         
}
