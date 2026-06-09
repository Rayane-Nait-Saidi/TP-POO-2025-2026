//package alertes;
package HelloFX.alertes;
import java.util.*;
import java.time.*;
//import Zones.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.common.*;

/*public class Alerte_GPS implements Comparable<Alerte_GPS>{
    Releve_GPS releve ;
    Gravite gravite ;
    boolean acquitted ;

    public Alerte_GPS(Releve_GPS releve, Gravite gravite) {
        this.releve = releve;
        this.gravite = gravite;
        this.acquitted = false ;
    }

    public Releve_GPS getReleve() {
        return releve;
    }

    public Gravite getGravite() {
        return gravite;
    }

    public boolean isAcquitted() {
        return acquitted;
    }

    public void acquitter() {
        this.acquitted = true ;
    }

    public String display_alerte_gps() {
        StringBuilder res = new StringBuilder();
        res.append("Gravite : ").append(this.gravite).append("\n");
        res.append("Acquitted : ").append(this.acquitted).append("\n");
        res.append("Releve GPS : \n").append(this.releve.display_releve_gps()).append("\n");
        return res.toString();
    }
    
    //on doit trier les alertes par ordre de gravite (critique avant avertissement)
    public int compareTo(Alerte_GPS other){
        if (this.gravite == Gravite.CRIT && other.gravite == Gravite.AVERT){
            return -1 ; //this is more severe than other
        } else if (this.gravite == Gravite.AVERT && other.gravite == Gravite.CRIT){
            return 1 ; //other is more severe than this
        } else {
            return 0 ; //both have the same severity
        }
    }
    
}*/
