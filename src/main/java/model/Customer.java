package model;

import java.time.LocalDateTime;

public class Customer implements DomainObject
{
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String password;
    private final Address address;
    private final LocalDateTime birthDate;
    
    
    
    

    @Override
    public Integer getId()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DomainObject clone()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
