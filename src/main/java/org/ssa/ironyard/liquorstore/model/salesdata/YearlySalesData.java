package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class YearlySalesData
{
    private final Integer year;
    private final Integer numberSold;
    private final BigDecimal totalValue;
    private final List<MonthlySalesData> monthlySalesData;

    public YearlySalesData(Integer year, List<SalesDaily> sales)
    {
        this.year = year;
        this.monthlySalesData = new ArrayList<>();

        Set<Integer> months = sales.stream().filter(s -> year.equals(s.getDateSold().getYear())).map(s -> s.getDateSold().getMonthValue())
                .collect(Collectors.toSet());
        
        

        this.numberSold = 0;
        this.totalValue = BigDecimal.ZERO;
    }

    public Integer getYear()
    {
        return year;
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
