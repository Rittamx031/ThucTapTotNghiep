package stock.thuctap.stock.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("stock.thuctap.stock.domain")
@EnableJpaRepositories("stock.thuctap.stock.repos")
@EnableTransactionManagement
public class DomainConfig {
}
