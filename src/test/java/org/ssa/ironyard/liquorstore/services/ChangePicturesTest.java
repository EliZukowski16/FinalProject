//package org.ssa.ironyard.liquorstore.services;
//
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
//public class ChangePicturesTest
//{
//    DAOProduct daoProd;
//    DAOCoreProduct daoCP;
//    
//    ChangePictures cp;
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
//        cp = new ChangePictures(daoProd,daoCP);
//        
//       
//    }
//
//    @Test
//    public void test() throws IOException
//    {
//        cp.changePicture();
//    }
//
//}
