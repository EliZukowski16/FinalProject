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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOSales;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
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

    static Logger LOGGER = LogManager.getLogger(SalesServiceImpl.class);
    
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
    public List<TypeSalesData> aggregateDailySales()
    {
        return aggregateDailySales(LocalDate.now().minusDays(1));
    }

    @Override
    @Transactional
    public List<TypeSalesData> aggregateDailySales(LocalDate date)
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

        return this.createTypeFormattedSalesData(dailyAggregateSales);
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
    public List<TypeSalesData> readAllSales()
    {
        return createTypeFormattedSalesData(daoSales.readAll());
    }

    private List<ProductSalesData> createProductFormattedSalesData(List<Sales> dailySales)
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
    
    private List<TypeSalesData> createTypeFormattedSalesData(List<Sales> dailySales)
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

        return typeSales;
    }

    @Override
    public List<TypeSalesData> searchTimeFrame(LocalDate start, LocalDate end)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesInDateRange(start, end));
    }

    @Override
    public List<Sales> searchType(Type type)
    {
        
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TypeSalesData> readSalesForYesterday()
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForPreviousDay());
    }

    @Override
    public List<TypeSalesData> readSalesForYesterday(Integer productID)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForPreviousDay(productID));
    }

    @Override
    public List<TypeSalesData> readSalesForYesterday(List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForPreviousDay(productIDs));
    }

    @Override
    public List<TypeSalesData> readSalesForLast30Days()
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast30Days());
    }

    @Override
    public List<TypeSalesData> readSalesForLast30Days(Integer productID)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast30Days(productID));
    }

    @Override
    public List<TypeSalesData> readSalesForLast30Days(List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast30Days(productIDs));
    }
    
    @Override
    public List<TypeSalesData> readTopSellersForLast30Days(Integer numberOfProducts)
    {
        return this.createTypeFormattedSalesData(daoSales.readTopSellers(30, numberOfProducts));
    }

    @Override
    public List<TypeSalesData> readSalesForLast90Days()
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast90Days());
    }

    @Override
    public List<TypeSalesData> readSalesForLast90Days(Integer productID)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast90Days(productID));
    }

    @Override
    public List<TypeSalesData> readSalesForLast90Days(List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast90Days(productIDs));
    }

    @Override
    public List<TypeSalesData> readSalesForLast180Days()
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast180Days());
    }

    @Override
    public List<TypeSalesData> readSalesForLast180days(Integer productID)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast180Days(productID));
    }

    @Override
    public List<TypeSalesData> readSalesForLast180Days(List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLast180Days(productIDs));
    }

    @Override
    public List<TypeSalesData> readSalesForLastYear()
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLastYear());
    }

    @Override
    public List<TypeSalesData> readSalesForLastYear(Integer productID)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLastYear(productID));
    }

    @Override
    public List<TypeSalesData> readSalesForLastYear(List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLastYear(productIDs));
    }

    @Override
    public List<TypeSalesData> readSalesForPastNumberOfDays(Integer numberOfDays)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLastVariableDays(numberOfDays, new ArrayList<>()));
    }

    @Override
    public List<TypeSalesData> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs)
    {
        return this.createTypeFormattedSalesData(daoSales.readSalesForLastVariableDays(numberOfDays, productIDs));
    }

    @Override
    public List<ProductSalesData> searchProduct(Integer productID)
    {
        if (productID == null)
            return new ArrayList<>();

        List<ProductSalesData> productList = this.createProductFormattedSalesData(daoSales.readSalesForProduct(productID));
        
        
        
        return productList;
    }

    @Override
    public List<ProductSalesData> searchProduct(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();
            
        LOGGER.info("Product IDs: {}", productIDs);
        
        List<Sales> sales = daoSales.readSalesForProduct(productIDs);
        
        LOGGER.info("Sales : {}", sales);
        
        return this.createProductFormattedSalesData(sales);

    }
    
    @Override
    public List<ProductSalesData> searchProduct(List<Tag> tags, List<Type> types)
    {
        List<Tag> cleanedTags = tags.stream().filter(t -> !t.equals(null)).collect(Collectors.toList());
        List<Type> cleanedTypes = types.stream().filter(t -> !t.equals(null)).collect(Collectors.toList());
        
        
        
        List<ProductSalesData> productSales = createProductFormattedSalesData(daoSales.searchProducts(cleanedTags, cleanedTypes));
        
        if(productSales.size() > 500)
        {
            productSales =  productSales.subList(0, 500);
        }
        
        return productSales;
    }

    @Override
    public boolean searchKeyword(String keyword)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
