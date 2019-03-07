import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends Element {
    private List<Position> functionList = new ArrayList<>();

    public Monster(int x, int y) {
        super(x, y);

        functionList.add(moveUp());
        functionList.add(moveDown());
        functionList.add(moveLeft());
        functionList.add(moveDown());

        functionList.add(new Position(position.getX() + 1, position.getY() + 1));
        functionList.add(new Position(position.getX() + 2, position.getY() + 2));
        functionList.add(new Position(position.getX() - 1, position.getY() - 1));
        functionList.add(new Position(position.getX() - 2, position.getY() - 2));
        functionList.add(new Position(position.getX() + 1, position.getY() - 1));
        functionList.add(new Position(position.getX() + 2, position.getY() - 2));
        functionList.add(new Position(position.getX() - 1, position.getY() + 1));
        functionList.add(new Position(position.getX() - 2, position.getY() + 2));
    }

    public Position move() {
        Random random = new Random();

        return functionList.get(random.nextInt(functionList.size() - 1));
    }


    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF1E1E"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
