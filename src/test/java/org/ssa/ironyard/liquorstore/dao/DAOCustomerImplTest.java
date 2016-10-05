package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOCustomerImplTest extends AbstractSpringDAOTest<Customer>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    AbstractSpringDAO<Customer> customerDAO;
    

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
        this.customerDAO = new DAOCustomerImpl(dataSource);
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
    }

//    @Test
    public void test()
    {
        fail("Not yet implemented"); // TODO
    }

    @Override
    protected AbstractSpringDAO<Customer> getDAO()
    {
        customerDAO = new DAOCustomerImpl(dataSource);
        
        return customerDAO;
    }

    @Override
    protected Customer newInstance()
    {
        String userName = "testusername";
        Password password = new BCryptSecurePassword().secureHash("testpassword");
        String firstName = "First";
        String lastName = "Last";
        Address address = new Address();
        address.setStreet("123 Main Street");
        address.setCity("Baltimore");
        address.setState(State.ALABAMA);
        address.setZip(new ZipCode("12345"));
        LocalDateTime birthDate = LocalDateTime.of(LocalDate.of(2016, 5, 25), LocalTime.of(10, 15, 25));   
        
        Customer customer = new Customer(userName, password, firstName, lastName, address, birthDate);
        
        return customer;
    }

}
