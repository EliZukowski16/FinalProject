package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Password;

public class ORMAdminImpl extends AbstractORM<Admin> implements ORM<Admin>
{
    public ORMAdminImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("username");
        this.fields.add("salt");
        this.fields.add("hash");
        this.fields.add("first_name");
        this.fields.add("last_name");
        this.fields.add("role");
    }
    
    public String prepareReadByUserName()
    {
        return this.prepareQuery("username");
    }

    @Override
    public String table()
    {
        return "admin";
    }

    @Override
    public Admin map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(table() + ".id");
        String userName = results.getString(table() + ".username");
        Password password = new Password(results.getString(table() + ".salt"), results.getString(table() + ".hash"));
        String firstName = results.getString(table() + ".first_name");
        String lastName = results.getString(table() + ".last_name");
        Integer role = results.getInt(table() + ".role");
        
        Admin admin = new Admin(id, userName, password, firstName, lastName, role);
        
        admin.setLoaded(true);
        
        return admin;
    }

}
