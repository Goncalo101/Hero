import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;

    private Hero hero;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;

        hero = new Hero(5, 5);
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
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

            default:
                break;
        }
    }

    public void draw(Screen screen) {
        hero.draw(screen);
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        return position.getX() <= width && position.getX() >= 0 && position.getY() <= height && position.getY() >= 0;
    }
}
