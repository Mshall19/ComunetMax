package com.comunetmax.ms_contacto.service;

import com.comunetmax.ms_contacto.dto.ContactoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoContacto(ContactoDTO contacto) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Configuramos los datos del envío
        message.setTo("juandapeva282@gmail.com"); // El correo corporativo que recibirá el mensaje
        message.setSubject("Nuevo mensaje de contacto: " + contacto.getNombre());
        message.setText("Has recibido un nuevo mensaje desde la web:\n\n" +
                "Nombre: " + contacto.getNombre() + " " + contacto.getApellidos() + "\n" +
                "Teléfono: " + contacto.getTelefono() + "\n" +
                "Correo: " + contacto.getCorreoElectronico() + "\n" +
                "Mensaje: " + contacto.getMensaje());

        mailSender.send(message);
    }
}