package com.edu.Ecom_product.Config;

import com.edu.Ecom_product.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request->request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //.formLogin(Customizer.withDefaults())

        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1= User.withDefaultPasswordEncoder().username("keshav").password("k123").build();
//        UserDetails user2= User.withDefaultPasswordEncoder().username("Harsh").password("harsh").build();
//        return new InMemoryUserDetailsManager(user1,user2);
//    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        // defAULT MEANS work for plain password test123 not decprt NoOpPasswordEncoder.getInstance()
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10)); //decpt the password from database
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }
}
