package org.ssa.ironyard.liquorstore.model;

import java.util.Objects;

/**
 *
 * @author thurston
 */
public class Password 
{
    final String salt, hash;

    public Password(String salt, String hash)
    {
        this.salt = salt;
        this.hash = hash;
    }

    public String getSalt()
    {
        return this.salt;
    }

    public String getHash()
    {
        return this.hash;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.salt);
        hash = 97 * hash + Objects.hashCode(this.hash);
        return hash;
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
        final Password other = (Password) obj;
        if (! Objects.equals(this.salt, other.salt))
            return false;
        if (! Objects.equals(this.hash, other.hash))
            return false;
        return true;
    }
    
    /**
     *
     * @return a String suitable for input into a password hash function
     */
    public String combine()
    {
        return this.salt + this.hash;
    }

    @Override
    public String toString()
    {
        int total = this.hash.length() + this.salt.length();
        StringBuilder builder = new StringBuilder(total);
        while (total-- > 0)
            builder.append('*');
        return builder.toString();
    }
    
    
}
