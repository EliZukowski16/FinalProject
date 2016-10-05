package org.ssa.ironyard.liquorstore.services;

import org.apache.tomcat.util.buf.B2CConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOAdminImpl;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOCustomerImpl;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.User;

public class LogInService implements LogInServiceInt
{
    DAOAdminImpl daoAdmin;
    DAOCustomerImpl daoCust;
    
    @Autowired
    public LogInService(DAOAdminImpl daoAdmin,DAOCustomerImpl daoCust)
    {
        this.daoAdmin = daoAdmin;
        this.daoCust = daoCust;
    }

    @Override
    public User checkAuthentication(String userName, String password)
    {
        User u =null;
        Admin a = null;
        boolean check;
        
        if((u = daoCust.readByUserName(userName)) != null)
        {
            Customer cust = (Customer) u;
            check = new BCryptSecurePassword().verify(password,cust.getPassword());
            if(check == true)
                return cust; 
        }
        
        if((u = daoAdmin.readByUserName(userName)) != null)
        {
            Admin admin = (Admin) a;
            check = new BCryptSecurePassword().verify(password,admin.getPassword());
            if(check == false)
                return admin;
        }
        
        return null;
    }

}
