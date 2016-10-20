package org.ssa.ironyard.liquorstore.dao.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public abstract class AbstractORM<T extends DomainObject> implements ORM<T>
{
    static Logger LOGGER = LogManager.getLogger(AbstractORM.class);
    
    protected final List<String> fields;
    protected final List<String> primaryKeys;
    protected final Map<String, String> foreignKeys;

    public AbstractORM()
    {
        foreignKeys = new HashMap<>();
        fields = new ArrayList<>();
        primaryKeys = new ArrayList<>();
    }
    
    @Override
    public String projection()
    {
        String projection = "";
        
        for (int i = 0; i < primaryKeys.size(); i++)
        {
            projection = projection + " " + table() + "." + this.primaryKeys.get(i) + ", ";
        }
        
        for(int i = 0; i < fields.size(); i++)
        {
            projection = projection + " " + table() + "." + this.fields.get(i) + ", ";
        }
        
        projection = projection.substring(0, projection.length() - 2);
        
        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Projection: {}", projection);
        
        return projection;
    }
    
    @Override
    public List<String> getFields()
    {
        return fields;
    }
    
    @Override
    public List<String> getPrimaryKeys()
    {
        return primaryKeys;
    }

    @Override
    public Map<String, String> getForeignKeys()
    {
        return foreignKeys;
    }
    
    @Override
    public String prepareUpdate()
    {
        String fieldNames = " SET ";
        for(int i = 0; i < this.fields.size(); i++)
        {
            fieldNames += (this.fields.get(i)) + " = ?, ";
        }
        
        fieldNames = fieldNames.substring(0, fieldNames.length() - 2);
        
        String update = " UPDATE " + table() + fieldNames + " WHERE id = ? ";
        
        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Update prepared Statement: {}", update);
        
        return update;
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
        
        String insert = " INSERT INTO " + table() + fieldNames + values;
        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Insert prepared Statement: {}", insert);
        
        return insert;
    }

    
}
