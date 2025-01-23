package PD;

import java.math.BigDecimal;

/**
 * Representation of a CashDrawer held by a Register
 */
public class CashDrawer {

    private BigDecimal startingCash;
    private BigDecimal cash;

    public CashDrawer() {
        startingCash = BigDecimal.ZERO;
        cash = BigDecimal.ZERO;
    }

    public BigDecimal getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(BigDecimal startingCash) {
        this.startingCash = startingCash;
        this.cash = startingCash; // Initialize the cash with starting cash
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void addCash(BigDecimal amount) {
        cash = cash.add(amount);
    }

    public void removeCash(BigDecimal amount) {
        cash = cash.subtract(amount);
    }

    @Override
    public String toString() {
        return "Cash in drawer: " + cash.toString();
    }
}
