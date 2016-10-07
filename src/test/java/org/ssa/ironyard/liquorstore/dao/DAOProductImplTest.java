package org.ssa.ironyard.liquorstore.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    static List<CoreProduct> rawCoreProducts;
    static List<CoreProduct> coreProductsInDB;
    static List<Tag> rawTags;
    static List<Product> rawProducts;
    static List<Product> productsInDB;

    static AbstractSpringDAO<Product> productDAO;

//    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        productDAO = new DAOProductImpl(dataSource);

        coreProductDAO = new DAOCoreProductImpl(dataSource);
        rawCoreProducts = new ArrayList<>();
        coreProductsInDB = new ArrayList<>();
        
        rawTags = new ArrayList<>();
        rawTags.add(new Tag("dry"));
        rawTags.add(new Tag("white"));
        rawTags.add(new Tag("red"));
        rawTags.add(new Tag("sweet"));
        rawTags.add(new Tag("imported"));
        rawTags.add(new Tag("domestic"));
        
        BufferedReader reader = null;
        
        try
        {
            reader = Files.newBufferedReader(
                    Paths.get("./src/test/resources/MOCK_CORE_PRODUCT_DATA.txt"),
                    Charset.defaultCharset());

            String line;

            while (null != (line = reader.readLine()))
            {
                String[] coreProductData = line.split(":");
                String name = coreProductData[0];
                Type type = Type.getInstance(coreProductData[1].toLowerCase());
                String subType = coreProductData[2];
                String description = coreProductData[3];
                
                List<Tag> tags = new ArrayList<>();
                
                for(int i = 0; i < 1; i++)
                {
                    Integer randomTagIndex = (int) (Math.random() * rawTags.size());
                    
                    tags.add(rawTags.get(randomTagIndex));
                }
                
                testCoreProduct = new CoreProduct(name, tags, type, subType, description);
                
                rawCoreProducts.add(testCoreProduct);
                coreProductsInDB.add(coreProductDAO.insert(testCoreProduct));
            }
        }
        catch (IOException iex)
        {
            System.err.println(iex);
            throw iex;
        }
        finally
        {
            if (null != reader)
                reader.close();
        }
        
        
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
    public void testProductSearchByTags()
    {
        
    }
    
//    @Test
    public void testProductSearchByTypes()
    {
        
    }
    
//    @Test
    public void testProductSearchByTagsAndTypes()
    {
        
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
        String name = "test core product";
        List<Tag> tags = new ArrayList<>();
        Type type = Type.BEER;
        String subType = "test sub type";
        String description = "test description";
        
        testCoreProduct = coreProductDAO.insert(new CoreProduct(name, tags, type, subType, description));
        
        BaseUnit baseUnit = BaseUnit._12OZ_BOTTLE;
        Integer quantity = 6;
        Integer inventory = 6;
        BigDecimal price = BigDecimal.valueOf(5.55);
        
        Product product = new Product(testCoreProduct, baseUnit , quantity, inventory, price);
        
        return product;
    }

}
