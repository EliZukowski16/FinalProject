package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

public class ORMProductImpl extends AbstractORM<Product> implements ORM<Product>
{
    AbstractORM<CoreProduct> coreProductORM;
    
    static Logger LOGGER = LogManager.getLogger(ORMProductImpl.class);
    
    public ORMProductImpl()
    {
        coreProductORM = new ORMCoreProductImpl();
        
        this.primaryKeys.add("id");
        
        this.fields.add("core_product_id");
        this.fields.add("base_unit");
        this.fields.add("quantity");
        this.fields.add("inventory");
        this.fields.add("price");
        
        this.foreignKeys.put("core_product", "core_product_id");
    }

    @Override
    public String table()
    {
        return "product";
    }

    @Override
    public Product map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(table() + ".id");
        BaseUnit baseUnit = BaseUnit.getInstance(results.getString(table() + ".base_unit"));
        Integer quantity = results.getInt(table() + ".quantity");
        Integer inventory = results.getInt(table() + ".inventory");
        BigDecimal price = results.getBigDecimal(table() + ".price");
        CoreProduct coreProduct = this.mapCoreProduct(results);
        Product product = new Product(id, coreProduct, baseUnit, quantity, inventory, price);
        
        product.setLoaded(true);
        
        return product;
        
    }
    
    private CoreProduct mapCoreProduct(ResultSet results) throws SQLException
    {
        return coreProductORM.map(results);
    }
     
    @Override
    public String prepareRead()
    {
        String ps = " SELECT " + this.projection() + " , " + coreProductORM.projection() + " FROM " + this.coreProductJoin() +
                " ON " + this.coreProductRelation() + " WHERE " + this.table() +"." + this.primaryKeys.get(0) + " = ? ";
        
        LOGGER.debug(ps);
        
        return ps;
    }
    
    private String coreProductJoin()
    {
        return this.table() + " JOIN " + coreProductORM.table();
    }
    
    private String coreProductRelation()
    {
        return this.table() + "." + this.foreignKeys.get(coreProductORM.table()) + " = " + coreProductORM.table() + "." + coreProductORM.getPrimaryKeys().get(0);
    }

}
