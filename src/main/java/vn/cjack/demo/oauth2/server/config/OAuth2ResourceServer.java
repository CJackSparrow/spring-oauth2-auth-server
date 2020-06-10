package vn.cjack.demo.oauth2.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Value("${app.security.client-id}")
    private String clientId;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(clientId).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.anonymous().disable().authorizeRequests()
                .antMatchers("/**/public/**").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("**/test/public/**").permitAll()

                .antMatchers("/test/private").hasAuthority("ADMIN")
                .antMatchers("/user/ahihi").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

    }

}
