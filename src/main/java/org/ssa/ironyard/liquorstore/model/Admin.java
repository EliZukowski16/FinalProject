package org.ssa.ironyard.liquorstore.model;

public class Admin extends AbstractDomainObject implements DomainObject
{
    private final String userName;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer role;

    public Admin(Integer id, String username, String password, String firstName, String lastName, Integer role)
    {
        super(id);
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Admin(String username, String password)
    {
        this(null, username, password, null, null, null);
    }

    public String getUsername()
    {
        return userName;
    }

    public String getPassword()
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
        if (this.getId() != other.getId())
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
        if (role != other.role)
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
        try
        {
            copy = (Admin) super.clone();
            return copy;

        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
