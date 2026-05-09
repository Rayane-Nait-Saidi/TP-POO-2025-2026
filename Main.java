import java.util.*;
import java.time.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Ferme ferme = new Ferme();

    public static void main(String[] args) {

        initialisation();

        boolean quitter = false;

        while (!quitter) {
            afficherMenu();

            int choix = lireInt("Choix : ");

            switch (choix) {

                case 1:
                    afficherZones();
                    break;

                case 2:
                    afficherTableauReleves();
                    break;

                case 3:
                    genererReleves();
                    break;

                case 4:
                    afficherHistoriqueCapteur();
                    break;

                case 5:
                    afficherAlertes();
                    break;

                case 6:
                    acquitterPremiereAlerte();
                    break;

                case 0:
                    quitter = true;
                    System.out.println("Fin du programme.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }

            if (!quitter) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                sc.nextLine();
            }
        }
    }

    static void afficherMenu() {
        System.out.println("\n==============================");
        System.out.println("   GESTION FERME INTELLIGENTE");
        System.out.println("==============================");
        System.out.println("1. Afficher les zones");
        System.out.println("2. Afficher tableau des relevés");
        System.out.println("3. Générer des relevés");
        System.out.println("4. Consulter historique capteur");
        System.out.println("5. Afficher alertes");
        System.out.println("6. Acquitter première alerte");
        System.out.println("0. Quitter");
        System.out.println("==============================");
    }

    static void initialisation() {

        Culture culture = new Culture("Zone Culture");
        Elevage elevage = new Elevage("Zone Elevage");

        ferme.ajouter_zone(culture);
        ferme.ajouter_zone(elevage);

        Sol sol = new Sol(
                0,
                Stat_Capt.ACTIF,
                culture,
                10,
                40,
                5,
                50,
                25,
                "%"
        );

        Env env = new Env(
                0,
                Stat_Capt.ACTIF,
                culture,
                15,
                30,
                10,
                40,
                35,
                "C"
        );

        culture.ajouter_capteur(sol);
        culture.ajouter_capteur(env);

        Animal vache = new Animal(1, 12, Type.RUMI, 150);
        elevage.ajouter_animal(vache);
    }

    static void afficherZones() {
        System.out.println(ferme.afficher_all_zones());
    }

    static void afficherTableauReleves() {
        for (Zone z : ferme.getAll_zones()) {
            System.out.println(z.tableau_releve());
        }
    }

    static void genererReleves() {

        for (Zone z : ferme.getAll_zones()) {

            for (Capteur c : z.getAll_capteurs()) {

                if (c instanceof Num) {

                    Releve r = ((Num) c).generer_releve();

                    System.out.println("Relevé généré :");
                    System.out.println(r.display_releve());

                    if (r.getNiveau_Releve() == Niveau_Releve.CRIT) {
                        ferme.engistrer_alerte(new Alerte(r, Gravite.CRIT));
                    }
                    else if (r.getNiveau_Releve() == Niveau_Releve.AVERT) {
                        ferme.engistrer_alerte(new Alerte(r, Gravite.AVERT));
                    }
                }
            }
        }
    }

    static void afficherHistoriqueCapteur() {

        for (Zone z : ferme.getAll_zones()) {

            for (Capteur c : z.getAll_capteurs()) {

                if (c instanceof Num) {
                    System.out.println(
                            ((Num) c)
                                    .getHistorique_releve()
                                    .display_releves(
                                            LocalDate.of(2020, 1, 1),
                                            LocalDate.now().plusDays(1)
                                    )
                    );
                }
            }
        }
    }

    static void afficherAlertes() {
        System.out.println(
                ferme.getAlertes().isEmpty()
                        ? "Aucune alerte."
                        : new Hist_AlerteWrapper().display()
        );
    }

    static void acquitterPremiereAlerte() {

        if (ferme.getAlertes().isEmpty()) {
            System.out.println("Aucune alerte.");
            return;
        }

        Alerte a = ferme.getAlertes().iterator().next();
        ferme.aquitter_alerte(a);

        System.out.println("Première alerte acquittée.");
    }

    static int lireInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int v = Integer.parseInt(sc.nextLine());
                return v;
            } catch (Exception e) {
                System.out.println("Veuillez entrer un entier.");
            }
        }
    }

    static class Hist_AlerteWrapper {
        public String display() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Alertes ===\n");

            for (Alerte a : ferme.getAlertes()) {
                sb.append(a.display_alerte()).append("\n");
            }

            return sb.toString();
        }
    }
}