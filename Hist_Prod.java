import java.util.*;
public class Hist_Prod {
    private List<Prod> productions ;

    public Hist_Prod(){
        this.productions = new ArrayList<>() ;
    }

    public List<Prod> getContent(){
        return productions ; 
    }

    public void Engistrer_Prod(Prod prod){
        productions.add(prod) ;
    }

}
