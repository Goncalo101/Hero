package com.goncalo.hero.core;

import com.goncalo.hero.entities.Coin;
import com.goncalo.hero.entities.Hero;
import com.goncalo.hero.entities.Monster;
import com.goncalo.hero.entities.Wall;
import com.goncalo.hero.utilities.Position;

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
    private List<Monster> monsters;

    private int numCoins = 5;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;

        hero = new Hero(5, 5);

        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
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

        moveMonsters();
    }


    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);

        for (Wall wall : walls)
            wall.draw(graphics);

        retrieveCoins();

        if (coins.isEmpty()) {
            Hero.win(graphics);
        }

        for (Coin coin : coins)
            coin.draw(graphics);

        verifyMonsterCollisions();

        for (Monster monster : monsters)
            monster.draw(graphics);
    }

    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (hero.getPosition().getX() == monster.getPosition().getX() && hero.getPosition().getY() == monster.getPosition().getY()) {
                System.out.println("Hero hit Monster");

                hero.setHealth(hero.getHealth() - 10);

                break;
            }
        }
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position next_pos = monster.move();
            if (canMove(next_pos))
                monster.setPosition(next_pos);
        }
    }

    private void moveHero(Position position) {
        if (canMove(position))
            hero.setPosition(position);
    }

    private boolean canMove(Position position) {
        return position.getX() < width - 1 && position.getX() > 0 && position.getY() < height - 1 && position.getY() > 0;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < numCoins; ++i) {
            Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);


            while (coins.indexOf(coin) != -1 || (coin.getPosition().getX() == hero.getPosition().getX() && coin.getPosition().getY() == hero.getPosition().getY())) {
                coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            }

            coins.add(coin);

        }

        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            Monster monster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);


            while (monsters.indexOf(monster) != -1 || (monster.getPosition().getX() == hero.getPosition().getX() && monster.getPosition().getY() == hero.getPosition().getY())) {
                monster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            }

            monsters.add(monster);

        }

        return monsters;
    }


    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (hero.getPosition().getX() == coin.getPosition().getX() && hero.getPosition().getY() == coin.getPosition().getY()) {
                coins.remove(coin);
                hero.setScore(hero.getScore() + 10);
                break;
            }
        }
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }
}
