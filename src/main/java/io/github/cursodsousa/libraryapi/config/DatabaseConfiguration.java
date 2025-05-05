package io.github.cursodsousa.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfiguration {
    /*
     * Em uma classe de configuração não é necessário o modificar de acesso private,
     * pois já é colocado automaticamente
     */
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Tamanho máximo do pool de conexões
        config.setMinimumIdle(1); // Tamanho mínimo do pool de conexões
        config.setPoolName("library-db-pool"); // Nome do pool de conexões
        config.setMaxLifetime(600000); // 10min. Tempo máximo de vida de uma conexão no pool (0 significa sem limite)
        config.setConnectionTimeout(100000); // 1,33min. Tempo máximo de espera para obter uma conexão do pool (0
                                             // significa sem limite)
        config.setConnectionTestQuery("select 1"); // Consulta para testar a conexão (opcional, mas recomendado)
        // config.setIdleTimeout(600000); // 10min. Tempo máximo de inatividade de uma
        // conexão no pool (0 significa sem limite)

        return new HikariDataSource(config);

    }
}
