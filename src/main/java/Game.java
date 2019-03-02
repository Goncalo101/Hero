import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.*;

public class Game {
    private Terminal terminal = new DefaultTerminalFactory().createTerminal();
    private Screen screen = new TerminalScreen(terminal);

    private Arena arena;

    public Game() throws IOException {
        arena = new Arena(20, 20);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        run();
    }


    private void draw() throws IOException {
        this.screen.clear();
        arena.draw(screen.newTextGraphics());
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
        if (key.getKeyType() == Character && key.getCharacter() == 'q') {
            screen.close();
        }

        arena.processKey(key);
    }
}
