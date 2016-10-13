package org.ssa.ironyard.liquorstore.services;

import java.io.IOException;

import org.junit.Test;
import org.ssa.ironyard.liquorstore.jsonModel.jsonBeer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BrewaryDBServiceTest
{
    
    BrewaryDBService bdbService = new BrewaryDBService();

    @Test
    public void test() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonNode node;
        try
        {
            node = objectMapper.readValue(bdbService.getBeers("hello"),JsonNode.class);
            
            //system.out.println(node);
            JsonNode array = node.get("result");
            
            //System.out.println(array.get(0));
            
            JsonNode a = array.get(0);
            String d = array.get(0).get("name").asText();
            //String ar = a.asText();
            
            System.out.println(d);
            
            //jsonBeer jBeer = objectMapper.readValue(ar, jsonBeer.class);
            
            //System.out.println(jBeer);
        }
        catch (JsonParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        
        
    }

}
