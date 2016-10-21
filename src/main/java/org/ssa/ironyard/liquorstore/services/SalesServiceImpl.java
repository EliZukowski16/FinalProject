package org.ssa.ironyard.liquorstore.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOSales;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.SalesMonthly;
import org.ssa.ironyard.liquorstore.model.SalesWeekly;
import org.ssa.ironyard.liquorstore.model.salesdata.CoreProductSalesData;
import org.ssa.ironyard.liquorstore.model.salesdata.ProductSalesData;
import org.ssa.ironyard.liquorstore.model.salesdata.TypeSalesData;

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
    public List<ProductSalesData> aggregateDailySales()
    {
        return aggregateDailySales(LocalDate.now().minusDays(1));
    }

    @Override
    @Transactional
    public List<ProductSalesData> aggregateDailySales(LocalDate date)
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

        return this.createFormattedSalesData(dailyAggregateSales);
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
    public List<ProductSalesData> readAllSales()
    {
        return createFormattedSalesData(daoSales.readAll());
    }

    private List<ProductSalesData> createFormattedSalesData(List<Sales> dailySales)
    {
        List<TypeSalesData> typeSales = new ArrayList<>();
        List<CoreProductSalesData> allCoreProductSales = new ArrayList<>();
        List<ProductSalesData> allProductSales = new ArrayList<>();

        for (Type t : Type.values())
        {
            List<CoreProductSalesData> coreProductSales = new ArrayList<>();

            Set<CoreProduct> coreProducts = dailySales.stream()
                    .filter(s -> s.getProduct().getCoreProduct().getType().equals(t))
                    .map(s -> s.getProduct().getCoreProduct()).collect(Collectors.toSet());

            for (CoreProduct c : coreProducts)
            {
                Set<Product> products = dailySales.stream().filter(s -> s.getProduct().getCoreProduct().equals(c)).map(s -> s.getProduct())
                        .collect(Collectors.toSet());
                
                List<ProductSalesData> productSales = new ArrayList<>(); 
                
                for(Product p : products)
                {
                    List<Sales> sales = dailySales.stream().filter(s -> s.getProduct().equals(p))
                            .collect(Collectors.toList());
                    
                    productSales.add(new ProductSalesData(p, sales));
                }
                
                allProductSales.addAll(productSales);

                coreProductSales.add(new CoreProductSalesData(c, productSales));
            }
            
            allCoreProductSales.addAll(coreProductSales);

            typeSales.add(new TypeSalesData(t, coreProductSales));
        }

        return allProductSales;
    }

    @Override
    public List<ProductSalesData> searchTimeFrame(LocalDate start, LocalDate end)
    {
        return this.createFormattedSalesData(daoSales.readSalesInDateRange(start, end));
    }

    @Override
    public List<Sales> searchType(Type type)
    {
        
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductSalesData> readSalesForYesterday()
    {
        return this.createFormattedSalesData(daoSales.readSalesForPreviousDay());
    }

    @Override
    public List<ProductSalesData> readSalesForYesterday(Integer productID)
    {
        return this.createFormattedSalesData(daoSales.readSalesForPreviousDay(productID));
    }

    @Override
    public List<ProductSalesData> readSalesForYesterday(List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForPreviousDay(productIDs));
    }

    @Override
    public List<ProductSalesData> readSalesForLast30Days()
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast30Days());
    }

    @Override
    public List<ProductSalesData> readSalesForLast30Days(Integer productID)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast30Days(productID));
    }

    @Override
    public List<ProductSalesData> readSalesForLast30Days(List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast30Days(productIDs));
    }
    
    @Override
    public List<ProductSalesData> readTopSellersForLast30Days(Integer numberOfProducts)
    {
        return this.createFormattedSalesData(daoSales.readTopSellers(30, numberOfProducts));
    }

    @Override
    public List<ProductSalesData> readSalesForLast90Days()
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast90Days());
    }

    @Override
    public List<ProductSalesData> readSalesForLast90Days(Integer productID)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast90Days(productID));
    }

    @Override
    public List<ProductSalesData> readSalesForLast90Days(List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast90Days(productIDs));
    }

    @Override
    public List<ProductSalesData> readSalesForLast180Days()
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast180Days());
    }

    @Override
    public List<ProductSalesData> readSalesForLast180days(Integer productID)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast180Days(productID));
    }

    @Override
    public List<ProductSalesData> readSalesForLast180Days(List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLast180Days(productIDs));
    }

    @Override
    public List<ProductSalesData> readSalesForLastYear()
    {
        return this.createFormattedSalesData(daoSales.readSalesForLastYear());
    }

    @Override
    public List<ProductSalesData> readSalesForLastYear(Integer productID)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLastYear(productID));
    }

    @Override
    public List<ProductSalesData> readSalesForLastYear(List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLastYear(productIDs));
    }

    @Override
    public List<ProductSalesData> readSalesForPastNumberOfDays(Integer numberOfDays)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLastVariableDays(numberOfDays, new ArrayList<>()));
    }

    @Override
    public List<ProductSalesData> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs)
    {
        return this.createFormattedSalesData(daoSales.readSalesForLastVariableDays(numberOfDays, productIDs));
    }

    @Override
    public List<ProductSalesData> searchProduct(Integer productID)
    {
        if (productID == null)
            return new ArrayList<>();

        return this.createFormattedSalesData(daoSales.readSalesForProduct(productID));
    }

    @Override
    public List<ProductSalesData> searchProduct(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.createFormattedSalesData(daoSales.readSalesForProduct(productIDs));

    }

    @Override
    public boolean searchKeyword(String keyword)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
