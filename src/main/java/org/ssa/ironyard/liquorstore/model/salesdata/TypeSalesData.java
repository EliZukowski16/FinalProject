package org.ssa.ironyard.liquorstore.model.salesdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

public class TypeSalesData
{
    private final Type type;

    private final List<CoreProductSalesData> coreProductSales;

    private final List<DailySalesData> dailySalesData;
    private final List<WeeklySalesData> weeklySalesData;
    private final List<MonthlySalesData> monthlySalesData;

    public TypeSalesData(Type type, List<CoreProductSalesData> coreProductSales)
    {
        WeekFields woy = WeekFields.of(Locale.US);
        TemporalField tf = woy.weekOfWeekBasedYear();
        
        this.type = type;
        this.coreProductSales = coreProductSales;
        this.dailySalesData = new ArrayList<>();
        this.weeklySalesData = new ArrayList<>();
        this.monthlySalesData = new ArrayList<>();
        List<DailySalesData> coreProductSalesData = new ArrayList<>();
        Set<LocalDate> salesDates = new HashSet<>();

        for (CoreProductSalesData c : coreProductSales)
        {
            coreProductSalesData.addAll(c.getDailySalesData());

            for (DailySalesData s : c.getDailySalesData())
            {
                salesDates.add(s.getDate());
            }
        }
        
        Set<Integer> weeks = coreProductSalesData.stream().map(s ->  s.getDate().get(tf))
                .collect(Collectors.toSet());
        Set<Integer> months = coreProductSalesData.stream().map(s -> s.getDate().getMonthValue())
                .collect(Collectors.toSet());

        for (LocalDate d : salesDates)
        {
            Integer numberSold = coreProductSalesData.stream().filter(s -> s.getDate().equals(d))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = coreProductSalesData.stream().filter(s -> s.getDate().equals(d))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));

            this.dailySalesData.add(new DailySalesData(d, numberSold, totalValue));
        }
        
        for (Integer i : weeks)
        {
            Integer numberSold = coreProductSalesData.stream().filter(s -> i.equals(s.getDate().get(tf)))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = coreProductSalesData.stream().filter(s -> i.equals(s.getDate().get(tf)))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            WeeklySalesData ws = new WeeklySalesData(i, numberSold, totalValue);
            weeklySalesData.add(ws);   
        }
        
        for(Integer i : months)
        {
            Integer numberSold = coreProductSalesData.stream().filter(s -> i.equals(s.getDate().getMonthValue()))
                    .map(s -> s.getNumberSold()).reduce(0, (s1, s2) -> s1 + s2);
            BigDecimal totalValue = coreProductSalesData.stream().filter(s -> i.equals(s.getDate().getMonthValue()))
                    .map(s -> s.getTotalValue()).reduce(BigDecimal.ZERO, (s1, s2) -> s1.add(s2));
            
            MonthlySalesData ms = new MonthlySalesData(i, numberSold, totalValue);
            monthlySalesData.add(ms);   
        }
        
        dailySalesData.sort((s1,s2) -> s2.getDate().compareTo(s1.getDate()));
        weeklySalesData.sort((s1,s2) -> s2.getWeek().compareTo(s1.getWeek()));
        monthlySalesData.sort((s1,s2) -> s2.getMonth().compareTo(s1.getMonth()));

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

    public Type getType()
    {
        return type;
    }

    public List<CoreProductSalesData> getCoreProductSales()
    {
        return coreProductSales;
    }

}
