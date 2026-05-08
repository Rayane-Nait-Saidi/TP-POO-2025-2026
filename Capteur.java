public abstract class Capteur implements Activable{
    private int code ; 
    private Stat_Capt statut ;
    private Zone zone ;
    private static int compteur = 0 ;
    
    public Capteur(int code, Stat_Capt statut, Zone zone) {
        this.code = compteur;
        compteur++;
        this.statut = statut;
        this.zone = zone;
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

    public void changeStatus(Stat_Capt newStatus){
        this.statut = newStatus ;
    }

    public void activer(){
        this.statut = Stat_Capt.ACTIF ; 
    }
    
    public void suspendre(){
        this.statut = Stat_Capt.SUSP ; 
    }

    //detect if the captor has reached or supassed the warning threshold!
    public abstract boolean est_hors_avertissement() ; 

    //detect if the captor has reached or supassed the critical threshold!
    public abstract boolean est_hors_critique() ; 
    
}