package releves;
import java.time.*;
import Zones.*;
import Entities.*;
import capteurs.*;
import alertes.*;
import common.*;

import java.util.* ; 
import java.time.* ;

public class Hist_Releve_Generale {
    private List<Releve_Generale> releves ;

    public Hist_Releve_Generale(){
        this.releves = new ArrayList<>() ;
    }

    public List<Releve_Generale> getContent(){
        return releves ; 
    }

    public void Enregistrer_Releve(Releve_Generale releve){
        releves.add(releve) ;
    }
    
    public String display_releves(LocalDate date1 , LocalDate date2){
        StringBuilder res = new StringBuilder("Releves between " + date1 + " and " + date2 + " :\n") ; 
        Iterator<Releve_Generale> it = releves.iterator() ;
        while(it.hasNext()){
            Releve_Generale r = it.next() ; 
            if (r.getDate().isAfter(date1) && r.getDate().isBefore(date2)){
                res.append(r.display_releve()).append("\n") ;
            }
        }
        return res.toString() ;
    }
}
