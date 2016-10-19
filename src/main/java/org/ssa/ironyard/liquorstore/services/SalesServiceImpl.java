package org.ssa.ironyard.liquorstore.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOSales;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.SalesMonthly;
import org.ssa.ironyard.liquorstore.model.SalesWeekly;

@Service
public class SalesServiceImpl implements SalesService
{

    DAOSales daoSales;

    @Autowired
    public SalesServiceImpl(DAOSales daoSales)
    {
        this.daoSales = daoSales;
    }

    @Override
    @Transactional
    public Sales addSale(Sales sales)
    {
        if (sales == null)
            return null;

        return daoSales.insert(sales);

    }
    
    @Override
    @Transactional
    public List<Sales> aggregateDailySales()
    {
        return aggregateDailySales(LocalDate.now().minusDays(1));
    }

    @Override
    @Transactional
    public List<Sales> aggregateDailySales(LocalDate date)
    {
        Map<Product, Sales> dailyProductSalesMap = new HashMap<>();

        List<Sales> dailyIndividualSales = daoSales.readSalesInDateRange(date, date);
        List<Sales> dailyAggregateSales = new ArrayList<>();

        for (Sales s : dailyIndividualSales)
        {
            Sales sales;

            if (!dailyProductSalesMap.containsKey(s.getProduct()))
            {
                dailyProductSalesMap.put(s.getProduct(), s);
            }
            else
            {

                sales = dailyProductSalesMap.get(s.getProduct());

                Integer numberSold = sales.getNumberSold() + s.getNumberSold();
                BigDecimal totalValue = sales.getTotalValue().add(s.getTotalValue());

                sales = ((SalesDaily) sales).of().numberSold(numberSold).totalValue(totalValue).build();

                dailyProductSalesMap.put(sales.getProduct(), sales);

            }
        }

        for (Entry<Product, Sales> e : dailyProductSalesMap.entrySet())
        {
            Sales sales;

            if ((sales = daoSales.insert(e.getValue().of().aggregateSales(true).build())) == null)
                throw new RuntimeException(
                        "Sales data for product " + e.getKey().getCoreProduct().getName() + " could not be aggregated");

            dailyAggregateSales.add(sales);
        }

        for (Sales s : dailyIndividualSales)
        {
            if (!daoSales.delete(s.getId()))
                throw new RuntimeException(
                        "Could not delete sales data " + s.getId() + " while aggregating daily sales");
        }

        return dailyAggregateSales;
    }

    @Override
    public boolean editSale(Order order)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Sales readSale(Integer id)
    {
        if (id == null)
            return null;

        return daoSales.read(id);
    }

    @Override
    @Transactional
    public List<Sales> readAllSales()
    {
        return daoSales.readAll();
    }

    @Override
    @Transactional
    public Map<Product, Map<LocalDate, Sales>> createDailySalesMap(List<SalesDaily> dailySales)
    {
        Map<Product, Map<LocalDate, Sales>> dailySalesMap = new HashMap<>();

        for (Sales s : dailySales)
        {
            if (s.getAggregateSales())
            {
                Map<LocalDate, Sales> productSales = new HashMap<>();

                if ((dailySalesMap.containsKey(s.getProduct())))
                {
                    productSales = dailySalesMap.get(s.getProduct());
                }

                productSales.put(((SalesDaily) s).getDateSold(), s);
                dailySalesMap.put(s.getProduct(), productSales);
            }
        }

        return dailySalesMap;
    }

    @Override
    @Transactional
    public Map<Product, Map<Integer, Sales>> createWeeklySalesMap(List<SalesDaily> dailySales)
    {
        Map<Product, Map<Integer, Sales>> weeklySalesMap = new HashMap<>();

        for (Sales s : dailySales)
        {
            if (s.getAggregateSales())
            {
                Sales sales = ((SalesWeekly.Builder) new SalesWeekly.Builder().id(s.getId()).numberSold(s.getNumberSold())
                        .product(s.getProduct()).totalValue(s.getTotalValue()).loaded(true).aggregateSales(true))
                                .weekSold(((SalesDaily) s).getDateSold()).build();

                Map<Integer, Sales> productSales = new HashMap<>();

                if (weeklySalesMap.containsKey((s.getProduct())))
                {
                    productSales = weeklySalesMap.get(s.getProduct());

                    Sales weeklySales;

                    if ((weeklySales = productSales.get(((SalesWeekly) sales).getWeekSold())) != null)
                    {
                        Integer numberSold = sales.getNumberSold() + weeklySales.getNumberSold();
                        BigDecimal totalValue = sales.getTotalValue().add(weeklySales.getTotalValue());

                        sales = sales.of().totalValue(totalValue).numberSold(numberSold).build();
                    }
                }

                productSales.put(((SalesWeekly) sales).getWeekSold(), sales);
                weeklySalesMap.put(s.getProduct(), productSales);
            }
        }
        
        return weeklySalesMap;
    }
    
    @Override
    public Map<Product, Map<Integer, Sales>> createMonthlySalesMap(List<SalesDaily> dailySales)
    {
        Map<Product, Map<Integer, Sales>> monthlySalesMap = new HashMap<>();

        for (Sales s : dailySales)
        {
            if (s.getAggregateSales())
            {
                Sales sales = ((SalesMonthly.Builder) new SalesMonthly.Builder().id(s.getId()).numberSold(s.getNumberSold())
                        .product(s.getProduct()).totalValue(s.getTotalValue()).loaded(true).aggregateSales(true))
                                .monthSold(((SalesDaily) s).getDateSold()).build();

                Map<Integer, Sales> productSales = new HashMap<>();

                if (monthlySalesMap.containsKey((s.getProduct())))
                {
                    productSales = monthlySalesMap.get(s.getProduct());

                    Sales monthlySales;

                    if ((monthlySales = productSales.get(((SalesMonthly) sales).getMonthSold())) != null)
                    {
                        Integer numberSold = sales.getNumberSold() + monthlySales.getNumberSold();
                        BigDecimal totalValue = sales.getTotalValue().add(monthlySales.getTotalValue());

                        sales = sales.of().totalValue(totalValue).numberSold(numberSold).build();
                    }
                }

                productSales.put(((SalesMonthly) sales).getMonthSold(), sales);
                monthlySalesMap.put(s.getProduct(), productSales);
            }
        }
        
        return monthlySalesMap;
    }

    @Override
    public List<Sales> searchTimeFrame(LocalDate start, LocalDate end)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Sales> searchType(Type type)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Sales> readSalesForYesterday()
    {
        return daoSales.readSalesForPreviousDay();
    }
    
    @Override
    public List<Sales> readSalesForYesterday(Integer productID)
    {
        return daoSales.readSalesForPreviousDay(productID);
    }
    
    @Override
    public List<Sales> readSalesForYesterday(List<Integer> productIDs)
    {
        return daoSales.readSalesForPreviousDay(productIDs);
    }
    
    @Override
    public List<Sales> readSalesForLast30Days()
    {
        return daoSales.readSalesForLast30Days();
    }
    
    @Override
    public List<Sales> readSalesForLast30Days(Integer productID)
    {
        return daoSales.readSalesForLast30Days(productID);
    }
    
    @Override
    public List<Sales> readSalesForLast30Days(List<Integer> productIDs)
    {
        return daoSales.readSalesForLast30Days(productIDs);
    }
    
    @Override
    public List<Sales> readSalesForLast90Days()
    {
        return daoSales.readSalesForLast90Days();
    }
    
    @Override
    public List<Sales> readSalesForLast90Days(Integer productID)
    {
        return daoSales.readSalesForLast90Days(productID);
    }
    
    @Override
    public List<Sales> readSalesForLast90Days(List<Integer> productIDs)
    {
        return daoSales.readSalesForLast90Days(productIDs);
    }
    
    @Override
    public List<Sales> readSalesForLast180Days()
    {
        return daoSales.readSalesForLast180Days();
    }
    
    @Override
    public List<Sales> readSalesForLast180days(Integer productID)
    {
        return daoSales.readSalesForLast180Days(productID);
    }
    
    @Override
    public List<Sales> readSalesForLast180Days(List<Integer> productIDs)
    {
        return daoSales.readSalesForLast180Days(productIDs);
    }
    
    @Override
    public List<Sales> readSalesForLastYear()
    {
        return daoSales.readSalesForLastYear();
    }
    
    @Override
    public List<Sales> readSalesForLastYear(Integer productID)
    {
        return daoSales.readSalesForLastYear(productID);
    }
    
    @Override
    public List<Sales> readSalesForLastYear(List<Integer> productIDs)
    {
        return daoSales.readSalesForLastYear(productIDs);
    }
    
    @Override
    public List<Sales> readSalesForPastNumberOfDays(Integer numberOfDays)
    {
        return daoSales.readSalesForLastVariableDays(numberOfDays, new ArrayList<>());
    }
    
    @Override
    public List<Sales> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs)
    {
        return daoSales.readSalesForLastVariableDays(numberOfDays, productIDs);
    }
    
    @Override
    public List<Sales> searchProduct(Integer productID)
    {
        if(productID ==  null)
            return new ArrayList<>();
        
        return daoSales.readSalesForProduct(productID);
    }
    
    @Override
    public List<Sales> searchProduct(List<Integer> productIDs)
    {
        if(productIDs.isEmpty())
            return new ArrayList<>();
        
        return daoSales.readSalesForProduct(productIDs);
        
    }

    @Override
    public boolean searchKeyword(String keyword)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
