package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.Product;

public interface ProductService
{
    Product readProduct(Integer id);
    List<Product> readAllProducts();
    List<Product> readAllProductsByCoreProduct(Integer coreProductId);
    Product editProduct(Product product);
    Product addProduct(Product product);
    boolean deleteProduct(Integer id);
    
}
