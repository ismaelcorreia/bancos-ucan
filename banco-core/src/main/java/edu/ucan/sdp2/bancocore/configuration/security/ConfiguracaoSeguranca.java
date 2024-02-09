package edu.ucan.sdp2.bancocore.configuration.security;



import edu.ucan.sdp2.bancocore.services.UtilizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@RequiredArgsConstructor
public class ConfiguracaoSeguranca {

    private final FiltroAutenticacao filtroAutenticacao;
    private final UtilizadorService servico;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(servico);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request -> {
                        request.requestMatchers(HttpMethod.POST, "/autenticacao/**").permitAll();
                        request.requestMatchers(HttpMethod.GET,  "/storage/**").permitAll();
                        request.requestMatchers(HttpMethod.GET, "/src/**").permitAll();
                        request.requestMatchers(HttpMethod.GET, "/publico/**").permitAll();
                        request.requestMatchers(HttpMethod.POST, "/publico/**").permitAll();
                        request.requestMatchers(HttpMethod.POST, "/swagger-ui/**").permitAll();
                        request.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                        request.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll();
                        request.anyRequest().authenticated();
//                        request.anyRequest().permitAll();
                    })
                    .sessionManagement(sessionManagement->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
