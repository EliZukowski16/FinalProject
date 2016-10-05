package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;

public class ORMCustomerImpl extends AbstractORM<Customer> implements ORM<Customer>
{
    public ORMCustomerImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("username");
        this.fields.add("salt");
        this.fields.add("hash");
        this.fields.add("first_name");
        this.fields.add("last_name");
        this.fields.add("street");
        this.fields.add("city");
        this.fields.add("state");
        this.fields.add("zip_code");
        this.fields.add("birth_date");
    }

    @Override
    public String table()
    {
        return "customer";
    }
    
    public String prepareReadUser()
    {
        return this.prepareQuery("username");
    }

    @Override
    public Customer map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt("customer.id");
        String userName = results.getString("customer.username");
        String firstName = results.getString("customer.first_name");
        String lastName = results.getString("customer.last_name");
        Password password = new Password(results.getString("customer.salt"), results.getString("customer.hash"));
        LocalDateTime birthDate = results.getTimestamp("customer.birth_date").toLocalDateTime();
        
        Address address = new Address();
        address.setCity(results.getString("customer.city"));
        address.setZip(new ZipCode(results.getString("customer.zip_code")));
        address.setStreet(results.getString("customer.street"));
        address.setState(State.getInstance(results.getString("customer.state")));
        
        Customer customer = new Customer(id, userName, password, firstName, lastName, address, birthDate);
        
        customer.setLoaded(true);
        
        return customer;
    }

}
