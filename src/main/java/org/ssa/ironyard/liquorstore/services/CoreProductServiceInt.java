package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct;

public interface CoreProductServiceInt
{
    CoreProduct readCoreProduct(Integer id);
    List<CoreProduct> readAllCoreProduct();
    CoreProduct editCoreProduct(CoreProduct coreproduct);
    CoreProduct addCoreProduct(CoreProduct coreproduct);
    CoreProduct deleteCoreProduct(CoreProduct coreproduct);
}
