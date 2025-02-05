package ent.musa.musa.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandlineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log =
        LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandlineRunner initDatabase(CustomerRepository customers, ShoppingCartRepository shoppingCarts) {
        return args -> {
            log.info("Preloading...\t" +  customers.save(new Customer("Ayrton Senna")));
            customers.findAll().forEach(c -> log.info(c));

            shoppingCarts.save(new ShoppingCart("ABC 'Life Gems'", Status.IN_PROGRESS));
            shoppingCarts.findAll().forEach(s -> {
                log.info(s);
            });
        };
    }
}
