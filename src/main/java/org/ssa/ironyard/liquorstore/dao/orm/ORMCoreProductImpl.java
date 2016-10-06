package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

public class ORMCoreProductImpl extends AbstractORM<CoreProduct> implements ORM<CoreProduct>
{    
    public ORMCoreProductImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("name");
        this.fields.add("type");
        this.fields.add("subtype");
        this.fields.add("description");
    }

    @Override
    public String table()
    {
        return "core_product";
    }

    @Override
    public CoreProduct map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(table() + ".id");
        String name = results.getString(table() + ".name");
        Type type = Type.getInstance(results.getString(table() + ".type"));
        String subType = results.getString(table() + ".subtype");
        String description = results.getString(table() + ".description");
        List<Tag> tags = new ArrayList<>();
        
        CoreProduct coreProduct = new CoreProduct(id, name, tags, type, subType, description);
        
        coreProduct.setLoaded(true);
        
        return coreProduct;
    }
    
    public String prepareInsertTag()
    {
        return " INSERT INTO product_tags (product_id, name) values (?, ?)";
        
    }
    
    private String joinProductTags()
    {
        return " JOIN product_tags ON " + this.table() + "." + this.primaryKeys.get(0) + " = product_tags.core_product_id ";
    }

  

}
