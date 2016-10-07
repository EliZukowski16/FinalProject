package org.ssa.ironyard.liquorstore.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Configuration
public class DataSourceConfiguration 
{
    static final String DATABASE_URL = "jdbc:mysql://localhost/liquor_store?" + "user=root&password=Fireproof!007&" + "useServerPrepStmts=true";
    static final Logger LOGGER = LogManager.getLogger(DataSourceConfiguration.class);
    @Bean
    public DataSource datasource()
    {
        LOGGER.debug("YEAH ANnotation based processing is working");
        MysqlDataSource mysqlds = new MysqlDataSource();
        mysqlds.setURL(DATABASE_URL);
        return mysqlds;
    }


}