package org.ssa.ironyard.liquorstore.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ssa.ironyard.liquorstore.controller.CustomerController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//@Configuration
public class MapperConfiguration
{

    static Logger LOGGER = LogManager.getLogger(MapperConfiguration.class);
    
    @Bean
    public ObjectMapper jacksonConfig()
    {
        ObjectMapper mapper = new ObjectMapper();
        
        LOGGER.info("We are using the configuration");
        
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        return mapper;

    }
}
