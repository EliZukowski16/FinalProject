package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;
import org.springframework.jdbc.core.PreparedStatementCreator;
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
        Integer id = results.getInt(table() + ".id");
        String userName = results.getString(table() + ".username");
        String firstName = results.getString(table() + ".first_name");
        String lastName = results.getString(table() + ".last_name");
        Password password = new Password(results.getString(table() + ".salt"), results.getString(table() + ".hash"));
        LocalDateTime birthDate = results.getTimestamp(table() + ".birth_date").toLocalDateTime();
        
        Address address = new Address();
        address.setCity(results.getString(table() + ".city"));
        address.setZip(new ZipCode(results.getString(table() + ".zip_code")));
        address.setStreet(results.getString(table() + ".street"));
        address.setState(State.getInstance(results.getString(table() + ".state")));
        
        Customer customer = new Customer(id, userName, password, firstName, lastName, address, birthDate);
        
        customer.setLoaded(true);
        
        return customer;
    }

    public String prepareReadByUserName()
    {
        return this.prepareQuery("username");
    }

}
