package org.ssa.ironyard.liquorstore.services;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.client.RestTemplate;
import org.ssa.ironyard.liquorstore.jsonModel.JsonBeer;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BrewaryDBService
{
    public void getBeers() throws IOException 
    {
        
        //final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
        
        List<JsonBeer> beers = new ArrayList();
        List<Product> productList = new ArrayList<>();
        List<CoreProduct> coreProductList = new ArrayList<>();
        int pg = 1;
        
        String uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
       
        String result;
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject(uri2, String.class);
    
        while(result != null)
        {
            
            System.out.println(pg);
            System.out.println(uri2);
            System.out.println(result);
            ObjectMapper objectMapper = new ObjectMapper();
        
            JsonNode node;
            try
            {
            
                node = objectMapper.readValue(result,JsonNode.class);

                JsonNode array = node.get("result");
            
                System.out.println(array.get(0) + " result . array");
        
                
        
                for (int i = 0; i < array.size(); i++)
                {
                    String name = array.get(i).get("name").asText();
                    String tags = array.get(i).get("tags").asText();
                    int regular_price_in_cents = array.get(i).get("regular_price_in_cents").asInt();
                    String primary_category = array.get(i).get("primary_category").asText();
                    String secondary_category = array.get(i).get("secondary_category").asText();
                    String origin = array.get(i).get("origin").asText();
                    String packageP = array.get(i).get("package").asText();
                    String package_unit_type = array.get(i).get("package_unit_type").asText();
                    String package_unit_volume_in_milliliters = array.get(i).get("package_unit_volume_in_milliliters").asText();
                    int total_package_units = array.get(i).get("total_package_units").asInt();
                    int alcohol_content = array.get(i).get("alcohol_content").asInt();
                    String producer_name = array.get(i).get("producer_name").asText();
                    String description = array.get(i).get("description").asText();
                    String image_thumb_url = array.get(i).get("image_thumb_url").asText();
                    String image_url = array.get(i).get("image_url").asText();
                    String tertiary_category = array.get(i).get("tertiary_category").asText();
                
                    JsonBeer jb = new JsonBeer(name,tags,regular_price_in_cents,primary_category,secondary_category,origin,packageP,package_unit_volume_in_milliliters,
                            package_unit_type,total_package_units,alcohol_content,producer_name,description,image_thumb_url,image_url,tertiary_category);
                
                    beers.add(jb);

                }
            
                System.out.println(beers + " list of jsonBeers");
                
                String unitSize = "";
                
                for (int i = 0; i < beers.size(); i++)
                {
                    unitSize += "package: " + beers.get(i).getPackageP() + " package unit type: " + beers.get(i).getPackage_unit_type() + " package unit in ml " + 
                    beers.get(i).getPackage_unit_volume_in_milliliters() + " total package units: " + beers.get(i).getTotal_package_units() + "\r\n";
                }
                
                System.out.println(unitSize);
                
                File fileSize = new File("C:/text/package.txt");
                
                if(!fileSize.exists())
                    fileSize.createNewFile();
                        
                FileWriter fwSize = new FileWriter(fileSize.getAbsoluteFile());
                BufferedWriter bwSize = new BufferedWriter(fwSize);
                bwSize.write(unitSize);
                bwSize.close();
                        
                System.out.println("done URL");       
                
                
            
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
                
                    coreProductList.add(coreProduct);
                
                    BaseUnit baseUnit;
                    
                    switch(beers.get(i).getPackage_unit_type())
                    {
                        case("bottle"):
                        {
                            switch(beers.get(i).getPackage_unit_volume_in_milliliters())
                            {
                                case("330"):
                                case("341"):
                                case("355"):
                                {
                                    baseUnit = BaseUnit._12OZ_BOTTLE;
                                    break;
                                }
                                case("473"):
                                {
                                    baseUnit = BaseUnit._16OZ_BOTTLE;
                                    break;
                                }
                                case("500"):
                                {
                                    baseUnit = BaseUnit._500ML_BOTTLE;
                                    break;
                                }
                                case("600"):
                                {
                                    baseUnit = BaseUnit._600ML_BOTTLE;
                                    break;
                                }
                                case("750"):
                                {
                                    baseUnit = BaseUnit._750ML_BOTTLE;
                                    break;
                                }
                                case("1000"):
                                {
                                    baseUnit = BaseUnit._1000ML_BOTTLE;
                                    break;
                                }
                                case("1140"):
                                {
                                    baseUnit = BaseUnit._1140ML_BOTTLE;
                                    break;
                                }case("1500"):
                                {
                                    baseUnit = BaseUnit._1500ML_BOTTLE;
                                    break;
                                }case("1750"):
                                {
                                    baseUnit = BaseUnit._1750ML_BOTTLE;
                                    break;
                                }case("2000"):
                                {
                                    baseUnit = BaseUnit._2000ML_BOTTLE;
                                    break;
                                }
                                case("3000"):
                                {
                                    baseUnit = BaseUnit._3000ML_BOTTLE;
                                    break;
                                }
                                default:
                                    baseUnit = BaseUnit._NA;
                                    break;
                            }
                        }
                        break;
                        
                        case("can"):
                        {
                            switch(beers.get(i).getPackage_unit_volume_in_milliliters())
                            {
                             case("330"):
                             case("341"):
                             case("355"):
                             {
                                 baseUnit = BaseUnit._12OZ_CAN;
                                 break;
                             }
                             case("473"):
                             {
                                 baseUnit = BaseUnit._16OZ_CAN;
                                 break;
                             }
                             case("500"):
                             {
                                 baseUnit = BaseUnit._500ML_CAN;
                                 break;
                             }
                             default:
                                 baseUnit = BaseUnit._NA;
                                 break;
                            }
                            
                        }
                        break;
                        
                        case("bagnbox"):
                        {
                            baseUnit = BaseUnit._4000ML_BOX;
                            break;
                        }
                        default:
                            baseUnit = BaseUnit._NA;
                            break;
                    }
                    
                    
                    Integer quantity = beers.get(i).getTotal_package_units();
                    Random r = new Random();
                    int Low = 10;
                    int High = 500;
                    int resultR = r.nextInt(High-Low) + Low;
                    Integer inventory = resultR;
                    Double priceC = Double.valueOf(beers.get(i).getRegular_price_in_cents()); 
                    BigDecimal price = BigDecimal.valueOf(priceC/100);  
                    Product p = new Product(coreProduct,baseUnit,quantity,inventory,price);
                
                    productList.add(p);
//                    
//                    System.out.println("name: " + name);
//                    System.out.println("tags: " + tags);
//                    System.out.println("type: " + type);
//                    System.out.println("subType: " + subType);
//                    System.out.println("desc : " + description);
//                    System.out.println("fullUrl: " + fullUrl);
//                    System.out.println("thumbUrl: " + thumbUrl);
                    System.out.println("baseUnit: " + baseUnit);
//                    System.out.println("qty: " + quantity);
//                    System.out.println("inv: " + inventory);
//                    System.out.println("price: " + price);
                    
                    

                }
                


                
                String URLString = "";
                
                for (int i = 0; i < beers.size(); i++)
                {
                    URLString += beers.get(i).getImage_url() + "\r\n" + beers.get(i).getImage_thumb_url() + "\r\n";
                }
                
                File fileURL = new File("C:/text/URL.txt");
                
                if(!fileURL.exists())
                    fileURL.createNewFile();
                        
                FileWriter fwURL = new FileWriter(fileURL.getAbsoluteFile());
                BufferedWriter bwURL = new BufferedWriter(fwURL);
                bwURL.write(URLString);
                bwURL.close();
                        
                System.out.println("done URL");
                
            
//                for (int i = 0; i < coreProductList.size(); i++)
//                {
//                    System.out.println("Name: " + coreProductList.get(i).getName());
//                    for (int j = 0; j < coreProductList.get(i).getTags().size(); j++)
//                    {
//                        System.out.println("Tags: " + coreProductList.get(i).getTags().get(j).getName());
//                    }
//                
//                    System.out.println("Type: " + coreProductList.get(i).getType());
//                    System.out.println("subType: " + coreProductList.get(i).getSubType());
//                    System.out.println("Desc: " + coreProductList.get(i).getDescription());
//                    System.out.println("BU: " + productList.get(i).getBaseUnitType().toString());
//                    System.out.println("Qty: " + productList.get(i).getQuantity());
//                    System.out.println("Inv: " + productList.get(i).getInventory());
//                    System.out.println("Price: " + productList.get(i).getPrice());
//                
//                
//                
//                }
            
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
            
             
            
            pg++;
            uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
            result = restTemplate.getForObject(uri2, String.class);
        }
        
        
    }
    
    
//  System.out.println(beers.size() + " size of beers");
//  System.out.println(coreProductList.size() + " size or core product");
//  System.out.println(productList.size() + " size of product");
//  
//  String beersFile = "";
//  
//  for (int i = 0; i < beers.size(); i++)
//  {
//      if(beers.get(i) == null)
//          break;
//
//      beersFile += "name: " + beers.get(i).getName() + "Tags: " + beers.get(i).getTags() + 
//              "Regular price(cents) :" + beers.get(i).getRegular_price_in_cents() + 
//              "Primary Cat: " + beers.get(i).getPrimary_category() + "secondary cat: " + beers.get(i).getSecondary_category() + 
//              "Package: " + beers.get(i).getPackageP() + "package unit type: " + beers.get(i).getPackage_unit_type() + 
//              "Total Package Units: " + beers.get(i).getTotal_package_units() +"desc: " + beers.get(i).getDescription();
//
//  }
//  
//  File fileBeer = new File("C:/text/beer.txt");
//  
//  if(!fileBeer.exists())
//      fileBeer.createNewFile();
//  
//  FileWriter fwBeer = new FileWriter(fileBeer.getAbsoluteFile());
//  BufferedWriter bwBeer = new BufferedWriter(fwBeer);
//  bwBeer.write(beersFile);
//  bwBeer.close();
//          
//  System.out.println("Beer done");
//  
//
//  
//  String productFile = "";
//  
//  for (int j = 0; j < coreProductList.size(); j++)
//  {
//      if(coreProductList.get(j) == null)
//          break;
//      
//      String tagString = "";
//      
//      for (int i = 0; i < coreProductList.get(j).getTags().size(); i++)
//      {
//          tagString += coreProductList.get(j).getTags().get(i).getName() + " ";
//      }
//      
//      String name = coreProductList.get(j).getName();
//      String tags = tagString;
//      //System.out.println("type " + coreProductList.get(j).getType().toString());
//      String type = coreProductList.get(j).getType().toString();
//      String subType = coreProductList.get(j).getSubType();
//      String desc = coreProductList.get(j).getDescription();
//      String baseunit = productList.get(j).getBaseUnit().toString();
//      String qty = Integer.toString(productList.get(j).getQuantity());
//      String inventory = Integer.toString(productList.get(j).getInventory());
//      BigDecimal priceB = productList.get(j).getPrice();
//      Double priceD = priceB.doubleValue();
//      String price = Double.toString(priceD);
//      
//      
//      productFile += "Core Product:  name: " + coreProductList.get(j).getName() + "Tags: " + tagString + "Type:" + 
//      coreProductList.get(j).getType().toString() + "SubType: " + coreProductList.get(j).getSubType() + "Desc: " + //coreProductList.get(j).getDescription() + 
//      "Product:   baseUnit: " + productList.get(j).getBaseUnit().toString() + "Qty: " + productList.get(j).getQuantity() + 
//      "inventory: " + productList.get(j).getInventory() +"Price: " + productList.get(j).getPrice();
//      
//      
//  }
    
//  
//  File fileProd = new File("C:/text/product.txt");
//  
//  if(!fileProd.exists())
//      fileProd.createNewFile();
//          
//  FileWriter fwProd = new FileWriter(fileProd.getAbsoluteFile());
//  BufferedWriter bwProd = new BufferedWriter(fwProd);
//  bwProd.write(productFile);
//  bwProd.close();
//  System.out.println("done products");
}
