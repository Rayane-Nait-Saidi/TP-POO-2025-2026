package releves;
import java.time.*;
import Zones.*;
import Entities.*;
import capteurs.*;
import alertes.*;
import common.*;

/*import java.util.*;
import java.time.*;

public class Hist_Releve_GPS {
    private List<Releve_GPS> releves_gps ;

    public Hist_Releve_GPS(){
        this.releves_gps = new ArrayList<>() ;
    }

    public List<Releve_GPS> getContent(){
        return releves_gps ; 
    }

    public void Enregistrer_Releve_GPS(Releve_GPS releve_gps){
        releves_gps.add(releve_gps) ;
    }


    //display the content of the history of the releves filtred by a plage of dates
    public String display_releves_gps(LocalDate date1 , LocalDate date2){
        StringBuilder res = new StringBuilder("Releves GPS between " + date1 + " and " + date2 + " :\n") ; 
        Iterator<Releve_GPS> it = releves_gps.iterator() ;
        while(it.hasNext()){
            Releve_GPS r = it.next() ; 
            if (r.getDate().isAfter(date1) && r.getDate().isBefore(date2)){
                res.append(r.display_releve_gps()).append("\n") ;
            }
        }
        return res.toString() ;
    }
}*/
