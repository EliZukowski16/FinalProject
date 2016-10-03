package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.ssa.ironyard.liquorstore.model.Product;

@Component
public class ProductService implements ProductServiceInt
{

    @Override
    public Product readProduct(Integer id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> readAllProducts()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> readAllProductsByCoreProduct(Integer coreProductId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product editProduct(Product product)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product addProduct(Product product)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product deleteProduct(Integer id)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
