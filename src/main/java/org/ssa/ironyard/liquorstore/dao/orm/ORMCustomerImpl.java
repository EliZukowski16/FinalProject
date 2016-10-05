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

        this.fields.add("userName");
        this.fields.add("salt");
        this.fields.add("hash");
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
        Password password = new Password(results.getString("customer.salt"), results.getString("customer.hash"));
        LocalDateTime birthDate = results.getTimestamp("customer.birthDate").toLocalDateTime();
        
        Address address = new Address();
        address.setCity(results.getString("customer.city"));
        address.setZip(new ZipCode(results.getString("customer.zipCode")));
        address.setStreet(results.getString("customer.street"));
        address.setState(State.getInstance(results.getString("customer.state")));
        
        return new Customer(id, firstName, lastName, userName, password, address, birthDate);
    }

}
