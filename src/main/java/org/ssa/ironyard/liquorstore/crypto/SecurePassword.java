package org.ssa.ironyard.liquorstore.crypto;

import org.ssa.ironyard.liquorstore.model.Password;

/**
 *
 * @author thurston
 */
public interface SecurePassword 
{

    /**
     *
     * @param clearText a raw password, un-hashed, un-encrypted
     * @return a Password suitable for secure persistent storage
     */
    Password secureHash(String clearText);
    
    /**
     * Does the <code>clearText</code> match the secure password?
     * @param clearText
     * @param secure
     * @return
     */
    boolean verify(String clearText, Password secure);
}
