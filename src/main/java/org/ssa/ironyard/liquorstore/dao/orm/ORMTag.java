package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;

public class ORMTag extends AbstractORM<Tag> implements ORM<Tag>
{
    
    public ORMTag()
    {
        this.primaryKeys.add("id");
        this.fields.add("tagName");
    }

    @Override
    public String table()
    {
        return "tags";
    }

    @Override
    public Tag map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt("tags.id");
        String tagName = results.getString("tags.name");
        
        return new Tag(id, tagName);
    }
    

}
