package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
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
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOProductImplTest extends AbstractSpringDAOTest<Product>
{

    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static CoreProduct testCoreProduct;

    AbstractSpringDAO<Product> productDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        coreProductDAO = new DAOCoreProductImpl(dataSource);

        String name = "testCoreProduct";
        List<Tag> tags = new ArrayList<>();
        Type type = Type.BEER;
        String subType = "testSubType";
        String description = "test Description";

        testCoreProduct = coreProductDAO.insert(new CoreProduct(name, tags, type, subType, description));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        coreProductDAO.clear();
    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
        productDAO = new DAOProductImpl(dataSource);
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

    @Override
    protected AbstractSpringDAO<Product> getDAO()
    {
        productDAO = new DAOProductImpl(dataSource);

        return productDAO;
    }

    @Override
    protected Product newInstance()
    {
        BaseUnit baseUnit = BaseUnit._12OZ_BOTTLE;
        Integer quantity = 6;
        Integer inventory = 6;
        BigDecimal price = BigDecimal.valueOf(5.55);
        
        Product product = new Product(testCoreProduct, baseUnit , quantity, inventory, price);
        
        return product;
    }

}
