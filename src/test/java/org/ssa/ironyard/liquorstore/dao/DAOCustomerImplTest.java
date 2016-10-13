package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.ssa.ironyard.liquorstore.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOCustomerImplTest extends AbstractSpringDAOTest<Customer>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    static AbstractSpringDAO<Customer> customerDAO;
    
    static List<Customer> rawCustomers;
    static List<Customer> customersInDB;
    
    static Customer testCustomer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
        
        customerDAO = new DAOCustomerImpl(dataSource);
        

        rawCustomers = new ArrayList<>();
        customersInDB = new ArrayList<>();
        
        BufferedReader reader = null;
        
        try
        {
        
        reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_CUSTOMER_DATA.csv"),
                Charset.defaultCharset());

        String line = null;

        while (null != (line = reader.readLine()))
        {
            String[] customerData = line.split(",");
            String userName = customerData[0];
            Password password = new BCryptSecurePassword().secureHash(customerData[1]);
            String firstName = customerData[2];
            String lastName = customerData[3];
            Address address = new Address();
            address.setStreet(customerData[4]);
            address.setCity(customerData[5]);
            address.setState(State.ALABAMA);
            address.setZip(new ZipCode(customerData[7]));
            String[] dateTime = customerData[8].split(" ");
            String[] date = dateTime[0].split("-");
            LocalDate localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]));
            String[] time = dateTime[1].split(":");
            LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]),
                    Integer.parseInt(time[2]));
            LocalDateTime birthDate = LocalDateTime.of(localDate, localTime);

            testCustomer = new Customer(userName, password, firstName, lastName, address, birthDate);

            rawCustomers.add(testCustomer);

            Customer customerFromDB = customerDAO.insert(testCustomer);

            customersInDB.add(customerFromDB);
        }
        }
        catch(IOException iex)
        {
            System.err.println(iex.getStackTrace());
            throw iex;
        }
        finally
        {
            if(reader != null)
                reader.close();
        }
        

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        customerDAO.clear();
        
    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
        customerDAO = new DAOCustomerImpl(dataSource);
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
    }

    @Test
    public void testReadMultipleCustomersByIDs()
    {
        List<Customer> customerSubList = new ArrayList<>();
        List<Customer> testCustomer = new ArrayList<>();
        Set<Integer> customerIdsSet = new HashSet<>();
        List<Integer> customerIdsList = new ArrayList<>();

        while (customerIdsSet.size() < (customersInDB.size() / 2))
        {
            Integer i = (int) (Math.random() * customersInDB.size());
            if (!customerIdsSet.contains(customersInDB.get(i).getId()))
            {
                customerIdsSet.add(customersInDB.get(i).getId());
                customerSubList.add(customersInDB.get(i));
            }
        }

        customerIdsList = customerIdsSet.stream().collect(Collectors.toList());

        testCustomer = customerDAO.readByIds(customerIdsList);

        testCustomer.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        customerSubList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        assertEquals(customerIdsList.size(), customerIdsSet.size());
        assertEquals(customerSubList.size(), testCustomer.size());
        assertTrue(customersInDB.containsAll(customerSubList));
        assertTrue(customerSubList.containsAll(testCustomer));
        assertTrue(testCustomer.containsAll(customerSubList));

        for (int i = 0; i < customerIdsList.size(); i++)
        {
            assertEquals(customerSubList.get(i), testCustomer.get(i));
            assertTrue(customerSubList.get(i).deeplyEquals(testCustomer.get(i)));
        }
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
