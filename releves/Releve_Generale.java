package releves;
import java.time.*;
import Zones.*;
import Entities.*;
import capteurs.*;
import alertes.*;
import common.*;

import java.time.* ; 

public abstract class Releve_Generale {
    private LocalDate date ;
    private Capteur capteur ; 
    private Niveau_Releve niveau_releve ;

    public Releve_Generale(LocalDate date, Capteur capteur) {
        this.date = date;
        this.capteur = capteur;
    }


    public LocalDate getDate() {
        return date;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public Niveau_Releve getNiveau_releve() {
        return niveau_releve;
    }

    public void setNiveau_releve(Niveau_Releve niveau_releve) {
        this.niveau_releve = niveau_releve;
    }

    public abstract String display_releve() ;

    public boolean est_hors_avertissement() {
        return getCapteur().est_hors_avertissement() ; 
    }

    public boolean est_hors_critique() {
        return getCapteur().est_hors_critique() ; 
    }
}
