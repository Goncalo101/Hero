import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Hero {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#FF0033"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(position.getX(), position.getY()), "|");
    }
}
