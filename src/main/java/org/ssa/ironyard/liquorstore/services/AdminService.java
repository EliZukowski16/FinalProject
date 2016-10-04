package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.model.Admin;

@Component
public class AdminService implements AdminServiceInt
{
    
    DAOAdmin daoAdmin;
    
    @Autowired
    public AdminService(DAOAdmin daoAdmin)
    {
        this.daoAdmin = daoAdmin;
    }

    @Override
    @Transactional
    public Admin readAdmin(Integer id)
    {
        return daoAdmin.read(id);
        
    }

    @Override
    @Transactional
    public List<Admin> readAllAdmins()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public Admin editAdmin(Admin admin)
    {
        if(daoAdmin.read(admin.getId()) == null)
            return null;
        
        Admin ad = new Admin(admin.getId(),admin.getUsername(),admin.getPassword(),admin.getFirstName(),admin.getLastName(),admin.getRole());
        return daoAdmin.update(ad);
    }

    @Override
    @Transactional
    public Admin addAdmin(Admin admin)
    {
        Admin ad = new Admin(admin.getId(),admin.getUsername(),admin.getPassword(),admin.getFirstName(),admin.getLastName(),admin.getRole());
        return daoAdmin.insert(admin);
    }

    @Override
    @Transactional
    public boolean deleteAdmin(Integer id)
    {
        if(daoAdmin.read(id) == null)
            return false;
        return daoAdmin.delete(id);
    }

}
