package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

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
     
        
        return daoProd.delete(id);
    }

    @Override
    public List<Product> searchUnitQty(BaseUnit baseUnit)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> searchUnitQty(Integer quanity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> searchUnitQty(BaseUnit baseUnit, Integer quanity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> searchProduct(List<Tag> tags, List<Type> type)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    

    
    
    

}
