package com.example.parqueadero.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Indico que esta clase es una configuración de Spring
@EnableWebSecurity // Habilito la seguridad web en la aplicación
public class SecurityConfig {

    /**
     * Defino un `PasswordEncoder` que usa BCrypt para encriptar contraseñas.
     * Esto me permite almacenar contraseñas de forma segura.
     * @return Un `BCryptPasswordEncoder` para cifrar las contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuro un servicio de detalles de usuario en memoria.
     * Aquí defino dos usuarios: un administrador y un usuario estándar.
     * Las contraseñas están encriptadas con BCrypt.
     * @return Un `UserDetailsService` que almacena los usuarios en memoria.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Creo un usuario con rol de administrador
        UserDetails admin = User.builder()
                .username("admin") // Nombre de usuario
                .password(passwordEncoder().encode("1234")) // Contraseña encriptada
                .roles("ADMIN") // Rol asignado
                .build();

        // Creo un usuario con rol estándar
        UserDetails usuario = User.builder()
                .username("usuario") // Nombre de usuario
                .password(passwordEncoder().encode("1234")) // Contraseña encriptada
                .roles("USER") // Rol asignado
                .build();

        // Retorno un gestor de usuarios en memoria con los usuarios creados
        return new InMemoryUserDetailsManager(admin, usuario);
    }

    /**
     * Configuro la cadena de filtros de seguridad.
     * Deshabilito la protección CSRF y permito acceso a todas las rutas sin autenticación.
     * También habilito la autenticación básica HTTP.
     * @param http Objeto `HttpSecurity` con la configuración de seguridad.
     * @return La configuración de seguridad construida.
     * @throws Exception Si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilito la protección CSRF para simplificar pruebas
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // Permito acceso a todas las rutas sin autenticación
                )
                .httpBasic(Customizer.withDefaults()); // Habilito autenticación básica HTTP

        return http.build();
    }
}
