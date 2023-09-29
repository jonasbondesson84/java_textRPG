import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        String heroName;
        Character myHero;
        int currentLocation=100;
        Location world = new Location(100);
        functions function = new functions();
        ArrayList<Monster> monsterArrayList = new ArrayList<>();

        monsterArrayList.add(new Monster("Giftig spindel", 20, 2, 1, 1, 22,2));
        monsterArrayList.add(new Monster("Varg", 25,5,2, 1, 18, 10));
        monsterArrayList.add(new Monster("Troll", 50, 9,5, 1, 20, 1));
        monsterArrayList.add(new Monster("Rövare", 40, 7,5,2,25, 5));
        monsterArrayList.add(new Monster("Vampyr", 40, 15, 10,2, 250, 10));
        monsterArrayList.add(new Monster("Drake", 200, 30, 20,5, 1000,20));





        System.out.println("Skriv in ditt hjältenamn:");
        heroName = sc.nextLine();
        myHero =new Character(heroName);
//       myHero.getInventory().add(new Misc("Helningsdryck", "potion"));
//       myHero.getMiscList().add(new Misc("Helningsdryck", "potion"));
//        function.showInventory(myHero);
//        myHero.setHealth(50);
//        System.out.println(myHero.getHealth());
//        function.potionDrinking(myHero);
//        System.out.println(myHero.getHealth());
//        function.showInventory(myHero);

        //System.out.println(myHero.getInventory().get(0).getName());



        do {
            currentLocation=world.location(currentLocation, myHero, function, monsterArrayList);

        } while(currentLocation!=0);
        System.out.println("Game over!");


    }
}