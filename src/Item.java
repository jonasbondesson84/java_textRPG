public interface Item {
    Weapon sword = new Weapon("Svärd", 10, "weapon");
    Weapon battleAxe = new Weapon("Stridsyxa", 15, "weapon");
    Weapon dragonLance = new Weapon("Draklans", 40, "weapon");
    Misc healthPotion = new Misc("Helningsdryck", "potion");
    Misc magicPotion = new Misc("Lykta", "potion");
    Misc rope = new Misc("Rep", "misc");
    Misc wingedBoots = new Misc("bevingade stövlar", "misc");


    public String getName();

}

//import java.util.ArrayList;
//
//public class Item {
//
//    private String name;
//    private int attackValue;
//    private int defenceValue;
//    private String type;
//
//    //Constructor
//    public Item(String name, int attackValue, String type) {
//        this.name = name;
//        this.attackValue = attackValue;
//       // this.defenceValue = defenceValue;
//        this.type = type;
//    }
//
//    //getters and setters
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getDefenceValue() {
//        return defenceValue;
//    }
//
//    public void setDefenceValue(int defenceValue) {
//        this.defenceValue = defenceValue;
//    }
//
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public int getAttackValue() {
//        return attackValue;
//    }
//    public void setAttackValue(int attackValue) {
//        this.attackValue = attackValue;
//    }
//
//
//}
