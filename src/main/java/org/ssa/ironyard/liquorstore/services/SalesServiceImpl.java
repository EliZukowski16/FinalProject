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
        Map<Product, Sales> dailyProductSalesMap = new HashMap<>();

        List<Sales> dailyIndividualSales = daoSales.readSalesForPreviousDay();
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

            if ((sales = daoSales.insert(e.getValue())) == null)
                throw new RuntimeException(
                        "Sales data for product " + e.getKey().getCoreProduct().getName() + " could not be aggregated");

            dailyAggregateSales.add(sales);
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
       if(id == null)
           return null;
       
       return daoSales.read(id);
    }

    @Override
    public List<Sales> readAllSales()
    {
        return daoSales.readAll();
    }

    @Override
    public boolean searchTimeFrame(LocalDate date1, LocalDate date2)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchType(Type type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchKeyword(String keyword)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
