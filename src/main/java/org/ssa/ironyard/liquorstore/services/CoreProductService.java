package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Product;

public interface CoreProductService
{
    CoreProduct readCoreProduct(Integer id);
    List<CoreProduct> readAllCoreProduct();
    CoreProduct editCoreProduct(CoreProduct coreproduct);
    CoreProduct addCoreProduct(CoreProduct coreproduct);
    boolean deleteCoreProduct(Integer id);
    List<Product> searchCoreProduct(Product product);
    List<Product> searchType(CoreProduct.Type cpTypea);
    List<Product> searchDateTimeFrame(LocalDate date1, LocalDate date2);
    
}
