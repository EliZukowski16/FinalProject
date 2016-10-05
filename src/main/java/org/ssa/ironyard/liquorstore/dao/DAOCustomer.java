package org.ssa.ironyard.liquorstore.dao;

import org.ssa.ironyard.liquorstore.model.Customer;

public interface DAOCustomer extends DAO<Customer>
{

    public Customer readByUserName(String username);

}
