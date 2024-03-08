package model.units;

public class Corn {
    private int maturity;
    private boolean crowOnTop;

    public Corn() {
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
            this.maturity = 0;
        }
    }

    public void setCrowOnTop(boolean crowOnTop) {
        this.crowOnTop = crowOnTop;
    }

    public int getMaturity() {
        return this.maturity;
    }
}