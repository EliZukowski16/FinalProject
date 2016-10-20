package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    private final List<DailySalesData> dailySalesData;
    private final List<WeeklySalesData> weeklySalesData;
    private final List<MonthlySalesData> monthlySalesData;

    public CoreProductSalesData(CoreProduct coreProduct, List<ProductSalesData> productSales)
    {
        WeekFields woy = WeekFields.of(Locale.US);
        TemporalField tf = woy.weekOfWeekBasedYear();
        
        this.coreProduct = coreProduct;
        this.productSales = productSales;

        this.dailySalesData = new ArrayList<>();
        this.weeklySalesData = new ArrayList<>();
        this.monthlySalesData = new ArrayList<>();

        List<DailySalesData> sales = new ArrayList<>();

        for (ProductSalesData p : productSales)
        {

            sales.addAll(p.getDailySalesData());

        }

        Set<LocalDate> salesDates = sales.stream().map(s -> s.getDate()).collect(Collectors.toSet());
        
        Set<Integer> weeks = sales.stream().map(s ->  s.getDate().get(tf))
                .collect(Collectors.toSet());
        Set<Integer> months = sales.stream().map(s -> s.getDate().getMonthValue())
                .collect(Collectors.toSet());
        
        for (LocalDate d : salesDates)
        {
            Integer numberSold = sales.stream().filter(s -> s.getDate().equals(d)).map(DailySalesData::getNumberSold)
                    .reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> s.getDate().equals(d)).map(DailySalesData::getTotalValue)
                    .reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));

            dailySalesData.add(new DailySalesData(d, numberSold, totalValue));
        }
        
        for (Integer i : weeks)
        {
            Integer numberSold = sales.stream().filter(s -> i.equals(s.getDate().get(tf)))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> i.equals(s.getDate().get(tf)))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            WeeklySalesData ws = new WeeklySalesData(i, numberSold, totalValue);
            weeklySalesData.add(ws);   
        }
        
        for(Integer i : months)
        {
            Integer numberSold = sales.stream().filter(s -> i.equals(s.getDate().getMonthValue()))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> i.equals(s.getDate().getMonthValue()))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            MonthlySalesData ms = new MonthlySalesData(i, numberSold, totalValue);
            monthlySalesData.add(ms);   
        }
        
        dailySalesData.sort((s1,s2) -> s2.getDate().compareTo(s1.getDate()));
        weeklySalesData.sort((s1,s2) -> s2.getWeek().compareTo(s1.getWeek()));
        monthlySalesData.sort((s1,s2) -> s2.getMonth().compareTo(s1.getMonth()));
    }

     public CoreProduct getCoreProduct()
     {
     return coreProduct;
     }

    public List<ProductSalesData> getProductSales()
    {
        return productSales;
    }

    public List<DailySalesData> getDailySalesData()
    {
        return dailySalesData;
    }

    public List<WeeklySalesData> getWeeklySalesData()
    {
        return weeklySalesData;
    }

    public List<MonthlySalesData> getMonthlySalesData()
    {
        return monthlySalesData;
    }
    
    
}
