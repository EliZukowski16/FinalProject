package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public interface ORM<T extends DomainObject>
{
    String projection();
    
    String table();
    
    String prepareRead();

    String prepareInsert();

    String prepareUpdate();

    default String prepareDelete()
    {
        return " DELETE FROM " + table() + " WHERE id = ? ";
    }
    
    T map(ResultSet results);

    List<String> getFields();

    void addField(String field);

    List<String> getPrimaryKeys();

    void addPrimaryKey(String primaryKey);

    Map<String, String> getForeignKeys();

    void addForeignKey(String foreignKeyTable, String foreignKeyName);
    
    public default String prepareQuery(String queryField)
    {
        return " SELECT " + this.projection() + " FROM " + table() + " WHERE " + queryField + " = ? ";
    }

    default String prepareReadAll()
    {
        return " SELECT " + this.projection() + " FROM " + table();
    }

    default String prepareReadById()
    {
        return " SELECT " + projection() + " FROM " + table() + " WHERE id = ? ";
    }

}
