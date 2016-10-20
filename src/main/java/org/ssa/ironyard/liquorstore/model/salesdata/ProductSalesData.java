package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class ProductSalesData
{

    private final Product product;

    private final List<DailySalesData> dailySalesData;
    private final List<WeeklySalesData> weeklySalesData;
    private final List<MonthlySalesData> monthlySalesData;

    public ProductSalesData(Product product, List<Sales> sales)
    {
        WeekFields woy = WeekFields.of(Locale.US);
        TemporalField tf = woy.weekOfWeekBasedYear();
        weeklySalesData = new ArrayList<>();
        monthlySalesData = new ArrayList<>();

        this.product = product;

        dailySalesData = sales.stream()
                .map(s -> new DailySalesData(((SalesDaily) s).getDateSold(), s.getNumberSold(), s.getTotalValue()))
                .collect(Collectors.toList());

        Set<Integer> weeks = sales.stream().map(s -> ((SalesDaily) s).getDateSold().get(tf))
                .collect(Collectors.toSet());
        Set<Integer> months = sales.stream().map(s -> ((SalesDaily) s).getDateSold().getMonthValue())
                .collect(Collectors.toSet());

        for (Integer i : weeks)
        {
            Integer numberSold = sales.stream().filter(s -> i.equals(((SalesDaily) s).getDateSold().get(tf)))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> i.equals(((SalesDaily) s).getDateSold().get(tf)))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            WeeklySalesData ws = new WeeklySalesData(i, numberSold, totalValue);
            weeklySalesData.add(ws);   
        }
        
        for(Integer i : months)
        {
            Integer numberSold = sales.stream().filter(s -> i.equals(((SalesDaily) s).getDateSold().getMonthValue()))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = sales.stream().filter(s -> i.equals(((SalesDaily) s).getDateSold().getMonthValue()))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            MonthlySalesData ms = new MonthlySalesData(i, numberSold, totalValue);
            monthlySalesData.add(ms);   
        }
        
        dailySalesData.sort((s1,s2) -> s2.getDate().compareTo(s1.getDate()));
        weeklySalesData.sort((s1,s2) -> s2.getWeek().compareTo(s1.getWeek()));
        monthlySalesData.sort((s1,s2) -> s2.getMonth().compareTo(s1.getMonth()));

    }

    public Product getProduct()
    {
        return product;
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
