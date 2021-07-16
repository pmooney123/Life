import java.util.ArrayList;
import java.util.List;

public class Item {
    String name;
    ArrayList<Tag> tags = new ArrayList<Tag>();

    public Item(String name) {
        this.name = name;
    }
    public Item(String name, ArrayList<Tag> tags) {
        this.name = name;
        this.tags = tags;
    }

    public Tag tag(String name) {
        for (Tag tag : tags) {
            if (tag.string.equals(name)) {
                return tag;
            }
        }
        return null;
    }
    public Tag getTag(String string) {
        for (Tag tag : tags) {
            if (tag.string.equals(string)) {
                return tag;
            }
        }
        return null;
    }
    public boolean hasTag(String string) {
        for (Tag tag : tags) {
            if (tag.string.equals(string)) {
                return true;
            }
        }
        return false;
    }
    public int value(String string) {
        for (Tag tag : tags) {
            if (tag.string.equals(string)) {
                return tag.value;
            }
        }
        return 0;
    }
    public void addTag(String string, int value) {
        tags.add(new Tag(string, value));
    }
    public void addTag(String string) {
        tags.add(new Tag(string));
    }
    public Tag getTagContains(String string) {
        for (Tag tag : tags) {
            if (tag.string.contains(string)) {
                return tag;
            }
        }
        return null;
    }
    public boolean hasTagContains(String string) {
        for (Tag tag : tags) {
            if (tag.string.contains(string)) {
                return true;
            }
        }
        return false;
    }


    public String getItemOutput() {
        String string = "[" + name + "]  ";
        if (hasTag("STACKABLE")) {
            string = string.concat("x" + value("STACKABLE") + "/" + value("STACKABLE_MAX"));
        }
        return  string;
    }


}
