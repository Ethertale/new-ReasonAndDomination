package io.ethertale.reasonanddominationspringdefenseproject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
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
                        .requestMatchers("/favicon.ico", "/imgs/**", "/css/**", "/fonts/**", "/js/**").permitAll()
                        .requestMatchers("/", "/register").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(form -> form.loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error")
                .permitAll()
        ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
        );
        return http.build();
    }
}
