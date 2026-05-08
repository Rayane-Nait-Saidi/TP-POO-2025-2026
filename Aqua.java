
public class Aqua extends Zone implements Programmable{
    //Attributs
    private String espece;              // ex: poisson, crevette
    private int number;                 // nombr dans le réservoir
    private Prog_Ali programme_aliment;

    //constructeur
    public Aqua(int code, String nom, String espece, int number) {
        super(code, nom);
        this.espece = espece;
        this.number = number;
        this.programme_aliment = null;
    }

    public void definir_programme(String type_aliment, int quantite) {
        this.programme_aliment = new Prog_Ali(type_aliment, quantite);
    }


    //Implémentation Programmable
    public String afficher_prog() {
        StringBuilder sb = new StringBuilder();
        if (programme_aliment != null) {
            sb.append(programme_aliment.displayProgram());
        } else {
            sb.append("Aucun programme alimentaire défini pour [").append(nom).append("]");
        }
        return sb.toString();
    }


    public String afficher_zone() {
        StringBuilder sb = new StringBuilder();
        sb.append("Aqua{");
        sb.append("code=").append(code);
        sb.append(", nom='").append(nom).append("'");
        sb.append(", status=").append(status);
        sb.append(", espece='").append(espece).append("'");
        sb.append(", number=").append(number);
        sb.append(", nbCapteurs=").append(all_capteurs.size());
        sb.append("}");
        return sb.toString();
    }


        //getters / setters
    public String getEspece() { return espece; }
    public void setEspece(String espece) { this.espece = espece; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public Prog_Ali getProgramme_aliment() { return programme_aliment; }
}

