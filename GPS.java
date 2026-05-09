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

    private Hist_Releve_GPS hist_releve_gps ; 

    public GPS(int code, Stat_Capt statut, Zone zone, float latitude_min_avert, float latitude_max_avert, float latitude_min_critique, float latitude_max_critique, float longitude_min_avert, float longitude_max_avert, float longitude_min_critique, float longitude_max_critique) {
        super(code, statut, zone);
        this.latitude_min_avert = latitude_min_avert;
        this.latitude_max_avert = latitude_max_avert;
        this.latitude_min_critique = latitude_min_critique;
        this.latitude_max_critique = latitude_max_critique;
        this.longitude_min_avert = longitude_min_avert;
        this.longitude_max_avert = longitude_max_avert;
        this.longitude_min_critique = longitude_min_critique;
        this.longitude_max_critique = longitude_max_critique;
        this.hist_releve_gps = new Hist_Releve_GPS();
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

    public Hist_Releve_GPS getHist_releve_gps() {
        return hist_releve_gps;
    }

    public boolean est_hors_avertissement(){
        return (this.latitude < this.latitude_min_avert || this.latitude > this.latitude_max_avert || this.longitude < this.longitude_min_avert || this.longitude > this.longitude_max_avert) ; 
    }

    public boolean est_hors_critique(){
        return (this.latitude < this.latitude_min_critique || this.latitude > this.latitude_max_critique || this.longitude < this.longitude_min_critique || this.longitude > this.longitude_max_critique) ; 
    }

    public Releve_GPS generer_releve_gps(){
        Releve_GPS releve_gps = new Releve_GPS(LocalDate.now(), latitude, longitude, this) ; 
        hist_releve_gps.Enregistrer_Releve_GPS(releve_gps) ;
        return releve_gps ;
    }

    
}
