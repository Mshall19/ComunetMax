package com.comunetmax.ms_contacto.service;

import com.comunetmax.ms_contacto.dto.ContactoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker; // IMPORTANTE
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    // El nombre debe ser 'contactoCB' como en tu application.properties
    @CircuitBreaker(name = "contactoCB", fallbackMethod = "fallbackEnviarCorreo")
    public void enviarCorreoContacto(ContactoDTO contacto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            Context context = new Context();
            context.setVariable("nombre", contacto.getNombre());
            context.setVariable("apellidos", contacto.getApellidos());
            context.setVariable("correo", contacto.getCorreoElectronico());
            context.setVariable("telefono", contacto.getTelefono());
            context.setVariable("mensaje", contacto.getMensaje());

            String htmlContent = templateEngine.process("email-template", context);

            helper.setTo("perez@gmail.com");
            helper.setSubject("Nuevo Contacto de: " + contacto.getNombre());
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // Este error disparará el Circuit Breaker si ocurre seguido
            throw new RuntimeException("Error al procesar el envío de correo", e);
        }
    }

    // EL PLAN B: Se ejecuta si el SMTP falla o el circuito está abierto
    public void fallbackEnviarCorreo(ContactoDTO contacto, Throwable e) {
        System.err.println("FALLBACK ACTIVADO: No se pudo enviar el correo a " + contacto.getCorreoElectronico());
        System.err.println("Razón técnica: " + e.getMessage());

        // Aquí podrías guardar el contacto en un log o en una BD local
        // para que no se pierda la información del cliente.
        System.out.println("Información del cliente rescatada: " + contacto.getNombre() + " - " + contacto.getMensaje());
    }
}