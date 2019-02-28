import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private Terminal terminal = new DefaultTerminalFactory().createTerminal();
    private Screen screen = new TerminalScreen(terminal);

    private int x = 10;
    private int y = 10;

    public Game() throws IOException {
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        run();
    }

    private void draw() throws IOException {
        this.screen.clear();
        screen.setCharacter(x, y, new TextCharacter('X'));
        screen.refresh();
    }

    public void run() throws IOException {
        while(true) {
            this.draw();

            KeyStroke key = screen.readInput();
            processKey(key);

            if (key.getKeyType() == EOF) break;
        }
    }

    private void processKey(KeyStroke key) throws IOException {
        switch(key.getKeyType()) {
            case ArrowUp: {
                --y;
                break;
            }

            case ArrowDown: {
                ++y;
                break;
            }

            case ArrowLeft: {
                --x;
                break;
            }

            case ArrowRight: {
                ++x;
                break;
            }

            case Character: {
                switch(key.getCharacter()) {
                    case 'q': {
                        screen.close();
                    }
                }
            }

            default:break;
        }
    }
}
