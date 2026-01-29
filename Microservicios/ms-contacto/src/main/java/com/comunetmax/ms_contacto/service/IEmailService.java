package com.comunetmax.ms_contacto.service;

import com.comunetmax.ms_contacto.dto.ContactoDTO;

public interface IEmailService {
    void enviarCorreoContacto(ContactoDTO contacto);
}