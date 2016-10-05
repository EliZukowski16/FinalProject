package org.ssa.ironyard.liquorstore.services;

import org.apache.tomcat.util.buf.B2CConverter;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.User;

public class LogInService implements LogInServiceInt
{
    DAOCustomer daoCust;
    DAOAdmin    daoAdmin;

    @Override
    public User checkAuthentication(String userName, String password)
    {
        User u;
        Admin a;
        boolean check;
        
        if((u = daoCust.read(userName)) != null)
        {
            Customer cust = (Customer) u;
            check = new BCryptSecurePassword().verify(password,cust.getPassword());
            if(check == true)
                return cust; 
        }
        
        if((u = daoAdmin.read(userName)) != null)
        {
            Admin admin = (Admin) a;
            check = new BCryptSecurePassword().verify(password,admin.getPassword());
            if(check == false)
                return admin;
        }
        
        return null;
    }

}
