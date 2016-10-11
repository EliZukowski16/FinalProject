package org.ssa.ironyard.liquorstore.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.services.AdminServiceImpl;
import org.ssa.ironyard.liquorstore.services.AnalyticsServiceImpl;
import org.ssa.ironyard.liquorstore.services.CoreProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.CustomerServiceImpl;
import org.ssa.ironyard.liquorstore.services.OrdersServiceImpl;
import org.ssa.ironyard.liquorstore.services.ProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.SalesServiceImpl;


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
        
        String[] tagArray = request.getParameterValues("tags");
        String [] typeArray = request.getParameterValues("types");
       
        LOGGER.info(tagArray);
        LOGGER.info(tagArray);
                
        List<Tag> tags = Stream.of(tagArray).map(Tag::new).collect(Collectors.toList());
        List<Type> types = Stream.of(typeArray).map(Type::getInstance).collect(Collectors.toList());
        
        LOGGER.info(tags);
        LOGGER.info(types);
        
        List<Product> products = productService.searchProduct(tags,types);
        
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
    
    
    @RequestMapping(value="/{customerID}/placeOrder", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Order>> placeOrder(@PathVariable String customerID, HttpServletRequest request)
    {
        Order ord;
        
        Customer cus;
        String cusUserName = request.getParameter("customerUserName");
        String cusPassword = request.getParameter("customerPassword");
        String cusFName = request.getParameter("customerFName");
        String cusLName = request.getParameter("customerLName");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
    }
    
    

    
    
    
}
