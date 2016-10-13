package org.ssa.ironyard.liquorstore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.Customer.Builder;

public class Admin extends AbstractUser implements User
{
    private final String userName;
    private final Password password;
    private final String firstName;
    private final String lastName;
    private final Integer role;

    public Admin(Integer id, String username, Password password, String firstName, String lastName, Integer role,
            Boolean loaded)
    {
        super(id, loaded);
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Admin(Integer id, String username, Password password, String firstName, String lastName, Integer role)
    {
        this(id, username, password, firstName, lastName, role, false);
    }

    public Admin(String username, Password password, String firstName, String lastName, Integer role)
    {
        this(null, username, password, firstName, lastName, role, false);
    }

    public Admin(String username, Password password)
    {
        this(null, username, password, null, null, null, false);
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

    public Integer getRole()
    {
        return role;
    }

    public Boolean isAdmin()
    {
        return true;
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

        if (this.getId() == null)
        {
            if (other.getId() != null)
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

        if (this.role == null)
        {
            if (other.role != null)
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
    
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder
    {
        private Boolean loaded;
        private Integer id;
        private String firstName;
        private String lastName;
        private String userName;
        private Password password;
        private Integer role;

        public Builder()
        {
        }

        public Builder(Admin admin)
        {
            this.loaded = admin.isLoaded();
            this.id = admin.getId();
            this.firstName = admin.firstName;
            this.lastName = admin.lastName;
            this.password = admin.password;
            this.userName = admin.userName;
        }

        public Builder id(Integer id)
        {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public Builder password(Password password)
        {
            this.password = password;
            return this;
        }

        public Builder userName(String email)
        {
            this.userName = email;
            return this;
        }

        public Builder loaded(Boolean loaded)
        {
            this.loaded = loaded;
            return this;
        }

        public Admin build()
        {
            return new Admin(this.id, this.userName, this.password, this.firstName, this.lastName, this.role, this.loaded);
        }
    }

}
