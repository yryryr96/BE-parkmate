package com.parkmate.userparkinghistoryservice.common.config;

import com.parkmate.userparkinghistoryservice.common.converter.DateToLocalDateTimeKstConverter;
import com.parkmate.userparkinghistoryservice.common.converter.LocalDateTimeToDateKstConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
@EnableMongoAuditing
@Configuration
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
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                                       MappingMongoConverter mappingMongoConverter) {

        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }
}
