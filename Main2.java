import java.util.*;
import java.time.*;
import Ferme.*;
import Zones.*;
import Entities.*;
import capteurs.*;
import releves.*;
import alertes.*;

public class Main2 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Ferme ferme = new Ferme();

    public static void main(String[] args) {
        initialiserDemo();

        boolean quitter = false;
        while (!quitter) {
            afficherMenu();
            int choix = lireInt("Choix : ");

            switch (choix) {
                case 1:
                    ajouterZone();
                    break;
                case 2:
                    modifierZone();
                    break;
                case 3:
                    changerEtatZone();
                    break;
                case 4:
                    afficherZones();
                    break;
                case 5:
                    ajouterCultureAUneZone();
                    break;
                case 6:
                    mettreAJourStadeCulture();
                    break;
                case 7:
                    afficherRapportCultures();
                    break;
                case 8:
                    ajouterAnimalAUneZone();
                    break;
                case 9:
                    consignerEvenementSanitaire();
                    break;
                case 10:
                    definirProgrammeAlimentaire();
                    break;
                case 11:
                    afficherProgrammesAlimentaires();
                    break;
                case 12:
                    ajouterCapteur();
                    break;
                case 13:
                    changerStatutCapteur();
                    break;
                case 14:
                    enregistrerReleveManuel();
                    break;
                case 16:
                    consulterHistoriqueCapteur();
                    break;
                case 17:
                    afficherGraphique();
                    break;
                case 18:
                    afficherAlertesActives();
                    break;
                case 19:
                    acquitterAlerte();
                    break;
                case 20:
                    supprimerAlerte();
                    break;
                case 21:
                    enregistrerProductionZone();
                    break;
                case 22:
                    afficherHistoriqueProductions();
                    break;
                case 0:
                    quitter = true;
                    System.out.println("Merci d'avoir utilisé notre système .. Au revoir!!!");
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

    private static void afficherMenu() {
        System.out.println("\n==============================");
        System.out.println("   TEST FERME INTELLIGENTE");
        System.out.println("==============================");
        System.out.println("1. Ajouter une zone");
        System.out.println("2. Modifier une zone");
        System.out.println("3. Désactiver / réactiver une zone");
        System.out.println("4. Afficher toutes les zones");
        System.out.println("5. Affecter une culture à une zone");
        System.out.println("6. Mettre à jour le stade d'une culture");
        System.out.println("7. Rapport des cultures par zone");
        System.out.println("8. Affecter un animal à une zone");
        System.out.println("9. Consigner un événement sanitaire");
        System.out.println("10. Définir un programme alimentaire");
        System.out.println("11. Afficher les programmes alimentaires");
        System.out.println("12. Ajouter / configurer un capteur");
        System.out.println("13. Changer le statut d'un capteur");
        System.out.println("14. Enregistrer un relevé et générer une alerte"); 
        System.out.println("16. Consulter l'historique des releves d'un capteur entre deux dates");
        System.out.println("17. Afficher un graphique des relevés");
        System.out.println("18. Afficher les alertes actives triées");
        System.out.println("19. Acquitter une alerte");
        System.out.println("20. Supprimer une alerte");
        System.out.println("21. Enregistrer une production de zone");
        System.out.println("22. Afficher l'historique des productions");
        System.out.println("0. Quitter");
        System.out.println("==============================");
    }

    private static void initialiserDemo() {
        Culture culture = new Culture("Zone Culture");
        Elevage elevage = new Elevage("Zone Elevage");

        ferme.ajouter_zone(culture);
        ferme.ajouter_zone(elevage);

        Sol sol = new Sol(0, Stat_Capt.ACTIF, culture, 10, 40, 5, 50, 60, "%");
        Env env = new Env(0, Stat_Capt.ACTIF, culture, 15, 30, 10, 40, 25, "C");
        Eau eau = new Eau(0, Stat_Capt.ACTIF, culture, 20, 60, 10, 80, 75, "L");
        GPS gpsCulture = new GPS(0, Stat_Capt.ACTIF, culture, 0, 0, 0, 100, -10, 110, 0, 100, -10, 110);

        culture.ajouter_capteur(sol);
        culture.ajouter_capteur(env);
        culture.ajouter_capteur(eau);
        culture.ajouter_capteur(gpsCulture);

        culture.ajouter_culture(new Cult(
                LocalDate.of(2026, 3, 15),
                LocalDate.of(2026, 8, 30),
                Famille.CEREAL,
                6.2f,
                7.4f,
                30f,
                60f
        ));
        culture.ajouter_culture(new Cult(
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 15),
                Famille.LEGUMES,
                6.0f,
                7.0f,
                40f,
                70f
        ));

        elevage.ajouter_animal(new Animal(12, Type.RUMI, 150));
        elevage.ajouter_animal(new Animal(8, Type.VOL, 3));

        GPS gpsElevage = new GPS(0, Stat_Capt.ACTIF, elevage, 0, 0, 0, 100, -10, 110, 0, 100, -10, 110);
        elevage.ajouter_capteur(gpsElevage);

        sol.generer_releve();
        env.generer_releve();
        eau.generer_releve();
        gpsCulture.generer_releve();
        gpsElevage.generer_releve();

        fermerAlertesDemo(sol, env, eau, gpsCulture, gpsElevage);

        elevage.ajouter_prod(new Prod(LocalDate.now(), LocalTime.now(), Type_Prod.LAIT, 120, "L"));
        culture.ajouter_prod(new Prod(LocalDate.now(), LocalTime.now(), Type_Prod.RECOLTE, 80, "kg"));

        elevage.getList_animaux().iterator().next().definir_programme("Mélange riche en fibres", 5);
        elevage.getList_animaux().iterator().next().consigne_event_sanitaire(new Event_Sanitaire(Type_Event_Sant.EVOL_POID, "Prise de poids normale"));
    }

    private static void fermerAlertesDemo(Num sol, Num env, Num eau, GPS gpsCulture, GPS gpsElevage) {
        Releve_Generale rSol = sol.getHistorique_releve().getContent().isEmpty() ? null : sol.getHistorique_releve().getContent().get(0);
        if (rSol != null && rSol.getNiveau_releve() != Niveau_Releve.NORMAL) {
            ferme.engistrer_alerte(new Alerte(rSol, rSol.getNiveau_releve() == Niveau_Releve.CRIT ? Gravite.CRIT : Gravite.AVERT));
        }

        Releve_Generale rEnv = env.getHistorique_releve().getContent().isEmpty() ? null : env.getHistorique_releve().getContent().get(0);
        if (rEnv != null && rEnv.getNiveau_releve() != Niveau_Releve.NORMAL) {
            ferme.engistrer_alerte(new Alerte(rEnv, rEnv.getNiveau_releve() == Niveau_Releve.CRIT ? Gravite.CRIT : Gravite.AVERT));
        }

        Releve_Generale rEau = eau.getHistorique_releve().getContent().isEmpty() ? null : eau.getHistorique_releve().getContent().get(0);
        if (rEau != null && rEau.getNiveau_releve() != Niveau_Releve.NORMAL) {
            ferme.engistrer_alerte(new Alerte(rEau, rEau.getNiveau_releve() == Niveau_Releve.CRIT ? Gravite.CRIT : Gravite.AVERT));
        }

        Releve_Generale rGpsCulture = gpsCulture.getHist_releve_gps().getContent().isEmpty() ? null : gpsCulture.getHist_releve_gps().getContent().get(0);
        if (rGpsCulture != null && rGpsCulture.getNiveau_releve() != Niveau_Releve.NORMAL) {
            ferme.engistrer_alerte(new Alerte(rGpsCulture, rGpsCulture.getNiveau_releve() == Niveau_Releve.CRIT ? Gravite.CRIT : Gravite.AVERT));
        }

        Releve_Generale rGpsElevage = gpsElevage.getHist_releve_gps().getContent().isEmpty() ? null : gpsElevage.getHist_releve_gps().getContent().get(0);
        if (rGpsElevage != null && rGpsElevage.getNiveau_releve() != Niveau_Releve.NORMAL) {
            ferme.engistrer_alerte(new Alerte(rGpsElevage, rGpsElevage.getNiveau_releve() == Niveau_Releve.CRIT ? Gravite.CRIT : Gravite.AVERT));
        }
    }

    private static void ajouterZone() {
        int type = lireInt("Type de zone (1=Culture, 2=Elevage, 3=Aquacole) : ");
        String nom = lireTexte("Nom de la zone : ");

        if (type == 1) {
            ferme.ajouter_zone(new Culture(nom));
            System.out.println("Zone culture ajoutée.");
        } else if (type == 2) {
            ferme.ajouter_zone(new Elevage(nom));
            System.out.println("Zone élevage ajoutée.");
        } else if (type == 3){
            String espece = lireTexte("Espèce (ex: poisson, crevette) : ");
            int number = lireInt("Nombre : ");
            ferme.ajouter_zone(new Aqua(nom , espece, number));
        }else{
            System.out.println("Type de zone invalide.");
        }
    }

    private static void modifierZone() {
        Zone zone = selectionnerZone();
        if (zone == null) {
            return;
        }

        String nouveauNom = lireTexte("Nouveau nom : ");
        zone.setNom(nouveauNom);
        System.out.println("Zone renommée.");
    }

    private static void changerEtatZone() {
        Zone zone = selectionnerZone();
        if (zone == null) {
            return;
        }

        int choix = lireInt("1=Activer, 2=Suspendre : ");
        if (choix == 1) {
            ferme.activer(zone);
            System.out.println("Zone activée.");
        } else if (choix == 2) {
            ferme.suspendue(zone);
            System.out.println("Zone suspendue.");
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private static void afficherZones() {
        System.out.println(ferme.afficher_all_zones());
    }

    private static void ajouterCultureAUneZone() {
        Culture zone = selectionnerZoneCulture();
        if (zone == null) {
            return;
        }

        LocalDate plantation;
        while (true) {
            plantation = lireDate("Date de plantation (aaaa-mm-jj) : ");
            if (!plantation.isAfter(LocalDate.now())) break;
            System.out.println("Erreur: la date de plantation ne doit pas être après aujourd'hui. Veuillez réessayer.");
        }

        LocalDate recolte;
        while (true) {
            recolte = lireDate("Date de récolte (aaaa-mm-jj) : ");
            if (recolte.isAfter(plantation)) break;
            System.out.println("Erreur: la date de récolte doit être strictement après la date de plantation. Veuillez réessayer.");
        }
        Famille famille = lireFamille();
        float minPh = lireFloat("pH min : ");
        float maxPh;
        while (true) {
            maxPh = lireFloat("pH max : ");
            if (minPh < maxPh) break;
            System.out.println("Erreur: pH max doit être strictement supérieur à pH min. Veuillez réessayer.");
        }

        float minHum = lireFloat("Humidité min : ");
        float maxHum;
        while (true) {
            maxHum = lireFloat("Humidité max : ");
            if (minHum < maxHum) break;
            System.out.println("Erreur: Humidité max doit être strictement supérieur à Humidité min. Veuillez réessayer.");
        }

        zone.ajouter_culture(new Cult(plantation, recolte, famille, minPh, maxPh, minHum, maxHum));
        System.out.println("Culture ajoutée.");
    }

    private static void mettreAJourStadeCulture() {
        Culture zone = selectionnerZoneCulture();
        if (zone == null) {
            return;
        }

        Cult culture = selectionnerCulture(zone);
        if (culture == null) {
            return;
        }

        Croissance stade = lireStadeCroissance();
        culture.mettre_a_jour_stade_croissance(stade);
        System.out.println("Stade mis à jour.");
    }

    private static void afficherRapportCultures() {
        boolean trouve = false;
        for (Zone zone : ferme.getAll_zones()) {
            if (zone instanceof Culture) {
                trouve = true;
                System.out.println(((Culture) zone).rapport_etat_cult());
            }
        }

        if (!trouve) {
            System.out.println("Aucune zone culture.");
        }
    }

    private static void ajouterAnimalAUneZone() {
        Elevage zone = selectionnerZoneElevage();
        if (zone == null) {
            return;
        }

        int age = lireInt("Age (mois) : ");
        Type espece = lireTypeAnimal();
        float poids = lireFloat("Poids (kg) : ");

        zone.ajouter_animal(new Animal(age, espece, poids));
        System.out.println("Animal ajouté.");
    }

    private static void consignerEvenementSanitaire() {
        Elevage zone = selectionnerZoneElevage();
        if (zone == null) {
            return;
        }

        Animal animal = selectionnerAnimal(zone);
        if (animal == null) {
            return;
        }

        Type_Event_Sant type = lireTypeEvenement();
        String description = lireTexte("Description : ");
        Event_Sanitaire event = new Event_Sanitaire(type, description);
        animal.consigne_event_sanitaire(event);

        if (type == Type_Event_Sant.EVOL_POID) {
            float nouveauPoids = lireFloat("Nouveau poids (kg) : ");
            animal.setPoid(nouveauPoids);
        }

        System.out.println("Événement sanitaire enregistré.");
    }

    private static void definirProgrammeAlimentaire() {
        Zone zone = selectionnerZone();
        if (zone == null) {
            return;
        }

        if (zone instanceof Elevage) {
            Elevage elevage = (Elevage) zone;

            Animal animal = selectionnerAnimal(elevage);
            if (animal == null) {
                return;
            }

            String typeAliment = lireTexte("Type d'aliment : ");
            int quantite = lireInt("Quantité : ");
            animal.definir_programme(typeAliment, quantite);
            System.out.println("Programme alimentaire défini pour l'animal.");
        } else if (zone instanceof Aqua) {
            Aqua aqua = (Aqua) zone;

            String typeAliment = lireTexte("Type d'aliment : ");
            int quantite = lireInt("Quantité : ");
            aqua.definir_programme(typeAliment, quantite);
            System.out.println("Programme alimentaire défini pour l'aquaculture.");
        } else {
            System.out.println("La zone choisie n'est ni un élevage ni une aquaculture.");
        }
    }

    private static void afficherProgrammesAlimentaires() {
        boolean trouve = false;
        for (Zone zone : ferme.getAll_zones()) {
            if (zone instanceof Elevage) {
                Elevage elevage = (Elevage) zone;
                trouve = true;
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("║ Programmes alimentaires - " + String.format("%-14s", elevage.getNom()) + "║");
                System.out.println("╚════════════════════════════════════════════╝");
                for (Animal animal : elevage.getList_animaux()) {
                    System.out.println("\n┌─ Animal #" + animal.getId() + " ─────────────────────────────────┐");
                    System.out.println("│ Espèce : " + animal.getEspece());
                    System.out.println("│ Âge : " + animal.getAge() + " moins");
                    System.out.println("│ Poids : " + animal.getPoid() + " kg");
                    System.out.println("│ État de santé : " + animal.getEtat_sante());
                    System.out.println("│");
                    System.out.println("│ Alimentation :");
                    String prog = animal.afficher_prog();
                    for (String ligne : prog.split("\n")) {
                        if (!ligne.isEmpty()) {
                            System.out.println("│   " + ligne);
                        }
                    }
                    System.out.println("└─────────────────────────────────────────┘");
                }
            } else if (zone instanceof Aqua) {
                Aqua aqua = (Aqua) zone;
                trouve = true;
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("║ Programmes alimentaires - " + String.format("%-14s", aqua.getNom()) + "║");
                System.out.println("╚════════════════════════════════════════════╝");
                System.out.println("\n┌─ Aquaculture : " + aqua.getNom() + " ──────────────┐");
                System.out.println("│ Espèce : " + aqua.getEspece());
                System.out.println("│ Nombre d'individus : " + aqua.getNumber());
                System.out.println("│");
                System.out.println("│ Alimentation :");
                String prog = aqua.afficher_prog();
                for (String ligne : prog.split("\n")) {
                    if (!ligne.isEmpty()) {
                        System.out.println("│   " + ligne);
                    }
                }
                System.out.println("└─────────────────────────────────────────┘");
            }
        }

        if (!trouve) {
            System.out.println("\n  Aucune zone d'élevage ou aquaculture disponible.");
        }
    }

    private static void ajouterCapteur() {
        int type = lireInt("Type capteur (1=Numérique, 2=GPS) : ");
        Zone zone = selectionnerZone();
        if (zone == null) {
            return;
        }

        //comme chaque animal a un collierGPS , donc quand l'usager choisit 2-GPS , on lui affecte à un animal
        if (type == 1) {
            float minAvert = lireFloat("Seuil avertissement min : ");
            float maxAvert;
            while (true) {
                maxAvert = lireFloat("Seuil avertissement max : ");
                if (minAvert < maxAvert) break;
                System.out.println("Erreur: Seuil avertissement max doit être strictement supérieur au min. Veuillez réessayer.");
            }

            float minCrit = lireFloat("Seuil critique min : ");
            while (true) {
                if (minCrit < minAvert) break;
                System.out.println("Erreur: Seuil critique min doit être strictement inférieur au Seuil avertissement min. Veuillez réessayer.");
                minCrit = lireFloat("Seuil critique min : ");
            }
            float maxCrit;
            while (true) {
                maxCrit = lireFloat("Seuil critique max : ");
                if (minCrit < maxCrit && maxCrit > maxAvert) break;
                System.out.println("Erreur: Seuil critique max doit être strictement supérieur au Seuil avertissement max et > Seuil critique min. Veuillez réessayer.");
            }

            float valeur = lireFloat("Valeur actuelle : ");
            String unite = lireTexte("Unité : ");
            int typeNum = lireInt("Sous-type (1=Sol, 2=Env, 3=Eau, 4=Bio) : ");

            Num capteur;
            if (typeNum == 1) {
                capteur = new Sol(0, Stat_Capt.ACTIF, zone, minAvert, maxAvert, minCrit, maxCrit, valeur, unite);
            } else if (typeNum == 2) {
                capteur = new Env(0, Stat_Capt.ACTIF, zone, minAvert, maxAvert, minCrit, maxCrit, valeur, unite);
            } else if (typeNum == 3) {
                capteur = new Eau(0, Stat_Capt.ACTIF, zone, minAvert, maxAvert, minCrit, maxCrit, valeur, unite);
            } else if (typeNum == 4) {
                capteur = new Bio(0, Stat_Capt.ACTIF, zone, minAvert, maxAvert, minCrit, maxCrit, valeur, unite);
            } else {
                System.out.println("Sous-type invalide.");
                return;
            }

            zone.ajouter_capteur(capteur);
            System.out.println("Capteur numérique ajouté.");

        } else if (type == 2 && zone instanceof Elevage) {
            //on accede aux animaux de cette zone d'elevage pour que l'usager puisse choisir à quel animal il veut affecter le capteur GPS
            Animal animal = selectionnerAnimal((Elevage)zone) ; 
            if (animal == null) {
                return ; 
            }



            float latitude = lireFloat("Latitude actuelle : ");
            float longitude = lireFloat("Longitude actuelle : ");

            float latMinAvert = lireFloat("Latitude avertissement min : ");
            float latMaxAvert;
            while (true) {
                latMaxAvert = lireFloat("Latitude avertissement max : ");
                if (latMinAvert < latMaxAvert) break;
                System.out.println("Erreur: Latitude avertissement max doit être strictement supérieur au min. Veuillez réessayer.");
            }

            float latMinCrit = lireFloat("Latitude critique min : ");
            while (true) {
                if (latMinCrit < latMinAvert) break;
                System.out.println("Erreur: Latitude critique min doit être strictement inférieur à la Latitude avertissement min. Veuillez réessayer.");
                latMinCrit = lireFloat("Latitude critique min : ");
            }
            float latMaxCrit;
            while (true) {
                latMaxCrit = lireFloat("Latitude critique max : ");
                if (latMinCrit < latMaxCrit && latMaxCrit > latMaxAvert) break;
                System.out.println("Erreur: Latitude critique max doit être strictement supérieur à la Latitude avertissement max et > Latitude critique min. Veuillez réessayer.");
            }

            float lonMinAvert = lireFloat("Longitude avertissement min : ");
            float lonMaxAvert;
            while (true) {
                lonMaxAvert = lireFloat("Longitude avertissement max : ");
                if (lonMinAvert < lonMaxAvert) break;
                System.out.println("Erreur: Longitude avertissement max doit être strictement supérieur au min. Veuillez réessayer.");
            }

            float lonMinCrit = lireFloat("Longitude critique min : ");
            while (true) {
                if (lonMinCrit < lonMinAvert) break;
                System.out.println("Erreur: Longitude critique min doit être strictement inférieur à la Longitude avertissement min. Veuillez réessayer.");
                lonMinCrit = lireFloat("Longitude critique min : ");
            }
            float lonMaxCrit;
            while (true) {
                lonMaxCrit = lireFloat("Longitude critique max : ");
                if (lonMinCrit < lonMaxCrit && lonMaxCrit > lonMaxAvert) break;
                System.out.println("Erreur: Longitude critique max doit être strictement supérieur à la Longitude avertissement max et > Longitude critique min. Veuillez réessayer.");
            }

            GPS gps = new GPS(0, Stat_Capt.ACTIF, zone,
                    latitude, longitude,
                    latMinAvert, latMaxAvert, latMinCrit, latMaxCrit,
                    lonMinAvert, lonMaxAvert, lonMinCrit, lonMaxCrit);
            zone.ajouter_capteur(gps);
            animal.setCollierGPS(gps);
            System.out.println("Capteur GPS ajouté et affecté à l'animal choisi!");

        } else {
            System.out.println("Type de capteur invalide.");
        }
    }

    private static void changerStatutCapteur() {
        Capteur capteur = selectionnerCapteur();
        if (capteur == null) {
            return;
        }

        Zone zone = capteur.getZone();
        if (zone.getStatus() != STATUS.ACTIF) {
            System.out.println("La zone est suspendue. Impossible de modifier le statut du capteur.");
            return;
        }

        int choix = lireInt("1=Actif, 2=Suspendu, 3=Défaillant : ");
        if (choix == 1) {
            capteur.changeStatus(Stat_Capt.ACTIF);
        } else if (choix == 2) {
            capteur.changeStatus(Stat_Capt.SUSP);
        } else if (choix == 3) {
            capteur.changeStatus(Stat_Capt.DEF);
        } else {
            System.out.println("Choix invalide.");
            return;
        }

        System.out.println("Statut du capteur mis à jour.");
    }

    private static void enregistrerReleveManuel() {
        int type = lireInt("Type capteur (1=Numérique, 2=GPS) : ");

        if (type == 1) {
            Num capteur = selectionnerCapteurNumerique();
            if (capteur == null) {
                return;
            }
            if (capteur.getStatut() != Stat_Capt.ACTIF) {
                System.out.println("Le capteur n'est pas actif. Impossible d'enregistrer un relevé.");
                return;
            }
            Releve_Generale releve = capteur.generer_releve();
            enregistrerAlerteSiBesoin(releve);
            System.out.println("Relevé numérique enregistré.");

        } else if (type == 2) {
            GPS capteur = selectionnerCapteurGPS();
            if (capteur == null) {
                return;
            }
            if (capteur.getStatut() != Stat_Capt.ACTIF) {
                System.out.println("Le capteur n'est pas actif. Impossible d'enregistrer un relevé.");
                return;
            }
            Releve_Generale releve = capteur.generer_releve();
            enregistrerAlerteSiBesoin(releve);
            System.out.println("Relevé GPS enregistré.");

        } else {
            System.out.println("Type invalide.");
        }
    }

    private static void enregistrerAlerteSiBesoin(Releve_Generale releve) {
        if (releve.getNiveau_releve() == Niveau_Releve.CRIT) {
            ferme.engistrer_alerte(new Alerte(releve, Gravite.CRIT));
        } else if (releve.getNiveau_releve() == Niveau_Releve.AVERT) {
            ferme.engistrer_alerte(new Alerte(releve, Gravite.AVERT));
        }
    }

    /*private static void enregistrerAlerteSiBesoinGPS(Releve_GPS releve) {
        if (releve.getNiveau_releve() == Niveau_Releve.CRIT) {
            ferme.engistrer_alerte(new Alerte(releve, Gravite.CRIT));
        } else if (releve.getNiveau_releve() == Niveau_Releve.AVERT) {
            ferme.engistrer_alerte(new Alerte(releve, Gravite.AVERT));
        }
    }*/

    private static void consulterHistoriqueCapteur() {
        int type = lireInt("Type capteur (1=Numérique, 2=GPS) : ");
        LocalDate date1 = lireDate("Date début (aaaa-mm-jj) : ");
        LocalDate date2;
        while (true) {
            date2 = lireDate("Date fin (aaaa-mm-jj) : ");
            if (date2.isAfter(date1)) break;
            System.out.println("Erreur: la date fin doit être strictement après la date début. Veuillez réessayer.");
        }

        if (type == 1) {
            Num capteur = selectionnerCapteurNumerique();
            if (capteur == null) {
                return;
            }
            System.out.println(capteur.getHistorique_releve().display_releves(date1, date2));
        } else if (type == 2) {
            GPS capteur = selectionnerCapteurGPS();
            if (capteur == null) {
                return;
            }
            System.out.println(capteur.getHistorique_releve().display_releves(date1, date2));
        } else {
            System.out.println("Type invalide.");
        }
    }

    private static void afficherGraphique() {
        System.out.println("pas encore implémenté!") ;
    }

    private static void afficherGraphiqueCapteur(Capteur capteur) {
        if (capteur instanceof Num) {
            Num num = (Num) capteur;
            List<Releve_Generale> releves = num.getHistorique_releve().getContent();
            if (releves.isEmpty()) {
                System.out.println("Aucun relevé.");
                return;
            }

            float min = Float.MAX_VALUE;
            float max = Float.MIN_VALUE;
            for (Releve_Generale releve : releves) {
                if (releve instanceof Releve) {
                    Releve r = (Releve) releve;
                    min = Math.min(min, r.getValeur());
                    max = Math.max(max, r.getValeur());
                }
            }

            System.out.println("=== Graphique relevés capteur #" + num.getCode() + " ===");
            for (Releve_Generale releve : releves) {
                if (releve instanceof Releve) {
                    Releve r = (Releve) releve;
                    System.out.println(formatGraphLine(r.getDate().toString(), r.getValeur(), r.getUnite(), r.getNiveau_releve(), min, max));
                }
            }
        } else if (capteur instanceof GPS) {
            GPS gps = (GPS) capteur;
            List<Releve_Generale> releves = gps.getHistorique_releve().getContent();
            if (releves.isEmpty()) {
                System.out.println("Aucun relevé.");
                return;
            }

            System.out.println("=== Graphique relevés GPS #" + gps.getCode() + " ===");
            for (Releve_Generale releve : releves) {
                if (releve instanceof Releve_GPS) {
                    Releve_GPS r = (Releve_GPS) releve;
                    float score = Math.abs(r.getLatitude()) + Math.abs(r.getLongitude());
                    System.out.println(formatGraphLine(r.getDate().toString(), score, "coord", r.getNiveau_releve(), 0, 200));
                }
            }
        }
    }

    private static void afficherGraphiqueZone(Zone zone) {
        System.out.println("=== Graphique zone : " + zone.getNom() + " ===");
        if (zone.getAll_capteurs().isEmpty()) {
            System.out.println("Aucun capteur.");
            return;
        }

        for (Capteur capteur : zone.getAll_capteurs()) {
            afficherGraphiqueCapteur(capteur);
        }
    }

    private static void afficherAlertesActives() {
        System.out.println(ferme.getHist_alertes().display_sorted_alertes());
    }

    private static void acquitterAlerte() {
        Alerte alerte = selectionnerAlerteNumerique();
        if (alerte != null) {
            ferme.aquitter_alerte(alerte);
            System.out.println("Alerte acquittée.");
        }
    }

    private static void supprimerAlerte() {
        Alerte alerte = selectionnerAlerteNumerique();
        if (alerte != null) {
            ferme.supprimer_alerte(alerte);
            System.out.println("Alerte supprimée.");
        }
    }

    private static void enregistrerProductionZone() {
        Zone zone = selectionnerZone();
        if (zone == null) {
            return;
        }

        if (zone.getStatus() != STATUS.ACTIF) {
            System.out.println("La zone est suspendue. Impossible d'enregistrer une production.");
            return;
        }

        Type_Prod type = lireTypeProduction();
        if ((zone instanceof Elevage && (type == Type_Prod.LAIT || type == Type_Prod.OEUF)) || (zone instanceof Culture && (type == Type_Prod.RECOLTE || type == Type_Prod.REND_CULT))) {
           int quantite = lireInt("Quantité : ");
           String unite = lireTexte("Unité : ");

           zone.ajouter_prod(new Prod(LocalDate.now(), LocalTime.now(), type, quantite, unite));
           System.out.println("Production enregistrée.");
        }else{
            System.out.println("Type de production imcompatible avec le type de la zone choisie!!") ; 
        }
    }

    private static void afficherHistoriqueProductions() {
        boolean trouve = false;
        for (Zone zone : ferme.getAll_zones()) {
            trouve = true;
            System.out.println("=== Productions - " + zone.getNom() + " ===");
            if (zone.getHistorique_prod().getContent().isEmpty()) {
                System.out.println("Aucune production.");
            } else {
                for (Prod prod : zone.getHistorique_prod().getContent()) {
                    System.out.println(prod.display_prod());
                }
            }
        }

        if (!trouve) {
            System.out.println("Aucune zone.");
        }
    }

    private static Zone selectionnerZone() {
        if (ferme.getAll_zones().isEmpty()) {
            System.out.println("Aucune zone disponible.");
            return null;
        }

        afficherZonesPourSelection();
        int code = lireInt("Code de la zone : ");
        for (Zone zone : ferme.getAll_zones()) {
            if (zone.getCode() == code) {
                return zone;
            }
        }

        System.out.println("Zone introuvable.");
        return null;
    }

    private static Culture selectionnerZoneCulture() {
        Zone zone = selectionnerZone();
        if (zone instanceof Culture) {
            if (zone.getStatus() != STATUS.ACTIF) {
                System.out.println("Cette zone culture est suspendue. Impossible d'effectuer cette opération.");
                return null;
            }
            return (Culture) zone;
        }
        if (zone != null) {
            System.out.println("Cette zone n'est pas une zone culture.");
        }
        return null;
    }

    private static Elevage selectionnerZoneElevage() {
        Zone zone = selectionnerZone();
        if (zone instanceof Elevage) {
            return (Elevage) zone;
        }
        if (zone != null) {
            System.out.println("Cette zone n'est pas une zone élevage.");
        }
        return null;
    }

    private static void afficherZonesPourSelection() {
        System.out.println("=== Zones disponibles ===");
        for (Zone zone : ferme.getAll_zones()) {
            System.out.println("Code " + zone.getCode() + " | " + zone.getClass().getSimpleName()
                    + " | " + zone.getNom() + " | statut=" + zone.getStatus());
        }
    }

    private static Cult selectionnerCulture(Culture zone) {
        List<Cult> cultures = new ArrayList<>(zone.getList_cultures());
        if (cultures.isEmpty()) {
            System.out.println("Aucune culture dans cette zone.");
            return null;
        }

        for (int i = 0; i < cultures.size(); i++) {
            System.out.println((i + 1) + ". " + cultures.get(i).get_etat_cult());
        }

        int choix = lireInt("Choix de la culture : ");
        if (choix < 1 || choix > cultures.size()) {
            System.out.println("Choix invalide.");
            return null;
        }

        return cultures.get(choix - 1);
    }

    private static Animal selectionnerAnimal(Elevage zone) {
        List<Animal> animaux = new ArrayList<>(zone.getList_animaux());
        if (animaux.isEmpty()) {
            System.out.println("Aucun animal dans cette zone.");
            return null;
        }

        for (Animal animal : animaux) {
            System.out.println(animal);
        }

        int id = lireInt("Id de l'animal : ");
        for (Animal animal : animaux) {
            if (animal.getId() == id) {
                return animal;
            }
        }

        System.out.println("Animal introuvable.");
        return null;
    }

    private static Num selectionnerCapteurNumerique() {
        List<Num> capteurs = new ArrayList<>();
        for (Zone zone : ferme.getAll_zones()) {
            for (Capteur capteur : zone.getAll_capteurs()) {
                if (capteur instanceof Num) {
                    capteurs.add((Num) capteur);
                }
            }
        }

        if (capteurs.isEmpty()) {
            System.out.println("Aucun capteur numérique.");
            return null;
        }

        for (Num capteur : capteurs) {
            afficherCapteurSelection(capteur);
        }

        int code = lireInt("Code du capteur : ");
        for (Num capteur : capteurs) {
            if (capteur.getCode() == code) {
                return capteur;
            }
        }

        System.out.println("Capteur introuvable.");
        return null;
    }

    private static GPS selectionnerCapteurGPS() {
        List<GPS> capteurs = new ArrayList<>();
        for (Zone zone : ferme.getAll_zones()) {
            for (Capteur capteur : zone.getAll_capteurs()) {
                if (capteur instanceof GPS) {
                    capteurs.add((GPS) capteur);
                }
            }
        }

        if (capteurs.isEmpty()) {
            System.out.println("Aucun capteur GPS.");
            return null;
        }

        for (GPS capteur : capteurs) {
            afficherCapteurSelection(capteur);
        }

        int code = lireInt("Code du capteur : ");
        for (GPS capteur : capteurs) {
            if (capteur.getCode() == code) {
                return capteur;
            }
        }

        System.out.println("Capteur introuvable.");
        return null;
    }

    private static Capteur selectionnerCapteur() {
        int type = lireInt("Type capteur (1=Numérique, 2=GPS) : ");
        if (type == 1) {
            return selectionnerCapteurNumerique();
        }
        if (type == 2) {
            return selectionnerCapteurGPS();
        }

        System.out.println("Type invalide.");
        return null;
    }

    private static void afficherCapteurSelection(Capteur capteur) {
        String resume = capteur.display_capteur().trim().replace("\n", " ");
        System.out.println(resume + " | Type : " + capteur.getClass().getSimpleName());
        System.out.println("\n");
    }

    private static Alerte selectionnerAlerteNumerique() {
        List<Alerte> alertes = new ArrayList<>();
        for (Alerte alerte : ferme.getAlertes()) {
            alertes.add(alerte);
        }

        if (alertes.isEmpty()) {
            System.out.println("Aucune alerte disponible.");
            return null;
        }

        Collections.sort(alertes);
        for (int i = 0; i < alertes.size(); i++) {
            System.out.println((i + 1) + ". " + alertes.get(i).getGravite());
            System.out.println(alertes.get(i).display_alerte());
        }

        int choix = lireInt("Choix de l'alerte : ");
        if (choix < 1 || choix > alertes.size()) {
            System.out.println("Choix invalide.");
            return null;
        }

        return alertes.get(choix - 1);
    }

    private static String lireTexte(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    private static int lireInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Veuillez entrer un entier.");
            }
        }
    }

    private static float lireFloat(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Float.parseFloat(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private static LocalDate lireDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Format de date invalide. Utilisez aaaa-mm-jj.");
            }
        }
    }

    private static LocalTime lireHeure(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalTime.parse(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Format d'heure invalide. Utilisez HH:mm.");
            }
        }
    }

    private static Famille lireFamille() {
        System.out.println("Famille (1=CEREAL, 2=LEGUMES, 3=FRUITS) : ");
        int choix = lireInt("Choix : ");
        if (choix == 1) {
            return Famille.CEREAL;
        } else if (choix == 2) {
            return Famille.LEGUMES;
        } else if (choix == 3) {
            return Famille.FRUITS;
        }
        System.out.println("Choix invalide, CEREAL utilisé par défaut.");
        return Famille.CEREAL;
    }

    private static Croissance lireStadeCroissance() {
        System.out.println("Stade (1=SEMIS, 2=GERM, 3=CROIS, 4=MAT, 5=REC) : ");
        int choix = lireInt("Choix : ");
        switch (choix) {
            case 1:
                return Croissance.SEMIS;
            case 2:
                return Croissance.GERM;
            case 3:
                return Croissance.CROIS;
            case 4:
                return Croissance.MAT;
            case 5:
                return Croissance.REC;
            default:
                System.out.println("Choix invalide, SEMIS utilisé par défaut.");
                return Croissance.SEMIS;
        }
    }

    private static Type lireTypeAnimal() {
        System.out.println("Espèce (1=RUMI, 2=VOL) : ");
        int choix = lireInt("Choix : ");
        if (choix == 1) {
            return Type.RUMI;
        } else if (choix == 2) {
            return Type.VOL;
        }
        System.out.println("Choix invalide, RUMI utilisé par défaut.");
        return Type.RUMI;
    }

    private static Type_Event_Sant lireTypeEvenement() {
        System.out.println("Type d'événement (1=MALADIES, 2=EVOL_POID) : ");
        int choix = lireInt("Choix : ");
        if (choix == 1) {
            return Type_Event_Sant.MALADIES;
        } else if (choix == 2) {
            return Type_Event_Sant.EVOL_POID;
        }
        System.out.println("Choix invalide, MALADIES utilisé par défaut.");
        return Type_Event_Sant.MALADIES;
    }

    private static Type_Prod lireTypeProduction() {
        System.out.println("Type de production (1=LAIT, 2=OEUF, 3=RECOLTE, 4=REND_CULT) : ");
        int choix = lireInt("Choix : ");
        if (choix == 1) {
            return Type_Prod.LAIT;
        } else if (choix == 2) {
            return Type_Prod.OEUF;
        } else if (choix == 3) {
            return Type_Prod.RECOLTE;
        } else if (choix == 4) {
            return Type_Prod.REND_CULT;
        }
        System.out.println("Choix invalide, RECOLTE utilisé par défaut.");
        return Type_Prod.RECOLTE;
    }

    private static String decrireNiveau(Niveau_Releve niveau) {
        if (niveau == Niveau_Releve.NORMAL) {
            return "[NORMAL]";
        } else if (niveau == Niveau_Releve.AVERT) {
            return "[AVERT ]";
        }
        return "[CRIT  ]";
    }

    private static String formatGraphLine(String etiquette, float valeur, String unite, Niveau_Releve niveau, float min, float max) {
        int taille = 1;
        if (max > min) {
            taille = Math.max(1, Math.min(30, Math.round(((valeur - min) / (max - min)) * 30f)));
        }

        StringBuilder barre = new StringBuilder();
        for (int i = 0; i < taille; i++) {
            barre.append('#');
        }

        return etiquette + " | " + barre + " " + valeur + " " + unite + " | " + niveau;
    }
}
