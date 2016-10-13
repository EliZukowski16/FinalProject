package org.ssa.ironyard.liquorstore.services;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.liquorstore.jsonModel.JsonBeer;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BrewaryDBService
{
    public void getBeers() 
    {
        
        //final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
        
        
        
        
        final String uri2 = "https://lcboapi.com/products?page=3&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
       
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri2, String.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonNode node;
        try
        {
            node = objectMapper.readValue(result,JsonNode.class);

            JsonNode array = node.get("result");
            
            System.out.println(array.get(0));
        
            List<JsonBeer> beers = new ArrayList();
        
            for (int i = 0; i < array.size(); i++)
            {
                String name = array.get(i).get("name").asText();
                String tags = array.get(i).get("tags").asText();
                int regular_price_in_cents = array.get(i).get("regular_price_in_cents").asInt();
                String primary_category = array.get(i).get("primary_category").asText();
                String secondary_category = array.get(i).get("secondary_category").asText();
                String origin = array.get(i).get("origin").asText();
                String package_unit_type = array.get(i).get("package_unit_type").asText();
                String package_unit_volume_in_milliliters = array.get(i).get("package_unit_volume_in_milliliters").asText();
                int total_package_units = array.get(i).get("total_package_units").asInt();
                int alcohol_content = array.get(i).get("alcohol_content").asInt();
                String producer_name = array.get(i).get("producer_name").asText();
                String description = array.get(i).get("description").asText();
                String image_thumb_url = array.get(i).get("image_thumb_url").asText();
                String image_url = array.get(i).get("image_url").asText();
                String tertiary_category = array.get(i).get("tertiary_category").asText();
                
                JsonBeer jb = new JsonBeer(name,tags,regular_price_in_cents,primary_category,secondary_category,origin,package_unit_type,
                        package_unit_volume_in_milliliters,total_package_units,alcohol_content,producer_name,description,
                        image_thumb_url,image_url,tertiary_category);
                
                beers.add(jb);
            }
            
            System.out.println(beers);
            
            for (int i = 0; i < beers.size(); i++)
            {
                String name = beers.get(i).getName();
                
                String tagsS = beers.get(i).getTags();
                String[] tagArray = tagsS.split("\\s");
                List<Tag> tags = Stream.of(tagArray).map(Tag::new).collect(Collectors.toList());
                
                Type type = Type.getInstance(beers.get(i).getPrimary_category());
                String subType = beers.get(i).getSecondary_category();
                String description = beers.get(i).getDescription();
                String fullUrl = beers.get(i).getImage_url();
                String thumbUrl = beers.get(i).getImage_thumb_url();
                CoreProduct coreProduct = new CoreProduct(name,tags,type,subType,description);
                
                BaseUnit baseUnit; 
                Integer quantity;
                Integer inventory;
                BigDecimal price;  
            }
            
             
            
            
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
