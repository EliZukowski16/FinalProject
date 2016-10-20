package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;

public class WeeklySalesData
{
    private final Integer week;
    private final Integer numberSold;
    private final BigDecimal totalValue;

    public WeeklySalesData(Integer week, Integer numberSold, BigDecimal totalValue)
    {
        this.week = week;
        this.numberSold = numberSold;
        this.totalValue = totalValue;
    }

    public Integer getWeek()
    {
        return week;
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
