package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.Admin;

public interface AdminServiceInt
{
    Admin readAdmin(Integer id);
    List<Admin> readAllAdmins();
    Admin editAdmin(Admin admin);
    Admin addAdmin(Admin admin);
    Admin deleteAdmin(Integer id);
}
