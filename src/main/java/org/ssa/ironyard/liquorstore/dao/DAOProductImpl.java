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
        insertStatement.setInt(1, domainToInsert.getCoreProduct().getId());
        insertStatement.setString(2, domainToInsert.getBaseUnit().toString());
        insertStatement.setInt(3, domainToInsert.getQuantity());
        insertStatement.setInt(4, domainToInsert.getInventory());
        insertStatement.setBigDecimal(5, domainToInsert.getPrice());

    }

    @Override
    protected Product afterInsert(Product copy, Integer id)
    {
        Product product;
        product = new Product(id, copy.getCoreProduct(), copy.getBaseUnit(), copy.getQuantity(), copy.getInventory(),
                copy.getPrice());
        product.setLoaded(true);

        return product;

    }

    @Override
    protected Product afterUpdate(Product copy)
    {
        copy.setLoaded(true);

        return copy;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Product domainToUpdate)
    {
        return new PreparedStatementSetter()
        {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setInt(1, domainToUpdate.getCoreProduct().getId());
                ps.setString(2, domainToUpdate.getBaseUnit().toString());
                ps.setInt(3, domainToUpdate.getQuantity());
                ps.setInt(4, domainToUpdate.getInventory());
                ps.setBigDecimal(5, domainToUpdate.getPrice());
                
            }

        };

    }

}
