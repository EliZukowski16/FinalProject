package org.ssa.ironyard.liquorstore.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;
import org.ssa.ironyard.liquorstore.services.AdminServiceImpl;
import org.ssa.ironyard.liquorstore.services.AnalyticsServiceImpl;
import org.ssa.ironyard.liquorstore.services.CoreProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.CustomerServiceImpl;
import org.ssa.ironyard.liquorstore.services.OrdersServiceImpl;
import org.ssa.ironyard.liquorstore.services.ProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.SalesServiceImpl;

public class AdminControllerTest
{
    AdminServiceImpl adminService;
    CustomerServiceImpl custService;
    AnalyticsServiceImpl anService;
    CoreProductServiceImpl cpService;
    OrdersServiceImpl orderService;
    ProductServiceImpl prodService;
    SalesServiceImpl salesService;
    
    
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
       custService = EasyMock.niceMock(CustomerServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       adminService = EasyMock.niceMock(AdminServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       cpService = EasyMock.niceMock(CoreProductServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       orderService = EasyMock.niceMock(OrdersServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       prodService = EasyMock.niceMock(ProductServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       anService = EasyMock.niceMock(AnalyticsServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       salesService = EasyMock.niceMock(SalesServiceImpl.class);
       this.adminController = new AdminController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       Address address = new Address();
       address.setStreet("111 road");
       address.setCity("Columbia");
       address.setZip(new ZipCode("21122"));
       address.setState(State.ARIZONA);
       LocalDate d = LocalDate.of(1992, 12, 24);
       LocalTime t = LocalTime.of(12, 00);
       LocalDateTime ldt = LocalDateTime.of(d,t);
       
       Password p = new BCryptSecurePassword().secureHash("password");
       c = new Customer(1,"username",p,"Michael","Patrick",address,ldt, true);

       ad = new Admin(1,"username",p,"Joe","Patrick",1);

       
       List<Tag> tags = new ArrayList();
       tags.add(new Tag("beer"));
       tags.add(new Tag("light beer"));
       cp = new CoreProduct(1,"Bud Light", tags, Type.BEER, "Light Beer", "Tastes Great");
       
      
       
       prod = new Product(1,cp,BaseUnit._12OZ_BOTTLE,6,100,BigDecimal.valueOf(50.00));
    }
    
    @Test
    public void testGetAllCustomers()
    {
        
    }
    
    @Test
    public void testGetCustomer()
    {
  
        EasyMock.expect(custService.readCustomer(c.getId())).andReturn(c);
        EasyMock.replay(custService);
        
        ResponseEntity<Map<String,Customer>> customerMap = this.adminController.getCustomer(c.getId().toString());
        
        Customer cust = customerMap.getBody().get("success");
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("error"));
        assertEquals(c.getId(), cust.getId());
        assertEquals(c.getUserName(), cust.getUserName());
        assertEquals(c.getPassword(),cust.getPassword());
        assertEquals(c.getFirstName(),cust.getFirstName());
        assertEquals(c.getLastName(),cust.getLastName());
        assertEquals(c.getAddress().getStreet(),cust.getAddress().getStreet());
        assertEquals(c.getAddress().getCity(),cust.getAddress().getCity());
        assertEquals(c.getAddress().getState(),cust.getAddress().getState());
        assertEquals(c.getAddress().getZip().toString(),cust.getAddress().getZip().toString());
        assertEquals(c.getBirthDate(),cust.getBirthDate());
    }
    
    //@Test
//    public void testDeleteCustomer()
//    {
//        boolean yep = false;
//        
//        EasyMock.expect(custService.deleteCustomer(c.getId())).andAnswer(yep);
//        EasyMock.replay(custService);
//        
//        boolean del = this.adminController.deleteCustomer(c.getId().toString());
//       
//        assertTrue(del == true);
//    }
    
    @Test
    public void testGetProducts()
    {
        
    }
    
    @Test
    public void testGetProduct()
    {
        EasyMock.expect(prodService.readProduct(prod.getId())).andReturn(prod);
        EasyMock.replay(prodService);
        
        ResponseEntity<Map<String,Product>> customerMap = this.adminController.getProdcut(prod.getId().toString());
        
        Product p = customerMap.getBody().get("success");
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("error"));
        assertEquals(prod.getId(),p.getId());
        assertEquals(prod.getCoreProduct(),p.getCoreProduct());
        assertEquals(prod.getBaseUnit(),p.getBaseUnit());
        assertEquals(prod.getQuantity(),p.getQuantity());
        assertEquals(prod.getInventory(),p.getInventory());
        assertEquals(prod.getPrice(),p.getPrice());

        
    }
    
    //@Test
    public void testAddProduct()
    {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("CoreProdcutID", "3");
        mockRequest.addParameter("baseUnit", "_12OZ_Bottle");
        mockRequest.addParameter("quanity", "6");
        mockRequest.addParameter("inventory", "100");
        
        Capture<Product> capturedProd = Capture.<Product>newInstance();
        
        EasyMock.expect(prodService.addProduct(EasyMock.capture(capturedProd))).andReturn(prod);
        EasyMock.replay(custService);
        
        ResponseEntity<Map<String,Product>> productMap = this.adminController.addProduct(mockRequest);
        
        Product pr = productMap.getBody().get("success");
        
        assertTrue(productMap.getBody().containsKey("success"));
        assertFalse(productMap.getBody().containsKey("errors"));
        assertTrue(capturedProd.getValue().getId() == null);
        assertNotEquals(capturedProd.getValue().getId(), pr.getId());
        assertEquals(capturedProd.getValue().getCoreProduct(), pr.getCoreProduct());
        assertEquals(capturedProd.getValue().getBaseUnit(), pr.getBaseUnit());
        assertEquals(capturedProd.getValue().getQuantity(), pr.getQuantity());
        assertEquals(capturedProd.getValue().getInventory(), pr.getInventory());
    }
    
    //@Test
    public void testEditProduct()
    {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("CoreProdcutID", "3");
        mockRequest.addParameter("baseUnit", "12 oz bottle");
        mockRequest.addParameter("quanity", "6");
        mockRequest.addParameter("inventory", "100");
        
        Capture<Product> capturedProd = Capture.<Product>newInstance();
        
        EasyMock.expect(prodService.editProduct(EasyMock.capture(capturedProd))).andReturn(prod);
        EasyMock.replay(custService);
        
        ResponseEntity<Map<String,Product>> productMap = this.adminController.editProduct(prod.getId().toString(),mockRequest);
        
        Product pr = productMap.getBody().get("success");
        
        assertTrue(productMap.getBody().containsKey("success"));
        assertFalse(productMap.getBody().containsKey("errors"));
        assertTrue(capturedProd.getValue().getId() == null);
        assertNotEquals(capturedProd.getValue().getId(), pr.getId());
        assertEquals(capturedProd.getValue().getCoreProduct(), pr.getCoreProduct());
        assertEquals(capturedProd.getValue().getBaseUnit(), pr.getBaseUnit());
        assertEquals(capturedProd.getValue().getQuantity(), pr.getQuantity());
        assertEquals(capturedProd.getValue().getInventory(), pr.getInventory());
    }

}
