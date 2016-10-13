package org.ssa.ironyard.liquorstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class MapperConfiguration
{

    @Bean
    public ObjectMapper jacksonConfig()
    {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        
        return mapper;

    }
}
