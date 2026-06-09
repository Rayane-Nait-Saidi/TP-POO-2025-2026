package HelloFX.Zones;
import java.util.*;
import HelloFX.Entities.*;
import HelloFX.capteurs.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

import java.util.*;
public class Culture extends Zone  {
    private Collection<Cult> list_cultures;

    //constructeur
    public Culture(String nom) {
        super(nom);
        this.list_cultures = new ArrayList<>();
    }
//Ajoute une culture à la zone.
public void ajouter_culture(Cult c) {
    list_cultures.add(c);
}
    public String rapport_etat_cult() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Rapport cultures Zone [").append(nom).append("] ===\n");
        for (Cult c : list_cultures) {
            sb.append(c.get_etat_cult()).append("\n");
        }
        return sb.toString();
    }
    public String afficher_zone() {
        return "Culture{code=" + code +
                ", nom='" + nom + '\'' +
                ", status=" + status +
                ", nbCultures=" + list_cultures.size() +
                ", nbCapteurs=" + all_capteurs.size() + "}";
    }
//getters
public Collection<Cult> getList_cultures() { return list_cultures; }

}
