//package org.ssa.ironyard.liquorstore.services;
//package org.ssa.ironyard.liquorstore.services;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.client.RestTemplate;
//import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
//import org.ssa.ironyard.liquorstore.dao.DAOProduct;
//import org.ssa.ironyard.liquorstore.jsonModel.JsonBeer;
//import org.ssa.ironyard.liquorstore.model.CoreProduct;
//import org.ssa.ironyard.liquorstore.model.Product;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mysql.cj.jdbc.PreparedStatement;
//
//import javax.sql.DataSource;
//
//public class changePictures
//{
//
//  
//  DAOProduct daoProd;
//  DAOCoreProduct daoCP;
//      
//      @Autowired
//      public changePictures(DAOProduct daoProd, DAOCoreProduct daoCP)
//      {
//          this.daoProd = daoProd;
//          this.daoCP = daoCP;
//      }
//      public void getBeers() throws IOException 
//      {
//          
//          //final String uri =  "https://api.brewerydb.com/v2/search/?q=" + search + "$type=beers&key=c66e4aae43258ca39d5309cc0281d11f&format=json";
//          
//          List<JsonBeer> beers = new ArrayList<>();
//          JsonBeer js = new JsonBeer();
//          int pg = 1;
//          
//          String uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
//         
//          String result;
//          RestTemplate restTemplate = new RestTemplate();
//          result = restTemplate.getForObject(uri2, String.class);
//          
//          JsonBeer jb = null;
//          do
//          {
//              System.out.println(pg);
//              System.out.println(uri2);
//              System.out.println(result);
//              ObjectMapper objectMapper = new ObjectMapper();
//          
//              JsonNode node;
//              try
//              {
//              
//                  node = objectMapper.readValue(result,JsonNode.class);
//  
//                  JsonNode array = node.get("result");
//                  JsonNode page = node.get("pager");
//                  
//                  //System.out.println(page.get("records_per_page"));
//              
//                  System.out.println(array.get(0) + " result . array");
//          
//                  
//          
//                  for (int i = 0; i < array.size(); i++)
//                  {
//                      String id = array.get(i).get("id").asText();
//                      
//                      String image_thumb_url = array.get(i).get("image_thumb_url").asText();
//                      String image_url = array.get(i).get("image_url").asText();
//                      String is_final_page = page.get("is_final_page").asText();
//                     
//                      js = new JsonBeer(id,"","",0,"","","","","","",0,0,"","",image_thumb_url,image_url,"",0,is_final_page);
//                      
//                      beers.add(js);
//                   
//  
//                  }
//              
//                  //System.out.println(beers + " list of jsonBeers");
//                  
//              }
//              catch (JsonParseException e)
//              {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//              catch (JsonMappingException e)
//              {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//              catch (IOException e)
//              {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//              
//              pg++;
//              uri2 = "https://lcboapi.com/products?page=" + pg  + "&access_key=MDoxZjA5MTVmMC05MTU4LTExZTYtOWY3Ni02MzM2M2YyNzBlOWU6VldlVkdsZEVGSGZCUzhjYUF0WnN4VDlOOWJhS0FVWGdHekp3";
//              result = restTemplate.getForObject(uri2, String.class);
//          }
//          while(js.getIs_final__page().equals("false"));
//          
//          List<CoreProduct> listCP = daoCP.readAll();
//          
//          for (int i = 0; i < beers.size(); i++)
//          {
//              int num = 0;
//              System.out.println(num);
//              JsonBeer jsb = beers.get(i);
//              if(jsb.getImage_url().equals("null"))
//              {
//                  for (int j = 0; j < listCP.size(); j++)
//                  {
//                        CoreProduct cpT = listCP.get(j);
//                        if(cpT.getFullSizeImage().equals(jsb.getId()))
//                        {
//                            CoreProduct cpN = new CoreProduct(cpT.getId(),cpT.getName(),cpT.getTags(),cpT.getType(),cpT.getSubType(),
//                            cpT.getDescription(),"noimage",cpT.getThumbnail(),false);
//                            daoCP.update(cpN);
//                            num++;
//                        }
//                  }
//              }
//              
//              if(jsb.getImage_thumb_url().equals("null"))
//              {
//                  for (int j = 0; j < listCP.size(); j++)
//                  {
//                        CoreProduct cpT = listCP.get(j);
//                        if(cpT.getThumbnail().equals(jsb.getId()))
//                        {
//                            CoreProduct cpN = new CoreProduct(cpT.getId(),cpT.getName(),cpT.getTags(),cpT.getType(),cpT.getSubType(),
//                            cpT.getDescription(),cpT.getFullSizeImage(),"noimage",false);
//                            daoCP.update(cpN);
//                            num++;
//                        }
//                  }
//              }
//          }
//          
//      }
//}
