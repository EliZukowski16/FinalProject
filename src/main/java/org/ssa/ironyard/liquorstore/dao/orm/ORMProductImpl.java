package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Product;

public class ORMProductImpl extends AbstractORM<Product> implements ORM<Product>
{
    AbstractORM<CoreProduct> coreProductORM;
    
    public ORMProductImpl()
    {
        coreProductORM = new ORMCoreProductImpl();
        
        this.primaryKeys.add("id");
        
        this.fields.add("coreProductID");
        this.fields.add("baseUnit");
        this.fields.add("quantity");
        this.fields.add("inventory");
        
        this.foreignKeys.put("coreProduct", "coreProductID");
    }

    @Override
    public String table()
    {
        return "product";
    }

    @Override
    public Product map(ResultSet results) throws SQLException
    {
        // TODO Auto-generated method stub
        return null;
    }

}
