package org.ssa.ironyard.liquorstore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.dao.orm.ORMCustomerImpl;
import org.ssa.ironyard.liquorstore.model.Customer;

public class DAOCustomerImpl extends AbstractDAOCustomer implements DAOCustomer
{

    protected DAOCustomerImpl(DataSource dataSource)
    {
        super(new ORMCustomerImpl(), dataSource);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Customer domainToInsert) throws SQLException
    {
        insertStatement.setString(1, domainToInsert.getUserName());
        insertStatement.setString(2, domainToInsert.getPassword());
        insertStatement.setString(3, domainToInsert.getFirstName());
        insertStatement.setString(4, domainToInsert.getLastName());
        insertStatement.setString(5, domainToInsert.getAddress().getStreet());
        insertStatement.setString(6, domainToInsert.getAddress().getState().getAbbreviation());
        insertStatement.setString(7, domainToInsert.getAddress().getZip().datafy());
        insertStatement.setTimestamp(8, Timestamp.valueOf(domainToInsert.getBirthDate()));
    }

    @Override
    protected Customer afterInsert(Customer copy, Integer id)
    {
        Customer customer;
        customer = new Customer(id, copy.getFirstName(), copy.getLastName(), copy.getUserName(), copy.getPassword(),
                copy.getAddress(), copy.getBirthDate());
        customer.setLoaded(true);

        return customer;
    }

    @Override
    protected Customer afterUpdate(Customer copy)
    {
        copy.setLoaded(true);
        
        return copy;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Customer domainToUpdate)
    {
        return new PreparedStatementSetter()
        {
            
            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setString(1, domainToUpdate.getUserName());
                ps.setString(2, domainToUpdate.getPassword());
                ps.setString(3, domainToUpdate.getFirstName());
                ps.setString(4, domainToUpdate.getLastName());
                ps.setString(5, domainToUpdate.getAddress().getStreet());
                ps.setString(6, domainToUpdate.getAddress().getState().getAbbreviation());
                ps.setString(7, domainToUpdate.getAddress().getZip().datafy());
                ps.setTimestamp(8, Timestamp.valueOf(domainToUpdate.getBirthDate()));
                ps.setInt(9, domainToUpdate.getId());
            }
        };
    }

}
