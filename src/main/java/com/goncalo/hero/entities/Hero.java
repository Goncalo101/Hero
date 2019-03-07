package com.goncalo.hero.entities;

import com.goncalo.hero.core.Game;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element {
    private int health = 100;
    private static int score = 0;

    public Hero(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        TextColor originalBackground = graphics.getBackgroundColor();

        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));

        if (health > 75) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        } else if (health > 20) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        } else {
            graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        }

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(25, 0), "Health: " + health + "%");
        graphics.putString(new TerminalPosition(25, 1), "Score: " + score);

        graphics.setBackgroundColor(originalBackground);
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "X");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        Hero.score = score;
    }

    public static void lose(TextGraphics graphics) {
        graphics.putString(new TerminalPosition(25, 10), "You lost. Your score: " + score + ". Press q to exit.");
        Game.disableInput();
    }

    public static void win(TextGraphics graphics) {
        graphics.putString(new TerminalPosition(25, 10), "You win. Your score: " + score + ". Press q to exit.");
        Game.disableInput();
    }
}
