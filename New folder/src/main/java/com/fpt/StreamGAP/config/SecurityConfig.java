package com.fpt.StreamGAP.config;

import com.fpt.StreamGAP.service.userDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private userDetailsService UserDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/auth/**", "/public/**").permitAll()


                                .requestMatchers("/songs/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/songs/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/songs/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/songs/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasAuthority("ADMIN")


                                .requestMatchers("/account-settings/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/account-settings/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/account-settings/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/account-settings/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/account-settings/**").hasAuthority("ADMIN")


                                .requestMatchers("/albums/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/albums/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/albums/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/albums/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/albums/**").hasAuthority("ADMIN")


                                .requestMatchers("/artists/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/artists/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/artists/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/artists/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/artists/**").hasAuthority("ADMIN")


                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/**").hasAuthority("USER")
                                .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(UserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
