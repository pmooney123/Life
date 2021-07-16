import javax.naming.CompositeName;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class PlayScreen implements Screen {
    public Factory factory;
    public static Random random = new Random();
    private int centerX;
    private int centerY;
    private final int screenWidth;
    private final int screenHeight;
    public ArrayList<String> input = new ArrayList<String>();
    public boolean blinker = false;
    public ArrayList<String> output = new ArrayList<>();
    private boolean paused = false;
    private int describing = 0;
    public Creature player;
    private Screen subscreen;

    public static boolean HELP = true;

    //debugging thing
    public boolean reset = false;

    public PlayScreen(){
        screenWidth = AsciiPanel.PORT_WIDTH;
        screenHeight = AsciiPanel.PORT_HEIGHT;

        this.factory = new Factory();
        player = factory.newPlayer();

    }

    public void update() {

    }

    public int getScrollX() { //return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
        return 0;
        }
    public int getScrollY() { //return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
        return 0;
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {

    }
    public void displayOutput(AsciiPanel terminal) {
        if (subscreen != null) {
            subscreen.displayOutput(terminal);
        } else {
        terminal.write("Enter text below: ", 1, 1);

        int x = 1;
        int y = 2;
        for (String part : input) {
            terminal.write(part, x, y);
            x += part.length();
            if (x > screenWidth) {
                x = 1;
                y++;
            }
        }

        blinker(terminal, x, y);
        terminal.writeCenter("Output text here:", 1);
        displayTextOutput(terminal);





        }
    }
    public void blinker(AsciiPanel terminal, int x, int y) {
        if (ApplicationMain.count % 30 == 0) {
            blinker = !blinker;
        }
        if (blinker) {
            terminal.write("/", x, y);

        }
    }
    public void displayTextOutput(AsciiPanel terminal) {
        int y = 2;
        if (output.size() > 0) {
            terminal.writeCenter(output.get(output.size() - 1), y++, Color.white, Color.blue);
            for (int j = output.size() - 2; j >= 0; j--) {
                String string = output.get(j);
                terminal.writeCenter(string, y++);
            }
        }

        //DESCRIPTION
        writePlayerDescription(terminal);


        //INVENTORY
        writePlayerInventory(terminal);
    }
    public void writePlayerDescription(AsciiPanel terminal) {
        if (describing == 1) {
            int x = 1;
            int y = AsciiPanel.PORT_HEIGHT;
            terminal.write("Player: " + player.name, x, y++);
            for (Note note : player.listParts()) {
                if (y > AsciiPanel.SCREEN_HEIGHT ){
                    break;
                }
                if (note.string.length() + x > AsciiPanel.SCREEN_WIDTH) {
                    x = 1;
                    y++;
                }
                if (note.string.equals("&")) {
                    y++;
                    x = 1;
                } else {
                    terminal.write(note.string, x, y, note.color);
                    x += note.string.length();
                }
            }
        }
        if (describing == 2) {
            int x = 1;
            int y = AsciiPanel.PORT_HEIGHT;
            terminal.write("Equipped: " + player.name, x, y++);
            for (Note note : player.listEquipped()) {
                if (y > AsciiPanel.SCREEN_HEIGHT ){
                    break;
                }
                if (note.string.length() + x > AsciiPanel.SCREEN_WIDTH) {
                    x = 1;
                    y++;
                }
                if (note.string.equals("&")) {
                    y++;
                    x = 1;
                } else {
                    terminal.write(note.string, x, y, note.color);
                    x += note.string.length();
                }
            }

            x = screenWidth/2;
            y = AsciiPanel.PORT_HEIGHT;

            terminal.write("INVENTORY:",x, y++);
            for (Note note : player.getInventoryPrint()) {
                if (y > AsciiPanel.SCREEN_HEIGHT ){
                    break;
                }
                if (note.string.length() + x > AsciiPanel.SCREEN_WIDTH) {
                    x = screenWidth / 2;
                    y++;
                }
                if (note.string.equals("&")) {
                    y++;
                    x = screenWidth/2;
                } else {
                    terminal.write(note.string, x, y, note.color);
                    x += note.string.length();
                }
            }

        }

    }
    public void writePlayerInventory(AsciiPanel terminal) {

    }

    public Screen respondToInput() {
        output.clear();
        output.add("");
        String string = "";
        //output.add("Input ");
        for (String part : input) {
            string = string.concat(part);
        }
        if(string.equals("NAME")) {
            input.clear();
            output.add("Your name is " + player.name);
        }
        if(string.contains("HELP")) {
            input.clear();
            return new HelpScreen();
        }
        if (string.equals("GRAB")) {
            player.inv.addItem(factory.arrowStack(20));
        }
        if (string.equals("ATTACK")) {
            if (player.weapon() != null) {
                output = player.resolveAttack(player, new Attack(
                        player.weapon().getTag("PIERCING").value,
                        player.weapon().getTag("SLASHING").value,
                        player.weapon().getTag("BLUDGEONING").value, player));
            } else {
                output.add("Cannot attack-- no weapon equipped");
            }
        }
        if (string.contains("SWORD")) {
            player.equip(factory.newSword());
            output.add("Equipped a sword");
        }
        if (string.contains("SPEAR")) {
            player.equip(factory.newSpear());
            output.add("Equipped a spear");
        }
        if (string.contains("HAMMER")) {
            player.equip(factory.newHammer());
            output.add("Equipped a hammer");
        }
        if (string.contains("BOOTS")) {
            player.equip(factory.newBoot());
            output.add("Equipped boot");
        }
        input.clear();
        return null;
    }
    public Screen respondToUserInput(KeyEvent key) {

        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            switch (key.getKeyCode()) {
                case (KeyEvent.VK_ESCAPE):
                    input.clear();
                    return this;
                case (KeyEvent.VK_V):
                    describing++;
                    if (describing > 2) {
                        describing = 0;
                    }
                    return this;
                case (KeyEvent.VK_ENTER):
                    subscreen = respondToInput();
                    return this;
                case (KeyEvent.VK_BACK_SPACE):
                    if (input.size() > 0) {
                        input.remove(input.size() - 1);
                    }
                    return this;
                default:
                    if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ ".contains((key.getKeyChar() + "").toUpperCase())) {
                        try {
                            char Character = key.getKeyChar();
                            String string = Character + "";
                            string = string.toUpperCase();
                            input.add(string);
                            //  System.out.println("key.toString().toUpperCase() " + key.toString().toUpperCase());
                        } catch (Exception e) {
                            System.out.println("UNKNWON INPUT" + key.getKeyCode());
                        }
                    }
            }
        }
        return this;
    }
}