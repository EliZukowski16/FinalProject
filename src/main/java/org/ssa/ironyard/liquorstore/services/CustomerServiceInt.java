package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.Customer;

public interface CustomerServiceInt
{
    Customer readCustomer(Integer id);
    List<Customer> readAllCustomers();
    Customer editCustomer(Customer customer);
    Customer addCustomer(Customer customer);
    Customer deleteCustomer(Integer id);
}
