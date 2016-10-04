package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;

public class ORMCustomerImpl extends AbstractORM<Customer> implements ORM<Customer>
{
    public ORMCustomerImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("userName");
        this.fields.add("password");
        this.fields.add("firstName");
        this.fields.add("lastName");
        this.fields.add("street");
        this.fields.add("city");
        this.fields.add("state");
        this.fields.add("zipCode");
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
        
        Address address = new Address();
        address.setCity(results.getString("city"));
        address.setZip(new ZipCode(results.getString("zipCode")));
        address.setStreet(results.getString("street"));
        address.setState(State.getInstance(results.getString("state")));
        
        return new Customer(id, firstName, lastName, userName, password, address, birthDate);
    }

}
