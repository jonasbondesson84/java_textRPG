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
        int heroHealth = myHero.getHealth();
        int runningAway = 0;
        String answer;
        //   System.out.println(heroHealth);
        //   System.out.println(monsterHealth);
        Item chosenWeapon = function.chooseWeapon(myHero);

        while (heroHealth > 0 && monsterHealth > 0 && runningAway == 0) {

            heroAttack = rand.nextInt(0, chosenWeapon.getAttackValue());//getDamage(chosenWeapon.getAttackValue(), monster.getDefenceValue(),myHero.getLevel());
            monsterHealth = monsterHealth - heroAttack;
            System.out.println("Du slog monstret för " + heroAttack + ". " + monster.getName() + " har " + monsterHealth + " liv kvar.");
            if (monsterHealth > 0) {
                monsterAttack = rand.nextInt(0, monster.getAttackValue());//getDamage(monster.getAttackValue(), function.getDefence(myHero, chosenWeapon),monster.getDifficulty());
                heroHealth -= monsterAttack;
                System.out.println(monster.getName() + " attackerade dig för " + monsterAttack + ". Du har " + heroHealth + " liv kvar.\n");
            }
            if (monsterHealth > 0 && heroHealth > 0) {
                System.out.println("Vill du (1) attackera igen eller (2) springa din väg?");

                answer = sc.nextLine();
                switch (answer.toLowerCase()) {
                    case "1", "attackera igen" -> {
                        break;
                    }
                    case "2", "springa din väg" -> {
                        switch (runAway(myHero, monster)) {
                            case 1 -> {
                                monsterAttack = rand.nextInt(0, monster.getAttackValue());//getDamage(monster.getAttackValue(), function.getDefence(myHero, chosenWeapon),monster.getDifficulty());
                                heroHealth -= monsterAttack;
                                System.out.println(monster.getName() + " attackerade dig för " + monsterAttack + ". Du har " + heroHealth + " liv kvar.\n");
                                System.out.println("Du sprang ifrån " + monster.getName());
                                runningAway = runAway(myHero, monster);
                                break;
                            }
                            case 2 -> {
                                System.out.println("Du sprang ifrån " + monster.getName());
                                runningAway = runAway(myHero, monster);
                                break;
                            }
                            default -> {
                                System.out.println(monster.getName() + " var för snabb! Du måste slåss.");
                                break;
                            }
                        }
                    }
                }
            }
        }
        myHero.setHealth(heroHealth);
        if (monsterHealth <= 0) {
            return 0;
        } else if (myHero.getHealth() <= 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public int encounter(Character myHero, Monster monster, functions function) {
        int encounterOutcome = function.attack(myHero, monster, function);
        if (encounterOutcome == 0) {
            System.out.println("Du dödade " + monster.getName() + ".\n");
            return 0;
        } else if (encounterOutcome == 1) {
            System.out.println(monster.getName() + " dödade dig.\n");
            return 1;
        } else {
            return 2;
        }
    }

    public Monster randomMonsterByDifficulty(ArrayList<Monster> monsterArrayList, Character myHero) {
        ArrayList<Monster> monsterListByLevel = new ArrayList<>();
        int randomNr;

        for (Monster monster : monsterArrayList) {
            if (monster.getDifficulty() <= myHero.getLevel())
                monsterListByLevel.add(monster);
        }
        randomNr = (int) (Math.random() * monsterListByLevel.size());
        return monsterArrayList.get(randomNr);
    }

    public Item chooseWeapon(Character myHero) {
        int chosenWeapon;
        System.out.println("Välj vilket vapen du vill använda:");
        for (Item item : myHero.getInventory()) {
            System.out.println(myHero.getInventory().indexOf(item) + " " + item.getName() + " - attack " + item.getAttackValue());
        }
        chosenWeapon = sc.nextInt();
        sc.nextLine();
        try {
            return myHero.getInventory().get(chosenWeapon);
        } catch (InputMismatchException e) {
            System.out.println("Fel");
        }
        return myHero.getInventory().get(0);
    }

    ArrayList<String> checkForInventory(Character myHero) {
        ArrayList<String> check = new ArrayList<>();
        for (Item item : myHero.getInventory()) {
            check.add(item.getName());
        }
        ;
        return check;
    }

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

        System.out.println("Du dricker en flaska med helningsdryck.");
        if(myHero.getHealth() < myHero.getMaxHealth()-20) {
            myHero.setHealth(myHero.getHealth() + 20);
        } else {
            myHero.setHealth(myHero.getMaxHealth());
        }

    }

//    public int getDamage(int attackValue, int defenceValue, int lvl) {
//        int dmg;
//        Random theRandom = new Random();
//        double randomDmg = 0.20 + (1.10 - 0.20) * theRandom.nextDouble();
//        dmg = (int) Math.ceil(((randomDmg * (attackValue - (0.5 * defenceValue)))) + 0.2 * lvl);
//        //System.out.println(dmg);
//        return dmg;
//    }
//
//    public int getDefence(Character myHero, Item chosenWeapon) {
//        return (myHero.getLevel() + chosenWeapon.getDefenceValue());
//    }
}
