import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class functions {
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();

    //Methods
    public int attack(Character myHero, Monster monster, functions function) {
        int heroAttack;
        int monsterAttack;
        int monsterHealth = monster.getMaxHealth();
        int runningAway = 0;
        String answer;
        Weapon chosenWeapon = function.chooseWeapon(myHero);

        while (myHero.getHealth() > 0 && monsterHealth > 0 && runningAway == 0) {  //Håller på tills antingen hjältens hp=0, monster hp=0 eller man väljer att springa iväg.

            heroAttack = (rand.nextInt(0,   chosenWeapon.getAttackValue()) + myHero.getLevel()*2); //Slumpar fram ett attackvärde för den här rundan. Ju högre level man är och ju bättre vapen man har, desto mer skada gör man
            monsterHealth = monsterHealth - heroAttack;  //Minskar monstrets hp med attckvärdet.
            System.out.println("Du slog monstret för " + heroAttack + ". " + monster.getName() + " har " + monsterHealth + " liv kvar.");

            if (monsterHealth > 0) { //Om monstret fortfarande har liv kvar attackerar den tillbaks.
                monsterAttack = rand.nextInt(0, monster.getAttackValue());
                myHero.setHealth(myHero.getHealth()- monsterAttack);
                System.out.println(monster.getName() + " attackerade dig för " + monsterAttack + ". Du har " + myHero.getHealth() + " liv kvar.\n");
            }
            if (monsterHealth > 0 && myHero.getHealth() > 0) { //Om någon har liv kvar så får man frågan om att fortsätta
                System.out.println(((myHero.getMiscList().contains(Item.healthPotion)) ? "Vill du (1) attackera igen, (2) springa din väg eller (3) dricka en helningsdryck och sedan attackera igen?": "Vill du (1) attackera igen eller (2) springa din väg?"));
                answer = sc.nextLine();
                switch (answer.toLowerCase()) {
                    case "1", "attackera igen" -> {
                        break;
                    }
                    case "2", "springa din väg" -> { //OBS! denna är egen för om man springer ifrån under en strid.
                        switch (runAway(myHero, monster)) {
                            case 1 -> {  //Om monstret är lika snabbt som hjälten
                                monsterAttack = rand.nextInt(0, monster.getAttackValue());
                                myHero.setHealth(myHero.getHealth()- monsterAttack);
                                System.out.println(monster.getName() + " attackerade dig för " + monsterAttack + ". Du har " + myHero.getHealth() + " liv kvar.\n");
                                System.out.println("Du sprang ifrån " + monster.getName());
                                runningAway = runAway(myHero, monster);
                                break;
                            }
                            case 2 -> { //Om hjälten är snabbare än monstret
                                System.out.println("Du sprang ifrån " + monster.getName());
                                runningAway = runAway(myHero, monster);
                                break;
                            }
                            default -> { //Om monstret är snabbare än hjälten
                                System.out.println(monster.getName() + " var för snabb! Du måste slåss.");
                                break;
                            }
                        }
                    }
                    case "3", "dricka en helningsdryck" ->  {
                        function.potionDrinking(myHero);
                        break;
                    }
                }
            }
        }

        if (monsterHealth <= 0) { //Om monstret har dött
            return 0;
        } else if (myHero.getHealth() <= 0) { //Om hjälten har dött
            return 1;
        } else {
            return 2; //Om man springer
        }
    }

    public int encounter(Character myHero, Monster monster, functions function) {
        int encounterOutcome = function.attack(myHero, monster, function);  //Kollar vad som händer i varje attack (vad den returnerar)
        if (encounterOutcome == 0) {
            System.out.println("Du dödade " + monster.getName() + ".\n");
            myHero.setXp(myHero.getXp()+ monster.getXpGiven());
            function.checkIfLevelUp(myHero);
            return 0;
        } else if (encounterOutcome == 1) {
            System.out.println(monster.getName() + " dödade dig.\n");
            return 1;
        } else {
            return 2;
        }
    }

    public Monster randomMonsterByDifficulty(ArrayList<Monster> monsterArrayList, Character myHero) {  //slumpar fram ett monster beroende på vilken level man är.
        ArrayList<Monster> monsterListByLevel = new ArrayList<>();
        int randomNr;
        for (Monster monster : monsterArrayList) { //Hämtar alla monster som är på samma nivå som en själv och lägre.
            if (monster.getDifficulty() <= myHero.getLevel())
                monsterListByLevel.add(monster);
        }
        randomNr = (int) (Math.random() * monsterListByLevel.size()); //Slumpar fram vilket monster det blir.
        return monsterArrayList.get(randomNr);
    }

    public Weapon chooseWeapon(Character myHero) {
        int chosenWeapon;
        System.out.println("Välj vilket vapen du vill använda:");
        for (Weapon weapon : myHero.getWeaponsList()) { //Listar alla vapen man har med sig.
                System.out.println(myHero.getWeaponsList().indexOf(weapon) + " " + weapon.getName() + " - attack " + weapon.getAttackValue());
        }
        chosenWeapon = sc.nextInt();
        sc.nextLine();
        try {
            return myHero.getWeaponsList().get(chosenWeapon);
        } catch (InputMismatchException e) {
            System.out.println("Fel");
        }
        return myHero.getWeaponsList().get(0);
    }

//    public ArrayList<String> checkForInventory(Character myHero) {
//        ArrayList<String> check = new ArrayList<>();
//        for (Item item : myHero.getInventory()) {
//            check.add(item.getName());
//        }
//        return check;
//    }

    public boolean randomSpawnMonster(int spawnRate) {

        int spawn = new Random().nextInt(spawnRate);
        if (spawn > 3) {
            return true;
        }
        return false;
    }

    public int runAway(Character myHero, Monster monster) {
        if (myHero.getSpeed() > monster.getSpeed()) { //om man är snabbare än monstret
            return 2;
        } else if (myHero.getSpeed() == monster.getSpeed()) { //om man är lika snabb
            return 1;
        } else { //om monstret är snabbare
            return 0;
        }
    }

    public void potionDrinking(Character myHero){
        Item currentItem = null;
        Misc currentItemMisc = null;
        if(myHero.getInventory().contains(Item.healthPotion)) {
            System.out.println("Du dricker en flaska med helningsdryck.");

            if (myHero.getHealth() <= (myHero.getMaxHealth() - 21)) {
                myHero.setHealth(myHero.getHealth() + 20);
            } else {
                myHero.setHealth(myHero.getMaxHealth());
            }
            System.out.println("Du har nu " + myHero.getHealth() + " liv.");
        }
        for(Item item: myHero.getInventory()) { //Tar bort helningsdrycken från Inventory
            if(item.getName().equalsIgnoreCase("Helningsdryck")) {
                currentItem = item;
                break;
            }
        }
        if(currentItem != null) {
            myHero.getInventory().remove(currentItem);
        }
        for(Misc item: myHero.getMiscList()) { //Tar bort helningsdrycken från Misc
            if(item.getName().equalsIgnoreCase("Helningsdryck")) {
                currentItemMisc = item;
                break;
            }
        }
        if(currentItemMisc != null) {
            myHero.getMiscList().remove(currentItemMisc);
        }
    }

    public void showInventory(Character myHero) {
        for(Item item : myHero.getInventory()) {
            System.out.println(item.getName());
        }


    }

    public void checkIfLevelUp(Character myHero) {
        if(myHero.getXp() > (myHero.getLevel() * 120)) {
            myHero.setLevel((myHero.getLevel() + 1));
            myHero.setSpeed(myHero.getSpeed() + 3);
            System.out.println("Du känner dig starkare! Du har gått upp i nivå.");
            System.out.println("Du är nu på nivå " + myHero.getLevel());
        }

    }

}
