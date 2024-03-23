package ru.poltoranin.datafaker.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.format.DateTimeFormatter;

// how changed dateformat see here
// https://stackoverflow.com/questions/51527794/how-do-i-configure-jackson-serialization-on-localdatetime-and-localdate-for-java

@Configuration
public class JacksonConfig {
    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        var builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(new JsonNullableModule(), new JavaTimeModule());
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        new LocalDateTimeSerializer(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        new LocalDateTimeDeserializer(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return builder;
    }
}
