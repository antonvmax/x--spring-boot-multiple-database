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
import org.springframework.context.annotation.Primary;
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
 * Spring configuration of the "DB1" database.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:jpa-multiple-database.properties"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "db1EntityManager",
        transactionManagerRef = "db1TransactionManager",
        basePackages = "pro.antonvmax.xspringbootmultipledatabase.repository.db1"
)
public class Db1Configuration {

    @Primary
    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "spring.db1.datasource")
    public DataSource db1DataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Primary
    @Bean(name = "db1EntityManager")
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db1DataSource())
                .properties(hibernateProperties())
                .packages("pro.antonvmax.xspringbootmultipledatabase.entity.db1")
                .persistenceUnit("db1PU")
                .build();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager db1TransactionManager(@Qualifier("db1EntityManager") EntityManagerFactory entityManagerFactory) {
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
