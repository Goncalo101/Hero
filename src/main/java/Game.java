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

    private Hero hero;

    public Game() throws IOException {
        hero = new Hero(10,10);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        run();
    }

    private void draw() throws IOException {
        this.screen.clear();
        hero.draw(screen);
        screen.refresh();
    }

    public void run() throws IOException {
        while (true) {
            this.draw();

            KeyStroke key = screen.readInput();
            processKey(key);

            if (key.getKeyType() == EOF) break;
        }
    }

    private void processKey(KeyStroke key) throws IOException {
        switch(key.getKeyType()) {
            case ArrowUp: {
                moveHero(hero.moveUp());
                break;
            }

            case ArrowDown: {
                moveHero(hero.moveDown());
                break;
            }

            case ArrowLeft: {
                moveHero(hero.moveLeft());
                break;
            }

            case ArrowRight: {
                moveHero(hero.moveRight());
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

    private void moveHero(Position position) {
        hero.setPosition(position);
    }
}
