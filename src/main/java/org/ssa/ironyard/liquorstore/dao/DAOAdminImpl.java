package org.ssa.ironyard.liquorstore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.dao.orm.ORMAdminImpl;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.DomainObject;

public class DAOAdminImpl extends AbstractDAOAdmin implements DAOAdmin
{

    protected DAOAdminImpl(DataSource dataSource)
    {
        super(new ORMAdminImpl(), dataSource);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Admin domainToInsert) throws SQLException
    {
        insertStatement.setString(1, domainToInsert.getUsername());
        insertStatement.setString(2, domainToInsert.getPassword().getSalt());
        insertStatement.setString(3, domainToInsert.getPassword().getHash());
        insertStatement.setString(4, domainToInsert.getFirstName());
        insertStatement.setString(5, domainToInsert.getLastName());
        insertStatement.setInt(6, domainToInsert.getRole());
    }

    @Override
    protected Admin afterInsert(Admin copy, Integer id)
    {
        Admin admin = new Admin(id, copy.getUsername(), copy.getPassword(), copy.getFirstName(), copy.getLastName(), copy.getRole());
        admin.setLoaded(true);
        
        return admin;
    }

    @Override
    protected Admin afterUpdate(Admin copy)
    {
        copy.setLoaded(true);
        
        return copy;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Admin domainToUpdate)
    {
        return new PreparedStatementSetter()
        {
            
            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setString(1, domainToUpdate.getUsername());
                ps.setString(2, domainToUpdate.getPassword().getSalt());
                ps.setString(3, domainToUpdate.getPassword().getHash());
                ps.setString(4, domainToUpdate.getFirstName());
                ps.setString(5, domainToUpdate.getLastName());
                ps.setInt(6, domainToUpdate.getRole());
                ps.setInt(7, domainToUpdate.getId());
            }
        };
    }
}
