package org.ssa.ironyard.liquorstore.controller;

import static org.junit.Assert.*;

import java.awt.PageAttributes.OrientationRequestedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;
import org.ssa.ironyard.liquorstore.services.AdminService;
import org.ssa.ironyard.liquorstore.services.AnalyticsService;
import org.ssa.ironyard.liquorstore.services.CoreProductService;
import org.ssa.ironyard.liquorstore.services.CustomerService;
import org.ssa.ironyard.liquorstore.services.OrdersService;
import org.ssa.ironyard.liquorstore.services.ProductService;
import org.ssa.ironyard.liquorstore.services.SalesService;



public class CustomerControllerTest
{

    AdminService adminService;
    CustomerService custService;
    AnalyticsService anService;
    CoreProductService cpService;
    OrdersService orderService;
    ProductService prodService;
    SalesService salesService;
    
    
    AdminController adminController;
    CustomerController custController;
    
    Customer c;
    Admin ad;
    CoreProduct cp;
    Order ord;
    Product prod;
    
    Customer ci;
    Admin adi;
    CoreProduct cpi;
    Order oi;
    Product pi;
    
    @Before
    public void setup()
    {
       custService = EasyMock.niceMock(CustomerService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       adminService = EasyMock.niceMock(AdminService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       cpService = EasyMock.niceMock(CoreProductService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       orderService = EasyMock.niceMock(OrdersService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       prodService = EasyMock.niceMock(ProductService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       anService = EasyMock.niceMock(AnalyticsService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       salesService = EasyMock.niceMock(SalesService.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       Address address = new Address();
       address.setStreet("111 road");
       address.setCity("Columbia");
       address.setZip(new ZipCode("21122"));
       address.setState(State.ARIZONA);
       LocalDate d = LocalDate.of(1992, 12, 24);
       LocalTime t = LocalTime.of(12, 00);
       LocalDateTime ldt = LocalDateTime.of(d,t);
       Password p = new BCryptSecurePassword().secureHash("password")
       
       c = new Customer(1,"Michael","Patrick","username",p,address,ldt);
       ad = new Admin(1,"username","password","Joe","Patrick",1);
       
       c = new Customer(1,"username","password","Michael","Patrick",address,ldt);
       c.setLoaded(true);
       ad = new Admin(1,"username","password","Joe","Patrick",1);
       c.setLoaded(true);
       
       List<Tag> tags = new ArrayList();
       tags.add(new Tag("beer"));
       tags.add(new Tag("light beer"));
       cp = new CoreProduct(1,"Bud Light", tags, Type.BEER, "Light Beer", "Tastes Great");
       
      
       
       prod = new Product(1,cp,BaseUnit._12OZ_BOTTLE,6,100);
       
       List<OrderDetail> odList = new ArrayList();
       OrderDetail od = new OrderDetail(1,prod,6,15.00f);
       OrderDetail od2 = new OrderDetail(2,prod,12,20.00f);
       odList.add(od);
       odList.add(od2);
       ord = new Order(1,c,ldt,50.00f,odList);
    }
    
    @Test
    public void testAddCustomer()
    {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("userName", "username");
        mockRequest.addParameter("password", "password");
        mockRequest.addParameter("firstName", "Michael");
        mockRequest.addParameter("lastName", "Patrick");
        mockRequest.addParameter("street", "111 road");
        mockRequest.addParameter("city", "Columbia");
        mockRequest.addParameter("state", "AZ");
        mockRequest.addParameter("zipCode", "21122");
        mockRequest.addParameter("birthMonth", "12");
        mockRequest.addParameter("birthDay", "24");
        mockRequest.addParameter("birthYear", "1992");
        
        Capture<Customer> capturedCust = Capture.<Customer>newInstance();
        
        
        EasyMock.expect(custService.addCustomer(EasyMock.capture(capturedCust))).andReturn(c);
        EasyMock.replay(custService);
        
        ResponseEntity<Map<String,Customer>> customerMap = this.custController.addCustomer(mockRequest);
        
        Customer cust = customerMap.getBody().get("success");
        
        
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("error"));
        assertNotEquals(capturedCust.getValue().getId(), cust.getId());
        assertEquals(capturedCust.getValue().getUserName(), cust.getUserName());
        assertEquals(capturedCust.getValue().getPassword(),cust.getPassword());
        assertEquals(capturedCust.getValue().getFirstName(),cust.getFirstName());
        assertEquals(capturedCust.getValue().getLastName(),cust.getLastName());
        assertEquals(capturedCust.getValue().getAddress().getStreet(),cust.getAddress().getStreet());
        assertEquals(capturedCust.getValue().getAddress().getCity(),cust.getAddress().getCity());
        assertEquals(capturedCust.getValue().getAddress().getState(),cust.getAddress().getState());
        assertEquals(capturedCust.getValue().getAddress().getZip().toString(),cust.getAddress().getZip().toString());        
        assertEquals(capturedCust.getValue().getBirthDate(),cust.getBirthDate());
    }
    
    @Test
    public void testEditCustomer()
    {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("userName", "username");
        mockRequest.addParameter("password", "password");
        mockRequest.addParameter("firstName", "Michael");
        mockRequest.addParameter("lastName", "Patrick");
        mockRequest.addParameter("street", "111 road");
        mockRequest.addParameter("city", "Columbia");
        mockRequest.addParameter("state", "AZ");
        mockRequest.addParameter("zipCode", "21122");
        mockRequest.addParameter("birthMonth", "12");
        mockRequest.addParameter("birthDay", "24");
        mockRequest.addParameter("birthYear", "1992");
        
        Capture<Customer> capturedCust = Capture.<Customer>newInstance();
        
        
        EasyMock.expect(custService.editCustomer(EasyMock.capture(capturedCust))).andReturn(c);
        EasyMock.replay(custService);
        
        ResponseEntity<Map<String,Customer>> customerMap = this.custController.editCustomer(c.getId().toString(),mockRequest);
        
        Customer cust = customerMap.getBody().get("success");
        
        System.out.println(capturedCust.getValue().getAddress().getZip());
        System.out.println(cust.getAddress().getZip());
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("errors"));
        assertEquals(capturedCust.getValue().getId(), cust.getId());
        assertEquals(capturedCust.getValue().getUserName(), cust.getUserName());
        assertEquals(capturedCust.getValue().getPassword(),cust.getPassword());
        assertEquals(capturedCust.getValue().getFirstName(),cust.getFirstName());
        assertEquals(capturedCust.getValue().getLastName(),cust.getLastName());
        assertEquals(capturedCust.getValue().getAddress().getStreet(),cust.getAddress().getStreet());
        assertEquals(capturedCust.getValue().getAddress().getCity(),cust.getAddress().getCity());
        assertEquals(capturedCust.getValue().getAddress().getState(),cust.getAddress().getState());
        assertEquals(capturedCust.getValue().getAddress().getZip().toString(),cust.getAddress().getZip().toString());            
        assertEquals(capturedCust.getValue().getBirthDate(),cust.getBirthDate());
        
    }

}
