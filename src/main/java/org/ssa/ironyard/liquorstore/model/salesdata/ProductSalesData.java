package org.ssa.ironyard.liquorstore.model.salesdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class ProductSalesData
{
    
    private final Product product;
    
    private final List<SalesData> productSales;
    
    public ProductSalesData(Product product, List<Sales> sales)
    {
        this.product = product;
        
        productSales = sales.stream().map(s -> new SalesData(((SalesDaily) s).getDateSold(), s.getNumberSold(), s.getTotalValue())).collect(Collectors.toList());
       
    }

    public Product getProduct()
    {
        return product;
    }

    public List<SalesData> getProductSales()
    {
        return productSales;
    }



    

}
