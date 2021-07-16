import java.util.ArrayList;

public class Area {
    ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Area> parent_areas = new ArrayList<>();

    public Area() {

    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }
    public void addItem(Item item) {
        items.add(item);
    }
}
