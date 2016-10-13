package org.ssa.ironyard.liquorstore.services;


import org.springframework.web.client.RestTemplate;


public class BrewaryDBService
{
    public String getBeers(String search) 
    {
        
        final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
        
        
        
        
        final String uri2 = "https://lcboapi.com/products?access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
       
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri2, String.class);
        
        return result;
    }
}
