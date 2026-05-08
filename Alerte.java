public class Alerte implements Comparable<Alerte>{
    private Releve releve ;
    private Gravite gravite ;
    private boolean acquitted;//

    public Alerte(Releve releve, Gravite gravite) {
        this.releve = releve;
        this.gravite = gravite;
        this.acquitted = false ;
    }

    public Releve getReleve() {
        return releve;
    }

    public Gravite getGravite() {
        return gravite;
    }

    public boolean isAcquitted() {
        return acquitted;
    }

    public void acquitter() {
        this.acquitted = true ;
    }

    public String display_alerte() {
        StringBuilder res = new StringBuilder();
        res.append("Gravite : ").append(this.gravite).append("\n");
        res.append("Acquitted : ").append(this.acquitted).append("\n");
        res.append("Releve : \n").append(this.releve.display_releve()).append("\n");
        return res.toString();
    }
    
    //on doit trier les alertes par ordre de gravite (critique avant avertissement)
    public int compareTo(Alerte other){
        if (this.gravite == Gravite.CRIT && other.gravite == Gravite.AVERT){
            return -1 ; //this is more severe than other
        } else if (this.gravite == Gravite.AVERT && other.gravite == Gravite.CRIT){
            return 1 ; //other is more severe than this
        } else {
            return 0 ; //both have the same severity
        }
    }

    
}
