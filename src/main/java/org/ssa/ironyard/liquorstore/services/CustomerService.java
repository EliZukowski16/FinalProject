package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.model.Customer;

@Service
public class CustomerService implements CustomerServiceInt
{
    DAOCustomer daoCust;
    
    @Autowired
    public CustomerService(DAOCustomer daoCust)
    {
         this.daoCust = daoCust;
    }

    @Override
    @Transactional
    public Customer readCustomer(Integer id)
    {
        return daoCust.read(id);
    }

    @Override
    @Transactional
    public List<Customer> readAllCustomers()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public Customer editCustomer(Customer customer)
    {
     
        Customer cust = new Customer(customer.getId(),customer.getUserName(),customer.getPassword(),customer.getFirstName(),customer.getLastName(),customer.getAddress(),customer.getBirthDate());
        return daoCust.update(cust);
    }

    @Override
    @Transactional
    public Customer addCustomer(Customer customer)
    {
        Customer cust = new Customer(customer.getId(),customer.getUserName(),customer.getPassword(),customer.getFirstName(),customer.getLastName(),customer.getAddress(),customer.getBirthDate());
        return daoCust.insert(cust);
    }

    @Override
    @Transactional
    public boolean deleteCustomer(Integer id)
    {

        return daoCust.delete(id);
    }

}
