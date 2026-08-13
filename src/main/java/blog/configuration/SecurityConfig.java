package blog.configuration;

import blog.Security.JwtAuthenticationEntryPoint;
import blog.Security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                // ==============================
                // CSRF
                // ==============================
                .csrf(csrf -> csrf.disable())

                // ==============================
                // EXCEPTION HANDLING
                // ==============================
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                // ==============================
                // JWT = STATELESS
                // ==============================
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ==============================
                // AUTHORIZATION
                // ==============================
                .authorizeHttpRequests(auth -> auth

                        // =================================
                        // 1. LOGIN - PUBLIC
                        // =================================
                        .requestMatchers(
                                "/api/v1/auth/login"
                        ).permitAll()


                        // =================================
                        // 2. REGISTER USER - PUBLIC
                        // =================================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users",
                                "/api/users/"
                        ).permitAll()


                        // =================================
                        // 3. SWAGGER - PUBLIC
                        // =================================
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =================================
                        // 4. IMAGE DOWNLOAD - PUBLIC
                        // =================================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/post/image/**"
                        ).permitAll()


                        // =================================
                        // 5. PUBLIC BLOG READING
                        // =================================

                        // Anyone can see categories
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/category",
                                "/api/category/**"
                        ).permitAll()

                        // Anyone can see posts
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/post",
                                "/api/post/**"
                        ).permitAll()


                        // =================================
                        // 6. USER VIEW USERS
                        // =================================

                        // Logged-in USER or ADMIN can view users
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users/**"
                        ).hasAnyRole("USER", "ADMIN")


                        // =================================
                        // 7. ADMIN USER MANAGEMENT
                        // =================================

                        // Only ADMIN can update users
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete users
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/users/**"
                        ).hasRole("ADMIN")


                        // =================================
                        // 8. ADMIN CATEGORY MANAGEMENT
                        // =================================

                        // Only ADMIN can create category
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/category/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can update category
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/category/**"
                        ).hasRole("ADMIN")

                        // Only ADMIN can delete category
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/category/**"
                        ).hasRole("ADMIN")


                        // =================================
                        // 9. LOGGED-IN USERS
                        // =================================

                        // Creating/updating/deleting posts
                        // requires login
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/**"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/post/**"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/post/**"
                        ).hasAnyRole("USER", "ADMIN")


                        // =================================
                        // 10. EVERYTHING ELSE
                        // =================================
                        .anyRequest().authenticated()
                );


        // ==============================
        // JWT FILTER
        // ==============================
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }


    // ==============================
    // PASSWORD ENCODER
    // ==============================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // ==============================
    // AUTHENTICATION MANAGER
    // ==============================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}