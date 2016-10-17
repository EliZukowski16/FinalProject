//package org.ssa.ironyard.liquorstore.services;
//
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
//import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
//import org.ssa.ironyard.liquorstore.dao.DAOProduct;
//import org.ssa.ironyard.liquorstore.jsonModel.JsonBeer;
//import org.ssa.ironyard.liquorstore.model.CoreProduct;
//import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
//import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
//import org.ssa.ironyard.liquorstore.model.Product;
//import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//public class BrewaryDBService
//{
//    
//DAOProduct daoProd;
//DAOCoreProduct daoCP;
//    
//    @Autowired
//    public BrewaryDBService(DAOProduct daoProd, DAOCoreProduct daoCP)
//    {
//        this.daoProd = daoProd;
//        this.daoCP = daoCP;
//    }
//    
//    public void getBeers() throws IOException 
//    {
//        
//        //final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
//        
//        List<JsonBeer> beers = new ArrayList<>();
//        List<Product> productList = new ArrayList<>();
//        List<CoreProduct> coreProductList = new ArrayList<>();
//        int pg = 1;
//        
//        String uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
//       
//        String result;
//        RestTemplate restTemplate = new RestTemplate();
//        result = restTemplate.getForObject(uri2, String.class);
//        
//        JsonBeer jb = null;
//        do
//        {
//            System.out.println(pg);
//            System.out.println(uri2);
//            System.out.println(result);
//            ObjectMapper objectMapper = new ObjectMapper();
//        
//            JsonNode node;
//            try
//            {
//            
//                node = objectMapper.readValue(result,JsonNode.class);
//
//                JsonNode array = node.get("result");
//                JsonNode page = node.get("pager");
//                
//                //System.out.println(page.get("records_per_page"));
//            
//                System.out.println(array.get(0) + " result . array");
//        
//                
//        
//                for (int i = 0; i < array.size(); i++)
//                {
//                    String id = array.get(i).get("id").asText();
//                    String name = array.get(i).get("name").asText();
//                    String tags = array.get(i).get("tags").asText();
//                    int regular_price_in_cents = array.get(i).get("regular_price_in_cents").asInt();
//                    String primary_category = array.get(i).get("primary_category").asText();
//                    String secondary_category = array.get(i).get("secondary_category").asText();
//                    String origin = array.get(i).get("origin").asText();
//                    String packageP = array.get(i).get("package").asText();
//                    String package_unit_type = array.get(i).get("package_unit_type").asText();
//                    String package_unit_volume_in_milliliters = array.get(i).get("package_unit_volume_in_milliliters").asText();
//                    int total_package_units = array.get(i).get("total_package_units").asInt();
//                    int alcohol_content = array.get(i).get("alcohol_content").asInt();
//                    String producer_name = array.get(i).get("producer_name").asText();
//                    String description = array.get(i).get("description").asText();
//                    String image_thumb_url = array.get(i).get("image_thumb_url").asText();
//                    String image_url = array.get(i).get("image_url").asText();
//                    String tertiary_category = array.get(i).get("tertiary_category").asText();
//                    int records_per_page = page.get("current_page_record_count").asInt();
//                    String is_final_page = page.get("is_final_page").asText();
//                    
//
//                     jb = new JsonBeer(id,name,tags,regular_price_in_cents,primary_category,secondary_category,origin,packageP,package_unit_volume_in_milliliters,
//                            package_unit_type,total_package_units,alcohol_content,producer_name,description,image_thumb_url,image_url,tertiary_category,records_per_page,is_final_page);
//                
//                    beers.add(jb);
//
//                }
//            
//                //System.out.println(beers + " list of jsonBeers");
//                
//            }
//            catch (JsonParseException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            catch (JsonMappingException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            
//            pg++;
//            uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
//            result = restTemplate.getForObject(uri2, String.class);
//        }
//        while(jb.getIs_final__page().equals("false"));
//       
//        
//        System.out.println(beers.size());
//        System.out.println("done beer");
//        
//        String URLString = "";
//        
//        for (int j = 0; j < beers.size(); j++)
//        {
//            URLString += beers.get(j).getImage_url() + "\r\n" + beers.get(j).getImage_thumb_url() + "\r\n";
//        }
//        
//        
//        File fileURL = new File("C:/text/URL.txt");
//        
//        if(!fileURL.exists())
//            fileURL.createNewFile();
//                         
//        FileWriter fwURL = new FileWriter(fileURL.getAbsoluteFile());
//        BufferedWriter bwURL = new BufferedWriter(fwURL);
//        bwURL.write(URLString);
//        bwURL.close();
//        
//        System.out.println("done file");
//        
//        int num = 0;
//        
//        
//                   
//       for (int i = 0; i < beers.size(); i++)
//       {
//        
//           
//           String name = beers.get(i).getName();
//           
//           if(name.length() >= 50)
//           {
//               name = beers.get(i).getName().substring(0, 49);
//           }
//           
//           String tagsS = beers.get(i).getTags();
//           String[] tagArray = tagsS.split("\\s");
//           Set<Tag> tagsSet = Stream.of(tagArray).map(Tag::new).collect(Collectors.toSet());
//           List<Tag> tags = tagsSet.stream().collect(Collectors.toList());
//           
//           
////           for (int j = 0; j < tags.size(); j++)
////           {
////               if(tags.get(j).getName().equals("ros") || tags.get(j).getName().equals("menage") || tags.get(j).getName().equals("à"))
////               {
////                   tags.remove(j);
////               }
////           }
//           
//       
//           Type type;
//           if(beers.get(i).getPrimary_category().equals("Ready-to-Drink/Coolers"))
//           {
//               type = Type.SPIRITS;
//           }
//           else
//           {
//               type = Type.getInstance(beers.get(i).getPrimary_category());
//           }
//         
//           if(type == null)
//           {
//               System.out.println(beers.get(i).getPrimary_category());
//               break; 
//           }
//               
//           String subType = beers.get(i).getSecondary_category();
//           String description = beers.get(i).getDescription();
//           String fullUrl;
//           String thumbUrl;
//           
//           
//           String productID = beers.get(i).getId();
////           if(beers.get(i).getImage_url().equals("null") && beers.get(i).getImage_thumb_url().equals("null"))
////           {
////               fullUrl = "imageNA.jpeg";
////               thumbUrl = "imageThumbNA.png";
////           }
////           else if((!beers.get(i).getImage_url().equals("null")) && beers.get(i).getImage_thumb_url().equals("null"))
////           {
////               fullUrl = "full_" + num + ".jpeg";
////               thumbUrl = "imageThumbNA.png";
////               num++;
////           }
////           else if(beers.get(i).getImage_url().equals("null") && (!beers.get(i).getImage_thumb_url().equals("null")))
////           {
////               fullUrl = "imageNA.jpeg";
////               thumbUrl = "thumb_" + num + ".png";
////               num++;
////           }
////           else
////           {
////               fullUrl = "full_" + num + ".jpeg";
////               thumbUrl = "thumb_" + num + ".png";
////               num++;
////           }
//           
////           System.out.println(beers.get(i).getImage_url());
////           System.out.println(fullUrl);
////           System.out.println(beers.get(i).getImage_thumb_url());
////           System.out.println(thumbUrl);
//          
//           
//           
//           CoreProduct coreProduct;
//           coreProduct = new CoreProduct.Builder().name(name).tags(tags).type(type).subType(subType).description(description).fullSizeImage(productID).thumbnail(productID).loaded(false).build();
//       
//           coreProductList.add(coreProduct);
//          
//       
//           BaseUnit baseUnit;
//           
//           switch(beers.get(i).getPackage_unit_type())
//           {
//               case("bottle"):
//               {
//                   switch(beers.get(i).getPackage_unit_volume_in_milliliters())
//                   {
//                       case("330"):
//                       case("341"):
//                       case("355"):
//                       {
//                           baseUnit = BaseUnit._12OZ_BOTTLE;
//                           break;
//                       }
//                       case("473"):
//                       {
//                           baseUnit = BaseUnit._16OZ_BOTTLE;
//                           break;
//                       }
//                       case("500"):
//                       {
//                           baseUnit = BaseUnit._500ML_BOTTLE;
//                           break;
//                       }
//                       case("600"):
//                       {
//                           baseUnit = BaseUnit._600ML_BOTTLE;
//                           break;
//                       }
//                       case("750"):
//                       {
//                           baseUnit = BaseUnit._750ML_BOTTLE;
//                           break;
//                       }
//                       case("1000"):
//                       {
//                           baseUnit = BaseUnit._1000ML_BOTTLE;
//                           break;
//                       }
//                       case("1140"):
//                       {
//                           baseUnit = BaseUnit._1140ML_BOTTLE;
//                           break;
//                       }case("1500"):
//                       {
//                           baseUnit = BaseUnit._1500ML_BOTTLE;
//                           break;
//                       }case("1750"):
//                       {
//                           baseUnit = BaseUnit._1750ML_BOTTLE;
//                           break;
//                       }case("2000"):
//                       {
//                           baseUnit = BaseUnit._2000ML_BOTTLE;
//                           break;
//                       }
//                       case("3000"):
//                       {
//                           baseUnit = BaseUnit._3000ML_BOTTLE;
//                           break;
//                       }
//                       default:
//                           baseUnit = BaseUnit._NA;
//                           break;
//                   }
//               }
//               break;
//               
//               case("can"):
//               {
//                   switch(beers.get(i).getPackage_unit_volume_in_milliliters())
//                   {
//                    case("330"):
//                    case("341"):
//                    case("355"):
//                    {
//                        baseUnit = BaseUnit._12OZ_CAN;
//                        break;
//                    }
//                    case("473"):
//                    {
//                        baseUnit = BaseUnit._16OZ_CAN;
//                        break;
//                    }
//                    case("500"):
//                    {
//                        baseUnit = BaseUnit._500ML_CAN;
//                        break;
//                    }
//                    default:
//                        baseUnit = BaseUnit._NA;
//                        break;
//                   }
//                   
//               }
//               break;
//               
//               case("bagnbox"):
//               {
//                   baseUnit = BaseUnit._4000ML_BOX;
//                   break;
//               }
//               default:
//                   baseUnit = BaseUnit._NA;
//                   break;
//           }
//           
//           
//           Integer quantity = beers.get(i).getTotal_package_units();
//           Random r = new Random();
//           int Low = 10;
//           int High = 500;
//           int resultR = r.nextInt(High-Low) + Low;
//           Integer inventory = resultR;
//           Double priceC = Double.valueOf(beers.get(i).getRegular_price_in_cents()); 
//           BigDecimal price = BigDecimal.valueOf(priceC/100);  
//           Product p = new Product(coreProduct,baseUnit,quantity,inventory,price);
//       
//           productList.add(p);
//           
//           
//       }
//       
//       System.out.println("done lists");
//       Set<CoreProduct> setCP = new HashSet<>();
//       Map<CoreProduct,CoreProduct> mapCP = new HashMap<>();
//       
//       for (int j = 0; j < productList.size(); j++)
//       {
//           CoreProduct cp = productList.get(j).getCoreProduct();
//           
//           if(setCP.add(cp) == true)
//           {
//               
//               CoreProduct cpI = daoCP.insert(cp);
//               mapCP.put(cp, cpI);
//               Product pN = productList.get(j).of().coreProduct(cpI).build();
//               
//               daoProd.insert(pN);
//           }
//           else
//           {
//               Product pN = productList.get(j).of().coreProduct(mapCP.get(cp)).build();
//               daoProd.insert(pN);
//           }
//       }
//       
//       System.out.println("FINALLY");
//    }
//    
//
//}
