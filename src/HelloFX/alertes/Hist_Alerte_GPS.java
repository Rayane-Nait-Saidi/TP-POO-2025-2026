package HelloFX.alertes;
import java.util.*;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.common.*;

/*import java.util.*;

public class Hist_Alerte_GPS {
    private List<Alerte_GPS> alertes_gps ;

    public Hist_Alerte_GPS(){
        this.alertes_gps = new ArrayList<>() ;
    }

    public List<Alerte_GPS> getContent(){
        return alertes_gps ; 
    }

    public void Enregistrer_Alerte_GPS(Alerte_GPS alerte_gps){
        alertes_gps.add(alerte_gps) ;
    }

    public void supprimer_alerte_gps(Alerte_GPS alerte_gps){
        alertes_gps.remove(alerte_gps) ;
    }

    //dipslay the alertes sorted by gravity
    public String display_sorted_alertes_gps(){
        Collections.sort(alertes_gps) ; 
        StringBuilder res = new StringBuilder("Alertes GPS sorted by gravity :\n") ;
        Iterator<Alerte_GPS> it = alertes_gps.iterator() ;
        while(it.hasNext()){
            Alerte_GPS a = it.next() ; 
            res.append(a.display_alerte_gps()).append("\n") ;
        }

        return res.toString() ;
    }

    //on doit aussi afficher les alertes filtrables par zone (Alerte_GPS => Releve_GPS => Capteur => Zone) , type de capteur
    public String display_filtered_alertes_gps(Zone zone , Capteur capt){
        StringBuilder res = new StringBuilder("Alertes GPS filtered by the specified zone and capteur:\n") ;
        Iterator<Alerte_GPS> it = alertes_gps.iterator() ;
        while(it.hasNext()){
            Alerte_GPS a = it.next() ; 
            if (a.getReleve().getCapteur().getZone().equals(zone) && a.getReleve().getCapteur().equals(capt)){
                res.append(a.display_alerte_gps()).append("\n") ;
            }
        }

        return res.toString() ;
    }
    
    
}*/
