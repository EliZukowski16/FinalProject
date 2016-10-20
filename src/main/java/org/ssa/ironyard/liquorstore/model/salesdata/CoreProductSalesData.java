package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class CoreProductSalesData
{
    private final CoreProduct coreProduct;

    private final List<Sales> productSales;

    private final List<SalesData> salesData;

    public CoreProductSalesData(CoreProduct coreProduct, List<Sales> productSales)
    {
        this.coreProduct = coreProduct;
        this.productSales = productSales;
        this.salesData = new ArrayList<>();

        Set<LocalDate> salesDates = productSales.stream().map(s -> ((SalesDaily) s).getDateSold())
                .collect(Collectors.toSet());
        for (LocalDate d : salesDates)
        {
            Integer numberSold = productSales.stream().filter(s -> ((SalesDaily) s).getDateSold().equals(d))
                    .map(Sales::getNumberSold).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = productSales.stream().filter(s -> ((SalesDaily) s).getDateSold().equals(d))
                    .map(Sales::getTotalValue).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));

            salesData.add(new SalesData(d, numberSold, totalValue));
        }
    }

    public CoreProduct getCoreProduct()
    {
        return coreProduct;
    }

    public List<Sales> getProductSales()
    {
        return productSales;
    }

    public List<SalesData> getSalesData()
    {
        return this.salesData;
    }
}
