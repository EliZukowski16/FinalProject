package org.ssa.ironyard.liquorstore.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.services.AdminService;
import org.ssa.ironyard.liquorstore.services.AnalyticsService;
import org.ssa.ironyard.liquorstore.services.CoreProductService;
import org.ssa.ironyard.liquorstore.services.CustomerService;
import org.ssa.ironyard.liquorstore.services.OrdersService;
import org.ssa.ironyard.liquorstore.services.ProductService;
import org.ssa.ironyard.liquorstore.services.SalesService;


@RestController
@RequestMapping("/TheBeerGuys")
public class CustomerController
{
    
    @Autowired
    AdminService adminService;
    @Autowired
    AnalyticsService analyticsService;
    @Autowired
    CoreProductService coreProductService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrdersService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    SalesService salesService;
    
    static Logger LOGGER = LogManager.getLogger(CustomerController.class);
    
    public CustomerController(AdminService as, AnalyticsService ans, CoreProductService cps, CustomerService cs, OrdersService os, ProductService ps, SalesService ss)
    {
        adminService = as;
        analyticsService = ans;
        coreProductService = cps;
        customerService = cs;
        orderService = os;
        productService = ps;
        salesService = ss;       
        
    }
    
    @RequestMapping(value="/customers", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Customer>> addCustomer(HttpServletRequest request)
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
        
        Customer customer = new Customer(userName,password,firstName,lastName,address,ldt);
        
        Customer customerAdd = customerService.addCustomer(customer);
        
        if(customerAdd == null)
            response.put("error", customerAdd);
        else
            response.put("success", customerAdd);
        
        return ResponseEntity.ok().header("Customer", "Add Customer").body(response);
    }
    
    @RequestMapping(value="/customers/{id}", method = RequestMethod.PUT)
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
        
        
        Customer customer = new Customer(customerID,userName,password,firstName,lastName,address,ldt);
        Customer customerEdit = customerService.editCustomer(customer);
        
        if(customerEdit == null)
            response.put("error", customerEdit);
        else
            response.put("success", customerEdit);
        
        return ResponseEntity.ok().header("Customer", "Customer Edit").body(response);
        
    }
    
    

    
    
    
}
