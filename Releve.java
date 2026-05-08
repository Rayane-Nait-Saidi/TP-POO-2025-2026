import java.time.*;

public class Releve {
    private LocalDate date ; 
    private float valeur ; 
    private String unite ;
    private Num capteur ;
    private Niveau_Releve niveau_releve ;

    public Releve(LocalDate date, float valeur, String unite, Num capteur) {
        this.date = date;
        this.valeur = valeur;
        this.unite = unite;

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

    public float getValeur() {
        return valeur;
    }

    public Niveau_Releve getNiveau_Releve() {
        return niveau_releve;
    }

    public String getUnite() {
        return unite;
    }

    public Num getCapteur() {
        return capteur;
    }

    public String display_releve() {
        StringBuilder res = new StringBuilder();
        res.append("Date : ").append(this.date).append("\n");
        res.append("Valeur : ").append(this.valeur).append(" ").append(this.unite).append("\n");
        res.append("Capteur : ").append(this.capteur.getClass().getSimpleName()).append("\n");
        res.append("Niveau : ").append(this.niveau_releve).append("\n");
        return res.toString();
    }

    public boolean est_hors_avertissement() {
        return this.capteur.est_hors_avertissement() ; 
    }

    public boolean est_hors_critique(){
        return this.capteur.est_hors_critique() ; 
    }

    


}
