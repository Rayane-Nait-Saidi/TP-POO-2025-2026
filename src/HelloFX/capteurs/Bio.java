package HelloFX.capteurs;
import java.time.*;
import HelloFX.Zones.*;
import HelloFX.Entities.*;
import HelloFX.releves.*;
import HelloFX.alertes.*;
import HelloFX.common.*;

public class Bio extends Num{
    public Bio(int code , Stat_Capt statut, Zone zone, float valeur_min_avert, float valeur_max_avert, float valeur_min_critique, float valeur_max_critique, float valeur_actuelle, String unite) {
        super(code, statut, zone, valeur_min_avert, valeur_max_avert, valeur_min_critique, valeur_max_critique, valeur_actuelle, unite);
    }
    
}
