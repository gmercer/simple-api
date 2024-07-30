package com.github.capm.security;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityContext().requireExplicitSave(false)
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new RegexRequestMatcher("/greetings/.*", null)
                        ).permitAll()
                        .requestMatchers(
                                new RegexRequestMatcher("/retrieve/.*", null)
                        ).hasAnyRole("VIEWER", "EDITOR")
                        .requestMatchers(
                                new RegexRequestMatcher("/publish/.*", null)
                        ).hasRole("EDITOR")
                        .requestMatchers(
                                new RegexRequestMatcher("/auth/.*", null)
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true));

//                .logoutSuccessUrl("/")
        return http.build();
    }

    public SecurityFilterChain GAVfilterChain(HttpSecurity http) throws Exception {
        http
//                .regexMatcher("/greetings/*")
//                .anonymous()
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new RegexRequestMatcher("/greetings/.*", null),
                                new RegexRequestMatcher("/retrieve/.*", null)
                        ).hasAnyRole("VIEWER", "EDITOR")
                        .requestMatchers(
                                new RegexRequestMatcher("/publish/.*", null)
                        ).hasRole("EDITOR")
                        .requestMatchers(
                                new RegexRequestMatcher("/auth/.*", null)
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )//                .requestMatchers(
//                        new AntPathRequestMatcher("/greetings**", null)
//                )
//                .permitAll()
//                .requestMatchers(
//                        new AntPathRequestMatcher("/retrieve/**", null)
//                )
//                .hasAnyRole("VIEWER", "EDITOR")
//                .requestMatchers(
//                        new AntPathRequestMatcher("/publish/**", null)
//                )
//                .hasAnyRole("EDITOR")
//                .requestMatchers(
//                        new AntPathRequestMatcher("/**", null))
//                .hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
                //                .and()
//                .rememberMe()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .formLogin((form) ->
                        form
                                .loginPage("/login")
                                .permitAll()
                )
                .logout((logout) ->
                        logout
                                .permitAll()
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                )
        ;

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