package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

public class ORMProductImpl extends AbstractORM<Product> implements ORM<Product>
{
    AbstractORM<CoreProduct> coreProductORM;
    
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
     
    
    public String prepareReadEager()
    {
        return " SELECT " + this.projection() + " " + coreProductORM.projection() + " FROM " + this.coreProductJoin() +
                " ON " + this.coreProductRelation() + " WHERE " + this.primaryKeys.get(0) + " = ? ";
    }
    
    private String coreProductJoin()
    {
        return this.table() + " JOIN " + coreProductORM.table();
    }
    
    private String coreProductRelation()
    {
        return this.foreignKeys.get(coreProductORM.table()) + " = " + coreProductORM.getPrimaryKeys().get(0);
    }

}
