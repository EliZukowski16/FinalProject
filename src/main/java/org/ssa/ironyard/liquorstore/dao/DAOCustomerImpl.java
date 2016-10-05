package org.ssa.ironyard.liquorstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.ssa.ironyard.liquorstore.dao.orm.ORMAdminImpl;
import org.ssa.ironyard.liquorstore.dao.orm.ORMCustomerImpl;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Customer;

@Repository
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
        insertStatement.setString(2, domainToInsert.getPassword().getSalt());
        insertStatement.setString(3, domainToInsert.getPassword().getHash());
        insertStatement.setString(4, domainToInsert.getFirstName());
        insertStatement.setString(5, domainToInsert.getLastName());
        insertStatement.setString(6, domainToInsert.getAddress().getStreet());
        insertStatement.setString(7, domainToInsert.getAddress().getCity());
        insertStatement.setString(8, domainToInsert.getAddress().getState().getAbbreviation());
        insertStatement.setString(9, domainToInsert.getAddress().getZip().datafy());
        insertStatement.setTimestamp(10, Timestamp.valueOf(domainToInsert.getBirthDate()));
    }

    @Override
    protected Customer afterInsert(Customer copy, Integer id)
    {
        Customer customer;
        customer = new Customer(id, copy.getUserName(), copy.getPassword(), copy.getFirstName(), copy.getLastName(), 
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
                ps.setString(2, domainToUpdate.getPassword().getSalt());
                ps.setString(3, domainToUpdate.getPassword().getHash());
                ps.setString(4, domainToUpdate.getFirstName());
                ps.setString(5, domainToUpdate.getLastName());
                ps.setString(6, domainToUpdate.getAddress().getStreet());
                ps.setString(7, domainToUpdate.getAddress().getCity());
                ps.setString(8, domainToUpdate.getAddress().getState().getAbbreviation());
                ps.setString(9, domainToUpdate.getAddress().getZip().datafy());
                ps.setTimestamp(10, Timestamp.valueOf(domainToUpdate.getBirthDate()));
                ps.setInt(11, domainToUpdate.getId());
            }
        };
    }
    
    public Customer readByUserName(String username)
    {
        if (null == username)
            return null;
        return this.springTemplate.query(((ORMCustomerImpl) this.orm).prepareReadByUserName(), (PreparedStatement ps) -> ps.setString(1, username),
                (ResultSet cursor) ->
                {
                    if (cursor.next())
                    {
                        return this.orm.map(cursor);
                    }
                    return null;
                });
    }

}
