package org.ssa.ironyard.liquorstore.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    static Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

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
        return daoProd.readAll();
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
        if (daoProd.read(product.getId()) == null)
            return null;

        Product prod = new Product(product.getId(), product.getCoreProduct(), product.getBaseUnitType(),
                product.getQuantity(), product.getInventory(), product.getPrice());
        return daoProd.update(prod);
    }

    @Override
    @Transactional
    public Product addProduct(Product product)
    {
        Product prod = new Product(product.getCoreProduct(), product.getBaseUnitType(), product.getQuantity(),
                product.getInventory(), product.getPrice());
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
    public List<Product> searchProduct(List<Tag> tags, List<Type> types)
    {
        return daoProd.searchProducts(tags, types);
    }

    @Override
    public Map<String,List<Product>> topSellersForPastMonth()
    {
        Map<String, List<Product>> topSellersMap = new HashMap<>();
        
        List<Product> topBeer = new ArrayList<>();
        List<Product> topWine = new ArrayList<>();
        List<Product> topSpirits = new ArrayList<>();

        List<Product> topSellers = daoProd.readTopSellersForPastMonth();

        LOGGER.info("Top Sellers Service : {}", topSellers.size());
        
        for (int i = 0; i < topSellers.size(); i++)
        {
            LOGGER.info(topSellers.get(i).getBaseUnit());
            
            switch (topSellers.get(i).getCoreProduct().getType().toString())
            {
            case ("beer"):
            case ("ciders"):
                topBeer.add(topSellers.get(i));
                LOGGER.info("Added to beer list");
                break;
            case ("wine"):
                topWine.add(topSellers.get(i));
            LOGGER.info("Added to wine list");

                break;
            case ("spirits"):
                topSpirits.add(topSellers.get(i));
            LOGGER.info("Added to liqor list");

                break;
            default:
                break;
            }
        }
        
        topSellersMap.put("beer", topBeer);
        topSellersMap.put("wine", topWine);
        topSellersMap.put("spirits", topSpirits);

        LOGGER.info(topSellersMap);
        
        return topSellersMap;
    }

    
    @Override
    @Transactional
    public List<Product> readLowInventory()
    {
        List<Product> lowInventory = new ArrayList<>();
        
        lowInventory = daoProd.readLowInventoryProducts();
        
        lowInventory.sort((p1, p2) -> p1.getInventory().compareTo(p2.getInventory()));
        
        return lowInventory;
    }

}
