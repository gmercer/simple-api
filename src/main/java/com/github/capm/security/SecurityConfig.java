package com.github.capm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(
                        new RegexRequestMatcher("/users/*", null),
                        new RegexRequestMatcher("/compass-api/*", null)
                        ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .logout()
//                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    JdbcUserDetailsManager jdbc(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);
        jdbc.setEnableAuthorities(false);
        jdbc.setEnableGroups(true);
        return jdbc;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner initializeUsers(JdbcUserDetailsManager userDetailsManager) {
        return (args) -> {
            String userName = "admin";
            if (!userDetailsManager.userExists(userName)) {
                userDetailsManager.createGroup("GROUP_USERS", AuthorityUtils.createAuthorityList("ROLE_USER"));
                userDetailsManager.addUserToGroup(userName, "GROUP_USERS");
                userDetailsManager.createGroup("GROUP_ADMINS", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
                userDetailsManager.addUserToGroup(userName, "GROUP_ADMINS");
                userDetailsManager.createUser(
                        User.builder()
                                .username(userName)
                                .password("{bcrypt}$2a$10$jdJGhzsiIqYFpjJiYWMl/eKDOd8vdyQis2aynmFN0dgJ53XvpzzwC")
                                .authorities(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"))
                                .build()
                );
            }
        };
    }

}