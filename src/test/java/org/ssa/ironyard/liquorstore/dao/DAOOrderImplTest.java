package org.ssa.ironyard.liquorstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.model.Address;
import org.ssa.ironyard.liquorstore.model.Address.State;
import org.ssa.ironyard.liquorstore.model.Address.ZipCode;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOOrderImplTest extends AbstractSpringDAOTest<Order>
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static AbstractSpringDAO<Product> productDAO;
    static AbstractSpringDAO<Customer> customerDAO;
    static AbstractSpringDAO<Order> orderDAO;

    static CoreProduct testCoreProduct;
    static Product testProduct;
    static Customer testCustomer;
    static Order testOrder;

    static List<CoreProduct> rawCoreProducts;
    static List<CoreProduct> coreProductsInDB;

    static List<Customer> rawCustomers;
    static List<Customer> customersInDB;

    static List<Tag> rawTags;

    static List<Product> rawProducts;
    static List<Product> productsInDB;

    static List<Order> rawOrders;
    static List<Order> ordersInDB;

    static List<OrderDetail> rawOrderDetails;

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
        customerDAO = new DAOCustomerImpl(dataSource);
        orderDAO = new DAOOrderImpl(dataSource);

        rawCustomers = new ArrayList<>();
        customersInDB = new ArrayList<>();

        rawCoreProducts = new ArrayList<>();
        coreProductsInDB = new ArrayList<>();

        rawProducts = new ArrayList<>();
        productsInDB = new ArrayList<>();

        rawOrders = new ArrayList<>();
        ordersInDB = new ArrayList<>();

        rawOrderDetails = new ArrayList<>();

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

            reader.close();

            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_PRODUCT_DATA.csv"),
                    Charset.defaultCharset());

            line = null;

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

            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_CUSTOMER_DATA.csv"),
                    Charset.defaultCharset());

            line = null;

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

            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_ORDER_DETAIL_DATA.csv"),
                    Charset.defaultCharset());

            line = null;

            while (null != (line = reader.readLine()))
            {
                Integer quantity = Integer.parseInt(line);
                Product product = productsInDB.get((int) (Math.random() * productsInDB.size()));
                BigDecimal unitPrice = product.getPrice();

                OrderDetail orderDetail = new OrderDetail(product, quantity, unitPrice);

                rawOrderDetails.add(orderDetail);

            }

            reader = Files.newBufferedReader(Paths.get("./src/test/resources/MOCK_ORDER_DATA.csv"),
                    Charset.defaultCharset());

            while (null != (line = reader.readLine()))
            {
                List<OrderDetail> oD = new ArrayList<>();

                String[] orderData = line.split(",");
                OrderStatus status = OrderStatus.getInstance(orderData[1]);
                String[] dateTime = orderData[0].split(" ");
                String[] date = dateTime[0].split("-");
                String[] time = dateTime[1].split(":");
                LocalDate localDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]));
                LocalTime localTime = LocalTime.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]),
                        Integer.parseInt(time[2]));
                LocalDateTime orderDate = LocalDateTime.of(localDate, localTime);
                Customer customer = customersInDB.get((int) (Math.random() * customersInDB.size()));

                List<Integer> orderDetailsChosen = new ArrayList<>();
                while (oD.size() < 3)
                {
                    Integer i = (int) (Math.random() * rawOrderDetails.size());

                    if (!orderDetailsChosen.contains(i))
                    {
                        oD.add(rawOrderDetails.get(i));

                        orderDetailsChosen.add(i);
                    }
                }

                BigDecimal total = BigDecimal.ZERO;

                for (int i = 0; i < oD.size(); i++)
                {
                    OrderDetail orderDetail = oD.get(i);

                    total = total.add(orderDetail.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQty())));
                }

                testOrder = new Order(customer, orderDate, total, oD, status);

                rawOrders.add(testOrder);

                Order orderFromDB = orderDAO.insert(testOrder);

                ordersInDB.add(orderFromDB);

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
        orderDAO.clear();
        customerDAO.clear();
        productDAO.clear();
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
    public void testReadByIds()
    {
        List<Order> orderSubList = new ArrayList<>();
        List<Order> testOrders = new ArrayList<>();
        Set<Integer> orderIdsSet = new HashSet<>();
        List<Integer> orderIdsList = new ArrayList<>();

        while (orderIdsSet.size() < (ordersInDB.size() / 2))
        {
            Integer i = (int) (Math.random() * ordersInDB.size());
            if (!orderIdsSet.contains(ordersInDB.get(i).getId()))
            {
                orderIdsSet.add(ordersInDB.get(i).getId());
                orderSubList.add(ordersInDB.get(i));
            }
        }

        orderIdsList = orderIdsSet.stream().collect(Collectors.toList());

        testOrders = orderDAO.readByIds(orderIdsList);

        testOrders.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        orderSubList.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        assertEquals(orderIdsList.size(), orderIdsSet.size());
        assertEquals(orderSubList.size(), testOrders.size());
        assertTrue(ordersInDB.containsAll(orderSubList));
        assertTrue(orderSubList.containsAll(testOrders));
        assertTrue(testOrders.containsAll(orderSubList));

        for (int i = 0; i < orderIdsList.size(); i++)
        {
            assertEquals(orderSubList.get(i), testOrders.get(i));
            assertTrue(orderSubList.get(i).deeplyEquals(testOrders.get(i)));
        }

    }

    @Override
    protected AbstractSpringDAO<Order> getDAO()
    {
        orderDAO = new DAOOrderImpl(dataSource);

        return orderDAO;
    }

    @Override
    protected Order newInstance()
    {
        testProduct = productsInDB.get((int) (Math.random() * productsInDB.size()));
        testCustomer = customersInDB.get((int) (Math.random() * customersInDB.size()));
        
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2016, 5, 12), LocalTime.of(5, 12, 25));
        BigDecimal total = BigDecimal.valueOf(50.00);
        List<OrderDetail> oD = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail(testProduct, 10, BigDecimal.valueOf(5.55));

        oD.add(orderDetail);

        Order order = new Order(testCustomer, date, total, oD, OrderStatus.PENDING);

        return order;
    }

}
