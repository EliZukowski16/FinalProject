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
        this.fields.add("subType");
        this.fields.add("description");
    }

    @Override
    public String table()
    {
        return "coreProduct";
    }

    @Override
    public CoreProduct map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt("coreProduct.id");
        String name = results.getString("coreProduct.getName");
        Type type = Type.getInstance(results.getString("coreProduct.type"));
        String subType = results.getString("coreProduct.subType");
        String description = results.getString("coreProduct.description");
        List<Tag> tags = new ArrayList<>();
        
        return new CoreProduct(id, name, tags, type, subType, description);
    }
    
    public String prepareInsertTag()
    {
        return " INSERT INTO product_tags (product_id, name) values (?, ?)";
        
    }
    
    private String joinProductTags()
    {
        return " JOIN productTags ON " + this.table() + "." + this.primaryKeys.get(0) + " = productTags.coreProducttId ";
    }

  

}
