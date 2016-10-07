package org.ssa.ironyard.liquorstore.model;

public class Admin extends AbstractUser implements User
{
    private final String userName;
    private final Password password;
    private final String firstName;
    private final String lastName;
    private final Integer role;

    public Admin(Integer id, String username, Password password, String firstName, String lastName, Integer role)
    {
        super(id);
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isAdmin = true;
    }
    
    public Admin(String username, Password password, String firstName, String lastName, Integer role)
    {
        this(null, username, password, firstName, lastName, role);
    }

    public Admin(String username, Password password)
    {
        this(null, username, password, null, null, null);
    }

    public String getUsername()
    {
        return userName;
    }

    public Password getPassword()
    {
        return password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public int getRole()
    {
        return role;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getId();

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        
        return true;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        
        if (firstName == null)
        {
            if (other.firstName != null)
                return false;
        }
        else if (!firstName.equals(other.firstName))
            return false;
        
        if(this.getId() == null)
        {
            if(other.getId() != null)
                return false;
        }
        else if (this.getId() != other.getId())
            return false;
        
        if (lastName == null)
        {
            if (other.lastName != null)
                return false;
        }
        else if (!lastName.equals(other.lastName))
            return false;
        
        if (password == null)
        {
            if (other.password != null)
                return false;
        }
        else if (!password.equals(other.password))
            return false;
        
        
        if(this.role == null)
        {
            if(other.role != null)
                return false;
        }
        else if (role != other.role)
            return false;
        
        if (userName == null)
        {
            if (other.userName != null)
                return false;
        }
        else if (!userName.equals(other.userName))
            return false;
        
        return true;
    }

    @Override
    public Admin clone()
    {
        Admin copy;
        copy = (Admin) super.clone();
        return copy;
    }

}
