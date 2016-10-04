package org.ssa.ironyard.liquorstore.model;

import java.time.LocalDateTime;

public class Customer extends AbstractDomainObject implements DomainObject
{
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String password;
    private Address address;
    private final LocalDateTime birthDate;

    public Customer(String userName, String password)
    {
        this(null, null, null, userName, password, null, null);
    }

    public Customer(String firstName, String lastName, String userName, String password, Address address,
            LocalDateTime birthDate)
    {
        this(null, firstName, lastName, userName, password, address, birthDate);
    }

    public Customer(Integer id, String firstName, String lastName, String userName, String password, Address address,
            LocalDateTime birthDate)
    {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public LocalDateTime getBirthDate()
    {
        return birthDate;
    }

    @Override
    public Customer clone()
    {
        try
        {
            Customer copy = (Customer) super.clone();
            copy.setAddress(this.getAddress());
            return copy;

        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
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
        Customer other = (Customer) obj;
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
        Customer other = (Customer) obj;
        if (address == null)
        {
            if (other.address != null)
                return false;
        }
        else if (!address.equals(other.address))
            return false;
        if (birthDate == null)
        {
            if (other.birthDate != null)
                return false;
        }
        else if (!birthDate.equals(other.birthDate))
            return false;
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
        else if (!this.getId().equals(other.getId()))
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
        if (userName == null)
        {
            if (other.userName != null)
                return false;
        }
        else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    
}
