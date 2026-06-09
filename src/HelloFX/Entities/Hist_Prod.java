package HelloFX.Entities;
import java.util.*;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.util.*;
public class Hist_Prod {
    private List<Prod> productions ;

    public Hist_Prod(){
        this.productions = new ArrayList<>() ;
    }

    public List<Prod> getContent(){
        return productions ; 
    }

    public void Enregistrer_Prod(Prod prod){
        productions.add(prod) ;
    }

}