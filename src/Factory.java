import java.lang.reflect.Array;
import java.util.ArrayList;

public class Factory {


    public Factory(){

    }
    //creatures
    public Creature newPlayer() {
        Creature player = new Creature("Patrick");
        player.isPlayer = true;
        player.inv.addItem(arrowStack(20));
        return player;
    }

    //locations
    public Area locationDefault() {
        Area area = new Area();

        return area;
    }

    //items
    public Item newHammer() {
        Item item = new Item("Iron_Hammer");
        item.addTag("HOLDABLE_MAIN");
        item.addTag("TWO_HANDED");
        item.addTag("BLUDGEONING", 25);
        item.addTag("SLASHING", 0);
        item.addTag("PIERCING",0);
        item.addTag("HOLD:TOOL");


        return item;
    }
    public Item newSpear() {
        Item item = new Item("Iron_Spear");
        item.addTag("HOLDABLE_MAIN");
        item.addTag("TWO_HANDED");
        item.addTag("BLUDGEONING", 0);
        item.addTag("SLASHING", 0);
        item.addTag("PIERCING",15);
        item.addTag("HOLD:TOOL");


        return item;
    }
    public Item newBoot() {
        Item item = new Item("Iron_Boot");
        item.addTag("WEAR:BOOT");
        item.addTag("ARMOR", 100);

        return item;
    }
    public Item newSword() {
        Item item = new Item("Iron_Long_Sword");
        item.addTag("HOLDABLE_MAIN");
        item.addTag("TWO_HANDED");
        item.addTag("BLUDGEONING", 0);
        item.addTag("SLASHING", 15);
        item.addTag("PIERCING",0);
        item.addTag("HOLD:TOOL");


        return item;
    }
    public Item newArrow() {
        Item item = new Item("Arrow");
        item.addTag("VALUABLE", 1);
        item.addTag("STACKABLE", 1);
        item.addTag("STACKABLE_MAX", 40);
        return item;
    }
    public Item arrowStack(int x) {
        Item item = new Item("Arrow");
        item.addTag("VALUABLE", x);
        item.addTag("STACKABLE", x);
        item.addTag("STACKABLE_MAX", 40);
        return item;
    }
}




