package PD;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representation of a TaxCategory
 */
public class TaxCategory
{
    /**
     * Name/Category for taxes
     */
    private String category;
    /**
     * Collection of TaxRates for the TaxCategory
     */
    private TreeSet<TaxRate> taxRates;
    /**
     * Collection of Items associated with the TaxCategory
     */
    private TreeMap<String, Item> items;

    public TaxCategory()
    {
        category = "";
        taxRates = new TreeSet<TaxRate>();
        items = new TreeMap<String, Item>();
    }

    public TaxCategory(String category)
    {
        this();
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public TreeSet<TaxRate> getTaxRates()
    {
        return taxRates;
    }

    public TreeMap<String, Item> getItems()
    {
        return items;
    }

    public void addItem(Item item)
    {
        items.put(item.getNumber(), item);
    }

    public void removeItem(Item item)
    {
        items.remove(item.getNumber());
    }

    /**
     * Adds a Tax Rate to the collection of Tax Rates
     * @param taxRate Tax Rate to be added to the collection of Tax Rates
     */
    public void addTaxRate(TaxRate taxRate)
    {
        taxRates.add(taxRate);
    }

    /**
     * Removes a Tax Rate from the collection of TaxRates
     * @param taxRate Tax Rate to remove from the collection of TaxRates
     */
    public void removeTaxRate(TaxRate taxRate)
    {
        taxRates.remove(taxRate);
    }

    /**
     * Gets the TaxRate for a specified date
     * @param date Date to determine a TaxRate
     * @return TaxRate for given date
     */
    public BigDecimal getTaxRateForDate(LocalDate date)
    {
        BigDecimal result = BigDecimal.ZERO;

        for (TaxRate t : taxRates)
        {
            if (t.isEffective(date))
                result = t.getTaxRate();
        }

        return result;
    }

    /**
     * Determines whether the TaxCategory is used (e.g., associated with any items)
     * @return True if the TaxCategory is used
     */
    public boolean isUsed()
    {
        return false;    }

    /**
     * Makes a string representation of a Tax Category
     * @return String representation of a Tax Category
     */
    @Override
    public String toString()
    {
        return category;
    }
}
