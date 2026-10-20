package br.com.aweb.sistema_de_vendas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
                    req.requestMatchers("/login", "/logout").permitAll();

                    req.requestMatchers("/produtos/**").hasRole("ADMIN");
                    req.requestMatchers("/clientes/**").hasRole("ADMIN");

                    req.requestMatchers("/pedidos/**").hasAnyRole("ADMIN", "PADRAO");

                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/pedidos", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe.key("lembrarDeMim"))
                .build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }
}
