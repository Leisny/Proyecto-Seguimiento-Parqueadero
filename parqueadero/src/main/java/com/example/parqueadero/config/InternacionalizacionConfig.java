package com.example.parqueadero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class InternacionalizacionConfig implements WebMvcConfigurer {

    /**
     * Configuro el `LocaleResolver` para gestionar la localización en la aplicación.
     * En este caso, opto por `SessionLocaleResolver` en lugar de `CookieLocaleResolver`
     * para facilitar pruebas y evitar problemas relacionados con el uso de cookies.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        // Establezco el español como idioma predeterminado
        localeResolver.setDefaultLocale(new Locale("es"));
        return localeResolver;
    }

    /**
     * Defino un interceptor que permite cambiar el idioma de la aplicación
     * mediante el uso de un parámetro en la URL, llamado "lang".
     * Esto permite cambiar el idioma dinámicamente sin necesidad de reiniciar la sesión.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        // Especifico el nombre del parámetro que debe detectarse en la URL para cambiar el idioma
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * Configuro la fuente de mensajes para la internacionalización.
     * Especifico el archivo base donde se encuentran los mensajes traducidos
     * y defino la codificación en UTF-8 para evitar problemas con caracteres especiales.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // Indico la ubicación base de los archivos de mensajes
        messageSource.setBasename("messages/messages");
        // Establezco la codificación predeterminada
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Registro el interceptor de cambio de idioma dentro del sistema de Spring,
     * permitiendo que todas las peticiones puedan interpretar el parámetro "lang"
     * para modificar el idioma en tiempo de ejecución.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
