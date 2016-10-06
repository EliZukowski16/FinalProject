package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOOrderImplTest extends AbstractSpringDAOTest<Order>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static AbstractSpringDAO<Product> productDAO;
    static AbstractSpringDAO<Customer> customerDAO;
    static CoreProduct testCoreProduct;
    static Product testProduct;
    static Customer testCustomer;
    
    AbstractSpringDAO<Order> orderDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        coreProductDAO = new DAOCoreProductImpl(dataSource);
        productDAO = new DAOProductImpl(dataSource);
        customerDAO = new DAOCustomerImpl(dataSource);
        

        String name = "testCoreProduct";
        List<Tag> tags = new ArrayList<>();
        Type type = Type.BEER;
        String subType = "testSubType";
        String description = "test Description";

        testCoreProduct = coreProductDAO.insert(new CoreProduct(name, tags, type, subType, description));
        
        BaseUnit baseUnit = BaseUnit._12OZ_BOTTLE;
        Integer quantity = 6;
        Integer inventory = 6;
        BigDecimal price = BigDecimal.valueOf(5.55);
        
        testProduct = productDAO.insert(new Product(testCoreProduct, baseUnit, quantity, inventory, price));
        
        String userName = "testcustname";
        Password password = new BCryptSecurePassword().secureHash("testpassword");
        String firstName = "First";
        String lastName = "Last";
        Address address = new Address();
        address.setStreet("123 Main Street");
        address.setCity("Baltimore");
        address.setState(State.ALABAMA);
        address.setZip(new ZipCode("12345"));
        LocalDateTime birthDate = LocalDateTime.of(LocalDate.of(2016, 5, 25), LocalTime.of(10, 15, 25));
        
        testCustomer = customerDAO.insert(new Customer(userName, password, firstName, lastName, address, birthDate));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        customerDAO.clear();
        productDAO.clear();
        coreProductDAO.clear();
    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
        orderDAO = new DAOOrderImpl(dataSource);
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
        orderDAO.clear();
    }

//    @Test
    public void test()
    {
        fail("Not yet implemented"); // TODO
    }

    @Override
    protected AbstractSpringDAO<Order> getDAO()
    {
        orderDAO = new DAOOrderImpl(dataSource);
        
        return orderDAO;
    }

    @Override
    protected Order newInstance()
    {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2016, 5, 12), LocalTime.of(5, 12, 25));
        BigDecimal total = BigDecimal.valueOf(50.00);
        List<OrderDetail> oD = new ArrayList<>();
        
        Order order = new Order(testCustomer, date, total, oD);
        
        return order;
    }

}
