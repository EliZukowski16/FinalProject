package org.ssa.ironyard.liquorstore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.dao.orm.ORMProductImpl;
import org.ssa.ironyard.liquorstore.model.Product;

public class DAOProductImpl extends AbstractDAOProduct implements DAOProduct
{

    protected DAOProductImpl(DataSource dataSource)
    {
        super(new ORMProductImpl(), dataSource);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Product domainToInsert) throws SQLException
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected Product afterInsert(Product copy, Integer id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Product afterUpdate(Product copy)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Product domainToUpdate)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
