package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public interface ORM<T extends DomainObject>
{
    String projection();
    
    String table();

    String prepareInsert();

    String prepareUpdate();

    default String prepareDelete()
    {
        return " DELETE FROM " + table() + " WHERE id = ? ";
    }
    
    T map(ResultSet results) throws SQLException;

    List<String> getFields();

    List<String> getPrimaryKeys();

    Map<String, String> getForeignKeys();
    
    public default String prepareQuery(String queryField)
    {
        return this.prepareReadAll() + " " + queryField + " = ? ";
    }

    default String prepareReadAll()
    {
        return " SELECT " + this.projection() + " FROM " + table();
    }

    default String prepareRead()
    {
        return this.prepareReadAll() + " WHERE id = ? ";
    }

}
