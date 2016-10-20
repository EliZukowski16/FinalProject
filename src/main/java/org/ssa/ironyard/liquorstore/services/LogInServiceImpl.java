package org.ssa.ironyard.liquorstore.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.User;

@Service
public class LogInServiceImpl implements LogInService
{
    DAOAdmin daoAdmin;
    DAOCustomer daoCust;
    
    static Logger LOGGER = LogManager.getLogger(LogInServiceImpl.class);
    
    @Autowired
    public LogInServiceImpl(DAOAdmin daoAdmin,DAOCustomer daoCust)
    {
        this.daoAdmin = daoAdmin;
        this.daoCust = daoCust;
    }

    @Override
    public User checkAuthentication(String userName, String password)
    {
        User u =null;

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
            LOGGER.info("in service" + u);
            Admin admin = (Admin) u;
            check = new BCryptSecurePassword().verify(password,admin.getPassword());
            LOGGER.info("check" + check);
            if(check == true)
                return admin;
        }
        
        return null;
    }

}
