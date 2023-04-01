package com.example.healthiu.config;

import com.example.healthiu.entity.Role;
import com.example.healthiu.security.MyAuthenticationProvider;
import com.example.healthiu.security.jwt.JwtConfigurer;
import com.example.healthiu.security.jwt.JwtTokenProvider;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyAuthenticationProvider authProvider;


    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("111111")).roles("ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
//                .antMatchers("/register").anonymous()
//                .antMatchers("/chat-messaging/**", "/", "/test", "/test/result",
//                        "/css/**", "/js/**", "/img/**", "/webjars/**"
//                )
//                .permitAll()
//                .antMatchers("/profile", "/profile/test", "/chatroom", "/chatroom/**").authenticated()
//                .antMatchers("/chatroom/admin", "/chatroom/admin/add-chatroom/**", "/admin-register")
//                .hasRole(Role.ADMIN.toString())
//                .antMatchers("/chatroom/request-chatroom-doctor", "/chatroom/unrequest-chatroom-doctor",
//                        "/profile/tests", "/profile/tests/**")
//                .hasRole(Role.DOCTOR.toString())
//                .antMatchers("/chatroom/request-chatroom", "/chatroom/request-chatroom/requested")
//                .hasRole(Role.USER.toString())
                .anyRequest().permitAll();
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
//        http.csrf().disable().cors().configurationSource(request -> corsConfiguration);
//        http.authenticationProvider(authProvider);
        http.apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(ImmutableList.of("http://localhost:5173/"));
        config.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
