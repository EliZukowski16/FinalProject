package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;

public class MonthlySalesData
{
    private final Integer month;
    private final Integer numberSold;
    private final BigDecimal totalValue;

    public MonthlySalesData(Integer month, Integer numberSold, BigDecimal totalValue)
    {
        this.month = month;
        this.numberSold = numberSold;
        this.totalValue = totalValue;
    }

    public Integer getMonth()
    {
        return month;
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
