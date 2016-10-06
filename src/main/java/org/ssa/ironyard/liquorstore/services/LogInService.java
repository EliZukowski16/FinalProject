package org.ssa.ironyard.liquorstore.services;

import org.ssa.ironyard.liquorstore.model.User;

public interface LogInService
{
    User checkAuthentication(String userName, String password);
}
