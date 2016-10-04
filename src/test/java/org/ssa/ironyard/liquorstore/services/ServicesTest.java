package org.ssa.ironyard.liquorstore.services;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.easymock.EasyMock;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer.Address;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

public class ServicesTest
{
    AdminService adminService;
    CustomerService custService;
    AnalyticsService anService;
    CoreProductService cpService;
    OrdersService orderService;
    ProductService prodService;
    SalesService salesService;
    
    DAOAdmin daoAdmin;
    DAOCoreProduct daoCoreProduct;
    DAOCustomer daoCustomer;
    DAOOrder daoOrder;
    DAOProduct daoProduct;
    
    Customer c;
    Admin ad;
    CoreProduct cp;
    Order ord;
    Product prod;
    
    @Before
    public void setup()
    {
        
        
        adminService = new AdminService(daoAdmin);
        custService = new CustomerService(daoCustomer);
        cpService = new CoreProductService(daoCoreProduct);
        orderService = new OrdersService(daoOrder);
        prodService = new ProductService(daoProduct);
        
        Address address = new Address("111","road","","columbia","MD","21122");
        LocalDate d = LocalDate.of(1992, 12, 24);
        LocalTime t = LocalTime.of(12, 00);
        LocalDateTime ldt = LocalDateTime.of(d,t);
        
        c = new Customer(1,"username","password","Michael","Patrick",address,ldt);
        c.setLoaded(true);
        ad = new Admin(1,"username","password","Joe","Patrick",1);
        c.setLoaded(true);
        
        List<String> tags = new ArrayList();
        tags.add("beer");
        tags.add("light beer");
        cp = new CoreProduct(1,"Bud Light", tags, Type.BEER, "Light Beer", "Tastes Great");
        
        List<OrderDetail> odList = new ArrayList();
        OrderDetail od = new OrderDetail(1,3,6,15.00f);
        OrderDetail od2 = new OrderDetail(2,4,12,20.00f);
        odList.add(od);
        odList.add(od2);
        ord = new Order(1,2,ldt,50.00f,odList);
        
        prod = new Product(1,3,BaseUnit._12OZ_BOTTLE,6,100);
        
        daoCustomer = EasyMock.niceMock(DAOCustomer.class);
        this.custService = new CustomerService(daoCustomer); 
        
        daoAdmin = EasyMock.niceMock(DAOAdmin.class);
        this.adminService = new AdminService(daoAdmin);
        
        daoCoreProduct = EasyMock.niceMock(DAOCoreProduct.class);
        this.cpService = new CoreProductService(daoCoreProduct);
        
        daoOrder = EasyMock.niceMock(DAOOrder.class);
        this.orderService = new OrdersService(daoOrder);
        
        daoProduct = EasyMock.niceMock(DAOProduct.class);
        this.prodService = new ProductService(daoProduct);
    }

    @Test
    public void readTest()
    {
              
        EasyMock.expect(daoCustomer.read(c.getId())).andReturn(c);
        EasyMock.replay(daoCustomer);
        
        Customer cCheck = custService.readCustomer(c.getId());
        assertTrue(cCheck.deeplyEquals(c));
                
        EasyMock.expect(daoAdmin.read(ad.getId())).andReturn(ad);
        EasyMock.replay(daoAdmin);
        
        Admin adCheck = adminService.readAdmin(ad.getId());
        assertTrue(adCheck.deeplyEquals(ad));
        
        EasyMock.expect(daoCoreProduct.read(cp.getId())).andReturn(cp);
        EasyMock.replay(daoCoreProduct);
        
        CoreProduct cpCheck = cpService.readCoreProduct(cp.getId());
        assertTrue(cpCheck.deeplyEquals(cp));
        
        EasyMock.expect(daoOrder.read(ord.getId())).andReturn(ord);
        EasyMock.replay(daoOrder);
        
        Order ordCheck = orderService.readOrder(ord.getId());
        assertTrue(ordCheck.deeplyEquals(ord));
        
        EasyMock.expect(daoProduct.read(prod.getId())).andReturn(prod);
        EasyMock.replay(daoProduct);
        
        Product prodCheck = prodService.readProduct(prod.getId());
        assertTrue(prodCheck.deeplyEquals(prod));

    }
    
    @Test
    public void updateTest()
    {
        EasyMock.expect(daoCustomer.update(c)).andReturn(null);
        EasyMock.replay(daoCustomer);
        assertTrue(custService.editCustomer(c) == null);
    }

}
