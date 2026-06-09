/*import java.util.* ; 
import java.time.* ;

public class Main3 {
    Scanner sc = new Scanner(System.in) ; 
    public void menu() {
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
        System.out.println("16. Consulter l'historique d'un capteur");
        System.out.println("17. Afficher un graphique des relevés");
        System.out.println("18. Afficher les alertes actives triées");
        System.out.println("19. Acquitter une alerte");
        System.out.println("20. Supprimer une alerte");
        System.out.println("21. Enregistrer une production de zone");
        System.out.println("22. Afficher l'historique des productions");
        System.out.println("0. Quitter");
        System.out.println("==============================");
    }

    //initialisation de la ferme 
    public void initialiser_ferme(Ferme ferme) {
         
        Zone z1 = new Culture("Zone des tomates") ; 
        Zone z2 = new Elevage("Zone des poulets") ;
        Zone z3 = new Aqua("Zone de l'aquaculture" , "poisson", 100) ;
        ferme.ajouter_zone(z1);
        ferme.ajouter_zone(z2);
        ferme.ajouter_zone(z3);
    }

    public void function1(Ferme ferme) {
        System.out.print("Enter the type : 1-Culture | 2-Elevage | 3-Aquacole : ");
        int type = sc.nextInt() ;
        sc.nextLine(); // Consume newline
        while (type < 1 || type > 3) {
            System.out.println("Invalid type!");
            System.out.print("Enter the type : 1-Culture | 2-Elevage | 3-Aquacole : ");
            type = sc.nextInt();
            sc.nextLine(); // Consume newline
        }
        System.out.print("Enter the name of the zone : ");
        String name = sc.nextLine() ;
        if (type == 1){
            Culture c = new Culture(name) ;
            ferme.ajouter_zone(c);
        }
        else if (type == 2){
            Elevage e = new Elevage(name) ;
            ferme.ajouter_zone(e);
        }
        else if (type == 3){
            System.out.print("Enter the species : ");
            String espece = sc.nextLine() ;
            System.out.print("Enter the number of individuals : ");
            int number = sc.nextInt() ;
            Aqua a = new Aqua(name, espece, number) ;
            ferme.ajouter_zone(a);
        }
        System.out.println("Zone added successfully!");
    }

    //modifier une zone 
    public void function2(Ferme ferme) {
        function4(ferme);
        System.out.print("Enter the code of the zone to modify : ");
        int code = sc.nextInt() ;
        sc.nextLine(); // Consume newline

    }

    //afficher les zones de la ferme
    public void function4(Ferme ferme) {
        System.out.println(ferme.afficher_all_zones());
    }



    public static void main(String[] args) {
        Main3 app = new Main3() ; 
        Ferme ferme = new Ferme() ;
        app.initialiser_ferme(ferme);
        int choice = 1;
        do {
            app.menu(); 
            System.out.print("--> Enter your choice : ");
            choice = app.sc.nextInt();
            app.sc.nextLine(); // Consume newline
            switch (choice) {
                case 1: 
                //ajouter une zone
                app.function1(ferme) ;
                System.out.println("Press Enter to continue...");
                app.sc.nextLine();
                break ;

                case 2:
                break ;

                case 3:
                break ;
 
                case 4:
                app.function4(ferme);
                break ;

                case 5:
                break ;

                case 6:
                break ;

                case 7:
                break ;

                case 8:
                break ;

                case 9:
                break ;

                case 10:
                break ;

                case 11:
                break ;

                case 12:
                break ;

                case 13:
                break ;

                case 14:
                break ;

                case 15:
                break ;

                case 16:
                break ;

                case 17:
                break ;

                case 18:
                break ;

                case 19:
                break ;

                case 20:
                break ;

                case 21:
                break ;

                case 22:
                break ;

                case 0: 
                break ; 

                default:
                    System.out.println("Invalid choice. Please try again.");

            }

        }while (choice != 0);
    }
}*/