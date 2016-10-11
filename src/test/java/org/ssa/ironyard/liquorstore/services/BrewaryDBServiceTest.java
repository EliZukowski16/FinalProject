package org.ssa.ironyard.liquorstore.services;

import org.junit.Test;

public class BrewaryDBServiceTest
{
    
    BrewaryDBService bdbService = new BrewaryDBService();

    @Test
    public void test() 
    {
        JSONObject jsonObj = new JSONObject(bdbService.getBeers("budweiser"));
        
        String value = jsonObj.getString("data");
        
        
        System.out.println(value);
    }

}
