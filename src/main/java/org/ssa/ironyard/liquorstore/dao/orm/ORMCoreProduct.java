package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

public class ORMCoreProduct extends AbstractORM<CoreProduct> implements ORM<CoreProduct>
{    
    private final AbstractORM<Tag> tagORM;
    
    ORMCoreProduct()
    {
        this.primaryKeys.add("id");

        this.fields.add("name");
        this.fields.add("type");
        this.fields.add("subType");
        this.fields.add("description");
        
        tagORM = new ORMTag();
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
        
        return new CoreProduct(id, name, null, type, subType, description);
    }
    
    private String joinProductTags()
    {
        return " JOIN productTags ON " + this.table() + "." + this.primaryKeys.get(0) + " = productTags.coreProducttId ";
    }
    
    private String joinTags()
    {
        return " JOIN " + tagORM.table() + " ON " + tagORM.table() + "." + tagORM.getPrimaryKeys().get(0) + " productTags.tags ";
    }
    
    @Override
    public String prepareRead()
    {
        return " SELECT " + this.projection() + " , " + tagORM.projection() + " FROM " + this.table() +
                " " + this.joinTags() + " " + this.joinProductTags() + " WHERE " + this.table() + "." + 
                this.getPrimaryKeys().get(0) + " = ? ";
    }

}
