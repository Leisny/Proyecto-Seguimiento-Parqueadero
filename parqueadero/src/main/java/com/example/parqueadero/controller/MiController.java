package com.example.parqueadero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MiController {

    // Inyecto la fuente de mensajes para manejar la internacionalización
    @Autowired
    private MessageSource messageSource;

    /**
     * Este endpoint devuelve un mensaje de bienvenida en el idioma correspondiente.
     * Utilizo `messageSource` para obtener la traducción desde los archivos de mensajes.
     */
    @GetMapping("/publico")
    @ResponseBody
    public String publico() {
        return messageSource.getMessage("app.welcome", null, LocaleContextHolder.getLocale());
    }

    /**
     * Este endpoint responde con un mensaje sobre el acceso a contenido privado.
     * También uso `messageSource` para obtener la traducción adecuada.
     */
    @GetMapping("/privado")
    @ResponseBody
    public String privado() {
        return messageSource.getMessage("app.access", null, LocaleContextHolder.getLocale());
    }

    /**
     * Retorno la vista correspondiente a la página de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Redirijo a la ruta "/publico" cuando se accede a la raíz de la aplicación.
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/publico";
    }

    /**
     * Permito cambiar el idioma de la aplicación de manera explícita.
     * Obtengo el parámetro "lang" de la URL y luego redirijo a la página previa.
     * Si no hay una página previa, redirijo a la raíz.
     */
    @GetMapping("/cambiarIdioma")
    public String cambiarIdioma(@RequestParam String lang, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}
