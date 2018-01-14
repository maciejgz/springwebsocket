package pl.mg.springwebsocket.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@Order(1)
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {


    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john").password("123").roles("CLIENT").and()
                .withUser("admin").password("123").roles("ADMIN").and()
                .withUser("client").password("123").roles("ADMIN");
    }

}
