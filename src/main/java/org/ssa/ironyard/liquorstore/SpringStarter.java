
package org.ssa.ironyard.liquorstore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarter 
{
    static Logger LOGGER = LogManager.getLogger(SpringStarter.class);

    public static void main(String[] args)
    {
        LOGGER.info("Starting application liquor");
        SpringApplication.run(SpringStarter.class, args);
    }
}