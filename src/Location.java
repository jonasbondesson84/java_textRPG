import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Location {
    Random randomNr = new Random();
    private int locationNumber;



    //Constructor
    public Location(int locationNumber) {
        this.locationNumber = locationNumber;
    }

    //Getters and setters
    public int getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
    }

    //Methods
    public int location(int locationNumber, Character myHero, functions function, ArrayList<Monster> monsterArrayList) {
        Scanner sc = new Scanner(System.in);
        String answer;
        Monster currentMonster;
        int attackOutcome;


        switch (locationNumber) {
            //--------------------------------------------------------------------
            case 100 -> { //I huset
                System.out.println("\nDu befinner dig i ett rum. Du ser dig omkring. Det är ett litet rum, i rummet finns ett bord och en stol. I ena hörnet finns en eldstad. ");

                if (!myHero.getInventory().contains(Item.sword)) {//Om man redan har svärdet kan man inte plocka upp det igen.
                    System.out.println("Du ser en kista bredvid eldstaden. Vill du (1) öppna den eller (2) låta den vara?");
                    answer = sc.nextLine();
                    switch (answer) {
                        case "1", "öppna den" -> {
                            //Slumpar fram om man kommer få upp svärdet eller möta en spindel
                            if (randomNr.nextBoolean()) { //50% chans att man hittar svärd och flaska.
                                System.out.println("\nDu har hittat ett svärd och en flaska. ");
                                System.out.println("Vill du (1) plocka upp svärdet och flaskan eller (2) låta det vara?");
                                answer = sc.nextLine();
                                switch (answer.toLowerCase()) {
                                    case "1", "plocka upp svärdet och flaskan" -> {
                                        System.out.println("Du plockade upp svärdet och la det i din väska.");
                                        System.out.println("Flaskan som du hittade innehåller en helningsdryck. Du lägger den i väskan också.");
                                        myHero.getInventory().add(Item.sword);
                                        myHero.getWeaponsList().add(Item.sword);
                                        myHero.getInventory().add(Item.healthPotion);
                                        myHero.getMiscList().add(Item.healthPotion);

                                    }
                                    case "2", "låta det vara" -> {
                                        System.out.println("Du låter svärdet ligga kvar i kistan.");
                                    }
                                    default -> {
                                        System.out.println("Fel input");
                                        return 100;
                                    }
                                }

                            } else { //50% chans att man möter en spindel och måste slåss mot den.
                                currentMonster = monsterArrayList.get(0);
                                System.out.println("När du försökte öppna kistan dök det upp en " + currentMonster.getName().toLowerCase());
                                attackOutcome = function.encounter(myHero, currentMonster, function);  //KOllar hur det går i attacken.
                                if (attackOutcome == 0) {
                                    return 100;
                                } else if (attackOutcome == 1) {
                                    return 0;
                                } else if (attackOutcome == 2) {
                                    return myHero.getLastLocation();
                                }

                            }
                        }
                        case "2", "låta den vara" -> {
                            System.out.println("\nDu struntar i den tråkiga kistan, det är säkert inget värdefullt i den ändå...");
                        }
                        default -> {
                            System.out.println("Fel input");
                            return 100;
                        }
                    }
//
                } //Efter man har plockat upp svärdet kan man inte se kistan längre.
                System.out.println("Rummet är tomt. Du ser en dörr. Vill du (1) öppna dörren (2) stanna kvar inne i rummet?");
                answer = sc.nextLine();
                switch (answer.toLowerCase()) {
                    case "1", "öppna dörren" -> {
                        System.out.println("\nDu öppnar dörren.");
                        myHero.setLastLocation(100);
                        return 200;
                    }
                    case "2", "stanna kvar" -> {
                        System.out.println("Du sätter dig ner på en stol en stund...");
                        System.out.println("...");
                        System.out.println("Du ställer dig upp igen.");
                        return 100;
                    }
                    default -> {
                        System.out.println("Fel input");
                        return 100;
                    }
                }
                //}
            }
            //--------------------------------------------------------------------
            case 200 -> { //Utanför huset
                System.out.println("Du står utanför huset. Du befinner dig i en liten by med ett fåtal hus.");

                if (myHero.isMonsterOutSideHouse() || function.randomSpawnMonster(7)) {  //första gången man går utanför dörren kommer det alltid finnas ett monster, annars finns det en mindre möjlighet att det finns ett monster.
                    currentMonster = function.randomMonsterByDifficulty(monsterArrayList, myHero); //slumpar fram ett monster beroende på vilken level man är

                    System.out.println("Du ser en " + currentMonster.getName() + ".");
                    System.out.println("Vill du (1) attackera eller (2) springa?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "attackera" -> { //Attackera
                            attackOutcome = function.encounter(myHero, currentMonster, function);
                            if (attackOutcome == 0) {
                                myHero.setMonsterOutSideHouse(false);
                                return 200;
                            } else if (attackOutcome == 1) {
                                return 0;
                            } else if (attackOutcome == 2) {
                                return myHero.getLastLocation();
                            }
                        }
                        case "2", "springa" -> { //Springa iväg
                            switch (function.runAway(myHero, currentMonster)) {
                                case 0 -> {  //Om monstret är snabbare än du.
                                    System.out.println(currentMonster.getName() + " hann ikapp dig och attackerar dig.");
                                    attackOutcome = function.encounter(myHero, currentMonster, function);
                                    if (attackOutcome == 0) {
                                        myHero.setMonsterOutSideHouse(false);
                                        break;
                                    } else if (attackOutcome == 1) {
                                        return 0;
                                    } else if (attackOutcome == 2) {
                                        return myHero.getLastLocation();
                                    }
                                }
                                case 1, 2 -> {
                                    System.out.println("Du lyckades springa ifrån " + currentMonster.getName().toLowerCase() + ". Du sprang tillbaks till den senaste platsen du var på.");

                                    return myHero.getLastLocation();
                                }
                            }
                        }
                        default -> {
                            System.out.println("Fel input");
                            return 200;
                        }
                    }
                } else {
                    System.out.println("\nTill höger ser du en skog, till vänster ser du en väg, bakom dig ser du en dörr.");
                    System.out.println("Vill du (1) gå till skogen, (2) gå till vägen, (3) eller öppna dörren?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "gå till skogen" -> {
                            System.out.println("Du går mot skogen.");
                            myHero.setLastLocation(200);
                            return 300;
                        }
                        case "2", "gå till vägen" -> {
                            System.out.println("Du går mot vägen.");
                            myHero.setLastLocation(200);
                            return 400;
                        }
                        case "3", "öppna dörren" -> {
                            myHero.setLastLocation(200);
                            System.out.println("Du går in igen.");
                            return 100;
                        }
                        default -> {
                            System.out.println("Fel input");
                            return 200;
                        }
                    }
                }

            }


            //--------------------------------------------------------------------
            case 300 -> { // Skogen


                if (function.checkForInventory(myHero).contains("Lykta")) {  //Har man hittat lyktan kan man gå vidare
                    System.out.println("\nDet är en mörk skog, men med hjälp av lyktan du har kan du se en liten stig som leder längre in i skogen.");
                    System.out.println("Vill du (1) följa stigen eller (2) gå tillbaks?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "följa stigen" -> {
                            myHero.setLastLocation(300);
                            return 301;
                        }
                        case "2", "gå tillbaks" -> {
                            myHero.setLastLocation(300);
                            return 200;
                        }
                        default -> {
                            System.out.println("Fel input.");
                            return 300;
                        }
                    }
                } else { //Har man inte lyktan går man bara ut igen.
                    System.out.println("\nDu kommer in i en mörk skog. Du skulle behöva något som lyser upp vägen för dig.");
                    System.out.println("Det är alldeles för mörkt och du väljer att gå tillbaka.");
                    myHero.setLastLocation(300);
                    return 200;
                }
            }
            //--------------------------------------------------------------------
            case 301 -> { //stigen man ser om man har lyktan

            }
            //--------------------------------------------------------------------
            case 400 -> { // Vägen
                System.out.println("Det är en gammal, dammig väg. Du vandrar vidare längs med vägen.");
                if (!function.checkForInventory(myHero).contains("Lykta")) { //Om man redan har lyktan träffar man inte kvinnan igen
                    System.out.println("\nDu träffar en gammal kvinna. Hon försöker prata med dig.");
                    System.out.println("Vill du (1) prata med kvinnan eller (2) strunta i kvinnan?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "prata med kvinnan" -> {
                            System.out.println("\nKvinnan berättar att hon är på väg hem till släktingar på besök. Hon har fått höra att det finns rövare på vägen och undrar ifall du har lust att följa henne sista biten?");
                            System.out.println("Vill du (1) följa med kvinnan eller (2) låta henna gå själv?");
                            answer = sc.nextLine();
                            switch (answer.toLowerCase()) {
                                case "1", "följa med kvinnan" -> {
                                    System.out.println("Du lovar kvinnan att följa henne sista biten.");
                                    myHero.setLastLocation(400);
                                    return 420;
                                }
                                case "2", "låta henne gå själv" -> {
                                    System.out.println("Du säger åt kvinnan att du inte har tid och fortsätter på vägen.");
                                    myHero.setLastLocation(400);
                                    return 410;
                                }
                                default -> {
                                    System.out.println("Fel input");
                                    return 400;
                                }
                            }
                        }
                        case "2", "strunta i kvinnan" -> {
                            System.out.println("Du fortsätter att gå på vägen.");
                            myHero.setLastLocation(400);
                            return 410;
                        }
                        default -> {
                            System.out.println("Fel input");
                            return 400;
                        }
                    }

                }

            }
            //--------------------------------------------------------------------
            case 410 -> { //vägskälet
                System.out.println("Du går ...");
                System.out.println("Du kommer fram till ett vägskäl. Det finns en skylt pekar åt tre håll: (1) Staden, (2) Vattenfallet, (3) Byn");
                System.out.println("Vill du gå (1) mot staden, (2) mot vattenfallet eller (3) mot byn?");
                answer = sc.nextLine();;
                switch (answer.toLowerCase()) {
                    case "1", "mot staden" -> {
                        System.out.println("Du går mot staden. Efter en stund kommer du fram till ytterligare ett vägskäl, men där vägen till höger är blockerad av stora stenar. Du väljer då att gå åt vänster.");
                        myHero.setLastLocation(410);
                        return 420;
                    }
                    case "2", "mot vattenfallet" -> {
                        System.out.println("Du går mot vattenfallet.");
                        myHero.setLastLocation(410);
                        return 411;

                    }
                    case "3", "mot byn" -> {
                        System.out.println("Du går mot byn.");
                        myHero.setLastLocation(410);
                        return 200;
                    }
                    default -> {
                        System.out.println("Fel input");
                        return 410;
                    }
                }

            }
            //--------------------------------------------------------------------
            case 411 -> { //vattenfallet

            }
            //--------------------------------------------------------------------
            case 420 -> { //mot staden

            }
        }

        //--------------------------------------------------------------------        }
        return 0;
    }
}
