package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class CoreProductSalesData
{
    private final CoreProduct coreProduct;

    private final List<ProductSalesData> productSales;

    private final List<SalesData> salesData;

    public CoreProductSalesData(CoreProduct coreProduct, List<ProductSalesData> productSales)
    {
        this.coreProduct = coreProduct;
        this.productSales = productSales;

        this.salesData = new ArrayList<>();

        List<SalesData> sales = new ArrayList<>();

        for (ProductSalesData p : productSales)
        {

            sales.addAll(p.getProductSales());

        }

        Set<LocalDate> salesDates = sales.stream().map(s -> s.getDate()).collect(Collectors.toSet());
        for (LocalDate d : salesDates)
        {
            Integer numberSold = sales.stream().filter(s -> s.getDate().equals(d)).map(SalesData::getNumberSold)
                    .reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> s.getDate().equals(d)).map(SalesData::getTotalValue)
                    .reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));

            salesData.add(new SalesData(d, numberSold, totalValue));
        }
    }

     public CoreProduct getCoreProduct()
     {
     return coreProduct;
     }

    public List<ProductSalesData> getProductSales()
    {
        return productSales;
    }

    public List<SalesData> getSalesData()
    {
        return this.salesData;
    }
}
