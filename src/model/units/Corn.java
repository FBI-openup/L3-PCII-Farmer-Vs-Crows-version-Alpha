package model.units;

import java.awt.*;

public class Corn extends Units{
    private int maturity;
    private boolean crowOnTop;

    public Corn(Point position) {
        super(position);
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