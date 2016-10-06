package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.Product;

public interface ProductService
{
    Product readProduct(Integer id);
    List<Product> readAllProducts();
    List<Product> readAllProductsByCoreProduct(Integer coreProductId);
    Product editProduct(Product product);
    Product addProduct(Product product);
    boolean deleteProduct(Integer id);
    List<Product> searchKeywordByTags(List<Tag> tag);
    List<Product> searchBaseUnit(Product.BaseUnit baseUnit);
    List<Product> searchQuanity(Integer quanity);
    List<Product> searchBaseUnitAndQuanity(Product.BaseUnit baseUnit, Integer quanity);
    
    
}
