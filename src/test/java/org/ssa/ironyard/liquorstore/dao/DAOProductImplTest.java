package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOProductImplTest extends AbstractSpringDAOTest<Product>
{

    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static AbstractSpringDAO<Product> productDAO;

    static CoreProduct testCoreProduct;
    static List<CoreProduct> rawCoreProducts;
    static List<CoreProduct> coreProductsInDB;
    static List<Tag> rawTags;
    static List<Product> rawProducts;
    static List<Product> productsInDB;

    static Map<Tag, List<Product>> tagMap;
    static Map<Type, List<Product>> typeMap;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        productDAO = new DAOProductImpl(dataSource);

        coreProductDAO = new DAOCoreProductImpl(dataSource);
        rawCoreProducts = new ArrayList<>();
        coreProductsInDB = new ArrayList<>();
        rawProducts = new ArrayList<>();
        productsInDB = new ArrayList<>();

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

            String line;

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
            System.err.println(iex);
            throw iex;
        }
        finally
        {
            if (null != reader)
                reader.close();
        }

        try
        {
            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_PRODUCT_DATA.csv"),
                    Charset.defaultCharset());

            String line;

            while (null != (line = reader.readLine()))
            {
                String[] productData = line.split(",");
                BaseUnit baseUnit = BaseUnit.getInstance(productData[0]);
                Integer quantity = Integer.parseInt(productData[1]);
                Integer inventory = Integer.parseInt(productData[2]);
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(productData[3]));

                Integer randomCoreProductIndex = (int) (Math.random() * coreProductsInDB.size());

                CoreProduct coreProduct = coreProductsInDB.get(randomCoreProductIndex);

                Product testProduct = new Product(coreProduct, baseUnit, quantity, inventory, price);

                rawProducts.add(testProduct);

                Product productInDB = productDAO.insert(testProduct);

                for (Tag t : coreProduct.getTags())
                {
                    List<Product> mappedTagProducts = tagMap.get(t);

                    mappedTagProducts.add(productInDB);

                    tagMap.put(t, mappedTagProducts);
                }

                List<Product> mappedTypeProducts = typeMap.get(productInDB.getCoreProduct().getType());

                mappedTypeProducts.add(productInDB);

                typeMap.put(productInDB.getCoreProduct().getType(), mappedTypeProducts);

                productsInDB.add(productInDB);

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

//    @Test
    public void testProductSearchByTags()
    {
        List<Product> testProducts = new ArrayList<>();

        for (Tag t : rawTags)
        {
            List<Tag> testTags = new ArrayList<>();
            Set<Product> productSet = new HashSet<>();

            testTags.add(t);

            testProducts = ((DAOProductImpl) productDAO).searchProducts(testTags, new ArrayList<Type>());

            productSet = testProducts.stream().collect(Collectors.toSet());

            assertEquals(productSet.size(), testProducts.size());
            assertEquals(tagMap.get(t).size(), testProducts.size());
            assertTrue(tagMap.get(t).containsAll(testProducts));
            assertTrue(testProducts.containsAll(tagMap.get(t)));
        }

        for (int i = 0; i < rawTags.size(); i++)
        {
            for (int j = (i + 1); j < rawTags.size(); j++)
            {

                List<Product> matchingProducts = new ArrayList<>();

                List<Tag> testTags = new ArrayList<>();
                Set<Product> productSet = new HashSet<>();

                matchingProducts.addAll(tagMap.get(rawTags.get(i)));
                matchingProducts.addAll(tagMap.get(rawTags.get(j)));

                productSet = matchingProducts.stream().collect(Collectors.toSet());

                matchingProducts = productSet.stream().collect(Collectors.toList());

                testTags.add(rawTags.get(i));
                testTags.add(rawTags.get(j));

                testProducts = ((DAOProductImpl) productDAO).searchProducts(testTags, new ArrayList<Type>());

                productSet = testProducts.stream().collect(Collectors.toSet());

                assertEquals(productSet.size(), testProducts.size());
                assertEquals(matchingProducts.size(), testProducts.size());
                assertTrue(matchingProducts.containsAll(testProducts));
                assertTrue(testProducts.containsAll(matchingProducts));
                assertTrue(testProducts.containsAll(tagMap.get(rawTags.get(i))));
                assertTrue(testProducts.containsAll(tagMap.get(rawTags.get(j))));

            }
        }

        for (int i = 0; i < rawTags.size(); i++)
        {
            for (int j = (i + 1); j < rawTags.size(); j++)
            {
                for (int k = (j + 1); k < rawTags.size(); k++)
                {
                    List<Product> matchingProducts = new ArrayList<>();

                    List<Tag> testTags = new ArrayList<>();
                    Set<Product> productSet = new HashSet<>();

                    matchingProducts.addAll(tagMap.get(rawTags.get(i)));
                    matchingProducts.addAll(tagMap.get(rawTags.get(j)));
                    matchingProducts.addAll(tagMap.get(rawTags.get(k)));

                    productSet = matchingProducts.stream().collect(Collectors.toSet());

                    matchingProducts = productSet.stream().collect(Collectors.toList());

                    testTags.add(rawTags.get(i));
                    testTags.add(rawTags.get(j));
                    testTags.add(rawTags.get(k));

                    testProducts = ((DAOProductImpl) productDAO).searchProducts(testTags, new ArrayList<Type>());

                    productSet = testProducts.stream().collect(Collectors.toSet());

                    assertEquals(productSet.size(), testProducts.size());
                    assertEquals(matchingProducts.size(), testProducts.size());
                    assertTrue(matchingProducts.containsAll(testProducts));
                    assertTrue(testProducts.containsAll(matchingProducts));
                    assertTrue(testProducts.containsAll(tagMap.get(rawTags.get(i))));
                    assertTrue(testProducts.containsAll(tagMap.get(rawTags.get(j))));
                    assertTrue(testProducts.containsAll(tagMap.get(rawTags.get(k))));

                }
            }
        }
        
    }
    
    
//    @Test
    public void testSearchByPartialTags()
    {
        List<Product> matchingProducts = new ArrayList<>();
        Set<Product> productSet = new HashSet<>();
        
        productSet.addAll(tagMap.get(new Tag("domestic")));
        productSet.addAll(tagMap.get(new Tag("dry")));
        
        matchingProducts = productSet.stream().collect(Collectors.toList());
        
        List<Tag> testTags = new ArrayList<>();
        
        testTags.add(new Tag("d"));
        
        List<Product> testProducts = ((DAOProductImpl) productDAO).searchProducts(testTags, new ArrayList<>());
        
        productSet = testProducts.stream().collect(Collectors.toSet());
        
        assertEquals(productSet.size(), testProducts.size());
        assertEquals(matchingProducts.size(), testProducts.size());
        assertTrue(matchingProducts.containsAll(testProducts));
        assertTrue(testProducts.containsAll(matchingProducts));
        assertTrue(testProducts.containsAll(tagMap.get(new Tag("domestic"))));
        assertTrue(testProducts.containsAll(tagMap.get(new Tag("dry"))));
        assertTrue(testProducts.size() >= tagMap.get(new Tag("domestic")).size());
        assertTrue(testProducts.size() >= tagMap.get(new Tag("dry")).size());
    }

//    @Test
    public void testProductSearchByTagsAndTypes()
    {
        List<Product> testProducts = new ArrayList<>();

        for (Type t : Type.values())
        {
            for (Tag g : rawTags)
            {
                List<Product> matchingProducts = new ArrayList<>();
                Set<Product> productSet = new HashSet<>();
                
                List<Tag> testTags = new ArrayList<>();
                List<Type> testTypes = new ArrayList<>();
                testTags.add(g);
                testTypes.add(t);
                
                for(Product p : tagMap.get(g))
                {
                    for(Product r : typeMap.get(t))
                    {
                        if(p.equals(r))
                        {
                            productSet.add(p);
                        }
                    }
                }
                
                matchingProducts = productSet.stream().collect(Collectors.toList());

                testProducts = ((DAOProductImpl) productDAO).searchProducts(testTags, testTypes);

                productSet = testProducts.stream().collect(Collectors.toSet());

                assertEquals(productSet.size(), testProducts.size());
                assertEquals(matchingProducts.size(), testProducts.size());
                assertTrue(tagMap.get(g).containsAll(testProducts));
                assertTrue(typeMap.get(t).containsAll(testProducts));
                assertTrue(matchingProducts.containsAll(testProducts));
                assertTrue(testProducts.containsAll(matchingProducts));
            }
        }

    }

//    @Test
    public void testProductSearchByTypes()
    {
        List<Product> testProducts = new ArrayList<>();

        for (Type t : Type.values())
        {
            List<Type> testTypes = new ArrayList<>();
            testTypes.add(t);

            Set<Product> productSet = new HashSet<>();

            testProducts = ((DAOProductImpl) productDAO).searchProducts(new ArrayList<Tag>(), testTypes);

            productSet = testProducts.stream().collect(Collectors.toSet());

            assertEquals(productSet.size(), testProducts.size());
            assertEquals(typeMap.get(t).size(), testProducts.size());
            assertTrue(typeMap.get(t).containsAll(testProducts));
            assertTrue(testProducts.containsAll(typeMap.get(t)));
        }
    }
    
//    @Test
    public void testReadAllProducts()
    {
        List<Product> testProducts = new ArrayList<>();
        Set<Product> productSet = new HashSet<>();
        
        testProducts = productDAO.readAll();
        
        productSet = testProducts.stream().collect(Collectors.toSet());
        
        assertEquals(productSet.size(), testProducts.size());
        assertEquals(productsInDB.size(), testProducts.size());
        assertTrue(productsInDB.containsAll(testProducts));
        assertTrue(testProducts.containsAll(productsInDB)); 
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

        Product product = new Product(testCoreProduct, baseUnit, quantity, inventory, price);

        return product;
    }

}
