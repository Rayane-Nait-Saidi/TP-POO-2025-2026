package HelloFX.capteurs;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.time.* ; 

public class GPS extends Capteur{
    private float latitude;
    private float longitude;

    private float latitude_min_avert ;
    private float latitude_max_avert ;
    private float latitude_min_critique ;
    private float latitude_max_critique ;
    private float longitude_min_avert ;
    private float longitude_max_avert ;
    private float longitude_min_critique ;
    private float longitude_max_critique ;

    //private Hist_Releve_GPS hist_releve_gps ; 

    public GPS(int code, Stat_Capt statut, Zone zone, float latitude,float longitude,float latitude_min_avert, float latitude_max_avert, float latitude_min_critique, float latitude_max_critique, float longitude_min_avert, float longitude_max_avert, float longitude_min_critique, float longitude_max_critique) {
        super(code, statut, zone);
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitude_min_avert = latitude_min_avert;
        this.latitude_max_avert = latitude_max_avert;
        this.latitude_min_critique = latitude_min_critique;
        this.latitude_max_critique = latitude_max_critique;
        this.longitude_min_avert = longitude_min_avert;
        this.longitude_max_avert = longitude_max_avert;
        this.longitude_min_critique = longitude_min_critique;
        this.longitude_max_critique = longitude_max_critique;
        //this.hist_releve_gps = new Hist_Releve_GPS();
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude_min_avert() {
        return latitude_min_avert;
    }

    public float getLatitude_max_avert() {
        return latitude_max_avert;
    }

    public float getLatitude_min_critique() {
        return latitude_min_critique;
    }

    public float getLatitude_max_critique() {
        return latitude_max_critique;
    }

    public float getLongitude_min_avert() {
        return longitude_min_avert;
    }

    public float getLongitude_max_avert() {
        return longitude_max_avert;
    }   

    public float getLongitude_min_critique() {
        return longitude_min_critique;
    }

    public float getLongitude_max_critique() {
        return longitude_max_critique;
    }

    public Hist_Releve_Generale getHist_releve_gps() {
        return getHistorique_releve();
    }

    public boolean est_hors_avertissement(){
        return (this.latitude < this.latitude_min_avert || this.latitude > this.latitude_max_avert || this.longitude < this.longitude_min_avert || this.longitude > this.longitude_max_avert) ; 
    }

    public boolean est_hors_critique(){
        return (this.latitude < this.latitude_min_critique || this.latitude > this.latitude_max_critique || this.longitude < this.longitude_min_critique || this.longitude > this.longitude_max_critique) ; 
    }

    public String display_capteur() {
        StringBuilder res = new StringBuilder(super.display_capteur().trim());
        res.append(" | Latitude actuelle : ").append(this.latitude);
        res.append(" | Longitude actuelle : ").append(this.longitude);
        res.append(" | Plage avert lat : [").append(this.latitude_min_avert).append(" ; ").append(this.latitude_max_avert).append("]");
        res.append(" | Plage avert lon : [").append(this.longitude_min_avert).append(" ; ").append(this.longitude_max_avert).append("]");
        res.append(" | Plage crit lat : [").append(this.latitude_min_critique).append(" ; ").append(this.latitude_max_critique).append("]");
        res.append(" | Plage crit lon : [").append(this.longitude_min_critique).append(" ; ").append(this.longitude_max_critique).append("]");
        res.append(" | Etat danger: ").append(est_hors_critique() ? "CRIT" : est_hors_avertissement() ? "AVERT" : "NORMAL");
        return res.toString();
    }

    public Releve_Generale generer_releve(){
        Releve_Generale releve_gps = new Releve_GPS(LocalDate.now(), this, this.latitude, this.longitude) ; 
        getHistorique_releve().Enregistrer_Releve(releve_gps) ;
        return releve_gps ;
    }

    
}
