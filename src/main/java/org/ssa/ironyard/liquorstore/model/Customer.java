package org.ssa.ironyard.liquorstore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;

public class Customer extends AbstractUser implements User
{

    private final String firstName;
    private final String lastName;
    private final String userName;
    private final Password password;
    private Address address;
    private final LocalDateTime birthDate;
    
    public Customer(Integer id)
    {
        this(id, null, null, null, null, null, null, false);
    }

    
    public Customer(String userName, Password password)
    {
        this(null, userName, password, null, null, null, null, false);
    }

    public Customer(String userName, Password password, String firstName, String lastName, Address address,
                    LocalDateTime birthDate)
    {
        this(null, userName, password, firstName, lastName, address, birthDate, false);
    }
    
    public Customer(Integer id, String userName, Password password, String firstName, String lastName, Address address,
                    LocalDateTime birthDate)
    {
        this(id, userName, password, firstName, lastName, address, birthDate, false);
    }

    public Customer(Integer id, String userName, Password password, String firstName, String lastName, Address address,
                    LocalDateTime birthDate, Boolean loaded)
    {
        super(id, loaded);
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

    public Password getPassword()
    {
        return password;
    }

    public Address getAddress()
    {
        return null == this.address ? null : this.address.clone();
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public LocalDateTime getBirthDate()
    {
        return birthDate;
    }
    
    public Boolean isAdmin()
    {
        return false;
    }

    @Override
    public Customer clone()
    {
        Customer copy = (Customer) super.clone();
        copy.setAddress(getAddress());
        return copy;
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
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Customer other = (Customer) obj;

        if (this.getId() == null)
        {
            if (other.getId() != null)
            {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId()))
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Customer other = (Customer) obj;
        if (address == null)
        {
            if (other.address != null)
            {
                return false;
            }
        }
        else if (!address.equals(other.address))
        {
            return false;
        }

        if (birthDate == null)
        {
            if (other.birthDate != null)
            {
                return false;
            }
        }
        else if (!birthDate.equals(other.birthDate))
        {
            return false;
        }

        if (firstName == null)
        {
            if (other.firstName != null)
            {
                return false;
            }
        }
        else if (!firstName.equals(other.firstName))
        {
            return false;
        }

        if (this.getId() == null)
        {
            if (other.getId() != null)
            {
                return false;
            }
        }
        else if (!this.getId().equals(other.getId()))
        {
            return false;
        }

        if (lastName == null)
        {
            if (other.lastName != null)
            {
                return false;
            }
        }
        else if (!lastName.equals(other.lastName))
        {
            return false;
        }

        if (password == null)
        {
            if (other.password != null)
            {
                return false;
            }
        }
        else if (!password.equals(other.password))
        {
            return false;
        }

        if (userName == null)
        {
            if (other.userName != null)
            {
                return false;
            }
        }
        else if (!userName.equals(other.userName))
        {
            return false;
        }
        return true;
    }

    public Builder of()
    {
        return new Builder(this);
    }
    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private Boolean loaded;
        private Integer id;
        private String firstName;
        private String lastName;
        private String userName;
        private Password password;
        private Address address;
        private LocalDateTime birthDate;

        public Builder()
        {
        }
        
        public Builder(Customer customer)
        {
            this.loaded = customer.isLoaded();
            this.id = customer.getId();
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.address = customer.getAddress(); //because address is mutable
            this.birthDate = customer.birthDate;
            this.password = customer.password;
            this.userName = customer.userName;
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
        
        public Builder address(Address address)
        {
            this.address = address;
            return this;
        }
        
        public Builder street(String street)
        {
            this.address = null == this.address ? new Address() : this.address.clone();
            this.address.setStreet(street);
            return this;
        }
        
        public Builder city(String city)
        {
            this.address = null == this.address ? new Address() : this.address.clone();
            this.address.setCity(city);
            return this;
        }
        
        public Builder state(State state)
        {
            this.address = null == this.address ? new Address() : this.address.clone();
            this.address.setState(state);
            return this;
        }
        public Builder zip(ZipCode zip)
        {
            this.address = null == this.address ? new Address() : this.address.clone();
            this.address.setZip(zip);
            return this;
        }
        public Builder birth(LocalDateTime birth)
        {
            this.birthDate = birth;
            return this;
        }
        
        public Builder birth(LocalDate birth)
        {
            return birth(birth.atTime(0, 0, 0, 0));
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
        
        public Customer build()
        {
            return new Customer(this.id, this.userName, this.password, this.firstName, this.lastName, 
                                this.address, this.birthDate, this.loaded);
        }
    }

}
