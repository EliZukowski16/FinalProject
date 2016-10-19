package org.ssa.ironyard.liquorstore.dao;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;

public interface DAOProduct extends DAO<Product>
{
    public List<Product> searchProducts(List<Tag> tags, List<Type> types);
    public List<Product> readProductsByCoreProduct(Integer coreProductId);
    public List<Product> readProductsByCoreProducts(List<Integer> coreProductIDs);

    public List<Product> readByUnitAndQuantity(Product.BaseUnit baseUnit);
    public List<Product> readByUnitAndQuantity(Integer quantity);
    public List<Product> readByUnitAndQuantity(Product.BaseUnit baseUnit, Integer quantity);
    
    public List<Product> readTopSellersForPastMonth();
    public List<Product> readLowInventoryProducts(Integer numberOfProducts);
}
