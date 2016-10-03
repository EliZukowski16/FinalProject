package org.ssa.ironyard.liquorstore.model;

public class Admin implements DomainObject
{
    private final Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer role;
    
    
    public Admin(Integer id, String username, String password, String firstName, String lastName, Integer role)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    
    public Admin(String username, String password) 
    {
        this(null, username, password, null, null, null);
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + role;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        if(this.getId() == null)
        {
            if(other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id != other.id)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (role != other.role)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
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
            copy.setUsername(this.getUsername());
            copy.setPassword(this.getPassword());
            copy.setFirstName(this.getFirstName());
            copy.setLastName(this.getLastName());
            copy.setRole(this.getRole());
            return copy;
            
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

}
