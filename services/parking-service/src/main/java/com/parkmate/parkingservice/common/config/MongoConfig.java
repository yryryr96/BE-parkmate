package com.parkmate.parkingservice.common.config;

import com.parkmate.parkingservice.common.converter.DateToLocalDateTimeKstConverter;
import com.parkmate.parkingservice.common.converter.LocalDateTimeToDateKstConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.parkmate.parkingservice.parkingoperation")
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext,
            MongoCustomConversions mongoCustomConversions
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);

        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setCustomConversions(mongoCustomConversions);
        converter.afterPropertiesSet();

        return converter;
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions(
            DateToLocalDateTimeKstConverter dateToLocalDateTimeKstConverter,
            LocalDateTimeToDateKstConverter localDateTimeToDateKstConverter) {

        return new MongoCustomConversions(
                List.of(dateToLocalDateTimeKstConverter, localDateTimeToDateKstConverter)
        );
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                                       MappingMongoConverter mappingMongoConverter) {

        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }
}
