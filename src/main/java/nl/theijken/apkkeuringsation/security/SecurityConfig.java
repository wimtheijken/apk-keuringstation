package nl.theijken.apkkeuringsation.security;

import nl.theijken.apkkeuringsation.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

//                        LOGIN
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()

//                        ADMIN / BACKOFFICE
                        .requestMatchers(HttpMethod.POST, "/actions").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.PUT, "/actions/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/actions/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.POST, "/carparts").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.PUT, "/carparts/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/carparts/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/cars/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/customers/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/invoices/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/tickets/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.PUT, "/users/*").hasRole("ADMIN") // ADMIN - password adjusment
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.GET, "/users/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.GET, "/roles/*").hasRole("ADMIN") // ADMIN
                        .requestMatchers(HttpMethod.GET, "/roles").hasRole("ADMIN") // ADMIN

//                        CASHIER
                        .requestMatchers(HttpMethod.POST, "/cars").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.PUT, "/cars/*").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.POST, "/customers").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.PUT, "/customers/*").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.GET, "/customers/*").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.POST, "/invoices").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.GET, "/invoices").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN
                        .requestMatchers(HttpMethod.GET, "/invoices/*").hasAnyRole("ADMIN", "CASHIER") // CASHIER & ADMIN

//                        MECHANIC
                        .requestMatchers(HttpMethod.POST, "/tickets").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.PUT, "/tickets/*").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/actions").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/actions/*").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/carparts").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/carparts/*").hasAnyRole("ADMIN", "MECHANIC" ) // MECHANIC & ADMIN

//                        CASHIER & MECHANIC
                        .requestMatchers(HttpMethod.GET, "/cars").hasAnyRole("ADMIN", "CASHIER", "MECHANIC" ) // CASHIER & MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/cars/*").hasAnyRole("ADMIN", "CASHIER", "MECHANIC" ) // CASHIER & MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/tickets").hasAnyRole("ADMIN", "CASHIER", "MECHANIC" ) // CASHIER & MECHANIC & ADMIN
                        .requestMatchers(HttpMethod.GET, "/tickets/*").hasAnyRole("ADMIN", "CASHIER", "MECHANIC" ) // CASHIER & MECHANIC & ADMIN
                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
