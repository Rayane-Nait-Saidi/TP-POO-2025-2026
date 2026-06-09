package HelloFX.Entities;
import java.util.*;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

public class Prog_Ali {
    private String type_aliment ; 
    private int quantite ; 

    public Prog_Ali(String type_aliment, int quantite) {
        this.type_aliment = type_aliment;
        this.quantite = quantite;
    }

    public String getType_aliment() {
        return type_aliment;
    }

    public int getQuantite() {
        return quantite;
    }
    
    public String displayProgram(){
        StringBuilder res = new StringBuilder() ; 
        res.append("Type d'aliment : ").append(type_aliment).append("\n");
        res.append("Quantité : ").append(quantite).append("\n");
        return res.toString();
    }
}
