package com.comunetmax.ms_contacto.controller;

import com.comunetmax.ms_contacto.dto.ContactoDTO;
import com.comunetmax.ms_contacto.service.IEmailService; // Importación necesaria
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; // Importación necesaria
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private IEmailService emailService;

    @PostMapping
    public ResponseEntity<String> enviarMensaje(@Valid @RequestBody ContactoDTO contacto) {
        // Validación básica de campos obligatorios
        if (contacto.getNombre() == null || contacto.getNombre().isEmpty() ||
                contacto.getCorreoElectronico() == null || contacto.getCorreoElectronico().isEmpty() ||
                contacto.getMensaje() == null || contacto.getMensaje().isEmpty()) {

            return ResponseEntity.badRequest().body("Error: Nombre, Correo y Mensaje son campos obligatorios.");
        }

        // Llamada al servicio para enviar el correo
        emailService.enviarCorreoContacto(contacto);

        // Logs de verificación
        System.out.println("Mensaje recibido de: " + contacto.getNombre() + " " + contacto.getApellidos());
        System.out.println("Correo: " + contacto.getCorreoElectronico());
        System.out.println("Contenido: " + contacto.getMensaje());

        return ResponseEntity.ok("Mensaje recibido correctamente. Pronto nos pondremos en contacto.");
    }
}