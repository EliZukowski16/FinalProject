package org.ssa.ironyard.liquorstore.dao.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ssa.ironyard.liquorstore.model.DomainObject;

public abstract class AbstractORM<T extends DomainObject> implements ORM<T>
{
    protected final List<String> fields;
    protected final List<String> primaryKeys;
    protected final Map<String, String> foreignKeys;

    public AbstractORM()
    {
        fields = new ArrayList<>();
        primaryKeys = new ArrayList<>();
        foreignKeys = new HashMap<>();
    }
    
    @Override
    public List<String> getFields()
    {
        return fields;
    }
    
    @Override
    public void addField(String field)
    {
        fields.add(field);
    }

    @Override
    public List<String> getPrimaryKeys()
    {
        return primaryKeys;
    }
    
    @Override
    public void addPrimaryKey(String primaryKey)
    {
        primaryKeys.add(primaryKey);
    }

    @Override
    public Map<String, String> getForeignKeys()
    {
        return foreignKeys;
    }
    
    @Override
    public void addForeignKey(String foreignKeyTable, String foreignKeyName)
    {
        foreignKeys.put(foreignKeyTable, foreignKeyName);
    }
    
    @Override
    public String prepareUpdate()
    {
        String fieldNames = " SET ";
        for(int i = 0; i < this.fields.size(); i++)
        {
            fieldNames += (this.fields.get(i) + " = ?, ");
        }
        
        fieldNames = fieldNames.substring(0, fieldNames.length() - 2);
        
        return " UPDATE " + this.table() + fieldNames + " WHERE id = ? ";
    }
    
    @Override
    public String prepareInsert()
    {
        String fieldNames = " ( ";
        String values = " VALUES ( ";
        for(int i = 0; i <this.fields.size(); i++)
        {
            fieldNames += (this.fields.get(i)) + ", ";
            values += "?, ";
        }
        
        fieldNames = fieldNames.substring(0, fieldNames.length() - 2);
        values = values.substring(0, values.length() - 2);
        
        fieldNames += " ) ";
        values += " ) ";
        
        return " INSERT INTO " + this.table() + fieldNames + values;
    }

    
}
