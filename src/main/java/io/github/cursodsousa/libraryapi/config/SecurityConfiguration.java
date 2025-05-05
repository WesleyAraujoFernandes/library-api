package io.github.cursodsousa.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.cursodsousa.libraryapi.security.CustomUserDetailsService;
import io.github.cursodsousa.libraryapi.security.LoginSocialSuccessHandler;
import io.github.cursodsousa.libraryapi.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LoginSocialSuccessHandler successHandler) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()) // Permite que receba os dados de autenticação pelo postman

                .formLogin(configurer -> {
                    configurer.loginPage("/login"); //
                }) // Permite que receba os dados de autenticação por formulário de login via
                   // browser
                   // .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    // authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers("/livros/**").hasAnyRole("USER","ADMIN");
                    authorize.anyRequest().authenticated(); // anyRequest dever ser a última configuração
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                        .loginPage("/login")
                        .successHandler(successHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10); // Vai recriptografar 10x

    }

    // @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        /*
         * UserDetails user1 = User.builder()
         * .username("usuario")
         * .password(encoder.encode("123"))
         * .roles("USER")
         * .build();
         * 
         * UserDetails user2 = User.builder()
         * .username("admin")
         * .password(encoder.encode("321"))
         * .roles("ADMIN")
         * .build();
         * return new InMemoryUserDetailsManager(user1, user2);
         */
        return new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
