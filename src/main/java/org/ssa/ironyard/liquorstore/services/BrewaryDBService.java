package org.ssa.ironyard.liquorstore.services;


import org.springframework.web.client.RestTemplate;


public class BrewaryDBService
{
    public String getBeers(String search) 
    {
        
        final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
        
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        
        
       
        
        return result;
    }
}
