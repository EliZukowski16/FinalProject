package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesData
{
    private final LocalDate date;
    private final Integer numberSold;
    private final BigDecimal totalValue;

    public SalesData(LocalDate date, Integer numberSold, BigDecimal totalValue)
    {
        this.date = date;
        this.numberSold = numberSold;
        this.totalValue = totalValue;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public Integer getNumberSold()
    {
        return numberSold;
    }

    public BigDecimal getTotalValue()
    {
        return totalValue;
    }

}
