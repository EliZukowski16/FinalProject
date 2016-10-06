package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.Product;

@Service
public class ProductServiceImpl implements ProductService
{
    
    DAOProduct daoProd;
    
    @Autowired
    public ProductServiceImpl(DAOProduct daoProd)
    {
        this.daoProd = daoProd;
    }

    @Override
    @Transactional
    public Product readProduct(Integer id)
    {
        return daoProd.read(id);
    }

    @Override
    @Transactional
    public List<Product> readAllProducts()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<Product> readAllProductsByCoreProduct(Integer coreProductId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public Product editProduct(Product product)
    {
        if(daoProd.read(product.getId()) == null)
            return null;
        
        Product prod = new Product(product.getId(),product.getCoreProduct(),product.getBaseUnit(),product.getQuantity(),product.getInventory(),product.getPrice());
        return daoProd.update(prod);
    }

    @Override
    @Transactional
    public Product addProduct(Product product)
    {
        Product prod = new Product(product.getCoreProduct(),product.getBaseUnit(),product.getQuantity(),product.getInventory(),product.getPrice());
        return daoProd.insert(prod);
    }

    @Override
    @Transactional
    public boolean deleteProduct(Integer id)
    {
        if(daoProd.read(id) == null)
            return false;
        
        return daoProd.delete(id);
    }

}
