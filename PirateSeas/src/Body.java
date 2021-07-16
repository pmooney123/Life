import java.util.ArrayList;
import java.util.Random;

public class Body {
    ArrayList<BodyPart> bodyParts = new ArrayList<BodyPart>(); //what the creature DOES look like
    ArrayList<BodyPart> bodyScheme = new ArrayList<>(); //what the creature SHOULD look like

    public Body() {
        addHead();
        addUpperBody();
        addLowerBody();
        addHand();
        addHandOff();

        addFoot();
        addFoot();
    }


    public void buildBody(ArrayList<String> desiredParts) {

    }

    public void addPart(BodyPart part) {
        bodyScheme.add(part);

        BodyPart part2 = new BodyPart(part);
        bodyParts.add(part2);
    }

    public BodyPart newUpperBody() {
        BodyPart body = new BodyPart("UPPER_BODY");
        body.addTag("CAN_WEAR_ARMOR");
        body.addTag("INSEVERABLE");
        body.addTag("ORGAN:RIGHT_LUNG", 1);
        body.addTag("ORGAN:LEFT_LUNG", 1);
        body.addTag("ORGAN:HEART", 1);
        body.addTag("ORGAN:SPINE_UPPER", 1);
        body.addTag("WEAR:SHIRT");
        body.addTag("SKIN", 25);
        body.addTag("BONE", 70);
        return body;
    }
    public void addUpperBody() {
        addPart(newUpperBody());
    }

    public BodyPart newFoot() {
        BodyPart body = new BodyPart("FOOT");
        body.addTag("CAN_WEAR_BOOT");
        body.addTag("APPENDAGE:TOE", 5);
        body.addTag("WEAR:BOOT");
        body.addTag("SKIN", 20);
        body.addTag("BONE", 50);
        body.addTag("LOCOMOTION:CRAWL");
        body.addTag("LOCOMOTION:WALK");

        return body;
    }
    public void addFoot() {
        addPart(newFoot());
    }
    public BodyPart newHandOff() {
        BodyPart body = new BodyPart("OFF_HAND");
        body.addTag("CAN_WEAR_GAUNTLET");
        body.addTag("CAN_HOLD_ITEM");
        body.addTag("APPENDAGE:FINGER", 5);
        body.addTag("OFFHAND");
        body.addTag("WEAR:GLOVE");
        body.addTag("HOLD:TOOL");
        body.addTag("SKIN", 20);
        body.addTag("BONE", 50);
        body.addTag("LOCOMOTION:CRAWL");

        return body;
    }
    public void addHandOff() {
        addPart(newHandOff());
    }
    public BodyPart newHand() {
        BodyPart body = new BodyPart("MAIN_HAND");
        body.addTag("CAN_WEAR_GAUNTLET");
        body.addTag("CAN_HOLD_ITEM");
        body.addTag("APPENDAGE:FINGER", 5);
        body.addTag("DOMINANT");
        body.addTag("WEAR:GLOVE");
        body.addTag("HOLD:TOOL");
        body.addTag("SKIN", 20);
        body.addTag("BONE", 50);
        body.addTag("LOCOMOTION:CRAWL");

        return body;
    }
    public void addHand() {
        addPart(newHand());
    }

    public BodyPart newHead() {

        BodyPart body = new BodyPart("HEAD");
        body.addTag("CAN_WEAR_HEADWEAR");
        body.addTag("ORGAN:MOUTH",1);
        body.addTag("ORGAN:RIGHT_EYE", 1);
        body.addTag("ORGAN:LEFT_EYE", 1);
        body.addTag("ORGAN:BRAIN", 1);
        body.addTag("WEAR:HAT");
        body.addTag("SKIN", 12);
        body.addTag("BONE", 90);

        return body;
    }
    public void addHead() {
        addPart(newHead());
    }

    public BodyPart newLowerBody() {
        BodyPart body = new BodyPart("LOWER_BODY");
        body.addTag("CAN_WEAR_PANTS");

        body.addTag("ORGAN:GUT", 1);
        body.addTag("ORGAN:SPINE_LOWER", 1);
        body.addTag("INSEVERABLE");
        body.addTag("WEAR:PANTS");
        body.addTag("SKIN", 25);
        body.addTag("BONE", 30);
        return body;
    }
    public void addLowerBody() {
        addPart(newLowerBody());
    }

    public void removePart(BodyPart part) {
        bodyParts.remove(part);
    }


}
