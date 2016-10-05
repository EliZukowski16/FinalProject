
package org.ssa.ironyard.liquorstore.crypto;

import org.mindrot.jbcrypt.BCrypt;
import org.ssa.ironyard.liquorstore.model.Password;

/**
 *
 * @author thurston
 */
public class BCryptSecurePassword implements SecurePassword
{

    @Override
    public Password secureHash(String clearText)
    {
        String salt = BCrypt.gensalt(4);
        String aggregateHash = BCrypt.hashpw(clearText, salt);
        return new Password(salt, aggregateHash.substring(salt.length()));
    }

    @Override
    public boolean verify(String clearText, Password secure)
    {
        return BCrypt.checkpw(clearText, secure.combine());
    }

}