package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOCoreProductImplTest
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    AbstractSpringDAO<CoreProduct> coreProductDAO;

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
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
    }

    // @Test
    public void test()
    {
        fail("Not yet implemented"); // TODO
    }

//    @Override
    protected AbstractSpringDAO<CoreProduct> getDAO()
    {
        this.coreProductDAO = new DAOCoreProductImpl(dataSource);

        return coreProductDAO;
    }

//    @Override
    protected CoreProduct newInstance()
    {
        String name = "testCoreProduct";
        List<Tag> tags = new ArrayList<>();
        Type type = Type.BEER;
        String subType = "testSubType";
        String description = "testDescription";

        CoreProduct coreProduct = new CoreProduct(name, tags, type, subType, description);

        return coreProduct;

    }

}
