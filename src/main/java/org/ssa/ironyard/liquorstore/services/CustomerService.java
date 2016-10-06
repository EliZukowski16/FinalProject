package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.Customer;

public interface CustomerService
{
    Customer readCustomer(Integer id);
    List<Customer> readAllCustomers();
    Customer editCustomer(Customer customer);
    Customer addCustomer(Customer customer);
    boolean deleteCustomer(Integer id);
}
