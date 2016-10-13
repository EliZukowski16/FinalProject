package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOCoreProductImplTest extends AbstractSpringDAOTest<CoreProduct>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<CoreProduct> coreProductDAO;

    static List<CoreProduct> rawCoreProducts;
    static List<CoreProduct> coreProductsInDB;

    static CoreProduct testCoreProduct;

    static Map<Tag, List<CoreProduct>> tagMap;
    static Map<Type, List<CoreProduct>> typeMap;

    static List<Tag> rawTags;
    static List<Type> rawTypes;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        coreProductDAO = new DAOCoreProductImpl(dataSource);

        rawCoreProducts = new ArrayList<>();
        coreProductsInDB = new ArrayList<>();

        tagMap = new HashMap<>();
        typeMap = new HashMap<>();

        typeMap.put(Type.BEER, new ArrayList<>());
        typeMap.put(Type.SPIRITS, new ArrayList<>());
        typeMap.put(Type.WINE, new ArrayList<>());

        rawTags = new ArrayList<>();

        rawTags.add(new Tag("dry"));
        tagMap.put(new Tag("dry"), new ArrayList<>());

        rawTags.add(new Tag("white"));
        tagMap.put(new Tag("white"), new ArrayList<>());

        rawTags.add(new Tag("red"));
        tagMap.put(new Tag("red"), new ArrayList<>());

        rawTags.add(new Tag("sweet"));
        tagMap.put(new Tag("sweet"), new ArrayList<>());

        rawTags.add(new Tag("imported"));
        tagMap.put(new Tag("imported"), new ArrayList<>());

        rawTags.add(new Tag("domestic"));
        tagMap.put(new Tag("domestic"), new ArrayList<>());

        BufferedReader reader = null;

        try
        {

            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_CORE_PRODUCT_DATA.txt"),
                    Charset.defaultCharset());

            String line = null;

            while (null != (line = reader.readLine()))
            {
                String[] coreProductData = line.split(":");
                String name = coreProductData[0];
                Type type = Type.getInstance(coreProductData[1].toLowerCase());
                String subType = coreProductData[2];
                String description = coreProductData[3];

                List<Tag> tags = new ArrayList<>();

                List<Integer> tagsChosen = new ArrayList<>();

                for (int i = 0; i < 3; i++)
                {
                    Integer randomTagIndex = (int) (Math.random() * rawTags.size());

                    while (tagsChosen.contains(randomTagIndex))
                    {
                        randomTagIndex = (int) (Math.random() * rawTags.size());
                    }

                    tags.add(rawTags.get(randomTagIndex));

                    tagsChosen.add(randomTagIndex);
                }

                testCoreProduct = new CoreProduct(name, tags, type, subType, description);

                rawCoreProducts.add(testCoreProduct);

                CoreProduct coreProductFromDB = coreProductDAO.insert(testCoreProduct);

                coreProductsInDB.add(coreProductFromDB);
            }
        }
        catch (IOException iex)
        {
            System.err.println(iex.getStackTrace());
            throw iex;
        }
        finally
        {
            if (reader != null)
                reader.close();
        }

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        coreProductDAO.clear();

    }

    @Before
    public void setUpBeforeEach() throws Exception
    {
    }

    @After
    public void tearDownAfterEach() throws Exception
    {
    }

    @Test
    public void testReadMultipleCoreProductsByIDs()
    {
        List<CoreProduct> coreProductSubList = new ArrayList<>();
        List<CoreProduct> testCoreProducts = new ArrayList<>();
        Set<Integer> coreProductIdsSet = new HashSet<>();
        List<Integer> coreProductIdsList = new ArrayList<>();

        while (coreProductIdsSet.size() < (coreProductsInDB.size() / 2))
        {
            Integer i = (int) (Math.random() * coreProductsInDB.size());
            if (!coreProductIdsSet.contains(coreProductsInDB.get(i).getId()))
            {
                coreProductIdsSet.add(coreProductsInDB.get(i).getId());
                coreProductSubList.add(coreProductsInDB.get(i));
            }
        }

        coreProductIdsList = coreProductIdsSet.stream().collect(Collectors.toList());

        testCoreProducts = coreProductDAO.readByIds(coreProductIdsList);

        testCoreProducts.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        coreProductSubList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        assertEquals(coreProductIdsList.size(), coreProductIdsSet.size());
        assertEquals(coreProductSubList.size(), testCoreProducts.size());
        assertTrue(coreProductsInDB.containsAll(coreProductSubList));
        assertTrue(coreProductSubList.containsAll(testCoreProducts));
        assertTrue(testCoreProducts.containsAll(coreProductSubList));

        for (int i = 0; i < coreProductIdsList.size(); i++)
        {
            assertEquals(coreProductSubList.get(i), testCoreProducts.get(i));
            assertEquals(coreProductSubList.get(i).getName(), testCoreProducts.get(i).getName());
            assertEquals(coreProductSubList.get(i).getSubType(), testCoreProducts.get(i).getSubType());
            assertEquals(coreProductSubList.get(i).getType(), testCoreProducts.get(i).getType());
            // assertTrue(coreProductSubList.get(i).deeplyEquals(testCoreProducts.get(i)));
        }
    }

    @Override
    protected AbstractSpringDAO<CoreProduct> getDAO()
    {
        DAOCoreProductImplTest.coreProductDAO = new DAOCoreProductImpl(dataSource);

        return coreProductDAO;
    }

    @Override
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
