package vn.cjack.demo.oauth2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import vn.cjack.demo.oauth2.server.repository.UserRepository;
import vn.cjack.demo.oauth2.server.repository.entity.User;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class AuthServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.findByUsername("admin").isPresent()){
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .address("HA NOI")
                    .build());
        }
    }
}
