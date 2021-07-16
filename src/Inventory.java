import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> items = new ArrayList<Item>();

    public Inventory() {

    }


    public void addItem(Item item) {
        if (item.hasTag("STACKABLE")) {
            if (hasItemWithName(item.name)) {
                Tag tag = getItemWithName(item.name).getTag("STACKABLE");
                tag.value += item.getTag("STACKABLE").value;
                if (tag.value > getItemWithName(item.name).getTag("STACKABLE_MAX").value) {
                    tag.value = getItemWithName(item.name).getTag("STACKABLE_MAX").value;
                    System.out.println("Reached item stack cap");
                }
            } else {
                items.add(item);
                Tag tag = getItemWithName(item.name).getTag("STACKABLE");
                if (tag.value > getItemWithName(item.name).getTag("STACKABLE_MAX").value) {
                    tag.value = getItemWithName(item.name).getTag("STACKABLE_MAX").value;
                    System.out.println("Reached item stack cap");
                }
            }
        } else {
            items.add(item);
        }
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public void clearItems() {items.clear();}
    public void removeItem(int x) {
        items.remove(x);
    }
    public boolean hasItem(Item item) {
        for (Item item2 : items) {
            if (item == item2) {
                return true;
            }
        }
        return false;
    }
    public boolean hasItemWithName(String string) {
        for (Item item2 : items) {
            if (item2.name.equals(string)) {
                return true;
            }
        }
        return false;
    }
    public Item getItemWithName(String string) {
        for (Item item2 : items) {
            if (item2.name.equals(string)) {
                return item2;
            }
        }
        return null;
    }
    public boolean hasItemWithTag(String tagName) {
        for (Item item2 : items) {
            if (item2.hasTag(tagName)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasItemWithTagWithValue(String tagName, int value) {
        for (Item item2 : items) {
            if (item2.hasTag(tagName) && item2.tag(tagName).value >= value) {
                return true;
            }
        }
        return false;
    }

}
