import java.util.* ;
import java.time.*;

public class Hist_Releve {
    private List<Releve> releves ;

    public Hist_Releve(){
        this.releves = new ArrayList<>() ;
    }

    public List<Releve> getContent(){
        return releves ; 
    }

    public void Enregistrer_Releve(Releve releve){
        releves.add(releve) ;
    }

    //display the content of the history of the releves filtred by a plage of dates 
    public String display_releves(LocalDate date1 , LocalDate date2){
        StringBuilder res = new StringBuilder("Releves between " + date1 + " and " + date2 + " :\n") ; 
        Iterator<Releve> it = releves.iterator() ;
        while(it.hasNext()){
            Releve r = it.next() ; 
            if (r.getDate().isAfter(date1) && r.getDate().isBefore(date2)){
                res.append(r.display_releve()).append("\n") ;
            }
        }
        return res.toString() ;
    }

}
