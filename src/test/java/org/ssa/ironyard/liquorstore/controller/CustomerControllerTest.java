package org.ssa.ironyard.liquorstore.controller;

import static org.junit.Assert.*;

import java.awt.PageAttributes.OrientationRequestedType;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.jsonModel.JsonProduct;
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
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Password;
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



public class CustomerControllerTest
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
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       adminService = EasyMock.niceMock(AdminServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       cpService = EasyMock.niceMock(CoreProductServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       orderService = EasyMock.niceMock(OrdersServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       prodService = EasyMock.niceMock(ProductServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       anService = EasyMock.niceMock(AnalyticsServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       salesService = EasyMock.niceMock(SalesServiceImpl.class);
       this.custController = new CustomerController(adminService,anService,cpService,custService,orderService,prodService,salesService);
       
       Address address = new Address();
       address.setStreet("111 road");
       address.setCity("Columbia");
       address.setZip(new ZipCode("21122"));
       address.setState(State.ARIZONA);
       LocalDate d = LocalDate.of(1992, 12, 24);
       LocalTime t = LocalTime.of(12, 00);
       LocalDateTime ldt = LocalDateTime.of(d,t);
       Password p = new BCryptSecurePassword().secureHash("password");
       
       c = new Customer(1,"username",p,"Michael","Patrick",address,ldt,true);
       ad = new Admin(1,"username",p,"Joe","Patrick",1,true);
       
       
       List<Tag> tags = new ArrayList();
       tags.add(new Tag("beer"));
       tags.add(new Tag("light beer"));
       cp = new CoreProduct(1,"Bud Light", tags, Type.BEER, "Light Beer", "Tastes Great");
       
      
       
       prod = new Product(1,cp,BaseUnit._12OZ_BOTTLE,6,100,BigDecimal.valueOf(10.00));
       
       List<OrderDetail> odList = new ArrayList();
       OrderDetail od = new OrderDetail(prod,6,BigDecimal.valueOf(15.00));
       OrderDetail od2 = new OrderDetail(prod,12,BigDecimal.valueOf(20.00));
       odList.add(od);
       odList.add(od2);
       ord = new Order(1,c,ldt,BigDecimal.valueOf(50.00),odList,OrderStatus.PENDING);
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
        
        ResponseEntity<Map<String,Customer>> customerMap = this.custController.addCustomer("2",mockRequest);
        
        Customer cust = customerMap.getBody().get("success");
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("error"));
        assertNotEquals(capturedCust.getValue().getId(), cust.getId());
        assertEquals(capturedCust.getValue().getUserName(), cust.getUserName());
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
        
        
        
        assertTrue(customerMap.getBody().containsKey("success"));
        assertFalse(customerMap.getBody().containsKey("errors"));
        assertEquals(capturedCust.getValue().getId(), cust.getId());
        assertEquals(capturedCust.getValue().getUserName(), cust.getUserName());
        assertEquals(capturedCust.getValue().getFirstName(),cust.getFirstName());
        assertEquals(capturedCust.getValue().getLastName(),cust.getLastName());
        assertEquals(capturedCust.getValue().getAddress().getStreet(),cust.getAddress().getStreet());
        assertEquals(capturedCust.getValue().getAddress().getCity(),cust.getAddress().getCity());
        assertEquals(capturedCust.getValue().getAddress().getState(),cust.getAddress().getState());
        assertEquals(capturedCust.getValue().getAddress().getZip().toString(),cust.getAddress().getZip().toString());            
        assertEquals(capturedCust.getValue().getBirthDate(),cust.getBirthDate());
        
    }
    
    
    @Test
    public void testGetProducts()
    {
        List<Product> products = new ArrayList();
        
        String cpName = "Bud Light";
        Tag tag = new Tag("light beer");
        Tag tag2 = new Tag("beer");
        List<Tag> tagList = new ArrayList();
        tagList.add(tag);
        tagList.add(tag2);
        Type type = Type.BEER;
        String cpSubType = "Light Beer";
        String cpDes = "Tastes Great";
        CoreProduct cp = new CoreProduct(1,cpName,tagList,type,cpSubType,cpDes);
        
        BaseUnit bu = BaseUnit._12OZ_CAN;
        Integer q = 12;
        Integer inv = 100;
        BigDecimal price = BigDecimal.valueOf(12.00);
        Product prod = new Product(2,cp,bu,q,inv,price);
        
        BaseUnit bu2 = BaseUnit._12OZ_BOTTLE;
        Integer q2 = 16;
        Integer inv2 = 50;
        BigDecimal price2 = BigDecimal.valueOf(15.00);
        Product prod2 = new Product(3,cp,bu2,q2,inv2,price2);

        products.add(prod);
        products.add(prod2);
        
        EasyMock.expect(prodService.readAllProducts()).andReturn(products);
        EasyMock.replay(prodService);
        
        ResponseEntity<Map<String,List<Product>>> productMap = this.custController.getProducts("12");
        
        List<Product> pList = productMap.getBody().get("success");
        
        
        assertTrue(pList.size() == 2);
        assertTrue(pList.get(0).deeplyEquals(prod));
        assertTrue(pList.get(1).deeplyEquals(prod2));
    }
    
    @Test
    public void testSearchKeyword()
    {
        List<Product> products = new ArrayList();
        
        String cpName = "Bud Light";
        Tag tag = new Tag("light beer");
        Tag tag2 = new Tag("beer");
        List<Tag> tagList = new ArrayList();
        tagList.add(tag);
        tagList.add(tag2);
        Type type = Type.BEER;
        String cpSubType = "Light Beer";
        String cpDes = "Tastes Great";
        CoreProduct cp = new CoreProduct(1,cpName,tagList,type,cpSubType,cpDes);
        
        BaseUnit bu = BaseUnit._12OZ_CAN;
        Integer q = 12;
        Integer inv = 100;
        BigDecimal price = BigDecimal.valueOf(12.00);
        Product prod = new Product(2,cp,bu,q,inv,price);
        
        BaseUnit bu2 = BaseUnit._12OZ_BOTTLE;
        Integer q2 = 16;
        Integer inv2 = 50;
        BigDecimal price2 = BigDecimal.valueOf(15.00);
        Product prod2 = new Product(3,cp,bu2,q2,inv2,price2);

        products.add(prod);
        products.add(prod2);
        
        List<Tag> tags = new ArrayList();
        Tag t = new Tag("");
        Tag t2 = new Tag("light");
        Tag t3 = new Tag("beer");
        tags.add(t);
//        tags.add();
//        tags.add();
        
        String tagsA = "";
       
        
        List<Type> types = new ArrayList();
        Type ty = Type.BEER;
        Type ty2 = Type.WINE;
        types.add(ty);
        types.add(ty2);
        
        String[] typesA = {"beer", "wine"};
        
                
                 
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("keywords", tagsA);
        mockRequest.addParameter("types", typesA);
        
        
        Capture<Product> capturedProdSearch = Capture.<Product>newInstance();
        
        
        EasyMock.expect(prodService.searchProduct(tags,types)).andReturn(products);
        EasyMock.replay(prodService);
        
        
        
        ResponseEntity<Map<String,List<Product>>> searchMap = this.custController.searchKeywordType("12", mockRequest);
        
        List<Product> productList = searchMap.getBody().get("success");
        
        assertTrue(productList.size() == 2);
        assertTrue(productList.get(0).deeplyEquals(prod));
        assertTrue(productList.get(1).deeplyEquals(prod2));
        
    }
    
    //@Test
//    public void testPlaceOrderSucess()
//    {
//        String[] orderDetail = {"bud light","BEER","Light Beer","Taste great","12 oz can","6","25","12.50","1","12.50","wine bottle","WINE","Apple Wine","Tarty but great","750 ml bottle","1","25","15","1","15"};
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
//        mockRequest.addParameter("orderDetail", orderDetail);
//        mockRequest.addParameter("orderMonth", "05");
//        mockRequest.addParameter("orderDay", "15");
//        mockRequest.addParameter("orderYear", "2016");
//        mockRequest.addParameter("total", "27.50");
//        
//        Order ordN = new Order(ord.getCustomer(),ord.getDate(),ord.getTotal(),ord.getoD(),ord.getOrderStatus());
//        
//        EasyMock.expect(orderService.addOrder(ordN)).andReturn(ord);
//        EasyMock.replay(orderService);
//        
//
//        ResponseEntity<Map<String,Order>> orderMap = this.custController.placeOrder(c.getId().toString(),mockRequest);
//        
//        Order ordPlace = orderMap.getBody().get("success");
//        
//        assertTrue(ord.deeplyEquals(ordPlace)); 
//        
//       
//            
//    }
    
    //@Test
//    public void testPlaceOrderOutofStock()
//    {
//        String[] orderDetail = {"bud light","BEER","Light Beer","Taste great","12 oz can","6","25","12.50","1","12.50","wine bottle","WINE","Apple Wine","Tarty but great","750 ml bottle","1","25","15","1","15"};
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
//        mockRequest.addParameter("orderDetail", orderDetail);
//        mockRequest.addParameter("orderMonth", "05");
//        mockRequest.addParameter("orderDay", "15");
//        mockRequest.addParameter("orderYear", "2016");
//        mockRequest.addParameter("total", "27.50");
//        
//        Order ordN = new Order(null,ord.getCustomer(),ord.getDate(),ord.getTotal(),ord.getoD(),ord.getOrderStatus());
//        Order ordS = new Order(null,null,null,ord.getoD(),null);
//        
//        EasyMock.expect(orderService.addOrder(ordN)).andReturn(ordS);
//        EasyMock.replay(orderService);
//        
//
//        ResponseEntity<Map<String,Order>> orderStockMap = this.custController.placeOrder(c.getId().toString(),mockRequest);
//        
//        assertTrue(orderStockMap.getBody().containsKey("outofstock"));
//        Order ordStock = orderStockMap.getBody().get("outofstock");
//        
//        assertTrue(ordS.deeplyEquals(ordStock)); 
//
//    }
    
    //@Test
//    public void testPlaceOrderPriceChange()
//    {
//        String[] orderDetail = {"bud light","BEER","Light Beer","Taste great","12 oz can","6","25","12.50","1","12.50","wine bottle","WINE","Apple Wine","Tarty but great","750 ml bottle","1","25","15","1","15"};
//        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
//        mockRequest.addParameter("orderDetail", orderDetail);
//        mockRequest.addParameter("orderMonth", "05");
//        mockRequest.addParameter("orderDay", "15");
//        mockRequest.addParameter("orderYear", "2016");
//        mockRequest.addParameter("total", "27.50");
//        
//        Order ordN = new Order(null,ord.getCustomer(),ord.getDate(),ord.getTotal(),ord.getoD(),ord.getOrderStatus());
//        Order ordS = new Order(null,ord.getDate(),null,ord.getoD(),null);
//        
//        EasyMock.expect(orderService.addOrder(ordN)).andReturn(ordS);
//        EasyMock.replay(orderService);
//        
//
//        ResponseEntity<Map<String,Order>> orderPriceMap = this.custController.placeOrder(c.getId().toString(),mockRequest);
//        
//        assertTrue(orderPriceMap.getBody().containsKey("pricechange"));
//        Order ordPrice = orderPriceMap.getBody().get("pricechange");
//        
//        assertTrue(ordS.deeplyEquals(ordPrice)); 
//
//    }
    
    //@Test
    public void testPlaceOrder()
    {
        Map<String, Object> map = new HashMap<String, Object>();
               
        
        
        map.put("orderMonth","12");
        map.put("orderDay", "15");
        map.put("orderYear", "2016");
        //map.put("products", product);
        map.put("total", "15.00");
        map.put("orderStatus", "pending");
        
        Order ordN = new Order(ord.getCustomer(),ord.getDate(),ord.getTotal(),ord.getoD(),ord.getOrderStatus());
      
        EasyMock.expect(orderService.addOrder(ordN)).andReturn(ord);
        EasyMock.replay(orderService);
      
        //ResponseEntity<Map<String,Order>> orderMap = this.custController.placeOrder(c.getId().toString(),map);
    
        //assertTrue(orderMap.getBody().containsKey("success"));
        //Order ordPrice = orderMap.getBody().get("success");
    
        //assertTrue(ordN.deeplyEquals(ordPrice)); 
        
       // assertEquals(ordN.getId(),ordPrice.getId());;
//        assertEquals(ordN.getCustomer(),ordPrice.getCustomer());
//        assertEquals(ordN.getDate(),ordPrice.getDate());
//        assertEquals(ordN.getoD(),ordPrice.getoD());
//        assertEquals(ordN.getOrderStatus(),ordPrice.getOrderStatus());
//        assertEquals(ordN.getTotal(),ordPrice.getTotal());
    }
    
    //@Test
    public void testObjectMapper()
    {
        String productS = "{ \"id\" : \"1\", \"qty\" : \"1\", \"price\" : \"15.00\"}";
        ObjectMapper om = new ObjectMapper();
        JsonProduct product = null;
        
        try
        {
            product = om.readValue(productS, JsonProduct.class);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println(product);
    }

}
