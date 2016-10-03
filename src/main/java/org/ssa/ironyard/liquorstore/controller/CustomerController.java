package org.ssa.ironyard.liquorstore.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Customer.Address;
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
    
    @RequestMapping("")
    public View home()
    {
        return new InternalResourceView("index.html");
    }
    
    @RequestMapping(value="/customers", method = RequestMethod.GET)
    public ResponseEntity<Map<String,List<Customer>>> getAllCustomers()
    {
        Map<String,List<Customer>> response = new HashMap<>();
        List<Customer> customers = customerService.readAllCustomers();
        
        if(customers == null)
            response.put("error", customers);
        else
            response.put("success", customers);
        
        return ResponseEntity.ok().header("The Beer Guy", "Customers").body(response);
        
    }
    
    @RequestMapping(value="/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Customer>> getCustomer(@PathVariable Integer id)
    {
        Map<String,Customer> response = new HashMap<>();
        Customer customer = customerService.readCustomer(id);
        
        if(customer == null)
            response.put("error", customer);
        else
            response.put("success", customer);
        
        return ResponseEntity.ok().header("Customers", "Customer").body(response);
        
    }
    
    @RequestMapping(value="/customers", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Customer>> addCustomer(HttpServletRequest request)
    {
        Map<String,Customer> response = new HashMap<>();
        
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        String streetNumber = request.getParameter("streetNumber");
        String streetName = request.getParameter("streetName");
        String apptNumber = request.getParameter("apptNumber");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        Address address = new Address(streetNumber, streetName, apptNumber, city, state, zipCode);
        
        String month = request.getParameter("birthMonth");
        String day = request.getParameter("birthDay");
        String year = request.getParameter("birthYear");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        LocalTime time = null;
        LocalDateTime ldt = LocalDateTime.of(date, time);
        
        Customer customer = new Customer(firstName,lastName,userName,password,address,ldt);
        
        Customer customerAdd = customerService.addCustomer(customer);
        
        if(customerAdd == null)
            response.put("error", customerAdd);
        else
            response.put("success", customerAdd);
        
        return ResponseEntity.ok().header("Customer", "Add Customer").body(response);
    }
    
    @RequestMapping(value="/customers", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Customer>> editCustomer(@PathVariable int id, HttpServletRequest request)
    {
        Map<String,Customer> response = new HashMap<>();
        
        int customerID = id;
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        String streetNumber = request.getParameter("streetNumber");
        String streetName = request.getParameter("streetName");
        String apptNumber = request.getParameter("apptNumber");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        Address address = new Address(streetNumber, streetName, apptNumber, city, state, zipCode);
        
        String month = request.getParameter("birthMonth");
        String day = request.getParameter("birthDay");
        String year = request.getParameter("birthYear");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.US);
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        LocalTime time = null;
        LocalDateTime ldt = LocalDateTime.of(date, time);
        
        Customer customer = new Customer(customerID,firstName,lastName,userName,password,address,ldt);
        Customer customerEdit = customerService.editCustomer(customer);
        
        if(customerEdit == null)
            response.put("error", customerEdit);
        else
            response.put("success", customerEdit);
        
        return ResponseEntity.ok().header("Customer", "Customer Edit").body(response);
        
    }
    
    

    
    
    
}
