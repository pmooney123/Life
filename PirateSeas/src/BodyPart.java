import java.util.ArrayList;

public class BodyPart {
    ArrayList<Tag> tags = new ArrayList<>();
    String name;
    Item equipped;

    public BodyPart(String name) {
        this.name = name;
    }
    public BodyPart(BodyPart part) {
        this.name = part.name;
        for (Tag tag : part.tags) {
            Tag tag2 = new Tag(tag.string, tag.value);
            tags.add(tag2);
        }
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
        for (Tag tag : tags) {
            if (tag.string.equals(string)) {
                tag.value += value;
                return;
            }
        }
        tags.add(new Tag(string, value));
    }
    public void addTag(String string) {
        for (Tag tag : tags) {
            if (tag.string.equals(string)) {
                return;
            }
        }
        tags.add(new Tag(string, 0));
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
    public int getArmor() {

        if (equipped != null ) {
            return equipped.value("ARMOR");
        }
        return 0;
    }


}
