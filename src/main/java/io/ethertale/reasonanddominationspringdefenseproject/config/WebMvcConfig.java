package io.ethertale.reasonanddominationspringdefenseproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         *         authorizeHttpRequests = конфиг за група от ендпойнти
         *         requestMatchers = достъпд до даден ендпойнт
         *         requestMatchers(PathRequest.toStaticResources().atCommonLocations()) = всички статични обекти да бъдат достъпни
         *         .permitAll() = всеки може да достъпи ендпойнта
         *         .anyRequest() = всички заявки, които не съм изброил
         *         .authenticated() = за достъп трябва да си аутентикиран
         */

        http.authorizeHttpRequests(matchers -> matchers
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("static/***").permitAll()
                .requestMatchers("/", "/register").permitAll()
                .requestMatchers("/database-dar").hasRole("ADMIN")
                .anyRequest().authenticated()
        ).formLogin(form -> form.loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .permitAll()
        ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
        );



        return http.build();
    }
}
