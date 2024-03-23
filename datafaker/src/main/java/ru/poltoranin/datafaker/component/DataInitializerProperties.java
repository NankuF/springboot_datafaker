package ru.poltoranin.datafaker.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "data-initializer")
@Getter
@Setter
public class DataInitializerProperties {

    private Integer count;
}
