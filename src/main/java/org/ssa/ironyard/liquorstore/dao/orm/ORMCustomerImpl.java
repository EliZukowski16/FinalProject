package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Customer.Address;

public class ORMCustomerImpl extends AbstractORM<Customer> implements ORM<Customer>
{
    public ORMCustomerImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("userName");
        this.fields.add("password");
        this.fields.add("firstName");
        this.fields.add("lastName");
        this.fields.add("address");
        this.fields.add("birthDate");
    }

    @Override
    public String table()
    {
        return "customer";
    }

    @Override
    public Customer map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt("customer.id");
        String userName = results.getString("customer.userName");
        String firstName = results.getString("customer.firstName");
        String lastName = results.getString("customer.lastName");
        String password = results.getString("password");
        LocalDateTime birthDate = results.getTimestamp("birthDate").toLocalDateTime();
        
        //TODO: Fix address modeling
        Address address = new Address(null, null, null, null, null, null);
        
        return new Customer(id, firstName, lastName, userName, password, address, birthDate);
    }

}
