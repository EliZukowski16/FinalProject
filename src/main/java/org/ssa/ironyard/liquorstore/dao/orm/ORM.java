package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public interface ORM<T extends DomainObject>
{
    static Logger LOGGER = LogManager.getLogger(ORM.class);
    
    String projection();
    
    String table();

    String prepareInsert();

    String prepareUpdate();

    default String prepareDelete()
    {
        String delete = " DELETE FROM " + table() + " WHERE id = ? ";
        
        LOGGER.debug(delete);
        
        return delete;
    }
    
    T map(ResultSet results) throws SQLException;

    List<String> getFields();

    List<String> getPrimaryKeys();

    Map<String, String> getForeignKeys();
    
    public default String prepareQuery(String queryField)
    {
        String query = this.prepareReadAll() + " WHERE " + queryField + " = ? ";
        
        LOGGER.debug(query);
        
        return query;
    }

    default String prepareReadAll()
    {
        String readAll = " SELECT " + this.projection() + " FROM " + table();
        
        LOGGER.debug(readAll);
        
        return readAll;
    }

    default String prepareRead()
    {
        String read = this.prepareReadAll() + " WHERE id = ? ";
        
        LOGGER.debug(read);
        
        return read;
    }

}
