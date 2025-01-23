package PD;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representation of a TaxRate
 */
public class TaxRate implements Comparable<TaxRate>
{
    /**
     * TaxRate of the TaxRate, percentage of what a customer should pay a governing body for an item.
     */
    private BigDecimal taxRate;
    /**
     * Effective date, the date the tax rate will be effective
     */
    private LocalDate effectiveDate;

    public TaxRate()
    {
        taxRate = BigDecimal.ZERO;
        effectiveDate = LocalDate.parse("1/1/1111", DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    public TaxRate(String effectiveDate, String rate)
    {
        taxRate = new BigDecimal(rate);
        this.effectiveDate = LocalDate.parse(effectiveDate, DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    /**
     * Constructor that initializes effectiveDate to effectiveDate, and taxRate to rate
     * @param effectiveDate EffectiveDate for a taxRate, when a tax rate will take effect
     * @param rate Tax rate percentage
     */
    public TaxRate(LocalDate effectiveDate, BigDecimal rate)
    {
        taxRate = rate;
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getTaxRate()
    {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate)
    {
        this.taxRate = taxRate;
    }

    public LocalDate getEffectiveDate()
    {
        return this.effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Determines whether or not a taxRate is effective.
     * @param date Date to determine whether or not a tax rate is effective
     * @return True if the tax rate is effective on the given date
     */
    public Boolean isEffective(LocalDate date)
    {
        return !date.isBefore(this.effectiveDate);
    }

    /**
     * Compares one taxRate to another tax rate based on effective date and rate
     * @param other TaxRate to compare to this.taxRate
     * @return Comparison result for ordering
     */
    @Override
    public int compareTo(TaxRate other)
    {
        int dateComparison = this.effectiveDate.compareTo(other.getEffectiveDate());
        if (dateComparison != 0)
        {
            return dateComparison;
        }
        else
        {
            return this.taxRate.compareTo(other.getTaxRate());
        }
    }

    /**
     * Makes a string representation of a Tax Rate
     * @return String representation of a Tax Rate
     */
    @Override
    public String toString()
    {
        return taxRate.toString() + " effective " + effectiveDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    /**
     * Determines whether the Tax Rate is used in transactions (placeholder logic)
     * @return True if the Tax Rate is used
     */
    public boolean isUsed()
    {
        // Placeholder implementation, adjust based on actual usage in transactions
        return false;
    }
}
