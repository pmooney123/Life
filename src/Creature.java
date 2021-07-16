import com.sun.security.auth.module.LdapLoginModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.StrictMath.random;

public class Creature {
    public int x;
    public int y;
    public boolean isPlayer = false;
    public String name;

    public int pain = 0;
    public int pain_threshold = 900;

    public int blood = 2000;
    public int blood_unc = 500;

    public int oxygen = 100;
    public int oxygen_unc = 20;

    public boolean isPlayer() {

        return isPlayer;
    }

    public boolean canWalk() {
        int number_of_crawling_parts = 0;
        boolean upper_nervous_system = false;
        boolean lower_nervous_system = false;
        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTag("LOCOMOTION:WALK")) {
                number_of_crawling_parts++;
            }
            if (part.hasTagContains("SPINE_UPPER")) {
                if (part.getTagContains("SPINE_UPPER").value > 0) {
                    upper_nervous_system = true;
                }
            }
            if (part.hasTagContains("SPINE_LOWER")) {
                if (part.getTagContains("SPINE_LOWER").value > 0) {
                    lower_nervous_system = true;
                }
            }
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        return number_of_crawling_parts >= 2 && has_brain && upper_nervous_system && lower_nervous_system;
    }
    public boolean canCrawl() {
        int number_of_crawling_parts = 0;
        boolean upper_nervous_system = false;
        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTag("LOCOMOTION:CRAWL")) {
                number_of_crawling_parts++;
            }
            if (part.hasTagContains("SPINE")) {
                if (part.getTagContains("SPINE").value > 0) {
                    upper_nervous_system = true;
                }
            }
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        return number_of_crawling_parts >= 1 && has_brain && upper_nervous_system;
    }

    public int getBleedCount() {
        int bleed = 0;
        for (BodyPart part : body.bodyParts) {
            for (Tag tag : part.tags) {
                if (tag.string.contains("CUT")) {
                    bleed += tag.value;
                }
                if (tag.string.contains("HEMORRHAGE")) {
                    bleed += tag.value;
                }
            }
        }
        return bleed;
    }
    public int getPainFromWoundCount() {
        int pain = 0;
        for (BodyPart part : body.bodyParts) {
            for (Tag tag : part.tags) {
                if (tag.string.contains("BRUISED")) {
                    pain += 5;
                }
            }
            for (Tag tag : part.tags) {
                if (tag.string.contains("BROKEN")) {
                    pain += 25;
                }
            }
            for (Tag tag : part.tags) {
                if (tag.string.contains("ORGAN") && tag.value == 0) {
                    pain += 90;
                }
            }
        }
        return pain;
    }
    public void healBleedDamagePassive() {
            for (BodyPart part : body.bodyParts) {
                for (Tag tag : part.tags) {
                    if (tag.string.contains("DEEP")) {
                        int reduce = random.nextInt(3);
                        if (reduce > tag.value) {
                            reduce = tag.value;
                        }
                        tag.value -= reduce;
                    }
                    if (tag.string.contains("SHALLOW")) {
                        int reduce = random.nextInt(3) + 1;
                        if (reduce > tag.value) {
                            reduce = tag.value;
                        }
                        tag.value -= reduce;

                    }
                    if (tag.string.contains("HEMORRHAGE")) {
                        int reduce = random.nextInt(3);
                        if (reduce > tag.value) {
                            reduce = tag.value;
                        }
                        tag.value -= reduce;
                    }
                }
            }
    }


    public boolean checkBloodPassout() {
        if (blood < blood_unc) {
            return true;
        }
        return false;
    }
    public boolean checkPainPassout() {
        if (pain > pain_threshold ) {
            return true;
        }
        return false;
    }
    public boolean checkBloodAlive() {
        if (blood < blood_unc/2.0) {
            return false;
        }
        return true;
    }

    public boolean checkConscience() {
        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        return !checkBloodPassout() && !checkPainPassout() && oxygen > oxygen_unc && has_brain;
    }
    public int numEyes() {
        int number_of_eyes = 0;
        boolean upper_nervous_system = false;
        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            for (Tag tag : part.tags) {
                if (tag.string.contains("EYE")) {
                    if (tag.value > 0) {
                        number_of_eyes++;
                    }
                }
                if (tag.string.contains("BRAIN")) {
                    if (tag.value > 0) {
                        has_brain = true;
                    }
                }
                if (tag.string.contains("SPINE_UPPER")) {
                    if (tag.value > 0) {
                        upper_nervous_system = true;
                    }
                }
            }

        }

        if (has_brain && upper_nervous_system) {
            return number_of_eyes;
        } else {
            return 0;
        }
    }
    public boolean canTalk() {
        boolean has_mouth = false;

        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTag("ORGAN:MOUTH")) {
                if (part.getTagContains("ORGAN:MOUTH").value > 0) {
                    has_mouth = true;
                }
            }
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        return has_mouth && has_brain;
    }
    public boolean canEatandDrink() {
        boolean has_mouth = false;
        boolean has_brain = false;
        boolean has_gut = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTag("ORGAN:MOUTH")) {
                if (part.getTagContains("ORGAN:MOUTH").value > 0) {
                    has_mouth = true;
                }
            }
            if (part.hasTag("ORGAN:GUT")) {
                if (part.getTagContains("GUT").value > 0) {
                    has_gut = true;
                }
            }
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        return has_mouth && has_brain && has_gut;
    }
    public int numLungs() {
        boolean has_brain = false;
        for (BodyPart part : body.bodyParts) {
            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        boolean has_heart = false;
        boolean has_upperNS = false;
        int lungs = 0;
        for (BodyPart part : body.bodyParts) {
            for (Tag tag : part.tags) {
                if (tag.string.contains("HEART")) {
                    if (tag.value > 0) {
                        has_heart = true;
                    }
                }
                if (tag.string.contains("LUNG")) {
                    if (tag.value > 0) {
                        lungs++;
                    }
                }
                if (tag.string.contains("SPINE_UPPER")) {
                    if (tag.value > 0) {
                        has_upperNS = true;
                    }
                }
            }

        }
       // System.out.println(lungs);
        if (has_heart && has_upperNS && has_brain) {
            return lungs;
        } else {
            return 0;
        }

    }

    public boolean isAlive() {

        boolean upper_nervous_system = false;
        boolean has_brain = false;

        for (BodyPart part : body.bodyParts) {

            if (part.hasTagContains("BRAIN")) {
                if (part.getTagContains("BRAIN").value > 0) {
                    has_brain = true;
                }
            }
        }
        if (!has_brain) {
            return  false;
        }
        if (blood <= 0) {
            return false;
        }
        if (oxygen == 0) {
            return false;
        }
        return true;

    }


    public Inventory inv = new Inventory();
    public Random random = new Random();
    public Body body;

    public int strength = 10; //armor wearing, bludgeoning
    public int spearmanship = 10; //nimbleness, dodging
    public int swordsmanship = 10; //swords, rapiers, etc
    public int marksmanship = 10; //guns

    public Item weapon() {
        for (BodyPart part : body.bodyParts) {
            if (part.hasTag("HOLD:TOOL")) {
                if (part.equipped != null) {
                    if (part.equipped.hasTag("HOLD:TOOL")) {
                        return part.equipped;
                    }
                }
            }
        }
        return null;
    }

    public void equip(Item item) {
        if (item.hasTag("HOLD:TOOL")) {
            for (BodyPart part : body.bodyParts) {
                if (part.hasTag("HOLD:TOOL")) {
                    if (part.equipped == null ) {
                        part.equipped = item;
                        if (part.equipped.hasTag("TWO_HANDED")) {

                        } else {
                            return;
                        }
                    }
                }
            }
        }
        if (item.hasTagContains("WEAR:")) {
            for (BodyPart part : body.bodyParts) {
                if (part.hasTag(item.getTagContains("WEAR:").string)) {
                    if (part.equipped == null ) {
                        part.equipped = item;
                        break;
                    }
                }
            }
        }
        if (!inv.hasItem(item)) {
            inv.addItem(item);
        }
    }

    public Creature(String name) {
        this.name = name;
        this.body = new Body();
    }

    public ArrayList<Note> getInventoryPrint(){
        ArrayList<Note> output = new ArrayList<>();
        ArrayList<Item> inv2 = new ArrayList<>(inv.items);
        for (Item item : inv2) {
            if (isEquipped(item)) {
                if (item.hasTag("STACKABLE") && item.value("STACKABLE") > 0) {
                    output.add(new Note(item.name + "(x" + item.value("STACKABLE")+")", Color.white));
                } else {
                    output.add(new Note(item.name, Color.white));
                }
                output.add(new Note(" [EQUIPPED]", Color.cyan));
            } else {
                if (item.hasTag("STACKABLE") && item.value("STACKABLE") > 0) {
                    output.add(new Note(item.name + "(x" + item.value("STACKABLE")+")", Color.white));
                } else {
                    output.add(new Note(item.name, Color.white));
                }
            }
            for (Tag tag : item.tags) {
                if (tag.value > 0) {
                    output.add(new Note(" [" + tag.string + ":"+tag.value + "]", Color.white));
                } else {
                    output.add(new Note(" [" + tag.string + "]", Color.white));

                }
            }
            output.add(new Note("&",Color.white));
        }


        return output;
    }
    public boolean isEquipped(Item item) {
        for (BodyPart bodyPart : body.bodyParts) {
            if (bodyPart.equipped == item) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Note> listParts() {
        ArrayList<Note> output = new ArrayList<>();
        if (isAlive()) {
            output.add(new Note(" [ALIVE]", Color.green));
        } else {
            output.add(new Note(" [ALIVE]", Color.red));
        }
        if (checkConscience()) {
            output.add(new Note(" [AWAKE]", Color.green));
        } else {
            output.add(new Note(" [AWAKE]", Color.red));
        }
        if (canCrawl()) {
            output.add(new Note(" [CAN_CRAWL]", Color.green));
        } else {
            output.add(new Note(" [CAN_CRAWL]", Color.red));
        }
        if (canWalk()) {
            output.add(new Note(" [CAN_WALK]", Color.green));
        } else {
            output.add(new Note(" [CAN_WALK]", Color.red));
        }
        // output.add(new Note("&", Color.green));

        if (numEyes() > 0) {
            if (numEyes() == 2) {
                output.add(new Note(" [CAN_SEE]", Color.green));
            } else {
                output.add(new Note(" [CAN_SEE]", Color.yellow));
            }
        } else {
            output.add(new Note(" [CAN_SEE]", Color.red));
        }
        if (canTalk()) {
            output.add(new Note(" [CAN_TALK]", Color.green));
        } else {
            output.add(new Note(" [CAN_TALK]", Color.red));
        }
        if (numLungs() > 0) {
            if (numLungs() == 2) {
                output.add(new Note(" [CAN_BREATH]", Color.green));
            } else {
                output.add(new Note(" [CAN_BREATH]", Color.yellow));
            }
        } else {
            output.add(new Note(" [CAN_BREATH]", Color.red));
        }
        if (canEatandDrink()) {
            output.add(new Note(" [CAN_EAT_AND_DRINK]", Color.green));
        } else {
            output.add(new Note(" [CAN_EAT_AND_DRINK]", Color.red));
        }
        output.add(new Note("&", Color.white));
        for (BodyPart bodyPart : body.bodyParts) {
            Color color;

            output.add(new Note((" [" + bodyPart.name + "]:"), Color.cyan));

            for (Tag tag : bodyPart.tags) {
                color = Color.white;
                if (!hasUnwantedTagString(tag)) {
                    if (tag.string.contains("ORGAN")) {
                        if (tag.value > 0) {
                            color = Color.green;
                        } else {
                            color = Color.red;
                        }
                    }
                    if (tag.string.contains("APPENDAGE")) {
                        if (tag.value == 5) {
                            color = Color.green;
                        } else {
                            color = Color.yellow;
                        }
                    }
                    if (tag.string.contains("CUT")) {
                        if (tag.value > 25) {
                            color = Color.red;
                        } else {
                            color = Color.yellow;
                        }
                    }
                    if (tag.string.contains("BRUISED")) {
                            color = Color.yellow;
                    }
                    if (tag.string.contains("BROKEN")) {
                        color = Color.red;
                    }
                    if (tag.string.contains("HEMORRHAGE")) {
                        color = Color.red;
                    }

                    if (tag.value != 0 && tag.value != 1) {
                        output.add(new Note((" [" + tag.string + ":" + tag.value + "]"), color));
                    } else {
                        output.add(new Note((" [" + tag.string + "]"), color));
                    }

                }

            }


            output.add(new Note("&", Color.white)); {
        }


        }

        Color color = Color.red.darker();
        if (blood < blood_unc) {
            color = Color.yellow;
        }
        output.add(new Note(" [BLOOD:" + blood + "]", color));

        if (pain > pain_threshold) {
            color = Color.yellow;
        } else {
            color = Color.pink;
        }
        output.add(new Note(" [PAIN:" + pain + "]", color));

        if (oxygen > 60) {
            color = Color.cyan;
        } else if (oxygen > 25) {
            color = Color.yellow;
        } else {
            color = Color.red;
        }
        output.add(new Note(" [OXYGEN:" + oxygen + "]", color));

        return output;
    }
    public ArrayList<Note> listEquipped() {
        ArrayList<Note> output = new ArrayList<>();

        for (BodyPart bodyPart : body.bodyParts) {

            output.add(new Note((" [" + bodyPart.name + "]"), Color.cyan));
            if (bodyPart.equipped != null) {
                output.add(new Note(" [" + bodyPart.equipped.name + "]", Color.white));

            }
            output.add(new Note("&", Color.white));

        }
        return output;
    }

    public boolean hasUnwantedTagString(Tag tag) {
        String[] unwanted = {"CAN", "TARGET", "VITAL", "COVER", "SKELETON", "BONE","SKIN", "INSEVERABLE","WEAR", "HOLD","LOCO"};
        for (String string : unwanted) {
            if (tag.string.contains(string)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> resolveAttack(Creature attacker, Attack attack) {
        ArrayList<String> events = new ArrayList<>();
        int momentum = attack.momentum;
        BodyPart part = body.bodyParts.get(random.nextInt(body.bodyParts.size()));
        events.add(part.name + " targeted");
        boolean damaged = false;
        //SKILL VS DODGE
        events.add("Not dodged");

        //SHIELD/PARRY
        events.add("Not blocked");

        int armor = part.getArmor();
        int skin = part.value("SKIN");
        int bone = part.value("BONE");
        if (part.hasTag("BROKEN")) {
            bone = (int) (bone / 2.0);
        }
        if (part.hasTag("BRUISED")) {
            skin = (int) (skin / 2.0);
        }

        int skin_armor = armor + skin;
        int total = armor + skin + bone;
        int appendages = 0;
        for (Tag tag : part.tags) {
            if (tag.string.contains("APPENDAGE")) {
                appendages = tag.value;
            }
        }
        int organs = 0;
        for (Tag tag : part.tags) {
            if (tag.string.contains("ORGAN")) {
                organs++;
            }
        }

        double chance_bruise = 10.0 * attack.bludgeoning / (skin_armor);
        double chance_break = 10.0 * attack.bludgeoning / total;
        double bleed_pierce = (attack.piercing - skin_armor) / (skin_armor + 0.0);
        double bleed_edge = (2 * attack.slashing - armor ) / (skin_armor + 0.0);
        double dismember_appendage = (10.0 * (attack.slashing - total) * appendages / total);
        if (dismember_appendage > 50) {
            dismember_appendage = 50;
        }
        double dismember_limb = (5.0 * (attack.slashing - total) / total);
        double chance_crush_organ = (5.0 * attack.bludgeoning / total);
        double chance_pierce_organ = (10.0 * organs * attack.piercing - total) / total;


        events.add("CBru " + (int) chance_bruise);
        events.add("CBre " + (int) chance_break);
        events.add("BleeP " + (int) bleed_pierce);
        events.add("BleeE " + (int) bleed_edge);
        events.add("DMa " + (int) dismember_appendage);
        events.add("DMl " + (int) dismember_limb);
        events.add("CrOr " + (int) chance_crush_organ);
        events.add("PeOr " + (int) chance_pierce_organ);



        if (random.nextInt(101) < chance_bruise) {
            damaged = true;
            part.addTag("BRUISED");
            events.add(part.name + "'s muscles were bruised.");
        }

        if (random.nextInt(101) < chance_break) {
            damaged = true;
            part.addTag("BROKEN");
            part.addTag("HEMORRHAGE", random.nextInt(15)+5);
            events.add(part.name + "'s bones were shattered.");
        }

        if (random.nextInt(101) < dismember_limb && !part.hasTag("INSEVERABLE")) {
            damaged = true;
            body.removePart(part);
            events.add(part.name + " was severed completely");
            return events;
        }
        if (random.nextInt(101) < dismember_limb && part.hasTag("INSEVERABLE")) {
            damaged = true;
            events.add(part.name + " was opened up completely");
            part.addTag("CUT_DEEP", 25);
            if (organs > 0) {
                ArrayList<String> organ_names = new ArrayList<>();
                for (Tag tag : part.tags) {
                    if (tag.string.contains("ORGAN")) {
                        organ_names.add(tag.string);
                    }
                }
                String killed_organ = organ_names.get(random.nextInt(organ_names.size()));
                for (Tag tag : part.tags) {
                    if (tag.string.contains(killed_organ)) {
                        tag.value = 0;
                        String organ_name = killed_organ.replace("ORGAN:", "");
                        events.add(organ_name + " was cut and destroyed.");
                    }
                }
            }
            return events;
        }
        if (random.nextInt(101) < dismember_appendage) {
            damaged = true;
            for (Tag tag : part.tags) {
                if (tag.string.contains("APPENDAGE")) {
                    tag.value -= 1;
                    if (tag.value < 0) {
                        tag.value = 0;
                    }
                    String appendage_name = tag.string.replace("APPENDAGE:", "");
                    events.add("A " + appendage_name + " was severed completely");
                }
            }
        }


        if (bleed_edge > 2) {
            damaged = true;
            bleed_edge = bleed_edge/2 + random.nextInt((int)(bleed_edge/2));

            part.addTag("CUT_SHALLOW", (int) bleed_edge);
            events.add(part.name + " was cut open.");
        }
        if (bleed_pierce > 2) {
            damaged = true;
            bleed_pierce = bleed_pierce/2 + random.nextInt((int)(bleed_pierce/2));

            part.addTag("CUT_DEEP", (int) bleed_pierce);
            events.add(part.name + " was pierced deeply.");
        }

        if (random.nextInt(101) < chance_crush_organ && organs > 0) {
            damaged = true;
            ArrayList<String> organ_names = new ArrayList<>();
            for (Tag tag : part.tags) {
                if (tag.string.contains("ORGAN")) {
                    organ_names.add(tag.string);
                }
            }
            String killed_organ = organ_names.get(random.nextInt(organ_names.size()));
            for (Tag tag : part.tags) {
                if (tag.string.contains(killed_organ)) {
                    tag.value = 0;
                    String organ_name = killed_organ.replace("ORGAN:", "");
                    events.add(organ_name + " was crushed and destroyed.");
                }
            }
            part.addTag("HEMORRHAGE", random.nextInt(15)+10);
        }

        if (random.nextInt(101) < chance_pierce_organ && organs > 0) {
            damaged = true;
            ArrayList<String> organ_names = new ArrayList<>();
            for (Tag tag : part.tags) {
                if (tag.string.contains("ORGAN")) {
                    organ_names.add(tag.string);
                }
            }
            String killed_organ = organ_names.get(random.nextInt(organ_names.size()));
            for (Tag tag : part.tags) {
                if (tag.string.contains(killed_organ)) {
                    tag.value = 0;
                    String organ_name = killed_organ.replace("ORGAN:", "");
                    events.add(organ_name + " was pierced and destroyed.");
                }
            }
            part.addTag("HEMORRHAGE", random.nextInt(10)+10);

        }
        if (!damaged) {
            events.add("No noticeable damage was dealt");
        }


        blood -= getBleedCount();
        pain += getPainFromWoundCount();
        if (blood < 0) {
            blood = 0;
        }
        if (pain > pain_threshold) {
            pain = pain_threshold;
        }

        if (numLungs() <= 0) {
            oxygen -= 10;
        } else {
            oxygen += numLungs() * 5;
        }
        if (oxygen > numLungs() * 50) {
            oxygen = numLungs() * 50;
        }
        if (oxygen < 0) {
            oxygen = 0;
        }
        healBleedDamagePassive();
        return events;
    }

}
