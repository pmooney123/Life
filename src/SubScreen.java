import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class SubScreen implements Screen {

    protected abstract String getVerb();
    protected abstract boolean isAcceptable();
    protected abstract Screen use();

    public SubScreen(){

    }

    public void displayOutput(AsciiPanel terminal) {

    }
    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}