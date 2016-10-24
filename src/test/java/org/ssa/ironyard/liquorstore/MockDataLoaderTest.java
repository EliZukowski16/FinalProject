package org.ssa.ironyard.liquorstore;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.ssa.ironyard.liquorstore.controller.AdminController;
import org.ssa.ironyard.liquorstore.controller.CustomerController;
import org.ssa.ironyard.liquorstore.controller.LogInController;
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

public class MockDataLoader
{
    static String URL = "jdbc:mysql://localhost/test_liquor_store?user=root&password=root&useServerPrpStmts=true";
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
    public void setupBeforeClass()
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
        
        customerController = new CustomerController((AdminServiceImpl) adminService, (AnalyticsServiceImpl) analyticsService,
                (CoreProductServiceImpl) coreProductService, (CustomerServiceImpl) customerService,
                (OrdersServiceImpl) ordersService, (ProductServiceImpl) productService,
                (SalesServiceImpl) salesService);
        
        logInController = new LogInController();
    }

}
