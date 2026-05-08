import java.time.*;

public class Releve_GPS {
    private LocalDate date ;
    private float latitude;
    private float longitude;
    private GPS capteur ;
    private Niveau_Releve niveau_releve ;

    public Releve_GPS(LocalDate date, float latitude, float longitude, GPS capteur) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capteur = capteur;
        if (est_hors_critique()){
            this.niveau_releve = Niveau_Releve.CRIT ;
        } else if (est_hors_avertissement()){
            this.niveau_releve = Niveau_Releve.AVERT ;
        } else {
            this.niveau_releve = Niveau_Releve.NORMAL ;
        }
    }


    public LocalDate getDate() {
        return date;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }   

    public GPS getCapteur() {
        return capteur;
    }   

    public Niveau_Releve getNiveau_releve() {
        return niveau_releve;
    }

    public String display_releve_gps(){
        StringBuilder res = new StringBuilder();
        res.append("Date : ").append(this.date).append("\n");
        res.append("Latitude : ").append(this.latitude).append("\n");
        res.append("Longitude : ").append(this.longitude).append("\n");
        res.append("Capteur : ").append(this.capteur.getClass().getSimpleName()).append("\n");
        res.append("Niveau : ").append(this.niveau_releve).append("\n");
        return res.toString();
    }

    public boolean est_hors_avertissement(){
        return this.capteur.est_hors_avertissement() ; 
    }

    public boolean est_hors_critique(){
        return this.capteur.est_hors_critique() ;
    }           
}
