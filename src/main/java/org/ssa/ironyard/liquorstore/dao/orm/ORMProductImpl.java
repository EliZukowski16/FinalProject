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
        Integer id = results.getInt("product.id");
        BaseUnit baseUnit = BaseUnit.getInstance(results.getString("product.base_unit"));
        Integer quantity = results.getInt("product.quantity");
        Integer inventory = results.getInt("product.inventory");
        BigDecimal price = results.getBigDecimal("product.price");
        CoreProduct coreProduct = this.mapCoreProduct(results);
        
        return new Product(id, coreProduct, baseUnit, quantity, inventory, price);
        
    }
    
    private CoreProduct mapCoreProduct(ResultSet results) throws SQLException
    {
        return coreProductORM.map(results);
    }

}
