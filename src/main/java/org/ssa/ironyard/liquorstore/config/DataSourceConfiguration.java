package org.ssa.ironyard.liquorstore.config;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Configuration
public class DataSourceConfiguration
{
    

    static final String DATABASE_URL = "jdbc:mysql://localhost/liquor_store?" + "user=root&password=root&" + "useServerPrepStmts=true";
    static final Logger LOGGER = LogManager.getLogger(DataSourceConfiguration.class);
    @Bean
    public DataSource datasource() throws URISyntaxException
    {
//        URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
//        
//        
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
//        
//        LOGGER.debug("YEAH ANnotation based processing is working");
//        MysqlDataSource mysqlds = new MysqlDataSource();
//        mysqlds.setURL(dbUrl);
//        mysqlds.setUser(username);
//        mysqlds.setPassword(password);
//        return mysqlds;
        
        MysqlDataSource mysqlds = new MysqlDataSource();
        mysqlds.setURL(DATABASE_URL);
        return mysqlds;
    }


}