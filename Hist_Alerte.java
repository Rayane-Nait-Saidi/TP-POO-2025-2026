import java.util.* ; 

public class Hist_Alerte {
    private List<Alerte> alertes ;

    public Hist_Alerte(){
        this.alertes = new ArrayList<>() ;
    }

    public List<Alerte> getContent(){
        return alertes ; 
    }

    public void Enregistrer_Alerte(Alerte alerte){
        alertes.add(alerte) ;
    }

    public void supprimer_alerte(Alerte alerte){
        alertes.remove(alerte) ;
    }

    //dipslay the alertes sorted by gravity
    public String display_sorted_alertes(){
        Collections.sort(alertes) ; 
        StringBuilder res = new StringBuilder("Alertes sorted by gravity :\n") ;
        Iterator<Alerte> it = alertes.iterator() ;
        while(it.hasNext()){
            Alerte a = it.next() ; 
            res.append(a.display_alerte()).append("\n") ;
        }

        return res.toString() ;
    }

    //on doit aussi afficher les alertes filtrables par zone (Alerte => Releve => Capteur => Zone) , type de capteur
    public String display_filtered_alertes(Zone zone , Capteur capt){
        StringBuilder res = new StringBuilder("Alertes filtered by the specified zone and capteur:\n") ;
        Iterator<Alerte> it = alertes.iterator() ;
        while(it.hasNext()){
            Alerte a = it.next() ; 
            if (a.getReleve().getCapteur().getZone().equals(zone) && a.getReleve().getCapteur().equals(capt)){
                res.append(a.display_alerte()).append("\n") ;
            }
        }

        return res.toString() ;
    }
    
    
}
