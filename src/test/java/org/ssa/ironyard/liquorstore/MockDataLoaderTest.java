package org.ssa.ironyard.liquorstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ssa.ironyard.liquorstore.controller.AdminController;
import org.ssa.ironyard.liquorstore.controller.CustomerController;
import org.ssa.ironyard.liquorstore.controller.LogInController;
import org.ssa.ironyard.liquorstore.crypto.BCryptSecurePassword;
import org.ssa.ironyard.liquorstore.dao.AbstractSpringDAO;
import org.ssa.ironyard.liquorstore.dao.DAOAdmin;
import org.ssa.ironyard.liquorstore.dao.DAOAdminImpl;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
import org.ssa.ironyard.liquorstore.dao.DAOCoreProductImpl;
import org.ssa.ironyard.liquorstore.dao.DAOCustomer;
import org.ssa.ironyard.liquorstore.dao.DAOCustomerImpl;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOOrderImpl;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.dao.DAOProductImpl;
import org.ssa.ironyard.liquorstore.dao.DAOSales;
import org.ssa.ironyard.liquorstore.dao.DAOSalesImpl;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Password;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.services.AdminService;
import org.ssa.ironyard.liquorstore.services.AdminServiceImpl;
import org.ssa.ironyard.liquorstore.services.AnalyticsService;
import org.ssa.ironyard.liquorstore.services.AnalyticsServiceImpl;
import org.ssa.ironyard.liquorstore.services.CoreProductService;
import org.ssa.ironyard.liquorstore.services.CoreProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.CustomerService;
import org.ssa.ironyard.liquorstore.services.CustomerServiceImpl;
import org.ssa.ironyard.liquorstore.services.LogInService;
import org.ssa.ironyard.liquorstore.services.LogInServiceImpl;
import org.ssa.ironyard.liquorstore.services.OrdersService;
import org.ssa.ironyard.liquorstore.services.OrdersServiceImpl;
import org.ssa.ironyard.liquorstore.services.ProductService;
import org.ssa.ironyard.liquorstore.services.ProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.SalesService;
import org.ssa.ironyard.liquorstore.services.SalesServiceImpl;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MockDataLoaderTest
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;

    static AbstractSpringDAO<Admin> adminDAO;
    static AbstractSpringDAO<Customer> customerDAO;
    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static AbstractSpringDAO<Product> productDAO;
    static AbstractSpringDAO<Order> orderDAO;
    static AbstractSpringDAO<Sales> salesDAO;

    static AdminService adminService;
    static AnalyticsService analyticsService;
    static CoreProductService coreProductService;
    static CustomerService customerService;
    static LogInService logInService;
    static OrdersService ordersService;
    static ProductService productService;
    static SalesService salesService;

    static AdminController adminController;
    static CustomerController customerController;
    static LogInController logInController;

    @BeforeClass
    public static void setupBeforeClass()
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;

        adminDAO = new DAOAdminImpl(dataSource);
        customerDAO = new DAOCustomerImpl(dataSource);
        coreProductDAO = new DAOCoreProductImpl(dataSource);
        productDAO = new DAOProductImpl(dataSource);
        orderDAO = new DAOOrderImpl(dataSource);
        salesDAO = new DAOSalesImpl(dataSource);

        adminService = new AdminServiceImpl((DAOAdmin) adminDAO);
        analyticsService = new AnalyticsServiceImpl();
        coreProductService = new CoreProductServiceImpl((DAOCoreProduct) coreProductDAO);
        customerService = new CustomerServiceImpl((DAOCustomer) customerDAO);
        logInService = new LogInServiceImpl((DAOAdmin) adminDAO, (DAOCustomer) customerDAO);
        ordersService = new OrdersServiceImpl((DAOOrder) orderDAO, (DAOProduct) productDAO, (DAOSales) salesDAO);
        productService = new ProductServiceImpl((DAOProduct) productDAO);
        salesService = new SalesServiceImpl((DAOSales) salesDAO);

        adminController = new AdminController((AdminServiceImpl) adminService, (AnalyticsServiceImpl) analyticsService,
                (CoreProductServiceImpl) coreProductService, (CustomerServiceImpl) customerService,
                (OrdersServiceImpl) ordersService, (ProductServiceImpl) productService,
                (SalesServiceImpl) salesService);

        customerController = new CustomerController((AdminServiceImpl) adminService,
                (AnalyticsServiceImpl) analyticsService, (CoreProductServiceImpl) coreProductService,
                (CustomerServiceImpl) customerService, (OrdersServiceImpl) ordersService,
                (ProductServiceImpl) productService, (SalesServiceImpl) salesService);

        logInController = new LogInController();

//        BufferedReader reader = null;
//
//        try
//        {
//
//            reader = Files.newBufferedReader(Paths.get("./src/test/resources/popular.txt"), Charset.defaultCharset());
//
//            String line = null;
//
//            while (null != (line = reader.readLine()))
//            {
//                String[] adminData = line.split(",");
//                String username = adminData[0];
//                Password password = new BCryptSecurePassword().secureHash(adminData[1]);
//                String firstName = adminData[2];
//                String lastName = adminData[3];
//                Integer role = Integer.parseInt(adminData[4]);
//
//                testAdmin = new Admin(username, password, firstName, lastName, role);
//
//                rawAdmins.add(testAdmin);
//
//                Admin adminFromDB = adminDAO.insert(testAdmin);
//
//                adminsInDB.add(adminFromDB);
//            }
//        }
//        catch (IOException iex)
//        {
//            System.err.println(iex.getStackTrace());
//            throw iex;
//        }
//        finally
//        {
//            if (reader != null)
//                reader.close();
//        }

    }

    // @Test
    public void testFillHistoricalData()
    {
        List<Customer> customersInDB = customerDAO.readAll();

        List<Product> productsInDB = productDAO.readAll();
        
//        List<Product> popularProducts = productsInDB.stream().filter(p -> p.getCoreProduct().getName().)

        for (int i = 0; i < 2000; i++)
        {
            Integer customerIndex = (int) (Math.random() * customersInDB.size());

            List<Product> productsInOrder = new ArrayList<>();
            List<OrderDetail> orderDetails = new ArrayList<>();

            Order order;
            BigDecimal totalPrice = BigDecimal.ZERO;

            Long minDay = LocalDate.of(2015, 11, 1).toEpochDay();
            Long maxDay = LocalDate.of(2016, 10, 23).toEpochDay();
            Long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            Integer randomHour = (int) (Math.random() * 24);
            Integer randomMinute = (int) (Math.random() * 59);
            Integer randomSecond = (int) (Math.random() * 59);

            LocalDate deliveryDate = LocalDate.ofEpochDay(randomDay);
            LocalDateTime orderDate = LocalDateTime.of(deliveryDate.minus(2, ChronoUnit.DAYS),
                    LocalTime.of(randomHour, randomMinute, randomSecond));

            for (int j = (int) (Math.random() * 3); j < 3; j++)
            {
                Integer productIndex = (int) (Math.random() * productsInDB.size());

                while (productsInOrder.contains(productsInDB.get(productIndex))
                        || productsInDB.get(productIndex).getInventory() <= 0)
                {
                    productIndex = (int) (Math.random() * productsInDB.size());
                }

                Product productToAddToOrder = productsInDB.get(productIndex);

                Integer productQuantityToOrder = ((int) (Math.random() * 3)) + 1;
                orderDetails.add(
                        new OrderDetail(productToAddToOrder, productQuantityToOrder, productToAddToOrder.getPrice()));
                totalPrice = totalPrice
                        .add((productToAddToOrder.getPrice().multiply(BigDecimal.valueOf(productQuantityToOrder))));
            }

            Customer testCustomer = customersInDB.get(customerIndex);

            order = new Order.Builder().customer(testCustomer).date(deliveryDate).timeOfOrder(orderDate)
                    .orderDetails(orderDetails).orderStatus(OrderStatus.PENDING).total(totalPrice).build();

            ((DAOOrderImpl) orderDAO).insert(order, orderDate);
        }

        List<Order> ordersInDB = orderDAO.readAll();

        for (int i = 0; i < ordersInDB.size(); i++)
        {
            Integer statusChange = ((int) (Math.random() * 20)) + 1;

            switch (statusChange)
            {

            case 1:
                ordersService.rejectOrder(ordersInDB.get(i).getId());
                break;
            default:
                ((OrdersServiceImpl) ordersService).testApproveOrder(ordersInDB.get(i).getId(),
                        ordersInDB.get(i).getTimeOfOrder().plus(1, ChronoUnit.DAYS).toLocalDate());
                break;

            }
        }

    }

}
