package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.User;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOAdminImplTest extends AbstractSpringDAOTest<Admin>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    static AbstractSpringDAO<Admin> adminDAO;
    
    static List<Admin> rawAdmins;
    static List<Admin> adminsInDB;
    
    static Admin testAdmin;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
        
        adminDAO = new DAOAdminImpl(dataSource);
        

        rawAdmins = new ArrayList<>();
        adminsInDB = new ArrayList<>();
        
        BufferedReader reader = null;
        
        try
        {
        
        reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_ADMIN_DATA.csv"),
                Charset.defaultCharset());

        String line = null;

        while (null != (line = reader.readLine()))
        {
            String[] adminData = line.split(",");
            String username = adminData[0];
            Password password = new BCryptSecurePassword().secureHash(adminData[1]);
            String firstName = adminData[2];
            String lastName = adminData[3];
            Integer role = Integer.parseInt(adminData[4]);
            

            testAdmin = new Admin(username, password, firstName, lastName, role);

            rawAdmins.add(testAdmin);

            Admin adminFromDB = adminDAO.insert(testAdmin);

            adminsInDB.add(adminFromDB);
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
        adminDAO.clear();
    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
        adminDAO = new DAOAdminImpl(dataSource);
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
        
    }

    @Test
    public void testReadMultipleAdminsByIDs()
    {
        List<Admin> adminSubList = new ArrayList<>();
        List<Admin> testAdmin = new ArrayList<>();
        Set<Integer> adminIdsSet = new HashSet<>();
        List<Integer> adminIdsList = new ArrayList<>();

        while (adminIdsSet.size() < (adminsInDB.size() / 2))
        {
            Integer i = (int) (Math.random() * adminsInDB.size());
            if (!adminIdsSet.contains(adminsInDB.get(i).getId()))
            {
                adminIdsSet.add(adminsInDB.get(i).getId());
                adminSubList.add(adminsInDB.get(i));
            }
        }

        adminIdsList = adminIdsSet.stream().collect(Collectors.toList());

        testAdmin = adminDAO.readByIds(adminIdsList);

        testAdmin.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        adminSubList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        assertEquals(adminIdsList.size(), adminIdsSet.size());
        assertEquals(adminSubList.size(), testAdmin.size());
        assertTrue(adminsInDB.containsAll(adminSubList));
        assertTrue(adminSubList.containsAll(testAdmin));
        assertTrue(testAdmin.containsAll(adminSubList));

        for (int i = 0; i < adminIdsList.size(); i++)
        {
            assertEquals(adminSubList.get(i), testAdmin.get(i));
            assertTrue(adminSubList.get(i).deeplyEquals(testAdmin.get(i)));
        }
        
    }

    @Override
    protected AbstractSpringDAO<Admin> getDAO()
    {      
        adminDAO = new DAOAdminImpl(dataSource);
        
        return adminDAO;
    }

    @Override
    protected Admin newInstance()
    {
        String username = "testusername";
        Password password = new BCryptSecurePassword().secureHash("testpassword");
        String firstName = "First";
        String lastName = "Last";
        Integer role = 1;
        
        User admin = new Admin(username, password, firstName, lastName, role);
        
        return (Admin) admin;
    }

}
