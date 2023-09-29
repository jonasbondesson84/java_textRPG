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

                System.out.println("Du står utanför ditt hus. Du befinner dig i en liten by med ett fåtal hus.");
                if(myHero.getMiscList().contains(Item.magicPotion)) {
                    System.out.println("Du ser plötsligt en symbol inristad på dörren. Den ser ut såhär: '⧳'");
                }

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
                if (myHero.getMiscList().contains(Item.magicPotion)) {  //Har man hittat lyktan kan man gå vidare
                    System.out.println("\nDet är en mörk skog, men med hjälp av den magiska flaskan du har fck av Ragna du se en liten stig som leder längre in i skogen.");
                    System.out.println("Vill du (1) följa stigen eller (2) gå tillbaks?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "följa stigen" -> {
                            myHero.setLastLocation(300);
                            return 310;
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
            case 310 -> { //stigen man ser om man har lyktan
                if(function.randomSpawnMonster(7)) {
                    currentMonster = function.randomMonsterByDifficulty(monsterArrayList, myHero);
                    System.out.println("\nDu går utmed stigen när plötsligt en " + currentMonster.getName().toLowerCase() + " dyker upp.");
                    System.out.println("Vill du (1) attackera eller (2) springa tillbaka?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "attackera" -> { //Attackera
                            attackOutcome = function.encounter(myHero, currentMonster, function);
                            if (attackOutcome == 0) {
                                myHero.setLastLocation(310);
                                return 320;
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
                                        myHero.setLastLocation(310);
                                        return 320;

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
                            return 310;
                        }
                    }
                } else {
                    myHero.setLastLocation(310);
                    System.out.println("Du fortsätter längs med stigen.");
                    System.out.println("...");
                    return 320;
                }

            }
            //--------------------------------------------------------------------
            case 320 -> {
                if(!myHero.isTriedOpenChest()) {
                    System.out.println("Du kommer fram till en glänta, där det står ett enormt, ihåligt träd. Inne i trädet ser du tre kistor. Ovanför kistorna sitter en skylt.");
                    System.out.println("Du har hittat de hemliga kistorna. Bara en av de är riktig, de andra är magiska och kommer skada dig om du försöker öppna dem.");
                    System.out.println("Du har bara en chans på dig att öppna en kista, efter det kommer de försvinna för alltid.");
                    System.out.println("På locket på varje kista är det inristat tre symboler.");
                    System.out.println("Kista 1: ⧳ ♈ ⊗");
                    System.out.println("Kista 2: ⧳ ¥ ⪥");
                    System.out.println("Kista 3: ⊗ ⪥ ⧳");
                    System.out.println("Vill du (1) öppna en kista eller (2) vända tillbaka?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "öppna en kista" -> {
                            System.out.println("Vill du öppna kista (1), kista (2) eller kista (3)");
                            answer = sc.nextLine();
                            switch (answer.toLowerCase()) {
                                case "1", "2" -> {
                                    System.out.println("Ett stort giftmoln stiger upp från kistan när du försöker öppna den. Du faller avsvimmad ner till marken och förlorar 30 liv.");
                                    myHero.setHealth(myHero.getHealth()-30);
                                    if(myHero.getHealth() <= 0) {
                                        System.out.println("Du överlevde inte giftmolnet.");
                                        return 0;
                                    } else {
                                        System.out.println("Du vaknar upp efter en stund och ser att kistorna är borta. Du går tillbaks längs med stigen.");
                                        System.out.println("Du har nu " + myHero.getHealth() + " liv kvar.");
                                        myHero.setTriedOpenChest(true);
                                        myHero.setLastLocation(310);
                                        return 300;
                                    }

                                }
                                case "3" -> {
                                    System.out.println("Du öppnade rätt kista!");
                                    System.out.println("Du hittade en draklans, en helningsflaska och ett par bevingade stövlar som gör dig extra snabb.");
                                    myHero.getMiscList().add(Item.healthPotion);
                                    myHero.getInventory().add(Item.healthPotion);

                                    myHero.getMiscList().add(Item.wingedBoots);
                                    myHero.getInventory().add(Item.wingedBoots);
                                    myHero.setSpeed(myHero.getSpeed()+5);

                                    myHero.getWeaponsList().add(Item.dragonLance);
                                    myHero.getInventory().add(Item.dragonLance);

                                    myHero.setTriedOpenChest(true);
                                    System.out.println("Du går tillbaka längs med stigen.");
                                    myHero.setLastLocation(310);
                                    return 300;
                                }
                            }

                        }
                        case "2", "vända tillbaka" -> {
                            System.out.println("Trots att det verkar väldigt lockande att öppna en kista vänder du om och går tillbaks längs med stigen.");
                            myHero.setLastLocation(310);
                            return 300;
                        }
                        default -> {
                            System.out.println("Fel input");
                            return 320;
                        }
                    }
                } else {
                    System.out.println("Du ser ett stort ihåligt träd. Det är helt tomt. Du går tillbaka längs med stigen.");
                    myHero.setLastLocation(310);
                    return 300;
                }

            }
            //--------------------------------------------------------------------
            case 400 -> { // Vägen
                System.out.println("Det är en gammal, dammig väg.");
                if (!myHero.getMiscList().contains(Item.magicPotion)) { //Om man redan har lyktan träffar man inte kvinnan igen
                    System.out.println("\nVid en stig ser du en gammal kvinna. Hon kommer fram och vill prata med dig.");
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
                System.out.println("Du går vidare längs vägen.");
                return 410;
            }
            //--------------------------------------------------------------------
            case 410 -> { //vägskälet
                System.out.println("...");
                System.out.println("Du kommer fram till ett vägskäl. Det finns en skylt pekar åt tre håll: (1) Staden, (2) Vattenfallet, (3) Byn");
                System.out.println("Vill du gå (1) mot staden, (2) mot vattenfallet eller (3) mot byn?");
                answer = sc.nextLine();;
                switch (answer.toLowerCase()) {
                    case "1", "mot staden" -> {
                        System.out.println("Du går mot staden. Efter en stund kommer du fram till ytterligare ett vägskäl, men där vägen till höger är blockerad av stora stenar. På ett träd ser du att någon har ristat in följande tecken: '⪥'");
                        System.out.println("Du väljer att fortsätta gå åt vänster.");
                        myHero.setLastLocation(410);
                        return 420;
                    }
                    case "2", "mot vattenfallet" -> {
                        System.out.println("Du går mot vattenfallet.");
                        myHero.setLastLocation(410);
                        return 4101;

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
            case 4101 -> { //vattenfallet

            }
            //--------------------------------------------------------------------
            case 411 -> { //mot staden

            }
            //--------------------------------------------------------------------
            case 420 -> { //Följa med kvinnan
                System.out.println("Kvinnan berättar att hon heter Ragna och bor i en ny långt upp i norr. Hon berättar också att hon ofta brukar vara ute och vandra själv.");
                System.out.println("\nPlötsligt hör du något från en skogsdunge i närheten.");
                System.out.println("Vill du (1) undersöka skogsdungen eller (2) fortsätta gå?");
                answer = sc.nextLine();
                switch (answer.toLowerCase()) {
                    case "1", "undersöka skogsdungen" -> {
                        System.out.println("Du ber Ragna stanna kvar på vägen medan du undersöker skogsdungen.");
                        myHero.setLastLocation(420);
                        return 421;
                    }
                    case "2", "fortsätta gå" -> {
                        System.out.println("Du struntar i ljudet och ni fortsätter gå på vägen.");
                        myHero.setLastLocation(420);
                        return 430;

                    }
                    default -> {
                        System.out.println("Fel input");
                        return 420;
                    }
                }
            }
            //--------------------------------------------------------------------
            case 421 -> { //Möter rövare.
                currentMonster = new Monster("Rövare", 40, 7,5,2,25, 200);
                System.out.println("\nNär du kommer in i skogsdungen kommer det tre rövare springande mot dig. Du hinner inte springa undan!");
                attackOutcome = function.encounter(myHero, currentMonster, function);
                if (attackOutcome == 0) {
                    System.out.println("Du lyckas döda alla rövarna. Bland deras tillhörigheter hittar du en stridsyxa samt ett rep.");
                    System.out.println("Vill du (1) plocka upp deras tillhörigheter eller (2) låta de vara?");
                    answer = sc.nextLine();
                    switch (answer.toLowerCase()) {
                        case "1", "plocka upp deras tillhörigheter" -> {
                            System.out.println("Du plockar upp stridsyxan och repet.");
                            myHero.getInventory().add(Item.battleAxe);
                            myHero.getInventory().add(Item.rope);
                            myHero.getWeaponsList().add(Item.battleAxe);
                            myHero.getMiscList().add(Item.rope);
                        }
                        case "2", "låta de vara" -> {
                            System.out.println("Du låter rövarnas saker vara.");
                        }
                    }
                    return 430;
                } else if (attackOutcome == 1) {
                    return 0;
                } else if (attackOutcome == 2) {
                    return myHero.getLastLocation();
                }
            }
            //--------------------------------------------------------------------
            case 430 -> { //kvinnans hus
                System.out.println("\nNi kommer fram till Ragnas släktingar. Ragna är väldigt glad för att du har hjälp henna på vägen. Hon plockar upp något ur sin väska som hon ger till dig.");
                System.out.println("'Tack " + myHero.getName() + "! Som tack för din hjälp ska du få något som kan hjälpa dig på dina vandringar.'");
                System.out.println("'Det är en magisk flaska. När mörkret är som störst kommer den ge dig ljus.'");
                System.out.println("Du tar emot flaskan och stoppar den i din väska.");
                if(!myHero.getMiscList().contains(Item.magicPotion)) {
                    myHero.getInventory().add(Item.magicPotion);
                    myHero.getMiscList().add(Item.magicPotion);
                }
                System.out.println("\nVill du (1) stanna kvar i huset eller (2) gå tillbaks på vägen?");
                answer = sc.nextLine();
                switch (answer.toLowerCase()) {
                    case "1", "stanna kvar i huset" -> {
                        System.out.println("Du stannar kvar på middag men sedan lämnar du kvinnan ifred och går tillbaks på vägen.");
                        System.out.println("När du är på väg att gå ser du något inristat på en dörrpost. Det ser ut såhär: '⊗'");
                        System.out.println("...");
                        return 400;
                    }
                    case "2", "gå tillbaks på vägen" -> {
                        System.out.println("Du tackar kvinnan och går sedan tillbaks längs vägen.");
                        System.out.println("...");
                        return 400;
                    }
                    default -> {
                        System.out.println("Fel input");
                        return 430;
                    }
                }

            }
        }

        //--------------------------------------------------------------------        }
        return 0;
    }
}
