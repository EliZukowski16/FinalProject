package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

public class TypeSalesData
{
    private final Type type;

    private final List<CoreProductSalesData> coreProductSales;

    private final List<SalesData> salesData;

    public TypeSalesData(Type type, List<CoreProductSalesData> coreProductSales)
    {
        this.type = type;
        this.coreProductSales = coreProductSales;
        this.salesData = new ArrayList<>();
        List<SalesData> coreProductSalesData = new ArrayList<>();
        Set<LocalDate> salesDates = new HashSet<>();

        for (CoreProductSalesData c : coreProductSales)
        {
            coreProductSalesData.addAll(c.getSalesData());

            for (SalesData s : c.getSalesData())
            {
                salesDates.add(s.getDate());
            }
        }

        for (LocalDate d : salesDates)
        {
            Integer numberSold = coreProductSalesData.stream().filter(s -> s.getDate().equals(d))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = coreProductSalesData.stream().filter(s -> s.getDate().equals(d))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));

            this.salesData.add(new SalesData(d, numberSold, totalValue));
        }

    }

    public Type getType()
    {
        return type;
    }

    public List<CoreProductSalesData> getCoreProductSales()
    {
        return coreProductSales;
    }

}
