package com.parkmate.parkingreadservice.common.config;

import com.mongodb.client.MongoClient;
import com.parkmate.parkingreadservice.common.converter.DateToLocalDateTimeKstConverter;
import com.parkmate.parkingreadservice.common.converter.LocalDateTimeToDateKstConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.List;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext,
            MongoCustomConversions mongoCustomConversions // (1)에서 만든 Bean을 주입받습니다.
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
    public MongoTemplate mongoTemplate(
            MongoDatabaseFactory mongoDatabaseFactory,
            MappingMongoConverter mappingMongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }
}
