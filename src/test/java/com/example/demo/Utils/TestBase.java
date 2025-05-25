package com.example.demo.Utils;

import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Eine Utilityklasse für Integrationtests
 */
@Testcontainers
public class TestBase {
    @Container
    public static MariaDBContainer mariaDBContainer =
        new MariaDBContainer("mariadb:10.11").withDatabaseName("menstrunation_test").withPassword("root").withUsername("root");

    static {
        mariaDBContainer.start();
        System.setProperty("spring.datasource.url", mariaDBContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mariaDBContainer.getUsername());
        System.setProperty("spring.datasource.password", mariaDBContainer.getPassword());
        System.setProperty("spring.jpa.hibernate.ddl-auto", "none");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MariaDBDialect");
        System.setProperty("spring.flyway.enabled", "true");
        System.setProperty("spring.flyway.locations", "classpath:/db/migration");
        System.setProperty("spring.flyway.clean-disabled", "false");
    }
}
