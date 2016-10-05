package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.Password;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOAdminImplTest extends AbstractSpringDAOTest<Admin>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    AbstractSpringDAO<Admin> adminDAO;

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
    public void setUpBeforeEachTest() throws Exception
    {
    }

    @After
    public void tearDownAfterEachTest() throws Exception
    {
        
    }

//    @Test
    public void test()
    {
        fail("Not yet implemented"); // TODO
    }

    @Override
    protected AbstractSpringDAO<Admin> getDAO()
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
        
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
        
        Admin admin = new Admin(username, password, firstName, lastName, role);
        
        return admin;
    }

}
