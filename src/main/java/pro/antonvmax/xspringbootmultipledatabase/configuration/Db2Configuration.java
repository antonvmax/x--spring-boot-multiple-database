package pro.antonvmax.xspringbootmultipledatabase.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring configuration of the "DB2" database.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:jpa-multiple-database.properties"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "db2EntityManager",
        transactionManagerRef = "db2TransactionManager",
        basePackages = "pro.antonvmax.xspringbootmultipledatabase.repository.db2"
)
public class Db2Configuration {

    @Bean
    @ConfigurationProperties(prefix = "spring.db2.datasource")
    public DataSource db2DataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean(name = "db2EntityManager")
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db2DataSource())
                .properties(hibernateProperties())
                .packages("pro.antonvmax.xspringbootmultipledatabase.entity.db2")
                .persistenceUnit("db2PU")
                .build();
    }

    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager db2TransactionManager(@Qualifier("db2EntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> hibernateProperties() {

        Resource resource = new ClassPathResource("hibernate.properties");

        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap<String, Object>();
        }
    }
}
