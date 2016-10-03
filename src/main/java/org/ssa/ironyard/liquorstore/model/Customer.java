package org.ssa.ironyard.liquorstore.model;

import java.time.LocalDateTime;

public class Customer implements DomainObject
{
    private final Integer id;
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

    public Customer(Integer id, String firstName, String lastName, String userName, String password, Address address,
            LocalDateTime birthDate)
    {
        this.id = id;
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
    public Integer getId()
    {
        return id;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    public static class Address
    {
        final String streetNumber;
        final String streetName;
        final String apptNumber;
        final String city;
        final String state;
        final String zipCode;

        public Address(String streetNumber, String streetName, String apptNumber, String city, String state,
                String zipCode)
        {
            this.streetNumber = streetNumber;
            this.streetName = streetName;
            this.apptNumber = apptNumber;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }

        public String getStreetNumber()
        {
            return streetNumber;
        }

        public String getStreetName()
        {
            return streetName;
        }

        public String getApptNumber()
        {
            return apptNumber;
        }

        public String getCity()
        {
            return city;
        }

        public String getState()
        {
            return state;
        }

        public String getZipCode()
        {
            return zipCode;
        }

    }

}
