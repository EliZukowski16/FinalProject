package org.ssa.ironyard.liquorstore;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.ssa.ironyard.liquorstore.dao.AbstractSpringDAO;
import org.ssa.ironyard.liquorstore.model.Admin;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MockDataLoader
{
    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
    static DataSource dataSource;
    
    static AbstractSpringDAO<Admin> adminDAO;
    static AbstractSpringDAO<Customer> customerDAO;
    static AbstractSpringDAO<CoreProduct> coreProductDAO;
    static AbstractSpringDAO<Product> productDAO;
    static AbstractSpringDAO<Order> orderDAO;
    
    @BeforeClass
    public void setupBeforeClass()
    {
        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
        mysqlDdataSource.setURL(URL);

        dataSource = mysqlDdataSource;
    }

}
