package io.kpatil.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class ResolutionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResolutionsApplication.class, args);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User
                        .withUsername("kpatil")
                        .password("{bcrypt}$2y$12$SMCRDn5PeFp77.2f6Q16Ee66aHaKTQexKj0EoRkJi7CkBpXMjKWl.")
                        .authorities("resolution:read")
                        .build()
        );
    }

}
