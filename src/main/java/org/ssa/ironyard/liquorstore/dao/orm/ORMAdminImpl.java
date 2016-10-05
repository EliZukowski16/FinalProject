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
    
    public String prepareReadUser()
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
        Integer id = results.getInt("admin.id");
        String userName = results.getString("admin.username");
        Password password = new Password(results.getString("admin.salt"), results.getString("admin.hash"));
        String firstName = results.getString("admin.first_name");
        String lastName = results.getString("admin.last_name");
        Integer role = results.getInt("admin.role");
        
        return new Admin(id, userName, password, firstName, lastName, role);
    }

}
