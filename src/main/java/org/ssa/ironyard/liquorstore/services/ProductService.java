package org.ssa.ironyard.liquorstore.services;

import java.util.List;
import java.util.Map;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;

public interface ProductService
{
    Product readProduct(Integer id);
    List<Product> readAllProducts();
    List<Product> readAllProductsByCoreProduct(Integer coreProductId);
    Product editProduct(Product product);
    Product addProduct(Product product);
    boolean deleteProduct(Integer id);
    List<Product> searchUnitQty(Product.BaseUnit baseUnit);
    List<Product> searchUnitQty(Integer quanity);
    List<Product> searchUnitQty(Product.BaseUnit baseUnit, Integer quanity);
    List<Product> searchProduct(List<Tag> tags,List<Type> type);
    Map<String, List<Product>> topSellersForPastMonth();
    
    
}
