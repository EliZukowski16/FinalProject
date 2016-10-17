
//package org.ssa.ironyard.liquorstore.services;
//
//import java.io.IOException;
//
//import javax.sql.DataSource;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.ssa.ironyard.liquorstore.dao.DAOAdminImpl;
//import org.ssa.ironyard.liquorstore.dao.DAOCoreProduct;
//import org.ssa.ironyard.liquorstore.dao.DAOCoreProductImpl;
//import org.ssa.ironyard.liquorstore.dao.DAOProduct;
//import org.ssa.ironyard.liquorstore.dao.DAOProductImpl;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//
//public class BrewaryDBServiceTest
//{
//    DAOProduct daoProd;
//    DAOCoreProduct daoCP;
//    
//    BrewaryDBService bdbService;
//    
//    static String URL = "jdbc:mysql://localhost/liquor_store?user=root&password=root&useServerPrpStmts=true";
//    static DataSource dataSource;
//
//    @Before
//    public void setup()
//    {
//        MysqlDataSource mysqlDdataSource = new MysqlDataSource();
//        mysqlDdataSource.setURL(URL);
//
//        dataSource = mysqlDdataSource;
//        
//        daoProd = new DAOProductImpl(dataSource);
//        daoCP = new DAOCoreProductImpl(dataSource);
//        
//        bdbService = new BrewaryDBService(daoProd,daoCP);
//        
//       
//    }
//    
//    @Test
//    public void test() throws IOException 
//    {
//        
//        daoCP.clear();
//        
//        bdbService.getBeers();

//    @Test
//    public void test() throws IOException 
//    {
//        bdbService.getBeers();;

//    }

//}

