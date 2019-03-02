import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;

    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;

        hero = new Hero(5, 5);

        this.walls = createWalls();
        this.coins = createCoins();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; ++c) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; ++r) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
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

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);

        for (Wall wall : walls)
            wall.draw(graphics);

        retrieveCoins();

        for (Coin coin : coins)
            coin.draw(graphics);
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position) {
        return position.getX() < width - 1 && position.getX() > 0 && position.getY() < height - 1 && position.getY() > 0;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);


            while (coins.indexOf(coin) != -1 || (coin.position.getX() == hero.position.getX() && coin.position.getY() == hero.position.getY())) {
                coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            }

            coins.add(coin);

        }

        return coins;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (hero.position.getX() == coin.position.getX() && hero.position.getY() == coin.position.getY()) {
                coins.remove(coin);
                break;
            }
        }
    }
}
