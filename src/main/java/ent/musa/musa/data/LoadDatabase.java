package ent.musa.musa.data;

import ent.musa.musa.data.repository.CustomerRepository;
import ent.musa.musa.data.repository.ShoppingCartRepository;
import ent.musa.musa.integrals.Status;
import ent.musa.musa.models.Customer;
import ent.musa.musa.models.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    static final Logger log =
        LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customers, ShoppingCartRepository shoppingCarts) {
        return args -> {
            log.info("Preloading...\t{}", customers.save(new Customer("Ayrton Senna")));
            customers.findAll().forEach(c -> log.info(String.valueOf(c)));

            shoppingCarts.save(new ShoppingCart("ABC 'Life Gems'", Status.IN_PROGRESS));
            shoppingCarts.findAll().forEach(s -> {
                log.info(String.valueOf(s));
            });
        };
    }
}
