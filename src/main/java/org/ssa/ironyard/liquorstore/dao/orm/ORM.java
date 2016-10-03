package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public interface ORM<T extends DomainObject>
{
    String projection();
    
    String table();
    
    PreparedStatementCreator prepareRead();

    String prepareInsert();

    String prepareUpdate();

    String prepareDelete();
    
    T map(ResultSet results);

}
