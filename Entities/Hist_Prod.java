package Entities;
import java.util.*;
import java.time.*;
import Zones.*;
import capteurs.*;
import releves.*;
import alertes.*;
import common.*;

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