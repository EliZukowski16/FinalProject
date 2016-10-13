package org.ssa.ironyard.liquorstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.jsonModel.JsonOrder;
import org.ssa.ironyard.liquorstore.jsonModel.JsonProduct;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;
import org.ssa.ironyard.liquorstore.services.AdminServiceImpl;
import org.ssa.ironyard.liquorstore.services.AnalyticsServiceImpl;
import org.ssa.ironyard.liquorstore.services.CoreProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.CustomerServiceImpl;
import org.ssa.ironyard.liquorstore.services.OrdersServiceImpl;
import org.ssa.ironyard.liquorstore.services.ProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.SalesServiceImpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.api.log.Log;


@RestController
@RequestMapping("/TheBeerGuys/customer")
public class CustomerController
{
    
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    AnalyticsServiceImpl analyticsService;
    @Autowired
    CoreProductServiceImpl coreProductService;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    OrdersServiceImpl orderService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    SalesServiceImpl salesService;
    
    static Logger LOGGER = LogManager.getLogger(CustomerController.class);
    
    public CustomerController(AdminServiceImpl as, AnalyticsServiceImpl ans, CoreProductServiceImpl cps, CustomerServiceImpl cs, OrdersServiceImpl os, ProductServiceImpl ps, SalesServiceImpl ss)
    {
        adminService = as;
        analyticsService = ans;
        coreProductService = cps;
        customerService = cs;
        orderService = os;
        productService = ps;
        salesService = ss;       
        
    }
    
    @RequestMapping(value="/{customerID}/customers", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Customer>> addCustomer(@PathVariable String CustomerID, HttpServletRequest request)
    {
        Map<String,Customer> response = new HashMap<>();
        
        
        
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        ZipCode zipCode = new ZipCode(request.getParameter("zipCode"));
        State state = State.getInstance(request.getParameter("state"));
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zipCode);
        address.setState(state);
        
        String month = request.getParameter("birthMonth");
        String day = request.getParameter("birthDay");
        String year = request.getParameter("birthYear");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        LocalTime time = LocalTime.of(12, 00);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        
        LOGGER.info("got customer info add ",userName,password,firstName,lastName,street,city,state,zipCode,address,ldt);
        
        Password p = new BCryptSecurePassword().secureHash(password);
        Customer customer = new Customer(userName,p,firstName,lastName,address,ldt);
        
        Customer customerAdd = customerService.addCustomer(customer);
        
        if(customerAdd == null)
            response.put("error", customerAdd);
        else
            response.put("success", customerAdd);
        
        return ResponseEntity.ok().header("Customer", "Add Customer").body(response);
    }
    
    @RequestMapping(value="/{customerID}/customerEdit", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Customer>> editCustomer(@PathVariable String id, HttpServletRequest request)
    {
        Map<String,Customer> response = new HashMap<>();
        
        int customerID = Integer.parseInt(id);
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        ZipCode zipCode = new ZipCode(request.getParameter("zipCode"));
        State state = State.getInstance(request.getParameter("state"));
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zipCode);
        address.setState(state);
        
        String month = request.getParameter("birthMonth");
        String day = request.getParameter("birthDay");
        String year = request.getParameter("birthYear");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        LocalTime time = LocalTime.of(12, 00);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        
        LOGGER.info("got customer info edit ",customerID,userName,password,firstName,lastName,street,city,state,zipCode,address,ldt);
        
        Password p = new BCryptSecurePassword().secureHash(password);
        Customer customer = new Customer(customerID,userName,p,firstName,lastName,address,ldt);
        Customer customerEdit = customerService.editCustomer(customer);
        
        if(customerEdit == null)
            response.put("error", customerEdit);
        else
            response.put("success", customerEdit);
        
        return ResponseEntity.ok().header("Customer", "Customer Edit").body(response);
        
    }
    
    @RequestMapping(value= "/{customerID}/products", method = RequestMethod.GET)
    public ResponseEntity<Map<String,List<Product>>> getProducts(@PathVariable String customerID)
    {
        Map<String,List<Product>> response = new HashMap<>();
        
        List<Product> products = productService.readAllProducts();
        LOGGER.info("we are trying to get all the products");
        //List<Product> products = new ArrayList();
        //Product p = productService.readProduct(107);
        //Product p2 = productService.readProduct(108);


       // products.add(p);
        //products.add(p2);

    
        
        LOGGER.info(products);
        if(products == null)
            response.put("error", products);
        else
            response.put("success", products);
        
        return ResponseEntity.ok().header("Products", "Get All Products").body(response);
    }
    
    @RequestMapping(value="/{customerID}/search", method = RequestMethod.GET)
    public ResponseEntity<Map<String,List<Product>>> searchKeywordType(@PathVariable String customerID, HttpServletRequest request)
    {
        Map<String,List<Product>> response = new HashMap<>();
        
        LOGGER.info("Going to the search");
        
        LOGGER.info(request.getParameterValues("types") + " request types");
        LOGGER.info(request.getParameter("keywords") + " request keywords");
        
        String keyword = request.getParameter("keywords");
        LOGGER.info(keyword + " String keyword");
        String[] tagArray = keyword.split("\\s");
        String[] typeArray = request.getParameterValues("types");
       
        for (int i = 0; i < tagArray.length; i++)
        {
            LOGGER.info(tagArray[i] + " String array tags" + i);
        }
        
        for (int i = 0; i < typeArray.length; i++)
        {
            LOGGER.info(typeArray[i] + " String array type" + i);
        }
        
        
        
       
        List<Tag> tags = new ArrayList<>();
        List<Type> types = new ArrayList<>();
                
        tags = Stream.of(tagArray).map(Tag::new).collect(Collectors.toList());
        LOGGER.info("hello");
        types = Stream.of(typeArray).map(Type::getInstance).collect(Collectors.toList());


        
        LOGGER.info(tags + "List tags");
        LOGGER.info(types + "List Tyeps");
        
        List<Product> products = productService.searchProduct(tags,types);
        
        LOGGER.info(products + "products");
        
        if(products.size() == 0)
        {
            response.put("error", products);
        }
        else
        {
            response.put("success", products);
        }
        
        return ResponseEntity.ok().header("Products", "Search By Keyword").body(response);
    }
    
    

    
    
    @RequestMapping(value="/{customerID}/placeOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Order>> placeOrder(@PathVariable String customerID,@RequestBody Map<String,Object> map)
    {
        LOGGER.info("you've made it to place order");
       Map<String,Order> addOrderMap = new HashMap<>();
        
        Customer cus = new Customer(Integer.parseInt(customerID),null,null,null,null,null,null);
        BigDecimal total = getBigDecimal(map.get("total"));
        
        LOGGER.info("Total: {}", total);
            
        String month = (String) map.get("orderMonth");
        String day = (String) map.get("orderDay");
        String year = (String) map.get("orderYear");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        LocalTime time = LocalTime.of(12, 00);
        LocalDateTime ldt = LocalDateTime.of(date, time);
        LOGGER.info("orderMonth: {}" , month);
        LOGGER.info("orderDay: {}", day);
        LOGGER.info(" orderyear: {}", year);
        LOGGER.info("date: {}", ldt);
        
        List<OrderDetail> ordDetailList = new ArrayList<>();
        
        for(Map<String, Object> product: (List<Map<String,Object>>) map.get("products")){
            
            //LOGGER.info("ID: {} QUANTITIY: {} PRICE: {}", product.get("id"), product.get("qty"), product.get("price"));
            Object obj = product.get("id");
            
            Integer id = (Integer) product.get("id");
            Integer qty = (Integer) product.get("qty");
            BigDecimal price = getBigDecimal(product.get("price"));
            LOGGER.info("ID: {} QTY: {} Price: {} ", id,qty,price);
            
            Product p = new Product(id,null,null,null,null,null);
            
            OrderDetail ordDetail = new OrderDetail(p, qty, price);
            LOGGER.info("Order Detail: {}",ordDetail);
            
            ordDetailList.add(ordDetail);            
            
        }
        
        LOGGER.info("Order Detail List: {}",ordDetailList);
        
        Order ord = new Order(cus, ldt, total, ordDetailList,OrderStatus.PENDING);
        
        LOGGER.info("Order from site: {}",ord);
        LOGGER.info("First Order Detail price from site: {}", ord.getoD().get(0).getUnitPrice());
        
        Order addOrder = orderService.addOrder(ord);
        
        LOGGER.info("Order from service: {}", addOrder);
        
        if(addOrder == null)
        {
            LOGGER.info("made it to error: {}",addOrder);
            addOrderMap.put("error", addOrder);
        } 
        else if(addOrder.getCustomer() == null && addOrder.getDate() == null && addOrder.getTotal() == null && addOrder.getoD() != null)
        {
            LOGGER.info("made it to out of stock: {}",addOrder);
            addOrderMap.put("outofstock", addOrder);
        } 
        else if(addOrder.getCustomer() == null && addOrder.getDate() != null && addOrder.getTotal() == null && addOrder.getoD() != null)
        {
            LOGGER.info("made it to price change: {}",addOrder);
            addOrderMap.put("pricechange", addOrder);
        }
        else
        {
            LOGGER.info("made it to success: {}",addOrder);
            addOrderMap.put("success",addOrder);
        }
        
//        if(addOrder == null)
//        {
//            LOGGER.info("made it to error: {}",addOrder);
//            addOrderMap.put("error", addOrder.getId());
//        } 
//        else if(addOrder.getCustomer() == null && addOrder.getDate() == null && addOrder.getTotal() == null && addOrder.getoD() != null)
//        {
//            LOGGER.info("made it to out of stock: {}",addOrder);
//            addOrderMap.put("outofstock", addOrder.getId());
//        } 
//        else if(addOrder.getCustomer() == null && addOrder.getDate() != null && addOrder.getTotal() == null && addOrder.getoD() != null)
//        {
//            LOGGER.info("made it to price change: {}",addOrder);
//            addOrderMap.put("pricechange", addOrder.getId());
//        }
//        else
//        {
//            LOGGER.info("made it to success: {}",addOrder);
//            addOrderMap.put("success",addOrder.getId());
//        }
          
        LOGGER.info("Final repsonse map: {}",addOrderMap);
        return ResponseEntity.ok().header("Customer", "Place Order").body(addOrderMap);
    

    }
    
    
    @RequestMapping(value="/{customerID}/Orders", method = RequestMethod.POST)
    public ResponseEntity<Map<String,List<Order>>> getOrdersByCustomer(@PathVariable String customerID)
    {
        Map<String,List<Order>> response = new HashMap<>();
        List<Order> orderList = new ArrayList<>();
        
        Integer cusID = Integer.parseInt(customerID);
        
        orderList = orderService.readOrdersByCustomer(cusID);
        
        if(orderList == null)
            response.put("error", orderList);
        else
            response.put("success", orderList);
        
        return ResponseEntity.ok().header("Customer", "Get Orders").body(response);
        
    }
    
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal result = new BigDecimal(0);
        if(value != null) {
            try {
                if(value instanceof BigDecimal) {
                    result = (BigDecimal) value;
                } else if(value instanceof String) {
                    result = new BigDecimal((String) value);
                } else if(value instanceof BigInteger) {
                    result = new BigDecimal((BigInteger) value);
                } else if(value instanceof Number) {
                    result = new BigDecimal(((Number) value).doubleValue());
                } else {
                    //throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
                    System.out.println("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal. " + e);
            } catch (ClassCastException e) {
                System.out.println("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal. " + e);
            } catch (Exception e) {
                System.out.println("Exception caught. " + e);
            }           
        }
        return result;
    }
    

    
}
