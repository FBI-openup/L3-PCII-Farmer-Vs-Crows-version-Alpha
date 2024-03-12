package model.units;

import java.awt.*;

import static java.lang.Math.random;

public class Corn extends Units{
    private int maturity;
    private boolean crowOnTop;

    public Corn() {
        super((int) (random() * 100), (int) (random() * 100));// Randomly generate the position of the corn
        this.maturity = 0;
        this.crowOnTop = false;
    }

    public void increaseMaturity(int amount) {
        this.maturity += amount;
        if (this.maturity > 100) {
            this.maturity = 100;
        }
    }

    public void checkCrow() {
        if (crowOnTop) {
            this.maturity = this.maturity - 10;// The crow eats the corn little by little
        }
    }

    public void setCrowOnTop(boolean crowOnTop) {
        this.crowOnTop = crowOnTop;
    }

    public int getMaturity() {
        return this.maturity;
    }
}