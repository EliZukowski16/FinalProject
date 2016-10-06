package org.ssa.ironyard.liquorstore.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOCustomerImplTest extends AbstractSpringDAOTest<Customer>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=Fireproof!007mc&useServerPrpStmts=true";
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



    @Override
    protected AbstractSpringDAO<Customer> getDAO()
    {
        customerDAO = new DAOCustomerImpl(dataSource);
        
        return customerDAO;
    }

    @Override
    protected Customer newInstance()
    {
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
        
        User customer = new Customer(userName, password, firstName, lastName, address, birthDate);
        
        return (Customer) customer;
    }

}
