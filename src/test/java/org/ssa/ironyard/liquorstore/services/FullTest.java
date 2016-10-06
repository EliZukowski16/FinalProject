package org.ssa.ironyard.liquorstore.services;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.AbstractSpringDAO;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOAdminImpl;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProductImpl;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOCustomerImpl;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOOrderImpl;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.dao.DAOProductImpl;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.mysql.cj.jdbc.MysqlDataSource;

public class FullTest
{
    
    AdminServiceImpl adminService;
    CustomerServiceImpl custService;
    AnalyticsServiceImpl anService;
    CoreProductServiceImpl cpService;
    OrdersServiceImpl orderService;
    ProductServiceImpl prodService;
    SalesServiceImpl salesService;
    
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
    Product prod2;
    
    Customer ci;
    Admin adi;
    CoreProduct cpi;
    Order oi;
    Product pi;
    
    
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
        
    }
    
    @Before
    public void setup()
    {
        daoAdmin = new DAOAdminImpl(dataSource);
        daoCoreProduct = new DAOCoreProductImpl(dataSource);
        daoCustomer = new DAOCustomerImpl(dataSource);
        daoOrder = new DAOOrderImpl(dataSource);
        daoProduct = new DAOProductImpl(dataSource);
        
        
        
       
        
        adminService = new AdminServiceImpl(daoAdmin);
        custService = new CustomerServiceImpl(daoCustomer);
        cpService = new CoreProductServiceImpl(daoCoreProduct);
        orderService = new OrdersServiceImpl(daoOrder);
        prodService = new ProductServiceImpl(daoProduct);
        
        Address address = new Address();
        address.setStreet("111 road");
        address.setCity("Columbia");
        address.setZip(new ZipCode("21122"));
        address.setState(State.ARIZONA);
        LocalDate d = LocalDate.of(1992, 12, 24);
        LocalTime t = LocalTime.of(12, 00);
        LocalDateTime ldt = LocalDateTime.of(d,t);
        
        Password p = new BCryptSecurePassword().secureHash("password");
        
        c = new Customer("username",p,"Michael","Patrick",address,ldt);
        ad = new Admin("username",p,"Joe","Patrick",1);
        
        List<Tag> tags = new ArrayList();
        tags.add(new Tag("beer"));
        tags.add(new Tag("light beer"));
        cp = new CoreProduct("Bud Light", tags, Type.BEER, "Light Beer", "Tastes Great");
        
        
        
        prod = new Product(cp,BaseUnit._12OZ_BOTTLE,6,100,BigDecimal.valueOf(20.00));
        prod2 = new Product(cp,BaseUnit._12OZ_CAN,30,50,BigDecimal.valueOf(50.00));
        
        List<OrderDetail> odList = new ArrayList();
        
        ord = new Order(c,ldt,BigDecimal.valueOf(50.00),odList);
        
        OrderDetail od = new OrderDetail(prod,6,BigDecimal.valueOf(15.00));
        OrderDetail od2 = new OrderDetail(prod2,12,BigDecimal.valueOf(20.00));
        
        
        
        
        ci = custService.addCustomer(c);
        adi = adminService.addAdmin(ad);
        cpi = cpService.addCoreProduct(cp);
        pi = prodService.addProduct(prod);
        oi = orderService.addOrder(ord);
        
        
    }

    @Test
    public void addTest()
    {
       Customer cAdd = custService.addCustomer(c);
       c = new Customer(cAdd.getId(),c.getUserName(),c.getPassword(),c.getFirstName(),c.getLastName(),c.getAddress(),c.getBirthDate());
       assertTrue(cAdd.deeplyEquals(c));
       assertTrue(custService.readCustomer(cAdd.getId()) != null);
       
       Admin adAdd = adminService.addAdmin(ad);
       ad = new Admin(adAdd.getId(),ad.getUsername(),ad.getPassword(),ad.getFirstName(),ad.getLastName(),ad.getRole());
       assertTrue(adAdd.deeplyEquals(ad));
       assertTrue(adminService.readAdmin(adAdd.getId()) != null);
       
       CoreProduct cpAdd = cpService.addCoreProduct(cp);
       cp = new CoreProduct(cpAdd.getId(),cp.getName(),cp.getTags(),cp.getType(),cp.getSubType(),cp.getDescription());
       assertTrue(cpAdd.deeplyEquals(cp));
       assertTrue(cpService.readCoreProduct(cpAdd.getId()) != null);
       
       Order ordAdd = orderService.addOrder(ord);
       ord = new Order(ordAdd.getId(),ord.getCustomer(),ord.getDate(),ord.getTotal(),ord.getoD());
       assertTrue(ordAdd.deeplyEquals(ord));
       assertTrue(orderService.readOrder(ordAdd.getId()) != null);
       
       Product prodAdd = prodService.addProduct(prod);
       prod = new Product(prodAdd.getId(),prod.getCoreProduct(),prod.getBaseUnit(),prod.getQuantity(),prod.getInventory(),prod.getPrice());
       assertTrue(prodAdd.deeplyEquals(prod));
       assertTrue(prodService.readProduct(prodAdd.getId()) != null);
       
    }
    
    //@Test
    public void readTest()
    {
        
        Customer cRead = custService.readCustomer(ci.getId());
        assertTrue(cRead != null);
        assertTrue(cRead.deeplyEquals(ci));
        
        
        Admin adRead = adminService.readAdmin(adi.getId());
        assertTrue(adRead != null);
        assertTrue(adRead.deeplyEquals(adi));
        
        
        CoreProduct cpRead = cpService.readCoreProduct(cpi.getId());
        assertTrue(cpRead != null);
        assertTrue(cpRead.deeplyEquals(cpi));
        
        
        Order oRead = orderService.readOrder(oi.getId());
        assertTrue(oRead != null);
        assertTrue(oRead.deeplyEquals(oi));
        
        Product pRead = prodService.readProduct(pi.getId());
        assertTrue(pRead != null);
        assertTrue(pRead.deeplyEquals(pi));      
        
        
    }
    
    //@Test
    public void readAllTest()
    {
        Address address = new Address();
        address.setStreet("222 Road");
        address.setCity("Columbia");
        address.setZip(new ZipCode("44444"));
        address.setState(State.ALABAMA);
        LocalDateTime ldt = LocalDateTime.of(10,30,52,0,0,0);
        
        Password pw = new BCryptSecurePassword().secureHash("PW");
        
        Customer c2 = new Customer("UN",pw,"Joe","Pat",address,ldt);
        Admin ad2 = new Admin("UN",pw,"Mikr","John",2);
        
        List<Tag> tags = new ArrayList();
        tags.add(new Tag("Whiskey"));
        tags.add(new Tag("Bourbon"));
        CoreProduct cp2 = new CoreProduct("Jack Danials", tags, Type.SPIRITS, "Whiskey", "yay");
        
        List<OrderDetail> odList = new ArrayList();
        OrderDetail od = new OrderDetail(1,prod,6,BigDecimal.valueOf(15.00));
        OrderDetail od2 = new OrderDetail(2,prod2,12,BigDecimal.valueOf(20.00));
        odList.add(od);
        odList.add(od2);
        Order ord2 = new Order(c,ldt,BigDecimal.valueOf(50.00),odList);
        
        Product prod2 = new Product(cp,BaseUnit._750ML_BOTTLE,1,50,BigDecimal.valueOf(20.00));
        
        custService.addCustomer(c2);
        adminService.addAdmin(ad2);
        cpService.addCoreProduct(cp2);
        orderService.addOrder(ord2);
        prodService.addProduct(prod2);
        
        List<Customer> cList = custService.readAllCustomers();
        assertTrue(cList != null && cList.size() == 2);
        assertTrue(cList.contains(c2) && cList.contains(c));
        
        List<Admin> adList = adminService.readAllAdmins();
        assertTrue(adList != null && cList.size() == 2);
        assertTrue(adList.contains(ad) && adList.contains(ad2));
        
        List<CoreProduct> cpList = cpService.readAllCoreProduct();
        assertTrue(cpList != null && cpList.size() == 2);
        assertTrue(cpList.contains(cp) && cpList.contains(cp2));
        
        List<Order>oList = orderService.readAllOrder();
        assertTrue(oList != null && oList.size() == 2);
        assertTrue(oList.contains(od) && oList.contains(od2));
        
        List<Product> pList = prodService.readAllProducts();
        assertTrue(pList != null && pList.size() == 2);
        assertTrue(pList.contains(prod) && pList.contains(prod2));
        
    }
    
    //@Test
    public void editTest()
    {
        Address address = new Address();
        address.setStreet("222 Road");
        address.setCity("Columbia");
        address.setZip(new ZipCode("44444"));
        address.setState(State.ALABAMA);
        
        LocalDateTime ldt = LocalDateTime.of(10,30,52,0,0,0);
        
        Password pw = new BCryptSecurePassword().secureHash("PW");
        
        Customer cu = new Customer("UN",pw,"Joe","Pat",address,ldt);
        Admin adu = new Admin("UN",pw,"Mikr","John",2);
        
        List<Tag> tags = new ArrayList();
        tags.add(new Tag("Whiskey"));
        tags.add(new Tag("Bourbon"));
        CoreProduct cpu = new CoreProduct("Jack Danials", tags, Type.SPIRITS, "Whiskey", "yay");
        
        List<OrderDetail> odList = new ArrayList();
        OrderDetail od = new OrderDetail(1,prod,6,BigDecimal.valueOf(15.00));
        OrderDetail od2 = new OrderDetail(2,prod2,12,BigDecimal.valueOf(20.00));
        odList.add(od);
        odList.add(od2);
        Order ordu = new Order(c,ldt,BigDecimal.valueOf(50.00),odList);
        
        Product produ = new Product(cp,BaseUnit._750ML_BOTTLE,1,50,BigDecimal.valueOf(20.00));
        
        
        assertTrue(ci.deeplyEquals(custService.readCustomer(ci.getId())));
        Customer cu2 = custService.editCustomer(cu);
        assertTrue(cu2.deeplyEquals(cu));
        assertTrue(cu.deeplyEquals(custService.readCustomer(cu2.getId())));
        
        assertTrue(adi.deeplyEquals(adminService.readAdmin(adi.getId())));
        Admin adu2 = adminService.editAdmin(adu);
        assertTrue(adu2.deeplyEquals(adu));
        assertTrue(adu.deeplyEquals(adminService.readAdmin(adu2.getId())));
        
        assertTrue(cpi.deeplyEquals(cpService.readCoreProduct(cpi.getId())));
        CoreProduct cpu2 = cpService.editCoreProduct(cpu);
        assertTrue(cpu2.deeplyEquals(cpu));
        assertTrue(cpu.deeplyEquals(cpService.readCoreProduct(cpu2.getId())));
        
        assertTrue(oi.deeplyEquals(orderService.readOrder(oi.getId())));
        Order ordu2 = orderService.editOrder(ordu);
        assertTrue(ordu2.deeplyEquals(ordu));
        assertTrue(ordu.deeplyEquals(orderService.readOrder(ordu2.getId())));
        
        assertTrue(pi.deeplyEquals(prodService.readProduct(pi.getId())));
        Product produ2 = prodService.editProduct(produ);
        assertTrue(produ2.deeplyEquals(produ));
        assertTrue(produ.deeplyEquals(prodService.readProduct(produ2.getId())));
    }
    
    //@Test
    public void testDelete()
    {
        assertTrue(custService.deleteCustomer(pi.getId()) == true);
        assertTrue(custService.readCustomer(pi.getId()) == null);
        
        assertTrue(adminService.deleteAdmin(adi.getId()) == true);
        assertTrue(adminService.readAdmin(adi.getId()) == null);
        
        assertTrue(cpService.deleteCoreProduct(cpi.getId()) == true);
        assertTrue(cpService.readCoreProduct(cpi.getId()) == null);
        
        assertTrue(orderService.deleteOrder(oi.getId()) == true);
        assertTrue(orderService.readOrder(oi.getId()) == null);
    }
    
    @After
    public void clear()
    {
        daoAdmin.clear();
        daoCoreProduct.clear();
        daoCustomer.clear();
        daoOrder.clear();
        daoProduct.clear();
    }

}
